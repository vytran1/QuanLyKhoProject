package com.quanlykho.inventory;

import java.util.List;

public class InventoryListResult {
	private List<InventoryDTO> inventoryDTOs;
	private int pageNum;
    private int pageSize;
    private String sortField;
    private String sortDir;
    private String reverseDir;
    private Long totalItems;
    private Integer totalPage;
    
    
    
    
    
	public InventoryListResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<InventoryDTO> getInventoryDTOs() {
		return inventoryDTOs;
	}
	public void setInventoryDTOs(List<InventoryDTO> inventoryDTOs) {
		this.inventoryDTOs = inventoryDTOs;
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
    
    

}
