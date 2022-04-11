package com.example.search.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SearchService {

    List<Map<String, Map>> getDetailsByCityName(List<String> cities);
    Map<String, Map> getDetailsByCityId(Long id);
}
