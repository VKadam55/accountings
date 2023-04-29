package com.book.accountings.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
//defining class name as Table name
@Table(name = "customer")
@Data
public class Customer {
    @Column(name = "customer_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(name = "customer_name")
    private String customerName;
}
