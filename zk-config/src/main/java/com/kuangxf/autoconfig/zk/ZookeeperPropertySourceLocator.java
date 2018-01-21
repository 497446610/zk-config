package com.kuangxf.autoconfig.zk;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class ZookeeperPropertySourceLocator implements PropertySourceLocator {
	private final static Logger log = LoggerFactory.getLogger(ZookeeperPropertySourceLocator.class);

	@Autowired(required = false)
	private ZookeeperProperties zookeeperProperties;

	@Autowired(required = false)
	private CuratorFramework curatorFramework;

	@Override
	public PropertySource<?> locate(Environment environment) {
		if (zookeeperProperties == null) {
			log.info("未启用zk配置中心");
		}

		if (curatorFramework == null) {
			log.info("curatorFramework未正确初始化,无法启用zk配置中心");
			return null;
		}
		log.info("启用zk配置中心,开始从zookeeper中获取配置信息");

		String zkConfigPath = zookeeperProperties.getPath();
		String dataFormat = zookeeperProperties.getDataFormat();
		CompositePropertySource composite = new CompositePropertySource("zookeeper");

		try {
			PropertySource propertySource = create(zkConfigPath, dataFormat);
			composite.addPropertySource(propertySource);
		} catch (Exception e) {
			log.error("读取zookeeper配置异常", e);
			ReflectionUtils.rethrowRuntimeException(e);
		}
		log.info("完成从zookeeper中获取配置信息");
		return composite;
	}

	protected ZookeeperPropertySource create(String zkConfigPath, String dataFormat) {
		return new ZookeeperPropertySource(zkConfigPath, curatorFramework, dataFormat);
	}

}
