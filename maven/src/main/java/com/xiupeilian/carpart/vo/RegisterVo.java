package com.xiupeilian.carpart.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class RegisterVo implements Serializable {
	// �û���Ϣ
	private String loginName;
	private String phone;
	private String email;
	private String password;
	private String username;

	// ��ҵ��Ϣ
	private String companyname;
	private String zone1;
	private String tel1;
	private String zone2;
	private String tel2;
	private String address;
	private String qq;
	// ʡ����
	private Integer procince;
	private Integer city;
    private Integer contry;
    //��Ӫ��Χ
    private String main;

    private String singleBrand;

    private String singleParts;

    private String prime;


}
