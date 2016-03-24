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
        function doExport(){
            $("#exportForm").submit();
        }

        function apply(){
        	$("#apply").attr("disabled",true);
            this.location = "${ctx}/common/ca/apply/initApply.act";
        }
        </script>
    </head>
    <body>
        <form id="exportForm" name="exportForm" action="exportApplyPDF.act" method="post">  
            <s:hidden name="applyModel.caid"></s:hidden>
            <s:hidden name="applyModel.applyno"></s:hidden>         
            <table class="form_table" border="0" style="width:100%">
                <tr>
                    <td align="center" >
                        <span style="font-size: 12px;font-weight: bold;">证书申请提交成功！</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center; height: 35px">
                        <a id="export" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="doExport();">打印</a> &nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton" id="apply" iconCls="icon-reload" onclick="apply();">再次申请</a>
                        <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
                    </td>
                </tr>
            </table>
        </form>      
    </body>
</html>