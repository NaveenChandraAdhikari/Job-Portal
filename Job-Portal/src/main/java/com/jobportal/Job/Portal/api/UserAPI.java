package com.jobportal.Job.Portal.api;


import com.jobportal.Job.Portal.dto.LoginDTO;
import com.jobportal.Job.Portal.dto.ResponseDTO;
import com.jobportal.Job.Portal.dto.UserDTO;
import com.jobportal.Job.Portal.exception.JobPortalException;
import com.jobportal.Job.Portal.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
//kisi bhi origin se request
@CrossOrigin
@RequestMapping("/users")


public class UserAPI

{

@Autowired
    private UserService userService;

@PostMapping("/register")
public ResponseEntity<UserDTO> registerUser( @RequestBody  @Valid UserDTO userDTO) throws JobPortalException {
userDTO=userService.registerUser(userDTO);
    return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
}

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser( @RequestBody  @Valid LoginDTO loginDTO) throws JobPortalException {
        return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
    }



    @PostMapping("/sendOtp/{email}")

    public ResponseEntity<ResponseDTO> sendOtp(@PathVariable  @Email(message = "{user.email.invalid}") String email) throws Exception {
    userService.sendOtp(email);
        return new ResponseEntity<>(new ResponseDTO("Otp sent SuccessFully "), HttpStatus.OK);
    }



    @GetMapping("/verifyOtp/{email}/{otp}")

    public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable   @Email(message = "{user.email.invalid}") String email, @PathVariable @Pattern(regexp="^[0-9]{6}$", message="{otp.invalid}" ) String otp ) throws JobPortalException {
        userService.verifyOtp(email,otp);
        return new ResponseEntity<>(new ResponseDTO("Otp has been Verified "), HttpStatus.OK);
    }




//    for change Password
@PostMapping("/changePass")
public ResponseEntity<ResponseDTO> changePassword( @RequestBody  @Valid LoginDTO loginDTO) throws JobPortalException {
    return new ResponseEntity<>(userService.changePassword(loginDTO), HttpStatus.OK);
}


}
