package com.seek.friend.configobject.RedisData;

import lombok.Data;

@Data
public class RedisStreamData {
    private String name;
    private String messageKey;
    private RedisConsumerData consumer;
}
