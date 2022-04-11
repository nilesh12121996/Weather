package com.example.details.service;



import com.example.details.config.EndpointConfig;
import com.example.details.pojo.City;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService{

    private final RestTemplate restTemplate;


    public WeatherServiceImpl(RestTemplate getRestTemplate) {
        this.restTemplate = getRestTemplate;
    }

    @Override
    @Retryable(include = IllegalAccessError.class)
    public List<Integer> findCityIdByName(String city) {
        List<Integer> ans = new ArrayList<>();
        try {
            City[] cities = restTemplate.getForObject(EndpointConfig.queryWeatherByCity + city, City[].class);
            for(City c: cities) {
                if(c != null && c.getWoeid() != null) {
                    ans.add(c.getWoeid());
                }
             }
            }
        catch (Exception e)
            {
                System.out.println(e);
            }
        finally {

        }
        return ans;
    }

    @Override
    //change findcitynamebyid => find weather details by id
    public Map<String, Map> findCityNameById(int id) {
        Map<String, Map> ans = restTemplate.getForObject(EndpointConfig.queryWeatherById + id, HashMap.class);
        return ans;
    }
}
