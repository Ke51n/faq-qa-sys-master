package com.example.faq.mapper;

import com.example.faq.entity.FaqPair;
import com.example.faq.entity.TaxText;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * package:  com.example.faq.mapper
 * project_name:  faq-qa-sys-master
 * 2023/3/5  18:30
 * description:
 *
 * @author wk
 * @version 1.0
 */
@Mapper
public interface TaxTextMapper {
    @Select("select idx,url,title,abstract, text from tax_text")
    @Results({
            @Result(property = "idx", column = "idx", id = true),
            @Result(property = "url", column = "url"),
            @Result(property = "title", column = "title"),
            //abstract1 防止和关键字冲突
            @Result(property = "abstract1", column = "abstract"),
            @Result(property = "text", column = "text")
    })
    List<TaxText> selectAll();
}
