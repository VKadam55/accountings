package com.book.accountings.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
//defining class name as Table name
@Table(name = "accounting_task")
@Data
public class AccountingTask {
    @Column(name = "accounting_task_id")
    @Id
    private String accountingTaskId;

    @Column(name = "accounting_task_name")
    private String accountingTaskName;

    @Column(name = "deadline")
    private Integer deadline;

    @Column(name = "time_needed")
    private Integer timeNeeded;

    @Column(name = "dependent_task_id")
    private String dependentTaskId;
}
