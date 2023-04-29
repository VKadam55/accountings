package com.book.accountings.service;

import com.book.accountings.entity.AccountingTask;
import com.book.accountings.entity.ActiveTask;
import com.book.accountings.entity.CustomerRequest;
import com.book.accountings.entity.Expert;
import com.book.accountings.repository.ActiveTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ActiveTaskService {

    @Autowired
    private ActiveTaskRepository activeTaskRepository;

    @Autowired
    private AccountingTaskService accountingTaskService;

    @Autowired
    private CustomerRequestService customerRequestService;

    @Autowired
    private ExpertService expertService;

    public List<ActiveTask> getActiveTasks(String taskStatus) {

        List<ActiveTask> activeTasks =
                activeTaskRepository.findAll();
        activeTasks = activeTasks.stream().filter(activeTask ->
                taskStatus.equals(activeTask.getTaskStatus())).collect(Collectors.toList());
        activeTasks.sort(Comparator.comparing(ActiveTask::getDeadlineDateTime));
        return activeTasks;
    }

    public void executeActiveTaskAction(Integer activeTaskId) {
        Optional<ActiveTask> activeTaskOptional = activeTaskRepository.findById(activeTaskId);
        ActiveTask activeTask = activeTaskOptional.get();
        activeTask.setTaskStatus("COMPLETED");
        activeTask.setEndDateTime(LocalDateTime.now());
        activeTaskRepository.save(activeTask);

        CustomerRequest customerRequest =
                customerRequestService.getCustomerRequest(activeTask.getRequestId());

        if ("Task 4".equals(activeTask.getAccountingTaskId())) {
            customerRequest.setRequestStatus("COMPLETED");
            customerRequestService.saveOrUpdate(customerRequest);
        } else {
            createNextTask(activeTask, customerRequest);
        }
    }

    private void createNextTask(ActiveTask activeTask, CustomerRequest customerRequest) {
        AccountingTask nextAccountingTask =
                accountingTaskService.getTaskWhoseDependentTaskIsCompleted(activeTask.getAccountingTaskId());

        ActiveTask nextActiveTask = new ActiveTask();
        nextActiveTask.setAccountingTaskId(nextAccountingTask.getAccountingTaskId());
        nextActiveTask.setTaskStatus("CREATED");
        nextActiveTask.setRequestId(customerRequest.getRequestId());
        nextActiveTask.setDeadlineDateTime(LocalDateTime.now().plusDays(nextAccountingTask.getDeadline()));
        activeTaskRepository.save(nextActiveTask);

        boolean isExpertAllocated = false;
        List<Expert> experts = expertService.getAllAvailableExperts();
        for (Expert expert : experts) {
            if (expert.getAvailableHoursForDay() >= nextAccountingTask.getTimeNeeded()) {
                nextActiveTask.setExpertId(expert.getExpertId());
                nextActiveTask.setTaskStatus("ASSIGNED");
                expert.setAvailableHoursForDay(expert.getAvailableHoursForDay() - nextAccountingTask.getTimeNeeded());
                activeTaskRepository.save(nextActiveTask);
                expertService.saveOrUpdate(expert);
                isExpertAllocated = true;
                break;
            }
            if (expert.getAvailableHoursForDay() <= 0) {
                expert.setExpertStatus("EXHAUSTED");
                expertService.saveOrUpdate(expert);
            }
        }
        if(!isExpertAllocated) {
            //add task in the activeTasks to allocate expert later.
            customerRequestService.addNewTasksToQueue(nextActiveTask);
        }
    }

    public ActiveTask saveOrUpdate(ActiveTask activeTask) {

        int sum = Stream.iterate(new int[]{0,1}, x-> new int[]{x[1], x[0]+x[1]}).limit(7)
                .map(x -> x[0])
                .collect(Collectors.toList())
                .stream()
                .distinct()
                .filter(i -> i% 2 == 0)
                .mapToInt(i -> i)
                .sum();
        System.out.println(sum);
        return activeTaskRepository.save(activeTask);
    }
}

