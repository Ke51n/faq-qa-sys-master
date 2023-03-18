package com.example.faq.service.retrieval.model;

import lombok.Data;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description
 */
@Data
public class RetrievalDataModel {
    //文档的url
    private String url;

    //docId
    private String id;
    //问答对的qa_id
    private Integer qaId;
    //标准问
    private String standardQuestion;
    //标准答
    private String standardAnswer;
    //相关度得分
    private Float relevanceScore;
}
