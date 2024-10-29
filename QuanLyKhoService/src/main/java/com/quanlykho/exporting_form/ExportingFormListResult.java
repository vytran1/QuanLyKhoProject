package com.quanlykho.exporting_form;

import java.util.List;

public class ExportingFormListResult {
    private List<ExportingFormDTO> dtos;
    private int pageNum;
    private int pageSize;
    private String sortField;
    private String sortDir;
    private String reverseDir;
    private Long totalItems;
    private Integer totalPage;
    private String keyWord;
	public ExportingFormListResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<ExportingFormDTO> getDtos() {
		return dtos;
	}
	public void setDtos(List<ExportingFormDTO> dtos) {
		this.dtos = dtos;
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
