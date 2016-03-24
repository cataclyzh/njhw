<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>知识查询</title>
    <%@ include file="/common/include/meta.jsp" %>
    <script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
    <link href="${ctx}/scripts/widgets/imgAreaSelect/css/imgareaselect-default.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/scripts/widgets/imgAreaSelect/scripts/jquery.imgareaselect.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function () {
        $('#fileImg').imgAreaSelect({ maxWidth: 400, maxHeight: 400, handles: true,
               onSelectEnd: function (img, selection) {
                   $('input[name="x1"]').val(selection.x1);
                   $('input[name="y1"]').val(selection.y1);
                   $('input[name="x2"]').val(selection.x2);
                   $('input[name="y2"]').val(selection.y2);
                   $('input[name="cutWidth"]').val(selection.x2-selection.x1);
                   $('input[name="cutHeight"]').val(selection.y2-selection.y1);
               }
         });

         initList("listCnt", "footId");
         initImg();
    });

    function initList(contentId, fixIds){
    	var fixHeight = calculateHeight(fixIds);
    	animateContent(contentId, fixHeight);	
    }

    function initImg(){
        var url = "${ctx}/common/knowledge/getImage.act?fileId=${fileId}";
        url += "&" + Math.random(); //avoid image buffer
        $("#fileImg").attr("src", url);
    }

    function submitForm(){
    	easyConfirm('提示', '确定裁剪选中区域？', function(r){
			if (r){
                $("#cutForm").submit();
			}
		});		
    }

    function closeWin(){
    	closeEasyWin('imageView');
    }

    <c:if test="${isSuc=='true'}">
    	easyAlert('提示信息','裁剪图片成功！','info',
    	   function(){closeEasyWin('winId');}
    	);
    </c:if>
    <c:if test="${isSuc=='false'}">
    	easyAlert('提示信息','裁剪图片失败！','error');
    </c:if>
    </script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<form id="cutForm" action="cutImage.act" method="post">
<input id="fileId" type="hidden" name="fileId" value="${fileId}"/>
<div id="listCnt" style="OVERFLOW-y:auto;overflow-x:hidden;"> 
    <img id="fileImg"/>
    <div id="preview"></div>
</div>
<div>
   <input id="x1" type="hidden" name="x1" value="" />
   <input id="y1" type="hidden" name="y1" value="" />
   <input id="x2" type="hidden" name="x2" value="" />
   <input id="y2" type="hidden" name="y2" value="" />
   <input id="cutWidth" type="hidden" name="cutWidth" value="" />
   <input id="cutHeight" type="hidden" name="cutHeight" value="" />
</div>
<div id="footId">
    <table align="center" id="foot_table" border="0" width="100%" class="form_table">
        <tr>
            <td align="center">           
                <a href="javascript:void(0);" id="submitBtn" class="easyui-linkbutton" iconCls="icon-save" onclick="submitForm();">裁剪图片</a>
                &nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0);" id="closeBtn" class="easyui-linkbutton" iconCls="icon-save" onclick="closeWin();">关闭</a>
            </td>
        </tr>
    </table> 
</div>
</form>
</body>
</html>