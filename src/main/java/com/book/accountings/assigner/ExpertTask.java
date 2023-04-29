//package com.book.accountings.assigner;
//
//import com.book.accountings.entity.AccountingTask;
//import com.book.accountings.entity.ActiveTask;
//import com.book.accountings.entity.CustomerRequest;
//import com.book.accountings.entity.Expert;
//import com.book.accountings.service.AccountingTaskService;
//import com.book.accountings.service.ActiveTaskService;
//import com.book.accountings.service.CustomerRequestService;
//import com.book.accountings.service.ExpertService;
//import lombok.SneakyThrows;
//
//import java.time.LocalDateTime;
//
//public class ExpertTask implements Runnable{
//    private ExpertService expertService;
//    private ActiveTaskService activeTaskService;
//    private AccountingTaskService accountingTaskService;
//    private CustomerRequestService customerRequestService;
//    private ActiveTask activeTask;
//    private Expert expert;
//    private AccountingTask accountingTask;
//    private Boolean isRunWithMinutes;
//    public ExpertTask(ExpertService expertService,
//                      ActiveTaskService activeTaskService,
//                      AccountingTaskService accountingTaskService,
//                      CustomerRequestService customerRequestService,
//                      ActiveTask activeTask,
//                      Expert expert,
//                      AccountingTask accountingTask,
//                      Boolean isRunWithMinutes){
//        this.expertService = expertService;
//        this.activeTaskService = activeTaskService;
//        this.accountingTaskService = accountingTaskService;
//        this.customerRequestService = customerRequestService;
//        this.activeTask = activeTask;
//        this.expert = expert;
//        this.accountingTask = accountingTask;
//        this.isRunWithMinutes = isRunWithMinutes;
//    }
//
//    @SneakyThrows
//    @Override
//    public void run() {
//
//        expert.setExpertStatus("WORKING");
//        expertService.saveOrUpdate(expert);
//
//        activeTask.setExpertId(expert.getExpertId());
//        activeTask.setStartDateTime(LocalDateTime.now());
//        activeTask.setTaskStatus("IN_PROGRESS");
//        setEndDateTimeInActiveTask();
//        activeTaskService.saveOrUpdate(activeTask);
//
//        CustomerRequest customerRequest =
//                customerRequestService.getCustomerRequest(activeTask.getRequestId());
//        customerRequest.setRequestStatus(
//                String.format("IN_PROGRESS: %s is Running", accountingTask.getAccountingTaskId()));
//        customerRequestService.saveOrUpdate(customerRequest);
//
//        Thread.sleep(getMilliSecondsToSleep());
//
//        activeTask.setTaskStatus("COMPLETED");
//        activeTaskService.saveOrUpdate(activeTask);
//
//        expert.setAvailableHoursForDay(expert.getAvailableHoursForDay() - accountingTask.getTimeNeeded());
//        if(expert.getAvailableHoursForDay() > 0) {
//            expert.setExpertStatus("AVAILABLE");
//        }
//        else{
//            expert.setExpertStatus("OFF_WORK_FOR_THE_DAY");
//        }
//        expertService.saveOrUpdate(expert);
//
//        if("Task 4".equals(accountingTask.getAccountingTaskId())){
//            customerRequest.setRequestStatus("COMPLETED");
//            customerRequestService.saveOrUpdate(customerRequest);
//        }
//        else{
//            AccountingTask nextAccountingTask =
//                    accountingTaskService.getTaskWhoseDependentTaskIsCompleted(accountingTask.getAccountingTaskId());
//
//            ActiveTask nextActiveTask = new ActiveTask();
//            nextActiveTask.setAccountingTaskId(nextAccountingTask.getAccountingTaskId());
//            nextActiveTask.setTaskStatus("CREATED");
//            nextActiveTask.setRequestId(customerRequest.getRequestId());
//            nextActiveTask.setDeadlineDateTime(LocalDateTime.now().plusDays(nextAccountingTask.getDeadline()));
//            activeTaskService.saveOrUpdate(nextActiveTask);
//        }
//    }
//
//    private void setEndDateTimeInActiveTask() {
//        if(isRunWithMinutes) {
//            activeTask.setEndDateTime(LocalDateTime.now().plusMinutes(accountingTask.getTimeNeeded()));
//        }
//        else{
//            activeTask.setEndDateTime(LocalDateTime.now().plusHours(accountingTask.getTimeNeeded()));
//        }
//    }
//
//    private long getMilliSecondsToSleep() {
//        long milliSecondsToSleep;
//        if(isRunWithMinutes){
//            milliSecondsToSleep = accountingTask.getTimeNeeded() * 60 * 1000;
//        }
//        else{
//            milliSecondsToSleep = accountingTask.getTimeNeeded() * 60 * 60 * 1000;
//        }
//        return milliSecondsToSleep;
//    }
//}
