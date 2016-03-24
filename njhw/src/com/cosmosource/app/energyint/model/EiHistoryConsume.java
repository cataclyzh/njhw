package com.cosmosource.app.energyint.model;

/**
 * 历史能耗信息 <一句话功能简述> <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2013-7-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EiHistoryConsume {
    
    /**
     * 水
     */
    public static final  int consume_type_1 = 1;
    
    /**
     * 气
     */
    public static final  int consume_type_2 = 2;
    
    /**
     * 气
     */
    public static final  int consume_type_22 = 22;
    
    /**
     * 电
     */
    public static final  int consume_type_3 = 3;
    
    /**
     * 油
     */
    public static final  int consume_type_4 = 4;
    
	// --历史数据ID
	private int history_id;

	// --年份
	private int history_year;
	
	private int history_month;

	// --委办局ID
	private String org_name;

	// --能耗类型（1：水；2：气；3：电）
	private int consume_type;

	// --能耗
	private Double measure;

	// --如果是气耗，则存储热量
	private Double measure_heat;

	public int getHistory_id() {
		return history_id;
	}

	public void setHistory_id(int history_id) {
		this.history_id = history_id;
	}

	public int getHistory_year() {
		return history_year;
	}

	public void setHistory_year(int history_year) {
		this.history_year = history_year;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public Double getMeasure() {
		return measure == null ? 0 : measure;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	public Double getMeasure_heat() {
		return measure_heat == null ? 0 : measure_heat;
	}

	public void setMeasure_heat(Double measure_heat) {
		this.measure_heat = measure_heat;
	}

	public int getConsume_type() {
		return consume_type;
	}

	public void setConsume_type(int consume_type) {
		this.consume_type = consume_type;
	}

    public int getHistory_month()
    {
        return history_month;
    }

    public void setHistory_month(int historyMonth)
    {
        history_month = historyMonth;
    }
}
