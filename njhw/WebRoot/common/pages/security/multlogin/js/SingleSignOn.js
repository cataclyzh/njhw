// 兼容浏览器: 属性设置
function attr(obj, key, val)
{
	if(val != null)
	{
		obj.setAttribute(key,val);
		return null;
	}
	if(obj.attributes == null)
		return null;
	if(obj.attributes[key] != null)
	{
		return obj.attributes[key].value;
	}
	return null;
}
function login_hover(dom, num, num1)
{
	if(dom.style.backgroundPosition.indexOf('-400') != -1)
			return false;
	if(num == 0)
	{
		
		dom.style.background ="url("+_ctx+"/common/pages/security/multlogin/images/login_main_left_img.png) -200px -" + num1*100 + "px no-repeat";
	}
	else
	{
		dom.style.background ="url("+_ctx+"/common/pages/security/multlogin/images/login_main_left_img.png) 0 -" + num1*100 + "px no-repeat";
	}
}
function login_click(dom, num)
{
	var domAll = document.getElementById("login_main_left").getElementsByTagName("div");
	for(var i = 0; i < domAll.length; i++)
	{
		domAll[i].style.background ="url("+_ctx+"/common/pages/security/multlogin/images/login_main_left_img.png) 0 -" + i*100 + "px no-repeat";
	}
	dom.style.background ="url("+_ctx+"/common/pages/security/multlogin/images/login_main_left_img.png) -400px -" + num*100 + "px no-repeat";
	var bg_dom = document.getElementById("login_main_right");
	bg_dom.style.background = "url("+_ctx+"/common/pages/security/multlogin/images/login_main_right" + num + ".jpg) 0 0 no-repeat";
	var login_main_right_table = document.getElementById("login_main_right_table");
	var login_main_right_table2 = document.getElementById("login_main_right_table2");
	var login_main_right_btn = document.getElementById("login_main_right_btn");
	var login_main_right_btn1 = document.getElementById("cas_submit");
	var login_main_right_btn2 = document.getElementById("reset");
	var verificationCode = document.getElementById("verificationCode");
	if(num == 0)
	{
		login_main_right_table.style.display = "block";
		login_main_right_table2.style.display = "none";
		login_main_right_btn.style.display = "block";
		login_main_right_btn.style.bottom = "40px";
		login_main_right_btn.style.right = "115px";
		login_main_right_btn1.style.display = "block";
		login_main_right_btn2.style.display = "block";
		attr(login_main_right_btn1, "newtype", 0);
		authType = AUTH_TYPE_LOGIN;
		reset();
	}
	if(num == 1)
	{
		login_main_right_table.style.display = "none";
		login_main_right_table2.style.display = "none";
		login_main_right_btn.style.display = "block";
		login_main_right_btn1.style.display = "block";
		login_main_right_btn.style.bottom = "60px";
		login_main_right_btn.style.right = "170px";
		login_main_right_btn2.style.display = "none";
		attr(login_main_right_btn1, "newtype", 1);
		authType = AUTH_TYPE_PERSON_CARD;
	}
	if(num == 2)
	{
		login_main_right_table.style.display = "none";
		login_main_right_table2.style.display = "block";
		login_main_right_btn.style.display = "block";
		login_main_right_btn.style.bottom = "60px";
		login_main_right_btn.style.right = "170px";
		login_main_right_btn1.style.display = "block";
		login_main_right_btn2.style.display = "none";
		attr(login_main_right_btn1, "newtype", 2);
		authType = AUTH_TYPE_PHONE_CODE;
		clear();
	}
	if(num == 3)
	{
		login_main_right_table.style.display = "none";
		login_main_right_table2.style.display = "none";
		login_main_right_btn.style.display = "block";
		login_main_right_btn1.style.display = "block";
		login_main_right_btn.style.bottom = "60px";
		login_main_right_btn.style.right = "170px";
		login_main_right_btn2.style.display = "none";
		attr(login_main_right_btn1, "newtype", 3);
		authType =  AUTH_TYPE_CA;
		accessToCAinit();
	}
}
function loginEnter_hover(dom, num)
{
	if(num == 0)
		dom.style.background ="url("+_ctx+"/common/pages/security/multlogin/images/login_main_right_btn1.png) 0 -100px no-repeat";
	else
		dom.style.background ="url("+_ctx+"/common/pages/security/multlogin/images/login_main_right_btn1.png) 0 0 no-repeat";
}
function loginEnter(dom)
{
	// 	账号登录
	if(attr(dom, "newtype") == 0)
	{
//		$("#cas_submit1").click();
		randImgLogin();
	}
	// 市民卡登录
	if(attr(dom, "newtype") == 1)
	{
		readCard();
	}
	// 手机动态密码登录
	if(attr(dom, "newtype") == 2)
	{
		phoneCodeLogin();
	}
	// CA认证登录
	if(attr(dom, "newtype") == 3)
	{
		doDataProcess();
	}
}