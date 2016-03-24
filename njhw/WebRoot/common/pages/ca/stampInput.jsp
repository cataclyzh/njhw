<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: caosq
- Date: 2011-11-24 
- Description: 申请表录入
--%>
<html>
    <head>
        <title>录入申请表</title>
        <%@ include file="/common/include/meta.jsp"%>

        <script type="text/javascript">

        //只允许输入数字--add by csq 2011-11-29
        jQuery.validator.addMethod("imageFile", 
              function(value, element) {  
                  var pattern = /^(.*)(\.jpg|\.gif|\.png|\.bmp)$/;
                  return this.optional(element) || (pattern.test(value));    
              }, "请选择后缀为*.jpg,*.gif,*.png,*.bmp的印章图片!"
        );
        
        $(document).ready(function(){
            $("#stampInputForm").validate({
                onsubmit:true,
                errorElement :"div",
                errorPlacement: function(error, element) { 
                    error.appendTo(element.parent()); 
                },
                rules: {
                    upload:{
                        required:true,
                        imageFile:true
                    }
                },
                messages: {
                    upload:{
                        required:"请选择印章图片文件!"
                    }
                },
                submitHandler: function(form) {
                	$("#submit").attr("disabled",true);
                    form.submit();
                }
            });
        });
            
        function showInfo(message){
            easyAlert("提示信息",message,"info");
        }
        
        function doNext(){
            $("#stampInputForm").submit();
        }
        </script>
    </head>
    <body>
        <form id="stampInputForm" action="saveStamp.act" enctype="multipart/form-data" method="post">
            <s:hidden name="applyModel.caid"></s:hidden>
            <s:hidden name="applyModel.applyno"></s:hidden>
            <s:hidden name="applyModel.applynum"></s:hidden>
            <h:panel width="100%" title="上传印章" id="list_panel">
            <table width="100%" align="center" class="form_table" border="0">
                <tr>
                    <td height="50" colspan="2" align="center" >
                        <span style="font-size: 12px;font-weight: bold;">上传印章</span>
                    </td>
                </tr>
                <tr>
                    <td class="form_label" nowrap>
                                                                 上传印章：<font style="color: red">*</font>
                    </td>
                    <td>
                        <s:file name="upload" theme="simple"></s:file><font style="color: red">请选择后缀为*.jpg,*.gif,*.png,*.bmp的印章图片！</font>
                    </td>
                </tr>
            </table>
            </h:panel>    
            <table class="form_table" border="0" style="width:100%">
                <tr>
                    <td colspan="4" style="text-align: center; height: 35px">
                        <a id="submit" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="doNext();">下一步</a> &nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#stampInputForm').resetForm();">重置</a>
                        <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>