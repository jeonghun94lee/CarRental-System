package codr;

import java.sql.Connection;
import java.sql.DriverManager;

public class CallConn {
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String username = "system";
	private String pass = "1111";
	private Connection conn = null;
	public CallConn() {
		
	}
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public boolean checkConn() {
		try {
			conn = DriverManager.getConnection(url, username, pass);
			System.out.println("ÄÁ³Ø¼Ç ¼º°ø");
			return true;
		} catch (Exception e) {
			System.out.println("ÄÁ³Ø¼Ç ½ÇÆÐ");
		}
		return false;
	}
}
