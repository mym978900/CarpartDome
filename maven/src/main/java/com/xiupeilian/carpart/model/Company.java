package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Company implements Serializable {
    private Integer id;

    private String companyCode;

    private String companyName;

    private String address;

    private Integer province;

    private Integer city;

    private Integer county;

    private String tel1;

    private String tel2;

    private String phone;

    private String main;

    private String singleBrand;

    private String singleParts;

    private String prime;

    private String leader;

    private Date createTime;

    private String picurl1;

    private String picurl2;

    private String picurl3;

    private Integer deleteStatus;

    private String qq;

    private String zone1;

    private String zone2;

    private String reservedField1;

    private String reservedField2;

    private String reservedField3;

    private String memo;


}