package com.shen.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName ElasticSearchClientConfig.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年04月05日 18:29:00
 */
@Configuration //xml  -bean
public class ElasticSearchClientConfig {

    // elk
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));
        return client;
    }
}
