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
        sendEmail(userRegistrationDTO.email,httpServletRequest.getHeader("Referer"));
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
        String urlAddress = httpServletRequest.getHeader("origin");
        System.out.println(urlAddress);
        sendEmail(email,urlAddress);
        return "Verification Link Has Been Sent To Your Account";
    }

    @Override
    public String forgotPassword(String email, HttpServletRequest httpServletRequest) {
        Optional<UserDetails> optionalUserDetails = userRepository.findByEmail(email);
        if (!optionalUserDetails.isPresent()) {
            throw new UserException("Enter Registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND);
        }
        String token = tokenGenerator.generateToken(optionalUserDetails.get().getId(), applicationProperties.getJwtVerificationExpiration());
        String url = httpServletRequest.getRequestURL().toString();
        String urlAddress = url.substring(0, url.lastIndexOf("/")) + "/resetpassword/" + token;
        emailService.notifyThroughEmail(email, "Reset Password", "Please click on below link to reset your password"+urlAddress);
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

    public void sendEmail(String email, String urlAddress){
        Optional<UserDetails> optionalUserDetails = userRepository.findByEmail(email);
        String token = tokenGenerator.generateToken(optionalUserDetails.get().id,applicationProperties.getJwtVerificationExpiration());
        urlAddress = urlAddress.contains("resendemail") ?
                "<a href='" + urlAddress.substring(0, urlAddress.indexOf("r") - 1) + "/resendemail/?token=" + token + "'>" + "Email</a>" :
                "<a href='" + urlAddress + "resendemail/?token=" + token + "'>" + "Email</a>";
        emailService.notifyThroughEmail(email,"Email Verification","To confirm your account, please click here : "
                +urlAddress);
    }
}
