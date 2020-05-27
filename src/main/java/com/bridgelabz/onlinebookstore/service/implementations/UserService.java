package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.exceptions.UserException;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.ICartService;
import com.bridgelabz.onlinebookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ICartService cartService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    HttpServletRequest httpServletRequest;


    @Override
    public String addUser(UserRegistrationDTO userRegistrationDTO) {
        boolean userDetailsByEmail = userRepository.findByEmail(userRegistrationDTO.email).isPresent();
        if (userDetailsByEmail) {
            throw new UserException("User With Same Email Id Already Exists", UserException.ExceptionType.USER_ALREADY_EXISTS);
        }
        String password = bCryptPasswordEncoder.encode(userRegistrationDTO.password);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        userDetails.password = password;
        userRepository.save(userDetails);
        return "Registration Successfull !! Please Check Your Registered Email For Email Verification";
    }

    @Override
    public String userLogin(UserLoginDTO userLoginDTO) {
        Optional<UserDetails> userDetailsByEmail = userRepository.findByEmail(userLoginDTO.email);
        if (userDetailsByEmail.isPresent() && userDetailsByEmail.get().status) {
            boolean password = bCryptPasswordEncoder.matches(userLoginDTO.password, userDetailsByEmail.get().password);
            if (!password) {
                throw new UserException("Invalid Password!!!Please Enter Correct Password", UserException.ExceptionType.PASSWORD_INVALID);
            }
            return "Login Successful";
        }
        throw new UserException("Enter Registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND);
    }
}
