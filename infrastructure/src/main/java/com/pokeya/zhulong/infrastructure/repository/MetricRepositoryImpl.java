package com.pokeya.zhulong.infrastructure.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.api.constant.MqConstant;
import com.pokeya.zhulong.infrastructure.mq.producer.MqProducer;
import com.pokeya.zhulong.service.acl.MetricRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author mac
 */
@Service
@Slf4j
public class MetricRepositoryImpl implements MetricRepository {
    public static String METRIC_INDEX = "METRIC_INDEX";
    private MqProducer mqProducer;
    private ElasticsearchClient elasticsearchClient;

    public MetricRepositoryImpl(MqProducer mqProducer, ElasticsearchClient elasticsearchClient) {
        this.mqProducer = mqProducer;
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public void send(HashMap hashMap) {
        mqProducer.send(hashMap, MqConstant.InfrastructureToolTag.TOOL_METRIC_TAG);
    }

    @Override
    public void pushMessage(HashMap message) {
        try {
            // 查询索引
            if (!elasticsearchClient.indices().exists(e -> e.index(METRIC_INDEX)).value()) {
                // 创建索引
                CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(c -> c.index(METRIC_INDEX));
                // 响应状态
                Boolean acknowledged = createIndexResponse.acknowledged();
                log.info("METRIC_INDEX create:{}", acknowledged);
            }
            // 向索引中添加数据
            CreateResponse createResponse = elasticsearchClient.create(e -> e.index(METRIC_INDEX).document(message));
            log.info("METRIC_INDEX save:{}", JSON.toJSONString(createResponse.result()));
        } catch (IOException e) {
            log.error("MetricRepositoryImpl:", e);
        }
    }
}
