package com.pokeya.zhulong.web;

import com.alibaba.fastjson.JSON;
import com.pokeya.zhulong.api.dto.push.PushDto;
import com.pokeya.zhulong.api.enums.push.PushContentEnums;
import com.pokeya.zhulong.api.enums.push.PushUrlEnums;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest(classes = ZhuLongApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebApplicationTests {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void contextLoads() {
        PushDto pushDto = new PushDto(PushUrlEnums.AGILE_TEAM, PushContentEnums.SECURITY_SCORE, new String[]{"mq test"}, PushDto.mentionedMobile);
        SendResult sendResult = rocketMQTemplate.syncSend("INFRASTRUCTURE_TOOL_TOPIC:TOOL_PUSH_TAG", pushDto);
        System.out.println(JSON.toJSONString(sendResult));
    }

}
