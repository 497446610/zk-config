zookeeper配置中心说明：
=====================
1、基于spring-cloud-zookeeper配置中心的思路，主要是获取zookeeper的配置方式做了些调整;
2、此配置中心只支持和spring boot的集成。

模块说明：
=====================
1.zk-config:zookeeper配置中心，使用的时候只需要依赖此工程
2.zk-configi-demo:用于演示如何集成zk-config

使用说明：
=====================
	1、项目依赖zk-config模块；
	
	2、项目配置方式：
	
		配置文件需要使用bootstrap.properties方式；
		
		添加如下配置，相关说明如下：
		
		#是否启用zk配置中心
		zk.config.enabled=true
		#配置文件在zookeeper的全路径
		#zk.config.path=/config/using/config.yml
		zk.config.path=/config/using/config.properties
		#zookeeper服务地址
		zk.config.connectString=localhost:2181
		#配置数据格式（yml、properties)
		#zk.config.data-format=yml
		zk.config.data-format=properties

技术交流：
=====================
QQ：497446610