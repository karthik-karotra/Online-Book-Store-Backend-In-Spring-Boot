package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.CustomerDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.service.ICustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class CustomerDetailsController {

    @Autowired
    ICustomerDetailsService customerDetailsService;

    @PostMapping("/customer")
    public ResponseEntity<ResponseDTO> addCustomerDetails(@RequestBody CustomerDTO customerDTO, @RequestHeader(value = "token") String token) {
        String message = customerDetailsService.addCustomerDetails(customerDTO, token);
        ResponseDTO responseDTO = new ResponseDTO(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity<ResponseDTO> getCustomerDetails(@RequestHeader(value = "token") String token) {
        UserDetails userDetails = customerDetailsService.getAllCustomers(token);
        ResponseDTO responseDTO = new ResponseDTO(userDetails, "Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
