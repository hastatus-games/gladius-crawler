package br.com.hastatus.crawlers.db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class SearchExtractionDAO extends DAO {
	
	public SearchExtractionDAO(Connection conn) {
		super(conn);
	}
	
	
	public Long persistSearchExtraction(long searchId, String itemId, int position, long executionId) throws SQLException {
		String sql = "INSERT INTO `crawlers`.`search_extraction`\n" + 
				"(" +
				"`search_id`,\n" + 
				"`position`,\n" + 
				"`item_id`,"
				+ "execution_id)\n" + 
				"VALUES\n" + 
				"(?,?,?,?)";
		
		PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stm.setLong(1, searchId);
		stm.setInt(2, position);
		stm.setString(3, itemId);
		stm.setLong(4, executionId);
		
		return insertRow(stm);
		
		
	}

}
