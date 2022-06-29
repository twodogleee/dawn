package com._54year.dawn.excel.entity;

import com._54year.dawn.common.annotation.EncryptData;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * excel导出测试
 * </p>
 *
 * @author Andersen
 * @since 2022-03-11
 */
@Data
public class ExcelDemo implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@ExcelProperty("ID")
	private Integer id;
	@ExcelProperty("姓名")
	@EncryptData
	private String fullName;
	@ExcelProperty("年龄")
	private Integer age;
	@ExcelProperty("详细地址")
	private String address;
	@ExcelProperty("电话号码")
	@EncryptData
	private String phone;
	@ExcelProperty("邮箱")
	private String email;
	@ExcelProperty("个性签名")
	private String sign;

	@ExcelProperty("字段1")
	private String c1;
	@ExcelProperty("字段2")
	private String c2;
	@ExcelProperty("字段3")
	private String c3;
	@ExcelProperty("字段4")
	private String c4;
	@ExcelProperty("字段5")
	private String c5;
	@ExcelProperty("字段6")
	private String c6;
	@ExcelProperty("字段7")
	private String c7;
	@ExcelProperty("字段8")
	private String c8;
	@ExcelProperty("字段9")
	private String c9;
	@ExcelProperty("字段10")
	private String c10;
	@ExcelProperty("字段11")
	private String c11;
	@ExcelProperty("字段12")
	private String c12;
	@ExcelProperty("字段13")
	private String c13;
	@ExcelProperty("字段14")
	private String c14;
	@ExcelProperty("字段15")
	private String c15;
	@ExcelProperty("字段16")
	private String c16;
	@ExcelProperty("字段17")
	private String c17;
	@ExcelProperty("字段18")
	private String c18;
	@ExcelProperty("字段19")
	private String c19;
	@ExcelProperty("字段20")
	private String c20;
	@ExcelProperty("字段21")
	private String c21;
	@ExcelProperty("字段22")
	private String c22;
	@ExcelProperty("字段23")
	private String c23;
	@ExcelProperty("字段24")
	private String c24;
	@ExcelProperty("字段25")
	private String c25;
	@ExcelProperty("字段26")
	private String c26;
	@ExcelProperty("字段27")
	private String c27;
	@ExcelProperty("字段28")
	private String c28;
	@ExcelProperty("字段29")
	private String c29;
	@ExcelProperty("字段30")
	private String c30;
	@ExcelProperty("字段31")
	private String c31;
	@ExcelProperty("字段32")
	private String c32;
	@ExcelProperty("字段33")
	private String c33;
	@ExcelProperty("字段34")
	private String c34;
	@ExcelProperty("字段35")
	private String c35;
	@ExcelProperty("字段36")
	private String c36;
	@ExcelProperty("字段37")
	private String c37;
	@ExcelProperty("字段38")
	private String c38;
	@ExcelProperty("字段39")
	private String c39;
	@ExcelProperty("字段40")
	private String c40;
	@ExcelProperty("字段41")
	private String c41;
	@ExcelProperty("字段42")
	private String c42;
	@ExcelProperty("字段43")
	private String c43;
	@ExcelProperty("字段44")
	private String c44;
	@ExcelProperty("字段45")
	private String c45;
	@ExcelProperty("字段46")
	private String c46;
	@ExcelProperty("字段47")
	private String c47;
	@ExcelProperty("字段48")
	private String c48;
	@ExcelProperty("字段49")
	private String c49;
	@ExcelProperty("字段50")
	private String c50;
}
