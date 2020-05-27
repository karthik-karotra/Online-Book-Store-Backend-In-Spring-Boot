package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.dto.UserRegistrationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    public Integer id;
    public String fullName;
    public String email;

    @JsonIgnore
    public String password;

    public String phoneNo;
    public boolean status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDetails")
    private List<CustomerDetails> customerDetails;

    public UserDetails(UserRegistrationDTO userRegistrationDTO) {
        this.fullName = userRegistrationDTO.fullName;
        this.email = userRegistrationDTO.email;
        this.password = userRegistrationDTO.password;
        this.phoneNo = userRegistrationDTO.phoneNo;
        this.status = userRegistrationDTO.status;
    }

    public UserDetails(UserLoginDTO userLoginDTO) {
        this.email = userLoginDTO.email;
        this.password = userLoginDTO.password;
    }
}
