package com.example.tmp;

/**
 * package:  com.example.tmp
 * project_name:  faq-qa-sys-master
 * 2023/3/6  16:07
 * description:
 *
 * @author wk
 * @version 1.0
 */

//import com.github.pagehelper.util.StringUtil;

import io.netty.util.internal.StringUtil;
//import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @ClassName: IKAnalyzerSupport
 * @Auther: wuhao
 * @Date: 2021/2/24 16:45
 * @Description:分词工具类
 */
@Component
public class IKAnalyzerSupport {
    public static void main(String[] args) {
        String s = "这里是一篇包含若干段落的文章，其中有很多的内容，但我们只对其中一些关键内容进行摘要";
        HashSet<String> strings = iKSegmenterToList(s);
        for (String string : strings) {
            System.out.println(string);
        }
    }


    /**
     * IK分词，加载扩展词和停用词
     *
     * @param string
     * @return
     */
    static public HashSet<String> iKSegmenterToList(String string) {
        HashSet<String> words = new HashSet<>();
        try {
            StringReader sr = new StringReader(string);
            IKSegmenter ik = new IKSegmenter(sr, true);
            Lexeme lex;
            while ((lex = ik.next()) != null) {
                String lexemeText = lex.getLexemeText();
                //只对长度大于2的词进行统计
                if (lexemeText.length() >= 2) {
                    words.add(lexemeText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }
}
