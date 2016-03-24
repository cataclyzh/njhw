<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: sqs
- Date: 2013-3-19
- Description: IP电话分配
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/metaIframe.jsp" %>
	<title>IP电话分配</title>	
	
		<script type="text/javascript"><!--
		$(document).ready(function(){
			var chk_options = {
				all:'#checkAll',
				item:'.checkItem'
			};
			
			showAllTel();
			//var orgId = '${page.result[0].ORG_ID}';
			//var orgName = '${page.result[0].NAME}';
			//ipTellAssign(orgId, orgName);
		});
	</script>
<style>

</style>
</head>
<body style="background: #fff;">
	
			

    	<div class="bgsgl_border_left">
	  	<div class="bgsgl_border_right">电话传真分配</div>
		</div>	
	<div class="bgsgl_conter" style="min-height: 660px">
	<input type="hidden" id="sel_org_id" />
	<input type="hidden" id="sel_org_name" />
	
	<div title="机构列表"
				style="width: 30%; height: 660px; padding: 0px; overflow: auto; float: LEFT;">
	  	<form id="queryForm" action="initOrgan.act" method="post"  autocomplete="off">
	  	<div style="height:650px;overflow:auto;">
					<div style="width:100%;height:30px;color:#808080;font-size:10pt;font-weight:bold;background:#f6f5f1;">
						<div style="height:10px;"></div>
						<span style="margin-left:24px;float:left;">机构列表</span>
						<a href="javascript:void(0);" style="float:right;"
									onclick="showAllTel()">查看全部</a>
					</div>
			<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
				<d:col style="width:30" title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
			    <d:col property="NAME" class="display_centeralign" title="机构名称"/>
			    <d:col class="display_centeralign"  media="html" title="操作">	
					<a href="javascript:void(0);" style="display:block;width:40px" onclick="ipTellAssign('${row.ORG_ID}','${row.NAME}')">[分配]</a>&nbsp;	
				</d:col>
				
			</d:table>
	  	</div>
		</form>
	</div>

	<div  title="电话传真分配" style="padding: 0px; background: #fff; overflow: hidden; float: LEFT; height: 660px; width: 70%;">
		<div id="center" title="电话传真分配" style="height: 100%; padding-left: 10px; overflow: hidden;">
			<iframe scrolling="no" frameborder="0" name="ifrmOrgList" id="ifrmObjList" src="${ctx}/common/include/body.jsp" style="overflow: hide;width:100%;height:100%;padding:0px;background-color:rgb(246, 245, 241);"></iframe>
		</div>
	</div>
	 <div class="bgsgl_clear"></div>
	</div>
	<div class="bgsgl_clear"></div>

	
<script type="text/javascript">

	function ipTellAssign(orgId,orgName){
    	//var url = "${ctx}/app/per/ipTellAssign.act?orgId="+$("#orgId").val();
    	var url = "${ctx}/app/per/ipTellAssign.act?orgId="+orgId;
    	$("#ifrmObjList").attr("src",url);
    	$("#sel_org_id").val(orgId);
    	$("#sel_org_name").val(orgName);
    }

	function addRecord(){
		var url = "${ctx}/app/per/ipTellAssign.act?orgId="+$("#orgId").val();
		openEasyWin("winId","IP电话分配",url,"800","500",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteIpTell.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
    function showAllTel()
    {
    	var url = "${ctx}/app/per/ipTellAssignAll.act?orgId="+70;
    	$("#ifrmObjList").attr("src",url);
    }
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
