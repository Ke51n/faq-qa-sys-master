package com.example.faq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description ES连接参数
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticsearchConfig {
    private String host;
    private Integer port;
}
