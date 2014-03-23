package net.hingyi.sqlExecutor.pool;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化数据连接池配置
 * 
 * @author guanzhenxing
 * @since 2014年3月22日 下午5:05:54
 */
public class InitPoolConfig {
	
	public static List<PoolConfig> poolConfigList; 
	static{
		
		poolConfigList = new ArrayList<PoolConfig>();
		
		//从properties文件中读取配置信息
		
		PoolConfig pc = new PoolConfig();
		pc.setDriverName("com.mysql.jdbc.Driver");
		pc.setUrl("jdbc:mysql://localhost:3306/spring_test");
		pc.setUserName("root");
		pc.setPassword("root");
		pc.setPoolName("testPool");
		
		poolConfigList.add(pc);
	}

}
