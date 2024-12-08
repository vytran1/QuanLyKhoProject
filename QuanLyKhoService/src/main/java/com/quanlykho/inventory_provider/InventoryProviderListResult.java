package com.quanlykho.inventory_provider;

import java.util.ArrayList;
import java.util.List;

public class InventoryProviderListResult {
    List<InventoryProviderDTO> providers = new ArrayList<>();
    private int pageNum;
    private int pageSize;
    private String sortField;
    private String sortDir;
    private String reverseDir;
    private Long totalItems;
    private Integer totalPage;
    private String keyWord;
    
    
	public InventoryProviderListResult() {
		super();
		// TODO Auto-generated constructor stub
	}


	public List<InventoryProviderDTO> getProviders() {
		return providers;
	}


	public void setProviders(List<InventoryProviderDTO> providers) {
		this.providers = providers;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getSortField() {
		return sortField;
	}


	public void setSortField(String sortField) {
		this.sortField = sortField;
	}


	public String getSortDir() {
		return sortDir;
	}


	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}


	public String getReverseDir() {
		return reverseDir;
	}


	public void setReverseDir(String reverseDir) {
		this.reverseDir = reverseDir;
	}


	public Long getTotalItems() {
		return totalItems;
	}


	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}


	public Integer getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}


	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
    
    
    
}
