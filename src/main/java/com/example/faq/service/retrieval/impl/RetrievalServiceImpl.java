package com.example.faq.service.retrieval.impl;

import com.example.faq.config.ElasticsearchConfig;
import com.example.faq.config.RetrievalConfig;
import com.example.faq.service.retrieval.RetrievalService;
import com.example.faq.service.retrieval.model.RetrievalDataModel;
import com.example.faq.util.RestClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description
 */
@Service
@Slf4j
public class RetrievalServiceImpl implements RetrievalService {


    @Autowired
    private ElasticsearchConfig ESConfig;

    @Autowired
    private RetrievalConfig RetrievalConfig;

    @Autowired
    private RestClientUtil restClientUtil;

    /**
     * 去es中检索相关问题
     * @param question 待搜索的问题
     * @return
     * @throws IOException
     */
    @Override
    public List<RetrievalDataModel> searchSimilarQuestions(String question) throws IOException {

        List<RetrievalDataModel> retrievalDataModelList = new ArrayList<>(RetrievalConfig.getSearch().getSize());
        RestHighLevelClient client;
        //初始化rest client
        try {
            client = restClientUtil.getClient(ESConfig.getHost(), ESConfig.getPort());
        } catch (ElasticsearchException e) {
            e.printStackTrace();
            return null;
        }
        //创建searchRequest todo    qa_knowledge_base 验证相似度模型
//        SearchRequest request = restClientUtil.getSearchRequest(RetrievalConfig.getIndex().getFaqPair(), "standard_question", question, RetrievalConfig.getSearch().getSize());
        SearchRequest request = restClientUtil.getSearchRequest("faq_pair", "standard_question", question, RetrievalConfig.getSearch().getSize());
        // 添加文档搜索，根据text正文建索引 todo
        SearchRequest request1 = restClientUtil.getSearchRequest("tax_text", "text", question, RetrievalConfig.getSearch().getSize());
        //以同步方式搜索问题，等待搜索结果
        SearchResponse response;
        SearchResponse response1;
        try {
            //这里有异常 TODO
            response = client.search(request, RequestOptions.DEFAULT);
            response1 = client.search(request1, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            e.printStackTrace();
            return null;
        }
        //状态
        RestStatus status = response.status();
        //耗时
        TimeValue took = response.getTook();
        SearchHits hits = response.getHits();
        SearchHits hits1 = response1.getHits();
        long totalHits = hits.getTotalHits() + hits1.getTotalHits();
        if (totalHits == 0) {
            log.info("未识别的问题\"{}\"", question);
            return retrievalDataModelList;
        }
        //遍历docs中的数据 添加从政策文档获取数据，一共60条
        SearchHit[] searchHits = hits.getHits();
        SearchHit[] searchHits1 = hits1.getHits();

        for (SearchHit hit : searchHits) {
            //docId
            String id = hit.getId();
            //相关度得分
            float score = hit.getScore();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            RetrievalDataModel retrievalDataModel = new RetrievalDataModel();
            retrievalDataModel.setId(id);
            // TODO 相关性得分，归一化 0~1
            retrievalDataModel.setRelevanceScore(score);
            retrievalDataModel.setStandardQuestion((String) sourceAsMap.get("standard_question"));
            retrievalDataModel.setStandardAnswer((String) sourceAsMap.get("standard_answer"));
            Integer qaId = (Integer) sourceAsMap.get("qa_id");
            retrievalDataModel.setQaId(qaId);


            retrievalDataModelList.add(retrievalDataModel);
        }
        // 这一部分数据来自 tax_text ，政策文档， 后续可根据文档做回答 todo
        for (SearchHit hit : searchHits1) {
            //docId
            String id = hit.getId();
            //相关度得分
            float score = hit.getScore();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            RetrievalDataModel retrievalDataModel = new RetrievalDataModel();
            retrievalDataModel.setId(id);
            retrievalDataModel.setRelevanceScore(score);
            retrievalDataModel.setStandardQuestion((String) sourceAsMap.get("title"));
            retrievalDataModel.setStandardAnswer((String) sourceAsMap.get("text"));
            Integer qaId = (Integer) sourceAsMap.get("idx");
            retrievalDataModel.setQaId(qaId);
            retrievalDataModel.setUrl((String) sourceAsMap.get("url"));
            retrievalDataModelList.add(retrievalDataModel);
        }
        client.close();
        return retrievalDataModelList;
    }

    @Override
    public Integer insertDocs(String indexName, List<Map<String, Object>> jsonMapList) throws IOException {
        RestHighLevelClient client = restClientUtil.getClient(ESConfig.getHost(), ESConfig.getPort());
        int account = 0;
        for (Map<String, Object> jsonMap : jsonMapList) {
            IndexRequest request = restClientUtil.getIndexRequest(indexName, jsonMap);
            client.index(request, RequestOptions.DEFAULT);
            account++;
        }
        client.close();
        log.info("成功插入{}个数据到索引{}中", account, indexName);

        return account;
    }
}
