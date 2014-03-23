package net.hingyi.sqlExecutor.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * 数据连接池管理器
 * 
 * @author guanzhenxing
 * @since 2014年3月22日 下午4:52:14
 */
public class ConnectionPoolManager {

	private static ConnectionPoolManager connectionPoolManager;
	// 连接池存放
	private Hashtable<String, ConnectionPool> pools;

	// 总的连接数
	private static int clients;

	private ConnectionPoolManager() {
		connectionPoolManager = this;
		pools = new Hashtable<String, ConnectionPool>();
		init();
	}

	private void init() {

		for (int i = 0; i < InitPoolConfig.poolConfigList.size(); i++) {
			PoolConfig pc = InitPoolConfig.poolConfigList.get(i);
			ConnectionPool pool = new ConnectionPool(pc);
			if (pool != null) {
				pools.put(pc.getPoolName(), pool);
			}
		}
	}

	/**
	 * 获得连接池管理器实例
	 * 
	 * @return
	 */
	public static synchronized ConnectionPoolManager getInstance() {
		if (null == connectionPoolManager) {
			connectionPoolManager = new ConnectionPoolManager();
		}
		clients++;
		return connectionPoolManager;
	}

	/**
	 * 获得连接池
	 * 
	 * @return
	 */
	public ConnectionPool getPool(String poolName) {
		return pools.get(poolName);
	}

	/**
	 * 获得数据库链接
	 * 
	 * @return
	 */
	public Connection getConnection(String poolName) {
		Connection conn = null;
		conn = getPool(poolName).getConnection();
		return conn;
	}

	/**
	 * 释放连接
	 */
	public void releaseConnection(String poolName, Connection conn) {

		try {
			ConnectionPool pool = getPool(poolName);
			if (null != null) {
				pool.releaseConnection(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清空连接池
	 * 
	 * @param poolName
	 */
	public void destory(String poolName) {
		ConnectionPool pool = getPool(poolName);
		if (null != null) {
			pool.destroy();
		}
	}

	/**
	 * 获得连接数
	 * @return
	 */
	public static int getClients() {
		return clients;
	}
	
	

}
