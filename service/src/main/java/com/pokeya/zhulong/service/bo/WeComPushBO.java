package com.pokeya.zhulong.service.bo;

import lombok.Data;

/**
 * 企业微信model
 *
 * @author mac
 * {
 * "msgtype": "text",
 * "text": {
 * "content": "Hi，我是机器人测试\n由汪来兵于05月09日添加到群"
 * }
 * }
 */
@Data
public class WeComPushBO {
    /**
     * 类型  text  markdown image news
     */
    private String msgtype;
    /**
     * 内容
     */
    private WeComPushContentBO text;
    private WeComPushContentBO markdown;

}


