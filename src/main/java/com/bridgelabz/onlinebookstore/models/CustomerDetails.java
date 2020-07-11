package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.dto.CustomerDTO;
import com.bridgelabz.onlinebookstore.filterenums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String address;
    public String city;
    public String pincode;
    public String landmark;
    public String locality;

    @Enumerated(EnumType.STRING)
    public AddressType type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails userDetails;

    public CustomerDetails(CustomerDTO customerDTO) {
        this.address = customerDTO.address;
        this.city = customerDTO.city;
        this.pincode = customerDTO.pincode;
        this.landmark = customerDTO.landmark;
        this.locality = customerDTO.locality;
    }
}
