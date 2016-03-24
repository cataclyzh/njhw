function inits(){
	$.ajax({
		dataType:'script',
		scriptCharset:'gb2312',
		url:'http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&day=3&city=南京&dfc=3',
		success:function(){
			//注视掉的代码都是新版页面上没有用到的，如需要用，可以在页面上添加该功能项的ID
	        var date = new Date();
	        var weeks = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	        var week0 = weeks[date.getDay()];// 获取星期
			document.getElementById("weather_img").src = dis_img(window.SWther.w["南京"][0].s1, 'newWea');// 今天天气图标
			document.getElementById("weather_img").title = window.SWther.w["南京"][0].s1;
	        document.getElementById("today_max").innerHTML = window.SWther.w["南京"][0].t1;// 今日最高温度
	        document.getElementById("today_min").innerHTML = window.SWther.w["南京"][0].t2;// 今日最低温度
			document.getElementById("now_date").innerHTML = window.SWther.add["now"].split(" ")[0];
	        document.getElementById("today_weather").innerHTML = window.SWther.w["南京"][0].s1;// 今日天气
			document.getElementById("today_fengxiang").innerHTML = window.SWther.w["南京"][0].d1;// 今日风向
			document.getElementById("today_fengji").innerHTML = window.SWther.w["南京"][0].p1;// 今日风级
	        document.getElementById("now_week").innerHTML = week0;
	        
	        for(var i=1; i<4; i++){
	        	$(".week"+i).html(weeks[((date.getDay()+i)%7)]);
		        $(".weather_img"+i).attr('src', dis_img(window.SWther.w["南京"][i].s1, 'newWea_small'));
		        $(".temperature"+i).html(window.SWther.w["南京"][i].t1 + '~' + window.SWther.w["南京"][i].t2 + '℃');
		        $(".weather_txt"+i+"_top").html(window.SWther.w["南京"][i].s1);
		        $(".weather_txt"+i+"_down").html(window.SWther.w["南京"][i].d1 + window.SWther.w["南京"][i].p1 + '级');
	        }
	        
	        var d=new Date();
			var yy = d.getFullYear();
			var mm = d.getMonth() + 1;
			var dd = d.getDate();
	        $(".min_title").append(" " + showCal1(d));
	        $(".min_title").append(" " + getTerm(yy,mm,dd));
	        
	        for(var i=1; i<4; i++){
	        	append_jieqi(new Date(d.getTime() + 1000*3600*24*i), i);
	        }
		}
	});
}

function append_jieqi(d, i){
	var yy = d.getFullYear();
    var mm = d.getMonth() + 1;
    var dd = d.getDate();
    $(".week"+i).append(" " + showCal1(d));
    $(".week"+i).append(" " + getTerm(yy,mm,dd));
}

var sTermMap = '0|gd4|wrn|1d98|1tuh|2akm|2rfn|38g9|3plp|46vz|4o9k|55px|5n73|64o5|6m37|73fd|7kna|81qe|8io7|8zgq|9g4b|9wnk|ad3g|ath2|'.split('|');
var sTermNames ="小寒|大寒|立春|雨水|惊蛰|春分|清明|谷雨|立夏|小满|芒种|夏至|小暑|大暑|立秋|处暑|白露|秋分|寒露|霜降|立冬|小雪|大雪|冬至".split('|');

for (var i = 24; i--;) sTermMap[i] = parseInt(sTermMap[i], 36);
function getTerm(y,m,d){
    function term(y,n){
        var d=new Date((31556925974.7*(y-1900)+sTermMap[n]*60000)+Date.UTC(1900,0,6,2,5) );
        return {m:d.getUTCMonth(),d:d.getUTCDate()};
    };
    for (var i=sTermNames.length,x;i--;){
        x=term(y,i);
        if(x.m==m-1&&x.d==d)return sTermNames[i];
    };
    return '';
};

// 天气图标
function dis_img(weather, forder){//显示不同天气对应的图片
		var route="images/weather/";//文件夹路径 //老版图片路径 images/weather
		//var forder='newWea'; //老版图片路径 qq
		var style_img=route+forder+"/s_13.png";
		if(weather.indexOf("多云")!==-1&&weather.indexOf("晴")!==-1){//多云转晴，以下类同 indexOf:包含字串
			style_img=route+forder+"/s_1.png";
		}else if(weather.indexOf("多云")!==-1&&weather.indexOf("阴")!==-1){
			style_img=route+forder+"/s_2.png";
		}else if(weather.indexOf("阴")!==-1&&weather.indexOf("雨")!==-1){
			style_img=route+forder+"/s_3.png";
		}else if(weather.indexOf("晴")!==-1&&weather.indexOf("雨")!==-1){
			style_img=route+forder+"/s_12.png";
		}else if(weather.indexOf("晴")!==-1&&weather.indexOf("雾")!==-1){
			style_img=route+forder+"/s_12.png";
		}else if(weather.indexOf("晴")!==-1){style_img=route+forder+"/s_13.png";}
		else if(weather.indexOf("多云")!==-1){style_img=route+forder+"/s_2.png";}
		else if(weather.indexOf("雷阵雨")!==-1){style_img=route+forder+"/s_7.png";}
		else if(weather.indexOf("阵雨")!==-1){style_img=route+forder+"/s_3.png";}
	    else if(weather.indexOf("小雨")!==-1){style_img=route+forder+"/s_3.png";}
		else if(weather.indexOf("中雨")!==-1){style_img=route+forder+"/s_4.png";}
		else if(weather.indexOf("大雨")!==-1){style_img=route+forder+"/s_5.png";}
		else if(weather.indexOf("暴雨")!==-1){style_img=route+forder+"/s_5.png";}
		else if(weather.indexOf("冰雹")!==-1){style_img=route+forder+"/s_6.png";}
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
