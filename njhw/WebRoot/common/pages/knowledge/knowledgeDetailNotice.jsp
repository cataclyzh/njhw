<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>重要通知</title>
    <%@ include file="/common/include/meta.jsp" %>
    <script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/custom/format.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        initQueryToggle("listCnt", "hide_table", "footId");
    });

    function closeWin(){
    	closeEasyWin('knowledgeDetail');
    }

    function download(fileId){
        $("#fileId").val(fileId);
        $("#downloadForm").submit();  
    }
    
    </script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <h:panel id="query_panel" width="100%" title="重要通知" pageName="filePage">
    <table align="center" id="hide_table" border="0" width="100%" class="form_table">
        <tr>
            <td class="form_label" width="15%">主题：</td>
            <td colspan="3">
                <s:property value="subject"/>
            </td> 

                <%--    <td width="20%" class="form_label">知识权限：</td>
            <td width="30%">
                <h:dict dictid="${restrictLevel}" dicttype="KNOWLEDGE_LEVEL"></h:dict>
        	                <td class="form_label">知识分类：</td>
        	    <h:dict dictid="${type}" dicttype="TESCO_KNOWLEDGE_TYPE"></h:dict>
            <td >
        	    
            </td> --%>
        </tr>
        <tr>
            <td class="form_label">编号：</td>
            <td>
                <s:property value="code"/>
            </td>
        </tr> 
        <tr>   
            <td class="form_label">内容：</td>
            <td colspan="3">
                <s:textarea  name="content" theme="simple" rows="16" cols="80" readonly="true"></s:textarea>
            </td> 
        </tr> 
             <%--   <tr>   
            <td class="form_label">问题说明：</td>
            <td colspan="3">
                <s:property value="question"/>
            </td> 
        </tr> 
        <tr>   
            <td class="form_label">解决办法：</td>
            <td colspan="3">
                <s:property value="answer"/>
            </td> 
        </tr>

        <tr>
            <td class="form_label">创建时间：</td>
            <td>
                <f:formatDate value='${createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
            </td>
            <td class="form_label">创建人：</td>
            <td>
                <s:property value="createUser"/>
            </td> 
        </tr>  
        <tr>
            <td class="form_label">发布时间：</td>
            <td>
                <f:formatDate value='${publishTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
            </td>
            <td class="form_label">发布人：</td>
            <td>
                <s:property value="publishUser"/>
            </td> 
        </tr>  
        
        <tr>   
            <td class="form_label">显示模式：</td>
            <td colspan="3">
                <h:dict dictid="${displayModel}" dicttype="KNOWLEDGE_MODEL"></h:dict>
            </td>  
        </tr>
        --%>
    </table>
    </h:panel>
    <div id="listCnt" style="OVERFLOW-y:auto;overflow-x:hidden;">
    <c:if test="${displayModel eq '1'}"> 
    <h:page id="grnList_panel" pageName="filePage" width="100%" title="文件列表">
        <d:table name="filePage.result" id="row" export="false" class="table">
            <d:col style="width:10%" title="序号" class="display_centeralign" >
                ${row_rowNum}
            </d:col>
            <d:col style="width:50%" property="FILE_NAME" class="display_leftalign" title="文件名"/>
            <d:col style="width:10%" class="display_centeralign" title="文件类型">
                ${fn:substringAfter(row.FILE_EXTENSION, '.')}
            </d:col>
            <d:col style="width:20%" class="display_leftalign" title="文件大小">
                <script>document.write(formatFileSize('${row.FILE_SIZE}'))</script>
            </d:col>
            <d:col style="width:10%" class="display_centeralign" media="html" title="操作">
                <a href="javascript:void(0);" onclick="download('${row.FILE_ID}')">[下载]</a>
            </d:col>
        </d:table>
    </h:page>
    </c:if>
    </div>
    <div id="footId">
        <table align="center" id="foot_table" border="0" width="100%" class="form_table">
            <tr>
                <td align="center">
                    <a href="javascript:void(0);" id="closeBtn" class="easyui-linkbutton" iconCls="icon-save" onclick="closeWin();">关闭</a>
                </td>
            </tr>
        </table> 
    </div>
    <form id="downloadForm" name="downloadForm" action="download.act" method="post">
        <input type="hidden" id="fileId" name="fileId"/>
    </form>
</body>
</html>