package br.com.hastatus.crawlers.service;

import java.sql.Connection;

public interface IItemCrawler {
	
	void crawlItem(Connection conn, long groupId);

}
