package br.com.hastatus.crawlers.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.nodes.Document;

public class ExecutionDAO extends DAO {
	
	
	public ExecutionDAO(Connection connection) {
		super(connection);
	}
	
	public Long persistExecution(Document doc, String id, String url, long group_id) throws SQLException {
		Long insertedId = null;
		
//		String payload = doc.toString();
		
		String sql = "INSERT INTO `crawlers`.`execution` ( item_id, url, group_id) " + 
				"VALUES" + 
				" (?, ?, ?)";
		
		PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stm.setString(1, id);
//		stm.setString(2, payload);
		stm.setString(2, url); //3
		stm.setLong(3, group_id); //4
		
		insertedId = insertRow(stm);
		
		return insertedId;
	}
	

}
