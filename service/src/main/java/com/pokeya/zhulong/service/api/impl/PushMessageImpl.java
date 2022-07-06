package com.pokeya.zhulong.service.api.impl;

import com.pokeya.zhulong.api.PushMessageApi;
import com.pokeya.zhulong.api.dto.push.PushDto;
import com.pokeya.zhulong.api.dto.push.PushResponseDto;
import com.pokeya.zhulong.service.biz.PushMessageBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushMessageImpl implements PushMessageApi {
    private PushMessageBiz pushMessageBiz;

    @Autowired
    public PushMessageImpl(PushMessageBiz pushMessageBiz) {
        this.pushMessageBiz = pushMessageBiz;
    }

    @Override
    public PushResponseDto pushMessage(PushDto message) {
        return pushMessageBiz.pushMessage(message);
    }
}
