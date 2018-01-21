package com.kuangxf.autoconfig.zk.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Value("${url:default}")
	private String url;

	@Value("${zkServer:default}")
	private String zkServer;

	@RequestMapping("url")
	public String url() {
		return url;
	}

	@RequestMapping("zkServer")
	public String zkServer() {
		return zkServer;
	}

}
