package br.com.hastatus.crawlers.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentRequest {
	
	public static Document requestHttpDocumentByGet(String url) throws IOException {
		
		Document doc = Jsoup.connect(url).get();
		doc.charset(StandardCharsets.UTF_8);
		
		return doc;
	}
}
