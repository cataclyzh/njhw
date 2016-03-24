window.onload = inits;
function inits()
{
	var LinkDom = document.getElementById("main_left_main1").getElementsByTagName("a");
	var len = LinkDom.length;
	for(var i = 0; i < len; i++)
	{
		LinkDom[i].onmouseover = function()
		{
			this.style.background = "url(pageIndex/images/" + this.className + "_hover.png)";
		}
		LinkDom[i].onmouseout = function()
		{
			this.style.background = "url(pageIndex/images/" + this.className + ".png)";
		}
	}
}
function tab(num, dom)
{
	var divDom = document.getElementById("main_left_header").getElementsByTagName("div");
	var len = divDom.length;
	for(var i = 0; i < len; i++)
	{
		if(divDom[i].id == dom.id)
		{
			divDom[i].style.background = "url(pageIndex/images/main_left_header_div_click.png)";
			divDom[i].getElementsByTagName("span")[0].style.fontWeight = "bold";
		}
		else
		{
			divDom[i].style.background = "none";
			divDom[i].getElementsByTagName("span")[0].style.fontWeight = "100";
		}
	}
	var position_dom1 = document.getElementById("main_left_main1"),
	position_dom2 = document.getElementById("main_left_main2"),
	position_dom3 = document.getElementById("main_left_main3"),
	search_dom = document.getElementById("main_left_search_main"),
	report_dom = document.getElementById("main_left_report");
	// 快速定位
	if(num == 0)
	{
		position_dom1.style.display = "block";
		position_dom2.style.display = "block";
		position_dom3.style.display = "block";
		search_dom.style.display = "none";
		report_dom.style.display = "none";
	}
	// 搜索定位
	if(num == 1)
	{
		position_dom1.style.display = "none";
		position_dom2.style.display = "none";
		position_dom3.style.display = "none";
		search_dom.style.display = "block";
		report_dom.style.display = "none";
	}
	// 报警处理
	if(num == 2)
	{
		position_dom1.style.display = "none";
		position_dom2.style.display = "none";
		position_dom3.style.display = "none";
		search_dom.style.display = "none";
		report_dom.style.display = "block";
	}
}
// 全屏与隐藏
function push()
{
	document.getElementById("main_left").style.display = "none";
	document.getElementById("main_right").style.width = "1332px";
	document.getElementById("main_right").style.borderLeft = "1px solid #87aaed";
	document.getElementById("main_right_header").style.width = "1332px";
	document.getElementById("main_right_main").style.width = "1332px";
	document.getElementById("push").style.display = "none";
	document.getElementById("show").style.display = "block";
}
function show()
{
	document.getElementById("main_left").style.display = "block";
	document.getElementById("main_right").style.width = "1010px";
	document.getElementById("main_right").style.borderLeft = "none";
	document.getElementById("main_right_header").style.width = "1010px";
	document.getElementById("main_right_main").style.width = "1010px";
	document.getElementById("push").style.display = "block";
	document.getElementById("show").style.display = "none";
}