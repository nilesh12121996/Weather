package com.example.search.pojo;

import lombok.Data;
import org.w3c.dom.ls.LSInput;

import java.util.List;

@Data
public class City {
    private String code;
    private String timestamp;
    private List<Integer> data;
}
