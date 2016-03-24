<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>用户注册</title>
    <script type="text/javascript">	
	</script>
  </head>
  
  <body>
  <pre>
  	运营管理中心:</br>
		--<a href="${ctx}/app/portal/portal.act" target="_self">portal</a><br/>
  	--访客管理<br/>
		----<a href="${ctx}/app/caller/login.act" target="_self">互联网用户登录</a><br/>
		----<a href="${ctx}/app/caller/regUser.act" target="_self">用户注册</a><br/>
  		----<a href="${ctx}/app/caller/userList.act" target="_self">用户列表+个人信息维护</a><br/>
  	        ----<a href="${ctx}/app/caller/init.act" target="_self">访客跟踪</a><br/>
  	        ----<a href="${ctx}/app/caller/initBlackList.act" target="_self">访客管理(黑名单管理)</a><br/>
  	        ----<a href="${ctx}/app/caller/init.act" target="_self">访客统计报表--(待公司报表实现)</a><br/>
		----<a href="${ctx}/app/caller/listOrders.act" target="_self">预约查询+ 访客预约</a><br/>
		----<a href="${ctx}/app/caller/init.act" target="_self">访客统计报表</a><br/>
		----<a href="${ctx}/app/myvisit/init.act" target="_self">我的来访</a><br/>
		----<a href="${ctx}/app/prebyvisit/byVisitInput.act" target="_self">被访者预约</a><br/>
		----<a href="${ctx}/app/visitor/visitorListIn.act" target="_self">访客登记</a><br/>
		----<a href="${ctx}/app/visitor/noOrderInput.act target="_self">未预约访客登记</a><br/>
    --人员管理<br/>
          ----<a href="${ctx}/app/per/objFrame.act" target="_self">资源树管理</a><br/>
          ----<a href="${ctx}/app/per/initOrg.act" target="_self">部门电话号码的分配</a><br/>
          ----<a href="${ctx}/app/per/initDoorBar.act" target="_self">部门门禁分配</a><br/>
    --物业管理中心:<br/>
  	----<a href="${ctx}/app/pro/init.act" target="_self">报修单列表</a><br/>
  	----<a href="${ctx}/app/pro/showInput.act" target="_self">一键报修</a><br/>
  	----<a href="${ctx}/app/pro/init.act" target="_self">我的报修=====cjw</a><br/>
  		----<a href="${ctx}/app/pro/repair.act" target="_self">一键报修=====cjw</a><br/>

  		----<a href="${ctx}/app/personnel/addPersonnelExinfo.act" target="_self">人员扩展信息</a><br/>
  		
	楼宇监控<br/>
		
	物业管理中心:<br/>
		----<a href="${ctx}/app/pro/showInput.act" target="_self">一键报修</a><br/>
  		----<a href="${ctx}/app/pro/init1.act" target="_self">报修单列表</a><br/>
  		----<a href="${ctx}/app/pro/showStatistics.act" target="_self">报修统计</a><br/>
		----<a href="${ctx}/app/pro/init.act" target="_self">我的报修=====cjw</a><br/>
		----<a href="${ctx}/app/pro/repair.act" target="_self">一键报修=====cjw</a><br/>
		----<a href="${ctx}/app/buildingmon/initBuild.act" target="_self">房间分配情况</a><br/>
		----<a href="${ctx}/app/visitor/inputExitCard.act" target="_self">退卡</a><br/>
	管理局(物业):<br/>
		----<a href="${ctx}/app/personnel/init.act" target="_self">车牌号分配信息</a><br/>
		----<a href="${ctx}/app/room/init1.act" target="_self">房间分配信息</a><br/>
		----<a href="${ctx}/app/per/listTcIpTels.act" target="_self">IP电话管理=====cjw</a><br/>
		
	<a href="${ctx}/app/portal/building.act" target="_self">楼宇监控统一监控</a><br/>
  	</pre>
  </body>
</html>
