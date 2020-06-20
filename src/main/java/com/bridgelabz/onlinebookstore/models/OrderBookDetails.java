package com.bridgelabz.onlinebookstore.models;

import com.bridgelabz.onlinebookstore.filterenums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class OrderBookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    public Integer orderId;
    public Double totalPrice;
    public String orderDate;


    public OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerDetails customerDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails userDetails;

    @OneToMany(mappedBy = "orderBookDetails")
    private List<OrderProduct> orderProduct;
}
