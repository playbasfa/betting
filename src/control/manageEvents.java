package control;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class manageEvents 
{
	
	@Resource(name = "betDatasource")
	private DataSource myDataSourceRef;
	
    private static Connection openConn(){
    	Connection conn = null;
    	try{
    		String user="username_bet";
        	String password="212212";
        	String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
    		Class.forName("oracle.jdbc.driver.OracleDriver");
        	
    		conn =  DriverManager.getConnection(url,user,password);
    	}catch(ClassNotFoundException | SQLException e){
        	System.out.println(e);            	
        }
    	return conn;
    }
    protected static void toEvent(String n_event, String i_event, String i_sport, Connection conn){
    	
    	PreparedStatement statement = null;
    	
    	try{
    		//Connection conn = myDataSourceRef.getConnection();
    		//Connection conn = openConn();
	    	
    		String query="INSERT INTO" + " EVENTS"+ " VALUES(?,?,?)";
	        
    		statement = conn.prepareStatement(query);
	        statement.setString(1, n_event);
	        statement.setString(2, i_event);
	        statement.setString(3, i_sport);
	        statement.executeUpdate();
	        statement.close();
	        
	        //conn.close();
	        
    	}catch(SQLException e){try {
			statement.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}}
	    }   
}
    
    
    