package com.pokeya.zhulong.service.biz;

import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.api.dto.push.PushDTO;
import com.pokeya.zhulong.api.dto.push.PushResponseDTO;
import com.pokeya.zhulong.service.acl.PushRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机器人通知
 * 每个机器人发送的消息不能超过20条/分钟。
 *
 * @author mac
 */
@Slf4j
@Component
public class PushMessageBiz {

    private Map<String, PushRepository> pushRepositoryMap;

    public PushMessageBiz(List<PushRepository> pushRepositoryList) {
        HashMap<String, PushRepository> repositoryMap = new HashMap<>();
        for (PushRepository pushRepository : pushRepositoryList) {
            repositoryMap.put(pushRepository.getPushEnums().getCode(), pushRepository);
        }
        this.pushRepositoryMap = repositoryMap;
    }


    public PushResponseDTO pushMessage(PushDTO message) {
        PushRepository pushRepository = pushRepositoryMap.get(message.pushUrlEnums().getCode());
        if (pushRepository != null) {
            return pushRepository.push(message);
        } else {
            log.error("PushMessageBiz no handler:{}", JSON.toJSONString(message));
        }
        return null;
    }
}
