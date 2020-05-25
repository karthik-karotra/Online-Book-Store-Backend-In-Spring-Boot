package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.dto.CustomerDTO;
import com.bridgelabz.onlinebookstore.filterenums.AddressType;
import com.bridgelabz.onlinebookstore.models.CustomerDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.ICustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerDetailsService implements ICustomerDetailsService {

    @Autowired
    CartService cartService;

    @Autowired
    UserRepository userRepository;

    @Override
    public String addCustomerDetails(CustomerDTO customerDTO, String token) {
        UserDetails userDetails = cartService.isUserPresent(token);
        CustomerDetails customerDetails = new CustomerDetails(customerDTO);
        List<CustomerDetails> customerDetailsList = new ArrayList<>();
        customerDetailsList.add(customerDetails);
        userDetails.getCustomerDetails().add(customerDetails);
        userDetails.setCustomerDetails(customerDetailsList);
        customerDetails.setUserDetails(userDetails);
        customerDetails.setType(AddressType.valueOf(customerDTO.type));
        userRepository.save(userDetails);
        return "Customer Details Added Successfully";
    }

}
