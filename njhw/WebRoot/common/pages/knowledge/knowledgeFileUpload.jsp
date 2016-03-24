<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>文件上传</title>
    <%@ include file="/common/include/meta.jsp" %>
    <link href="${ctx}/scripts/widgets/swfupload/style/default.css" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/scripts/widgets/swfupload/js/swfupload.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/widgets/swfupload/js/swfupload.queue.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/widgets/swfupload/js/fileprogress.js" type="text/javascript"></script>  
    <script src="${ctx}/scripts/widgets/swfupload/js/handlers.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/custom/format.js" type="text/javascript"></script>
    
    <script type="text/javascript">
    $(document).ready(function() {
    	loadFileList();
    });

    function loadFileList(){
    	clearFileList();
    	
        $.getJSON("${ctx}/common/knowledge/getJsonFileList.act",{knowledgeId:$("#knowledgeId").val()},
                function(data){ 
                    $.each(data,function(i){
                    	appendTr(data[i].FILE_ID, data[i].FILE_NAME, data[i].FILE_EXTENSION.substring(1), data[i].FILE_SIZE);
                });
        });
    }        
    
    //清空操作列表
    function clearFileList(){
        var fileList = $("#fileList tbody tr");
        $.each(fileList, function(index,item){
            $(item).remove();
        });
    }
    
    function appendTr(fileId, fileName, fileType, fileSize){
        var tr = "<tr id='" + fileId + "'>";
        tr += "<td align='left'>" + fileName + "</td>";
        tr += "<td align='center'>" + fileType + "</td>";
        tr += "<td align='left'>" + formatFileSize(fileSize) + "</td>";
        tr += "<td align='center'><a style='cursor:hand;' onclick=deleteFile('" + fileId + "')>删除</a></td>";
        tr += "</tr>";
        
        $('#fileList').append(tr);
    }

    function showInfo(message){
        easyAlert("提示信息",message,"info");
    }

    function deleteFile(fileId){
        var confirmMsg = "确认删除该文件？“确定”删除，“取消”保留！";
        easyConfirm('提示', confirmMsg, function(result){
            if(result){
                $.ajax({
                    type : "POST",
                    url : "${ctx}/common/knowledge/deleteKnowledgeFile.act",
                    data : "fileId=" + fileId,
                    async : false, 
                    success : function(msg){
                        if(msg != "true"){
                        	$("#fileList tr[id='" + fileId + "']").remove();
                        }
                        else {
                        	showInfo("删除文件出错!");
                        }
                    }
                });
            }
        });   
    }

    var upload;  
    window.onload = function() {  
        upload = new SWFUpload({  
            // 背景资料设置   
            upload_url: "${ctx}/common/knowledge/upload.act",  
            post_params: {"jsessionid":"<%=request.getSession().getId()%>"},
            //上传文件的名称
            file_post_name: "file",  
      
            // 文件上传设置  
            file_size_limit : "10 MB",  // 10MB  
            file_types : "*.*",  //*.jpg;*.gif;*.png;*.bmp;*.jpeg;*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;
            file_types_description : "所有文件",  
            file_upload_limit : "0",  //实例允许上传的最多文件数量,0表示允许上传的数量无限制  
            file_queue_limit : "0",  //设置文件上传队列中等待文件的最大数量限制  
      
            // 挂事件, 所有事件处理程序在Handler.js文件  
            file_dialog_start_handler : fileDialogStart, //此事件在selectFile或者selectFiles调用后，文件选择对话框显示之前触发。只能同时存在一个文件对话框。  
            file_queued_handler : fileQueued, //当文件选择对话框关闭消失时，如果选择的文件成功加入上传队列，那么针对每个成功加入的文件都会触发一次该事件（N个文件成功加入队列，就触发N次此事件）。  
            file_queue_error_handler : fileQueueError,//如果选择的文件加入到上传队列中失败，那么针对每个出错的文件都会触发一次该事件  
            file_dialog_complete_handler : fileDialogComplete,//当选择文件对话框关闭，并且所有选择文件已经处理完成（加入上传队列成功或者失败）时，此事件被触发  
            upload_start_handler : function(file){
            	this.addPostParam("fileName", file.name);
            	this.addPostParam("fileType", file.type);
            	this.addPostParam("knowledgeId", $("#knowledgeId").val());
            },  
            upload_progress_handler : uploadProgress,  
            upload_error_handler : uploadError,  
            upload_success_handler : uploadSuccess,  
            upload_complete_handler : function(file){
            	loadFileList();
            },  
      
            // 按键设置   
            button_image_url : "${ctx}/scripts/widgets/swfupload/image/XPButtonUploadText_61x22.png",  
            button_placeholder_id : "spanButtonPlaceholder",  
            button_width: 61,  
            button_height: 22,  
              
            // 指明swfupload.swf的位置  
            flash_url : "${ctx}/scripts/widgets/swfupload/swfupload.swf",  
              
            custom_settings : {  
                progressTarget : "fsUploadProgress",  
                cancelButtonId : "btnCancel"  
            },  
            // Debug开关  
            debug: false  
        });  
    }
      
    //获取指定名称的cookie的值  
    function getCookie(objName) {  
        var arrStr = document.cookie.split("; ");  
        for ( var i = 0; i < arrStr.length; i++) {  
            var temp = arrStr[i].split("=");  
            if (temp[0] == objName){
                return unescape(temp[1]);  
            }
        }  
    }  
        
    //读取所有保存的cookie字符串  
    function allCookie() {  
        var str = document.cookie;  
        if (str == "") {  
            str = "没有保存任何cookie";  
        }  

        return str;  
    }
        
    function start() {  
        upload.startUpload();  
    }  
        
    function cancelUpload() {  
        upload.cancelUpload();  
    }  
       
    </script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<s:hidden name="knowledgeId"/>
<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">  
    <tr> 
        <td style="padding:10px;">   
            <span id="flashPlayInstall"></span>
            <div id="content">  
                <form id="form1" action="upload.act" method="post" enctype="multipart/form-data">  
                    <div>  
                        <div class="fieldset flash" id="fsUploadProgress">  
                            <span class="legend">上传文件列表</span>  
                        </div>  
                        <div style="padding-left: 5px;">  
                            <span id="spanButtonPlaceholder"></span>  
                            <input id="btnCancel" type="button" value="取消上传" onclick="cancelUpload();" style="margin-left: 2px; height: 22px; font-size: 8pt; display:none;" />  
                        </div>  
                    </div>
                </form>  
            </div>  
        </td>  
    </tr>  
</table>
<table class="table" id="fileList" width="100%">
    <thead>
        <tr>
            <th width="70%">文件名</th>
            <th width="10%">文件类型</th>
            <th width="10%">文件大小</th>
            <th width="10%">操作</th>
        </tr>
    </thead>
    <tbody></tbody>
</table>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存知识失败！','error');
</c:if>

</script>