package com.pokeya.zhulong.infrastructure.mq.consumer;

import cn.hutool.core.util.HashUtil;
import com.pokeya.yao.component.infrastructure.RedisUtil;
import com.pokeya.yao.constant.NumberConstant;
import com.pokeya.yao.exception.RedisException;
import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.api.constant.MqConstant;
import com.pokeya.zhulong.api.dto.push.PushDTO;
import com.pokeya.zhulong.api.dto.push.PushResponseDTO;
import com.pokeya.zhulong.infrastructure.constant.RedisKeys;
import com.pokeya.zhulong.infrastructure.mq.MqGroupConstant;
import com.pokeya.zhulong.service.biz.PushMessageBiz;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Duration;

/**
 * @author mac
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqGroupConstant.PUSH_CONSUMER_GROUP, topic = MqConstant.INFRASTRUCTURE_TOOL_TOPIC, selectorExpression = MqConstant.InfrastructureToolTag.TOOL_PUSH_TAG, maxReconsumeTimes = 3)
public class PushConsumer implements RocketMQListener<PushDTO> {

    private PushMessageBiz pushMessageBiz;

    private RedisUtil redisUtil;

    @Autowired
    public PushConsumer(PushMessageBiz pushMessageBiz, RedisUtil redisUtil) {
        this.pushMessageBiz = pushMessageBiz;
        this.redisUtil = redisUtil;
    }

    @Override
    public void onMessage(PushDTO message) {
        String jsonString = JSON.toJSONString(message);
        log.info("PushConsumer onMessage:{}", jsonString);
        Long timeMillis = System.currentTimeMillis();
        Long hash = HashUtil.mixHash(jsonString);
        String mqKey = MessageFormat.format(MqConstant.TOPIC_TAG_FORMAT, MqConstant.INFRASTRUCTURE_TOOL_TOPIC, MqConstant.InfrastructureToolTag.TOOL_PUSH_TAG);
        String key = MessageFormat.format(RedisKeys.MQ_MESSAGE_KEY, mqKey, hash.toString());
        try {
            if (redisUtil.getRedisTemplate().opsForValue().setIfAbsent(key, timeMillis.toString(), Duration.ofMinutes(NumberConstant.num_1))) {
                PushResponseDTO pushResponseDto = pushMessageBiz.pushMessage(message);
                log.info("PushConsumer onMessage result:{}", JSON.toJSONString(pushResponseDto));
            }
        } finally {
            try {
                redisUtil.remove(key);
            } catch (RedisException ex) {
                log.error("PushConsumer", ex);
            }
        }
        log.warn("PushConsumer onMessage repeat:{}", jsonString);


    }

}
