package com.example.search.service;

import com.example.search.config.DetailServiceConfig;
import com.example.search.pojo.City;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class SearchServiceImp implements SearchService{
    private final RestTemplate restTemplate;

    public SearchServiceImp(RestTemplate getRestTemplate) {
        this.restTemplate = getRestTemplate;
    }

    @Override
    @Retryable(include = IllegalAccessError.class)
    public List<Map<String, Map>> getDetailsByCityName(List<String> cities) {
        List<Map<String, Map>> result= new ArrayList<>();
        ExecutorService executorService=Executors.newFixedThreadPool(5);
        List<City> listcompletableFuture=new ArrayList<>();
        try {
            for (String tmp : cities
            ) {
                Runnable runnable= () -> {listcompletableFuture.add(restTemplate.getForObject(DetailServiceConfig.queryWeatherByCity + tmp, City.class));};
                executorService.execute(runnable);
                }
            executorService.awaitTermination(20, TimeUnit.SECONDS);
//            executorService.shutdown();
//            System.out.println(listcompletableFuture);
            for (City city: listcompletableFuture
                 ) {
                if (city != null || city.getData() != null) {
                    result.add(restTemplate.getForObject(DetailServiceConfig.queryWeatherById + city.getData().get(0), HashMap.class));
                }
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public Map<String, Map> getDetailsByCityId(Long id) {
        return restTemplate.getForObject(DetailServiceConfig.queryWeatherById+id, HashMap.class);
    }

    public Runnable setWeatherdetails(List<Map<String, Map>> result, String city) throws ExecutionException, InterruptedException{
        CompletableFuture<City> completableFuture=new CompletableFuture<>();
        CompletableFuture<Map<String, Map>> completableFuture1=new CompletableFuture<>();
        completableFuture.complete(restTemplate.getForObject(DetailServiceConfig.queryWeatherByCity + city, City.class));
        return null;
    }
}
