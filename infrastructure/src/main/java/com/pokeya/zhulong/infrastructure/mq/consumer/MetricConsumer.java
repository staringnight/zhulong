package com.pokeya.zhulong.infrastructure.mq.consumer;

import cn.hutool.core.util.HashUtil;
import com.pokeya.yao.component.infrastructure.RedisUtil;
import com.pokeya.yao.constant.NumberConstant;
import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.api.constant.MqConstant;
import com.pokeya.zhulong.infrastructure.constant.RedisKeys;
import com.pokeya.zhulong.infrastructure.mq.MqGroupConstant;
import com.pokeya.zhulong.service.biz.MetricBiz;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.HashMap;

/**
 * @author mac
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqGroupConstant.PUSH_CONSUMER_GROUP, topic = MqConstant.INFRASTRUCTURE_TOOL_TOPIC,
        selectorExpression = MqConstant.InfrastructureToolTag.TOOL_METRIC_TAG, maxReconsumeTimes = 3)
public class MetricConsumer implements RocketMQListener<HashMap> {

    private MetricBiz metricBiz;

    private RedisUtil redisUtil;

    @Autowired
    public MetricConsumer(MetricBiz metricBiz, RedisUtil redisUtil) {
        this.metricBiz = metricBiz;
        this.redisUtil = redisUtil;
    }

    @Override
    public void onMessage(HashMap message) {
        String jsonString = JSON.toJSONString(message);
        log.info("MetricConsumer onMessage:{}", jsonString);
        Long timeMillis = System.currentTimeMillis();
        Long hash = HashUtil.mixHash(jsonString);
        String key = MessageFormat.format(RedisKeys.PUSH_MESSAGE_KEY, hash.toString());
        if (redisUtil.getRedisTemplate().opsForValue().setIfAbsent(key, timeMillis.toString(), Duration.ofMinutes(NumberConstant.num_1))) {
            metricBiz.pushMessage(message);
        }
        log.warn("MetricConsumer onMessage repeat:{}", jsonString);
    }

}
