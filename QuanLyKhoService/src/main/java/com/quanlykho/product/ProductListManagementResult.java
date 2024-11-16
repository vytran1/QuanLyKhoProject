package com.quanlykho.product;

import java.util.List;

public class ProductListManagementResult {
    private List<ProductDTOManagement> productDTOs;
    private int pageNum;
	private int pageSize;
	private String sortField;
	private String sortDir;
	private String keyword;
	private String reverseDir;
	private Long totalItems;
	private Integer totalPage;
	public ProductListManagementResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductListManagementResult(List<ProductDTOManagement> productDTOs, int pageNum, int pageSize, String sortField, String sortDir,
			String keyword, String reverseDir, Long totalItems, Integer totalPage) {
		super();
		this.productDTOs = productDTOs;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sortField = sortField;
		this.sortDir = sortDir;
		this.keyword = keyword;
		this.reverseDir = reverseDir;
		this.totalItems = totalItems;
		this.totalPage = totalPage;
	}
	public List<ProductDTOManagement> getProductDTOs() {
		return productDTOs;
	}
	public void setProductDTOs(List<ProductDTOManagement> productDTOs) {
		this.productDTOs = productDTOs;
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
