package br.com.hastatus.crawlers.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import br.com.hastatus.crawlers.db.dao.ExecutionDAO;
import br.com.hastatus.crawlers.db.dao.FieldDAO;
import br.com.hastatus.crawlers.db.dao.ItemsDAO;
import br.com.hastatus.crawlers.dto.FieldDTO;
import br.com.hastatus.crawlers.dto.ItemDTO;

public class ItemCrawlerImp implements IItemCrawler {

	private ItemsDAO itemsDAO;
	private ExecutionDAO executionDAO;
	private FieldDAO fieldDAO;
	private Map<Long, List<FieldDTO>> cacheFields;
	
	@Override
	public void crawlItem(Connection conn, long groupId){
		
		executionDAO = new ExecutionDAO(conn);
		fieldDAO = new FieldDAO(conn);
		itemsDAO = new ItemsDAO(conn);
		
		cacheFields = new TreeMap<Long, List<FieldDTO>>();
		
		try {		
			
			List<ItemDTO> items = itemsDAO.getItems();
			
			for(ItemDTO item : items) {
				try {
					List<FieldDTO> fields = getFieldsOfItem(item);
					crawItem(groupId, item, fields);
				}
				catch(HttpStatusException e ) {
					System.out.println("  ==> Item out: "+e.getMessage());
				}
			
			}
		}		
		catch(SQLException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<FieldDTO> getFieldsOfItem(ItemDTO item) throws SQLException {
		List<FieldDTO> fields;
		
		if(cacheFields.containsKey(item.getCategory())) {
			fields = cacheFields.get(item.getCategory());
		}
		else {
			fields = fieldDAO.getFieldsByType(item.getCategory());
			cacheFields.put(item.getCategory(), fields);
		}
		return fields;
	}
	
	private void crawItem(long groupId, ItemDTO item, List<FieldDTO> fieldsOfItem) throws IOException, SQLException {
		
		System.out.println("Item: "+item.getId()+" "+item.getUrl());
		
		Document doc = DocumentRequest.requestHttpDocumentByGet(item.getUrl());
		
		Long executionId = executionDAO.persistExecution(doc, item.getId(), item.getUrl(), groupId);

		
		for(FieldDTO field : fieldsOfItem) {
			try {
				Element el = doc.selectFirst(field.getFieldCssSelector());
				String value = "";
				if(el!=null) {
					value = el.html();
				}
				fieldDAO.persistFieldExtraction(field, executionId, value);
			}
			catch(RuntimeException e) {
				System.out.println("Error reading field: "+field.getFieldName());
				e.printStackTrace();
			}
		}
		
	}

}
