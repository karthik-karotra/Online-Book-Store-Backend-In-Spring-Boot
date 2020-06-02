package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.exceptions.UserException;
import com.bridgelabz.onlinebookstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException("Invalid Data!!!!! Please Enter Valid Data", UserException.ExceptionType.INVALID_DATA);
        }
        String message = userService.addUser(userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        if (bindingResult.hasErrors()) {
            throw new UserException("Invalid Data!!!!! Please Enter Valid Data", UserException.ExceptionType.INVALID_DATA);
        }
        String token = userService.userLogin(userLoginDTO);
        httpServletResponse.setHeader("Authorization", token);
        return new ResponseEntity("LOGIN SUCCESSFUL", HttpStatus.OK);
    }

    @GetMapping("/register/confirmation")
    public ResponseEntity accountConfirmation(@RequestParam("token") String token){
        String message = userService.emailVerification(token);
        return new ResponseEntity(message,HttpStatus.OK);
    }

    @PostMapping("/register/resend/confirmation/{email}")
    public ResponseEntity resendConfirmation(@PathVariable String email){
        String message = userService.resendConfirmation(email);
        return new ResponseEntity(message,HttpStatus.OK);
    }

    @PostMapping("/forgot/password/{email}")
    public ResponseEntity forgotPassword(@PathVariable String email, HttpServletRequest httpServletRequest){
        String message = userService.forgotPassword(email, httpServletRequest);
        return new ResponseEntity(message, HttpStatus.OK);
    }


}
