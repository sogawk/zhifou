package com.bean;

import javax.lang.model.element.NestingKind;
import java.util.HashMap;
import java.util.Map;

public class ViewObject {

    private Map<String, Object> map = new HashMap<>();

    public Object get(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }
}
