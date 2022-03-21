package com.pj.vaultdemo.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Application configuration class that sets up the application properties.
 *
 * @author Pavan Kumar Jadda
 * @since 1.0.0
 */
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
