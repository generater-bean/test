package li.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO<T> {
	private java.util.List<T> data;
	private boolean showPrevious;
	private boolean showFirstPage;
	private  boolean showNext;
	private boolean	showEndPage;
	private Integer page;
	private List<Integer> pages=new ArrayList<Integer>();
	private Integer totalPage;
	
	public boolean isShowPrevious() {
		return showPrevious;
	}
	public void setShowPrevious(boolean showPrevious) {
		this.showPrevious = showPrevious;
	}
	public boolean isShowFirstPage() {
		return showFirstPage;
	}
	public void setShowFirstPage(boolean showFirstPage) {
		this.showFirstPage = showFirstPage;
	}
	public boolean isShowNext() {
		return showNext;
	}
	public void setShowNext(boolean showNext) {
		this.showNext = showNext;
	}
	public boolean isShowEndPage() {
		return showEndPage;
	}
	public void setShowEndPage(boolean showEndPage) {
		this.showEndPage = showEndPage;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public List<Integer> getPages() {
		return pages;
	}
	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}
	
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public void setpagination(Integer totalCount, Integer page, Integer size) {
		
		
		
		if(totalCount%size==0) {
			totalPage=totalCount/size;
		}else {
			totalPage=totalCount/size+1;
		}
		
		
		if(page<1) {
			page=1;
		}
		if(page>totalPage) {
			page=totalPage;
		}
		
		this.page=page;
		
		pages.add(page);
		for(int i=1;i<=3;i++) {
			if(page-i>0) {
				pages.add(0,page-i);
			}
			if(page+i<=totalPage) {
				pages.add(page+i);
			}
		}
		
		
		
		
		
		//是否显示上一页
		 if(page==1) {
			 showPrevious=false;
			 
		 }else {
			 showPrevious=true;
		 }
		//
		 if(page==totalPage) {
			 
			 showNext=false;
		 }else {
			 showNext=true;
		 }
		 
		 //
		 if(pages.contains(1)) {
			 showFirstPage=false;
		 }else {
			 showFirstPage=true;
			 
			 
		 }
		 if(pages.contains(totalPage)) {
			 showEndPage=false;
		 }else {
			 showEndPage=true;
		 }
	}
	public java.util.List<T> getData() {
		return data;
	}
	public void setData(java.util.List<T> data) {
		this.data = data;
	}
	
}
