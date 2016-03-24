window.onload = inits;
function inits()
{
	$.ajax({
	dataType:'script',
	scriptCharset:'gb2312',
	url:'http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&day=2&city=南京&dfc=3',
	success:function(){
        var date = new Date();
        var week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];// 获取星期
		document.getElementById("weather_img").src = dis_img(window.SWther.w["南京"][0].s1);// 今天天气图标
        document.getElementById("weather_img2").src = dis_img(window.SWther.w["南京"][1].s1);// 明天天气图标
		document.getElementById("weather_img").title = window.SWther.w["南京"][0].s1;
        document.getElementById("weather_img2").title = window.SWther.w["南京"][0].s2;
        document.getElementById("today_temperature").innerHTML = window.SWther.w["南京"][0].t2;// 今日温度
        document.getElementById("today_max").innerHTML = window.SWther.w["南京"][0].t1;// 今日最高温度
        document.getElementById("today_min").innerHTML = window.SWther.w["南京"][0].t2;// 今日最低温度
        document.getElementById("tomorrow_max").innerHTML = window.SWther.w["南京"][1].t1;// 明日最高温度
        document.getElementById("tomorrow_min").innerHTML = window.SWther.w["南京"][1].t2;// 明日最低温度
		document.getElementById("now_date").innerHTML = window.SWther.add["now"].split(" ")[0];
		document.getElementById("today_weather").innerHTML = window.SWther.w["南京"][0].s1;// 今日天气
        document.getElementById("tomorrow_weather").innerHTML = window.SWther.w["南京"][1].s1;// 明日天气
		document.getElementById("now_week").innerHTML = week;
		}
	});
}
// 天气图标
function dis_img(weather){//显示不同天气对应的图片
		var route="images/weather/";//文件夹路径
		var forder='qq';
		var style_img=route+forder+"/s_13.png";
		if(weather.indexOf("多云")!==-1||weather.indexOf("晴")!==-1){//多云转晴，以下类同 indexOf:包含字串
			style_img=route+forder+"/s_1.png";}
		else if(weather.indexOf("多云")!==-1&&weather.indexOf("阴")!==-1){
			style_img=route+forder+"/s_2.png";}
		else if(weather.indexOf("阴")!==-1&&weather.indexOf("雨")!==-1){
			style_img=route+forder+"/s_3.png";}
		else if(weather.indexOf("晴")!==-1&&weather.indexOf("雨")!==-1){
			style_img=route+forder+"/s_12.png";}
		else if(weather.indexOf("晴")!==-1&&weather.indexOf("雾")!==-1){
			style_img=route+forder+"/s_12.png";}
		else if(weather.indexOf("晴")!==-1){style_img=route+forder+"/s_13.png";}
		else if(weather.indexOf("多云")!==-1){style_img=route+forder+"/s_2.png";}
		else if(weather.indexOf("阵雨")!==-1){style_img=route+forder+"/s_3.png";}
	    else if(weather.indexOf("小雨")!==-1){style_img=route+forder+"/s_3.png";}
		else if(weather.indexOf("中雨")!==-1){style_img=route+forder+"/s_4.png";}
		else if(weather.indexOf("大雨")!==-1){style_img=route+forder+"/s_5.png";}
		else if(weather.indexOf("暴雨")!==-1){style_img=route+forder+"/s_5.png";}
		else if(weather.indexOf("冰雹")!==-1){style_img=route+forder+"/s_6.png";}
		else if(weather.indexOf("雷阵雨")!==-1){style_img=route+forder+"/s_7.png";}
		else if(weather.indexOf("小雪")!==-1){style_img=route+forder+"/s_8.png";}
		else if(weather.indexOf("中雪")!==-1){style_img=route+forder+"/s_9.png";}
		else if(weather.indexOf("大雪")!==-1){style_img=route+forder+"/s_10.png";}
		else if(weather.indexOf("暴雪")!==-1){style_img=route+forder+"/s_10.png";}
		else if(weather.indexOf("扬沙")!==-1){style_img=route+forder+"/s_11.png";}
		else if(weather.indexOf("沙尘")!==-1){style_img=route+forder+"/s_11.png";}
		else if(weather.indexOf("雾")!==-1){style_img=route+forder+"/s_12.png";}
		else{style_img=route+forder+"/s_2.png";}
        
		return style_img;
}
