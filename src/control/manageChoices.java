package control;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class manageChoices 
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
    protected static void toChoice(String n_choice,String i_choice,BigDecimal o_choice,String i_bet, String xml_date, Connection conn){
   	
    	PreparedStatement statement = null;
    	try{
    		//Connection conn = myDataSourceRef.getConnection();
    		//Connection conn = openConn();
    		
	    	String query="INSERT INTO" + " CHOICES"+ " VALUES(?,?,?,?,?)";
	    
	    	statement = conn.prepareStatement(query);
		    statement.setString(1, n_choice);
		    statement.setString(2, i_choice);
		    statement.setBigDecimal(3, o_choice);
		    statement.setString(4, i_bet);
		    statement.setString(5, xml_date);
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
    
    
    