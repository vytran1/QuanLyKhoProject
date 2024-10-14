package com.quanlykho.inventory_user;

import java.util.List;

import com.quanlykho.common.InventoryUser;

public class InventoryUserList {
    private List<InventoryUser> inventoryUsers;
    private int pageNum;
    private int pageSize;
    private String sortField;
    private String sortDir;
    private String reverseDir;
    private Long totalItems;
    private Integer totalPage;
    
    
	public InventoryUserList(List<InventoryUser> inventoryUsers, int pageNum, int pageSize, String sortField,
			String sortDir, String reverseDir) {
		super();
		this.inventoryUsers = inventoryUsers;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sortField = sortField;
		this.sortDir = sortDir;
		this.reverseDir = reverseDir;
	}


	public InventoryUserList() {
		super();
		// TODO Auto-generated constructor stub
	}


	public List<InventoryUser> getInventoryUsers() {
		return inventoryUsers;
	}


	public void setInventoryUsers(List<InventoryUser> inventoryUsers) {
		this.inventoryUsers = inventoryUsers;
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
