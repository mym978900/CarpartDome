package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Items implements Serializable {
    private Integer id;

    private Integer companyId;

    private String title;

    private String content;

    private Integer userId;

    private String picUrl;

    private Integer deleteStatus;

    private Integer collectionCount;

    private Date updateTime;

    private Integer brandId;

    private Integer partId;

    private Integer primeId;

    private String reservedField1;

    private String reservedField2;

    private String reservedField3;


}