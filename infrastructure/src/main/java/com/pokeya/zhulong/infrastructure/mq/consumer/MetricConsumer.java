package com.pokeya.zhulong.infrastructure.mq.consumer;

import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.api.constant.MqConstant;
import com.pokeya.zhulong.infrastructure.mq.MqGroupConstant;
import com.pokeya.zhulong.service.biz.MetricBiz;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author mac
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqGroupConstant.PUSH_CONSUMER_GROUP, topic = MqConstant.INFRASTRUCTURE_TOOL_TOPIC, selectorExpression = MqConstant.InfrastructureToolTag.TOOL_METRIC_TAG, maxReconsumeTimes = 3)
public class MetricConsumer implements RocketMQListener<List<HashMap>> {

    private final MetricBiz metricBiz;


    @Autowired
    public MetricConsumer(MetricBiz metricBiz) {
        this.metricBiz = metricBiz;
    }

    @Override
    public void onMessage(List<HashMap> list) {
        String jsonString = JSON.toJSONString(list);
        log.info("MetricConsumer onMessage:{}", jsonString);
        metricBiz.pushMessage(list);
    }

}
