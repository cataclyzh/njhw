function showShelters11(width, height, src,id)
{
	var body =  window.top.document.body;
	var bodyheight = window.top.document.body.scrollHeight + "px";
	var element =  document.createElement("div");
	element.id = "fades";
	element.className = "over_layer";
	element.style.background = "black";
	element.style.position = "absolute";
	element.style.zIndex = 1005;
	element.style.left = 0;
	element.style.top = 0;
	element.style.width = "100%";
	element.style.height = bodyheight;
	element.style.filter = "alpha(opacity=50)"; 
	element.style.opacity = 0.5;  
	body.appendChild(element);
	showIframes11(width, height, src,id);
}

function showIframes11(width, height, src,id)
{
    var body =  window.top.document.body;
	var bodyleft =  body.clientWidth/2 - width/2;
	var bodytop =  body.clientHeight/2 - height/2;
	var element =  document.createElement("div");
	element.innerHTML = "<iframe frameborder='0' width='100%' height='100%' scrolling='no' src='"
						+ src
						+ "'></iframe>"
						+ "<div id='Main_fade_divs' style='height:5px;width:100%;position:absolute;top:0;left:0;z-index:4000;cursor:default;overflow:hidden;'></div>"
						+ "<div onclick='closeIframes11(\""+id+"\")' onmouseover='shoeModel_hover(this, 0)' onmouseout='shoeModel_hover(this, 1)' title='关闭' " // 关闭按钮div
						+ "style='cursor:pointer;z-index:9999;position:absolute;top:-19px;right:-19px;width:37px;height:37px;background:url("+ _ctx + "/app/portal/toolbar/images/close_showModel.png) 0 0 no-repeat'></div>";
	element.id = "Main_fade_iframes";
	element.style.zIndex = 2000;
	element.style.height = height + "px";
	element.style.width = width + "px";
	element.style.position = "absolute";
	element.style.left = "50%";
	element.style.top = "50%";
	element.style.marginLeft = 0 - width/2 + "px";
	element.style.marginTop = 0 - height/2 + "px";
	element.style.background = "#fff";
	body.appendChild(element);
	//Drag.init(document.getElementById("Main_fade_div"),document.getElementById("Main_fade_iframe"))
}

function closeIframes11(id)
{
	deleteDiv("fades");
	deleteDiv("Main_fade_iframes");
	$('#queryForm',parent.window.document).submit();

	//关闭窗口时间,先判断函数是否存在
	if(typeof(eval('frameDialogClose'))=="function") 
		frameDialogClose();

}
