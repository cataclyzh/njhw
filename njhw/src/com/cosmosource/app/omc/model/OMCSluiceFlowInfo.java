package com.cosmosource.app.omc.model;

/**
 * 闸机监控
 * @author Administrator
 *
 */
public class OMCSluiceFlowInfo
{
    //今日总量
    private int todayTotal;
    
    //出闸
    private int outTotal;
    
    //进闸
    private int enterTotal;
    
    //内部人员 
    private int insiderTotal;
    
    //外部人员
    private int outsiderTotal;

    public int getTodayTotal()
    {
        return todayTotal;
    }

    public void setTodayTotal(int todayTotal)
    {
        this.todayTotal = todayTotal;
    }

    public int getOutTotal()
    {
        return outTotal;
    }

    public void setOutTotal(int outTotal)
    {
        this.outTotal = outTotal;
    }

    public int getEnterTotal()
    {
        return enterTotal;
    }

    public void setEnterTotal(int enterTotal)
    {
        this.enterTotal = enterTotal;
    }

    public int getInsiderTotal()
    {
        return insiderTotal;
    }

    public void setInsiderTotal(int insiderTotal)
    {
        this.insiderTotal = insiderTotal;
    }

    public int getOutsiderTotal()
    {
        return outsiderTotal;
    }

    public void setOutsiderTotal(int outsiderTotal)
    {
        this.outsiderTotal = outsiderTotal;
    }
}
