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

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
			rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	</head>
		<script type="text/javascript">
function lookParking(parkingInfoId){
	var url = "${ctx}/app/pro/lookParking.act?parkingInfoId="+parkingInfoId;
	openEasyWin("orgInput","查看车位信息",url,"750","500",false);
}
function insertParking(){
    var url = "${ctx}/app/pro/insertParkingInfo.act";
    openEasyWin("orgInput","增加车位信息",url,"750","500",false);
    
}
function modifyParking(parkingInfoId){
    var url = "${ctx}/app/pro/modifyParking.act?parkingInfoId="+parkingInfoId;
    openEasyWin("orgInput","修改车位信息",url,"750","500",false);
    
}
function configParking(){
    var url = "${ctx}/app/pro/configParking.act";
    openEasyWin("orgInput","配置车位总数",url,"750","500",false);
    
}
function closeWin(){
    closeEasyWin('orgInput');
}
function deleteData(parkingInfoId){		       
		    easyConfirm('提示', '确定删除？', function(r){
				if (r){
					window.location.href="${ctx}/app/pro/deleteParking.act?parkingInfoId=" + parkingInfoId;
				}
			});			    
			
    	}
    	
    	
    	
	
</script>
<body>
	<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
                <jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
            </jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
			<div class="fkdj_index">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						 车辆进出管理
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				    <form id="queryForm" name="carManageForm" action="toParkingInfoListPage.act" method="post" autocomplete="off">
		<div class="fkdj_lfrycx">
 			<div class="fkdj_from">
 			
            	<table width="50%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    
    <td class="fkdj_name">单位名称</td>
    <td><select id="parkingInfoOrgId" name="parkingInfoOrgId">
		<c:forEach items="${orgList}" var="org">
		<option value="${org.orgId }">${org.name }</option>
		</c:forEach>
		</select></td>
     </tr>
      
     </table>
     <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
    	<div class="fkdj_ch_tabl_es">
		<a class="display_centeralign" onClick=" javascript:querySubmit();" onmousemove="this.style.color = '#ffae00';this.style.cursor='pointer';" onmouseout="this.style.color = '#8090b4';this.style.cursor='pointer';" >查&nbsp;&nbsp;&nbsp;&nbsp;询
       </a>
       </div>
 
 
     </div>
        </div>
    		<div class="fkdj_lfrycxs">
        <div class="fkdj_sxry_list">
<div class="fkdj_sxrys">
        <h:page id="list_panel" width="100%" title="结果列表">
 		<d:table name="page.result" id="row" uid="row" export="false" cellspacing="0" cellpadding="0" class="table">
				<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			
			<d:col property="PARKINGINFO_ORGNAME" class="display_centeralign"
				title="单位名称" />
			<d:col property="PARKINGINFO_NUMBER" class="display_centeralign"
				title="车位数" />
			<d:col class="display_centeralign" title="操作">
										<span class="display_centeralign"
												onclick="lookParking('${row.PARKINGINFO_ID}')"
												onmousemove="this.style.color='#ffae00';this.style.cursor='pointer';"
												onmouseout="this.style.color='#8090b4';this.style.cursor='pointer';">
												[查看]
										</span>
										
											<span class="display_centeralign"
												onclick="modifyParking('${row.PARKINGINFO_ID}')"
												onmousemove="this.style.color='#ffae00';this.style.cursor='pointer';"
												onmouseout="this.style.color='#8090b4';this.style.cursor='pointer';">[修改]
										   </span>
										   
										   	<span class="display_centeralign"
												onclick="deleteData('${row.PARKINGINFO_ID}')"
												onmousemove="this.style.color='#ffae00';this.style.cursor='pointer';"
												onmouseout="this.style.color='#8090b4';this.style.cursor='pointer';">[删除]
										   </span>
									
										
									</d:col>						
		</d:table>		
	</h:page>
	</div>
	</div>
	</div>
	</form>
		<div class="fkdj_botton_ls">
							<a class="fkdj_botton_left" href="javascript:insertParking()">增加</a>
						<a class="fkdj_botton_left" href="javascript:configParking()">车位总数配置</a>
							
						</div>

						    
				    
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>

	</body>
<s:actionmessage theme="custom" cssClass="success"/>
</html>