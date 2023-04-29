package com.book.accountings.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
//defining class name as Table name
@Table(name = "customer_request")
@Data
public class CustomerRequest {
    @Column(name = "customer_request_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerRequestId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "request_status", columnDefinition = "VARCHAR(30) DEFAULT 'CREATED'")
    private String requestStatus;
}
