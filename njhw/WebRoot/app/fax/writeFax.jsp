<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.cosmosource.base.util.Constants;"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	String SENT_FAX_SERVRE_URL = Constants.DBMAP.get("SEND_FAX");
 %>
<html>
<head>
    <%@ include file="/common/include/meta.jsp"%>
    <title></title>
    <link type="text/css" rel="stylesheet" href="css/ReadFax.css" />
    <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
        function tipsChange(dom)
        {
            dom.parentNode.className = 'reportTips_' + dom.className;
        }
        /**
        * 添加附件
        */
        function addAttach(){
        	if ($("#attach_content_tr td").length >= 3){
        		alert("最多能上传3个附件!");
        		return;
        	}
        	$("#add_attach").click();
        }
        /*删除附件*/
        function deleteAttach(obj){
        	$(obj).parent().remove();
        	if ($("#attach_content_tr td").length > 0){
        		$(".send_tr").show();
        	} else {
        		$(".send_tr").hide();
        	}
        }
        /**
        * 添加附件文件
        */
        function addFileInput(obj){
        	var fileName = $(obj).val();
        	var fileHtml = '<td class="send_td" style="height: 26px;"><span class="send_span">'+fileName+'</span> <a href="javascript:void(0);" class="send_del" onclick="javascript:deleteAttach(this)">删除</a></td>';
        	var fileClone = $(obj).clone(true).attr("name","fax_attach[]");
        	fileHtml = $(fileHtml).append(fileClone);
        	$("#attach_content_tr").append(fileHtml);
        	if ($("#attach_content_tr>td").length > 0){
        		$(".send_tr").show();
        	} else {
        		$(".send_tr").hide();
        	}
        }
        /**
        * 提交发送传真
        */
        function sendFaxCommit(){
        	if ($.trim($("#session_id").val())==""){
        		alert("登录网络传真服务失败,请检查用户名是否存在!");
        		return;
        	}
        	$("#send_fax_form").submit();
        }
        
        /**
        * 读取传真联系人
        */
        function sendFax(name, ipNum, fax) {
        	var text = $("#fo_to_id").val();
        	var append = name +"("+fax+");";
        	if (text.indexOf(append)==-1){
        		$("#fo_to_id").val($("#fo_to_id").val()+append);
        	}
    	}
        
		$(function() {
			//$("#user_fax").val($(parent.document).find("#userFax").val());
			$("#session_id").val($(parent.document).find("#session_id").val());
			$('#hiddenFrame').unbind('load').load(function() {
				//alert(document.getElementById('hiddenFrameN').contentWindow.document.body.innerHTML);
				var data = $.trim($(this).contents().find('html body').text());
				alert("data:"+data);
				alert($(data)[0].html());
			});
			
			$("#send_fax_form").validate({
				errorElement :"div",
				rules: {
					
					fo_subject: {
						required: true
					},
					fo_to: {
						required: true
					}
				},
				messages: {
					fo_subject: {
						required: "主题不能为空!"
					},
					fo_to: {
						required: "收件人不能为空!"
					}
				}
			});
		});
		/**
		* 修改紧急状态
		*/
		function changeUrgent(){
			if ($("#urgent_check").attr("checked")=="checked"){
				$("#urgent_input").val(1);
			} else {
				$("#urgent_input").val(0);
			}
		}
		
		/**
		* 指定发卡时间
		*/
		function chkSendTime(){
			if ($("#chk_send_time").attr("checked")=="checked") {
				$("#div_send_time_block").show();
			} else {
				$("#div_send_time_block").hide();
				$("#div_send_time_block input").val("");
			}
		}
    </script>
</head>
<body>
    <div class="main_left">
        <div class="main_left_top_span">
            <span>写传真</span>
        </div>
        <div class="main_main">
            <div class="main_left_top"></div>
            <iframe id='hiddenFrame' name='hiddenFrame' src="" style="display:none"></iframe>
            <form id="send_fax_form" method="POST" enctype="multipart/form-data" action="<%=SENT_FAX_SERVRE_URL %>">
            <input type="hidden" id="session_id" name="session_id" value=""/>
            <div class="main_left_main">
                <table class="main_left_main_table">
                	<!-- 
                    <tr>
                        <td class="main_left_td"><span>CSD</span></td>
                        <td class="main_left_td2"></td>
                        <td class="main_left_td3"><input type="text" id = "user_fax" class="main_left_input1" readonly></td>
                    </tr>
                     -->
                    <tr>
                        <td class="main_left_td"><span>收件人</span></td>
                        <td class="main_left_td2"></td>
                        <td class="main_left_td3"><input type="text" id="fo_to_id" name="fo_to" placeholder="多个收件人用;分割" class="main_left_input2"></td>
                    </tr>
                    <tr>
                        <td class="main_left_td"><span>主题</span></td>
                        <td class="main_left_td2"></td>
                        <td class="main_left_td3"><input type="text" name="fo_subject" class="main_left_input2"></td>
                    </tr>
                    <tr>
                        <td class="main_left_td" valign="top"><span>正文</span></td>
                        <td class="main_left_td2"></td>
                        <td class="main_left_td3"><textarea name="fo_body" class="main_left_input3"></textarea></td>
                    </tr>
                    <tr>
                        <td class="main_left_td"></td>
                        <td class="main_left_td2"></td>
                        <td class="main_left_td3" >
                            <img style="float: left" src="images/attach.png" onclick="addAttach();" alt="添加附件">
                            <div style="height:40px;line-height:40px;float: left"><span class="table_span2" onclick="addAttach();">添加附件</span></div>
                            <div class="table_div" ><input  id="urgent_input" name="urgent" value="0" type="hidden"><input class="table_check" onclick="changeUrgent();" id= "urgent_check" type="checkbox"></div>
                            <div class="table_div" ><span class="table_span2">加急</span></div><br>
                            <input type="file" style="display:none;" id="add_attach"  onchange="addFileInput(this);"/>
                            <input type="file" style="display:none;" name="fax_attach[]"/>
                        </td>
                    </tr>
                    <tr class="send_tr" style="display:none;">
                        <td class="main_left_td"></td>
                        <td class="main_left_td2"></td>
                        <td class="main_left_td3">
                            <table cellspacing="10" border="0">
                                <tr id="attach_content_tr">
                                	
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="main_left_td"></td>
                        <td class="main_left_td2"></td>
                        <td class="main_left_td3" >
                            <div class="table_div"><span class="table_span2"><input type="checkbox" onclick ="chkSendTime();" id="chk_send_time">指定发送时间</span></div>
                            <div id = "div_send_time_block" style = "display:none;">
                            <div class="table_div" >
                                <input style="width: 150px;height:22px;margin: 0px 0px 0 5px;" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="send_date" value="" />
                            </div>
                            <div class="table_div">
                                 <input style="width: 150px;height:22px;margin: 0px 5px 0 5px;" type="text" onClick="WdatePicker({dateFmt:'HH:mm:ss'})" name="send_time" value="" />
                            </div>
                            </div>
                            <!-- <img style="float: left;margin-top: 7px;cursor: pointer" src="images/cancel.png">  -->
                        </td>
                    </tr>
                    <tr>
                        <td class="main_left_td" valign="top"></td>
                        <td class="main_left_td2"></td>
                        <td class="main_left_td3"><div class="sure" onclick="sendFaxCommit();"></div></td>
                    </tr>
                </table>
            </div>
            </form>
        </div>
    </div>
    <div class="main_right">
        <jsp:include page="/app/integrateservice/publicBook.jsp?orgId=${_orgid}&type=fax&smac=${smac}&stel=${stel}"></jsp:include>
    </div>
</body>
</html>