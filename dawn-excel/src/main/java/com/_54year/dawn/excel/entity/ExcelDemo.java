package com._54year.dawn.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * excel导出测试
 * </p>
 *
 * @author Andersen
 * @since 2022-03-11
 */
public class ExcelDemo implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@ExcelProperty("ID")
	private Integer id;
	@ExcelProperty("姓名")
	private String fullName;
	@ExcelProperty("年龄")
	private Integer age;
	@ExcelProperty("详细地址")
	private String address;
	@ExcelProperty("电话号码")
	private String phone;
	@ExcelProperty("邮箱")
	private String email;
	@ExcelProperty("个性签名")
	private String sign;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "ExcelDemo{" +
			"id=" + id +
			", fullName=" + fullName +
			", age=" + age +
			", address=" + address +
			", phone=" + phone +
			", email=" + email +
			", sign=" + sign +
			"}";
	}
}
