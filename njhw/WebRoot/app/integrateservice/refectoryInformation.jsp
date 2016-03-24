<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
    <title>实时预览Demo</title>
    <style type="text/css">
        div,td,input{
        	font-size:12px;
        }
        .ptzBtn{
        	width:32px;
        }
        #Select1{
            width: 67px;
        }
        #SelectWnd{
            width: 70px;
        }
    </style>
    
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
		<body onload="videoload()">
		<input type="hidden" name="checkit" value = "10.250.248.170"  id="TextIP0" onClick="StartPlayView(0)" checked >
		<input type="hidden" name="checkit"  value = "10.250.247.169"  id="TextIP1"  onClick="StartPlayView(1)" checked>
		<input type="hidden" name="checkit" value = "10.250.247.171"  id="TextIP2" onClick="StartPlayView(2)" checked>
		<input type="hidden" name="checkit"  value = "10.250.247.172"  id="TextIP3" onClick="StartPlayView(3)" checked>
         <!--  port:&nbsp;-->
        <input id="TextPort" type="hidden" value="8000" />&nbsp;&nbsp; 
         <!--user:&nbsp;-->
        <input id="TextName" type="hidden" value="admin" />&nbsp;&nbsp;&nbsp; 
          <!--pwd:&nbsp-->
        <input id="TextPwd" type="hidden"  />&nbsp;&nbsp;&nbsp;
         <!--通道列表：-->
		<input id="SelectChan" type="hidden"  value="0" name="D1"/>&nbsp;&nbsp;&nbsp; 
        </div>
	    <div style="padding: 1px; margin: 1px;">
        <input id="streamIP" type="hidden" value="172.7.123.104" />
       <!-- rtsp端口:&nbsp;-->
        <input id="rtspPort" type="hidden" value="554" />
         <!--设备类型：-->
		<input id="SelectDeviceType" type="hidden" value="10001" />
            </div>
        <div style="width:860px ;margin-top:-20px">
        <input id="Hidden1" type="hidden" />   
        
        <div  style=" color: red ; font-size: 14px;">
          &nbsp;&nbsp;&nbsp; 说明：首次使用，请下载并安装&nbsp;<a href="<%=path%>/scripts/cmsocx.exe" title="cmsocx.exe" target="_blank">视频驱动</a></td>
        </div>
        
        <div style="width:750px;float:left;overflow:hidden; z-index: 510;">
            &nbsp;&nbsp;&nbsp;<br />
            &nbsp;&nbsp;&nbsp;&nbsp;
            <!-- 添加预览控件（需要先在windows下注册） -->
            <object classid="clsid:D5E14042-7BF6-4E24-8B01-2F453E8154D7"   style = "margin-top: -10px"id="PlayViewOCX"  width="600" height="450" name="ocx"  >
            </object>
        </div>
    </div>
</body>
</html>










