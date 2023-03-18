package com.example.faq.service;

import com.example.faq.dataObject.DialogueStatus;

import java.io.IOException;

/**
 * @Author: wang_k
 * @CreateDate: 2023/02/27
 * @Description
 */
public interface DialogueService {
    /**
     * 回答用户问题
     *
     * @param dialogueStatus 初始的对话状态模型
     * @return 完成的对话状态模型
     */
    DialogueStatus answer(DialogueStatus dialogueStatus) throws IOException;
}
