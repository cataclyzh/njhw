<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>
		<c:choose>
			<c:when test="${isAll==true}">
				全部意见	
			</c:when>
			<c:otherwise>
				我的意见
			</c:otherwise>
		</c:choose>
		</title>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
			rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/suggest/css/qbyj_index.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/integrateservice/css/wizard_css.css"
			rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script src="${ctx}/app/suggest/js/suggesst.js" type="text/javascript"></script>
<!--		<script src="${ctx}/scripts/placeholder/jquery.placeholder.1.3.min.js" type="text/javascript"></script>-->
		<script type="text/javascript">
			//搜索意见
			function searchSuggest(){
				var url = "${ctx}/app/suggest/suggestList.act?isAll=${isAll}";
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				var status = $("#status").val();
				var content = $("#content").val();
				var pageNo = $("#pageNo").val();
				var data = {startDate:startDate,endDate:endDate,status:status,content:content,pageNo:pageNo};
				sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
			}

			function searchPageSuggest(){
				var url = "${ctx}/app/suggest/suggestList.act?isAll=${isAll}";
				var startDate = $("#startDateHidden").val();
				var endDate = $("#endDateHidden").val();
				var status = $("#statusHidden").val();
				var content = $("#contentHidden").val();
				var pageNo = $("#pageNo").val();
				var data = {startDate:startDate,endDate:endDate,status:status,content:content,pageNo:pageNo};
				sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
			}
			
			function btnSearchSuggest(){
				$("#pageNo").val(1);
				searchSuggest();
			}
			
			$(function(){
				//初始意见箱管理列表
				searchSuggest();
				
				//$.Placeholder.init({ color : 'rgb(255, 255, 0)' });
				//$.Placeholder.cleanBeforeSubmit();
				
			});
			
			function goPage(pageNo){
				if (null != pageNo){
					$("#pageNo").val(pageNo);
				}
				searchPageSuggest();
			}
			
			//显示隐藏回复
			function toggleReply(sugid,obj){
				$("div[id^='"+sugid+"_']").toggle();
				
				if($(obj).hasClass('qbyj_no_a')){
					$(obj).attr("class","qbyj_no_as");
					$(obj).html("查看回复");
				}else{
					$(obj).attr("class","qbyj_no_a");
					$(obj).html("收起回复");
				}
				
			}
			function wireless(){
				var url="${ctx}/app/suggest/wireless.act";
				window.location.href=url;
			}
		</script>
	</head>
	<body>
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp" >
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="qbyj_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">
						意见箱
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				    <div class="fkdj_index">
					    <div class="qbyj_left">
							<%@ include file="/app/suggest/suggesstLeft.jsp" %>
						</div>
						<div class="qbyj_right">
							<div class="bgsgl_border_left">
								<div class="qbyj_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">
									<c:choose>
										<c:when test="${empty isAll or isAll==true}">
											全部意见	
										</c:when>
										<c:otherwise>
											我的意见										
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="bgsgl_conter">
								<div class="qbyj_top">
								<h:panel id="query_panel" width="100%" title="查询条件">
									<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
										<tr>
											<td width="10%">
												&nbsp;
												<input id="pageNo" value="1" type="hidden">
											</td>
											<td>时间:</td>
											<td>
												<input class="qbyj_input_text" name="startDate" id="startDate" placeholder="输入时间 年-月-日" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'endDate\',{M:-0,d:0})||\'2020-10-01\'}',el:'startDate'})" />
											</td>
											<td width="1%">
												至&nbsp;&nbsp;
											</td>
											<td>
												<input class="qbyj_input_text" name="endDate" id="endDate" placeholder="输入时间 年-月-日" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'startDate\',{M:-0,d:0})}',maxDate:'2020-10-01',el:'endDate'})"/>
											</td>
											<td width="1%">
												&nbsp;&nbsp;
											</td>
											<td>关键字:</td>
											<td>
												<input class="qbyj_input" name="content" id="content" placeholder="输入关键字" type="text" />
											</td>
											<td width="4%">
												状态
											</td>
											<td>
												<select class="qbyj_select" name="status" id="status">
													<option value="">
														全部
													</option>
													<option value="1">
														已回复
													</option>
													<option value="0">
														未回复
													</option>
												</select>
											</td>
											<td>
												<a href="javascript:void(0);" onclick="btnSearchSuggest();"><img src="images/qbyj_sou.jpg" width="65"
														height="22" border="0" />
												</a>
												<%-- <a href="javascript:void(0);" onclick="wireless();">无线
												</a>--%>
											</td>
										</tr>
									</table>
									</h:panel>
								</div>
								<div class="qbyj_conter" id="qbyj_conter_div">
								</div>
							</div>
						</div>
				    </div>
				    <!--此处写页面的内容 -->
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
	</body>
</html>