package com.example.tmp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.tmp.IKAnalyzerSupport.iKSegmenterToList;

/**
 * package:  com.example.tmp
 * project_name:  faq-qa-sys-master
 * 2023/3/6  16:18
 * description:根据query，从text中选择最相关的paras
 *
 * @author wk
 * @version 1.0
 */
public class SelectParagraphs {

    public static String selectMostRelevantParas(String query, String text) {
//        String[] paras = text.split("\n");
        String[] paras = text.split(" ");
        //query分词
        HashSet<String> queryWords = iKSegmenterToList(query);
        System.out.println("query 分词为：");
        System.out.println(queryWords.size());
        for (String queryWord : queryWords) {
            System.out.println(queryWord);
        }
        List<HashSet<String>> parasWords = new ArrayList<>();
        //段落分词
        for (int i = 0; i < paras.length; i++) {
            parasWords.add(iKSegmenterToList(paras[i]));
        }
//        System.out.println("段落数 = " + paras.length);
//        for (HashSet<String> parasWord : parasWords) {
//            for (String s : parasWord) {
//                System.out.println(s);
//            }
//            System.out.println("===========");
//        }

        //段落出现次数
        int maxIdx = 0;
        int[] occurs = new int[paras.length];
        for (String word : queryWords) {
            for (int i = 0; i < parasWords.size(); i++) {
                if (parasWords.get(i).contains(word)) {
                    occurs[i] += 1;
                    //更新段落索引
                    maxIdx = occurs[maxIdx] < occurs[i] ? i : maxIdx;
                }
            }
        }
        //返回最相关段落
        return paras[maxIdx];
    }
}
