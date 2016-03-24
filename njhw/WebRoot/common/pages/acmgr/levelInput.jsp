<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>录入级别信息</title>
    <%@ include file="/common/include/meta.jsp" %>
    
    <script type="text/javascript">

    $(document).ready(function() {
    	restrictText("remark", "remarkRemainCount", 200);
        
    	$("#inputForm").validate({
            onsubmit:true,
            errorElement :"div",
            errorPlacement: function(error, element) { 
                error.appendTo(element.parent()); 
            },
            rules: {
                levelCode:{
                    required:true
                },
                levelName:{
                    required:true
                }
            },
            messages: {
                levelCode:{
                    required:"编号不能为空!"
                },
                levelName:{
                    required:"主题不能为空!"
                }
            },
            submitHandler: function(form) {
            	$("#submit").attr("disabled",true);
                form.submit();
            }
        });
    });

    function initLevelList(){
		var url = "${pageContext.request.contextPath}/common/levelMgr/getLevelList.act";
		var company = $("#company").val();
		var levelCode = $("#levelCode").val();
	    $.getJSON(url, {company:company},
	        function(data){ 
	            $.each(data,function(i){
	                var value = data[i].LEVEL_CODE;
	                var name = data[i].LEVEL_NAME;
	                if(levelCode != value){    
	        	        $("#changeLevelCode").append("<option value='" + value + "'>" + name + "</option>");
	                }
	        });
	    });
    }

	function save(){
		$('#inputForm').submit();
	}

    function closeWin(){
    	closeEasyWin('levelInput');
    }

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

    </script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<form id="inputForm" action="updateLevel.act" method="post" autocomplete="off">
    <s:hidden name="levelId"/>
    <s:hidden name="company"/>
    <table align="center" border="0" width="100%" class="form_table">
        <tr>
            <td width="20%" class="form_label">级别编码：<font style="color:red">*</font></td>
            <td width="30%">
            	<s:textfield name="levelCode" theme="simple" size="20" maxlength="10"/>
            </td>
            <td width="20%" class="form_label">级别名称：<font style="color:red">*</font></td>
            <td width="30%">
            	<s:textfield name="levelName" theme="simple" size="20" maxlength="20"/>
            </td>
        </tr>
        <tr>
            <td class="form_label">级别说明：</td>
            <td colspan="3">
                <s:textarea name="remark" theme="simple" rows="10" cols="60"></s:textarea>
                <span style="width: 50px">还可以输入</span>
                <span id="remarkRemainCount" style="color: red"></span>
                <span>个字符</span>
            </td>
        </tr>
    </table>
    <table align="center" border="0" width="100%" class="form_table">  
        <tr class="form_bottom">
            <td>
       		    <a href="javascript:void(0);" id="submit" class="easyui-linkbutton" iconCls="icon-save" onclick="save();">保存</a>
       		    &nbsp;&nbsp;&nbsp;&nbsp;
       		    <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
       		    &nbsp;&nbsp;&nbsp;&nbsp;
       		    <a href="javascript:void(0);" id="closeBtn" class="easyui-linkbutton" iconCls="icon-save" onclick="closeWin();">关闭</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存级别成功！','info',
	   function(){closeEasyWin('levelInput');}
	);
</c:if>

<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存级别失败！','error');
</c:if>
</script>