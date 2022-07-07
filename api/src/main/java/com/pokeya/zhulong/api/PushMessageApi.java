package com.pokeya.zhulong.api;

import com.pokeya.zhulong.api.dto.push.PushDTO;
import com.pokeya.zhulong.api.dto.push.PushResponseDTO;

public interface PushMessageApi {
    PushResponseDTO pushMessage(PushDTO message);
}
