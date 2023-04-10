package com.mow.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JSONBuilder {

    private String json = new String();
    private Map<String, Object> collections = new HashMap<>();

    public JSONBuilder put(String key, Object value) {
        this.collections.put(key, value);
        return this;
    }

    public String stringify() {
        // JSON.put("key", "value").put("key2", "value2").stringify()
        // => {"key": "value", "key2", "value2"}
        this.json += "{";
        this.getCollections().forEach((key, value) -> {
            this.json += "\""+ key + "\":"; // "key:"
            this.json += "\""+ value + "\","; // "value",
        });
        this.json = this.json.substring(0, this.json.length() - 1); // remove last comma
        this.json += "}";

        return this.json;
    }

    // clear all the properties
    public void clear() {
        this.json = new String();
        this.collections = new HashMap<>();
    }

}
