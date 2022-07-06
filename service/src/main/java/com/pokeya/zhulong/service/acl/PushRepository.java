package com.pokeya.zhulong.service.acl;


import com.pokeya.zhulong.api.dto.push.PushDto;
import com.pokeya.zhulong.api.dto.push.PushResponseDto;
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
    PushResponseDto push(PushDto pushDto);
}
