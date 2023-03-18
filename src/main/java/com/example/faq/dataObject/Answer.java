package com.example.faq.dataObject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description
 */
@Data
public class Answer {
    //答案内容
    private String content;
    //相似问对应的标准问
    private String stdQ;
    //置信度
    private Float confidence;
    //多轮问答选项
    private List<String> options;

    public Answer() {
        this.options = new ArrayList<>();
    }
}
