package com.pokeya.zhulong.web.controller;

import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.service.biz.MetricBiz;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 * @author mac
 */
@RestController("metric")
public class MetricController {

    private MetricBiz metricBiz;

    public MetricController(MetricBiz metricBiz) {
        this.metricBiz = metricBiz;
    }

    @ResponseBody
    @GetMapping("upload")
    public void upload(@Param("d") String metricStr) {
        String metric = new String(Base64.getDecoder().decode(metricStr), StandardCharsets.UTF_8);
        List<HashMap> list = JSON.toList(metric, HashMap.class);
        metricBiz.upload(list);
    }

}
