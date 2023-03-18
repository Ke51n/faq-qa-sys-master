package com.example.faq.dataObject;

import lombok.Data;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description 其他相似问题推荐
 */
@Data
public class RecommendQuestion {
    //标准问
    private String stdQ;
    //标准答
    private String stdA;
    //置信度
    private Float confidence;
}
