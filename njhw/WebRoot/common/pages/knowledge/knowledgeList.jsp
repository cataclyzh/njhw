<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>知识查询</title>
    <%@ include file="/common/include/meta.jsp" %>
    <script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function() {        
        var chk_options = { 
            all:'#checkAll',
            item:'.checkItem'
        };
        $(this).chk_init(chk_options);
        
		changebasiccomponentstyle();
		changedisplaytagstyle();
        initQueryToggle("listCnt", "hide_table", "");
    });
    
	function addRecord(){
		var url = "${ctx}/common/knowledge/input.act?seed";
		var params = "";
		url += params;

		openEasyWin("knowledgeInput","录入 知识信息",url,"800","550",true);
	}
	
	function updateRecord(id){
		var url = "${ctx}/common/knowledge/input.act?seed";
		var params = "&knowledgeId=" + id;
		url += params;
		
		openEasyWin("knowledgeInput","修改知识信息",url,"800","550",true);
	}

	function detail(id){
		var url = "${ctx}/common/knowledge/detail.act?seed";
		var params = "&knowledgeId=" + id;
		url += params;
		
		openEasyWin("knowledgeDetail","知识信息",url,"800","550",true);
	}

	function fileList(id){
		var url = "${ctx}/common/knowledge/fileList.act?seed";
		var params = "&knowledgeId=" + id;
		url += params;
		
		openEasyWin("knowledgeFileList","文件列表",url,"800","550",true);
	}
	
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","delete.act");
					$('#queryForm').submit();
				}
			});		
		}
    }

    function publish(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要发布的记录！','info');
		}else{
			easyConfirm('提示', '确定发布？', function(r){
				if (r){
					$('#queryForm').attr("action","publish.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
	function querySubmit() {	
		if(checkPageSize()){
			$("#loadingdiv").show();
			$('a.easyui-linkbutton').linkbutton('disable');
			$("#pageNo").val("1");
			$("#queryForm").submit();
		}		
	}   
    </script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="query.act" method="post" autocomplete="off">
	<s:hidden name="knowledgeModel.viewType"/>
    <s:hidden name="knowledgeModel.state"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
        <table align="center" id="hide_table" border="0" width="100%" class="form_table">
            <tr>
                <td class="form_label" width="20%" align="left">
           	                   主题：
                </td>
                <td width="30%">
                    <s:textfield name="knowledgeModel.subject" theme="simple" cssClass="input180box" maxlength="50"/>  
                </td>
                <td class="form_label" width="20%" align="left">
                                                  内容：
                </td>
                <td width="30%">
                    <s:textfield name="knowledgeModel.content" theme="simple" cssClass="input180box" maxlength="50"/>  
                </td>
            </tr> 
            <%--<tr>
                <td class="form_label" align="left">
           	                   问题说明：
                </td>
                <td>
                    <s:textfield name="knowledgeModel.question" theme="simple" cssClass="input180box" maxlength="50"/>  
                </td>
                <td class="form_label" align="left">
                                                  解决办法：
                </td>
                <td>
                    <s:textfield name="knowledgeModel.answer" theme="simple" cssClass="input180box" maxlength="50"/>  
                </td>
            </tr>  --%>
            <tr>
                <td class="form_label" align="left">
           	                   知识分类：
                </td>
                <td>
                    <s:select list="#application.DICT_TESCO_KNOWLEDGE_TYPE" name="knowledgeModel.type" 
         				emptyOption="false" headerKey="" headerValue="全部" 
          				listKey="dictcode" listValue="dictname" cssClass="input180box"/>
                </td>
                <c:if test="${knowledgeModel.viewType eq 'edit' or knowledgeModel.viewType eq 'publish' or knowledgeModel.viewType eq 'generalView'}"> 
                <td class="form_label" align="left">
                                                  知识权限：
                </td>
                <td>
                    <s:select list="#application.DICT_KNOWLEDGE_LEVEL" name="knowledgeModel.restrictLevel" 
         				emptyOption="false" headerKey="" headerValue="全部" 
          				listKey="dictcode" listValue="dictname" cssClass="input180box"/>  
                </td>
                </c:if>
            </tr>     
            <tr>
                <td colspan="4" class="form_bottom">
                    <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	        &nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
			        &nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
                </td>
            </tr>
        </table>      
    </h:panel>
    
    <c:if test="${knowledgeModel.viewType eq 'publish'}">
        <c:set var="customButton" value="[{\"id\":\"publish\",\"name\":\"发布\",\"function\":\"publish();\"}]" />
    </c:if>
    <c:if test="${knowledgeModel.viewType eq 'edit'}">
        <c:set var="buttons" value="add,del" />
    </c:if>
    <c:if test="${knowledgeModel.viewType eq 'generalView'}">
        <c:set var="buttons" value="" />
    </c:if>
    <c:if test="${knowledgeModel.viewType eq 'sellView'}">
        <c:set var="buttons" value="" />
    </c:if>
    <c:if test="${knowledgeModel.viewType eq 'buyView'}"> 
        <c:set var="buttons" value="" />
    </c:if>
    <div id="listCnt" style="OVERFLOW-y:auto;">
	<h:page id="list_panel" width="100%" buttons="${buttons}" customButton="${customButton}" title="结果列表">		
        <d:table name="page.result" id="row" export="false" class="table">
            <d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45" class="display_centeralign" title="选择<input type='checkbox' id='checkAll'/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.KNOWLEDGE_ID}"/>
			</d:col>
			<d:col property="CODE" class="display_centeralign" title="编号"/>	
			<d:col property="SUBJECT" class="display_leftalign" title="主题"  maxLength="20"/>
			<d:col class="display_leftalign" title="内容"  property="CONTENT" maxLength="20"/>
            <%--
			<d:col class="display_leftalign" title="问题说明">
                <span title="${row.QUESTION}">${fn:substring(row.QUESTION, 0, 20)}<c:if test="${fn:length(row.QUESTION) > 20}">...</c:if></span>
            </d:col>
			<d:col class="display_leftalign" title="解决办法">
                <span title="${row.ANSWER}">${fn:substring(row.ANSWER, 0, 20)}<c:if test="${fn:length(row.ANSWER) > 20}">...</c:if></span>
            </d:col> --%>
			<d:col property="TYPE" class="display_leftalign" dictTypeId="TESCO_KNOWLEDGE_TYPE" title="知识分类"/>	
			<d:col property="RESTRICT_LEVEL" class="display_leftalign" dictTypeId="KNOWLEDGE_LEVEL" title="知识权限"/>
			<d:col property="CREATE_TIME" class="display_centeralign" format="{0,date,yyyy-MM-dd HH:mm:ss}" title="创建时间"/>
			<d:col class="display_centeralign" media="html" title="操作">
			    <c:if test="${knowledgeModel.viewType eq 'edit' or knowledgeModel.viewType eq 'publish\' }">
				<a href="javascript:void(0);" onclick="updateRecord('${row.KNOWLEDGE_ID}')">[修改]</a>&nbsp;&nbsp;
				</c:if>
				
				<a href="javascript:void(0);" onclick="detail('${row.KNOWLEDGE_ID}')">[明细]</a>&nbsp;&nbsp;
				<c:if test="${row.DISPLAY_MODEL eq '1'}"> 
				<a href="javascript:void(0);" onclick="fileList('${row.KNOWLEDGE_ID}')">[文件列表]</a>
				</c:if>
			</d:col>				
        </d:table>
    </h:page>
    </div>
    </form>
</body>
</html>