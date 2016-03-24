//说明：1.使用showShelter函数来生成弹出框.
//      2.showShelter(width,height,src)前两个参数分别控制高宽,第三个参数控制iframe地址
//      3.必须给需要引用改iframe的页面的images文件夹中添加close_showModel.png
// 显示遮挡层以及iframe
function showShelterOmc(width, height, src)
{
	var body =  window.top.document.body;
	var bodyheight = window.top.document.body.scrollHeight + "px";
	var element =  document.createElement("div");
	element.id = "fade";
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
	showIframeOmc(width, height, src);
}

function showShelter1(width, height, src,id)
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
	showIframe1(width, height, src,id);
}

function showIframe1(width, height, src,id)
{
    var body =  window.top.document.body;
	var bodyleft =  body.clientWidth/2 - width/2;
	var bodytop =  body.clientHeight/2 - height/2;
	var element =  document.createElement("div");
	element.innerHTML = "<iframe id='iframeReLoad' frameborder='0' width='100%' height='100%' scrolling='no' src='"
						+ src
						+ "'></iframe>"
						+ "<div id='Main_fade_divs' style='height:5px;width:100%;position:absolute;top:0;left:0;z-index:4000;cursor:default;overflow:hidden;'></div>"
						+ "<div onclick='closeIframe1(\""+id+"\")' onmouseover='shoeModel_hover(this, 0)' onmouseout='shoeModel_hover(this, 1)' title='关闭' " // 关闭按钮div
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

function closeIframe1(id)
{
	//编辑个人通讯录专用关闭刷新按钮，
	if("newTreeAddressListClose" == id){
		if(parent.parent.document.getElementById("iframeReLoad") != null){
			var location = parent.parent.document.getElementById("iframeReLoad");
		 	location.src = location.src;
		}
	}
	deleteDiv("fades");
	deleteDiv("Main_fade_iframes");
	//关闭窗口时间,先判断函数是否存在
	if(typeof(eval('frameDialogClose'))=="function") 
		frameDialogClose();
}

function showIframeOmc(width, height, src)
{
    var body =  window.top.document.body;
	var bodyleft =  body.clientWidth/2 - width/2;
	var bodytop =  body.clientHeight/2 - height/2;
	var element =  document.createElement("div");
	element.innerHTML = "<iframe id='iframeReLoad' frameborder='0' width='100%' height='100%' scrolling='no' src='"
						+ src
						+ "'></iframe>"
						+ "<div id='Main_fade_div' style='height:5px;width:100%;position:absolute;top:0;left:0;z-index:4000;cursor:default;overflow:hidden;'></div>"
						+ "<div onclick='closeIframe()' onmouseover='shoeModel_hover(this, 0)' onmouseout='shoeModel_hover(this, 1)' title='关闭' " // 关闭按钮div
						+ "style='cursor:pointer;z-index:9999;position:absolute;top:-19px;right:-19px;width:37px;height:37px;background:url("+ _ctx + "/app/portal/toolbar/images/close_showModel.png) 0 0 no-repeat'></div>";
	element.id = "Main_fade_iframe";
	element.style.zIndex = 2000;
	element.style.height = height + "px";
	element.style.width = width + "px";
	element.style.position = "absolute";
	element.style.left = "18%";
	element.style.top = "50%";
	element.style.marginLeft = 0 - width/2 + "px";
	element.style.marginTop = 0 - height/2 + "px";
	element.style.background = "#fff";
	body.appendChild(element);
	//Drag.init(document.getElementById("Main_fade_div"),document.getElementById("Main_fade_iframe"))
}

function frameDialogClose() {

}

function closeIframe()
{
	deleteDiv("fade");
	deleteDiv("Main_fade_iframe");
	//关闭窗口时间,先判断函数是否存在
	if(typeof(eval('frameDialogClose'))=="function") 
		frameDialogClose();
}
// 删除div元素
function deleteDiv(id)
{
  var my = document.getElementById(id);
  if (my != null)
      my.parentNode.removeChild(my);
}
// 滑动效果
function shoeModel_hover(dom, num)
{
	if(num == 0)
		dom.style.background = "url('" + _ctx + "/app/portal/toolbar/images/close_showModel.png') 0 -100px no-repeat";
	else
		dom.style.background = "url('" + _ctx + "/app/portal/toolbar/images/close_showModel.png') 0 0 no-repeat";
}
// 拖拽对象
var Drag={
    "obj":null,
	"init":function(handle, dragBody, e){
		if (e == null) {
			handle.onmousedown=Drag.start;
		}
		handle.root = dragBody;
		
		if(isNaN(parseInt(handle.root.style.left)))handle.root.style.left="0px";
		if(isNaN(parseInt(handle.root.style.top)))handle.root.style.top="0px";//确保后来能够取得top值
		handle.root.onDragStart=new Function();
		handle.root.onDragEnd=new Function();
		handle.root.onDrag=new Function();
		if (e !=null) {
			var handle=Drag.obj=handle;
			e=Drag.fixe(e);
			var top=parseInt(handle.root.style.top);
			var left=parseInt(handle.root.style.left);
			handle.root.onDragStart(left,top,e.pageX,e.pageY);
			handle.lastMouseX=e.pageX;
			handle.lastMouseY=e.pageY;
			document.onmousemove=Drag.drag;
			document.onmouseup=Drag.end;
		}
	},
	"start":function(e){
		var handle=Drag.obj=this;
		this.style.height="100%";
		e=Drag.fixEvent(e);
		var top=parseInt(handle.root.style.top);
		var left=parseInt(handle.root.style.left);
		//一般情况下 left top 在初始的时候都为0
		handle.root.onDragStart(left,top,e.pageX,e.pageY);
		handle.lastMouseX=e.pageX;
		handle.lastMouseY=e.pageY;
		document.onmousemove=Drag.drag;
		document.onmouseup=Drag.end;
		return false;
	},	
	"drag":function(e){//这里的this为document 所以拖动对象只能保存在Drag.obj里
		e=Drag.fixEvent(e);
		var handle=Drag.obj;
		var mouseY=e.pageY;
		var mouseX=e.pageX;
		var top=parseInt(handle.root.style.top);
		var left=parseInt(handle.root.style.left);//这里的top和left是handle.root距离浏览器边框的上边距和左边距
		
		var currentLeft,currentTop;
		currentLeft=left+mouseX-handle.lastMouseX;
		currentTop=top+(mouseY-handle.lastMouseY);
		
		//上一瞬间的上边距加上鼠标在两个瞬间移动的距离 得到现在的上边距
		
		handle.root.style.left=currentLeft +"px";
		handle.root.style.top=currentTop+"px";
		//更新当前的位置
		
		handle.lastMouseX=mouseX;
		handle.lastMouseY=mouseY;
		
		//保存这一瞬间的鼠标值 用于下一次计算位移
		
		handle.root.onDrag(currentLeft,currentTop,e.pageX,e.pageY);//调用外面对应的函数
		return false;
	},
	"end":function(){
		document.onmousemove=null;
		document.onmouseup=null;
		Drag.obj.root.onDragEnd(parseInt(Drag.obj.root.style.left),parseInt(Drag.obj.root.style.top));
		Drag.obj.style.height = "5px"
		Drag.obj=null;
		
	},
	"fixEvent":function(e){//格式化事件参数对象
		if(typeof e=="undefined")e=window.event;
		if(typeof e.layerX=="undefined")e.layerX=e.offsetX;
		if(typeof e.layerY=="undefined")e.layerY=e.offsetY;
		if(typeof e.pageX == "undefined")e.pageX = e.clientX + document.body.scrollLeft - document.body.clientLeft;
		if(typeof e.pageY == "undefined")e.pageY = e.clientY + document.body.scrollTop - document.body.clientTop;
		return e;
	}
};


function showGuide(width, height, src)
{
	var body =  window.top.document.body;
	var bodyheight = window.top.document.body.scrollHeight + "px";
	var element =  document.createElement("div");
	element.id = "fade";
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
	showGuideIframe(width, height, src);
}
function showGuideIframe(width, height, src)
{
	
	//showGuide('800', '705', _ctx + '/app/guide/guide.html');
	
	var src = _ctx + '/app/guide/guide.html';
    var body =  window.top.document.body;
	var bodyleft =  body.clientWidth/2 - width/2;
	var bodytop =  body.clientHeight/2 - height/2;
	
	var element =  document.createElement("div");
	element.innerHTML = "<iframe frameborder='0' width='100%' height='100%' scrolling='no' src='"
						+ src
						+ "'></iframe>"
						+ "<div id='Main_fade_div' style='height:5px;width:100%;position:absolute;top:0;left:0;z-index:4000;cursor:default;overflow:hidden;'></div>"
						+ "<div onclick='closeIframe()' onmouseover='shoeModel_hover(this, 0)' onmouseout='shoeModel_hover(this, 1)' title='关闭' " // 关闭按钮div
						+ "style='cursor:pointer;z-index:9999;position:absolute;top:-12px;right:-18px;width:37px;height:37px;background:url("+ _ctx + "/app/portal/toolbar/images/close_showModel.png) 0 0 no-repeat'></div>";
	element.id = "Main_fade_iframe";
	element.style.zIndex = 2000;
	element.style.height = height + "px";
	element.style.width = width + "px";
	element.style.position = "absolute";
	element.style.left = "50%";
	element.style.top = "50%";
	element.style.marginLeft = 0 - width/2 + "px";
	element.style.marginTop = 0 - height/2 + 20 + "px";
	//element.style.marginTop = 0 - height/2 + ($(window).height())*0.02 + "px";
	element.style.background = "#fff";
	body.appendChild(element);
	//Drag.init(document.getElementById("Main_fade_div"),document.getElementById("Main_fade_iframe"))
}


