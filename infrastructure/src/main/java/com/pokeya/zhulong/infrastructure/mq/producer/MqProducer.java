package com.pokeya.zhulong.infrastructure.mq.producer;

import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.api.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author mac
 */
@Slf4j
@Component
public class MqProducer {
    private RocketMQTemplate rocketMQTemplate;

    public MqProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void send(Object o, String tag) {
        String key = MessageFormat.format(MqConstant.TOPIC_TAG_FORMAT, MqConstant.INFRASTRUCTURE_TOOL_TOPIC, tag);
        SendResult sendResult = rocketMQTemplate.syncSend(key, o);
        log.info("MetricProducer:{}:{}", JSON.toJSONString(o), JSON.toJSONString(sendResult));
    }
}
