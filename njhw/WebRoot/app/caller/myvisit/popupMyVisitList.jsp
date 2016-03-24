<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-18 10:12:11
- Description: 来访列表
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>待处理来访列表</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body style="height:100%;width:100%;" class="easyui-layout">
   <div region="north" title="访客详情" style="padding:0px;background:#fafafa;overflow:hidden;">
		<div id="center" title="访客详情" style="width:100%;height:330px;padding:0px;background:#fafafa;overflow:hidden;">
			<iframe scrolling="auto" frameborder="0" name="ifrmOrgList" id="ifrmObjList" src="${ctx}/common/include/body.jsp" style="width:100%;height:100%;padding:0px;"></iframe>
		</div>
	</div>
	
	<div region="center" split="true" title="待处理来访" style="width:100%;height:auto;background:#fafafa;padding:0px;overflow:hidden;">
		<form  id="queryForm" action="queryPopup.act" method="post" autocomplete="off" >
			<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
				<d:col style="width:30" title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
			    <d:col property="VI_NAME" class="display_leftalign"   title="访客名称"/>
			    <d:col property="USER_NAME" class="display_leftalign" title="受访者名称"/>
			    <d:col property="ORG_NAME" class="display_leftalign" title="部门"/>
			    <d:col property="VSST"   class="display_centeralign" title="开始时间"/>
			    <d:col property="VSET"   class="display_centeralign" title="结束时间"/>
				<d:col class="display_centeralign"  media="html" title="操作">
					<input type="hidden" value="${row.VS_ID}" class="hvsid"/>
					<a href="javascript:void(0);" onclick="show('${row.VS_ID}')">[查看]</a>&nbsp;
				</d:col>
			</d:table>
	   </form>
   </div>

<script type="text/javascript">
	$(function(){
		if ($(".hvsid").first().val() != null && $(".hvsid").first().val() != "") {
			show($(".hvsid").first().val());
		} else {
			//alert("暂无来访");
			closeEasyWin('winVisitPopup');
		}
    })

    function show(vsid){
    	var url = "${ctx}/app/myvisit/showPopupOpt.act?vsid="+vsid;
    	$("#ifrmObjList").attr("src",url);
    }
</script>
</body>
</html>