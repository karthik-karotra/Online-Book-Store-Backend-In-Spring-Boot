package com.bridgelabz.onlinebookstore.service;


import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;

public interface IUserService {
    String addUser(UserRegistrationDTO userRegistrationDTO);
    String userLogin(UserLoginDTO userLoginDTO);
    String emailVerification(String token);
}
