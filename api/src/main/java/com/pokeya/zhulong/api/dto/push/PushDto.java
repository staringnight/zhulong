package com.pokeya.zhulong.api.dto.push;

import com.pokeya.zhulong.api.enums.push.PushContentEnums;
import com.pokeya.zhulong.api.enums.push.PushUrlEnums;


/**
 * @param pushUrlEnums        {@link PushUrlEnums}
 * @param pushContentEnums    {@link PushContentEnums}
 * @param contents            内容
 * @param mentionedMobileList 通知人手机号 "@all"全部
 */
public record PushDto(PushUrlEnums pushUrlEnums, PushContentEnums pushContentEnums, String[] contents,
                      String mentionedMobileList) implements java.io.Serializable {

    public static final String mentionedMobile = "@all";
}
