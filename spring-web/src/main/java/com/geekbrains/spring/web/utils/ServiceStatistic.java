package com.geekbrains.spring.web.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServiceStatistic {

    private Map<String, Long> timesMap;
    private Map<String, Long> numberOfHitsMap;

    public ServiceStatistic() {
        timesMap = new HashMap<>();
        numberOfHitsMap = new HashMap<>();
    }

    public void addTimeAndHits(String inputString, Long time) {
        String className = inputString.substring(0, inputString.indexOf('$'));
        if (!timesMap.containsKey(className)) {
            timesMap.put(className, time);
            numberOfHitsMap.put(className, 1l);
        }
        timesMap.put(className, timesMap.get(className) + time);
        numberOfHitsMap.put(className, numberOfHitsMap.get(className) + 1);
    }

    public void printStatistics() {
         for(Map.Entry<String, Long> entry : timesMap.entrySet()) {
             String serviceName = entry.getKey();
             Long time = entry.getValue();
             Long hits = numberOfHitsMap.get(serviceName);
             System.out.println(serviceName + " - time: " + time + " ms, "
                    + " number of hits: " + hits + " times");
        }
    }

    public List<String> getStatList() {
        List<String> list = new ArrayList<>();
        for(Map.Entry<String, Long> entry : timesMap.entrySet()) {
            String serviceName = entry.getKey();
            Long time = entry.getValue();
            Long hits = numberOfHitsMap.get(serviceName);
            list.add(serviceName + " - time: " + time + " ms, "
                    + " number of hits: " + hits + " times");
        }
        return list;
    }

}
