package br.com.hastatus.crawlers.service;

import java.sql.Connection;

public interface ISearch {
	
	void executeSearchs(Connection conn, long groupId);
	
}
