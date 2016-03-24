<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: QiYanQiang
- Date: 2013-05-03
- Description: CMS内容管理
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>CMS内容管理</title>
		<%@ include file="/common/include/meta.jsp"%>
		<script type="text/javascript">
		$(document).ready(function(){
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
		});

		function reset(){
			var cbox=$("input:checked[name='_chk']");        
			$.each(cbox, function(i,item){
			   	if($(this).parent().parent().attr("class") == "oddselected"){
			   		$(item).parent().parent().attr("class","odd");
			   	}
				if($(this).parent().parent().attr("class") == "evenselected"){
			 		$(item).parent().parent().attr("class","even");
				}					    
		    });	
			$("#queryForm").resetForm();
		}
		
		function querySubmit() {
			$('#queryForm').attr("action","listArticle.act");
			$('#queryForm').submit();
		}
	</script>
	</head>
	<body topmargin="0" leftmargin="0" rightmargin="0">
		<form id="queryForm" action="${ctx}/app/cms/channelcms/listArticle.act" method="post" autocomplete="off">
			<h:panel id="query_panel" width="100%" title="查询条件">
				<table align="center" id="hide_table" border="0" width="100%" class="form_table">
					<input id="mid" name="mid" type="hidden" value="${channel.id}"/>
					<tr>
						<td class="form_label" width="20%" align="right">标题： </td>
						<td width="40%" align="left">
							<s:textfield name="title" id="title" theme="simple" size="30" />
						</td>
						<c:if test="${channel.id == '1'}">
							<td class="form_label" width="20%" align="right">状态： </td>
							<td><s:select list="#application.DICT_CMS_CART_FLAG" headerKey="" headerValue="全部" listKey="dictcode" listValue="dictname" name="cartFlag"/></td>
						</c:if> 
					</tr>
					<tr>
						<td colspan="4" class="form_bottom">
						<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton"
								iconCls="icon-search"  onclick="querySubmit();">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton"
								iconCls="icon-reload" onclick="reset();">重置</a>
						</td>
					</tr>
				</table>
			</h:panel>
			<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">
				<d:table name="page.result" id="row" uid="row" export="false"
					class="table" requestURI="list.act?exFile=1">
					<d:col  title="序号" class="display_centeralign" style="width:60px;">
						${row_rowNum+((page.pageNo-1)*page.pageSize)}
					</d:col>
					<d:col class="display_centeralign" style="width:70px;"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
						<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.ID}"/>
					</d:col>
					<d:col property="TITLE" class="display_leftalign"  title="标题" />
					<c:if test="${channel.id == '3'}">
						<d:col property="WEIGHT" class="display_centeralign" title="排序" style="width:45px" />
					</c:if>
					<c:if test="${channel.id != '3'}">
						<d:col property="EDITTIME" class="display_centeralign" title="发布时间" style="width:200px" format="{0,date,yyyy-MM-dd }"/>
					</c:if>
					<c:if test="${channel.id == '1'}">
						${row.CART_FLAG }	
						${row.MID }
						<d:col property="CART_FLAG" dictTypeId="CMS_CART_FLAG" class="display_centeralign" title="状态"/>
					</c:if>
					<d:col class="display_centeralign" media="html" title="操作" style="width:100px">
						<c:if test="${row.MID != '1'}">
							<a href="javascript:void(0);" onclick="updateRecord('${row.ID}')">[修改]</a>&nbsp;	
						</c:if>
						<c:if test="${row.MID == '1' && row.CART_FLAG == '0'}">
							<a href="javascript:void(0);" onclick="updateRecord('${row.ID}')">[修改]</a>&nbsp;	
						</c:if>
						<a href="javascript:void(0);" onclick="show('${row.ID}')">[查看]</a>&nbsp;	
					</d:col>
				</d:table>
			</h:page>
		</form>
		
	<script type="text/javascript">
	function show(id){
		var url = "${ctx}/app/cms/channelcms/searchCmsDetail.act?id="+id;
		openEasyWin("DetailId","${channel.name}",url,"700","450",true);
	}
	function addRecord(){
		var url = "${ctx}/app/cms/channelcms/inputArticle.act?mid=${channel.id}";
		openEasyWin("winId","新增${channel.name}",url,"920","500",true);
	}
	function updateRecord(id){
		var url = "${ctx}/app/cms/channelcms/inputArticle.act?mid=${channel.id}&cmsId="+id;
		openEasyWin("winId","修改${channel.name}",url,"920","500",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteArticle.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success" />


