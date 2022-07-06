package com.pokeya.zhulong.api.enums.push;

/**
 * 推送url枚举
 *
 * @author mac
 */
public enum PushUrlEnums {
    /**
     * 企业微信敏捷试运行团队
     */
    AGILE_TEAM("agileTeam");
    /**
     * 编码
     */
    private String code;

    PushUrlEnums(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
