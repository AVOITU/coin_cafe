package com.example.sondagecoincafe.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BusinessException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private List<String> keys;

    public BusinessException() {
        super();
    }

    public List<String> getKeys() {
        return keys;
    }

    public void addKey(String key) {
        if (keys == null) {
            keys = new ArrayList<>();
        }
        this.keys.add(key);
    }




}