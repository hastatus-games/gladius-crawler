package br.com.hastatus.crawlers.dto;

public class SearchDTO {
	private Long id;
	private String url;
	private String itemsListCssSelector;
	
	private String itemsElementIdCssSelector;
	private String itemIdStringRegex;
	
	private String itemIdAttrCssSelector;
	private ItemCategoryDTO itemCategory;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getItemsListCssSelector() {
		return itemsListCssSelector;
	}
	public void setItemsListCssSelector(String itemsListCssSelector) {
		this.itemsListCssSelector = itemsListCssSelector;
	}
	public String getItemIdAttrCssSelector() {
		return itemIdAttrCssSelector;
	}
	public void setItemIdAttrCssSelector(String itemIdAttrCssSelector) {
		this.itemIdAttrCssSelector = itemIdAttrCssSelector;
	}
	public ItemCategoryDTO getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(ItemCategoryDTO itemCategory) {
		this.itemCategory = itemCategory;
	}
	public String getItemsElementIdCssSelector() {
		return itemsElementIdCssSelector;
	}
	public void setItemsElementIdCssSelector(String itemsElementIdCssSelector) {
		this.itemsElementIdCssSelector = itemsElementIdCssSelector;
	}
	public String getItemIdStringRegex() {
		return itemIdStringRegex;
	}
	public void setItemIdStringRegex(String itemIdStringRegex) {
		this.itemIdStringRegex = itemIdStringRegex;
	}
	
	
	
}
