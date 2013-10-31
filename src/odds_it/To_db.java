package odds_it;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class To_db 
{
    
    
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException, JAXBException, IOException, InterruptedException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, SQLException
      {
        To_db obj = new To_db();
        obj.todb(obj.objectize("../betting/src/files_xml/odds_it.xml"));
      }
    
    private Connection openConn(){
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
    
    private void closeConn(PreparedStatement statement,Connection conn)
    {
    	try{
    		conn.close();
    		}
    	catch(SQLException e){
    		e.printStackTrace();
    		}
    }
    
    private void todb(Sports obj) throws SQLException
    {
    	Connection conn = openConn();  	
    	PreparedStatement statement=null;
    	String query = null;
    	
    	
	    List<Sport> lspo = obj.getSport();
	    int nspo = lspo.size();
	    for(int a = 0; a<nspo ; a++){
            
            // Scrivi su DB Sport
            String i_sport = lspo.get(a).getId().toString();
            String n_sport = lspo.get(a).getName();
            
            query="INSERT INTO" + " SPORTS"+ " VALUES(?,?)";
            statement = conn.prepareStatement(query);
            statement.setString(1, n_sport);
            statement.setString(2, i_sport);
            statement.executeUpdate();
            statement.close();
            ///////////////////////
            
            // Prende lista eventi
            List<Event> leve = obj.getSport().get(a).getEvent();
            int neve = leve.size();
            for(int b = 0; b < neve; b++){
                
                // Scrivi su DB Event --> ricordati che la chiave esterna è i_sport
                String i_event = leve.get(b).getId().toString();
                String n_event = leve.get(b).getName();
                
                query="INSERT INTO" + " EVENTS"+ " VALUES(?,?,?)";
                statement = conn.prepareStatement(query);
                statement.setString(1, n_event);
                statement.setString(2, i_event);
                statement.setString(3, i_sport);
                statement.executeUpdate();
                statement.close();
                
                // Prende lista match
                List<Match> lmat = obj.getSport().get(a).getEvent().get(b).getMatch();
                int nmat = lmat.size();
                for(int c = 0; c < nmat-1;c++){
                    
                    // Scrivi su DB Matches --> ricordati che la chiave esterna è i_event
                    String i_match = lmat.get(c).getId().toString();
                    String n_match = lmat.get(c).getName();
                    String sd_match = lmat.get(c).getStartDate();
                    String l_match = lmat.get(c).getLiveId();
                    String st_match = lmat.get(c).getStreaming().toString();
                    
                    query="INSERT INTO" + " MATCHES"+ " VALUES(?,?,?,?,?,?)";
                    statement = conn.prepareStatement(query);
                    statement.setString(1, n_match);
                    statement.setString(2, i_match);
                    statement.setString(3, sd_match);
                    statement.setString(4, l_match);
                    statement.setString(5, st_match);
                    statement.setString(6, i_event);
                    statement.executeUpdate();
                    statement.close();
                    ///////////////////////
            
                    List<Bet> lbet = obj.getSport().get(a).getEvent().get(b).getMatch().get(c).getBets().getBet();
                    int nbet = lbet.size();
                    for(int d = 0; d < nbet; d++){
                    
                        // Scrivi su DB Event --> ricordati che la chiave esterna è i_match
                        String i_bet = lbet.get(d).getId().toString();
                        String c_bet = lbet.get(d).getCode();
                        String n_bet = lbet.get(d).getName();
                        
                        query="INSERT INTO" + " BETS"+ " VALUES(?,?,?,?)";
                        statement = conn.prepareStatement(query);
                        statement.setString(1, n_bet);
                        statement.setString(2, c_bet);
                        statement.setString(3, i_bet);
                        statement.setString(4, i_match);
                        statement.executeUpdate();
                        statement.close();
                        ///////////////////////
                        
                        List<Choice> lcho = obj.getSport().get(a).getEvent().get(b).getMatch().get(c).getBets().getBet().get(d).getChoice();
                        int ncho = lcho.size();
                        for(int e = 0; e < ncho ; e++){
                            // Scrivi su DB Choise --> ricordati che la chiave esterna è i_bet
                            String n_choice = lcho.get(e).getName();
                            String i_choice = lcho.get(e).getId().toString();
                            BigDecimal o_choice = lcho.get(e).getOdd();
                            
                            query="INSERT INTO" + " CHOICES"+ " VALUES(?,?,?,?)";
                            statement = conn.prepareStatement(query);
                            statement.setString(1, n_choice);
                            statement.setString(2, i_choice);
                            statement.setBigDecimal(3, o_choice);
                            statement.setString(4, i_bet);
                            statement.executeUpdate();
                            statement.close();
                        }                
                    }
                }
                
            	
            }
        
           
    	    	
    }
	    closeConn(statement,conn);
    } 
    
    //Una volta eseguito l'unmarshall crea l'oggetto riempito con i dati XML
    private Sports objectize(String s) throws ClassNotFoundException, JAXBException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
       Sports obj = unMarsh(s);
       return obj;
      }
    
    //Esegue l'unmarshall dell' XML
    private Sports unMarsh(String name) throws ClassNotFoundException, JAXBException{
        File f = new File(name);
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Sports obj = (Sports) unmarshaller.unmarshal(f);
        return obj;
    }
    

}
    
    
    