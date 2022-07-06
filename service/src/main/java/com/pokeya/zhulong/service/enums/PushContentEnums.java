package com.pokeya.zhulong.service.enums;

/**
 * 推送模板
 *
 * @author mac
 */
public enum PushContentEnums {
    /**
     * 内容模板
     */
    SECURITY_SCORE("securityScore", "安全值问题：{0}"),
    SECURITY_SCORE_TASK("securityScore", "安全值定时统计任务问题：{0}"),
    DEVICE_OFFLINE("device_offline", "设备在线离线消费：{0}"),
    ;
    /**
     * 编码
     */
    private String code;
    /**
     * 内容
     */
    private String content;

    PushContentEnums(String code, String content) {
        this.code = code;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }
}
