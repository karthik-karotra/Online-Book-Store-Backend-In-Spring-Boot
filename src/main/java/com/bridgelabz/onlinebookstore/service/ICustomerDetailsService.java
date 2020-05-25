package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.CustomerDTO;
import com.bridgelabz.onlinebookstore.models.UserDetails;

public interface ICustomerDetailsService {
    String addCustomerDetails(CustomerDTO customerDTO, String token);
}
