<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>用户注册</title>
    <script type="text/javascript">	
	</script>
  </head>
  
  <body>
  <pre>
  	楼宇统一监控:</br>
 		
  	--人员跟踪<br/>
		----<a href="" target="_self">访客</a><br/>
		----<a href="" target="_self">巡更人员</a><br/>
  		
	--安保设备监控<br/>


  		----<a href="${ctx}/app/buildingmon/sxtMonMain.act" target="_self">摄像头</a><br/>
  		----<a href="${ctx}/app/buildingmon/zjMain.act" target="_self">闸机</a><br/>
  		----<a href="${ctx}/app/buildingmon/mjMain.act" target="_self">门禁</a><br/>

			
	--环境监控:<br/>
		----<a href="" target="_self">co2</a><br/>
  		----<a href="" target="_self">光度</a><br/>
  		----<a href="" target="_self">温度</a><br/>
		----<a href="" target="_self">湿度</a><br/>
		
	--空调、电灯:<br/>
		----<a href="" target="_self">空调</a><br/>
		----<a href="" target="_self">电灯</a><br/>
    --房间活动:<br/>
    --房间分配:<br/>
    
    
     --楼座监控:<br/>
     	----<a href="${ctx}/app/buildingmon/dtMonMain.act" target="_self">楼座监控</a><br/>

  	</pre>
  </body>
</html>
