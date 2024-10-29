package com.quanlykho.inventory_order;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryOrderListResult {
    private List<InventoryOrderDTO> orderDTOs;
    private int pageNum;
    private int pageSize;
    private String sortField;
    private String sortDir;
    private String reverseDir;
    private Long totalItems;
    private Integer totalPage;
    private String keyWord;
    private LocalDateTime from;
    private LocalDateTime to;
    
    
    
	public InventoryOrderListResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<InventoryOrderDTO> getOrderDTOs() {
		return orderDTOs;
	}
	public void setOrderDTOs(List<InventoryOrderDTO> orderDTOs) {
		this.orderDTOs = orderDTOs;
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
	public LocalDateTime getFrom() {
		return from;
	}
	public void setFrom(LocalDateTime from) {
		this.from = from;
	}
	public LocalDateTime getTo() {
		return to;
	}
	public void setTo(LocalDateTime to) {
		this.to = to;
	}
 	
    
    
}
