package net.hingyi.sqlExecutor.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * 数据库连接池
 * 
 * @author guanzhenxing
 * @since 2014年3月22日 下午3:34:47
 */
public class ConnectionPool {

	private boolean isActive = false; // 连接池活动状态
	private PoolConfig poolConfig;
	private int totalConn; // 创建的连接数

	// 空闲连接
	private List<Connection> freeConnections = new Vector<Connection>();
	// 活动连接
	private List<Connection> activeConnections = new Vector<Connection>();
	// 将线程和连接绑定，保证事务能统一执行
	ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	/**
	 * 构造方法，初始化连接池
	 * 
	 * @param poolConfig
	 */
	public ConnectionPool(PoolConfig poolConfig) {
		super();
		this.poolConfig = poolConfig;
		createConnectionPool();
		checkConnectionPool();

	}

	private void createConnectionPool() {

		try {
			Class.forName(poolConfig.getDriverName());
			int initNum = poolConfig.getIniConnections();
			for (int i = 0; i < initNum; i++) {
				Connection conn = createConn();
				freeConnections.add(conn);
			}
			isActive = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private synchronized Connection createConn() throws ClassNotFoundException,
			SQLException {
		Connection conn = null;
		if (null != poolConfig) {
			Class.forName(poolConfig.getDriverName());
			conn = DriverManager.getConnection(poolConfig.getUrl(),
					poolConfig.getUserName(), poolConfig.getPassword());
			totalConn++;
		}

		return conn;
	}

	/**
	 * 获得连接
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {

		Connection conn = null;

		try {

			if (null == poolConfig) {
				return conn;
			}
			// 如果已经创建的连接数小于数据库的最大连接数而且小于连接池最大的连接数，那么再创建连接数
			if (totalConn < poolConfig.getMaxActiveConnections()
					&& totalConn < poolConfig.getMaxConnections()) {

				if (freeConnections.size() > 0) {// 如果还有空闲连接
					conn = freeConnections.get(0);
					if (null != conn) {
						threadLocal.set(conn);
						freeConnections.remove(0);

					}
				} else {
					conn = createConn();
				}

			} else {
				// 等待一段时间后再去获得连接
				wait(poolConfig.getConnWaitTime());
				conn = getConnection();
			}

			if (isValid(conn)) {
				activeConnections.add(conn);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return conn;
	}

	private boolean isValid(Connection conn) {
		try {
			if (conn.isClosed()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 获得当前连接
	 * 
	 * @return
	 */
	public Connection getCurrentConnection() {
		// 默认从线程中取出连接
		Connection conn = threadLocal.get();
		if (!isValid(conn)) {
			conn = getConnection();
		}
		return conn;
	}

	/**
	 * 释放连接
	 * 
	 * @param conn
	 */
	public synchronized void releaseConnection(Connection conn)  throws SQLException {

		if (isValid(conn)) {
			freeConnections.add(conn);
			activeConnections.remove(conn);
			threadLocal.remove();
			// 唤醒所有正待等待的线程，去抢连接
			notifyAll();
		}

	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 * @throws SQLException 
	 */
	public synchronized void closeConnection(Connection conn) throws SQLException {

		conn.close();
		freeConnections.remove(conn);
		activeConnections.remove(conn);
		threadLocal.remove();
	}

	/**
	 * 销毁连接
	 */
	public synchronized void destroy() {
		for (Connection conn : freeConnections) {
			try {
				if (isValid(conn)) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (Connection conn : activeConnections) {
			try {
				if (isValid(conn)) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		isActive = false;
		totalConn = 0;
	}

	/**
	 * 判断该连接池是否可用
	 * 
	 * @return
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * 获得活动的连接数
	 * 
	 * @return
	 */
	public int getActiveConnNum() {
		return activeConnections.size();
	}

	/**
	 * 获得空闲的连接数
	 * 
	 * @return
	 */
	public int getFreeConnNum() {
		return freeConnections.size();
	}

	private void checkConnectionPool() {

		if (poolConfig.isCheakPool()) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// 1.对线程里面的连接状态
					// 2.连接池最小 最大连接数
					// 3.其他状态进行检查，因为这里还需要写几个线程管理的类，暂时就不添加了
					System.out.println("空线池连接数：" + freeConnections.size());
					System.out.println("活动连接数：：" + activeConnections.size());
					System.out.println("总的连接数：" + totalConn);
					System.out.println("连接池状态：" + isActive);
				}
			}, poolConfig.getLazyCheck(), poolConfig.getPeriodCheck());
		}

	}

}
