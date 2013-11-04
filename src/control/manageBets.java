package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;

public class manageBets 
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
    protected static void toBet(String n_bet,String c_bet,String i_bet,String i_match, Connection conn){
    	
    	PreparedStatement statement = null;
    	
    	try{
    		//Connection conn1 = myDataSourceRef.getConnection();
    		//Connection conn = openConn();
    		
    		String query="INSERT INTO" + " BETS"+ " VALUES(?,?,?,?)";
	        
    		statement = conn.prepareStatement(query);
	        statement.setString(1, n_bet);
	        statement.setString(2, c_bet);
	        statement.setString(3, i_bet);
	        statement.setString(4, i_match);
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
    
    
    