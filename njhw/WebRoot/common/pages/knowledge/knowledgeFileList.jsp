<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>知识查询</title>
    <%@ include file="/common/include/meta.jsp" %>
    <script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/custom/format.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        initList("listCnt", "footId");
    });
    
    function initList(contentId, fixIds){
    	var fixHeight = calculateHeight(fixIds);
    	animateContent(contentId, fixHeight);	
    }
    
    function closeWin(){
    	closeEasyWin('knowledgeFileList');
    }

    function download(fileId){
        $("#fileId").val(fileId);
        $("#downloadForm").submit();  
    }

    function showImage(fileId){
		var url = "${ctx}/common/knowledge/showImage.act?seed";
		var params = "&fileId=" + fileId;
		url += params;
		
		openEasyWin("imageView","图片信息",url,"800","600",false);
    }
    
    </script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <div id="listCnt" style="OVERFLOW-y:auto;overflow-x:hidden;"> 
    <h:page id="list_panel" pageName="filePage" width="100%" title="文件列表">
        <d:table name="filePage.result" id="row" export="false" class="table">
            <d:col style="width:10%" title="序号" class="display_centeralign" >
                ${row_rowNum}
            </d:col>
            <d:col style="width:50%" property="FILE_NAME" class="display_leftalign" title="文件名"/>
            <d:col style="width:10%" class="display_centeralign" title="文件类型">
                ${fn:substringAfter(row.FILE_EXTENSION, '.')}
            </d:col>
            <d:col style="width:10%" class="display_leftalign" title="文件大小">
                <script>document.write(formatFileSize('${row.FILE_SIZE}'))</script>
            </d:col>
            <d:col style="width:20%" class="display_centeralign" media="html" title="操作">
                <a href="javascript:void(0);" onclick="download('${row.FILE_ID}')">[下载]</a>
                <c:if test="${fn:containsIgnoreCase('.jpg.jpeg.png.gif.bmp', row.FILE_EXTENSION)}">
                <a href="javascript:void(0);" onclick="showImage('${row.FILE_ID}')">[预览图片]</a>
                </c:if>
            </d:col>
        </d:table>
    </h:page>
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