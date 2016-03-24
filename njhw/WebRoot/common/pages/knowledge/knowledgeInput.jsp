<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>知识录入</title>
    <%@ include file="/common/include/meta.jsp" %> 
    
    <script type="text/javascript">
    $(document).ready(function() {
    	restrictText("content", "contentRemainCount", 1500);
    	restrictText("question", "questionRemainCount", 1500);
    	restrictText("answer", "answerRemainCount", 1500);
        
    	$("#inputForm").validate({
            onsubmit:true,
            errorElement :"div",
            errorPlacement: function(error, element) { 
                error.appendTo(element.parent()); 
            },
            rules: {
                code:{
                    required:true
                },
                subject:{
                    required:true
                },
                content:{
                    required:true
                },
                type:{
                    required:true
                },
                restrictLevel:{
                    required:true
                },
                displayModel:{
                    required:true
                }
            },
            messages: {
                code:{
                    required:"编号不能为空!"
                },
                subject:{
                    required:"主题不能为空!"
                },
                content:{
                    required:"内容不能为空!"
                },
                type:{
                    required:"知识分类不能为空!"
                },
                restrictLevel:{
                    required:"知识权限不能为空!"
                },
                displayModel:{
                    required:"显示模式不能为空!"
                }
            },
            submitHandler: function(form) {
            	$("#submitAndClose").attr("disabled",true);
            	$("#submitAndUpload").attr("disabled",true);
                form.submit();
            }
        });
    });

    function restrictText(textId, tipId, maxLength){
        //初始化剩余输入字数
    	$("#" + tipId).text(parseInt(maxLength) - $("#" + textId).val().length);
    	
        $("#" + textId).bind('keyup blur', function(){
            var filledText = $("#" + textId).val();
            var filledLength = filledText.length;
            if(filledLength >= parseInt(maxLength)){
                $("#" + tipId).text(0);
                var text = filledText.substr(0, parseInt(maxLength));  
                $("#" + textId).val(text);
            }
            else{
                $("#" + tipId).text(parseInt(maxLength) - filledLength);
            }
        });
    }

	function save(){
		$("#next").val("ok");
		$('#inputForm').submit();
	}

	function saveAndUploadFile(){
		$("#next").val("upload");
		$('#inputForm').submit();
	}
    </script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<form id="inputForm" action="save.act" method="post" autocomplete="off">
    <s:hidden name="knowledgeId"/>
    <s:hidden name="next"/>
    
    <table align="center" border="0" width="100%" class="form_table">
        <tr>
            <td width="20%" class="form_label">所属公司：<font style="color:red">*</font></td>
            <td width="30%">
            	<s:select list="#application.DICT_COMPANY" name="company" 
         				emptyOption="false"
          				listKey="dictcode" listValue="dictname" cssClass="input180box"/>
            </td>
        </tr>
        <tr>
            <td width="20%" class="form_label">知识分类：<font style="color:red">*</font></td>
            <td width="30%">
            	<s:select list="#application.DICT_TESCO_KNOWLEDGE_TYPE" name="type" 
         				emptyOption="false"
          				listKey="dictcode" listValue="dictname" cssClass="input180box"/>
            </td>
            <td width="20%" class="form_label">知识权限：<font style="color:red">*</font></td>
            <td width="30%">
            	<s:select list="#application.DICT_KNOWLEDGE_LEVEL" name="restrictLevel" 
         				emptyOption="false"
          				listKey="dictcode" listValue="dictname" cssClass="input180box"/>
            </td> 
        </tr>
        <tr>
            <td class="form_label">编号：<font style="color:red">*</font></td>
            <td>
                <s:textfield name="code" theme="simple" size="20" maxlength="20"/>
            </td>
            <td class="form_label">主题：<font style="color:red">*</font></td>
            <td>
                <s:textfield name="subject" theme="simple" size="50" maxlength="50"/>
            </td> 
        </tr> 
        <tr>   
            <td class="form_label">内容：</td>
            <td colspan="3">
                <s:textarea name="content" theme="simple" rows="20" cols="80"></s:textarea>
                <span style="width: 50px">还可以输入</span>
                <span id="contentRemainCount" style="color: red"></span>
                <span>个字符</span>
            </td> 
        </tr> 
    <%--   <tr>   
            <td class="form_label">问题说明：</td>
            <td colspan="3">
                <s:textarea name="question" theme="simple" rows="5" cols="80"></s:textarea>
                <span style="width: 50px">还可以输入</span>
                <span id="questionRemainCount" style="color: red"></span>
                <span>个字符</span>
            </td> 
        </tr> 
        <tr>   
            <td class="form_label">解决办法：</td>
            <td colspan="3">
                <s:textarea  name="answer" theme="simple" rows="5" cols="80"></s:textarea>
                <span style="width: 50px">还可以输入</span>
                <span id="answerRemainCount" style="color: red"></span>
                <span>个字符</span>
            </td> 
        </tr> --%> 
        <tr>   
            <td class="form_label">显示模式：<font style="color:red">*</font></td>
            <td colspan="3">  
            	<s:select list="#application.DICT_KNOWLEDGE_MODEL" name="displayModel" 
         				emptyOption="false"
          				listKey="dictcode" listValue="dictname" cssClass="input180box"/>
            </td> 
        </tr>
    </table>
    <table align="center" border="0" width="100%" class="form_table">  
        <tr class="form_bottom">
            <td>
       		    <a href="javascript:void(0);" id="submitAndClose" class="easyui-linkbutton" iconCls="icon-save" onclick="save();">保存并关闭</a> 
       		    &nbsp;&nbsp;&nbsp;&nbsp;
       		    <a href="javascript:void(0);" id="submitAndUpload" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAndUploadFile();">保存后上传文件</a>
       		    <c:if test="${not empty knowledgeId}">
       		    &nbsp;&nbsp;&nbsp;&nbsp;
       		    <a href="javascript:void(0);" id="submitAndUpload" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAndUploadFile();">修改文件</a>
       		    </c:if> 
       		    &nbsp;&nbsp;&nbsp;&nbsp;
       		    <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>          
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存知识成功！','info',
	   function(){closeEasyWin('knowledgeInput');}
	);
</c:if>

<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存知识失败！','error');
</c:if>
</script>