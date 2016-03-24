package com.cosmosource.app.port.model;

public class WisdomNj {
	
	//********************************公用属性**********************************
	/**
	 * 操作日期
	 */
	private String tStamp;
	/**
	 * 结果
	 */
	private String statecode;

	//---------------------------------------------------------------------------
	//*********************************周边环境信息*******************************
	//---------------------------------------------------------------------------
	/**
	 * 区域Id
	 */
	private String stationId;
	/**
	 * 区域
	 */
	private String stationName;
	/**
	 * 统计日期
	 */
	private String tDate;
	/**
	 * 当时温度
	 */
	private String temperature;
	/**
	 * 湿度
	 */
	private String rainfall;
	/**
	 * 能见度
	 */
	private String visibility;
	/**
	 * 风向
	 */
	private String windDirection;
	/**
	 * 风向名称
	 */
	private String windDirectionName;
	/**
	 * 风速
	 */
	private String windSpeed;
	/**
	 *  co2
	 */
	private String co2;
	
	//---------------------------------------------------------------------------
	//*********************************周边路况信息********************************
	//---------------------------------------------------------------------------
	/**
	 * 路段ID
	 */
	private String roadId;
	/**
	 * 基站对ID
	 */
	private String stationPairId;
	/**
	 * 采样时段(1分钟，0,1,..59)
	 */
	private String intervalTime;
	/**
	 * 采样时间
	 */
	private String sampTime;
	/**
	 * 路段平均速度
	 */
	private String roadAvgSpd;
	/**
	 * 路段平均时间
	 */
	private String roadAvgTime;
	/**
	 * 采样数量
	 */
	private String sampNum;

	/**
	 * 实时路况状态(A:拥堵 B缓慢: C:畅通)
	 */
	private String roadCondition;
	
	//---------------------------------------------------------------------------
	//*********************************周边天气信息********************************
	//---------------------------------------------------------------------------
	
	 /**
	 * 基站号
	 */
	private String stationNo;
	/**
	 * 预报时效
	 */
	private String tempo;
	/**
	 * 天气现象
	 */
	private String weather;
	/**
	 * 天气现象
	 */
	private String weatherName;
	/**
	 * 12小时累积降水量
	 */
	private String rain12;
	/**
	 * 24小时累积降水量
	 */
	private String rain24;
	/**
	 * 当时风速
	 */
	private String windSpeedName;
	/**
	 * 24小时最大相对湿度
	 */
	private String temperatureMin;
	/**
	 * 24小时最小相对湿度
	 */
	private String temperatureMax;
	
	//---------------------------------------------------------------------------
	//*********************************周边视频信息********************************
	//---------------------------------------------------------------------------

	/**
	 * 服务ip
	 */
	private String servIp;
	/**
	 * 服务端口
	 */
	private String servPort;
	/**
	 * 通道号码
	 */
	private String tongdao;
	/**
	 * 用户名
	 */
	private String admin;
	/**
	 * 密码
	 */
	private String pass;
	/**
	 * 
	 */
	private String creamId;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 广播的IP
	 */
	private String radioIp;
	/**
	 * 广播的端口
	 */
	private String radioPort;
	public String gettStamp() {
		return tStamp;
	}
	public void settStamp(String tStamp) {
		this.tStamp = tStamp;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String gettDate() {
		return tDate;
	}
	public void settDate(String tDate) {
		this.tDate = tDate;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getRainfall() {
		return rainfall;
	}
	public void setRainfall(String rainfall) {
		this.rainfall = rainfall;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getWindDirectionName() {
		return windDirectionName;
	}
	public void setWindDirectionName(String windDirectionName) {
		this.windDirectionName = windDirectionName;
	}
	public String getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	public String getRoadId() {
		return roadId;
	}
	public void setRoadId(String roadId) {
		this.roadId = roadId;
	}
	public String getStationPairId() {
		return stationPairId;
	}
	public void setStationPairId(String stationPairId) {
		this.stationPairId = stationPairId;
	}
	public String getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}
	public String getSampTime() {
		return sampTime;
	}
	public void setSampTime(String sampTime) {
		this.sampTime = sampTime;
	}
	public String getRoadAvgSpd() {
		return roadAvgSpd;
	}
	public void setRoadAvgSpd(String roadAvgSpd) {
		this.roadAvgSpd = roadAvgSpd;
	}
	public String getRoadAvgTime() {
		return roadAvgTime;
	}
	public void setRoadAvgTime(String roadAvgTime) {
		this.roadAvgTime = roadAvgTime;
	}
	public String getSampNum() {
		return sampNum;
	}
	public void setSampNum(String sampNum) {
		this.sampNum = sampNum;
	}
	public String getRoadCondition() {
		return roadCondition;
	}
	public void setRoadCondition(String roadCondition) {
		this.roadCondition = roadCondition;
	}
	public String getStationNo() {
		return stationNo;
	}
	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}
	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWeatherName() {
		return weatherName;
	}
	public void setWeatherName(String weatherName) {
		this.weatherName = weatherName;
	}
	public String getRain12() {
		return rain12;
	}
	public void setRain12(String rain12) {
		this.rain12 = rain12;
	}
	public String getRain24() {
		return rain24;
	}
	public void setRain24(String rain24) {
		this.rain24 = rain24;
	}
	public String getWindSpeedName() {
		return windSpeedName;
	}
	public void setWindSpeedName(String windSpeedName) {
		this.windSpeedName = windSpeedName;
	}
	public String getTemperatureMin() {
		return temperatureMin;
	}
	public void setTemperatureMin(String temperatureMin) {
		this.temperatureMin = temperatureMin;
	}
	public String getTemperatureMax() {
		return temperatureMax;
	}
	public void setTemperatureMax(String temperatureMax) {
		this.temperatureMax = temperatureMax;
	}
	public String getServIp() {
		return servIp;
	}
	public void setServIp(String servIp) {
		this.servIp = servIp;
	}
	public String getServPort() {
		return servPort;
	}
	public void setServPort(String servPort) {
		this.servPort = servPort;
	}
	public String getTongdao() {
		return tongdao;
	}
	public void setTongdao(String tongdao) {
		this.tongdao = tongdao;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getCreamId() {
		return creamId;
	}
	public void setCreamId(String creamId) {
		this.creamId = creamId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRadioIp() {
		return radioIp;
	}
	public void setRadioIp(String radioIp) {
		this.radioIp = radioIp;
	}
	public String getRadioPort() {
		return radioPort;
	}
	public void setRadioPort(String radioPort) {
		this.radioPort = radioPort;
	}
	public String getStatecode() {
		return statecode;
	}
	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}
	public String getCo2() {
		return co2;
	}
	public void setCo2(String co2) {
		this.co2 = co2;
	}
	
	
	
	
}
