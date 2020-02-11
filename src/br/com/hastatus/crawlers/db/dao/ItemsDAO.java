package br.com.hastatus.crawlers.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.hastatus.crawlers.dto.ItemDTO;

public class ItemsDAO extends DAO {

	public ItemsDAO(Connection conn) {
		super(conn);
	}
	
	public List<ItemDTO> getItems() throws SQLException {
		List<ItemDTO> items = new ArrayList<ItemDTO>();
		String sql = "SELECT id, url, category from items";
		
		PreparedStatement stm = this.connection.prepareStatement(sql);
		
		ResultSet result = stm.executeQuery();
		while(result.next()) {
			ItemDTO item = new ItemDTO();
			item.setId(result.getString(1));
			item.setUrl(result.getString(2));
			item.setCategory(result.getLong(3));
			items.add(item);
		}
		return items;
	}
	
	
	public void persistItem(String id, String url, long category) throws SQLException {
		String sql = "INSERT INTO items (id, url, category) VALUES (?,?, ?)";
		
		PreparedStatement stm = this.connection.prepareStatement(sql);
		stm.setString(1, id);
		stm.setString(2, url);
		stm.setLong(3, category);
		insertVoidRow(stm);
	}
	
}
