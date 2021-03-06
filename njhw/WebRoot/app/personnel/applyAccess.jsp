﻿﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>门禁闸机申请管理</title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
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
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
		<link href="${ctx}/styles/personinfo/css/table_per.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						门禁闸机申请管理
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 620px">
				    <form id="queryForm" action="queryUserAccess.act" method="post"  autocomplete="off">
						<s:hidden name="page.pageSize" id="pageSize" />
 						<h:panel id="query_panel" width="100%" title="查询条件">	
      						<table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        						<tr>
          							<td class="form_label" width="13%" align="left">
           								<div class="infrom_td">姓名</div>
          							</td>
          							<td width="20%">
           								<s:textfield name="name" id="name" theme="simple" cssStyle="width:150px;" size="18"/>  
          							</td>
          							<td class="form_label" width="13%" align="left">
           								<div class="infrom_td">人员类别</div>
          							</td>
          							<td width="20%">
           								<div style="width:160px;display:inline-block;">
           									<s:select list="#request.userType" headerKey="" headerValue="全部" id="userType" listKey="key" listValue="value" name="userType" cssStyle="width:150px;"/>
           								</div>
         							</td>
         							<td class="form_label" width="13%" align="left">
           								<div class="infrom_td">申请操作</div>
          							</td>
          							<td>
           								<div style="width:160px;display:inline-block;">
           									<s:select list="#request.optType" headerKey="" headerValue="全部" id="opt" listKey="key" listValue="value" name="opt" cssStyle="width:150px;"/>
           								</div>
         							</td>
        						</tr>     
        						<tr>
        							<td class="form_label" width="13%" align="left">
           								<div class="infrom_td">申请状态</div>
          							</td>
          							<td width="20%">
           								<div style="width:160px;display:inline-block;">
           									<s:select list="#application.DICT_APP_STATUS" headerKey="" headerValue="全部"  listKey="dictcode" listValue="dictname"  id="appStatus" name="appStatus" cssStyle="width:150px;"/>
           								</div>
         							</td>
        							<td class="form_label" width="13%" align="left">
           								<div class="infrom_td">申请时间</div>
          							</td>
          							<td width="20%">
           								<input type="text" class="infrom_input" style="width:150px;" onclick="WdatePicker({isShowClear:true,readOnly:true})" name="appTime" id="appTime"/>
    									<img onclick="WdatePicker({el:'appTime'})"
											src="${ctx}/scripts/widgets/My97DatePicker/skin/datePicker.gif"
											width="16" height="22" align="absmiddle" />
          							</td>
          							<td class="form_label" width="13%" align="left" colspan="2">
           								<a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="resetbutton" onclick="resetQueryForm();">重置</a>
            							<a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="searchbutton" onclick="querySubmit();">查询</a>
          							</td>
        						</tr>
      						</table>      
    					</h:panel>
						<h:page id="list_panel" width="100%" buttons="add,del" title="门禁闸机授权列表">		
							<d:table name="page.result" id="row" uid="row" export="false" class="table" requestURI="list.act?exFile=1">	
								<d:col style="width:50px;"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
									<c:if test="${row.APP_STATUS != '1'}">
										<input type="checkbox" name="_chk" id="_chk" class="checkItem" disabled/>
									</c:if>
									<c:if test="${row.APP_STATUS == '1'}">
										<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.NUAC_ID}-${row.LOCK_VER}"/>
									</c:if>
								</d:col>
		    					<d:col property="NAME" class="display_leftalign" title="姓名"/>
		    					<d:col property="USER_TYPE" class="display_leftalign" title="人员类别"/>
		    					<d:col property="APPTIME" class="display_leftalign" title="申请时间"/>
		    					<d:col property="CS_NAME" class="display_leftalign" title="处室"/>
		    					<d:col class="display_leftalign" title="操作">
		    						<c:if test="${row.NUAC_EXP1 == '1'}">
		    							新增
		    						</c:if>
		    						<c:if test="${row.NUAC_EXP1 == '2'}">
		    							更新
		    						</c:if>
		    						<c:if test="${row.NUAC_EXP1 == '3'}">
		    							删除
		    						</c:if>
		    					</d:col>
		    					<d:col class="display_leftalign" title="闸机">
		    						<c:if test="${fn:length(row.GUARD_AUTH) > 20}">
		    							${fn:substring(row.GUARD_AUTH, 0, 17)}...
		    						</c:if>
		    						<c:if test="${fn:length(row.GUARD_AUTH) <= 20}">
		    							${row.GUARD_AUTH}
		    						</c:if>
		    					</d:col>
		    					<d:col class="display_leftalign" title="门禁">
		    						<c:if test="${fn:length(row.ACCESS_AUTH) > 20}">
		    							${fn:substring(row.ACCESS_AUTH, 0, 17)}...
		    						</c:if>
		    						<c:if test="${fn:length(row.ACCESS_AUTH) <= 20}">
		    							${row.ACCESS_AUTH}
		    						</c:if>
		    					</d:col>
		    					<d:col class="display_leftalign" title="申请状态">
		    						<c:if test="${row.APP_STATUS == '1'}">
		    							未确认
		    						</c:if>
		    						<c:if test="${row.APP_STATUS == '2'}">
		    							同意
		    						</c:if>
		    						<c:if test="${row.APP_STATUS == '3'}">
		    							退回
		    						</c:if>
		    						<c:if test="${row.APP_STATUS == '4'}">
		    							注销
		    						</c:if>
		    						<c:if test="${row.APP_STATUS == '5'}">
		    							未确认
		    						</c:if>
		    					</d:col>
								<d:col class="display_centeralign"  media="html" title="详细">
									<a href="javascript:void(0);" onclick="updateAccessApply('${row.NUAC_ID}', '${row.APP_STATUS}')">[详细]</a>&nbsp;		
								</d:col>				
							</d:table>
						</h:page>
					</form>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
	</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#name').val("${map.name}");
		$('#appTime').val("${map.appTime}");
		$('#userType').val("${map.userType}");
		$('#opt').val("${map.opt}");
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		$(this).chk_init(chk_options);
		changebasiccomponentstyle();
		changedisplaytagstyle();
		changecheckboxstyle();
	});
	
	function resetQueryForm() {
		$('#name').val("");
		$('#appStatus').val("");
		$('#appTime').val("");
		$('#userType').val("");
		$('#opt').val("");
	}
	
	function addRecord(){
		var url = "${ctx}/app/per/accessApplyInput.act?status=1";
		openEasyWin("accessInput","新增门锁闸机授权申请",url,"420","440",true, false, 'aiFrame');
	}
	function updateAccessApply(id, status){
		var url = "${ctx}/app/per/accessApplyInput.act?nuacId="+id+"&status="+status;
		openEasyWin("accessInput","修改门锁闸机授权申请",url,"420","440",true, false, 'aiFrame');
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteAccess.act");
					$('#queryForm').submit();
				}
			});		
			
		}
    }
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
</script>
</html>
<s:actionmessage theme="custom" cssClass="success"/>