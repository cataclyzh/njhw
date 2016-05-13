<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
    <script src="${ctx}/app/omc/js/Qmap3d-core-1.1.js" type="text/javascript"></script>
    <script src="${ctx}/app/omc/js/jquery.Qmap3d.js" type="text/javascript"></script>
    <script src="${ctx}/app/omc/js/Xcds.total.js" type="text/javascript"></script>
    <script src="${ctx}/app/omc/js/MainMonitorNew.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>视频监控页面</title>
<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
<style type="text/css">
        div,td,input
        {
        	font-size:12px;
        }
        .ptzBtn
        {
        	width:32px;
        }
        #Select1
        {
            width: 67px;
        }
        #SelectWnd
        {
            width: 70px;
        }
    </style>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js" ></script>
<script type="text/javascript" src="${ctx}/app/omc/js/showModel.js" ></script>

<script type="text/javascript" src="${ctx}/app/portal/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
<script type="text/javascript">
document.domain = "njnet.gov.cn";
</script>
<script type="text/javascript">
	function logout(){
    	var url ="${ctx}/j_spring_security_logout" + '?t=' + Math.random();
    	window.open(url, "_self");
    }
    
	function loadRepair() {

		 showShelter('800','585','${ctx}/app/pro/repairListSimple.act');
	}
	function loadVisitor() {

		 showShelter('800','600','${ctx}/app/visitor/frontReg/noOrderInputSimple.act');
	}
	function deviceInfo() {
		 showShelter('650','600','${ctx}/app/omc/queryDeveiceToView.act');
	}
	function veicleAccessInfo() {
		 showShelter('650','600','${ctx}/app/omc/vehicleAccessInfo.act');
	}

	function toVideoIpSelectA(){
	    var url = "videoIpSelectA.jsp";
	    showShelterOmc('180','450',url);
	}
	function toVideoIpSelectB(){
	    var url = "videoIpSelectB.jsp";
	    showShelterOmc('180','440',url);
	}
	function toVideoIpSelectC(){
	    var url = "videoIpSelectC.jsp";
	    showShelterOmc('180','450',url);
	}
	function toVideoIpSelectD(){
	    var url = "videoIpSelectD.jsp";
	    showShelterOmc('180','470',url);
	}
	function toVideoIpSelectBall3(){
	    var url = "videoIpSelectBall3.jsp";
	    showShelterOmc('280','175',url);
	}
	function toVideoIpSelectBall19(){
	    var url = "videoIpSelectBall19.jsp";
	    showShelterOmc('280','160',url);
	}
	function toVideoIpSelectDR(){
	    var url = "videoIpSelectDR.jsp";
	    showShelterOmc('180','350',url);
	}
	function toVideoIpSelectDRB(){
	    var url = "videoIpSelectDRB.jsp";
	    showShelterOmc('270','250',url);
	}
	function toVideoIpSelectEL(){
	    var url = "videoIpSelectEL.jsp";
	    showShelterOmc('260','265',url);
	}
	function toVideoIpSelectFace(){
	    var url = "videoIpSelectFace.jsp";
	    showShelterOmc('280','185',url);
	}
	function toVideoIpSelectEast(){
	    var url = "videoIpSelectEast.jsp";
	    showShelterOmc('280','110',url);
	}
	
	function getWndSum(){
	    var OCXobj = document.getElementById("PlayViewOCX");
	    return OCXobj.GetWndNum();
	}
	
	function stopRealPlay(id){
	     var OCXobj = document.getElementById("PlayViewOCX");
	     OCXobj.StopRealPlay(id);
	}
	
	function getSelWnd(){
	     var OCXobj = document.getElementById("PlayViewOCX");
	     return OCXobj.GetSelWnd();
	}
	
	function getFreePreviewWnd(){
	     var OCXobj = document.getElementById("PlayViewOCX");
	     return OCXobj.GetFreePreviewWnd();
	}
	
	function isWndPreview(id){
	     var OCXobj = document.getElementById("PlayViewOCX");
	     return OCXobj.IsWndPreview(id);
	}
	
	function startTaskPreviewInWndV11(ip,id){
	    var OCXobj = document.getElementById("PlayViewOCX");
	    OCXobj.StartTask_Preview_InWnd_V11(ip,id);
	}
	
	function login(szUserName,szPassWord,szIP,lPort,lDataFetchType){
	    var OCXobj = document.getElementById("PlayViewOCX");
	    OCXobj. Login_V11(szUserName,szPassWord,szIP,lPort,lDataFetchType);
	}
</script>

<SCRIPT>
    function videoload()
	{
	  StartPlayView(0);
	  StartPlayView(1);
	  StartPlayView(2);
	  StartPlayView(3);
	  
	}
    /*****实时预览******/
    function StartPlayView(id)
    {
        var OCXobj = document.getElementById("PlayViewOCX");
		//OCXobj.SetWndMode(0);
        OCXobj.SelWindow(id);
        strIP = document.getElementById("TextIP" + id).value;
        strPort = document.getElementById("TextPort").value;
        strName = document.getElementById("TextName").value;
        strPwd = "12345";
        ChanNum = document.getElementById("SelectChan").value;
		checkstream = false;
		streamIp = document.getElementById("streamIP").value;
		rtsp = document.getElementById("rtspPort").value;
		deviceType = document.getElementById("SelectDeviceType").value;
		strXML = "<?xml version='1.0'?><Parament><DeviceIP>" + strIP + "</DeviceIP><DevicePort>" + strPort + "</DevicePort><DeviceType>" + deviceType + "</DeviceType><User>"+ strName +"</User><Password>" + strPwd + "</Password><ChannelNum>"+ ChanNum +"</ChannelNum><ProtocolType>0</ProtocolType><StreamType>0</lStreamType>";
		if(checkstream){
			strXML += "<Transmits><Transmit><SrvIp>"+streamIp+":"+rtsp+"</SrvIp></Transmit></Transmits></Parament>";
		}else{
			strXML += "<Transmits></Transmits></Parament>";
		}
		OCXobj.StartTask_Preview(strXML);
    }
    /*****指定窗口实时预览******/
    function StartPlayView_InWnd()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        strIP = document.getElementById("TextIP").value;
        strPort = document.getElementById("TextPort").value;
        strName = document.getElementById("TextName").value;
        strPwd = "12345";//document.getElementById("Textpwd").value;
        ChanNum = document.getElementById("SelectChan").value;
		checkstream = false;//document.getElementById("checkStream").checked;
		streamIp = document.getElementById("streamIP").value;
		rtsp = document.getElementById("rtspPort").value;
		deviceType = document.getElementById("SelectDeviceType").value;
        WndIndex = document.getElementById("SelectWnd").value;
		strXML = "<?xml version='1.0'?><Parament><DeviceIP>" + strIP + "</DeviceIP><DevicePort>" + strPort + "</DevicePort><DeviceType>" + deviceType + "</DeviceType><User>"+ strName +"</User><Password>" + strPwd + "</Password><ChannelNum>"+ ChanNum +"</ChannelNum><ProtocolType>0</ProtocolType><StreamType>0</lStreamType>";
		if(checkstream){
			strXML += "<Transmits><Transmit><SrvIp>"+streamIp+":"+rtsp+"</SrvIp></Transmit></Transmits></Parament>";
		}else{
			strXML += "<Transmits></Transmits></Parament>";
		}
		
		OCXobj.StartTask_Preview_InWnd(strXML,parseInt(WndIndex));
    }
    /*****空闲窗口实时预览******/
    function StartPlayView_Free()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        strIP = document.getElementById("TextIP").value;
        strPort = document.getElementById("TextPort").value;
        strName = document.getElementById("TextName").value;
        strPwd = "12345";//document.getElementById("Textpwd").value;
        ChanNum = document.getElementById("SelectChan").value;
        checkstream = false;//document.getElementById("checkStream").checked;
		streamIp = document.getElementById("streamIP").value;
		rtsp = document.getElementById("rtspPort").value;
		deviceType = document.getElementById("SelectDeviceType").value;
		strXML = "<?xml version='1.0'?><Parament><DeviceIP>" + strIP + "</DeviceIP><DevicePort>" + strPort + "</DevicePort><DeviceType>" + deviceType + "</DeviceType><User>"+ strName +"</User><Password>" + strPwd + "</Password><ChannelNum>"+ ChanNum +"</ChannelNum><ProtocolType>0</ProtocolType><StreamType>0</lStreamType>";
		if(checkstream){
			strXML += "<Transmits><Transmit><SrvIp>"+streamIp+":"+rtsp+"</SrvIp></Transmit></Transmits></Parament>";
		}else{
			strXML += "<Transmits></Transmits></Parament>";
		}
		
		OCXobj.StartTask_Preview_FreeWnd(strXML);
		OCXobj.SetWndMode(0);
    }
    /*****停止所有预览******/
    function StopPlayView()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        OCXobj.StopAllPreview();
    }
    /*****设置抓图格式为JPG******/
    function CatchPicJPG()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        OCXobj.SetCapturParam("C:\\pic",0);
    }
    /*****设置抓图格式为BMP******/
    function CatchPicBMP()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        OCXobj.SetCapturParam("C:\\pic",1);
    }
   
    /*****获取视频参数******/
    function GetVideoEffect()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        retXML = OCXobj.GetVideoEffect();
        var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async="false";
        xmlDoc.loadXML(retXML);
        document.getElementById("TextBright").value = xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue;
        document.getElementById("TextConstrast").value = xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue;
        document.getElementById("TextSaturation").value = xmlDoc.documentElement.childNodes[2].childNodes[0].nodeValue;
        document.getElementById("TextHue").value = xmlDoc.documentElement.childNodes[3].childNodes[0].nodeValue;
    }
    /*****设置视频参数******/
    function SetVideoEffect()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        BrightValue = document.getElementById("TextBright").value;
        ContrastValue = document.getElementById("TextConstrast").value;
        SaturationValue = document.getElementById("TextSaturation").value;
        HueValue = document.getElementById("TextHue").value;
        OCXobj.SetVideoEffect(parseInt(BrightValue),parseInt(ContrastValue),parseInt(SaturationValue),parseInt(HueValue));
    }
    
    </SCRIPT>
    
		<!-- 以下是事件触发函数接口 -->  
		<script language="javascript" for="PlayViewOCX" event="FireWindowsNumber(iSel)">   
			szMsg = "窗口分割消息,窗口数" + iSel;  
			
		</script>

		<script language="javascript" for="PlayViewOCX" event="FireSelectWindow(iSel)"> 
			szMsg = "窗口选择消息,窗口" + iSel;  
			
		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStartRealPlay(iSel)">
			szMsg = "开始预览消息" + iSel;  

		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStopRealPlay(iSel)">   
			szMsg = "停止预览消息" + iSel ;  
			
		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStopPreviewAll()">   
			szMsg = "停止所有预览消息" ;  
			
		</script>
		<script language="javascript" for="PlayViewOCX" event="FireChangeWindow(iFrom,iTo)">   
			szMsg = "互换窗口消息"+ "从" +iFrom+"到"+iTo;  
		</script>
		<script language="javascript" for="PlayViewOCX" event="FireCatchPic(szPath,iWindowID)">   
			szMsg = "抓图"+ "路径" +szPath+"窗口"+iWindowID;  
	
		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStartPlayBack(lWindowID)">  

		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStopPlayBack(lWindowID)">  
			
		</script>

</head>
 
<body class="oper_body" onload="videoload()">
<div class="oper_index">
	<div class="oper_header">
		<div class="oper_header_logo">
	   <div style="float:left;width:280px;height:80px;cursor:pointer;" onclick="location.href='${ctx}/app/integrateservice/init.act';">&nbsp;</div>
	   <div style="float:left;width:160px;height:80px;cursor:pointer;" onclick="location.href='${ctx}/app/omc/newIndex.jsp';">&nbsp;</div>
		</div>
		<div class="oper_header_right">
           	<span class="oper_name">当前用户：<script type="text/javascript">var len = '${_username}';if(len.replace(/(^\s*)|(\s*$)/g, "").length > 6){var text = len.substring(0,6) + "..."}else{text = len}document.write(text);</script></span>       <a class="oper_name_r" onclick="showShelter('780','510','${ctx}/common/userOrgMgr/personInfoChreach.act')" style="cursor:pointer">个人设置</a>       <a onclick="logout();return false" style="cursor:pointer" class="oper_name_i">退出登录</a>
           </div>
	</div>
	<div class="oper_contet" style="height:900px">
		<div class="oper_contet_conter">
               <div class="oper_contet_list oner_ovedr_left">
                   	<div class="oner_ovder_sp">
	                   	<div  style=" color: red ; font-size: 14px;">
			          	&nbsp;&nbsp;&nbsp; 说明：首次使用，请下载并安装&nbsp;<a href="<%=path%>/scripts/cmsocx.exe" title="cmsocx.exe" target="_blank">视频驱动</a></td>
				        </div>
	                    <div style="width:548px;margin-top: 2px;">
				        	<input id="Hidden1" type="hidden" />   
					        <div style="width:1280px;float:left;overflow:hidden; z-index: 510;">
					            <!-- 添加预览控件（需要先在windows下注册） -->
					            <object classid="clsid:D5E14042-7BF6-4E24-8B01-2F453E8154D7" id="PlayViewOCX"  width="1280" height="674" name="ocx"  >
					            </object>
					        </div>
				   		</div>
  				 		<div class="oper_clear"></div>
                    </div>
                    <div class="oner_ovder_lc" style="width:1280px">
                     		<a onclick="javascript:toVideoIpSelectA();" class="oner_ovder_a" style="cursor:pointer">A座</a>
                     		<a onclick="javascript:toVideoIpSelectB();" style="cursor:pointer" class="oner_ovder_a" >B座</a>
                     		<a onclick="javascript:toVideoIpSelectC();" style="cursor:pointer" class="oner_ovder_a" >C座</a>
                     		<a onclick="javascript:toVideoIpSelectD();" class="oner_ovder_a" style="cursor:pointer">D座</a>
                     		<a onclick="javascript:toVideoIpSelectEast();" class="oner_ovder_a" style="cursor:pointer">东门厅</a>
                     		<a onclick="javascript:toVideoIpSelectFace();" class="oner_ovder_a" style="cursor:pointer">人脸抓拍机</a>
                     		<a onclick="javascript:toVideoIpSelectEL();" class="oner_ovder_a" style="cursor:pointer">电梯</a>
                     		<a onclick="javascript:toVideoIpSelectDR();" class="oner_ovder_a" style="cursor:pointer">食堂</a>
                     		<a onclick="javascript:toVideoIpSelectDRB();" class="oner_ovder_a" style="cursor:pointer">食堂厨房</a>
					<!-- <a href="ov.jsp" target="_self" class="oner_ovder_a" style="cursor:pointer">其他监控</a>  -->
                    </div>
              </div>
           </div>
	</div>
	<div class="oper_fooer">
       	市信息中心服务热线：68789555　 服务邮箱：NJIC@njnet.gov.cn<br />市机关物业管理服务中心服务热线：68789666　 服务邮箱：NJIC@njnet.gov.cn
	</div>
</div>
	
	
	    <input type="hidden" name="checkit" value = "10.250.247.158"  id="TextIP0"/>
		<input type="hidden" name="checkit"  value = "10.250.247.159"  id="TextIP1" />
		<input type="hidden" name="checkit" value = "10.250.247.194"  id="TextIP2"/>
		<!-- 
		<input type="hidden" name="checkit"  value = "10.250.247.134"  id="TextIP3"/>
		-->
		<input type="hidden" name="checkit"  value = "10.250.248.134"  id="TextIP3"/>
		
		 
		<input type="hidden" name="checkit" id="TextIP4" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP5" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP6" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP7" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP8" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP9" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP10" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP11" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP12" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP13" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP14" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP15" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP16" onclick="StartPlayView_Free()"/>
         <!--  port:&nbsp;-->
        <input id="TextPort" type="hidden" value="8000" />&nbsp;&nbsp; 
         <!--user:&nbsp;-->
        <input id="TextName" type="hidden" value="admin" />&nbsp;&nbsp;&nbsp; 
          <!--pwd:&nbsp-->
        <input id="TextPwd" type="hidden"  />&nbsp;&nbsp;&nbsp;
         <!--通道列表：-->
		<input id="SelectChan" type="hidden"  value="0" name="D1"/>&nbsp;&nbsp;&nbsp; 
	    <div style="padding: 1px; margin: 1px;">
        <input id="streamIP" type="hidden" value="172.7.123.104" />
       <!-- rtsp端口:&nbsp;-->
        <input id="rtspPort" type="hidden" value="554" />
         <!--设备类型：-->
		<input id="SelectDeviceType" type="hidden" value="10001" />
      </div>
</body>
</html>
