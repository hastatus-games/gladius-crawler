package br.com.hastatus.crawlers.db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.hastatus.crawlers.dto.ItemCategoryDTO;
import br.com.hastatus.crawlers.dto.SearchDTO;

public class SearchRulesDAO extends DAO {
	
	public SearchRulesDAO(Connection conn) {
		super(conn);
	}

	public List<SearchDTO> getAllSearchRules() throws SQLException {
		List<SearchDTO> searchs = new ArrayList<SearchDTO>();
		
		String sql = "SELECT s.id, s.url, s.items_list_css_selector, s.item_id_attr_selector, s.item_category, i.name, i.base_url, s.item_element_id_css_selector, s.item_id_string_regex "
				+ "from search_rules s, item_category i where s.item_category = i.id and s.active = 1";
		
		PreparedStatement stm = connection.prepareStatement(sql); 
				
		ResultSet result = stm.executeQuery();
		while(result.next()) {
			SearchDTO search = new SearchDTO();
			search.setId(result.getLong(1));
			search.setUrl(result.getString(2));
			search.setItemsListCssSelector(result.getString(3));
			search.setItemIdAttrCssSelector(result.getString(4));
			search.setItemsElementIdCssSelector(result.getString("s.item_element_id_css_selector"));
			search.setItemIdStringRegex(result.getString("s.item_id_string_regex"));
			
			ItemCategoryDTO itemCategory = new ItemCategoryDTO();
			itemCategory.setId(result.getLong(5));
			itemCategory.setName(result.getString(6));
			itemCategory.setBaseUrl(result.getString(7));			
			
			search.setItemCategory(itemCategory);
			
			searchs.add(search);
		}
		
		return searchs;
		
	}
}
