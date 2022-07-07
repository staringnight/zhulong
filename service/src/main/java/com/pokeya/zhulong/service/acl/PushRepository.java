package com.pokeya.zhulong.service.acl;


import com.pokeya.zhulong.api.dto.push.PushDTO;
import com.pokeya.zhulong.api.dto.push.PushResponseDTO;
import com.pokeya.zhulong.api.enums.push.PushUrlEnums;

/**
 * 推送push
 *
 * @author mac
 */
public interface PushRepository {
    /**
     * 实现
     */
    PushUrlEnums getPushEnums();

    /**
     * 推送push
     */
    PushResponseDTO push(PushDTO pushDto);
}
