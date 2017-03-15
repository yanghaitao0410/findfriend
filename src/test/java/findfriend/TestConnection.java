package findfriend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;


public class TestConnection {
	public static final String url = "jdbc:mysql://localhost:3306/findfriend?useUnicode=true&characterEncoding=utf8";  
	public static final String name = "com.mysql.jdbc.Driver";  
	public static final String user = "root";  
	public static final String password = "1234";  
	public Connection conn = null;  
	public PreparedStatement pst = null; 
	public ResultSet ret = null;
	
//	@Test
	public void testConn() throws Exception{
		Class.forName(name);
		conn = DriverManager.getConnection(url, user, password);
		pst = conn.prepareStatement("select * from user");
		ret = pst.executeQuery();
		while(ret.next()){
			int id = ret.getInt(1);
			String name = ret.getString(2);
			String pwd = ret.getString(3);
			System.out.println(id + name + pwd);
		}
	}
	
}
