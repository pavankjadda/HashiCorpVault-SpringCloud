package com.pj.vaultdemo.web;

import com.pj.vaultdemo.config.AppConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController
{
	private final AppConfig appConfig;

	public EmployeeController(AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}


	@GetMapping("/test")
	public String test()
	{
		return appConfig.getEnvironment().getProperty("username")+", "+appConfig.getEnvironment().getProperty("password")+", "+appConfig.getEnvironment().getProperty("url");
	}
}
