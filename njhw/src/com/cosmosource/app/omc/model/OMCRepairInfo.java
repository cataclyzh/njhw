package com.cosmosource.app.omc.model;

/**
 * 维修设备报表
 * @author Administrator
 *
 */
public class OMCRepairInfo
{
    private int allCount;
    
    private int accomplishCount;
    
    private int dispatchedCount;
    
    private int returnVisitCount;
    
    public int getAllCount()
    {
        return allCount;
    }
    
    public void setAllCount(int allCount)
    {
        this.allCount = allCount;
    }
    
    public int getAccomplishCount()
    {
        return accomplishCount;
    }
    
    public void setAccomplishCount(int accomplishCount)
    {
        this.accomplishCount = accomplishCount;
    }
    
    public int getDispatchedCount()
    {
        return dispatchedCount;
    }
    
    public void setDispatchedCount(int dispatchedCount)
    {
        this.dispatchedCount = dispatchedCount;
    }

    public int getReturnVisitCount()
    {
        return returnVisitCount;
    }

    public void setReturnVisitCount(int returnVisitCount)
    {
        this.returnVisitCount = returnVisitCount;
    }
    
}
