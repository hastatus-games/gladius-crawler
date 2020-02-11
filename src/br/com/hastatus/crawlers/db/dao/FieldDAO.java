package br.com.hastatus.crawlers.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.hastatus.crawlers.dto.FieldDTO;




public class FieldDAO extends DAO {
	
	
	
	public FieldDAO(Connection connection) {
		super(connection);
	}
	
	
	public Long persistFieldExtraction(FieldDTO field, Long executionId, String value) throws SQLException {
		Long insertedId = null;
		
		String sql = "INSERT INTO `crawlers`.`fields_extraction`\n" + 
				"(" +
				"`execution_id`,\n" + 
				"`field_id`,\n" + 
				"`value`)\n" + 
				"VALUES\n" + 
				"(?, ?, ?);";
		
		PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stm.setLong(1, executionId);
		stm.setLong(2, field.getId());
		stm.setString(3, value);
		
		insertedId = insertRow(stm);
		
		return insertedId;
	}
	
	public List<FieldDTO> getFieldsByType(long type) throws SQLException {
		List<FieldDTO> campos = new ArrayList<FieldDTO>();
		String query = "SELECT id, field_name, field_css_selector FROM crawlers.field_rules where item_type = ?";
		
		PreparedStatement stm = this.connection.prepareStatement(query);
		stm.setLong(1, type);
		
		stm.execute();
		
		ResultSet result = stm.getResultSet();
		
		
		while(result.next()) {
			FieldDTO fieldDTO = new FieldDTO();
			fieldDTO.setItemType(type);
			
			Long fieldId = result.getLong(1);
			String fieldName = result.getString(2);
			String fieldSelector = result.getString(3);
			
			fieldDTO.setId(fieldId);
			fieldDTO.setFieldName(fieldName);
			fieldDTO.setFieldCssSelector(fieldSelector);
			
			campos.add(fieldDTO);			
		}
		
		return campos;
	}
	
	

}
