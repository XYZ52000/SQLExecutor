package net.hingyi.sqlExecutor.pool;

import net.hingyi.sqlExecutor.pool.ConnectionPoolManager;

import org.junit.Test;

public class ConnectionPoolManagerTest {

	@Test
	public void test() {
		
		for(int i = 0; i < 10; i++){
			//System.out.println(ConnectionPoolManager.getInstance().getConnection("testPool"));
			System.out.println(ConnectionPoolManager.getInstance().getPool("testPool"));
		}
		
		
	}

	

}
