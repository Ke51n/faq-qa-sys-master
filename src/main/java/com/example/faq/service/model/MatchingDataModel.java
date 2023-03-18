package com.example.faq.service.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description 匹配数据的领域模型
 */
@Data
public class MatchingDataModel {
    //URL
    private String url;
    //docId
    private String id;
    //问答知识库的qa_id
    private Integer qaId;
    //标准问
    private String standardQuestion;
    //标准答
    private String standardAnswer;
    //相似问
    private String similarQuestion;
    //相关度得分
    private Float relevanceScore;
    //相似度得分
    private Float similarityScore;
    //置信度
    private Float confidence;

    // 先加一个清爽版的的tostring函数
    public String toSimpleString() {
        return "MatchingDataModel{" +
                "standardQuestion='" + standardQuestion + '\'' +
                ", similarQuestion='" + similarQuestion + '\'' +
                ", relevanceScore=" + relevanceScore +
                ", similarityScore=" + similarityScore +
                ", confidence=" + confidence +
                ", url='" + url + '\'' +
                '}';
    }
}
