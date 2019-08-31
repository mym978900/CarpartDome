package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Collections implements Serializable {
    private Integer id;

    private Integer colletorId;

    private Integer itemsId;

    private Date createTime;

    private Integer deleteStatus;

   
}