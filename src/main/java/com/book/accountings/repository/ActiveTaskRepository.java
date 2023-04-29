package com.book.accountings.repository;

import com.book.accountings.entity.ActiveTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveTaskRepository extends JpaRepository<ActiveTask, Integer> {

}
