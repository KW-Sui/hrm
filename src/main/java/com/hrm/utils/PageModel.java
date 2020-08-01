package com.hrm.utils;

public class PageModel {
    private int pageIndex;  //当前页面的页码
    private int pageSize=4;   //页面大小
    private int totalSize;  //总页数
    private int recordCount;    //总记录数

    public PageModel() {
        pageIndex=1;
        pageSize=4;
    }

    public PageModel(int pageIndex, int pageSize, int totalSize, int recordCount) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.recordCount = recordCount;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", recordCount=" + recordCount +
                '}';
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        if(recordCount%pageSize==0){
            totalSize=recordCount/pageSize;
        }else{
            totalSize=recordCount/pageSize+1;
        }
        return totalSize;
    }

    public int getFirstLimitParam() {
        return (this.getPageIndex()-1)*this.pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
