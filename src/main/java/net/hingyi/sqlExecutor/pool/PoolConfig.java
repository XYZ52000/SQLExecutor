package net.hingyi.sqlExecutor.pool;

/**
 * 数据库连接池参数
 * 
 * 
 * @author guanzhenxing
 * @since 2014年3月22日 下午3:39:14
 */
public class PoolConfig {

	private String driverName;// 驱动名称
	private String url;// 连接地址
	private String userName;// 用户名
	private String password;// 密码
	private int dbType;// 数据库类型
	private String jndiName;// jndi名称
	private int minConnections = 1;// 最小连接数
	private int maxConnections = 10;// 最大连接数
	private int iniConnections = 5;// 初始化时连接数
	private int maxActiveConnections = 100;// 数据库最大的连接数
	private long connWaitTime = 1000;// 重复去获得连接的频率
	private long connectionTimeOut = 1000 * 60 * 20;// 连接超时时间，默认20分钟
	private boolean isCheakPool = true;// 是否定时检查连接池
	private long periodCheck = 1000 * 60 * 60;// 检查频率
	private long lazyCheck = 1000 * 60 * 60;// 延迟多少时间后开始 检查

	private String poolName = ""; // 数据库连接池的名称

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDbType() {
		return dbType;
	}

	public void setDbType(int dbType) {
		this.dbType = dbType;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public int getMinConnections() {
		return minConnections;
	}

	public void setMinConnections(int minConnections) {
		this.minConnections = minConnections;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getIniConnections() {
		return iniConnections;
	}

	public void setIniConnections(int iniConnections) {
		this.iniConnections = iniConnections;
	}

	public int getMaxActiveConnections() {
		return maxActiveConnections;
	}

	public void setMaxActiveConnections(int maxActiveConnections) {
		this.maxActiveConnections = maxActiveConnections;
	}

	public long getConnWaitTime() {
		return connWaitTime;
	}

	public void setConnWaitTime(long connWaitTime) {
		this.connWaitTime = connWaitTime;
	}

	public long getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(long connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public boolean isCheakPool() {
		return isCheakPool;
	}

	public void setCheakPool(boolean isCheakPool) {
		this.isCheakPool = isCheakPool;
	}

	public long getPeriodCheck() {
		return periodCheck;
	}

	public void setPeriodCheck(long periodCheck) {
		this.periodCheck = periodCheck;
	}

	public long getLazyCheck() {
		return lazyCheck;
	}

	public void setLazyCheck(long lazyCheck) {
		this.lazyCheck = lazyCheck;
	}

}
