package br.com.hastatus.crawlers;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.hastatus.crawlers.db.ConnectionFactory;
import br.com.hastatus.crawlers.db.dao.GroupDAO;
import br.com.hastatus.crawlers.service.IItemCrawler;
import br.com.hastatus.crawlers.service.ISearch;
import br.com.hastatus.crawlers.service.ItemCrawlerImp;
import br.com.hastatus.crawlers.service.SearchImp;


public class Main {
	
	

	public static void main(String[] args) throws SQLException {
		System.out.println("Starting...");
		Connection conn = ConnectionFactory.getConnection();		
		try {
			System.out.println("Executing crawlers");
			executeCrawlers(conn);
			System.out.println("Done.");
		}
		finally {
							
			conn.close();
			System.out.println("End.");
		}
		 
			
	}
	
//	public static void teste() {
//		try {
//			Document doc = DocumentRequest.requestHttpDocumentByGet("https://play.google.com/store/search?q=truco&c=apps&hl=pt_BR");
//			Elements els = doc.select("div.WHE7ib");
//			
//			for(Element el : els) {
//				
//				Elements elsId = el.select("div.Q9MA7b a");
//				
//				String itemIdAttr = elsId.attr("href");
//				
//				
//				String itemId = "";
//				Pattern regex = Pattern.compile("id=(.*)");
//				Matcher matcher = regex.matcher(itemIdAttr);
//				
//				
//				if (matcher.find()) {
//					itemId = matcher.group(1);
//				}
//				
//				System.out.println("itemId: "+itemId);
//				break;
//			}
//		}
//		catch(IOException e) {
//			System.out.println(e);
//		}
//	}
	
	public static void executeCrawlers(Connection conn) throws SQLException {
		System.out.println("Initializing crawlers...");
		
		conn.setAutoCommit(true);
		ISearch isearch = new SearchImp();
		
		
		GroupDAO groupDAO = new GroupDAO(conn);
		
		Long groupId = groupDAO.startGroup();		
		isearch.executeSearchs(conn, groupId);
		System.out.println("Search results recorded.");
		
		System.out.println("Searching itens...");
		
		IItemCrawler pageDetails = new ItemCrawlerImp();
		pageDetails.crawlItem(conn, groupId);			
		
	}
	
}
