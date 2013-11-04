package control;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class manageMatches 
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
    
    protected static void toMatch(String i_match,String n_match,String sd_match,String l_match,String st_match,String i_event, Connection conn){
    	
    	PreparedStatement statement = null;
    	try{
    		//Connection conn = myDataSourceRef.getConnection();
    		//Connection conn = openConn();
    		
    		String query="INSERT INTO" + " MATCHES"+ " VALUES(?,?,?,?,?,?)";
	        
    		statement = conn.prepareStatement(query);
	        statement.setString(1, n_match);
	        statement.setString(2, i_match);
	        statement.setString(3, sd_match);
	        statement.setString(4, l_match);
	        statement.setString(5, st_match);
	        statement.setString(6, i_event);
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
    
    
    