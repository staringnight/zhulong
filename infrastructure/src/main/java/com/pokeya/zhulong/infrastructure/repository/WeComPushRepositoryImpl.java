package com.pokeya.zhulong.infrastructure.repository;


import com.pokeya.yao.utils.HttpUtil;
import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.api.dto.push.PushDTO;
import com.pokeya.zhulong.api.dto.push.PushResponseDTO;
import com.pokeya.zhulong.api.enums.push.PushContentEnums;
import com.pokeya.zhulong.api.enums.push.PushUrlEnums;
import com.pokeya.zhulong.infrastructure.beans.WeComPushBean;
import com.pokeya.zhulong.infrastructure.beans.WeComPushContentBean;
import com.pokeya.zhulong.service.acl.PushRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;

@Slf4j
@Repository
public class WeComPushRepositoryImpl implements PushRepository {
    private Environment environment;

    @Autowired
    public WeComPushRepositoryImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public PushUrlEnums getPushEnums() {
        return PushUrlEnums.AGILE_TEAM;
    }

    @Override
    public PushResponseDTO push(PushDTO pushDto) {
        try {
            PushContentEnums weComContentEnum = pushDto.pushContentEnums();
            String[] contents = pushDto.contents();
            String mentionedMobileList = pushDto.mentionedMobileList();
            WeComPushBean weComPushBean = new WeComPushBean();
            weComPushBean.setMsgtype("text");
            WeComPushContentBean weComPushContentBean = new WeComPushContentBean();
            weComPushBean.setText(weComPushContentBean);
            weComPushContentBean.setContent(MessageFormat.format(weComContentEnum.getContent(), contents));
            weComPushContentBean.setMentioned_mobile_list(mentionedMobileList);
            String url = environment.getRequiredProperty(MessageFormat.format("weCom.webhookUrl.{0}", getPushEnums().getCode()));
            log.info("企业机器人通知:{},{}", JSON.toJSONString(weComPushBean), url);
            String result = HttpUtil.post(url, weComPushBean);
            log.info("企业机器人结果：{}", result);
            //{"errcode":0,"errmsg":"ok"}
            PushResponseDTO pushResponseDto = JSON.parseObject(result, PushResponseDTO.class);
            if (pushResponseDto.errcode() != 0) {
                log.warn("企业机器人通知失败！");
            }
            return pushResponseDto;
        } catch (Exception e) {
            log.error("企业机器人异常：{}", e.getMessage(), e);
        }
        return null;
    }
}
