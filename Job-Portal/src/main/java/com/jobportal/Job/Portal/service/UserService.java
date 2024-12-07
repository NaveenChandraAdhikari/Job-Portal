package com.jobportal.Job.Portal.service;

import com.jobportal.Job.Portal.dto.LoginDTO;
import com.jobportal.Job.Portal.dto.ResponseDTO;
import com.jobportal.Job.Portal.dto.UserDTO;
import com.jobportal.Job.Portal.exception.JobPortalException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

public interface UserService {


    public UserDTO registerUser(  UserDTO userDTO ) throws JobPortalException;


    public UserDTO loginUser( LoginDTO loginDTO) throws JobPortalException;

    public boolean sendOtp(String email) throws  Exception;
    boolean verifyOtp(String email,String otp)  throws  JobPortalException ;

    ResponseDTO changePassword( LoginDTO loginDTO) throws JobPortalException ;
}
