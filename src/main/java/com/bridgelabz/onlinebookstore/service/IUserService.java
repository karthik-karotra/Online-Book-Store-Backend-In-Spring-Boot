package com.bridgelabz.onlinebookstore.service;


import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;

public interface IUserService {
    String addUser(UserRegistrationDTO userRegistrationDTO);
}
