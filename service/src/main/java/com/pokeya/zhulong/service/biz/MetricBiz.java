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
        try {
            metricRepository.send(list);
        } catch (Exception e) {
            log.error("MetricBiz", e);
        }
    }

    public void pushMessage(List<HashMap> list) {
        metricRepository.pushMessage(list);
    }
}
