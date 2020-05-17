package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.dto.OrderBookDTO;
import javax.persistence.*;

@Entity
@Table
public class OrderBookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public Integer orderId;
    public Integer bookId;
    public Integer quantity;
    public Double orderPrice;
    public String customerName;
    public String mobileNo;
    public String pincode;
    public String locality;
    public String address;
    public String city;
    public String landmark;
    public String type;

    public OrderBookDetails(OrderBookDTO orderBookDTO) {
        this.bookId = orderBookDTO.bookId;
        this.quantity = orderBookDTO.quantity;
        this.orderPrice = orderBookDTO.orderPrice;
        this.customerName = orderBookDTO.customerName;
        this.mobileNo = orderBookDTO.mobileNo;
        this.pincode = orderBookDTO.pincode;
        this.locality = orderBookDTO.locality;
        this.address = orderBookDTO.address;
        this.city = orderBookDTO.city;
        this.landmark = orderBookDTO.landmark;
        this.type = orderBookDTO.type;
    }
}
