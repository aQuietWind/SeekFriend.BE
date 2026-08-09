package com.seek.friend.configobject.RedisData;

import lombok.Data;

@Data
public class RedisKeyData {
    private String name;
    private Long duration;
    public String getRedisKey(Object key){
        if (key!=null) return name + key;
        return name;
    }
}
