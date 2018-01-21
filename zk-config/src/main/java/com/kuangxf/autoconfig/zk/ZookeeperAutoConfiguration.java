package com.kuangxf.autoconfig.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnZookeeperEnabled
@EnableConfigurationProperties
public class ZookeeperAutoConfiguration {

	private final static Logger log = LoggerFactory.getLogger(ZookeeperAutoConfiguration.class);

	@Bean
	@ConditionalOnMissingBean
	public ZookeeperProperties zookeeperConfigProperties() {
		return new ZookeeperProperties();
	}

	@Bean(destroyMethod = "close")
	@ConditionalOnMissingBean
	public CuratorFramework curatorFramework(RetryPolicy retryPolicy, ZookeeperProperties properties) throws Exception {
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		log.info("即将从zookeeper配置中心获取配置信息，zkServer:{},path:{}", properties.getConnectString(), properties.getPath());

		builder.connectString(properties.getConnectString());

		CuratorFramework curator = builder.retryPolicy(retryPolicy).build();
		curator.start();
		log.info("正在连接zookeeper配置中心 ，等待:{}{}", properties.getBlockUntilConnectedWait(),
				properties.getBlockUntilConnectedUnit());
		curator.blockUntilConnected(properties.getBlockUntilConnectedWait(), properties.getBlockUntilConnectedUnit());
		log.info("已经连接到zookeeper配置中心,path:{}", properties.getPath());
		return curator;
	}

	@Bean
	@ConditionalOnMissingBean
	public RetryPolicy exponentialBackoffRetry(ZookeeperProperties properties) {
		return new ExponentialBackoffRetry(properties.getBaseSleepTimeMs(), properties.getMaxRetries(),
				properties.getMaxSleepMs());
	}

}
