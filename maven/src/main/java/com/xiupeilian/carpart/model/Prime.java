package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Prime implements Serializable {
    private Integer id;

    private String name;

    private Integer seq;

    private Date createTime;

    private String type;


}