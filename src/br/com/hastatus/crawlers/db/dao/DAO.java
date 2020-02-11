package br.com.hastatus.crawlers.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	
	Connection connection;
	
	public DAO(Connection conn) {
		this.connection = conn;
	}
	
	Long insertRow(PreparedStatement stm) throws SQLException{
		Long insertedId = null;
			
		int total = stm.executeUpdate();
		if(total == 1) {
			ResultSet result = stm.getGeneratedKeys();
			if (result.next()) {
                insertedId = result.getLong(1);
            }
			else {
				throw new RuntimeException("No id generated");	
			}
		}
		else {
			throw new RuntimeException("Inserted rows for execution:"+total);
		}
		
		return insertedId;		
		
	}
	
	void insertVoidRow(PreparedStatement stm) throws SQLException{
			
		int total = stm.executeUpdate();
		if(total != 1) {
			throw new RuntimeException("Inserted rows for execution:"+total);
		}
		
			
		
	}

}
