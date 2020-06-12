package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.dto.ResetPasswordDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.JWTException;
import com.bridgelabz.onlinebookstore.exceptions.OnlineBookStoreException;
import com.bridgelabz.onlinebookstore.exceptions.UserException;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.ICartService;
import com.bridgelabz.onlinebookstore.service.IUserService;
import com.bridgelabz.onlinebookstore.utils.IEmailService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import com.bridgelabz.onlinebookstore.utils.implementation.EmailVerificationTemplate;
import com.bridgelabz.onlinebookstore.utils.implementation.ResetPasswordTemplate;
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
    ITokenGenerator tokenGenerator;

    @Autowired
    IEmailService emailService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    EmailVerificationTemplate emailVerificationTemplate;

    @Autowired
    ResetPasswordTemplate resetPasswordTemplate;

    @Override
    public String addUser(UserRegistrationDTO userRegistrationDTO) {
        boolean userDetailsByEmail = userRepository.findByEmail(userRegistrationDTO.email).isPresent();
        if (userDetailsByEmail) {
            throw new UserException("User With Same Email Id Already Exists", UserException.ExceptionType.USER_ALREADY_EXISTS);
        }
        String password = bCryptPasswordEncoder.encode(userRegistrationDTO.password);
        UserDetails userDetails = new UserDetails(userRegistrationDTO);
        userDetails.password = password;
        UserDetails userDetails1 = userRepository.save(userDetails);
        sendEmail(userDetails1,httpServletRequest.getHeader("Referer"));
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
            String token = tokenGenerator.generateToken(userDetailsByEmail.get().id, applicationProperties.getJwtLoginExpiration());
            return token;
        }
        throw new UserException("Enter Registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND);
    }

    @Override
    public String emailVerification(String token) {
        int id = tokenGenerator.getId(token);
        Optional<UserDetails> userDetailsById = userRepository.findById(id);
        if (!userDetailsById.isPresent()){
            throw new JWTException("User With Same Email Id Already Exists", JWTException.ExceptionType.USER_NOT_FOUND);
        }
        UserDetails userDetails = userDetailsById.get();
        userDetails.setStatus(true);
        userRepository.save(userDetails);
        cartService.createCart(userDetails);
        return "Account Verified";
    }

    @Override
    public String resendConfirmation(String email) {
        Optional<UserDetails> optionalUserDetails = userRepository.findByEmail(email);
        if (!optionalUserDetails.isPresent()){
            throw new UserException("Enter Registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND);
        }
        UserDetails userDetails = optionalUserDetails.get();
        String urlAddress = httpServletRequest.getHeader("origin");
        sendEmail(userDetails,urlAddress);
        return "Verification Link Has Been Sent To Your Account";
    }

    @Override
    public String forgotPassword(String email, HttpServletRequest httpServletRequest) {
        Optional<UserDetails> optionalUserDetails = userRepository.findByEmail(email);
        if (!optionalUserDetails.isPresent()) {
            throw new UserException("Enter Registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND);
        }
        String token = tokenGenerator.generateToken(optionalUserDetails.get().getId(), applicationProperties.getJwtVerificationExpiration());
        String url = httpServletRequest.getHeader("Referer");
        String urlAddress = url.substring(0, url.indexOf("f") - 1) + "/resetpassword/?token=" + token;
        String message = resetPasswordTemplate.getResetPasswordString(urlAddress, optionalUserDetails.get().fullName);
        emailService.notifyThroughEmail(email, "Reset Password", message);
        return "We've Sent A Password Reset Link To Your Email Address";
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO, String token) {
        int id = tokenGenerator.getId(token);
        String encodePassword = bCryptPasswordEncoder.encode(resetPasswordDTO.password);
        UserDetails userDetails = userRepository.findById(id).get();
        userDetails.setPassword(encodePassword);
        userRepository.save(userDetails);
        return "Password Reseted Successfully";
    }

    public void sendEmail(UserDetails userDetails, String urlAddress){
        String token = tokenGenerator.generateToken(userDetails.id,applicationProperties.getJwtVerificationExpiration());
        urlAddress = urlAddress + "verification/?token=" + token;
        String message = emailVerificationTemplate.getVerificationEmailTemplate(urlAddress, userDetails.fullName);
        emailService.notifyThroughEmail(userDetails.email,"Email Verification",message);
    }
}
