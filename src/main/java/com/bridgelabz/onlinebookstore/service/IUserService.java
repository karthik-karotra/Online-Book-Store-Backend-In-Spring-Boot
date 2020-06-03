package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.ResetPasswordDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import javax.servlet.http.HttpServletRequest;

public interface IUserService {
    String addUser(UserRegistrationDTO userRegistrationDTO);
    String userLogin(UserLoginDTO userLoginDTO);
    String emailVerification(String token);
    String resendConfirmation(String email);
    String forgotPassword(String email, HttpServletRequest httpServletRequest);
    String resetPassword(ResetPasswordDTO resetPasswordDTO, String token);
}
