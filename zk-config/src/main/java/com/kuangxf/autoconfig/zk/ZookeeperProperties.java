/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kuangxf.autoconfig.zk;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Properties related to connecting to Zookeeper
 *
 * @author Spencer Gibb
 * @since 1.0.0
 */
@Validated
@ConfigurationProperties("zk.config")
public class ZookeeperProperties {

	/**
	 * zookeeper服务器地址
	 */
	@NotNull
	private String connectString = "localhost:2181";

	/**
	 * 配置文件存储的格式:properties,yml
	 */
	private String dataFormat = "properties";

	/**
	 * 是否启用zookeeper作为配置中心
	 */
	private boolean enabled = true;

	/**
	 * zk配置中心路径
	 */
	@NotEmpty
	private String path;

	/**
	 * Initial amount of time to wait between retries
	 */
	private Integer baseSleepTimeMs = 50;

	/**
	 * Max number of times to retry
	 */
	private Integer maxRetries = 10;

	/**
	 * Max time in ms to sleep on each retry
	 */
	private Integer maxSleepMs = 500;

	/**
	 * Wait time to block on connection to Zookeeper
	 */
	private Integer blockUntilConnectedWait = 10;

	/**
	 * The unit of time related to blocking on connection to Zookeeper
	 */
	private TimeUnit blockUntilConnectedUnit = TimeUnit.SECONDS;

	
	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getConnectString() {
		return this.connectString;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public Integer getBaseSleepTimeMs() {
		return this.baseSleepTimeMs;
	}

	public Integer getMaxRetries() {
		return this.maxRetries;
	}

	public Integer getMaxSleepMs() {
		return this.maxSleepMs;
	}

	public Integer getBlockUntilConnectedWait() {
		return this.blockUntilConnectedWait;
	}

	public TimeUnit getBlockUntilConnectedUnit() {
		return this.blockUntilConnectedUnit;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
		this.baseSleepTimeMs = baseSleepTimeMs;
	}

	public void setMaxRetries(Integer maxRetries) {
		this.maxRetries = maxRetries;
	}

	public void setMaxSleepMs(Integer maxSleepMs) {
		this.maxSleepMs = maxSleepMs;
	}

	public void setBlockUntilConnectedWait(Integer blockUntilConnectedWait) {
		this.blockUntilConnectedWait = blockUntilConnectedWait;
	}

	public void setBlockUntilConnectedUnit(TimeUnit blockUntilConnectedUnit) {
		this.blockUntilConnectedUnit = blockUntilConnectedUnit;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
