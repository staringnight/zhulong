package com.pokeya.zhulong.service.biz;

import com.pokeya.zhulong.service.acl.MetricRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author mac
 */
@Service
@Slf4j
public class MetricBiz {

    private MetricRepository metricRepository;

    public MetricBiz(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public void upload(List<HashMap> list) {
        for (HashMap hashMap : list) {
            try {
                metricRepository.send(hashMap);
            } catch (Exception e) {
                log.error("MetricBiz", e);
            }
        }
    }

    public void pushMessage(HashMap message) {
        metricRepository.pushMessage(message);
    }
}
