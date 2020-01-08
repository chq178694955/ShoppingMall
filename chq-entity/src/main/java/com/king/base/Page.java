package com.king.base;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/10
 * @描述
 */
public class Page<T> {

    private Integer pageNo;

    private Integer pageSize;

    private Long totalCount;

    private Long totalPage;

    private List<T> datas;

    private List<T> summary;

    public Page(){

    }

    public Page(Integer pageNo, Integer pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public void setResults(List<T> datas,Long totalCount){
        this.datas = datas;
        this.totalCount = totalCount;

        calcPages();
    }

    private void calcPages(){
        if(totalCount == null || totalCount == 0){
            this.totalPage = 0L;
        }else{
            if(totalCount % pageSize == 0){
                this.totalPage = totalCount / pageSize;
            }else{
                this.totalPage = totalCount / pageSize + 1;
            }
        }
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<T> getSummary() {
        return summary;
    }

    public void setSummary(List<T> summary) {
        this.summary = summary;
    }
}
