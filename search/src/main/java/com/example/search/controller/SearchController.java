package com.example.search.controller;

import com.example.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/weather", params = "cities")
    public ResponseEntity<?> getDetailsByCityName(@RequestParam List<String> cities) {
        //TODO
          return new ResponseEntity<>(searchService.getDetailsByCityName(cities), HttpStatus.OK);
    }

    @RequestMapping(value = "/weather", params = "id")
//    @GetMapping("/weather/{id}")
    public ResponseEntity<?> getDetailsByCityId(@RequestParam long id)
    {
        return new ResponseEntity<>(searchService.getDetailsByCityId(id), HttpStatus.OK);
    }
}
