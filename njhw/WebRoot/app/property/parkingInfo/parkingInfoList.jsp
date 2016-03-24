<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include/meta.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车辆进出管理</title>
<link href="${ctx}/app/property/css/wizard_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/property/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/property/css/block.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/property/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/property/css/css_body.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/app/property/js/carManage.js" type="text/javascript"></script>

</head>
  
  <body onload="loadBeforeCarManage()">
  <div class="fkdj_index">
    <div class="bgsgl_border_left">
	  <div class="bgsgl_border_rig_ht">车辆进出管理</div>
	</div>
	<div class="bgsgl_conter">
		<div class="fkdj_lfrycx">
 			<div class="fkdj_from">
 			<form id="carManageForm" name="carManageForm" action="toParkingInfoListPage.act" method="post">
            	<table width="80%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    
    <td class="fkdj_name">单位名称</td>
    <td><select id="org" name="org">
		<c:forEach items="${orgList}" var="org">
		<option value="${org.orgId }">${org.name }</option>
		</c:forEach>
		</select>
		<select name="carManageOrgName" id="carManageOrgName" class="fkdj_sele_ct">
    <option></option>
        <option>地震局</option>
      </select>
    </td>
    
     <td>      <a class="fkdj_botton_right_two_pointer" onclick="javascript:carFormSubmit()" onmousemove="this.style.background='FFC600'" onmouseout="this.style.background='#8090b4'">查询</a>
     </td> 
       
       <td>      <a class="fkdj_botton_right_two_pointer" onclick="javascript:carManageAddOccur()" onmousemove="this.style.background='FFC600'" onmouseout="this.style.background='#8090b4'">新增</a>
     </td> 
     
       <td>      <a class="fkdj_botton_right_two_pointer" onclick="#" onmousemove="this.style.background='FFC600'" onmouseout="this.style.background='#8090b4'">删除</a>
     </td> 
  </tr>

</table>
</form>
            </div>
        </div>
		<div class="fkdj_lfrycxs">
        <div class="fkdj_sxry_list">
<div class="fkdj_sxrys">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;">
  <tr class="fkdj_sxry_brack">
  	<td width="3%"><input name="carManageTableCheckList" id="carManageTableCheckList" type="checkbox" value="" /></td>
    <td width="8%">序号</td>
    <td width="10%">单位名称</td>
    <td width="8%">车位数</td>
    <td class="fkdj_check_time">操作</td>
  </tr>
  </table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;">
  <c:forEach items="${result}" var="result">
										<tr>
											  	<td width="3%"><input name="carManageTableCheckList1" id="carManageTableCheckList1" type="checkbox" value="" /></td>          

											<td width="8%">
												${result.parkingInfoOrgId }
											</td>
											<td width="10%">
												${result.parkingInfoOrgName }
											</td>
											<td width="8%">
												${result.parkingInfoNumber }
											</td>
											<td class="fkdj_check_time">
						<span class="fkdj_cbox_no" onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color='#8090b4'" onclick="javascript:carManageModifyOccur()">[修改]</span>   
						<span class="fkdj_cbox_no" onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color='#8090b4'" onclick="javascript:carManageViewOccur()">[查看]</span>

											</td>
										</tr>
									</c:forEach>
</table>
</div>
</div>
        
        </div>
<div class="main_peag"><a href="javascript:void(0);"><<</a> <a href="javascript:void(0);"><</a> <a href="javascript:void(0);">1</a> <a href="javascript:void(0);">2</a> <a href="javascript:void(0);">3</a> <a href="javascript:void(0);">4</a> <a href="javascript:void(0);">...</a> <a href="javascript:void(0);">></a> <a href="javascript:void(0);">>></a> </div>
	</div>
	
</div>  




<h:page id="list_panel" width="100%" title="结果列表">
 		<d:table name="page.result" id="row" uid="row" export="true" cellspacing="0" cellpadding="0" class="table">
			<d:col style="width:45" class="display_centeralign" title="选择">
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.PARKINGINFO_ID}" />
			</d:col>
			<d:col property="PARKINGINFO_ID" class="display_centeralign"
				title="序号" />
			<d:col property="PARKINGINFO_ORGNAME" class="display_centeralign"
				title="单位名称" />
			<d:col property="PARKINGINFO_NUMBER" class="display_centeralign"
				title="车位数" />
			<d:col class="display_centeralign" title="操作">
										<span class="display_centeralign"
												onclick="lookParking('${row.PARKINGINFO_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'">
												[查看]
										</span>
										
											<span class="display_centeralign"
												onclick="javascript:carManageModifyOccur()"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'">[修改]
										   </span>
									
										
									</d:col>						
		</d:table>		
	</h:page>
















<!-- -------------------------车辆总数配置Block------------------------- -->
<div id="box_config_car">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >
	    <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">车辆进出管理--车位总数配置页面</div>
		</div>
		<div class="form_right_move">
		
		<form action="" method="post" id="carManageConfigForm" name="carManageConfigForm">
<div class="show_from" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">车位总数：</td>
    <td width="50%"><input name="carManageConfigAll" id="carManageConfigAll"  class="show_inupt" type="text"/>
   
    </td>
     <td width="13px">位</td>
    <td></td>
  </tr>
</table>
</div>
</form>
</div>
</div>
   </div>	
<div class="show_pop_bottom"><div class="show_botton_list"><a href="javascript:void(0);">保存</a> </div><a href="javascript:carManageConfigCancel()">取消</a><div class="clear"></div></div>
</div>

 <div id="back_config_car">
	
</div>	



<!-- -------------------------新增Block------------------------- -->
<div id="box_add_car">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >
	    <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">车辆进出管理--新增页面</div>
		</div>
		<div class="form_right_move">
		
		<form action="addOneParkingInfo.act" method="post" id="carManageAddForm" name="carManageAddForm">
<div class="show_from" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">剩余车位：</td>
    <td width="50%"><input name="carManageAddAll" id="carManageAddAll"  class="show_inupt" type="text"/>
   
    </td>
     <td width="13px">位</td>
    <td></td>
  </tr>
  
   <tr>
    <td width="12%">单位编号：</td>
    <td width="50%"><input name="parkingInfoOrgId" id="parkingInfoOrgId"  class="show_inupt"/>
    </td>
    <td></td>
  </tr>
  
    <tr>
    <td width="12%">单位名称：</td>
    <td width="50%"><select name="parkingInfoOrgName" id="parkingInfoOrgName"  class="show_inupt">
   <option>地震局</option>
   <option>物价局 </option>
   </select>
    </td>
    <td></td>
  </tr>
  
      <tr>
    <td width="12%">分配车位：</td>
    <td width="50%"><input name="parkingInfoNumber" id="parkingInfoNumber"  class="show_inupt" type="text"/>
   
    </td>
     <td width="13px">位</td>
    <td></td>
  </tr>
  
</table>
</div>
</form>
</div>
</div>
   </div>	
<div class="show_pop_bottom"><div class="show_botton_list"><a href="javascript:addCar()">增加</a> </div><a href="javascript:carManageAddCancel()">取消</a><div class="clear"></div></div>
</div>

 <div id="back_add_car">
	
</div>	



<!-- -------------------------修改Block------------------------- -->
<div id="box_modify_car">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >
	    <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">车辆进出管理--修改页面</div>
		</div>
		<div class="form_right_move">
		
		<form action="" method="post" id="carManageModifyForm" name="carManageModifyForm">
<div class="show_from" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">剩余车位：</td>
    <td width="50%"><input name="carManageModifyAll" id="carManageModifyAll"  class="show_inupt" type="text"/>
   
    </td>
     <td width="13px">位</td>
    <td></td>
  </tr>
  
    <tr>
    <td width="12%">单位名称：</td>
    <td width="50%"><input name="carManageModifyOrgName" id="carManageModifyOrgName"  class="show_inupt" type="text"/>

    </td>
    <td></td>
  </tr>
  
      <tr>
    <td width="12%">分配车位：</td>
    <td width="50%"><input name="carManageModifyTo" id="carManageModifyTo"  class="show_inupt" type="text"/>
   
    </td>
     <td width="13px">位</td>
    <td></td>
  </tr>
  
</table>
</div>
</form>
</div>
</div>
   </div>	
<div class="show_pop_bottom"><div class="show_botton_list"><a href="javascript:void(0);">保存</a> </div><a href="javascript:carManageModifyCancel()">取消</a><div class="clear"></div></div>
</div>

 <div id="back_modify_car">
	
</div>	



<!-- -------------------------查看Block------------------------- -->
<div id="box_view_car">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >
	    <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">车辆进出管理--查看页面</div>
		</div>
		<div class="form_right_move">
		
		<form action="" method="post" id="carManageViewForm" name="carManageViewForm">
<div class="show_from" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">剩余车位：</td>
    <td width="50%"><input name="carManageViewAll" id="carManageViewAll"  class="show_inupt" type="text" readonly="readonly" value=""/>
   
    </td>
     <td width="13px">位</td>
    <td></td>
  </tr>
  
    <tr>
    <td width="12%">单位名称：</td>
    <td width="50%"><input name="carManageViewOrgName" id="carManageViewOrgName"  class="show_inupt" type="text" readonly="readonly"/>

    </td>
    <td></td>
  </tr>
  
      <tr>
    <td width="12%">分配车位：</td>
    <td width="50%"><input name="carManageViewTo" id="carManageViewTo"  class="show_inupt" type="text" readonly="readonly"/>
   
    </td>
     <td width="13px">位</td>
    <td></td>
  </tr>
  
</table>
</div>
</form>
</div>
</div>
   </div>	
<div class="show_pop_bottom"><div class="show_botton_list"><a href="javascript:void(0);">确定</a> </div><a href="javascript:carManageViewCancel()">取消</a><div class="clear"></div></div>
</div>

 <div id="back_view_car">
	
</div>	






<div id="layout"></div>
</body>
</html>
