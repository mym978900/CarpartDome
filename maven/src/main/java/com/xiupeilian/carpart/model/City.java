package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable
{
    private Integer id;

    private String name;

    private Integer parentId;

    private String shortName;

    private String leveltype;

    private String cityCode;

    private String zipCode;

    private String mergerName;

    private String lng;

    private String lat;

    private String pinyin;


}