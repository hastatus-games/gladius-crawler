package br.com.hastatus.crawlers.dto;

public class FieldDTO {
	private long id;
	private long itemType;
	private String fieldName;
	private String fieldCssSelector;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemType() {
		return itemType;
	}
	public void setItemType(long itemType) {
		this.itemType = itemType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fielddName) {
		this.fieldName = fielddName;
	}
	public String getFieldCssSelector() {
		return fieldCssSelector;
	}
	public void setFieldCssSelector(String fieldCssSelector) {
		this.fieldCssSelector = fieldCssSelector;
	}	
}
