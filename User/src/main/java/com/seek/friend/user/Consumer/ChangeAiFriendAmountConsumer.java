package com.seek.friend.user.Consumer;

import com.seek.friend.serviceobject.Common.ChangeAmountDTO;
import com.seek.friend.user.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "userTopicChangeAiFriendAmountConsumer",
        topic = "userTopic",
        selectorExpression = "changeAiFriendAmount")
public class ChangeAiFriendAmountConsumer implements RocketMQListener<ChangeAmountDTO> {
    private final UserMapper userMapper;
    @Autowired
    public ChangeAiFriendAmountConsumer(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void onMessage(ChangeAmountDTO changeAmountDTO) {
        userMapper.changeAiFriendAmount(changeAmountDTO.getId(),changeAmountDTO.getChangeNumber());
    }




}
