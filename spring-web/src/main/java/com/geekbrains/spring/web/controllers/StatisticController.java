package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.utils.ServiceStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
public class StatisticController {

    @Autowired
    private ServiceStatistic serviceStatistic;

    @GetMapping
    public List<String> getStatistic() {
        return serviceStatistic.getStatList();
    }

}
