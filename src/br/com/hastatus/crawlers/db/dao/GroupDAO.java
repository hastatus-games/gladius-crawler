package br.com.hastatus.crawlers.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupDAO extends DAO{
	
	public GroupDAO(Connection conn) {
		super(conn);
	}
	
	
	public Long startGroup() throws SQLException {
		String sql = "INSERT INTO `crawlers`.`group`\n" + 
				"(id)" + 
				"VALUES" + 
				"(null)";
		
		PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);		
		
		return insertRow(stm);
		
	}

}
