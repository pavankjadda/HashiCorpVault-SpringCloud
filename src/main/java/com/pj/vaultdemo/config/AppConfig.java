package com.pj.vaultdemo.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Data
public class AppConfig
{
	private final Environment environment;

	public AppConfig(Environment environment)
	{
		this.environment = environment;
	}

}
