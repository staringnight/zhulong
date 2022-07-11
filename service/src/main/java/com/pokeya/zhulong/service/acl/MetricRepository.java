package com.pokeya.zhulong.service.acl;


import java.util.HashMap;

/**
 * 埋点实践
 *
 * @author mac
 */
public interface MetricRepository {

    void send(HashMap hashMap);

    void pushMessage(HashMap message);
}
