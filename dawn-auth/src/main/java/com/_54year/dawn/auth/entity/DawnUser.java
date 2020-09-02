package com._54year.dawn.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 用户信息entity
 */
@TableName(value = "dawn_user")
public class DawnUser implements UserDetails, CredentialsContainer {

	/**
	 * userID
	 */
//	@TableId(type = IdType.ASSIGN_ID)
	private String userId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户名/登录名
	 */
	private String username;
	/**
	 * 角色数组
	 */
	private Set<GrantedAuthority> authorities;
	/**
	 * 是否启用 true启用 false未启用
	 */
	private boolean enabled;

	/**
	 * 账户未过期 true未过期 false已过期
	 * 目前数据库未存该字段 默认为true
	 */
	@TableField(exist = false)
	private boolean accountNonExpired = true;
	/**
	 * 账户未锁定 true未锁定 false已锁定
	 * 目前数据库未存该字段 默认为true
	 */
	@TableField(exist = false)
	private boolean accountNonLocked = true;
	/**
	 * 凭据未过期 true未过期 false已过期
	 * 目前未存该字段 默认为true
	 */
	@TableField(exist = false)
	private boolean credentialsNonExpired = true;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void eraseCredentials() {
		password = null;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "DawnUser{" +
			"userId='" + userId + '\'' +
			", nickName='" + nickName + '\'' +
			", password='" + password + '\'' +
			", username='" + username + '\'' +
			", authorities=" + authorities +
			", enabled=" + enabled +
			", accountNonExpired=" + accountNonExpired +
			", accountNonLocked=" + accountNonLocked +
			", credentialsNonExpired=" + credentialsNonExpired +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			'}';
	}
}
