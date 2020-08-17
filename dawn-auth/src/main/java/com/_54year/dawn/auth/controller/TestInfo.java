package com._54year.dawn.auth.controller;

import lombok.Data;

@Data
public class TestInfo {
	private String name;
	private int age;

	TestInfo() {
	}

	TestInfo(String name, int age) {
		this.name = name;
		this.age = age;
	}
}
