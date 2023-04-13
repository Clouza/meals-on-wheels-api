package com.mow.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GlobalRequest {

    private Map<String, Object> objects = new HashMap<>();

    @JsonAnySetter
    public void setObjects(String key, Object value) {
        this.objects.put(key, value);
    }

    public Map<String, Object> getObjects() {
        return objects;
    }

    public String get(String key) {
        return String.valueOf(objects.get(key));
    }

}
