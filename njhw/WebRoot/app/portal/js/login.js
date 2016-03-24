
// 返回最终样式函数，兼容IE和DOM，设置参数：元素对象、样式特性    
function getDefaultStyle(obj, attribute)
{
 	return obj.currentStyle ? obj.currentStyle[attribute] : document.defaultView.getComputedStyle(obj, false)[attribute];    
}  
// 定义验证码全局变量
var chk;
// 初始化
window.onload = function()
{	
	//change_vil();
	//document.getElementById("proving").value = "";
	// 判断单点登录参数是否齐全
	if((Request.QueryString['uid'] && (Request.QueryString['pwd'] || Request.QueryString['pwd_md5'])) || Request.QueryString['sn'])
		re_login();
	else
	{
		document.getElementById("bg_01").style.display = "block";
		document.getElementById("login_loading").style.display = "none";
		// ff获得光标
		document.getElementById("j_username").focus();
	}
}

// 参数明码单点登录
function re_login()
{
	var url = 'Server/User.aspx?command=login&' + window.location.search.replace("?","") + '&t=' + Math.random();
	var xml = GetInfoFromServer(url);
	// 不为空表示信息错误不能登录
	if(xml != "")
	{
		document.getElementById("error_tip").innerHTML = xml;
		if(Request.QueryString['uid'])
			document.getElementById("j_username").value = Request.QueryString['uid'];
		document.getElementById("bg_01").style.display = "block";
		document.getElementById("login_loading").style.display = "none";
		// ff获得光标
		document.getElementById("j_username").focus();
	}
	else
	{
		// redirecturl参数不为空则跳转到指定页面
		if(Request.QueryString['redirecturl'])
		{
			var url = Request.QueryString['redirecturl'] + '?t=' + Math.random();
			window.open(url, '_self');
			return false;
		}
		else
			window.open('main.htm', '_self');
	}
}
var username;
var password;
// 点击进行登陆处理
function login_clk()
{
	document.getElementById("error_tip").innerHTML = "";
	var num = 0;
	username = escape(document.getElementById("j_username").value);
	password = document.getElementById("j_password").value;
	var proving = document.getElementById("proving").value;
	// 用户名为空
	if(username == "")
	{
		document.getElementById("j_username").parentNode.getElementsByTagName("span")[0].style.display = "inline";
		num++;
	}
	// 密码为空
	if(password == "")
	{
		document.getElementById("j_password").parentNode.getElementsByTagName("span")[0].style.display = "inline";
		num++;
	}
	// 验证码为空
	if(proving == "")
	{
		document.getElementById("proving").parentNode.getElementsByTagName("span")[0].style.display = "inline";
		document.getElementById("proving").parentNode.getElementsByTagName("img")[0].style.display = "none";
		num++;
	}
	// 若以上三项有一项为空则不处理
	if(num != 0)
		return false;
	// 验证码不正确
	if(proving.toUpperCase() != chk)
	{
		document.getElementById("proving").parentNode.getElementsByTagName("img")[0].style.display = "none";
		document.getElementById("proving").parentNode.getElementsByTagName("span")[1].style.display = "inline";
		return false;
	}
	document.getElementById("error_tip").innerHTML = "登录中,请等待...";	
	// 登录的xml判断
	var url = 'Server/User.aspx?command=login&uid=' + username + '&pwd=' + password + '&t=' + Math.random();
	var xml = GetInfoFromServerAsyn(url, callback);
	function callback(xml)
	{
		// xml不为空返回错误信息
		if(xml != "")
		{	
			var xmlDoc1 = LoadXml(xml);
			if(xmlDoc1 == null)
			{
				alert(xml);
				return false;
			}
			var xml_num = xmlDoc1.getElementsByTagName("flag")[0].text;
			login_message(xml_num, xmlDoc1);
		}
		// xml为空进行登录判断跳转到指定页面还是main页面
		else
		{
			if(Request.QueryString['redirecturl'])
			{
				var url = Request.QueryString['redirecturl'] + '?t=' + Math.random();
				window.open(url, '_self');
			}
			else
				window.open('main.htm', '_self');
		}
	}
}
// 用户名，密码，验证码失去焦点校验
function leave(evt)
{
	if(img_num == 0)
	{
		if(evt.value == "")
			evt.parentNode.getElementsByTagName("span")[0].style.display = "inline";
		if(evt.value == "" && evt.id == 'proving')
			evt.parentNode.getElementsByTagName("img")[0].style.display = "none";
	}
}
// 用户名，密码，验证码获得焦点校验
function inner(evt)
{
	evt.parentNode.getElementsByTagName("span")[0].style.display = "none";
	if(evt.id == 'proving' && (getDefaultStyle(evt.parentNode.getElementsByTagName("span")[1],"display") != "none" || getDefaultStyle(evt.parentNode.getElementsByTagName("img")[0],"display") == "none"))
	{
		evt.parentNode.getElementsByTagName("span")[1].style.display = "none";
		document.getElementById("vail").src = 'Server/User.aspx?command=validate&code=' + chk + '&t=' + Math.random();
		evt.parentNode.getElementsByTagName("img")[0].style.display = "inline";
	}
}
// 重置按钮
function login_reset()
{
	window.location.reload();
}
// 验证码
function createCode()
{ 
	var code = "";
	var codeLength = 4;//验证码的长度
	var selectChar = new Array(2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');
	for(var i = 0;i < codeLength;i++) 
	{
	   var charIndex = Math.floor(Math.random()*32);
	   code += selectChar[charIndex];
	}
	if(code.length != codeLength)
	{
	   createCode();
	}
	return code;
}
// 刷新验证码
function change_vil()
{
	chk=createCode();
	document.getElementById("vail").src = 'Server/User.aspx?command=validate&code=' + chk + '&t=' + Math.random();
}
// flag参数判断
function login_message(num, xmlDoc)
{
	// flag为5则必须修改密码
	if(num == 5)
	{
		var rand = Math.random();
		var url_menu = "Server/GetOption.aspx?command=setconfig&key=Mxw" + '&t=' + Math.random();
		var xml_menu = GetInfoFromServer(url_menu);
		var url = xml_menu + "/FileAccess.aspx?model=self&FileName=usermanage/EditUserPwd.aspx" + "&pwd=" + password + "&uid=" + username + "&itemid=sys_userid" + '&t=' + Math.random();
		var returnVal = window.showModalDialog(url, '', 'dialogWidth:320px;dialogHeight:220px;help:no;status:no;scrollbar:no');
		if(returnVal == 'yes')
			window.open('main.htm', '_self');
		else
		{
			alert("请必须修改密码");
			return false;
		}
	}
	// flag为-3则表明产品已达最大数
	if(num == -3)
	{
		var sure = confirm("产品已达最大用户数，您有权限注销最早用户继续使用");
		if(sure == true)
		{
			var new_url = "Server/User.aspx?command=kick_user&login_uid=" + username + '&t=' + Math.random();
			var new_md = GetInfoFromServer(new_url);
			login_clk();
		}
		else
			return false;
	}
	// flag为-2则弹出错误信息
	if(num == -2)
	{
		alert(xmlDoc.getElementsByTagName("error")[0].text);
		return false;
	}
	// flag为-1或其他值则在页面写出错误信息
	else if(num <= 0)
	{
		document.getElementById("error_tip").innerHTML = xmlDoc.getElementsByTagName("error")[0].text;
		return false;
	}
}
// 该参数区分鼠标焦点是否在验证码上。1为在上面，0不在上面
var img_num = 0;
function img_in()
{
	img_num = 1;
}
function img_out()
{
	img_num = 0;
}
// 图片异步加载
function load(ent)
{
	ent.style.display = "block";
	document.getElementById("Img1").style.display = "none";
}
// 回车登录
var SubmitOrHidden = function(evt)
{  
	if(evt.keyCode == 13)
	{
		login_clk();
	}
 }  
 if(navigator.userAgent.indexOf("MSIE") <= 0)
	window.document.onkeydown=SubmitOrHidden;