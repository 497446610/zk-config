package com.kuangxf.autoconfig.zk;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.util.ReflectionUtils;
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings("rawtypes")
public class ZookeeperPropertySource extends EnumerablePropertySource<CuratorFramework> {
	private final static Logger log = LoggerFactory.getLogger(ZookeeperPropertySourceLocator.class);
	private Map<String, Object> properties = new LinkedHashMap<>();

	public ZookeeperPropertySource(String fullPath, CuratorFramework source, String dataFormat) {
		super(fullPath, source);
		switch (dataFormat) {
		case "properties":// 解析properties格式的数据
			getByProperty(fullPath);
			break;
		case "yml":// 解析yml格式的数据
			getByYml(fullPath);
			break;
		default:
			getByProperty(fullPath);
			break;
		}
	}

	private byte[] getZkData(String fullPath) {
		byte[] bytes = null;
		try {
			bytes = this.getSource().getData().forPath(fullPath);
		} catch (KeeperException e) {
			if (e.code() != KeeperException.Code.NONODE) { // not found
				log.error("未读取zookeeper中的配置信息,路径无效：{}", fullPath);
			}
		} catch (Exception e) {
			log.error("未读取zookeeper中的配置信息,path:{}", fullPath);
		}

		return bytes;
	}

	private void getByYml(String fullPath) {
		InputStream ins = null;
		try {
			byte[] bytes = getZkData(fullPath);

			if (bytes == null || bytes.length == 0) {
				log.error("未读取zookeeper中的配置信息,path:{}", fullPath);
				return;
			}
			ins = new ByteArrayInputStream(bytes);
			Yaml yaml = new Yaml();
			
			Map map = (Map) yaml.load(ins);
			readYamlMap(null,map);
		} catch (Exception exception) {
			log.error("读取zookeeper配置异常", exception);
			ReflectionUtils.rethrowRuntimeException(exception);
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					log.error("关闭IO流异常", e);
				}
			}
		}
	}

	private void readYamlMap(String keyPath, Map map) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			String keyPathChild = (keyPath == null ? "" : keyPath + ".") + key;
			if (value instanceof Map) {
				readYamlMap(keyPathChild, (Map) value);
			} else {
				this.properties.put(keyPathChild, value);
			}

		}
	}

	private void getByProperty(String fullPath) {
		InputStream ins = null;
		try {
			byte[] bytes = getZkData(fullPath);

			if (bytes == null || bytes.length == 0) {
				log.error("未读取zookeeper中的配置信息,path:{}", fullPath);
				return;
			}

			ins = new ByteArrayInputStream(bytes);
			Properties properties = new Properties();
			properties.load(ins);
			Iterator it = properties.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				this.properties.put(key, value);

			}
		} catch (Exception exception) {
			log.error("读取zookeeper配置异常", exception);
			ReflectionUtils.rethrowRuntimeException(exception);
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					log.error("关闭IO流异常", e);
				}
			}
		}
	}

	@Override
	public String[] getPropertyNames() {
		Set<String> strings = this.properties.keySet();
		return strings.toArray(new String[strings.size()]);
	}

	@Override
	public Object getProperty(String name) {
		return this.properties.get(name);
	}

}
