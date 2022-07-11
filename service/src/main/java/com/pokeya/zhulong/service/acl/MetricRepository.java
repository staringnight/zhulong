package com.pokeya.zhulong.service.acl;


import java.util.HashMap;
import java.util.List;

/**
 * 埋点实践
 *
 * @author mac
 */
public interface MetricRepository {

    void send(List<HashMap> list);

    void pushMessage(List<HashMap> list);
}
