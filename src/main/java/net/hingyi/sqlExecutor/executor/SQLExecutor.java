package net.hingyi.sqlExecutor.executor;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.hingyi.sqlExecutor.pool.ConnectionPool;
import net.hingyi.sqlExecutor.pool.DatabaseType;

public class SQLExecutor {

	// 数据库链接
	private Connection conn;
	// 数据库类型
	private int dbType;
	// 数据库连接池
	private ConnectionPool pool;

	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private PreparedStatement prepStatement;
	private int timeoutInSec = 0; // 0 = no timeout

	private int maxRows = 1000; // 当次查询的最大结果

	public SQLExecutor(Connection conn) {
		this.conn = conn;
		this.dbType = DatabaseType.getDbType(conn);
	}

	public SQLExecutor(ConnectionPool pool) {
		this.pool = pool;
		this.conn = pool.getConnection();
		this.dbType = DatabaseType.getDbType(conn);
	}

	public int getTimeoutInSec() {
		return timeoutInSec;
	}

	public void setTimeoutInSec(int timeoutInSec) {
		this.timeoutInSec = timeoutInSec;
	}

	/**
	 * 取消查询
	 */
	public void cancelQuery() {

		try {
			if (null != prepStatement) {
				prepStatement.cancel();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭查询
	 */
	public void closeQuery() {
		try {
			if (null != prepStatement) {
				prepStatement.close();
				prepStatement = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setPrepStatementParameters(List params) throws SQLException {

		for (int i = 0; i < params.size(); i++) {

			Object param = params.get(i);
			if (param instanceof NullParameterObject) {
				int sqltype = ((NullParameterObject) param).sqlType;
				prepStatement.setNull(i + 1, sqltype);
			} else if (param instanceof Integer) {
				int value = ((Integer) param).intValue();
				prepStatement.setInt(i + 1, value);
			} else if (param instanceof Short) {
				short value = ((Short) param).shortValue();
				prepStatement.setShort(i + 1, value);
			} else if (param instanceof Float) {
				float value = ((Float) param).floatValue();
				prepStatement.setFloat(i + 1, value);
			} else if (param instanceof Double) {
				double value = ((Double) param).doubleValue();
				prepStatement.setDouble(i + 1, value);
			} else if (param instanceof Boolean) {
				boolean value = ((Boolean) param).booleanValue();
				prepStatement.setBoolean(i + 1, value);
			} else if (param instanceof Long) {
				long value = ((Long) param).longValue();
				prepStatement.setLong(i + 1, value);
			} else if (param instanceof String) {
				String value = param.toString();
				prepStatement.setString(i + 1, value);
			} else if (param instanceof java.sql.Date) {
				java.sql.Date value = (java.sql.Date) param;
				prepStatement.setDate(i + 1, value);
			} else if (param instanceof java.util.Date) {
				java.util.Date date = new java.util.Date();
				long time = date.getTime();
				java.sql.Date value = new java.sql.Date(time);
				prepStatement.setDate(i + 1, value);
			} else if (param instanceof java.sql.Time) {
				java.sql.Time value = (java.sql.Time) param;
				prepStatement.setTime(i + 1, value);
			} else if (param instanceof java.sql.Timestamp) {
				java.sql.Timestamp value = (java.sql.Timestamp) param;
				prepStatement.setTimestamp(i + 1, value);
			}else if(param instanceof Blob){
				Blob value = (Blob)param;
				prepStatement.setBlob(i+ 1, value);
			}else if(param instanceof InputStream){
				InputStream value = (InputStream)param;
				prepStatement.setBlob(i + 1, value);
			}else if(param instanceof Byte){
				byte value = ((Byte)param).byteValue();
				
			}
			
			// prepStatement.setByte(parameterIndex, x);
			// prepStatement.setCharacterStream(parameterIndex, reader);
			// prepStatement.setClob(parameterIndex, x);

		}

	}

	private void setPrepStatementParameters(Object[] params) {

	}

	/**
	 * 执行增加、删除、修改操作
	 * 
	 * @param sql
	 * @param paramsList
	 * @return
	 */
	public int execute(String sql, List params) {

		int numRecordsUpdated = 0;
		try {
			prepStatement = conn.prepareStatement(sql);
			setPrepStatementParameters(params);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 执行增加、删除、修改操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int execute(String sql, Object[] params) {
		return 0;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object queryForObject(String sql, List params) {
		return null;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object queryForObject(String sql, Object[] params) {
		return null;
	}

	/**
	 * @param sql
	 * @param params
	 * @param elementTyp
	 * @return
	 */
	public <T> List<T> queryForList(String sql, Object[] params,
			Class<T> elementTyp) {
		return null;
	}

	/**
	 * @param sql
	 * @param params
	 * @param elementTyp
	 * @return
	 */
	public <T> List<T> queryForList(String sql, List params, Class<T> elementTyp) {
		return null;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, ?>> queryForList(String sql, List params) {
		return null;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, ?>> queryForList(String sql, Object[] params) {
		return null;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public List queryForlist(String sql, List params) {
		return null;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql, List params) {
		return null;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql, Object[] params) {
		return null;
	}

	/**
	 * 获得事务是否自动提交
	 * 
	 * @return
	 */
	public boolean getAutoCommit() {
		try {
			return conn.getAutoCommit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 设置是否自动提交
	 * 
	 * @param autoCommit
	 */
	public void setAutoCommit(boolean autoCommit) {
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置事务的隔离级别
	 * 
	 * @param level
	 */
	public void setTransactionIsolation(int level) {
		try {
			conn.setTransactionIsolation(level);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 事务提交
	 */
	public void commitTrans() {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 事务回滚
	 */
	public void rollbackTrans() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置该连接是否只读
	 * 
	 * @param readOnly
	 */
	public void setReadOnly(boolean readOnly) {
		try {
			conn.setReadOnly(readOnly);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断该链接是否只读
	 * 
	 * @return
	 */
	public boolean isReadOnly() {
		try {
			return conn.isReadOnly();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 释放连接
	 */
	public void releaseConnection() {
		try {
			this.pool.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接
	 */
	public void closeConnection() {
		try {
			this.pool.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

}

/** NullParameterObject inner class -- defines a null parameter */
class NullParameterObject extends Object {
	int sqlType;

	NullParameterObject(int sqlType) {
		this.sqlType = sqlType;
	}
}
