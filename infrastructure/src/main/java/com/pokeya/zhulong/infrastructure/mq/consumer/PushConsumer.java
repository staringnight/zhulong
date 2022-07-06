package com.pokeya.zhulong.infrastructure.mq.consumer;

import com.pokeya.zhulong.api.constant.MqConstant;
import com.pokeya.zhulong.api.dto.push.PushDto;
import com.pokeya.zhulong.infrastructure.MqGroupConstant;
import com.pokeya.zhulong.service.biz.PushMessageBiz;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mac
 */
@Component
@RocketMQMessageListener(consumerGroup = MqGroupConstant.PUSH_CONSUMER_GROUP, topic = MqConstant.INFRASTRUCTURE_TOOL_TOPIC, selectorExpression = MqConstant.InfrastructureToolTag.TOOL_PUSH_TAG)
public class PushConsumer implements RocketMQListener<PushDto> {

    private PushMessageBiz pushMessageBiz;

    @Autowired
    public PushConsumer(PushMessageBiz pushMessageBiz) {
        this.pushMessageBiz = pushMessageBiz;
    }


    @Override
    public void onMessage(PushDto message) {
        pushMessageBiz.pushMessage(message);
    }

}
