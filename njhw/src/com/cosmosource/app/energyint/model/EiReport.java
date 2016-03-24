package com.cosmosource.app.energyint.model;

/**
 * 能源综合运营信息 <一句话功能简述> <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2013-7-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EiReport
{
    // 哪种能耗
    private int queryEnergyType;
    
    // 查询时间精度
    private int queryTimeType;
    
    // 水、电-消耗情况
    private Double measure;
    
    // 气耗-单位时间气耗（单位：m³）
    private Integer measure_flow;
    
    // 气耗-单位时间气耗（单位：Gj）
    private Double measure_heat;
    
    // 删除类型
    private int deleteType;
    
    private int year;
    
    private int month;
    
    private int day;
    
    private int hour;
    
    // 机关名字
    private String org_name;
    
    // 楼层名字
    private String name;
    
    // 机关在当前楼层的所属的房间数
    private int totalRoom;
    
    // 水表所在座
    private String water_seat;
    
    // 水表所在层
    private String water_floor;
    
    // 气表所在座
    private String flow_seat;
    
    // 气表所在层
    private String flow_floor;
    
    // 3项电表所在座
    private String kwh_seat;
    
    // 3项电表所在层
    private String kwh_floor;
    
    public String getKwh_seat()
    {
        return kwh_seat;
    }
    
    public void setKwh_seat(String kwh_seat)
    {
        this.kwh_seat = kwh_seat;
    }
    
    public String getKwh_floor()
    {
        return kwh_floor;
    }
    
    public void setKwh_floor(String kwh_floor)
    {
        this.kwh_floor = kwh_floor;
    }
    
    public int getYear()
    {
        return year;
    }
    
    public void setYear(int year)
    {
        this.year = year;
    }
    
    public int getMonth()
    {
        return month;
    }
    
    public void setMonth(int month)
    {
        this.month = month;
    }
    
    public int getDay()
    {
        return day;
    }
    
    public void setDay(int day)
    {
        this.day = day;
    }
    
    public int getHour()
    {
        return hour;
    }
    
    public void setHour(int hour)
    {
        this.hour = hour;
    }
    
    public int getQueryEnergyType()
    {
        return queryEnergyType;
    }
    
    public void setQueryEnergyType(int queryEnergyType)
    {
        this.queryEnergyType = queryEnergyType;
    }
    
    public int getQueryTimeType()
    {
        return queryTimeType;
    }
    
    public void setQueryTimeType(int queryTimeType)
    {
        this.queryTimeType = queryTimeType;
    }
    
    public int getDeleteType()
    {
        return deleteType;
    }
    
    public void setDeleteType(int deleteType)
    {
        this.deleteType = deleteType;
    }
    
    public String getOrg_name()
    {
        return org_name;
    }
    
    public void setOrg_name(String org_name)
    {
        this.org_name = org_name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getTotalRoom()
    {
        return totalRoom;
    }
    
    public void setTotalRoom(int totalRoom)
    {
        this.totalRoom = totalRoom;
    }
    
    public String getWater_seat()
    {
        return water_seat;
    }
    
    public void setWater_seat(String water_seat)
    {
        this.water_seat = water_seat;
    }
    
    public String getWater_floor()
    {
        return water_floor;
    }
    
    public void setWater_floor(String water_floor)
    {
        this.water_floor = water_floor;
    }
    
    public String getFlow_seat()
    {
        return flow_seat;
    }
    
    public void setFlow_seat(String flow_seat)
    {
        this.flow_seat = flow_seat;
    }
    
    public String getFlow_floor()
    {
        return flow_floor;
    }
    
    public void setFlow_floor(String flow_floor)
    {
        this.flow_floor = flow_floor;
    }
    
    public Double getMeasure()
    {
        return measure == null ? 0 : measure;
    }
    
    public void setMeasure(Double measure)
    {
        this.measure = measure;
    }
    
    public Integer getMeasure_flow()
    {
        return measure_flow == null ? 0 : measure_flow;
    }
    
    public void setMeasure_flow(Integer measure_flow)
    {
        this.measure_flow = measure_flow;
    }
    
    public Double getMeasure_heat()
    {
        return measure_heat == null ? 0 : measure_heat;
    }
    
    public void setMeasure_heat(Double measure_heat)
    {
        this.measure_heat = measure_heat;
    }
    
}
