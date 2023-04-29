package com.book.accountings.service;

import com.book.accountings.entity.ActiveTask;
import com.book.accountings.entity.Expert;
import com.book.accountings.repository.ActiveTaskRepository;
import com.book.accountings.repository.ExpertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpertService {

    @Autowired
    private ExpertRepository expertRepository;

    @Autowired
    private ActiveTaskRepository activeTaskRepository;

    public List<Expert> getAllAvailableExperts() {
        return expertRepository.findAll().stream().filter(expert ->
                "AVAILABLE".equals(expert.getExpertStatus())).collect(Collectors.toList());
    }

    public List<ActiveTask> getAllActiveTasks(Integer expertId) {
        return activeTaskRepository.findAll().stream().filter(expert ->
                expertId.equals(expert.getExpertId())).collect(Collectors.toList());
    }

    public Expert saveOrUpdate(Expert expert) {
        return expertRepository.save(expert);
    }


    /**
     * This is the cron job which refills the capacity of the experts everyday at midnight 12 am.
     * So that next day they can be assiged with the queued customer request's tasks.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void timeRefillerForExperts() {

        List<Expert> experts = expertRepository.findAll();
        for (Expert expert : experts) {
            expert.setAvailableHoursForDay(8);
            expertRepository.save(expert);
        }
    }
}
