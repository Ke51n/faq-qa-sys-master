package com.example.faq.service.retrieval;

import com.example.faq.service.retrieval.model.RetrievalDataModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description 检索服务
 */
public interface RetrievalService {
    /**
     * 搜索一个问题，返回最相关的标准问等信息
     *
     * @param question 待搜索的问题
     * @return RetrievalDataModel
     */
    List<RetrievalDataModel> searchSimilarQuestions(String question) throws IOException;


    /**
     * 索引docs（插入docs到索引中）
     *
     * @param indexName   索引名
     * @param jsonMapList docs
     * @return 成功操作的数量
     */
    Integer insertDocs(String indexName, List<Map<String, Object>> jsonMapList) throws IOException;
}
