package br.com.hastatus.crawlers.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.hastatus.crawlers.db.dao.ExecutionDAO;
import br.com.hastatus.crawlers.db.dao.ItemsDAO;
import br.com.hastatus.crawlers.db.dao.SearchExtractionDAO;
import br.com.hastatus.crawlers.db.dao.SearchRulesDAO;
import br.com.hastatus.crawlers.dto.ItemCategoryDTO;
import br.com.hastatus.crawlers.dto.SearchDTO;

public class SearchImp implements ISearch {

	private SearchRulesDAO searchRulesDAO;
	private SearchExtractionDAO searchExtractionDAO;
	private ExecutionDAO executionDAO;
	private ItemsDAO itemsDAO;
	
	@Override
	public void executeSearchs(Connection conn, long groupId) {
		
		searchExtractionDAO = new SearchExtractionDAO(conn);
		searchRulesDAO = new SearchRulesDAO(conn);
		executionDAO  = new ExecutionDAO(conn);
		itemsDAO = new ItemsDAO(conn);
		
		try {
			List<SearchDTO> searchs = searchRulesDAO.getAllSearchRules();
			
			for(SearchDTO search : searchs) {
				try {
					
					String url = search.getUrl();
					System.out.println("Executing search: "+url);
					Document doc = DocumentRequest.requestHttpDocumentByGet(url);
					
					Long executionId = executionDAO.persistExecution(doc, "search-"+search.getId(), url, groupId);				
					
					Elements els = doc.select(search.getItemsListCssSelector());
					
					ItemCategoryDTO itemCategory = search.getItemCategory();
					
					int position = 1;				
					for(Element el : els) {
						
						String itemIdAttr;
						//se tiver selector para o elemento id
						if(search.getItemsElementIdCssSelector()!=null) {
							Elements elsId = el.select(search.getItemsElementIdCssSelector());
							
							itemIdAttr = elsId.attr(search.getItemIdAttrCssSelector());
						}
						else {
							itemIdAttr = el.attr(search.getItemIdAttrCssSelector());
						}
						
						String itemId = null;
						//se tiver regex para tratar o id
						if(search.getItemIdStringRegex()!=null) {
							
							Pattern regex = Pattern.compile(search.getItemIdStringRegex());
							Matcher matcher = regex.matcher(itemIdAttr);
							if (matcher.find()) {
								itemId = matcher.group(1);
							}
						}
						else {
							itemId = itemIdAttr;
						}						
						
						if(itemId!=null) {
							searchExtractionDAO.persistSearchExtraction(search.getId(), itemId, position, executionId);					
							
							String itemUrl = itemCategory.getBaseUrl()+itemId;
							try {
								itemsDAO.persistItem(itemId, itemUrl, itemCategory.getId());
							}
							catch(SQLIntegrityConstraintViolationException e) {
								//so grava novos itens, neste caso o item já está gravado, não seria um erro a ser tratado, mas ignorado. 
							}
						}
						position++;
					}	
				}
				catch(RuntimeException e) {
					System.out.println("An error occurred  executing search id:"+search.getId()+" error:"+e.getMessage());
				}
			}
		}
		catch(SQLException | IOException e) {
			throw new RuntimeException(e);
		}		
	}

}
