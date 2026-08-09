package com.seek.friend.configobject.RedisData;

import lombok.Data;

@Data
public class RedisKeyData {
    private String name;
    private Long secondDuration;
    public String getRedisKey(Object key){
        if (key==null||key.equals("")) return name;
        return name+key;
    }
}
