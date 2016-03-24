
//弹出对话框的url
var newUrl;
// ie6
var ie6=!-[1,]&&!window.XMLHttpRequest;
//屏幕分辨率的宽度
var screenWidth = window.screen.width;
function file(url, paras) 
{   
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");  
	var paraObj = {}  
	for (i = 0; j = paraString[i]; i++) 
	{  
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);  
	}  
	var returnValue = paraObj[paras.toLowerCase()];  
	if (typeof (returnValue) == "undefined") 
	{  
		return "";  
	}
	else
	{  
		return returnValue;  
	}  
} 
// 删除数组指定元素
Array.prototype.del=function(n) 
{
if(n<0)
	return this;
else
	return this.slice(0,n).concat(this.slice(n+1,this.length));
} 
// 删除节点
function removeNodeById(id) 
{
     var node = document.getElementById(id);
     if (node) 
	 {
         node.parentNode.removeChild(node);
     }
}
// 取消冒泡
function stopBubble(e) 
{ 
	if (navigator.userAgent.indexOf('Firefox') >= 0) 
	{
		 e.stopPropagation;
	}
	else 
	{
	 	window.event.cancelBubble = true; 
	} 
}
// 获取classname对象
function getElementsByClassName(className,tagName)
{   
	var ele = [],all = document.getElementsByTagName(tagName || "*");   
	for(var i = 0;i < all.length;i++)
	{   
		if(all[i].className == className)
		{   
		   ele[ele.length] = all[i];   
		}   
	}   
	return ele;   
}  
// 返回最终样式函数，兼容IE和DOM，设置参数：元素对象、样式特性    
function getDefaultStyle(obj, attribute)
{
 	return obj.currentStyle?obj.currentStyle[attribute]:document.defaultView.getComputedStyle(obj,false)[attribute];    
}
// 点击外部菜单消失
function memo_dis()
{	
	document.getElementById("btn_news").style.display = "none";
	document.getElementById("btn_news_main2").style.display = "none";
	document.getElementById("btn_news_main3").style.display = "none";
	// 多级导航
	var headerN = getElementsByClassName("headerN", "div"),
		btn_news_main_con = document.getElementById("btn_news_main").getElementsByTagName("li");
	for(var j = 0,max1 = headerN.length;j < max1;j++)
	{
		headerN[j].style.display = "none";
	}
	// 将开始菜单样式清空
	for(var i = 0,max2 = document.getElementById("btn_news_main").getElementsByTagName("li").length;i < max2;i++)
	{
		btn_news_main_con[i].getElementsByTagName("a")[0].style.color = "#5F5F5F";
	}
	document.getElementById("open_window").style.color = "#5F5F5F";
	memo_dis2();
}
// 导航失去焦点菜单消失
function memo_dis2()
{	
	document.getElementById("tip2").style.display = "none";
	document.getElementById("header2").style.display = "none";
	document.getElementById("header3").style.display = "none";
	document.getElementById('header3_iframe').style.display = "none";
	// 多级导航
	var headerN = getElementsByClassName("headerN", "div");
	for(var j = 0;j < headerN.length;j++)
	{
		headerN[j].style.display = "none";
	}
	// 将一级导航样式清空
	if(header_out_num == 0)
	{	
		if(header1_num == 1)
		{
			document.getElementById("tip2").style.display = "block";
			document.getElementById("header2").style.display = "block";
			return false;
		}
	}
	var header_main_con = document.getElementById("header_main").getElementsByTagName("a"),
		btn_news_main_con = document.getElementById("btn_news_main").getElementsByTagName("li");
	for(var i = 0,max3 = document.getElementById("header_main").getElementsByTagName("a").length;i < max3;i++)
	{
		header1_out(header_main_con[i])
	}
	// 将开始菜单样式清空
	for(var i = 0,max = document.getElementById("btn_news_main").getElementsByTagName("li").length;i < max;i++)
	{
		btn_news_main_con[i].getElementsByTagName("a")[0].style.color = "#5F5F5F";
	}
	document.getElementById("open_window").style.color = "#5F5F5F";
	lable_num = 0;
	header1_num = 0;
}
// 定义公共对象
var xmlDoc;
var xmlDoc2;
var user_date;
// 快速链接分辨率参数
var pic_size_num;
// 快速链接个数
var pic_num1;
// 定义标签页数组对象
var label_arr = [];
// 窗口改变大小时
function resize()
{
	if(getDefaultStyle(document.getElementById("user_mes"), "display") == "none")
	{
		var body_height = document.body.offsetHeight;
		var body_width = document.body.offsetWidth;
		document.getElementById("news_main1_left").style.height = (body_height*0.64) + "px";
		document.getElementById("news_main1_main").style.height = (body_height*0.64) + "px";
		document.getElementById("news_main1_right").style.height = (body_height*0.64) + "px";
		document.getElementById("news_main1_main").getElementsByTagName("iframe")[0].style.height = (body_height*0.64) + "px";
		// 获取快速背景图片宽度
		var imageE = document.getElementById("img1");
		imageSize = getImageSize(imageE);
		imgwidth = imageSize[0];
		imgheight = imageSize[1];
		img_change();
		// 小屏幕显示的快速个数
		if(body_width <= 1780)
		{
			pic_size1(pic_num1);
		}
		// 大屏幕显示快速个数
		if(body_width > 1780)
		{
			pic_size2(pic_num1);
		}
	}
	if(getDefaultStyle(document.getElementById("user_mes"), "display") == "block")
	{
		var body_height = document.body.offsetHeight;
		var body_width = document.body.offsetWidth;
		document.getElementById("bg_03").style.height = (body_height - 97) + "px";
		// 折叠菜单隐藏按钮位置
		document.getElementById("menu_lib").style.top = (body_height - 200)/2 + "px";
		document.getElementById("table_top_div1").style.width = (body_width - 110) + "px";
		main2_top_len = body_width - 110;
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			inits( - 20);
		else
			inits(0);
	}
} 
// 获取背景图片大小，用于设置快速链接间距
function getImageSize(imgE)
{
	var i = new Image(); //新建一个图片对象
	i.src = document.getElementById("img_select").src;
	//i.src = getDefaultStyle(imgE,"backgroundImage").replace(/url\((.*?)\)/ig,"$1").replace(/\"/g, "");
	return new Array(i.width,i.height);
} 
// 打开页面加载样式和js
window.onload = function ()
{
	document.getElementById("img_select").src = "images/shortcut/select.png";
	if(screenWidth > 1024)
	{
		document.getElementById("news").style.display = "block";
	}
	if(screenWidth <= 1024)
	{
		for(i = 6;i < 11;i++)
		{
			var btn_id = "footer_btn" + i;
			document.getElementById(btn_id).style.bottom = "90px";
			document.getElementById(btn_id).style.width = "12px";
			document.getElementById(btn_id).style.height = "12px";
		}
		// 更改显示窗口按钮提示信息
		document.getElementById("footer_btn5").title = "显示消息窗口";
		// 设置1024分辨率下图片切换按钮之间的位置
		document.getElementById("footer_btn6").style.right = "455px";
		document.getElementById("footer_btn7").style.right = "425px";
		document.getElementById("footer_btn8").style.right = "395px";
		document.getElementById("footer_btn9").style.right = "365px";
		document.getElementById("footer_btn10").style.right = "335px";
	}
	document.getElementById("news_main1_left").style.height = (document.body.offsetHeight*0.64) + "px";
	document.getElementById("news_main1_main").style.height = (document.body.offsetHeight*0.64) + "px";
	document.getElementById("news_main1_right").style.height = (document.body.offsetHeight*0.64) + "px";
	document.getElementById("bg_03").style.height = (document.body.offsetHeight - 97) + "px";
	lib_hidden();
	var url = 'Server/User.aspx?command=user_info' + '&t=' + Math.random();
	var xml = GetInfoFromServer(url);
	if(xml.indexOf("会话过期") != -1)
	{
		alert("会话过期");
		window.open('login.htm', '_self');
		return false;
	}
	xmlDoc = LoadXml(xml);
	if(xmlDoc == null)
	{
		alert(xml);
		return false;
	}
	document.getElementById("footer_user").getElementsByTagName("span")[1].innerHTML = xmlDoc.getElementsByTagName("display_name")[0].text;
	document.getElementById("user_name").getElementsByTagName("a")[0].innerHTML = "当前用户：" + xmlDoc.getElementsByTagName("display_name")[0].text;
	user_date = 'uid=' + (xmlDoc.getElementsByTagName("login_uid")[0].text).replace(/(\s*$)/g, "") + '&pwd_md5=' + xmlDoc.getElementsByTagName("login_pwd")[0].text;
	url = 'Server/GetMenu.aspx?'+user_date + '&command=main_menu&nodeid=-1' + '&t=' + Math.random();
	var xml = GetInfoFromServer(url);
	var xmlDoc1 = LoadXml(xml);
	if(xmlDoc1 == null)
	{
		alert(xml);
		return false;
	}
	document.getElementById("header_main").getElementsByTagName("ul")[0].innerHTML = "";
	var xml_len = xmlDoc1.getElementsByTagName("CategoryName").length;
	// 一级导航长度
	var header_len = 0
	// 一级导航最长宽度;
	var header_max = 0,
		header_ul = document.getElementById("header_main").getElementsByTagName("ul")[0];
		// 该数组用于存放一级导航文字额外宽(由于字体加粗可能导致的文字换行)
		header_arr = [];
	for(i = 0;i < xml_len;i++)
	{
		// 一级导航文字信息
		var header_text = unescape(xmlDoc1.getElementsByTagName("CategoryName")[i].text);
			// 文字的长度(多于11个字作加宽处理)
			header_text_len = header_text.length;
		header_arr[i] = ((header_text_len - 11) > 0)?(header_text_len - 9):0;
		header_ul.innerHTML += "<li class='li_dom'>"
			+ "<div class='dom_left' id='dom_left" 
			+ unescape(xmlDoc1.getElementsByTagName("CategoryID")[i].text) 
			+ "'></div><a  href='javascript:void(0)' onmouseover='header1_over(this)' onblur='memo_dis2()' onmouseout='header1_out(this)' title='"
			+ unescape(xmlDoc1.getElementsByTagName("CategoryName")[i].text) + "'"
			+ "onmousedown='header1_cik(this)'"
			+ "IsLink='" + unescape(xmlDoc1.getElementsByTagName("IsLink")[i].text) + "'"
			+ "id=" + unescape(xmlDoc1.getElementsByTagName("CategoryID")[i].text) + " hidefocus>"
			+ header_text
			+ "</a><div class='dom_right' id='dom_right" + unescape(xmlDoc1.getElementsByTagName("CategoryID")[i].text) + "'></div></li>"
		header_len += (header_ul.getElementsByTagName("li")[i].offsetWidth + 15);
		header_max = header_max > header_ul.getElementsByTagName("li")[i].offsetWidth?header_max:header_ul.getElementsByTagName("li")[i].offsetWidth;
	}
	// 设定一级导航每个文字链接宽度
	var header_ul_li = header_ul.getElementsByTagName("li");
	// 设定一级导航距右边的距离
	var header_arr_all = 0;
	for(var j = 0,max = header_ul.getElementsByTagName("li").length;j < max;j++)
	{
		header_ul_li[j].getElementsByTagName("a")[0].style.width = header_ul_li[j].offsetWidth + header_arr[j] + "px";
		header_arr_all += header_arr[j];
	}
	// 设定一级导航总体宽度
	document.getElementById("header").style.width = header_len  + 20 + header_arr_all + "px";
	//alert(header_arr);
	url = 'Server/GetMenu.aspx?command=shortcut_menu&'+user_date + '&t=' + Math.random();
	var xml = GetInfoFromServer(url);
	xmlDoc2 = LoadXml(xml);
	if(xmlDoc2 == null)
	{
		alert(xml);
		return false;
	}
	pic_num1 = xmlDoc2.getElementsByTagName("Ico").length;
	// 获取快速背景图片宽度
	var imageE = document.getElementById("img1");
    imageSize = getImageSize(imageE);
    imgwidth = imageSize[0];
    imgheight = imageSize[1];
	// 小屏幕显示的快速个数
	if(document.body.offsetWidth <= 1780)
	{
		pic_size1(pic_num1);
	}
	// 大屏幕显示快速个数
	if(document.body.offsetWidth > 1780)
	{
		pic_size2(pic_num1);
	}
	img_change();
	// 获取快速背景图片宽度;
	var max = document.getElementById("main_img").getElementsByTagName("img"),
		max2 = document.getElementById("main_img").getElementsByTagName("span"),
		 max_len = max.length;
    for(var i = 0;i <max_len;i++)
    {
		max[i].style.width = imgwidth;
		max[i].style.height = imgheight;
		if(ie6)
		{
			document.getElementById("img" +(i + 1) + "_div").style.width = imgwidth;
			document.getElementById("img" +(i + 1) + "_d").style.width = imgwidth;
			document.getElementById("img" +(i + 1) + "_div").style.height = imgheight;
			document.getElementById("img" +(i + 1) + "_d").style.height = imgheight;
		}
		max2[i].style.fontSize = imgwidth/140*17 + "px";
    }
	url = 'Server/GetOption.aspx?command=setconfig&key=Bulletin&'+ user_date + '&t=' + Math.random();
	var xml = GetInfoFromServer(url);
	if(xml != "")
	{
		if(xml.indexOf("?") < 0)
			xml = xml + "?" + user_date;
		else
			xml = xml + "&" + user_date;
	}
	if(screenWidth <= "1024")
	{
		newUrl =  xml + '&t=' + Math.random();
	}
	else
	{
		var news_main1_main = document.getElementById("news_main1_main").getElementsByTagName("iframe")[0];
		news_main1_main.src = xml +  '&t=' + Math.random();
		news_main1_main.style.height = (document.body.offsetHeight*0.64) + "px";
	}
	var url_menu = "Server/GetOption.aspx?command=setconfig&key=Info&" + user_date + '&t=' + Math.random();
	xml_menu = GetInfoFromServer(url_menu);
	if(ie6)
	{
		ie_png.fix('#header_left, #header_right, #header li');
	}
}
// 点击快捷方式图片跳转
function img_clk(ent)
{
	// 没有图标不跳转
	if(attr(ent, "title_txt") == "")
		return false;
	open_main2(ent, ent.id);
}
function img_clk2(ent)
{
	if(ie6)
	{
		var img_ie6 = document.getElementById(ent).getElementsByTagName("img")[0];
		// 没有图标不跳转
		if(attr(img_ie6, "title_txt") == "")
			return false;
		open_main2(img_ie6, img_ie6.id);
	}
}
// 设置右侧边栏宽度
function inits(num)
{
	var menu_len = 0;
	var evt2 = document.getElementById("out").getElementsByTagName("ul");
	for(var j = 0,max = evt2.length;j < max;j++)
	{
		if(evt2[j].id.indexOf("menu_") != -1)
			menu_len++;
	}
	if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
	{
		document.getElementById("out").style.height = document.getElementById("table_body").offsetHeight - 10 + "px";
		document.getElementById("out_push").style.height = document.getElementById("table_body").offsetHeight - 10 + "px";
		document.getElementById("out_push").style.width = "5px";
	}
	else
	{
		document.getElementById("out").style.height = document.getElementById("table_body").offsetHeight + "px";
		document.getElementById("out_push").style.height = document.getElementById("table_body").offsetHeight + "px";
		document.getElementById("out_push").style.width = "5px";
	}
	var height = document.getElementById("table_body").offsetHeight + num - (evt2.length - menu_len)*34 - 10;
	height = height + "px";
	height2 = (document.body.offsetHeight - 200)/2 + "px";
	for(var i = 0,max = evt2.length;i < max;i++)
	{
		evt2[i].style.height = height;
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			evt2[i].getElementsByTagName("iframe")[0].style.height = height.replace("px","") + "px";
		else
			evt2[i].getElementsByTagName("iframe")[0].style.height = height.replace("px","") -20 + "px";
	}
	document.getElementById("menu_lib").style.top = height2;
	document.getElementById("menu_lib_iframe").style.top = (document.body.offsetHeight - 200)/2 + 15 + "px";
	document.getElementById("out_push").style.top = "35px";
}
// 按钮颜色切换
function onchange_bg(event)
{
	var this_event = event.target?event.target:event.srcElement;
	var num = (this_event.className.charAt(10) - 2)*100;
	if(num == -200)
		num = 400;
	url = "url(images/main/bottom_btn.png)  -100px  -" + num + "px no-repeat";
	this_event.style.background = url;
}
// 按钮颜色切换
function outchange_bg(event)
{
	var this_event = event.target?event.target:event.srcElement;
	var num = this_event.className.charAt(10) - 2;
	if(num == -2)
		num = 4;
	url = "url(images/main/bottom_btn.png) 0 -" + num + "00px no-repeat";
	this_event.style.background = url;
}
// 开始按钮颜色切换
function openchange_on()
{
	document.getElementById("footer_btn1").style.background = "url(images/main/begin2.gif) 0 0 no-repeat";
}
// 开始按钮颜色切换
function openchange_out(event)
{
	document.getElementById("footer_btn1").style.background = "url(images/main/begin1.png) 0 0 no-repeat";
}
// 开始按钮点击切换
function openchange_click()
{
	if(attr(document.getElementById("btn_news_main"), "type") == "change")
	{
		if(getDefaultStyle(document.getElementById("btn_news"),'display') != "block")
			document.getElementById("btn_news").style.display = "block";
		else
		{
			document.getElementById("btn_news").style.display = "none";
			document.getElementById("btn_news_main2").style.display = "none";
			document.getElementById("btn_news_main3").style.display = "none";
		}
		return;
	}
	url = 'Server/GetMenu.aspx?command=begin_menu&p_id=-1&' + user_date + '&t=' + Math.random();
	var xml = GetInfoFromServer(url);
	var xmlDoc1 = LoadXml(xml);
	if(xmlDoc1 == null)
	{
		alert(xml);
		return false;
	}
	document.getElementById("btn_news_main").getElementsByTagName("ul")[0].innerHTML = "";
	attr(document.getElementById("btn_news_main"), "type", "change");
	for(var i = 0,max = xmlDoc1.getElementsByTagName("title_name").length;i < max;i++)
	{
		if(xmlDoc1.getElementsByTagName("Table")[i].getElementsByTagName("child")[0] && xmlDoc1.getElementsByTagName("Table")[i].getElementsByTagName("child")[0].text != "")
		{
			document.getElementById("btn_news_main").getElementsByTagName("ul")[0].innerHTML += "<li><a href='' onclick='return false' onmouseout='news_out(this)' onmouseover='news_on(this)' "
				+ "id=" + xmlDoc1.getElementsByTagName("id")[i].text + ">" 
				+ xmlDoc1.getElementsByTagName("title_name")[i].text.replace(/[ ]/g,"") 
				+ "<span>></span></a></li>";
		}
		else
		{
			document.getElementById("btn_news_main").getElementsByTagName("ul")[0].innerHTML += "<li><a href='' onclick='return false' onmouseout='news_out(this)' onmouseover='news_on(this)' "
				+ "id=" + xmlDoc1.getElementsByTagName("id")[i].text + ">" 
				+ xmlDoc1.getElementsByTagName("title_name")[i].text.replace(/[ ]/g,"") 
				+ "<span></span></a></li>";
		}
	}
	if(getDefaultStyle(document.getElementById("btn_news"),'display') != "block")
		document.getElementById("btn_news").style.display = "block";
	else
	{
		document.getElementById("btn_news").style.display = "none";
		document.getElementById("btn_news_main2").style.display = "none";
	}
}
// 开始按钮新闻二级菜单状态
function news_on(ent)
{
	document.getElementById("open_window").style.color = "#5F5F5F";
	document.getElementById("btn_news_main3").style.display = "none";
	for(var i = 0,max = document.getElementById("btn_news_main").getElementsByTagName("li");i < max.length;i++)
	{
		max[i].getElementsByTagName("a")[0].style.color = "#5F5F5F";
		max[i].getElementsByTagName("a")[0].style.fontWeight = 100;
	}
	ent.style.color = "#FF5900";
	for(var i = 0,max = document.getElementById("btn_news_main2_main").getElementsByTagName("ul");i < max.length;i++)
	{
		max[i].style.display = "none";
	}
	if(ent.getElementsByTagName("span")[0].innerHTML == "")
	{
		document.getElementById("btn_news_main2").style.display = "none";
		document.getElementById("btn_news_main2_main").innerHTML += "<ul "
			+ "id='btn_news_main2_" + ent.id + "'"
			+ "class='btn_news_main2_" + ent.id + "'"
			+ "></ul>"
		return false;
	}
	for(var i = 0,max = document.getElementById("btn_news_main2_main").getElementsByTagName("ul");i < max.length;i++)
	{
		var mes1 = "btn_news_main2_" + ent.id;
		if(mes1 == max[i].id)
		{
			if(document.getElementById("btn_news_main2_" + ent.id).getElementsByTagName("li").length == 0)
			{
				document.getElementById("btn_news_main2").style.display = "none";
				return false;
			}
			else
			{
				document.getElementById("btn_news_main2").style.display = "block";
				max[i].style.display = "block";
				var len = document.getElementById("btn_news").offsetHeight - document.getElementById("btn_news_main2").offsetHeight/2 - ent.parentNode.offsetTop - 15;
				document.getElementById("btn_news_main2").style.bottom = len + 'px';
				return false;
			}
		}
	}	
	var mes = "btn_news_main2_" + ent.id;
	document.getElementById("btn_news_main2_main").innerHTML += "<ul "
			+ "id='btn_news_main2_" + ent.id + "'"
			+ "class='btn_news_main2_" + ent.id + "'"
			+ "></ul>"
	document.getElementById("btn_news_main2_load").style.display = "block";
	document.getElementById("btn_news_main2").style.display = "block";
	var len = document.getElementById("btn_news").offsetHeight - document.getElementById("btn_news_main2").offsetHeight/2 - ent.parentNode.offsetTop - 40;
	document.getElementById("btn_news_main2").style.bottom = len + 'px';
	url = 'Server/GetMenu.aspx?command=begin_menu&p_id=' + ent.id + '&' + user_date + '&t=' + Math.random();
	// 异步加载二级新闻菜单
	var xml = GetInfoFromServerAsyn(url, callback);
	function callback(xml)
	{
		var xmlDoc1 = LoadXml(xml);
		if(xmlDoc1 == null)
		{
			alert(xml);
			return false;
		}
		document.getElementById("btn_news_main2_load").style.display = "none";
		for(var i = 0,max = xmlDoc1.getElementsByTagName("title_name");i < max.length; i++)
		{
			// 对开始菜单二级窗口作截字处理，并且去除前后空格
			var title_name = max[i].text;
			var news_val = (title_name.replace(/(^\s*)|(\s*$)/g, "")).length > 7 ? (title_name.replace(/(^\s*)|(\s*$)/g, "")).substr(0,6) + "..":(title_name.replace(/(^\s*)|(\s*$)/g, ""));
			var nodeid = "";
			if(xmlDoc1.getElementsByTagName("nodeid")[i] != null)
				nodeid = xmlDoc1.getElementsByTagName("nodeid")[i].text;
			document.getElementById(mes).innerHTML += "<li uid='" 
				+ xmlDoc1.getElementsByTagName("id")[i].text + "' class='btn2_" + xmlDoc1.getElementsByTagName("id")[i].text + "'"
				+ " id='" + nodeid + "'"
				+ " onmousedown='open_main2(this, this.id)' onmouseover='newslist_change(this,0)' " 
				+ " title='"+ xmlDoc1.getElementsByTagName("title_name")[i].text.replace(/(^\s*)|(\s*$)/g, "")  + "'"
				+ " onmouseout='newslist_change(this,1)'><a href='' onmousedown='return false'>" + news_val + "</a></li>"
		}
		if(document.getElementById("btn_news_main2_" + ent.id).getElementsByTagName("li").length == 0)
		{
			document.getElementById("btn_news_main2").style.display = "none";
			return false;
		}
		var len = document.getElementById("btn_news").offsetHeight - document.getElementById("btn_news_main2").offsetHeight/2 - ent.parentNode.offsetTop - 40;
		document.getElementById("btn_news_main2").style.bottom = len + 'px';
		document.getElementById("btn_news_main2").style.display = "block";
	}
}
// 开始按钮新闻二级菜单状态
function news_out(ent)
{
	var len = document.getElementById("btn_news").offsetHeight - document.getElementById("btn_news_main2").offsetHeight/2 - ent.parentNode.offsetTop - 15;
	var len2 = getDefaultStyle(document.getElementById("btn_news_main2"),'bottom').replace("px","")
	if(parseInt(len2) == parseInt(len) && getDefaultStyle(document.getElementById("btn_news_main2"),'display') != "none")
		return ;
	ent.style.color = "#5F5F5F";					
	var len = document.getElementById("btn_news_main").getElementsByTagName("span").length;
}
// 整理菜单
function news_on3(ent, which)
{
	if(which == 0)
	{
		ent.style.color = "#FF5900";
		document.getElementById("btn_news_main2").style.display = "none";
		document.getElementById("btn_news_main3").style.display = "none";
		for(var i = 0,max = document.getElementById("btn_news_main").getElementsByTagName("li");i < max.length;i++)
		{
			// 获取标签页对象
			var dom1 = max[i].getElementsByTagName("a")[0];
			dom1.style.color = "#5F5F5F";
			dom1.style.fontWeight = 100;
		}
		document.getElementById("open_window").style.color = "#5F5F5F";
	}
	if(which == 1)
	{
		ent.style.color = "#5F5F5F";
	}
}
// 打开收藏编辑页面
function open_menu()
{
	// 加载二级开始菜单
	for(var j = 0,max = document.getElementById("btn_news_main").getElementsByTagName("a");j < max.length;j++)
	{
		news_on(max[j]);
		memo_dis();
	}
	var url = "Support/edit_begin_menu.htm" + "?temp=" + Math.random() + "&" + user_date + '&t=' + Math.random();
	window.showModalDialog(url, this,"dialogWidth:350px;dialogHeight:400px;help:no;scrollbar:no")
}
//打开新建收藏页面
function add_begin()
{
	var label_01_con_len = label_arr.length;
	for(i = 0;i < label_01_con_len;i++)
	{
		var bg_pos;
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			bg_pos = document.defaultView.getComputedStyle(label_arr[i].getElementsByTagName("p")[1])['backgroundPosition'].replace("0px ","");
		if(navigator.userAgent.indexOf("MSIE") > 0)
			bg_pos = label_arr[i].getElementsByTagName("p")[1].currentStyle['backgroundPositionY'];
		if(bg_pos == "435px")
		{
			var node = label_arr[i].id.replace("label_","");
			if(node.substring(0,3) == "img" || node == "help" || node == "UserInfo" || node == "Discuss")
			{
				alert("快速菜单不能收藏")
				return false;
			}
			var value = label_arr[i].getElementsByTagName("p")[1].getElementsByTagName("span")[0].innerHTML;
			break;
		}
	}
	// 加载开始内容
	openchange_click();
	for(j = 0;j < document.getElementById("btn_news_main").getElementsByTagName("a").length;j++)
	{
		news_on(document.getElementById("btn_news_main").getElementsByTagName("a")[j]);
	}
	// 传入nodeid和标签名称
	url = "Support/add_begin_menu.htm" + "?temp=" + Math.random() + "&nodeid=" + node + "&value=" + escape(value) + "&" + user_date + '&t=' + Math.random();
	window.showModalDialog(url, this,"dialogWidth:450px;dialogHeight:200px;help:no;scrollbar:no");
}
//打开窗口样式
function news_on2(ent)
{
	ent.style.color = "#FF5900";
	document.getElementById("btn_news_main2").style.display = "none";
	var btn_con = document.getElementById("btn_news_main").getElementsByTagName("li");
	var btn_len = btn_con.length;
	for(i = 0;i < btn_len;i++)
	{
		var btn_con_a = btn_con[i].getElementsByTagName("a")[0];
		btn_con_a.style.color = "#5F5F5F";
		btn_con_a.style.fontWeight = 100;
	}
	// 没有标签时不打开窗口页;
	if(document.getElementById('table_body1').getElementsByTagName("iframe").length == 0)
		return false;
	document.getElementById("btn_news_main3").style.display = "block";
	document.getElementById("btn_news_main3_main").getElementsByTagName("ul")[0].innerHTML = "";
	var btn_new3_con = "";
	var label_01_len = label_arr.length;
	for(i = 0;i < label_01_len;i++)
	{
		var ent_main2 = "btn_news_" + label_arr[i].id.replace("label_","");
		var con = label_arr[i].getElementsByTagName("p")[1].getElementsByTagName("span")[0].innerHTML;
		// 打开窗口中标签名过长作截字处理
		var open_val = con.length > 7?con.substr(0,6) + "..":con;
		btn_new3_con += "<li onmouseover='newslist_change(this,0)' onmouseout='newslist_change(this,1)' "
			+ "title='" + con + "'"
			+  "><a href='javascript:void(0)' "
			+ "id='" + ent_main2 + "'"
			+ "onmousedown='btn_open_main2(this.id)'>"
			+ open_val + "</a></li>"
	}
	document.getElementById("btn_news_main3_main").getElementsByTagName("ul")[0].innerHTML += btn_new3_con;
	// 设置打开窗口悬浮div的位置
	var len = document.getElementById("btn_news").offsetHeight - document.getElementById("btn_news_main3").offsetHeight/2 - ent.offsetTop - 5;
	// 悬浮div的位置不能在底部撑开，则直接置底
	len = (len < 0)?0:len;
	document.getElementById("btn_news_main3").style.bottom = len + 'px';
}
// 打开窗口划过状态
function news_out2(ent)
{
	if(getDefaultStyle(document.getElementById("btn_news_main3"),"display") != "none")
		return false;
	ent.style.color = "#5F5F5F";
}
function btn_open_main2(ent)
{
	var top_id = ent.replace("btn_news_","");
	open_main2(document.getElementById(top_id), top_id);
}
// 弹出新闻关闭按钮划过状态
function close_news_on(ent, which)
{
	if(which == 0)
		ent.style.background = "url(images/main/close_btn_news.png) 0 -100px no-repeat";
	if(which == 1)
		ent.style.background = "url(images/main/close_btn_news.png) 0 0 no-repeat";
}

// 弹出新闻关闭按钮效果
function close_news()
{
	document.getElementById("news").style.display = "none";
	document.getElementById("footer_btn5").title = "显示消息窗口";
}
// 弹出新闻切换按钮效果
function close_news2()
{
	if(screenWidth <= "1024")
	{
		window.showModalDialog(newUrl, this,"dialogWidth:395px;dialogHeight:420px;help:no;scrollbar:no");
	}
	else
	{
	 	if(getDefaultStyle(document.getElementById("news"),"display") == "none")
		{
			document.getElementById("news").style.display = "block";
			document.getElementById("footer_btn5").title = "隐藏消息窗口";
		}
		else
		{
			document.getElementById("news").style.display = "none";
			document.getElementById("footer_btn5").title = "显示消息窗口";
		}
	}
}
// 一级导航滑过状态
function header1_over(this_event)
{
	var this_div_ent = this_event.parentNode.getElementsByTagName("div");
	if(!ie6)
	{
		this_div_ent[0].style.background = "url(images/main/header1_hover.png) 0 2px no-repeat";
		this_div_ent[1].style.background = "url(images/main/header1_hover.png) 0 -198px no-repeat";
		this_event.style.background = "url(images/main/header1_hover.png) 0 -98px repeat-x";
	}
	this_event.style.fontWeight = "bold";
	if(header1_num == 1)
	{
		header1_cik(this_event);
	}
}
// 一级导航滑过状态
function header1_out(this_event)
{
	lable_num = 0;
	var ent_half = parseInt(this_event.style.width.replace("px",""))/2;
	if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		var left_len = this_event.parentNode.offsetLeft + ent_half + 305;
	else
	{
		if(ie6)
			var left_len = this_event.parentNode.offsetLeft  + ent_half;
		else
			var left_len = document.getElementById("header").offsetLeft + this_event.parentNode.offsetLeft + + ent_half + 315;
	}
	if(document.getElementById("tip2").style.left == (left_len + "px") && getDefaultStyle(document.getElementById("tip2"),'display') != "none")
		return false;
	this_event.parentNode.getElementsByTagName("div")[0].style.background = "";
	this_event.parentNode.getElementsByTagName("div")[1].style.background = "";
	this_event.style.fontWeight = 100;
	this_event.style.background = "";
}
// 点击划过记住状态参数
var header1_num = 0;
// 点击产生标签状态参数
var lable_num = 0;
// 一级导航点击状态
var header_out_num;
function header_out()
{
	header_out_num = 1;
}
function header_over()
{
	header_out_num = 0;
}
var header_over_ent;
function header1_cik(ent)
{
	var new_id = "header2_" + ent.id;
	header_over_ent = ent;
	// 隐藏开始菜单
	document.getElementById("btn_news").style.display = "none";
	document.getElementById("btn_news_main2").style.display = "none";
	document.getElementById("btn_news_main3").style.display = "none";
	// 多级导航隐藏
	var headerN = getElementsByClassName("headerN", "div");
	var max = headerN.length;
	for(var j = max - 1;j >= 0;j--)
	{
		headerN[j].style.display = "none";
	}
	lable_num++;
	var tips2 = document.getElementById("tip2");
	var header2_dom = document.getElementById("header2");
	if(lable_num == 2)
	{
		tips2.style.display = "none";
		header2_dom.style.display = "none";
		if(attr(ent,"IsLink") == 1)
		{
			open_main2(ent, ent.id);
		}
		lable_num = 0;
		header1_num = 0;
		return false;
	}
	header1_num = 1;
	document.getElementById("header3").style.display = "none";
	document.getElementById('header3_iframe').height = 0;
	var max = getElementsByClassName("dom_left","div"),
		left_len = max.length;
		ent_parent = ent.parentNode;
	var ent_left = ent_parent.offsetLeft;
	for(var i = left_len - 1;i >= 0;i--)
	{
		if(max[i].parentNode.offsetLeft == ent_left)
			continue;
		var dom_left_a = max[i].parentNode.getElementsByTagName("a")[0];
		 max[i].parentNode.getElementsByTagName("div")[1].style.background = "";
		dom_left_a.style.fontWeight = 100;
		dom_left_a.style.background = "";
		max[i].style.background = "";
	}
	tips2.style.display = "block";
	var evt2 = document.getElementById("header2_main").getElementsByTagName("a");
	var evt2_len = evt2.length;
	for(var i = evt2_len - 1;i >= 0;i--)
	{
		evt2[i].style.color = "#000";
	}
	// 文字链接一半宽度，用于箭头定位
	var ent_half = parseInt(ent.style.width.replace("px",""))/2;
	if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		var left_len = ent_parent.offsetLeft + ent_half + 305;
	else
	{
		if(ie6)
			var left_len = ent_parent.offsetLeft  + ent_half;
		else
			var left_len = document.getElementById("header").offsetLeft + ent_parent.offsetLeft + ent_half + 315;
	}
	tips2.style.left = left_len + "px";
	var header2_len = header2_dom.offsetWidth/2;
	header2_dom.style.display = "block";
	var max = document.getElementById("header2_main").getElementsByTagName("ul");
	var max_len = max.length;
	for(var i =  max_len - 1;i >= 0;i--)
	{
		max[i].style.display = "none";
	}
	if(document.getElementById(new_id)&&document.getElementById(new_id).getElementsByTagName("li"))
		var header2_li = document.getElementById(new_id).getElementsByTagName("li");
	var header2_left_top = document.getElementById("header2_left"),
		header2_right_top = document.getElementById("header2_right");
	for(var i =  max_len - 1;i >= 0;i--)
	{
		var mes = "header2_" + ent.id;
		if(mes == max[i].id)
		{
			if(document.getElementById(mes).innerHTML == "")
			{
				tips2.style.display = "none";
				header2_dom.style.display = "none";
				return false;
			}
			else
			{
				tips2.style.display = "block";
				header2_dom.style.display = "block";
				max[i].style.display = "block";
				// 将二级导航left,right样式清空
				header2_dom.style.left = "auto";
				header2_dom.style.right = "auto";
				header2_dom.style.width = "";
				var header2_li_len = 0;
				for(var m = 0;m < header2_li.length;m++)
				{
					header2_li_len += header2_li[m].clientWidth;
				}
				header2_len = header2_dom.offsetWidth/2;
				if((left_len + header2_len) > (document.body.clientWidth - 10))
				{
					header2_dom.style.right = 0;
					header2_dom.style.left = "auto";
					header2_dom.style.width = header2_li_len + 40 + "px";
					// 折行时递归调用
					if(header2_left_top.offsetTop != header2_right_top.offsetTop || header2_dom.offsetHeight > 30)
						header_add();
					return false;
				}
				header2_dom.style.right = "auto";
				var left_len = (left_len - header2_len + 5) < 0?0:(left_len - header2_len + 5);
				header2_dom.style.left = left_len + "px";
				header2_dom.style.width = header2_li_len + 40 + "px";
				// 折行时递归调用
				if(header2_left_top.offsetTop != header2_right_top.offsetTop || header2_dom.offsetHeight > 30)
					header_add();
				return false;
			}
		}
	}
	// 二级导航请等待提示
	header2_dom.style.left = left_len -50 + "px";
	document.getElementById("header2_load").style.display = "block";
	header2_dom.style.width = "120px";
	header2_dom.style.display = "block";
	document.getElementById("header2_main").innerHTML += "<ul id=header2_" + ent.id + "></ul>"
	var url = 'Server/GetMenu.aspx?'+user_date + '&command=main_menu&nodeid=' + ent.id + '&t=' + Math.random();// 获取第二级主菜单
	//异步加载二级导航
	GetInfoFromServerAsyn(url,callback);
	function callback(xml)
	{
		var new_id = "header2_" + ent.id;
		var xmlDoc1 = LoadXml(xml);
		if(xmlDoc1 == null)
		{
			alert(xml);
			return false;
		}
		var xmld_len = xmlDoc1.getElementsByTagName("CategoryName").length,
			header2_ul_dom = "";
		for(var i = 0;i < xmld_len;i++)
		{
			var header2_id = unescape(xmlDoc1.getElementsByTagName("CategoryID")[i].text);
			if(i == xmlDoc1.getElementsByTagName("CategoryName").length - 1)
			{
				header2_ul_dom += "<li><a  onmousedown='header2_cik(this);return false' "
					+ "title='" + unescape(xmlDoc1.getElementsByTagName("CategoryName")[i].text) + "'"
					+ "onmouseover='header2_over(this)' onmouseout='header2_out(this)' "
					+ "IsLink='" + unescape(xmlDoc1.getElementsByTagName("IsLink")[i].text) + "'"
					+ "id=" + header2_id + ">"
					+ unescape(xmlDoc1.getElementsByTagName("CategoryName")[i].text) + "</a></li>";
			}
			else
			{
				header2_ul_dom += "<li><a  onmousedown='header2_cik(this);return false' "
					+ "title='" + unescape(xmlDoc1.getElementsByTagName("CategoryName")[i].text) + "'"
					+ "onmouseover='header2_over(this)' onmouseout='header2_out(this)' "
					+ "IsLink='" + unescape(xmlDoc1.getElementsByTagName("IsLink")[i].text) + "'"
					+ "id=" + header2_id + ">" 
					+ unescape(xmlDoc1.getElementsByTagName("CategoryName")[i].text) + "</a><span>|</span></li>";
			}
		}
		document.getElementById(new_id).innerHTML += header2_ul_dom;
		document.getElementById("header2_load").style.display = "none";
		header2_dom.style.width = "";
		if(document.getElementById("header2_" + ent.id).getElementsByTagName("li").length == 0)
		{
			tips2.style.display = "none";
			header2_dom.style.display = "none";
			return false;
		}
		// 将二级导航left,right样式清空
		header2_dom.style.left = "";
		header2_dom.style.right = "";
		header2_dom.style.width = "";
		var top2_len = parseInt(getDefaultStyle(tips2,"left").replace("px",""));
		var left_len = (top2_len - header2_dom.offsetWidth/2 + 5) < 0?0:(top2_len - header2_dom.offsetWidth/2 + 5);
		header2_dom.style.left = left_len + "px";
		var new_id2 = "header2_" + header_over_ent.id;
		var header2_li = document.getElementById(new_id2).getElementsByTagName("li");
		var header2_li_len = 0;
		for(var m = 0;m < header2_li.length;m++)
		{
			header2_li_len += header2_li[m].clientWidth;
		}
		header2_dom.style.width = header2_li_len + 40 + "px";
		var header2_len = (header2_li_len + 40)/2;
		if((top2_len + header2_len) > (document.body.clientWidth - 10))
		{
			header2_dom.style.right = 0; 
			header2_dom.style.left = "auto";
		}
		// 折行时递归调用
		if(document.getElementById("header2_left").offsetTop != document.getElementById("header2_right").offsetTop || header2_dom.offsetHeight > 30)
			header_add();
	}
}
// 当一级二级导航折行时递归增加宽度
function header_add()
{
	document.getElementById("header2").style.width = parseInt(document.getElementById("header2").style.width.replace("px","")) + 20 + "px";
	if(document.getElementById("header2_left").offsetTop != document.getElementById("header2_right").offsetTop || document.getElementById("header2").offsetHeight > 30)
		header_add();
	else
		return false;
}
// 二级导航滑动状态
function header2_over(evt)
{
	lable2_num = 0;
	for(var i = 0,max = document.getElementById("header2_main").getElementsByTagName("li");i < max.length;i++)
	{
		max[i].getElementsByTagName("a")[0].style.color = "#000";
	}
	evt.style.color = "#fff"
	header2_cik(evt);
}
// 二级导航滑过状态
function header2_out(evt)
{
	// 区分ff和ie三级导航的位置
	if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		var left_len = document.getElementById("header2").offsetLeft + evt.parentNode.offsetLeft - 20;
	else
		var left_len = document.getElementById("header2").offsetLeft + evt.parentNode.offsetLeft;
	if(document.getElementById("header3").style.left == left_len + "px" && getDefaultStyle(document.getElementById("header3"),'display') != "none")
		return;
	evt.style.color = "#000";
}
// 二级导航状态参数
var lable2_num = 0;
// 二级导航点击状态
function header2_cik(ent)
{
	// 多级导航隐藏
	var headerN = getElementsByClassName("headerN","div");
	var header3_dom = document.getElementById("header3");
	var header3_ifr_dom = document.getElementById('header3_iframe');
	for(var j = 0;j < headerN.length;j++)
	{
		headerN[j].style.display = "none";
	}
	header3_dom.style.right = "";
	header3_ifr_dom.style.right = "";
	header3_dom.style.left = "";
	header3_ifr_dom.style.left = "";
	// 区分ff和ie三级导航的位置
	if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		var left_len = document.getElementById("header2").offsetLeft + ent.parentNode.offsetLeft - 20;
	else
		var left_len = document.getElementById("header2").offsetLeft + ent.parentNode.offsetLeft;
	if(left_len + 116 > document.body.offsetWidth)
	{
		header3_dom.style.right = 0;
		header3_ifr_dom.style.right = 0;
	}
	else
	{
		header3_dom.style.left = left_len + "px";
		header3_ifr_dom.style.left = left_len + "px";
	}
	document.getElementById('header3').style.display = "block";
	header3_ifr_dom.height = 0;
	if(getDefaultStyle(document.getElementById("bg_03"),"display") != "none")
		header3_ifr_dom.style.display = "block";
	if(getDefaultStyle(document.getElementById("bg_03"),"display") == "none")
		header3_ifr_dom.style.display = "none";
	lable2_num++;
	if(lable2_num == 2)
	{	
		memo_dis();
		header3_dom.style.display ="none";
		if(attr(ent,"IsLink") == 1)
		{	
			open_main2(ent, ent.id);
			lable2_num = 0;
		}
		return false;
	}
	for(var i = 0,max = document.getElementById("header3_main").getElementsByTagName("ul");i < max.length;i++)
	{
		if(max[i].id == "header3_load")
			continue;
		max[i].style.display = "none";
	}
	document.getElementById("header3_load").style.display = "block";
	for(var i = 0,max = document.getElementById("header3_main").getElementsByTagName("ul");i < max.length;i++)
	{
		var mes = "header3_" + ent.id;
		if(mes == max[i].id)
		{
			if(document.getElementById("header3_" + ent.id).getElementsByTagName("li").length == 0)
			{
				header3_dom.style.display = "none";
				header3_ifr_dom.height = 0;
				return false;
			}
			else
			{
				document.getElementById("header3_load").style.display = "none";
				header3_dom.style.display = "block";
				max[i].style.display = "block";
				header3_ifr_dom.height = header3_dom.offsetHeight - 15;
				return false;
			}
		}
	}
	document.getElementById("header3_main").innerHTML += "<ul id=header3_" + ent.id + "><ul>";
	if(navigator.userAgent.indexOf("MSIE") > 0)
		document.getElementById("header3_" + ent.id).style.height = 0;
	var url = 'Server/GetMenu.aspx?'+user_date + '&command=main_menu&nodeid=' + ent.id + '&t=' + Math.random();// 获取第二级主菜单
	//三级导航异步加载
	var xml = GetInfoFromServerAsyn(url, callback);
	function callback(xml)
	{
		var xmlDoc1 = LoadXml(xml);
		if(xmlDoc1 == null)
		{
			alert(xml);
			return false;
		}
		var header3_ul_con = "";
		for(i = 0;i < xmlDoc1.getElementsByTagName("CategoryName").length;i++)
		{
			// 获取三级导航名称
			var catename = xmlDoc1.getElementsByTagName("CategoryName")[i].text;
			var header3_val = unescape(catename).length > 7?unescape(catename).substr(0,6) + "...":unescape(catename);
			header3_ul_con += "<li><a href='javascript:void(0)' onmouseover='header3_over(this,0,0)' onmousedown='headerN_clik(this, this.id)' onmouseout='header3_over(this,1,0)' "
				+ "title='" + unescape(xmlDoc1.getElementsByTagName("CategoryName")[i].text) + "'"
				+ "IsLink='" + unescape(xmlDoc1.getElementsByTagName("IsLink")[i].text) + "'"
				+ "id=" + unescape(xmlDoc1.getElementsByTagName("CategoryID")[i].text) + ">" 
				+ header3_val + "</a></li>"
		}
		document.getElementById("header3_load").style.display = "none";
		document.getElementById("header3_" + ent.id).innerHTML += header3_ul_con;
		if(document.getElementById("tip2").style.display == "block")
			header3_dom.style.display = "block";
		header3_ifr_dom.height = document.getElementById("header3").offsetHeight - 15;
		if(document.getElementById("header3_" + ent.id).getElementsByTagName("li").length == 0)
		{
			document.getElementById("header3").style.display = "none";
			header3_ifr_dom.height = 0;
		}
	}
}
// 图片滑过效果
function img_over(cur, which)
{
	if(getDefaultStyle(cur.parentNode,"backgroundImage").indexOf("null.png") != -1)
		return;
	if(which == 0)
		cur.src = "images/shortcut/select.png";
	if(which == 1)
		cur.src = "images/main/lemo.png";
}
function img_over2(cur, which, cur2)
{
	if(document.getElementById(cur2).style.backgroundImage.indexOf("null.png") != -1)
		return;
	if(which == 0)
		document.getElementById(cur).style.display = "block";
	if(which == 1)
		document.getElementById(cur).style.display = "none";
}
// 右图标颜色切换
function left_change(ent,which)
{
	if(which == 0)
		ent.style.background = "url(images/main/left_right_hover.png) 0 -100px no-repeat";
	else
		ent.style.background = "url(images/main/left_right.png) 0 0 no-repeat";
}
// 右图标颜色切换
function right_change(ent,which)
{
	if(which == 0)
		ent.style.background = "url(images/main/left_right_hover.png) 0 -300px no-repeat";
	else
		ent.style.background = "url(images/main/left_right.png) 0 -200px no-repeat";
}
// 点击列表原点变色
function list_change(evt, which)
{
	// 快速链接图片文字
	var max_span = document.getElementById("main_img").getElementsByTagName("span");
	if(screenWidth <= 1024)
	{
   		evt.style.background = 'url(images/main/footer_btn.png) 0 -100px no-repeat'; 
   		if(evt.className != "footer_btn6")
   		{
   			document.getElementById("footer_btn6").style.background = 'url(images/main/footer_btn.png) 0 0 no-repeat';
   		}
   		if(evt.className != "footer_btn7")
   		{
   			document.getElementById("footer_btn7").style.background = 'url(images/main/footer_btn.png) 0 0 no-repeat';
   		}
   		if(evt.className != "footer_btn8")
   		{
   			document.getElementById("footer_btn8").style.background = 'url(images/main/footer_btn.png) 0 0 no-repeat';
   		}
   		if(evt.className != "footer_btn9")
   		{
   			document.getElementById("footer_btn9").style.background = 'url(images/main/footer_btn.png) 0 0 no-repeat';
   		}
   		if(evt.className != "footer_btn10")
   		{
   			document.getElementById("footer_btn10").style.background = 'url(images/main/footer_btn.png) 0 0 no-repeat';
   		}
   	}
   	else
   	{
		evt.style.background = 'url(images/main/foot.png) 0 -800px no-repeat'; 
		if(evt.className != "footer_btn6")
		{
			document.getElementById("footer_btn6").style.background = 'url(images/main/foot.png) 0 -600px no-repeat';
		}
		if(evt.className != "footer_btn7")
		{
			document.getElementById("footer_btn7").style.background = 'url(images/main/foot.png) 0 -600px no-repeat';
		}
		if(evt.className != "footer_btn8")
		{
			document.getElementById("footer_btn8").style.background = 'url(images/main/foot.png) 0 -600px no-repeat';
		}
		if(evt.className != "footer_btn9")
		{
			document.getElementById("footer_btn9").style.background = 'url(images/main/foot.png) 0 -600px no-repeat';
		}
		if(evt.className != "footer_btn10")
		{
			document.getElementById("footer_btn10").style.background = 'url(images/main/foot.png) 0 -600px no-repeat';
		}
	}
	if(which == 0)
	{
		for(var i = 0,max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
		{
			max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i].text + ")";
			max[i].style.cursor = "pointer";
			max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
			if(ie6)
				document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
			max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i].text);
			var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i].text);
			if(img_url != "")
			{
				if(img_url.indexOf("?") < 0)
					img_url = img_url + "?" + user_date;
				else
					img_url = img_url + "&" + user_date;
			}
			attr(max[i],"Link",img_url);
			attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i].text));
			max[i].id = "img_" + i;
		}
		pic_right_num = 0;
	}
	if(getDefaultStyle(document.getElementById("footer_btn6"),"display") == "none")
	{
		if(which == 1)
		{
			for(var i = 0,max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i].text);	;
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i].text));
				max[i].id = "img_" + i;
			}
			pic_right_num = 0;
		}
		if(which == 2)
		{
			for(var i = 0,max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(images/shortcut/null.png)";
				max[i].style.cursor = "";
				max[i].title = "";
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = "";
				max_span[i].innerHTML = "";
				attr(max[i],"title_txt","");
			}
			for(var i = 0,max = document.getElementById("main_img").getElementsByTagName("img");i < pic_num1 - 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i + 9*pic_size_num].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num].text);
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i + 9*pic_size_num].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num].text));
				max[i].id = "img_" + (i + 9*pic_size_num);
			}
			pic_right_num = 1;
		}
	}
	if(getDefaultStyle(document.getElementById("footer_btn6"),"display") != "none")
	{
		if(which == 1)
		{
			for(var i = 0,max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i + 9*pic_size_num].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num].text);
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i + 9*pic_size_num].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);	
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num].text));
				max[i].id = "img_" + (i + 9*pic_size_num);
			}
			pic_right_num = 1;
		}
		if(which > 1)
		{
			for(var i = 0,max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(images/shortcut/null.png)";
				max[i].style.cursor = "";
				max[i].title = "";
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = "";
				max_span[i].innerHTML = "";
				attr(max[i],"title_txt","");
			}
			// 当快速链接图片超过3页
			var img_num2 = (pic_num1 > 9*pic_size_num*(which + 1))?9*pic_size_num:(pic_num1 - 9*pic_size_num*which);
			for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < img_num2;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i + 9*pic_size_num*which].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num*which].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num*which].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num*which].text);	
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i + 9*pic_size_num*which].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num*which].text));
				max[i].id = "img_" + (i + 9*pic_size_num*which);
			}
			pic_right_num = which;
		}
	}
}
// 二级菜单滑过状态
function newslist_change(evt, which)
{
	if(which == 0)
	{
		evt.getElementsByTagName("a")[0].style.color = "#FF5900";
	}
	if(which == 1)
	{
		evt.getElementsByTagName("a")[0].style.color = "#5F5F5F";
	}
	
}
// 定义标签栏的长度
var main2_top_len;
// 打开标签页
function open_main2(evt, id)
{
	header1_num = 0;
	header2_num = 0;
	main2_top_len = document.body.offsetWidth - 110;
	//document.getElementById("header2").style.display = "none";
	//document.getElementById("header3").style.display = "none";
	//document.getElementById('header3_iframe').height = 0;
	//document.getElementById("tip2").style.display = "none";
	if(document.getElementById("user_mes").style.display != "block")
	{
		var user_mes = document.getElementById("user_mes");
		document.getElementById("table_top_div1").style.width = (document.body.offsetWidth - 110) + "px";
		document.getElementById("main_body").style.display = "none";
		user_mes.style.display = "block";
		user_mes.style.width = user_mes.offsetWidth + 5;
		document.getElementById("up").style.display = "block";
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		document.getElementById("bg_03").style.display = "inline-table";
		else
			document.getElementById("bg_03").style.display = "block";//ie不支持inline
		var body_height = document.body.offsetHeight;
		document.getElementById("bg_03").style.height = (body_height - 97) + "px";
			// 折叠菜单隐藏按钮位置
		document.getElementById("menu_lib").style.top = (body_height - 200)/2 + "px";
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			inits( - 20);
		else
			inits(0);
	}
	//for(var i = 0, max = getElementsByClassName("li_dom", "li");i < max.length;i++)
	//{
	//	header1_out(max[i].getElementsByTagName("a")[0]);
	//}
	for(var i = 0, max_len = label_arr.length;i < max_len;i++)
	{
		if(label_arr[i].id == "label_" + id && getDefaultStyle(label_arr[i],"display") != "none")
		{
			tab_change(label_arr[i])
			return false;
		}
	}
	var	label_01_con = getElementsByClassName("label_01", "div");
	if(label_01_con.length == 1 && label_01_con[0].id == "")
	{
		var label_01 = label_01_con[0].getElementsByTagName("p")[1];
		if(evt.id.substring(0,3) != "img")
		{
			label_01.innerHTML = "<span>" + evt.title + "</span>";
			label_01.title = evt.title;
		}
		else
		{
			label_01.innerHTML = "<span>" + attr(evt,'title_txt') + "</span>";
			label_01.title = attr(evt,'title_txt');
			attr(label_01_con[0],'file',file(attr(evt,'Link'),'FileName')) ;
		}
		// 第一个标签位置区分ff和ie
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			label_01_con[0].style.left = "5px";
		label_01_con[0].id = "label_" + evt.id;
		label_arr.push(label_01_con[0]);
		tab_change(label_01_con[0]);
	}
	else
	{
		if(evt.id.substring(0,3) != "img")
			add_label(evt.id, evt.title);
		else
			add_label(evt.id,attr(evt, 'title_txt'));
	}
}
// 打开主页
function open_main1(evt)
{
	header1_num = 0;
	header2_num = 0;
	if(getDefaultStyle(document.getElementById("logo"),"display") == "none")
		header_up();
	document.getElementById("header2").style.display = "none";
	document.getElementById("header3").style.display = "none";
	document.getElementById('header3_iframe').height = 0;
	document.getElementById("tip2").style.display = "none";
	document.getElementById("main_body").style.display = "block";
	document.getElementById("user_mes").style.display = "none";
	document.getElementById("up").style.display = "none";
	document.getElementById("bg_03").style.display = "none";
	for(var i = 0, max = getElementsByClassName("li_dom", "div");i < max.length;i++)
	{
		header1_out(max[i].getElementsByTagName("a")[0]);
	}
	memo_dis();
}
// iframe切换变量
var iframe_num = 0;
// 右侧折叠栏标题个数
var right_len;
// 读取右侧边栏是否展开
var xml_menu;
// 内容区域点击切换
function tab_change(evt)
{
	//获取选中标签背景
	var bg_pos;
	if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		bg_pos = document.defaultView.getComputedStyle(evt.getElementsByTagName("p")[1])['backgroundPosition'].replace("0px ","");
	if(navigator.userAgent.indexOf("MSIE") > 0)
		bg_pos = evt.getElementsByTagName("p")[1].currentStyle['backgroundPositionY'];
	var evt2_len = label_arr.length;
	if(bg_pos != "435px")
	{
		var evt_p = evt.getElementsByTagName("p");
		if(ie6)
		{
			evt_p[1].style.background = "url('images/main/page_tab.gif') 0 435px";
			evt_p[0].style.background = "url('images/main/page_tab.gif')";
			evt_p[2].style.background = "url('images/main/page_tab.gif') 0 335px";
		}
		if(!ie6)
		{
			evt_p[1].style.background = "url('images/main/page_tab.png') 0 435px";
			evt_p[0].style.background = "url('images/main/page_tab.png')";
			evt_p[2].style.background = "url('images/main/page_tab.png') 0 335px";
		}
		var arr = [];
		for(var i = 0;i < evt2_len;i++)
		{
			var evt2_p = label_arr[i].getElementsByTagName("p");
			arr[i] = getDefaultStyle(label_arr[i],"zIndex")
			if(evt.id == label_arr[i].id)
			{
				if(parseInt(getDefaultStyle(evt, "left").replace("px","")) < 0)
				{
					var len = Math.abs(getDefaultStyle(evt, "left").replace("px",""));
					for(j = 0;j < evt2_len;j++)
					{
						label_arr[j].style.left = parseInt(getDefaultStyle(label_arr[j], "left").replace("px","")) + len + "px";
					}
				}
				if(parseInt(getDefaultStyle(evt, "left").replace("px","")) > main2_top_len)
				{
					var len = evt.offsetLeft + evt.offsetWidth - main2_top_len;
					for(j = 0;j < evt2_len;j++)
					{
						label_arr[j].style.left = parseInt(getDefaultStyle(label_arr[j], "left").replace("px","")) - len + "px";
					}
				}
				continue;
			}
			if(ie6)
			{
				evt2_p[1].style.background = "url('images/main/page_tab.gif') 0 135px";
				evt2_p[0].style.background = "url('images/main/page_tab.gif') 0 235px";
				evt2_p[2].style.background = "url('images/main/page_tab.gif') 0 35px";
			}
			if(!ie6)
			{
				evt2_p[1].style.background = "url('images/main/page_tab.png') 0 135px";
				evt2_p[0].style.background = "url('images/main/page_tab.png') 0 235px";
				evt2_p[2].style.background = "url('images/main/page_tab.png') 0 35px";
			}
			label_arr[i].style.zIndex = getDefaultStyle(label_arr[0], "zIndex") - i;
		  }
		evt.style.zIndex = Math.max.apply(this,arr) + 1;
	}
	// 标签页选中时再打开要能正确显示标签页位置c
	if(bg_pos == "435px")
	{
		var arr = [];
		for(var i = 0;i < evt2_len;i++)
		{
			arr[i] = getDefaultStyle(label_arr[i],"zIndex")
			if(evt.id == label_arr[i].id)
			{
				if(parseInt(getDefaultStyle(evt, "left").replace("px","")) < 0)
				{
					var len = Math.abs(getDefaultStyle(evt, "left").replace("px",""));
					for(j = 0;j < evt2_len;j++)
					{
						label_arr[j].style.left = parseInt(getDefaultStyle(label_arr[j], "left").replace("px","")) + len + "px";
					}
				}
				if(parseInt(getDefaultStyle(evt, "left").replace("px","")) > main2_top_len)
				{
					var len = evt.offsetLeft + evt.offsetWidth - main2_top_len;
					for(j = 0;j < evt2_len;j++)
					{
						label_arr[j].style.left = parseInt(getDefaultStyle(label_arr[j], "left").replace("px","")) - len + "px";
					}
				}
				continue;
			}
		  }
		evt.style.zIndex = Math.max.apply(this,arr) + 1;
	}
	var menu_lib_con = document.getElementById("menu_lib");
	var out_push_con = document.getElementById("out_push");
	var out_main_con = document.getElementById("out_main");
	var menu_lib_iframe_con = document.getElementById("menu_lib_iframe");
	// 先不给出隐藏按钮，样式给出后再展现
	menu_lib_con.style.display = "none";
	out_push_con.style.display = "none";
	var ind = "help,UserInfo,Discuss";
	var menu_lib = menu_lib_con.title;
	var sub = evt.id.substring(6,9);
	var ind_label = ind.indexOf((evt.id).replace('label_',''));
	// 快速链接，帮助，讨论还没有加上折叠菜单
	if(xml_menu == "off" || sub == "img" || ind_label != -1)
		out_main_con.style.display = "none";
	if(sub == "img" && attr(evt, "file") !="")
	{
		out_main_con.style.display = "block";
		// 显示折叠按钮
		var menu_height = (document.body.offsetHeight - 200)/2 + "px";
		menu_lib_con.style.top = menu_height;
		menu_lib_iframe_con.style.top = (document.body.offsetHeight - 200)/2 + 15 + "px";
		menu_lib_con.style.display = "block";
		if(menu_lib == "隐藏")
		{
			out_push_con.style.display = "block";
			menu_show(evt);
		}
	}
	if(xml_menu == "on" && sub != "img" && ind_label < 0)
	{
		out_main_con.style.display = "block";
		// 显示折叠按钮
		var menu_height = (document.body.offsetHeight - 200)/2 + "px";
		menu_lib_con.style.top = menu_height;
		menu_lib_iframe_con.style.top = (document.body.offsetHeight - 200)/2 + 15 + "px";
		menu_lib_con.style.display = "block";
		if(menu_lib == "隐藏")
		{
			out_push_con.style.display = "block";
			menu_show(evt);
		}
	}
	var max = document.getElementById("table_body1").getElementsByTagName("iframe");
	var max_len = max.length;
	for(var i = 0;i < max_len;i++)
	{
		if(max[i].id == "bg_iframe" + (evt.id).replace("label_",""))
		{
			max[i].style.display = "block";
			iframe_num = 1;
			continue;
		}
		max[i].style.display = "none";
	}
	if(iframe_num == 1)
	{
		iframe_num = 0;
		return false;
	}
	// 区分快速链接和导航
	if(sub != "img")
	{
		var op = document.createElement("iframe");
		op.setAttribute('frameborder', '0', 0);
		op.id = "bg_iframe" + (evt.id).replace("label_","");
		if((evt.id).replace('label_','') == "help")
		{
			op.src = "help/index.htm" + "?t=" + Math.random();
			document.getElementById("table_body1").appendChild(op);
		}
		else if((evt.id).replace('label_','') == "UserInfo")
		{
			var url = 'Server/GetOption.aspx?command=setconfig&key=UserInfo&' + user_date + '&t=' + Math.random();
			var xml = GetInfoFromServer(url);
			if(xml != "")
			{
				if(xml.indexOf("?") < 0)
					xml = xml + "?" + user_date + '&t=' + Math.random();
				else
					xml = xml + "&" + user_date + '&t=' + Math.random();
			}
			op.src =  xml;
			document.getElementById("table_body1").appendChild(op);
		}
		else
		{
			op.src = 'Support/TopMenu.aspx?leftid=' + (evt.id).replace('label_','') + '&t=' + Math.random();
			document.getElementById("table_body1").appendChild(op);
		}
	}
	if(sub == "img")
	{
		var op = document.createElement("iframe");
		op.setAttribute('frameborder', '0', 0);
		op.id = "bg_iframe" + (evt.id).replace("label_","");
		op.src = attr(document.getElementById(evt.id.replace('label_','')),'Link');
		document.getElementById("table_body1").appendChild(op);
	}
}
// 关闭图标滑过效果
function close_over(evt, which)
{
	if(which == 0)
	{
		if(ie6)
			evt.style.background = "url('images/main/tips_close.gif') 0 -100px no-repeat";
		else
			evt.style.background = "url('images/main/tips_close.png') 0 -100px no-repeat";
	}
	if(which == 1)
	{
		if(ie6)
			evt.style.background = "url('images/main/tips_close.gif')";
		else
			evt.style.background = "url('images/main/tips_close.png')";
	}
}
// 关闭图标效果
function close_clik(evt)
{	
	var ent = document.getElementById("table_top_div").getElementsByTagName("div");
	var top_len = ent.length;
	removeNodeById(evt.parentNode.parentNode.id.replace("label_","bg_iframe"));
	// 标签关闭后显示第一个标签
	showMenu(1, null);
	var len1 = evt.parentNode.parentNode.offsetWidth;
	evt.parentNode.parentNode.style.display = "none";
	var evt2 = evt.parentNode.parentNode.parentNode.getElementsByTagName("div");
	var i;
	for(i = 0;i < evt2.length;i++)
	{
		if(evt.parentNode.parentNode.id == evt2[i].id)
			var num = i + 1;
	}
	if(getDefaultStyle(evt.parentNode,"backgroundPositionY") == "335px" || getDefaultStyle(evt.parentNode,"backgroundPosition") == "0px 335px")
	{
		if(evt2.length > 1)
		{
			if(num == 1)
				tab_change(evt2[num]);
			else 
				tab_change(evt2[num - 2]);	
		}
	}
	if(getDefaultStyle(ent[0], "left").replace("px","") >= 0)
	{
		for(k = num;k < evt2.length;k++)
		{
				var t = (getDefaultStyle(evt2[k],'left').replace("px","") - len1 + 16) + "px";
				evt2[k].style.left=t;
		}
	}
	if(getDefaultStyle(ent[0], "left").replace("px","") < 0)
	{
		if( Math.abs(getDefaultStyle(ent[0], "left").replace("px","")) < (len1 - 16))
		{
			for(k = num;k < evt2.length;k++)
			{
				var t = (getDefaultStyle(evt2[k],'left').replace("px","") - len1 + 16 + Math.abs(getDefaultStyle(ent[0], "left").replace("px",""))) + "px";
				evt2[k].style.left=t;
			}
			len1 = Math.abs(getDefaultStyle(ent[0], "left").replace("px","")) + 16;
		}
		for(i = 0;i < num-1; i++)
		{
			ent[i].style.left = parseInt(getDefaultStyle(ent[i], "left").replace("px","")) + len1 - 16 +"px";
		}
	}
	var m = 0;
	for(var j = 0, max = getElementsByClassName("closs_01", "span").length;j < max;j++)
	{
		if(getDefaultStyle(document.getElementById("table_top_div").getElementsByTagName("div")[j],"display") != "none")
			m++;
	}
	if(m == 0)
	{
		open_main1();
	}
	var evt_par_id = evt.parentNode.parentNode.id;
	// 删除标签
	removeNodeById(evt_par_id);
	// 删除标签数组
	for(var i = 0;i<label_arr.length;i++)
	{
		if(label_arr[i].id == evt_par_id)
		{
			label_arr = label_arr.del(i);
			break;
		}
	}
}
var menu_id;
// 收缩栏
function showMenu(id ,xmldom)
{
	for(var i = 1;i <= right_len;i++)
	{ 
	 	if(i == id)
	 	{
			$("#a" + id).slideDown(0);
			var ss = "a" + id;
			var ss_dom = document.getElementById(ss).parentNode.parentNode.getElementsByTagName("dt")[0];
			ss_dom.style.background = "url(images/main/banner1.png) 0 0 repeat-x";
			ss_dom.style.borderBottom = "1px solid #F29E59";
			ss_dom.parentNode.style.border = "1px solid #F29E59";
			ss_dom.parentNode.style.borderTop = "1px solid #F29E59";
			if(attr(document.getElementById(ss).getElementsByTagName("iframe")[0],"src") == "#")
			{
				break;
			}
			if(attr(document.getElementById(ss).getElementsByTagName("iframe")[0],"src") != "")
			{
				document.getElementById(ss).display = "block";
				menu_id = document.getElementById(ss).getElementsByTagName("iframe")[0];
			}
			else
			{
				if(xmldom != null)
				{
					document.getElementById(ss).getElementsByTagName("iframe")[0].src = xmldom.getElementsByTagName("url")[i - 1].text + '&t=' + Math.random();
					menu_id = document.getElementById(ss).getElementsByTagName("iframe")[0];
				}
				else
				{
					var evt = tab_now();
					if(attr(evt, "file") != "")
						var url="Server/GetMenu.aspx?command=spread_menu&FileName=" + attr(evt, "file") + "&" + user_date + '&t=' + Math.random();
					else
						var url = "Server/GetMenu.aspx?command=spread_menu&nodeid=" + (evt.id).replace("label_","") + "&" + user_date + '&t=' + Math.random();
					var xml = GetInfoFromServer(url);
					if(xml != "")
					{
						var xmlDoc = LoadXml(xml);
						if(xmlDoc == null)
						{
							alert(xml);
							return false;
						}
						document.getElementById(ss).getElementsByTagName("iframe")[0].src = xmlDoc.getElementsByTagName("url")[i - 1].text + '&t=' + Math.random();
						menu_id = document.getElementById(ss).getElementsByTagName("iframe")[0];
					}
				}
			}
		}
	 	else
	 	{
			$("#a" + i).slideUp(0);
			var ss = "a" + i;
			var ss_dom = document.getElementById(ss).parentNode.parentNode.getElementsByTagName("dt")[0];
			ss_dom.style.background = "url(images/main/out_banner.png) 0 -100px";
			ss_dom.style.border = "0";
			ss_dom.parentNode.style.border = "";
		}
	}
}
// 收缩栏隐藏
function lib_hidden()
{
	if(getDefaultStyle(document.getElementById("out"),'display') == "block")
	{
		document.getElementById("menu_lib").style.background = "url(images/main/right_list.png) 0 0 no-repeat";
		document.getElementById("out").style.display = "none";
		document.getElementById("menu_lib").title = "展开";
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		{
			document.getElementById("menu_lib").style.right = "5px";
			document.getElementById("menu_lib_iframe").style.right = "5px";
			document.getElementById("out_push").style.display = "none";
		}
		else
		{
			document.getElementById("menu_lib").style.right = 0;
			document.getElementById("menu_lib_iframe").style.right = 0;
			document.getElementById("out_push").style.display = "none";
		}
	}
	else
	{
		// 获取当前标签
		var evt_menu = tab_now();
		// 折叠栏展开时加载iframe
		if(document.getElementById("out").getElementsByTagName("iframe").length == 0)
			menu_show(evt_menu);
		document.getElementById("menu_lib").style.background = "url(images/main/right_list.png) -200px 0 no-repeat";
		document.getElementById("out").style.display = "block";
		document.getElementById("menu_lib").title = "隐藏";
		document.getElementById("menu_lib").style.right = document.getElementById("out").offsetWidth;
		document.getElementById("menu_lib_iframe").style.right = document.getElementById("out").offsetWidth;
		//document.getElementById("menu_lib").style.right = "303px";
		//document.getElementById("menu_lib_iframe").style.right = "303px";留给firefox调整使用
		document.getElementById("out_push").style.display = "block";
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			document.getElementById("out_push").style.right = "308px";
		else
			document.getElementById("out_push").style.right = "300px";
		// 隐藏再打开时显示对应标签的折叠栏
		tab_change(evt_menu);
	}
}
// 收缩上边栏
function header_up(num)
{
	if(getDefaultStyle(document.getElementById("header"),"display") == 'block')
	{
		document.getElementById("header").style.display = "none";
		document.getElementById("tip2").style.display = "none";
		document.getElementById("header2").style.display = "none";
		document.getElementById("header3").style.display = "none";
		document.getElementById('header3_iframe').height = 0;
		document.getElementById("user_mes").style.display = "none";
		document.getElementById("logo").style.display = "none";
		document.getElementById("bg_03").style.marginTop = '4px';
		document.getElementById("up").style.top = 0;
		document.getElementById("up").style.background = 'url(images/main/top_touch.png) 0 -100px no-repeat';
		document.getElementById("up").title = "展开";
		document.getElementById("bg_03").style.height = (document.body.offsetHeight - 11) + "px";
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			inits( - 20);
		else
			inits(0);
	}
	else
	{
		document.getElementById("header").style.display = "block";
		document.getElementById("user_mes").style.display = "block";
		document.getElementById("logo").style.display = "block";
		document.getElementById("bg_03").style.marginTop = '90px';
		document.getElementById("up").style.top = '84px';
		document.getElementById("up").style.background = 'url(images/main/top_touch.png) 0 0 no-repeat';
		document.getElementById("up").title = "隐藏";
		document.getElementById("bg_03").style.height = (document.body.offsetHeight - 97) + "px";
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			inits(-20);
		else
			inits(0);
	}
}
//三级导航滑过效果
function header3_over(evt, which, num)
{
	if(which == 0)
	{
		evt.style.color = "#fff";
		headerN_over(evt, num);
	}
	if(which == 1)
	{
		evt.style.color = "#000";
	}
}
// 多级导航点击
function headerN_clik(evt, id)
{
	if(attr(evt, "IsLink") == 1)
		open_main2(evt, id);
	else
		return false;
}
//  多级导航位置设置
function headerN_over(evt, num)
{
	var headerN = getElementsByClassName("headerN", "div");
	var evt_parent = evt.parentNode.parentNode.parentNode.parentNode;
	for(var j = 0;j < headerN.length;j++)
	{
		if(attr(headerN[j], "op_num") < attr(evt_parent, "op_num"))
			continue;
		headerN[j].style.display = "none";
		for(var k = 0, max = headerN[j].getElementsByTagName("a");k < max.length;k++)
		{
			if(attr(max[k], "title_id") == evt_parent.id.replace("headerN_",""))
				headerN[j].style.display = "block";
		}
	}
	evt_parent.style.display = "block";
	if(evt.className == "headern_none")
		return false;
	if(document.getElementById("headerN_" + evt.id) && document.getElementById("headerN_" + evt.id).className != "header_none")
	{
		document.getElementById("headerN_" + evt.id).style.display = "block";
		return false;
	}
	var op_header3 = document.createElement("div");
	var op_top = document.createElement("div");
	var op_main = document.createElement("div");
	var op_bottom = document.createElement("div");
	var op_ul = document.createElement("ul");
	op_header3.className = "headerN";
	op_header3.id = "headerN_" + evt.id;
	op_top.className = "header3_top";
	op_main.className = "header3_main";
	// 加载前有请等待内容
	op_ul.innerHTML = "<li><a href='javascript:void(0)'>请等待</a></li>"
	op_bottom.className = "header3_bottom";
	document.body.appendChild(op_header3);
	num = num + 1;
	attr(op_header3, "op_num", num)
	op_header3.appendChild(op_top);
	op_header3.appendChild(op_main);
	op_header3.appendChild(op_bottom);
	op_main.appendChild(op_ul);
	//多级导航异步加载
	//op_header3.style.left = evt.parentNode.parentNode.parentNode.parentNode.offsetLeft + 116;
	//op_header3.getElementsByTagName("li")[0].style.display = "block";
	var url = 'Server/GetMenu.aspx?' + user_date + '&command=main_menu&nodeid=' + evt.id + '&t=' + Math.random();// 获取第二级主菜单
	GetInfoFromServerAsyn(url, callback);
	function callback(xml)
	{
		var xmlDoc1 = LoadXml(xml);
		if(xmlDoc1 == null)
		{
			alert(xml);
			return false;
		}
		var headeN_len = xmlDoc1.getElementsByTagName("CategoryName").length;
		if(headeN_len == 0)
		{
			op_header3.style.display = "none";	
			op_header3.className = "header_none";	
			evt.className = "headern_none";
			return false;
		}
		op_ul.getElementsByTagName("li")[0].style.display = "none";
		var op_con = "";
		for(var i = 0;i < headeN_len;i++)
		{
			var catename = xmlDoc1.getElementsByTagName("CategoryName")[i].text;
			var header_N_val = unescape(catename).length > 7?unescape(catename).substr(0,6) + "...":unescape(catename);
			op_con += "<li><a href='javascript:void(0)' onmouseover='header3_over(this,0," + num + ")'" + " onmousedown='headerN_clik(this, this.id)' onmouseout='header3_over(this,1,1)' "
							+ "title='" + unescape(xmlDoc1.getElementsByTagName("CategoryName")[i].text) + "'"
							+ "IsLink='" + unescape(xmlDoc1.getElementsByTagName("IsLink")[i].text) + "'"
							+ "title_id='" + unescape(xmlDoc1.getElementsByTagName("CategoryID")[i].text) + "'"
							+ "id=" + unescape(xmlDoc1.getElementsByTagName("CategoryID")[i].text) + ">" 
							+ header_N_val + "</a></li>"
		}
		op_ul.innerHTML += op_con;
		op_header3.style.display = "block";
		// 多级导航left距离
		var headerN_left = evt.parentNode.parentNode.parentNode.parentNode.offsetLeft;
		// 多级导航到了最右边左移
		if(headerN_left + 232 > document.body.offsetWidth || evt.parentNode.parentNode.className == "left")
		{
			op_header3.style.left = headerN_left - 116;
			op_ul.className = "left";
		}
		else
			op_header3.style.left = headerN_left + 116;
		if(num == 1)
			op_header3.style.top = evt.parentNode.offsetTop + op_header3.offsetTop;
		if(num > 1)
			op_header3.style.top = evt.parentNode.parentNode.parentNode.parentNode.offsetTop + evt.parentNode.offsetTop - 10;
	}
}
// 图片右滚动
// 定义全局变量判断图片页数
var pic_right_num = 0;
function pic_right()
{
	var max_span = document.getElementById("main_img").getElementsByTagName("span");
	if(pic_num1 < (9*pic_size_num+1))
		return false;
	if(pic_num1 > 9*pic_size_num && pic_num1 < (18*pic_size_num+1))
	{
		if(pic_right_num == 1)
		{
			list_change(document.getElementById("footer_btn7"),1);
			return false;
		}
		if(pic_right_num == 0)
		{
			for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(images/shortcut/null.png)";
				max[i].style.cursor = "";
				max[i].title = "";	
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = "";
				max_span[i].innerHTML = "";
				attr(max[i],"title_txt","");
			}
			for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < pic_num1 - 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i + 9*pic_size_num].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num].text);
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i + 9*pic_size_num].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num].text));
				max[i].id = "img_" + (i + 9*pic_size_num);
			}
			list_change(document.getElementById("footer_btn8"));
			pic_right_num++;
			return false;
		}
	}
	if(pic_num1 > (18*pic_size_num))
	{
		if(pic_right_num == (Math.ceil(pic_num1/(9*pic_size_num)) - 1))
		{
			if(pic_size_num == 1)
			{
				// 图片张数
				var img_nm = (Math.ceil(pic_num1/9) > 5)?5:Math.ceil(pic_num1/9);
				// 图片回滚
				if(img_nm == 4)
					list_change(document.getElementById('footer_btn6'), 0);
				if(img_nm == 3)
					list_change(document.getElementById('footer_btn6'), 0);
				return false;
			}
			if(pic_size_num == 2)
			{
				// 图片张数
				var img_nm = (Math.ceil(pic_num1/(9*pic_size_num)) > 3)?3:Math.ceil(pic_num1/(9*pic_size_num));
				// 图片回滚
				if(img_nm == 3)
					list_change(document.getElementById('footer_btn6'), 0);
				return false;
			}
		}
		if(pic_right_num == 0)
		{
			for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i + 9*pic_size_num].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + 9*pic_size_num].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num].text);	
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i + 9*pic_size_num].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i + 9*pic_size_num].text));
				max[i].id = "img_" + (i + 9*pic_size_num);
			}
			pic_right_num++;
			list_change(document.getElementById("footer_btn7"));
			return false;
		}
		if(pic_right_num > 0)
		{
			// 大分辨率下最多3张快速链接
			if(pic_size_num == 2)
			{
				if(pic_right_num == 2)
				{
					list_change(document.getElementById('footer_btn6'), 0);
					return false;
				}
					
			}
			if(pic_right_num == 4)
			{
				list_change(document.getElementById('footer_btn6'), 0);
				return false;
			}
			for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(images/shortcut/null.png)";
				max[i].style.cursor = "";
				max[i].title = "";	
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = "";
				max_span[i].innerHTML = "";
				attr(max[i],"title_txt","");
			}
			var img_num2 = (pic_num1 > (9*pic_size_num)*(pic_right_num + 2))?(9*pic_size_num):(pic_num1 - (9*pic_size_num)*(pic_right_num + 1));
			for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < img_num2;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i + (pic_right_num + 1)*9*pic_size_num].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + (pic_right_num + 1)*9*pic_size_num].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + (pic_right_num + 1)*9*pic_size_num].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i + (pic_right_num + 1)*9*pic_size_num].text);
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i + (pic_right_num + 1)*9*pic_size_num].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i + (pic_right_num + 1)*9*pic_size_num].text));
				max[i].id = "img_" + (i + (pic_right_num + 1)*9*pic_size_num);
			}
			list_change(document.getElementById("footer_btn" + (pic_right_num + 7)));
			pic_right_num++;
			return false;
		}
	}
}
// 图片左滚动
function pic_left()
{
	var max_span = document.getElementById("main_img").getElementsByTagName("span");
	if(pic_num1 < (9*pic_size_num+1))
		return false;
	if(pic_num1 > 9*pic_size_num && pic_num1 < (18*pic_size_num+1))
	{
		if(pic_right_num == 0)
		{
			list_change(document.getElementById('footer_btn8'), 2);
			return false;
		}
		if(pic_right_num == 1)
		{
			for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i].text);
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);	
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i].text));
				max[i].id = "img_" + i;
			}
			list_change(document.getElementById("footer_btn7"));
			pic_right_num--;
			return false;
		}
	}
	if(pic_num1 > 18*pic_size_num)
	{
		if(pic_right_num == 0)
		{
			if(pic_size_num == 1)
			{
				// 图片张数
				var img_nm = (Math.ceil(pic_num1/9) > 5)?5:Math.ceil(pic_num1/9);
				// 图片回滚
				if(img_nm == 5)
					list_change(document.getElementById('footer_btn10'), img_nm-1);
				if(img_nm == 4)
					list_change(document.getElementById('footer_btn9'), img_nm-1);
				if(img_nm == 3)
					list_change(document.getElementById('footer_btn8'), img_nm-1);
				return false;
			}
			if(pic_size_num == 2)
			{
				// 图片张数
				var img_nm = (Math.ceil(pic_num1/(9*pic_size_num)) > 3)?3:Math.ceil(pic_num1/(9*pic_size_num));
				// 图片回滚
				list_change(document.getElementById('footer_btn8'), 2);
				return false;
			}
		}
		if(pic_right_num > 0)
		{
			for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < 9*pic_size_num;i++)
			{
				max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i + (pic_right_num - 1)*9*pic_size_num].text + ")";
				max[i].style.cursor = "pointer";
				max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + (pic_right_num - 1)*9*pic_size_num].text);
				if(ie6)
					document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i + (pic_right_num - 1)*9*pic_size_num].text);
				max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i + (pic_right_num - 1)*9*pic_size_num].text);
				var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i + (pic_right_num - 1)*9*pic_size_num].text);
				if(img_url != "")
				{
					if(img_url.indexOf("?") < 0)
						img_url = img_url + "?" + user_date + '&t=' + Math.random();
					else
						img_url = img_url + "&" + user_date + '&t=' + Math.random();
				}
				attr(max[i],"Link",img_url);	
				attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i + (pic_right_num - 1)*9*pic_size_num].text));
				max[i].id = "img_" + (i + (pic_right_num - 1)*9*pic_size_num);
			}
			pic_right_num--;
			list_change(document.getElementById("footer_btn" + (pic_right_num + 6)));
			return false;
		}
	}
}
// 增加标签
function add_label(idname, text)
{
	//var evt2 = document.getElementById("table_top_div").getElementsByTagName("div");
	var evt2_len = label_arr.length;
	var arr = [];
	for(var i = 0;i < evt2_len;i++)
	{
		arr[i] = getDefaultStyle(label_arr[i],"zIndex")
	}
	if(idname.substring(0,3) == "img")
		file_txt = file(attr(document.getElementById(idname),'Link'),'FileName');
	else
		var file_txt = "";
	//document.getElementById("table_top_div").innerHTML += "<div  onclick='tab_change(this)'  class='label_01' id=label_" + idname + " file=" + file_txt + ">"
	//		 + "<p class='lableleft_off'></p>"
	//		 + "<p class='lablmidle_off' title='" + text + "'><span>" + text + "</span></p>"
	//		 + "<p class='lablright_off'>"
	//		 + "<span class='closs_01' onmouseover='close_over(this,0)' onmouseout='close_over(this,1)' onmousedown='close_clik(this)'></span>"
	//		 + "</p>"
	//		 + "</div>"
	var span_p1 = document.createElement("p"),
		span_p2 = document.createElement("p"),
		span_p3 = document.createElement("p"),
		span_p2_span = document.createElement("span"),
		span_p3_span = document.createElement("span"),
		span_div = document.createElement("div"),
		table_top_div = document.getElementById("table_top_div");
	span_p1.className  = "lableleft_off";
	span_p2.className  = "lablmidle_off";
	span_p2.title = text;
	span_p2_span.innerHTML = text;
	span_p3.className  = "lablright_off";
	span_p3_span.className  = "closs_01";
	span_div.className  = "label_01";
	span_div.id = "label_" + idname;
	attr(span_div,"file",file_txt);
	table_top_div.appendChild(span_div);
	span_div.appendChild(span_p1);
	span_div.appendChild(span_p2);
	span_div.appendChild(span_p3);
	span_p2.appendChild(span_p2_span);
	span_p3.appendChild(span_p3_span);	
	AttachEvent(span_p3_span,"onmouseover",function(){close_over(span_p3_span,0)});
	AttachEvent(span_p3_span,"onmouseout",function(){close_over(span_p3_span,1)});
	AttachEvent(span_p3_span,"onmousedown",function(){close_clik(span_p3_span)});
	AttachEvent(span_div,"onclick",function(){tab_change(span_div)});
	label_arr.push(span_div);
	var top_len = label_arr.length;
	//var ent = document.getElementById("table_top_div").getElementsByTagName("div");
	//var top_len = document.getElementById("table_top_div").getElementsByTagName("div").length;
	var label_idname = document.getElementById("label_" + idname);
	if(top_len == 1)
	{
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			label_idname.style.left = "5px";
		else
			label_idname.style.left = 0;
	}
	if(top_len > 1)
		label_idname.style.left = label_arr[top_len - 2].offsetLeft + label_arr[top_len - 2].offsetWidth - 16 + "px";
	label_idname.style.cursor = "pointer";
	label_idname.style.top = 0;
	label_idname.style.position = "absolute";
	label_idname.style.zIndex = (Math.max.apply(this,arr) + 1)?100:(Math.max.apply(this,arr) + 1);
	// 当标签页超出时标签左移
	if(label_arr[top_len - 1].offsetLeft + label_arr[top_len - 1].offsetWidth > main2_top_len)
	{
		var len = label_arr[top_len - 1].offsetLeft + label_arr[top_len - 1].offsetWidth - main2_top_len;
		for(var i = 0;i < top_len;i++)
		{
			label_arr[i].style.left = getDefaultStyle(label_arr[i], 'left').replace("px","") - len + "px";
		}
	}
	// label没有标签时重新定义z-index
	tab_change(document.getElementById("label_" + idname));
}
// 小图标变色
function change_col(ent, which, num, id)
{
	if(ent == 1)
		ent = document.getElementById("set").getElementsByTagName("a")[0];
	if(ent == 2)
		ent = document.getElementById("collect").getElementsByTagName("a")[0];
	if(ent == 3)
		ent = document.getElementById("talk").getElementsByTagName("a")[0];
	if(ent == 4)
		ent = document.getElementById("help1").getElementsByTagName("a")[0];
	if(ent == 5)
		ent = document.getElementById("edt").getElementsByTagName("a")[0];
	if(which == 0)
	{
		ent.style.color = "#FFFFFF";
		document.getElementById(id).style.background = 'url(images/main/top_img2.png) -100px ' + num + ' no-repeat';
	}
	if(which == 1)
	{
		ent.style.color = "#A7A6B9";
		document.getElementById(id).style.background = 'url(images/main/top_img2.png) 0 ' + num + ' no-repeat';
	}
	
}
function open_top(ent)
{
	if(ent == 1)
		ent = document.getElementById("set").getElementsByTagName("a")[0];
	if(ent == 3)
		ent = document.getElementById("talk").getElementsByTagName("a")[0];
	if(ent == 4)
		ent = document.getElementById("help1").getElementsByTagName("a")[0];
	open_main2(ent, ent.id)
}
// 该参数用于设置折叠栏是否需要重新加载
var menu_num;
// 显示折叠栏
function menu_show(evt)
{
	menu_num = 0;
	var max = document.getElementById("out").getElementsByTagName("dl");
	var max2 = document.getElementById("out").getElementsByTagName("ul");
	for(var j = 0;j < max.length;j++)
	{	
		max[j].style.display = "none";
		max2[j].id = "menu_" + max2[j].id.replace("menu_","");
		if(attr(max2[j], "id_name") == evt.id)
		{
			max[j].style.display = "block";
			//获取当前选中的iframe，用于拉伸栏隐藏
			if(getDefaultStyle(max[j].getElementsByTagName("ul")[0], "display") != "none")
				menu_id = max[j].getElementsByTagName("ul")[0].getElementsByTagName("iframe")[0];
			max2[j].id = max2[j].id.replace("menu_","");
			menu_num++;
		}
	}
	if(menu_num != 0)
	{
		right_len = menu_num;
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			inits(-20);
		else
			inits(0);
		return false;
	}
	if(evt.id.substring(6,9) == "img")
		var url = "Server/GetMenu.aspx?command=spread_menu&FileName=" + attr(evt, "file") + "&" +user_date + '&t=' + Math.random();
	else
		var url = "Server/GetMenu.aspx?command=spread_menu&nodeid=" + (evt.id).replace("label_","") + "&" +user_date + '&t=' + Math.random();
	var xml = GetInfoFromServer(url);
	// 若右侧边栏返回数据为空则置空边栏样式
	if(xml == "")
	{
		document.getElementById("out").innerHTML += "<dl class='LeftMenu'>"
			+ "<dt><a href='javascript:void(0)'  hidefocus></a></dt>"
			+ "<dd>"    
			+ "<ul id='a1' id_name='" + evt.id + "'>"
			+ "<iframe frameborder='0' src='#' scrolling='no' style='display:none'></iframe>"
			+ "</ul>"
			+ "</dd>"
			+ "</dl>"
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			inits(-20);
		else
			inits(0);
		document.getElementById("menu_lib").style.display = "block";
	}
	else
	{
		// 读取右侧边栏内容
		var xmlDoc1 = LoadXml(xml);
		if(xmlDoc1 == null)
		{
			alert(xml);
			return false;
		}
		right_len = xmlDoc1.getElementsByTagName("title").length;
		var out_con = "";
		for(var i = 0, max = xmlDoc1.getElementsByTagName("title");i < max.length;i++)
		{
			out_con += "<dl class='LeftMenu'>"
			+ "<dt><a href='javascript:void(0)' onclick='showMenu(" + (i + 1) + ", null);return false;' hidefocus>" + max[i].text + "</a></dt>"
			+ "<dd>"    
			+ "<ul id='a" + (i + 1) + "' style='display:none' id_name='" + evt.id + "'>"
			+ "<iframe frameborder='0' src=''></iframe>"
			+ "</ul>"
			+ "</dd>"
			+ "</dl>"
		}
		document.getElementById("out").innerHTML += out_con;
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		{
			inits(-20);
		}
		else
			inits(0);
		document.getElementById("menu_lib").style.display = "block";
		showMenu(1, xmlDoc1);
	}
}
// 获取当前标签选中对象
function tab_now()
{
	for(var i = 0, max = document.getElementById("table_top_div").getElementsByTagName("div");i < max.length;i++)
	{
		var evt_menu = max[i];
		var bg_pos;
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
				bg_pos = document.defaultView.getComputedStyle(evt_menu.getElementsByTagName("p")[1])['backgroundPosition'].replace("0px ","");
		if(navigator.userAgent.indexOf("MSIE") > 0)
			bg_pos = evt_menu.getElementsByTagName("p")[1].currentStyle['backgroundPositionY'];
		if(bg_pos == "435px")
			break;
	}
	return evt_menu;
}
// 图片根据不同分辨率显示不同个数
function pic_size1(pic_num1)
{
	var footer_btn6 = document.getElementById("footer_btn6");
	var footer_btn7 = document.getElementById("footer_btn7");
	var footer_btn8 = document.getElementById("footer_btn8");
	var footer_btn9 = document.getElementById("footer_btn9");
	var footer_btn10 = document.getElementById("footer_btn10");
	footer_btn6.style.right = 485 - (140 - imgwidth)*1.5 + "px";
	footer_btn7.style.right = 405 - (140 - imgwidth)*1.5 + "px";
	footer_btn8.style.right = 325 - (140 - imgwidth)*1.5 + "px";
	footer_btn9.style.right = 245 - (140 - imgwidth)*1.5 + "px";
	footer_btn10.style.right = 165 - (140 - imgwidth)*1.5 + "px";
	for(i = 10;i < 19;i++)
	{
		document.getElementById("img" + i).style.display = "none";
		if(ie6)
			document.getElementById("img" + i + "_div").style.display = "none";
	}
	document.getElementById("main_img").style.right = -372 - (140 - imgwidth)*2.5 + "px";
	document.getElementById("main_img").style.marginTop = -208 + (156 - imgheight) + "px";
	document.getElementById("img_left").style.right = 710 - (140 - imgwidth)*2.5 + "px";
	pic_size_num = 1;
	if(pic_num1 < 10)
	{
		footer_btn6.style.display = "none";
		footer_btn7.style.display = "none";
		footer_btn8.style.display = "none";
		footer_btn9.style.display = "none";
		footer_btn10.style.display = "none";
	}
	if(pic_num1 > 9 && pic_num1 < 19)
	{
		footer_btn6.style.display = "none";
		footer_btn7.style.display = "block";
		footer_btn7.title = "第一页";
		footer_btn8.style.display = "block";
		footer_btn8.title = "第二页";
		footer_btn9.style.display = "none";
		footer_btn10.style.display = "none";
		list_change(footer_btn7);
	}
	if(pic_num1 > 18 && pic_num1 < 28)
	{
		footer_btn6.style.display = "block";
		footer_btn6.title = "第一页";
		footer_btn7.style.display = "block";
		footer_btn7.title = "第二页";
		footer_btn8.style.display = "block";
		footer_btn8.title = "第三页";
		footer_btn9.style.display = "none";
		footer_btn10.style.display = "none";
		list_change(footer_btn6);
	}
	if(pic_num1 > 27 && pic_num1 < 37)
	{
		footer_btn6.style.display = "block";
		footer_btn6.title = "第一页";
		footer_btn7.style.display = "block";
		footer_btn7.title = "第二页";
		footer_btn8.style.display = "block";
		footer_btn8.title = "第三页";
		footer_btn9.style.display = "block";
		footer_btn9.title = "第四页";
		footer_btn10.style.display = "none";
		list_change(footer_btn6);
	}
	if(pic_num1 > 36)
	{
		footer_btn6.style.display = "block";
		footer_btn6.title = "第一页";
		footer_btn7.style.display = "block";
		footer_btn7.title = "第二页";
		footer_btn8.style.display = "block";
		footer_btn8.title = "第三页";
		footer_btn9.style.display = "block";
		footer_btn9.title = "第四页";
		footer_btn10.style.display = "block";
		footer_btn10.title = "第五页";
		list_change(footer_btn6);
	}
	var pic = (pic_num1 > 9)?9:pic_num1;
	var max_span = document.getElementById("main_img").getElementsByTagName("span");
	for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < pic;i++)
	{
		max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i].text + ")";
		max[i].style.cursor = "pointer";
		max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
		if(ie6)
			document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
		// 给快速链接图片上增加文字
		max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i].text);
		var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i].text);
		if(img_url != "")
		{
			if(img_url.indexOf("?") < 0)
				img_url = img_url + "?" + user_date + '&t=' + Math.random();
			else
				img_url = img_url + "&" + user_date + '&t=' + Math.random();
		}
		attr(max[i],"Link",img_url);	
		attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i].text));
		max[i].id = "img_" + i;
	}
}
// 图片根据不同分辨率显示不同个数
function pic_size2(pic_num1)
{
	var footer_btn6 = document.getElementById("footer_btn6");
	var footer_btn7 = document.getElementById("footer_btn7");
	var footer_btn8 = document.getElementById("footer_btn8");
	var footer_btn9 = document.getElementById("footer_btn9");
	var footer_btn10 = document.getElementById("footer_btn10");
	pic_size_num = 2;
	for(i = 10;i < 19;i++)
	{
		document.getElementById("img" + i).style.display = "block";
		if(ie6)
			document.getElementById("img" + i + "_div").style.display = "block";
	}
	document.getElementById("img_left").style.right = 1170 - (140 - imgwidth)*5.5 + "px";
	document.getElementById("main_img").style.marginTop = -208 + (156 - imgheight) + "px";
	document.getElementById("main_img").style.right = 120 - (140 - imgwidth)*5.5 + "px";
	if(pic_num1 < 19)
	{
		footer_btn6.style.display = "none";
		footer_btn7.style.display = "none";
		footer_btn8.style.display = "none";
		footer_btn9.style.display = "none";
		footer_btn10.style.display = "none";
		list_change(footer_btn7);
	}
	if(pic_num1 > 18 && pic_num1 < 37)
	{
		footer_btn6.style.display = "none";
		footer_btn7.style.display = "block";
		footer_btn7.style.right = "605px";
		footer_btn7.title = "第一页";
		footer_btn8.style.display = "block";
		footer_btn8.style.right = "525px";
		footer_btn8.title = "第二页";
		footer_btn9.style.display = "none";
		footer_btn10.style.display = "none";
		list_change(footer_btn7);
	}
	if(pic_num1 > 36)
	{
		footer_btn6.style.display = "block";
		footer_btn6.style.right = "685px";
		footer_btn6.title = "第一页";
		footer_btn7.style.display = "block";
		footer_btn7.style.right = "605px";
		footer_btn7.title = "第二页";
		footer_btn8.style.display = "block";
		footer_btn8.style.right = "525px";
		footer_btn8.title = "第三页";
		footer_btn9.style.display = "none";
		footer_btn10.style.display = "none";
		list_change(footer_btn6);
	}
	var pic = (pic_num1 > 18)?18:pic_num1;
	var max_span = document.getElementById("main_img").getElementsByTagName("span");
	for(var i = 0, max = document.getElementById("main_img").getElementsByTagName("img");i < pic;i++)
	{
		max[i].parentNode.style.backgroundImage = "url(" + xmlDoc2.getElementsByTagName('Ico')[i].text + ")";
		max[i].style.cursor = "pointer";
		max[i].title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
		if(ie6)
			document.getElementById("img" +(i + 1) + "_div").title = unescape(xmlDoc2.getElementsByTagName('Tip')[i].text);
		// 给快速链接图片上增加文字
		max_span[i].innerHTML = unescape(xmlDoc2.getElementsByTagName('Tile')[i].text);
		var img_url = unescape(xmlDoc2.getElementsByTagName('Link')[i].text);
		if(img_url != "")
		{
			if(img_url.indexOf("?") < 0)
				img_url = img_url + "?" + user_date + '&t=' + Math.random();
			else
				img_url = img_url + "&" + user_date + '&t=' + Math.random();
		}
		attr(max[i],"Link",img_url);
		attr(max[i],"title_txt",unescape(xmlDoc2.getElementsByTagName('Tile')[i].text));
		max[i].id = "img_" + i;
	}
}
// 折叠栏伸缩参数
var move = false;
function begin()
{
	var evt = tab_now();
	var ss = "bg_iframe" + evt.id.replace("label_","");
	document.getElementById(ss).style.display = "none";
	if(menu_id)
		menu_id.style.display = "none";
	move = true;
}
function end()
{
	var evt = tab_now();
	var ss = "bg_iframe" + evt.id.replace("label_","");
	document.getElementById(ss).style.display = "block";
	if(menu_id)
		menu_id.style.display = "block";
	move = false;
}
// 折叠栏左右拖动
function moving(e)
{
	var e = e || window.event;
	if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		var x = e.clientX -11;
	else
		var x = e.clientX-12;
	if(move == true)
	{
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
			var len = (document.body.offsetWidth - x - 25);
		else
			var len = (document.body.offsetWidth - x - 18);
		if(len < 0)
			len = 0;
		document.getElementById("out_push").style.left = x;
		if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0)
		{
			document.getElementById("menu_lib").style.right = len + 10 + "px";
			document.getElementById("menu_lib_iframe").style.right = len + 10 + "px";
		}
		else
		{
			document.getElementById("menu_lib").style.right = len + "px";
			document.getElementById("menu_lib_iframe").style.right = len + "px";
		}
		document.getElementById("out").style.width = len + "px";
		if(document.getElementById("out").style.width.replace("px","") < 250)
		{
			document.getElementById("out").style.width = "250px";
			document.getElementById("menu_lib_iframe").style.right = "250px";
			document.getElementById("menu_lib").style.right = "250px";
			document.getElementById("out_push").style.left = "";
			document.getElementById("out_push").style.right = "250px";
		}
	}
}
// 标签页左右按钮划过样式
function span_over(which, ent , what)
{
	if(which == 1)
		ent.style.background = 'url(images/main/top_rig_lef.png) -100px 0 no-repeat';
	if(which == 2)
		ent.style.background = 'url(images/main/top_rig_lef.png) -100px -100px no-repeat';
	if(which == 3)
		ent.style.background = 'url(images/main/top_rig_lef.png) -100px -200px no-repeat';
}
function span_out(which, ent , what)
{
	if(which == 1)
		ent.style.background = 'url(images/main/top_rig_lef.png) 0 0 no-repeat';
	if(which == 2)
		ent.style.background = 'url(images/main/top_rig_lef.png) 0 -100px no-repeat';
	if(which == 3)
		ent.style.background = 'url(images/main/top_rig_lef.png) 0 -200px no-repeat';
}
// 标签左移
function top_left()
{
	var ent = document.getElementById("table_top_div").getElementsByTagName("div");
	var top_len = document.getElementById("table_top_div").getElementsByTagName("div").length;
	var len = Math.abs(getDefaultStyle(ent[0], "left").replace("px",""));
	if(len < 160)
	{
		if(len == 0)
			return false;
		for(i = 0;i < top_len;i++)
		{
			ent[i].style.left = parseInt(getDefaultStyle(ent[i], "left").replace("px","")) + len + "px";
		}
	}
	else
	{
		for(i = 0;i < top_len;i++)
		{
			ent[i].style.left = parseInt(getDefaultStyle(ent[i], "left").replace("px","")) + 160 + "px";
		}
	}
}
// 标签右移
function top_right()
{
	var ent = document.getElementById("table_top_div").getElementsByTagName("div");
	var top_len = document.getElementById("table_top_div").getElementsByTagName("div").length;
	var len = ent[top_len - 1].offsetLeft + ent[top_len - 1].offsetWidth - main2_top_len;
	if(len <= 0)
		return false;
	if(len < 160)
	{
		for(i = 0;i < top_len;i++)
		{
			ent[i].style.left = parseInt(getDefaultStyle(ent[i], "left").replace("px","")) - len + "px";
		}
	}
	else
	{
		for(i = 0;i < top_len;i++)
		{
			ent[i].style.left = parseInt(getDefaultStyle(ent[i], "left").replace("px","")) - 160 + "px";
		}
	}
}
// 退出功能
function edit()
{
	window.open('Server/Logout.aspx?command=redirect', '_self')
}
// 改变快速链接图片间距
function img_change()
{
	var img_width = 140 - imgwidth;
	var img_height = 156 - imgheight;
	var max = document.getElementById("main_img").getElementsByTagName("h1");
	max[1].style.left = 160 - img_width + "px";
	max[2].style.left = 320 - img_width*2 + "px";
	max[15].style.top = 260 - img_height + "px";
	max[16].style.top = 260 - img_height + "px";
	max[17].style.top = 260 - img_height + "px";
	max[15].style.left = 480 - img_width*3 + "px";
	max[16].style.left = 640 - img_width*4 + "px";
	max[17].style.left = 800 - img_width*5 + "px";
	if(ie6)
	{
		document.getElementById("img2_div").style.left = 160 - img_width + "px";
		document.getElementById("img2_d").style.left = 160 - img_width + "px";
		document.getElementById("img3_div").style.left = 320 - img_width*2 + "px";
		document.getElementById("img3_d").style.left = 320 - img_width*2 + "px";
		document.getElementById("img16_div").style.top = 260 - img_height + "px";
		document.getElementById("img16_d").style.top = 260 - img_height + "px";
		document.getElementById("img17_div").style.top = 260 - img_height + "px";
		document.getElementById("img17_d").style.top = 260 - img_height + "px";
		document.getElementById("img18_div").style.top = 260 - img_height + "px";
		document.getElementById("img18_d").style.top = 260 - img_height + "px";
		document.getElementById("img16_div").style.left = 480 - img_width*3 + "px";
		document.getElementById("img16_d").style.left = 480 - img_width*3 + "px";
		document.getElementById("img17_div").style.left = 640 - img_width*4 + "px";
		document.getElementById("img17_d").style.left = 640 - img_width*4 + "px";
		document.getElementById("img18_div").style.left = 800 - img_width*5 + "px";
		document.getElementById("img18_d").style.left = 800 - img_width*5 + "px";
	}
	if(document.body.offsetWidth <= 1780)
	{
		max[3].style.left = 80 - img_width*0.5 + "px";
		max[4].style.left = 240 - img_width*1.5 + "px";
		max[5].style.left = 400 - img_width*2.5 + "px";
		max[6].style.left = 0;
		max[7].style.left = 160 - img_width + "px";
		max[8].style.left = 320 - img_width*2 + "px";
		max[3].style.top = 130 - img_height*0.5 + "px";
		max[4].style.top = 130 - img_height*0.5 + "px";
		max[5].style.top = 130 - img_height*0.5 + "px";
		max[6].style.top = 260 - img_height + "px";
		max[7].style.top = 260 - img_height + "px";
		max[8].style.top = 260 - img_height + "px";
		if(ie6)
		{
			document.getElementById("img4_div").style.left = 80 - img_width*0.5 + "px";
			document.getElementById("img4_d").style.left = 80 - img_width*0.5 + "px";
			document.getElementById("img5_div").style.left = 240 - img_width*1.5 + "px";
			document.getElementById("img5_d").style.left = 240 - img_width*1.5 + "px";
			document.getElementById("img6_div").style.left = 400 - img_width*2.5 + "px";
			document.getElementById("img6_d").style.left = 400 - img_width*2.5 + "px";
			document.getElementById("img7_div").style.left = 0;
			document.getElementById("img7_d").style.left = 0;
			document.getElementById("img8_div").style.left = 160 - img_width + "px";
			document.getElementById("img8_d").style.left = 160 - img_width + "px";
			document.getElementById("img9_div").style.left = 320 - img_width*2 + "px";
			document.getElementById("img9_d").style.left = 320 - img_width*2 + "px";
			document.getElementById("img4_div").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img4_d").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img5_div").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img5_d").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img6_div").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img6_d").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img7_div").style.top = 260 - img_height + "px";
			document.getElementById("img7_d").style.top = 260 - img_height + "px";
			document.getElementById("img8_div").style.top = 260 - img_height + "px";
			document.getElementById("img8_d").style.top = 260 - img_height + "px";
			document.getElementById("img9_div").style.top = 260 - img_height + "px";
			document.getElementById("img9_d").style.top = 260 - img_height + "px";
		}
	}
	if(document.body.offsetWidth > 1780)
	{
		max[9].style.left = 560 - img_width*3.5 + "px";
		max[10].style.left = 720 - img_width*4.5 + "px";
		max[11].style.left = 880 - img_width*5.5 + "px";
		max[9].style.top = 130 - img_height*0.5 + "px";
		max[10].style.top = 130 - img_height*0.5 + "px";
		max[11].style.top = 130 - img_height*0.5 + "px";
		max[3].style.left = 480 - img_width*3 + "px";
		max[4].style.left = 640 - img_width*4 + "px";
		max[5].style.left = 800 - img_width*5 + "px";
		max[3].style.top = 0;
		max[4].style.top = 0;
		max[5].style.top = 0;
		max[12].style.left = 0;
		max[13].style.left = 160 - img_width*1 + "px";
		max[14].style.left = 320 - img_width*2 + "px";
		max[6].style.left = 80 - img_width*0.5 + "px";
		max[7].style.left = 240 - img_width*1.5 + "px";
		max[8].style.left = 400 - img_width*2.5 + "px";
		max[12].style.top = 260 - img_height + "px";
		max[13].style.top = 260 - img_height + "px";
		max[14].style.top = 260 - img_height + "px";
		max[6].style.top = 130 - img_height*0.5 + "px";
		max[7].style.top = 130 - img_height*0.5 + "px";
		max[8].style.top = 130 - img_height*0.5 + "px";
		if(ie6)
		{
			document.getElementById("img10_div").style.left = 560 - img_width*3.5 + "px";
			document.getElementById("img10_d").style.left = 560 - img_width*3.5 + "px";
			document.getElementById("img11_div").style.left = 720 - img_width*4.5 + "px";
			document.getElementById("img11_d").style.left = 720 - img_width*4.5 + "px";
			document.getElementById("img12_div").style.left =  880 - img_width*5.5 + "px";
			document.getElementById("img12_d").style.left =  880 - img_width*5.5 + "px";
			document.getElementById("img10_div").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img10_d").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img11_div").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img11_d").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img12_div").style.top =  130 - img_height*0.5 + "px";
			document.getElementById("img12_d").style.top =  130 - img_height*0.5 + "px";
			document.getElementById("img4_div").style.left =480 - img_width*3 + "px";
			document.getElementById("img4_d").style.left = 480 - img_width*3 + "px";
			document.getElementById("img5_div").style.left = 640 - img_width*4 + "px";
			document.getElementById("img5_d").style.left = 640 - img_width*4 + "px";
			document.getElementById("img6_div").style.left =  800 - img_width*5 + "px";
			document.getElementById("img6_d").style.left =  800 - img_width*5 + "px";
			document.getElementById("img4_div").style.top = 0;
			document.getElementById("img4_d").style.top = 0;
			document.getElementById("img5_div").style.top = 0;
			document.getElementById("img5_d").style.top = 0;
			document.getElementById("img6_div").style.top =  0;
			document.getElementById("img6_d").style.top =  0;
			document.getElementById("img13_div").style.left = 0;
			document.getElementById("img13_d").style.left = 0;
			document.getElementById("img14_div").style.left = 160 - img_width*1 + "px";
			document.getElementById("img14_d").style.left = 160 - img_width*1 + "px";
			document.getElementById("img15_div").style.left =  320 - img_width*2 + "px";
			document.getElementById("img15_d").style.left =  320 - img_width*2 + "px";
			document.getElementById("img13_div").style.top = 260 - img_height + "px";
			document.getElementById("img13_d").style.top = 260 - img_height + "px";
			document.getElementById("img14_div").style.top = 260 - img_height + "px";
			document.getElementById("img14_d").style.top = 260 - img_height + "px";
			document.getElementById("img15_div").style.top =  260 - img_height + "px";
			document.getElementById("img15_d").style.top =  260 - img_height + "px";
			document.getElementById("img7_div").style.left = 80 - img_width*0.5 + "px";
			document.getElementById("img7_d").style.left = 80 - img_width*0.5 + "px";
			document.getElementById("img8_div").style.left = 240 - img_width*1.5 + "px";
			document.getElementById("img8_d").style.left = 240 - img_width*1.5 + "px";
			document.getElementById("img9_div").style.left =  400 - img_width*2.5 + "px";
			document.getElementById("img9_d").style.left =  400 - img_width*2.5 + "px";
			document.getElementById("img7_div").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img7_d").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img8_div").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img8_d").style.top = 130 - img_height*0.5 + "px";
			document.getElementById("img9_div").style.top =  130 - img_height*0.5 + "px";
			document.getElementById("img9_d").style.top =  130 - img_height*0.5 + "px";
		}
	}
}