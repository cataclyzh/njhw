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
			//回显示shuju
			$("#mid").val(${map.mid});
			$("#title").val(${map.title});
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
			
	</script>
		<style>
</style>
	</head>
	<body topmargin="0" leftmargin="0" rightmargin="0"  style="min-width: 1000px;">
		<form id="queryForm" action="listArticle.act" method="post"
			autocomplete="off">
			<h:panel id="query_panel" width="100%" title="查询条件">
				<table align="center" id="hide_table" border="0" width="100%"
					class="form_table">
					<tr>
						<td class="form_label" width="20%" align="left">
							标题：
						</td>
						<td width="30%">
							<s:textfield name="title" id="title" theme="simple" size="18" />
						</td>
						<td class="form_label">
							栏目名称：
						</td>
						<td>
							<select id="mid" name="mid">
								<option value="0">
									全部
								</option>
								<c:forEach items="${channels}" var="channel">
									<option value="${channel.id }">
										${channel.name }
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="4" class="form_bottom">
							<s:textfield name="page.pageSize" id="pageSize" theme="simple"
								cssStyle="width:20px" onblur="return checkPageSize();"
								onkeyup="value=value.replace(/[^\d]/g,'');" />
							条/页&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton"
								iconCls="icon-search" onclick="querySubmit();">查询</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton"
								iconCls="icon-reload" onclick="reset();">重置</a>
						</td>
					</tr>
				</table>
			</h:panel>
			<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">
				<d:table name="page.result" id="row" uid="row" export="false"
					class="table" requestURI="list.act?exFile=1">
					<d:col cssStyle="width:40" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
					<d:col cssStyle="width:40" class="display_centeralign"
						title="选择<input type=\" checkbox\" id=\ "checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem"
						value="${row.ID}" />
					</d:col>

					<d:col property="TITLE" cssStyle="width:40px;"
						class="display_leftalign" title="标题" />
					<d:col property="NAME" class="display_centeralign" title="栏目名称" />
					<d:col property="WEIGHT" class="display_centeralign" title="排序" />
					<d:col class="display_centeralign" media="html" title="操作">
						<a href="javascript:void(0);" onclick="updateRecord('${row.ID}')">[修改]</a>&nbsp;	
			</d:col>
				</d:table>
			</h:page>
		</form>
		<script type="text/javascript">

	function addRecord(){
		var url = "${ctx}/app/cms/inputArticle.act";
		openEasyWin("winId","录入栏目信息",url,"900","600",true);
	}
	function updateRecord(id){
		var url = "${ctx}/app/cms/inputArticle.act?cmsId="+id;
		openEasyWin("winId","修改栏目信息",url,"900","600",true);
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


