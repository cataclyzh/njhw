<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>发放临时卡</title>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script type="text/javascript">
		  function easyAlert(title,msg,type,fn){
    	using('messager', function () {
    		$.messager.alert(title,msg,type,fn);
    	});	
    }
		
	$(function() {
		
		//$("#all").click(function() {
			//$("#all_table :checkbox").each(function() {
			//	$(this).attr("checked", !$(this).attr("checked"));
		//	});
	//	});
		//全选
		$("#sonall").click(function() {
			$("#table2 :checkbox").each(function() {
				$(this).attr("checked", !$(this).attr("checked"));
			});
		});
		//市民卡类型
		var cardType = $("#cardType").text();
     
		if ($.trim(cardType) == "市民卡") {
           
			$("#checkbox_display").hide();
		} else {
       
			$("#checkbox_display").show();
		}
		
		var message = $("#message").val();
	    if (message != "") {
		alert(message);
		closeEasyWin('winIdss');
           	}
	

	$("#refund").click(function() {
			var str = "";
			var textstr = "";

			$("#table2 :checkbox").each(function() {
				var isCheck = $(this).attr("checked");
				if (isCheck) {
					str += $(this).val() + ",";
				//	alert(str)
				}
			})
			if ($("#maincaid").val() != null && $("#maincaid").val() != "") {
				str += $("#maincaid").val()+",";
			}
			//alert(str);
			textstr = str.substr(0, str.length - 1);
			//alert(textstr);
			$("#cardNum").val(textstr);
			if (textstr == "") {
				alert("请选择");
				return false;
			}
				$("#refundForm").submit();

			});
	});

	//选中主卡
	function maincards() {

		var cardid;
		var isck = $("#table1 input[name='father']").attr("checked");
		if (isck) {
			cardid = $("#father").val();
			$("#maincaid").val(cardid);
			//alert($("#maincaid").val());
		}
	}

	//黑名单
	function blackcheckbox() {
		var residentNo;
		var isck = $("#table input[name='addblack']").attr("checked");
		if (isck) {
			//alert(isck);
			residentNo = $("#addblack").val();
			$("#blackResidentNo").val(residentNo);
			$("#td_Reson").show();
		} else {
			$("#td_Reson").hide();
			$("#td_Reson").val("");
		}
	}
</script>
	</head>

	<body>
		<input type="hidden" name="message" id="message" value="${message}" />

		<form id="refundForm" action="exitCard.act" method="post"
			autocomplete="off">


			<table align="center" border="0" width="100%" class="form_table"
				id="table">
				<input type="hidden" name="userid" id="userid" value="${map.USER_ID}"/>
				<input type="hidden" name="cardNum" id="cardNum" />
				<input type="hidden" name="blackResidentNo" id="blackResidentNo" />
				<tr>
					<td class="form_label">
						<font style="color: red"></font>访客姓名：
					</td>
					<td>
                       
						${map.VI_NAME}
					</td>

					<td class="form_label">
						身份证号：
					</td>
					<td>
						${map.RESIDENT_NO }
					</td>
					<td class="form_label">
						手机号：
					</td>
					<td>
						${map.VI_MOBILE }
					</td>
				</tr>
				<tr>
					<td class="form_label">
						邮箱：
					</td>
					<td>
						${map.VI_MAIL }
					</td>

					<td class="form_label">
						车牌：
					</td>
					<td>
						${map.PLATE_NUM }
					</td>
					<td class="form_label">
						受访者姓名：
					</td>
					<td>
					    
						${map.USER_NAME}
					</td>
				</tr>
				<tr>

					<td class="form_label">
						机构名：
					</td>
					<td>
						${map.ORG_NAME}

					</td>

					<td class="form_label">
						来访人数：
					</td>
					<td>
						${map.VS_NUM}
					</td>
					<td class="form_label">
						访问时间：
					</td>
					<td>
						${map.VS_ST}
					</td>
				</tr>
				<tr>

					<td class="form_label">
						结束时间：
					</td>
					<td>
						${map.VS_ET}
					</td>
					<c:choose>
						<c:when test="${map.CARD_TYPE eq '2'}">
							<td class="form_label">
								卡类型：
							</td>
							<td id="cardType">
								临时卡
							</td>
						</c:when>
						<c:otherwise>

							<td class="form_label">
								卡类型：
							</td>
							<td id="cardType">
								市民卡
							</td>
						</c:otherwise>

					</c:choose>
					<td class="form_label">
						卡号：
					</td>
					<td>
						<label>
							${map.CARD_ID}
						</label>
					</td>

				</tr>

				<tr>
				<c:choose>
							<c:when test="${map.VS_RETURN eq '1'}">

								<td class="form_label">
									状态 ：
								</td>
								<td>
									<label>
										归还
									</label>
								</td>
							</c:when>
							<c:when test="${map.VS_RETURN eq '2'}">

								<td class="form_label">
									状态 ：
								</td>
								<td>
									<label>
										未归还
									</label>
								</td>
							</c:when>
							<c:otherwise>
							<td class="form_label">
									状态 ：
								</td>
								<td>
									<label>
										未绑定
									</label>
								</td>
							</c:otherwise>
						</c:choose>
					<td class="form_label">
						来访理由：
					</td>
					<td>
					
						<textarea rows="" cols="" readonly="readonly">${map.VS_INFO}</textarea>
					</td>
				</tr>

				<tr>
					<td class="form_label">
						<input type="checkbox" name="addblack" id="addblack"
							onclick="blackcheckbox()" value="${map.RESIDENT_NO }" />
						加入黑名单
					</td>
					
					
					
					<td id="td_Reson" style="display: none;" colspan="4">
						<s:select list="#application.DICT_BLACK_RESONS" headerKey="" headerValue="请选择..."  listKey="dictcode" listValue="dictname" name="blackReson" id="blackReson"/>
						
						<!--<textarea cols="30" rows="3" name="blackReson" id="blackReson"></textarea>
					--></td>
			 </tr>
				
			</table>
			<div id="all_table">
				<table align="center" border="0" width="100%" class="form_table"
					id="table1">
					<input type="hidden" name="maincaid" id="maincaid" />
					<tr>
						<td class="form_label" id="checkbox_display">
							<input type="checkbox" name="father" id="father"
							onclick="maincards()" value="${map.CARD_ID}" />
							主卡
						</td>
					</tr>

				</table>
				<table align="center" border="0" width="100%" class="form_table"
					id="table2">
					<tr>
						<td class="form_label" colspan="10" align="center">
							副卡列表
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" name="sonall" id="sonall" />
							全选副卡
						</td>
						<td class="form_label">
							姓名 ：
						</td>
						<td class="form_label">
							身份证号 ：
						</td>
						<td class="form_label">
							副卡号 ：
						</td>
						<td class="form_label">
							状态 ：
						</td>
					</tr>
					<c:if test="${null!=vvaList}">
						<c:forEach items="${vvaList}" var="vva" varStatus="num">
							<tr>
								<td>
									<input type="checkbox" name="checkbox" id="soncard"
										value="${vva.cardId}" />
								</td>

								<td>
									${vva.vaBak1}
								</td>

								<td>
									${vva.residentNo }
								</td>

								<td>
									${vva.cardId}
								</td>
								<c:choose>
									<c:when test="${vva.vaReturn eq '1'}">
										<td>
											<label>
												归还
											</label>
										</td>
									</c:when>
									<c:otherwise>

										<td>
											<label>
												未归还
											</label>
										</td>
									</c:otherwise>

								</c:choose>

							</tr>
						</c:forEach>
					</c:if>
					<tr>
						<td class="form_label" colspan="10" align="center">

							<input type="button" name="refund" id="refund" value=" 退卡"
								onclick="refund()" />
						</td>
					</tr>
				</table>
			</div>

		</form>
	</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","退卡成功!","info");
	 closeEasyWin('winIdss');

</c:if>

<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","退卡失败!","error");
	closeEasyWin('winIdss');
</c:if>
</script>
