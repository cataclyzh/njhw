<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath %>/app/portal/toolbar/toolbar.js"></script>
<script type="text/javascript" src="<%=basePath %>/app/portal/toolbar/showModel.js"></script>
<script type="text/javascript">
refreshTaskList();
//刷新工作清单
var refreshTaskTimer = setTimeout("refreshTaskList()",1000*30);
//如果有任务弹出任务窗口
var showTaskWindowTimer = setTimeout("showTaskWindow()",1000*30);

//小图标变色
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
		document.getElementById(id).style.background = 'url(<%=basePath%>/app/portal/toolbar/images/top_img2.png) -100px ' + num + ' no-repeat';
	}
	if(which == 1)
	{
		ent.style.color = "#A7A6B9";
		document.getElementById(id).style.background = 'url(<%=basePath%>/app/portal/toolbar/images/top_img2.png) 0 ' + num + ' no-repeat';
	}
	
}
function mission_close(dom, num)
{
	if(num == 0)
		dom.style.background = "url(<%=basePath%>/app/portal/toolbar/images/close.png)";
	if(num == 1)
		dom.style.background = "url(<%=basePath%>/app/portal/toolbar/images/close1.png)";
}

function returnMain()
{
	window.location.href="<%=basePath%>/app/portal/index.act";
}
</script>
<div class="user_mes" id="user_mes">
	<div class="us_bg1" id="us_bg1"></div>
	<div class="user_name" id="user_name">
		<span>${username}
		</span>
	</div>
	<div class="us_bg2" id="us_bg2" title="设置"
		onmouseover="change_col(1,0,'-50px','us_bg2')"
		onmouseout="change_col(1,1,'-50px','us_bg2')" onclick="openPwdDiv()"></div>
	<div class="set" id="set">
		<a title="设置" id="UserInfo" onclick="openPwdDiv()"
			onmouseover="change_col(1,0,'-50px','us_bg2')"
			onmouseout="change_col(1,1,'-50px','us_bg2')" hidefocus>设置</a>
	</div>
	<div class="us_bg4" id="us_bg4" onclick="javascript:playWin('open');" title="任务" onmouseover="change_col(3,0,'-150px','us_bg4')" onmouseout="change_col(3,1,'-150px','us_bg4')"></div>
			<div class="talk" id="talk" onclick="javascript:playWin('open');"><a title="任务" id="Discuss"  onclick="javascript:playWin('open');" onmouseover="change_col(3,0,'-150px','us_bg4')" onmouseout="change_col(3,1,'-150px','us_bg4')" hidefocus>任务</a></div>
			<div id="msg_task_size" onclick="javascript:playWin('open');"  class="header_message" style="display:none;"><span>0</span></div>
			
	<div class="us_bg3" id="us_bg3" title="帮助"
		onmouseover="change_col(4,0,'-100px','us_bg3')"
		onmouseout="change_col(4,1,'-100px','us_bg3')" onclick="open_top(4)"></div>
	<div class="help1" id="help1">
		<a title="帮助" id="help" onclick="open_main2(this, this.id)"
			onmouseover="change_col(4,0,'-100px','us_bg3')"
			onmouseout="change_col(4,1,'-100px','us_bg3')" hidefocus>帮助</a>
	</div>
	
	<div class="us_bg6" id="us_bg6" title="退出"
		onmouseover="change_col(5,0,'-300px','us_bg6')"
		onmouseout="change_col(5,1,'-300px','us_bg6')"
		onclick="edit();return false"></div>
	<div class="edt" id="edt">
		<a style="text-decoration:none;color:#A7A6B9;" href="javascript:void(0)" title="退出" onclick="edit();return false"
			onmouseover="change_col(5,0,'-300px','us_bg6')"
			onmouseout="change_col(5,1,'-300px','us_bg6')" hidefocus>退出</a>
	</div>
	
	<div class="us_bg7" id="us_bg7" title="返回"
		onclick="returnMain();return false"></div>
	<div class="returnMain" id="returnMain">
		<a style="text-decoration:none;color:#A7A6B9;" href="javascript:void(0)" title="进入功能导航页" onclick="returnMain();return false"
			 hidefocus>返回</a>
	</div>
	
</div>
<!-- 修改密码 -->
<div id='companyWin' class='easyui-window' collapsible='false'
	minimizable='false' maximizable='true'
	style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
	closed='true'>
	<iframe id='companyIframe' name='companyIframe'
		style='width: 100%; height: 100%;' frameborder='0'></iframe>
</div>
<!-- 工作清单列表 -->
<div id='user_task_list' style="position:absolute;bottom:40px;right:20px;width:302px;height:183px;background:url('<%=basePath %>/app/portal/toolbar/images/missionlist.png');z-index:9999;display:none">
	<div class="close" onclick="javascript:playWin('close');" title="关闭" onmouseover="mission_close(this,0)" onmouseout="mission_close(this,1)" style="height:20px;width:32px;background:url('<%=basePath %>/app/portal/toolbar/images/close1.png');position:absolute;right:7px;top:1px;"></div>
	<div id ="user_task_list_content" style="width:290px;height:148px;position:absolute;bottom:8px;left:5px;">您当前没有任务!</div>
</div>
<iframe src="" id="user_task_list_iframe" frameborder="0"  class="user_task_list_iframe" style="position:absolute;bottom:42px;right:22px;width:300px;height:181px;z-index:9998;display:none;"></iframe>
<!-- center ope -->
<div id='center_ope' class='easyui-window' collapsible='false'
	minimizable='false' maximizable='true'
	style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
	closed='true'>
	<iframe id='companyIframe' name='companyIframe'
		style='width: 100%; height: 100%;' frameborder='0'></iframe>
</div>
