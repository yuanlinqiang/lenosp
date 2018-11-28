package com.len.util;

/**
 * @author zhuxiaomeng
 * @date 2017/12/6.
 * @email 154040976@qq.com
 * 分页工具
 */
public class PageUtil <T>{
  /**当前页*/
  private int curPageNo=1;
  private int pageCount;//总页数
  private int pageSize=5;//每页大小 默认5
  private int upPageNo;//上一页
  private int nextPageNo;//下一页
  private int startPage;//开始页

  private T t;

  public int getCurPageNo() {
    return curPageNo;
  }

  public void setCurPageNo(int curPageNo) {
    if(curPageNo<=0){
      this.curPageNo=1;
    }
    if(curPageNo!=1&&curPageNo>0){
      upPageNo=curPageNo-1;
    }
    nextPageNo=curPageNo+1;
    this.curPageNo = curPageNo;
    this.startPage=(curPageNo-1)*pageSize;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    if(pageCount%pageSize>0){
      this.pageCount=pageCount/pageSize+1;
    }else {
      this.pageCount = pageCount/pageSize;
    }
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getUpPageNo() {
    return upPageNo;
  }

  public void setUpPageNo(int upPageNo) {
    this.upPageNo = upPageNo;
  }

  public int getNextPageNo() {
    return nextPageNo;
  }

  public void setNextPageNo(int nextPageNo) {
    this.nextPageNo = nextPageNo;
  }

  public int getStartPage() {
    return startPage;
  }

  public void setStartPage(int startPage) {

    this.startPage = startPage;
  }
}
