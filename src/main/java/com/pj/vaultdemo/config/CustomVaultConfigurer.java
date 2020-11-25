package com.pj.vaultdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.vault.config.SecretBackendConfigurer;
import org.springframework.cloud.vault.config.VaultConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CustomVaultConfigurer implements VaultConfigurer
{
	private final Environment environment;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CustomVaultConfigurer(Environment environment)
	{
		this.environment = environment;
	}

	@Override
	public void addSecretBackends(SecretBackendConfigurer configurer)
	{
		//Get active profile from environment, if none exist select DEV profile
		if (environment.getActiveProfiles().length > 0)
		{
			logger.info("Active Profile: {}", environment.getActiveProfiles()[0]);
			configurer.add("secret/app/pres/" + environment.getActiveProfiles()[0]);
		}
		else
		{
			logger.info("Active Profile: Default profile(DEV) selected ");
			configurer.add("secret/app/pres/dev");
		}

		configurer.registerDefaultKeyValueSecretBackends(false);
		configurer.registerDefaultDiscoveredSecretBackends(true);
	}
}
