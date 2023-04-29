package com.book.accountings.repository;

import com.book.accountings.entity.AccountingTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingTaskRepository extends JpaRepository<AccountingTask, String> {
}
