package com.book.accountings.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
//defining class name as Table name
@Table(name = "active_task")
@Data
public class ActiveTask {
    @Column(name = "active_task_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activeTaskId;

    @Column(name = "accounting_task_id")
    private String accountingTaskId;

    @Column(name = "expert_id")
    private Integer expertId;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "created_date_time")
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "deadline_date_time")
    private LocalDateTime deadlineDateTime;
}
