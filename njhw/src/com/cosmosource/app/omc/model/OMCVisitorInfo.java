package com.cosmosource.app.omc.model;

/**
 * 访客信息
 * @author Administrator
 *
 */
public class OMCVisitorInfo
{
    //总访客
    private int visitorCount;
    
    //外网预约访客统计
    private int outerNetVisitorCount;
    
    //前台登记
    private int receptionVisitorCount;
    
    //目前访客数
    private int nowVisitorCount;
    
    //主动约访
    private int accordVisitorCount;
    
    //受访者姓名
    private String user_name;
    
    //访客姓名
    private String vi_name;
    
    //市民卡号
    private String card_id;
    //上访的机关单位
    private String org_name;
    //照片
    private String res_bak2;

    public int getVisitorCount()
    {
        return visitorCount;
    }

    public void setVisitorCount(int visitorCount)
    {
        this.visitorCount = visitorCount;
    }

    public int getOuterNetVisitorCount()
    {
        return outerNetVisitorCount;
    }

    public void setOuterNetVisitorCount(int outerNetVisitorCount)
    {
        this.outerNetVisitorCount = outerNetVisitorCount;
    }
    
    public int getReceptionVisitorCount()
    {
        return receptionVisitorCount;
    }

    public void setReceptionVisitorCount(int receptionVisitorCount)
    {
        this.receptionVisitorCount = receptionVisitorCount;
    }

    public int getNowVisitorCount()
    {
        return nowVisitorCount;
    }

    public void setNowVisitorCount(int nowVisitorCount)
    {
        this.nowVisitorCount = nowVisitorCount;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String userName)
    {
        user_name = userName;
    }

    public String getCard_id()
    {
        return card_id;
    }

    public void setCard_id(String cardId)
    {
        card_id = cardId;
    }

    public String getOrg_name()
    {
        return org_name;
    }

    public void setOrg_name(String orgName)
    {
        org_name = orgName;
    }

    public String getRes_bak2()
    {
        return res_bak2;
    }

    public void setRes_bak2(String resBak2)
    {
        res_bak2 = resBak2;
    }

    public int getAccordVisitorCount()
    {
        return accordVisitorCount;
    }

    public void setAccordVisitorCount(int accordVisitorCount)
    {
        this.accordVisitorCount = accordVisitorCount;
    }

    public String getVi_name()
    {
        return vi_name;
    }

    public void setVi_name(String viName)
    {
        vi_name = viName;
    }
}
