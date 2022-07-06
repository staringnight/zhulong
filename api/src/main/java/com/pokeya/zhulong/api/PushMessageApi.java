package com.pokeya.zhulong.api;

import com.pokeya.zhulong.api.dto.push.PushDto;
import com.pokeya.zhulong.api.dto.push.PushResponseDto;

public interface PushMessageApi {
    PushResponseDto pushMessage(PushDto message);
}
