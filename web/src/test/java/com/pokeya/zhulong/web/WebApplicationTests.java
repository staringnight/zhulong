package com.pokeya.zhulong.web;

import com.alibaba.fastjson.JSON;
import com.pokeya.zhulong.api.constant.MqConstant;
import com.pokeya.zhulong.api.dto.push.PushDTO;
import com.pokeya.zhulong.api.enums.push.PushContentEnums;
import com.pokeya.zhulong.api.enums.push.PushUrlEnums;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.MessageFormat;


@SpringBootTest(classes = ZhuLongApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebApplicationTests {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void contextLoads() throws InterruptedException {
        String d = MessageFormat.format(MqConstant.TOPIC_TAG_FORMAT, MqConstant.INFRASTRUCTURE_TOOL_TOPIC, MqConstant.InfrastructureToolTag.TOOL_PUSH_TAG);
        PushDTO pushDto = new PushDTO(PushUrlEnums.AGILE_TEAM, PushContentEnums.SECURITY_SCORE, new String[]{"test"}, PushDTO.mentionedMobile);
        SendResult sendResult = rocketMQTemplate.syncSend(d, pushDto);
        System.out.println(JSON.toJSONString(sendResult));
        sendResult = rocketMQTemplate.syncSend(d, pushDto);
        System.out.println(JSON.toJSONString(sendResult));
        sendResult = rocketMQTemplate.syncSend(d, pushDto);
        System.out.println(JSON.toJSONString(sendResult));
        Thread.sleep(3000);
    }

}
