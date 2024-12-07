package com.jobportal.Job.Portal.service;

import com.jobportal.Job.Portal.dto.LoginDTO;
import com.jobportal.Job.Portal.dto.ResponseDTO;
import com.jobportal.Job.Portal.dto.UserDTO;
import com.jobportal.Job.Portal.entity.Data;
import com.jobportal.Job.Portal.entity.OTP;
import com.jobportal.Job.Portal.entity.User;
import com.jobportal.Job.Portal.exception.JobPortalException;
import com.jobportal.Job.Portal.repository.OTPRepository;
import com.jobportal.Job.Portal.repository.UserRepository;
import com.jobportal.Job.Portal.utility.Utilities;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImplementation implements UserService{

    //automatically object insert
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    //this doesnt work as field injection wont work here so make setter injection..thats why making securityconfig
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProfileService profileService;


    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {

        Optional<User> optional =userRepository.findByEmail(userDTO.getEmail());

        if(optional.isPresent()) throw new JobPortalException("USER_FOUND");

//        this way we set the profile ,,we didnt make apis for this
        userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));


        userDTO.setId(Utilities.getNextSequence("users"));

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

//convert to user
        User user =userDTO.toEntity();
        

        userRepository.save(user);

        return user.toDTO();
    }

/*
In loginUser, the focus is on directly fetching the user. If the user is not found, you immediately throw an exception. You don't store the Optional because you only care about the presence or absence of the user. Instead of handling the Optional manually, you're using the method orElseThrow to streamline this logic.However, you could use Optional similarly in loginUser for consistency, like this:

java
Copy code
Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
if (!optionalUser.isPresent()) {
    throw new JobPortalException("USER_NOT_FOUND");
}
User user = optionalUser.get();
 */

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException {

        // Find the user by email
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        // Check if the password matches..requestpassword,databasestoredPassword
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new JobPortalException("INVALID_CREDENTIALS");
        }

return user.toDTO();
    }






    @Override
    public boolean sendOtp(String email) throws Exception {
        System.out.println("Searching for email: " + email);

         User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));


//        for message send ..we have mime message and simple message so i use mime
        MimeMessage mm=mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm,true);
        message.setTo(email);
        message.setSubject("YOUR OTP CODE");

        String genOtp=Utilities.generateOTP();
        OTP otp = new OTP(email,genOtp, LocalDateTime.now());

        otpRepository.save(otp);


//        message.setText("Your code is ::"+genOtp,false);
        message.setText(Data.getMessageBody(genOtp,user.getName()),true);
        mailSender.send(mm);



        return true;
    }

    @Override
    public boolean verifyOtp(String email,String otp) throws JobPortalException {
        OTP otpEntity = otpRepository.findById(email).orElseThrow(()->new JobPortalException("OTP_NOT_FOUND"));

        if (!otpEntity.getOtpCode().equals(otp))throw  new JobPortalException("OTP_INCORRECT");


        return true;
    }

    @Override
    public ResponseDTO changePassword(LoginDTO loginDTO) throws JobPortalException {


        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        userRepository.save(user);
        return new ResponseDTO("Password changed successfully");

    }

//    for otp expiration we can use reddis but it is complex for now so write  a method for that
    @Scheduled(fixedRate = 60000)
    public void removeExpiredOTPs(){

        LocalDateTime expiry =LocalDateTime.now().minusMinutes(5);
//        System.out.println("hrloow");

        List<OTP> expiredOTPs= otpRepository.findByCreationTimeBefore(expiry);

        if(!expiredOTPs.isEmpty()){


            otpRepository.deleteAll(expiredOTPs);
            System.out.println("Removed"+ expiredOTPs.size() +" expired OTPs ");


        }

    }
}
