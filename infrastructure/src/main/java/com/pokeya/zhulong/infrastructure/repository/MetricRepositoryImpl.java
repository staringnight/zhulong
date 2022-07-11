package com.pokeya.zhulong.infrastructure.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import com.pokeya.yao.utils.JSON;
import com.pokeya.zhulong.api.constant.MqConstant;
import com.pokeya.zhulong.infrastructure.mq.producer.MqProducer;
import com.pokeya.zhulong.service.acl.MetricRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author mac
 */
@Service
@Slf4j
public class MetricRepositoryImpl implements MetricRepository {
    public static String METRIC_INDEX = "metric_index";
    private MqProducer mqProducer;
    private ElasticsearchClient elasticsearchClient;

    public MetricRepositoryImpl(MqProducer mqProducer, ElasticsearchClient elasticsearchClient) {
        this.mqProducer = mqProducer;
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public void send(List<HashMap> list) {
        mqProducer.send(list, MqConstant.InfrastructureToolTag.TOOL_METRIC_TAG);
    }

    @Override
    public void pushMessage(List<HashMap> list) {
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
            // 构建一个批量数据集合
            List<BulkOperation> bulkOperations = new ArrayList<>();
            for (HashMap hashMap : list) {
                bulkOperations.add(new BulkOperation.Builder().create(
                        d -> d.document(hashMap).index(METRIC_INDEX)).build());
            }
            // 调用bulk方法执行批量插入操作
            BulkResponse bulkResponse = elasticsearchClient.bulk(e -> e.index(METRIC_INDEX).operations(bulkOperations));
            log.info("METRIC_INDEX save:{}", JSON.toJSONString(bulkResponse.items()));
        } catch (IOException e) {
            log.error("MetricRepositoryImpl:", e);
        }
    }
}
