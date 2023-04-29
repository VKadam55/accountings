package com.book.accountings.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
//defining class name as Table name
@Table(name = "expert")
@Data
public class Expert {
    @Column(name = "expert_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expertId;

    @Column(name = "expert_name")
    private String expertName;

    @Column(name = "expert_status")
    private String expertStatus;

    @Column(name = "available_hours_for_day")
    private Integer availableHoursForDay;
}
