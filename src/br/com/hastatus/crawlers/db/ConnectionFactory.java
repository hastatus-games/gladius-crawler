package br.com.hastatus.crawlers.db;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	
	
	public static Connection getConnection() {		
        try {
        	
        	Properties properties = new Properties();

            ClassLoader classLoader = ConnectionFactory.class.getClassLoader();
            URL resource = classLoader.getResource("database.conf");
            properties.load(new FileReader(new File(resource.getFile())));            

        	
        	String url = properties.get("jdbc.url").toString();
        	String user = properties.get("jdbc.user").toString();
        	String password = properties.get("jdbc.password").toString();
        	
        	
        	Connection conn = (Connection) DriverManager.getConnection(url, user, password);
        	
            return conn; 
        }
        catch(IOException e) {
        	throw new RuntimeException(e);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}