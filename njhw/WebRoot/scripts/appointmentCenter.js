window.onload = inits;
function inits()
{
	$.ajax({
	dataType:'script',
	scriptCharset:'gb2312',
	url:'http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&day=2&city=南京&dfc=3',
	success:function(){
		document.getElementById("weather_img").src = dis_img(window.SWther.w["南京"][0].s1);
		document.getElementById("weather_img").title = window.SWther.w["南京"][0].s1;
		document.getElementById("now_time").innerHTML = window.SWther.add["now"].split(" ")[0];
		document.getElementById("now_weather").innerHTML = window.SWther.w["南京"][0].s1;
		document.getElementById("now_temperature").innerHTML = window.SWther.w["南京"][0].t2 + "-" + window.SWther.w["南京"][0].t1 + "℃";
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
function hover(dom, num)
{
	if(num == 0)
		dom.style.background = "url('images/" + dom.className + "_hover.png') 0 0 no-repeat";
	else
		dom.style.background = "url('images/" + dom.className + ".png') 0 0 no-repeat";
}
function a_hover(dom, num)
{
	if(num == 0)
	{
		dom.getElementsByTagName("span")[0].style.visibility = "visible";
		dom.getElementsByTagName("span")[1].style.background = "url('images/span_a.png') 0 -50px repeat-x";
		dom.getElementsByTagName("span")[2].style.visibility = "visible";
	}
	else
	{
		dom.getElementsByTagName("span")[0].style.visibility = "hidden";
		dom.getElementsByTagName("span")[1].style.background = "";
		dom.getElementsByTagName("span")[2].style.visibility = "hidden";
	}
}