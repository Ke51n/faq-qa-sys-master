package com.example.faq.controller;

import com.example.faq.config.DialogueConfig;
import com.example.faq.config.RetrievalConfig;
import com.example.faq.response.CommonReturnType;
import com.example.faq.service.ManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indexlifecycle.RemoveIndexLifecyclePolicyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description
 */
@Api(tags = "管理")
@RestController
@RequestMapping("/management")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")   //处理跨域请求
@Slf4j
public class ManagementController {
    private final static String ContentType = "application/x-www-form-urlencoded";

    @Autowired
    private ManagementService managementService;

    @Autowired
    private RetrievalConfig retrievalConfig;
    @Autowired
    public RestHighLevelClient restHighLevelClient;

    /**
     * 全量同步，将mysql中的faq_pair表全部同步到redis中
     */
    @ApiOperation("全量同步")
    @RequestMapping(value = "/total_synchronize", method = RequestMethod.GET)
    public CommonReturnType totalSynchronize() throws IOException {

        String tableIndexName="faq_pair";
//        String tableIndexName="tax_text";
        System.out.println("11");
        System.out.println(retrievalConfig.getIndex().getFaqPair());

        //检查表/索引名是否有效 todo 插入政策文档，临时注释
//        if (!retrievalConfig.getIndex().getFaqPair().equals(tableIndexName)) {
//            log.error("{}不在可以同步的表/索引中", tableIndexName);
//            return CommonReturnType.failed(String.format("%s不在可以同步的表/索引中", tableIndexName));
//        }

        //统计耗时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int account = managementService.totalSynchronize(tableIndexName);
        stopWatch.stop();

        if (account == 0) {
            return CommonReturnType.failed(String.format("mysql表%s中0条数据被同步", tableIndexName));
        }

        return CommonReturnType.success(String.format("成功同步mysql表%s中%d条数据到es索引%s，耗时%dms", tableIndexName, account, tableIndexName, stopWatch.getTotalTimeMillis()));
    }


    @ApiOperation("更新多轮问答树")
    @RequestMapping(value = "/update_multi_turn_qa_tree", method = RequestMethod.GET)
    public CommonReturnType updateMultiRoundQATree() {
        int account = managementService.updateMultiTree();
        if (account == 0) {
            return CommonReturnType.failed(null);
        }
        return CommonReturnType.success(String.format("成功更新%d个多轮问答树到redis", account));
    }

    // 测试索引删除
    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("index_name");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());// 是否删除成功
        restHighLevelClient.close();
    }
}
