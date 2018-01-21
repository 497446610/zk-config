package com.kuangxf.autoconfig.zk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnZookeeperEnabled
@Import(ZookeeperAutoConfiguration.class)
public class ZookeeperConfigBootstrapConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	public ZookeeperPropertySourceLocator zookeeperPropertySourceLocator() {
		return new ZookeeperPropertySourceLocator();
	}
	
}
