﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="p" %>

<html>
<head>
	<%@ include file="/common/include/meta.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我的访客</title>
	<script type="text/javascript">
		var _ctx = '${ctx}';
	</script>
	<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
		rel="stylesheet" type="text/css" />
	<script src="${ctx}/scripts/basic/jquery.js.gzip"
		type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
		type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript"
		src="${ctx}/app/portal/toolbar/showModel.js"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<link href="${ctx}/app/personnel/telAndNumber/css/TelMessage.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
</head>
<body>
	
<form action="queryMyCallList.act" method="post" id="CallForm">
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						我的访客
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 100px">
				    <!--此处写页面的内容 -->
					<div class="main1_main2_right">
						<input type="hidden" id="statusFlag" name="statusFlag" value="${flag}" />
						<input type="hidden" id="vsTypeFlag" name="vsTypeFlag" value="${vtFlag}" />
						<script type="text/javascript">
							function changeStatusSubmit(obj){
								if("flag" == obj){
									$("#statusFlag").attr('value',$("#allStatusFlag").val());
									$("#CallForm").submit();
								}
								else{
									$("#vsTypeFlag").attr('value',$("#allVsTypeFlag").val());
									$("#CallForm").submit();
								}
							}
						</script>
						<ul>
							<div class="caller_index">
								<span class="caller_title">访客姓名</span>
								<span class="caller_fist">访问事由</span>
								<span class="caller_left">到访时间</span>
								<span class="caller_left">结束时间</span>
								<span class="caller_left">访问状态：
									<select id="allStatusFlag" style="height: 17px;line-height: 17px;" onchange="changeStatusSubmit('flag')">
										<option value="all" <c:if test="${flag == 'all' }">selected="selected"</c:if>>全部</option>
										<!--  option value="00" <c:if test="${flag == '00' }">selected="selected"</c:if>>初始预约</option>-->
										<option value="01" <c:if test="${flag == '01' }">selected="selected"</c:if>>已申请</option>
										<option value="02" <c:if test="${flag == '02' }">selected="selected"</c:if>>已确认</option>
										<option value="03" <c:if test="${flag == '03' }">selected="selected"</c:if>>已拒绝</option>
										<option value="04" <c:if test="${flag == '04' }">selected="selected"</c:if>>已到访</option>
										<option value="05" <c:if test="${flag == '05' }">selected="selected"</c:if>>已结束</option>
										<option value="99" <c:if test="${flag == '99' }">selected="selected"</c:if>>已取消</option>
										<option value="06" <c:if test="${flag == '06' }">selected="selected"</c:if>>异常结束</option>
									</select>
								</span>
								<span class="caller_left">预约性质：
									<select id="allVsTypeFlag" style="height: 17px;line-height: 17px;" onchange="changeStatusSubmit('type')">
										<option value="all" <c:if test="${vsFlag == 'all' }">selected="selected"</c:if>>全部</option>
										<option value="1" <c:if test="${vtFlag == '1' }">selected="selected"</c:if>>主动预约</option>
										<option value="2" <c:if test="${vtFlag == '2' }">selected="selected"</c:if>>公网预约</option>
										<option value="3" <c:if test="${vtFlag == '3' }">selected="selected"</c:if>>前台预约</option>
									</select>
								</span>
							</div>
			    			<c:if test="${not empty queryVisitorDetailInfo }">
								<c:forEach items="${queryVisitorDetailInfo }" var="listDate" varStatus="status">
									<li>
										<div class="caller_center"><a class="caller_conter_right">${listDate.VI_NAME }</a>
										&nbsp;<span style="cursor: pointer;" class="caller_fist" title="${empty listDate.VS_INFO ? '访问事由' : listDate.VS_INFO}" onclick="show('${listDate.VS_ID}')">
											<script type="text/javascript">
											var str='${listDate.VS_INFO}';
											var textStr;
											if(str.length>30){
												str=str.substring(0,30);textStr=str + " ...";
											}else{
												textStr=str;
											}
											if(textStr == ""){
												textStr = "访问事由";
											}
											document.write(textStr);
											</script>
										</span>
										<span style="cursor: pointer;" class="caller_right" onclick="show('${listDate.VS_ID}')">${listDate.VSST }</span>
										<span style="cursor: pointer;" class="caller_right" onclick="show('${listDate.VS_ID}')">${listDate.VSET }</span>
										<input type="hidden" id="vs_flag" name="vs_flag" value="${listDate.VS_FLAG }" />
										&nbsp;&nbsp;<span class="${listDate.VS_FLAG == '01' ?'main3_list4 caller_right' : 'caller_refuse caller_right'}" style="cursor: pointer;" title="[${listDate.VSFLAG }]" onclick="show('${listDate.VS_ID}')">
											[${listDate.VSFLAG }]
										</span>
										<span style="cursor: pointer;" class="caller_refuse caller_right" title="[${listDate.VSFLAG }]" onclick="show('${listDate.VS_ID}')">
											[${listDate.VSTYPE }]
										</span>
										</div>
									</li>
								</c:forEach>
							</c:if>
						</ul>
						<p:pager recordCount="${recordCount}" pageSize="10" pageNo="${pageNo}" recordShowNum="4" url="${ctx}/app/personnel/telAndNumber/telAndNumberList.act"/>
						<div class="clear"></div>
					</div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
		</form>
	</body>

</html>
<script type="text/javascript">
	//分页附属方法
	function goPage(a){
		var url = "${ctx}/message/queryMyCallList.act?page="+(a);
		window.location.href = url;
	}

	function show(vsid){
		var url = "${ctx}/app/myvisit/showOpt.act?vsid="+vsid;
		//openEasyWin("winId","访问申请",url,"700","465",true);
		showShelter("700", "465", url);
	}
	
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					var result = new Array(); 
					$("div [id^='checked_0']").each(function(){
						if($(this).is(":checked")){
							result.push($(this).attr("value"));
						}
					});	
					var msgId = result.join(",");
					url = "delMess.act?msgIds="+msgId;
					window.location.href = url;
					//$('#queryForm').attr("action","msgBoxAction_delMess.act");
					//$('#queryForm').submit();
				}
			});		
		}
	}
	var flag = false;
	// CHECKED 全选效果
	function checkAll(){
		//var flag = $("div [id^='checked_0']").attr("checked");
		
		if(flag){
			//取消选中
			/* $("div [id^='checked_0']").each(function(){
				$(this).attr('checked',!$(this).attr('checked'));
			}); */
			$("div [id^='checked_0']").attr("checked",false);
			flag = false;
		}else{
			//选中全部
			$("div [id^='checked_0']").attr("checked",true);
			flag = true;
		}
	}
</script>