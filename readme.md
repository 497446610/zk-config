zookeeper��������˵����
=====================
1������spring-cloud-zookeeper�������ĵ�˼·����Ҫ�ǻ�ȡzookeeper�����÷�ʽ����Щ����;
2������������ֻ֧�ֺ�spring boot�ļ��ɡ�

ģ��˵����
=====================
1.zk-config:zookeeper�������ģ�ʹ�õ�ʱ��ֻ��Ҫ�����˹���
2.zk-configi-demo:������ʾ��μ���zk-config

ʹ��˵����
=====================
	1����Ŀ����zk-configģ�飻
	
	2����Ŀ���÷�ʽ��
	
		�����ļ���Ҫʹ��bootstrap.properties��ʽ��
		
		����������ã����˵�����£�
		
		#�Ƿ�����zk��������
		zk.config.enabled=true
		#�����ļ���zookeeper��ȫ·��
		#zk.config.path=/config/using/config.yml
		zk.config.path=/config/using/config.properties
		#zookeeper�����ַ
		zk.config.connectString=localhost:2181
		#�������ݸ�ʽ��yml��properties)
		#zk.config.data-format=yml
		zk.config.data-format=properties

����������
=====================
QQ��497446610