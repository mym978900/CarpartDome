package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Dymsn implements Serializable {
    private Integer id;

    private String message;

    private Date createTime;

    private Integer deleteStatus;


}