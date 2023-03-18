package com.example.faq.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * package:  com.example.faq.entity
 * project_name:  faq-qa-sys-master
 * 2023/3/5  18:32
 * description:
 *
 * @author wk
 * @version 1.0
 */
//11333 篇
@ApiModel("税务文档：中央法规")
@Data
public class TaxText {
    @JsonProperty(value = "idx")
    private Integer idx;
    //文章链接url
    @JsonProperty(value = "url")
    private String url;

    //标题
    @ApiModelProperty("标题")
    @JsonProperty(value = "title")
    private String title;

    //摘要
    @ApiModelProperty("摘要")
    @JsonProperty(value = "abstract")
    private String abstract1;

    //正文
    @ApiModelProperty("正文")
    @JsonProperty(value = "text")
    private String text;
}
