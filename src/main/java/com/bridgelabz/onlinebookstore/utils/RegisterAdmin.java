package com.bridgelabz.onlinebookstore.utils;

import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.bridgelabz.onlinebookstore.filterenums.UserRole;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class RegisterAdmin {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Optional<UserDetails> admin = userRepository.findByEmail("countrybookshop@gmail.com");
        if (!admin.isPresent()) {
            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("Admin", "countrybookshop@gmail.com", "RuntimeTerror@123", "9854784512", true, UserRole.ADMIN);
            UserDetails userDetails = new UserDetails(userRegistrationDTO);
            userDetails.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.password));
            userRepository.save(userDetails);
        }
    }
}
