package com.king.sys;

/**
 * @创建人 chq
 * @创建时间 2019/12/17
 * @描述
 */
public class SysDict {

    private Long dictSn;

    private Integer classNo;

    private Integer dictNo;

    private String dictDesc;

    private Integer dispOrder;

    private Long parentSn;

    private Integer useFlag;

    public Long getDictSn() {
        return dictSn;
    }

    public void setDictSn(Long dictSn) {
        this.dictSn = dictSn;
    }

    public Integer getClassNo() {
        return classNo;
    }

    public void setClassNo(Integer classNo) {
        this.classNo = classNo;
    }

    public Integer getDictNo() {
        return dictNo;
    }

    public void setDictNo(Integer dictNo) {
        this.dictNo = dictNo;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public Long getParentSn() {
        return parentSn;
    }

    public void setParentSn(Long parentSn) {
        this.parentSn = parentSn;
    }

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
    }
}
