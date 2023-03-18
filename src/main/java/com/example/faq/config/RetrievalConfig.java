package com.example.faq.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description 检索参数项配置
 */
@Configuration
@ConfigurationProperties(prefix = "retrieval")
@Data
public class RetrievalConfig {

    private Index index;
    private Search search;
    private String elasticsearchAPIPath;

    public static class Index {
        @Getter
        private String faqPair;
        public void setFaqPair(String faqPair){
            this.faqPair=faqPair;
        }
    }


    @Data
    public static class Search {
        private Integer size;
    }
}
