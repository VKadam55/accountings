package com.book.accountings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class AccountingsApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AccountingsApplication.class, args);
        int sum = Stream.iterate(new int[]{0,1}, x-> new int[]{x[1], x[0]+x[1]}).limit(8)
                .map(x -> x[0])
                .collect(Collectors.toList())
                .stream()
                .distinct()
                .filter(i -> i% 2 == 0)
                .mapToInt(i -> i)
                .sum();
        System.out.println(sum);
    }

}
