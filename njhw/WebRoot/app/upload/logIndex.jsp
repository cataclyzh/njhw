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
		操作日志
		</title>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/qbyj_index.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/table.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<!--		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>-->
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<!--		<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>-->
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script src="${ctx}/common/pages/misc/js/ctt.js" type="text/javascript"></script>
		<script type="text/javascript">
			function addRecord(){
				var url = "${ctx}/common/constants/input.act";
				openEasyWin("cttInput","录入常量信息",url,"800","450",false);
			}
			function updateRecord(id){
				var url = "${ctx}/common/constants/input.act?cttid="+id;
				openEasyWin("cttInput","修改常量信息",url,"800","450",false);
			}
			function delRecord(){
				var items = $("input[name='_chk']:checked");
				var chkArray = new Array();
				for(var i=0;i<items.size();i++){
					chkArray.push(items.eq(i).val());
				}
				if($("input[@type=checkbox]:checked").size()==0){
					easyAlert('提示信息','请勾选要删除的记录！','info');
				}else{
					easyConfirm('提示', '确定删除？', function(r){
						if (r){
							$.ajax({
								   type: "POST",
								   url: "${ctx}/common/constants/delete.act",
								   datatype:"json",
								   data:{"deleteIds":chkArray.join(",")},
								   success: function(msg){
								       alert("删除成功！");
								       searchLog();
								   }
								}); 
						}
					});		
				}
		    }
		    
		    function searchLog(){
		    	var url = "${ctx}/app/log/query/list.act";
		    	var pageNo = $("#pageNo").val();
		    	var resName = $.trim($("#resName").val());
		    	var insertName =$.trim($("#insertName").val());
				var bmType = $("#bmType").val();
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();		    	
		    	
		    	var data = {
		    		"page.pageNo":pageNo,
		    		"resName":resName,
		    		"insertName":insertName,
		    		"bmType":bmType,
		    		"startTime":startTime,
		    		"endTime":endTime
		    		};
		    	sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
		    }

		    function searchPageLog(){
		    	var url = "${ctx}/app/log/query/list.act";
		    	var pageNo = $("#pageNo").val();
		    	var resName = $("#resNameHidden").val();
		    	var insertName =$("#insertNameHidden").val();
		    	var bmType = $("#bmTypeHidden").val();
		    	var startTime = $("#startTimeHidden").val();
				var endTime = $("#endTimeHidden").val();		    
				
		    	var data = {
		    		"page.pageNo":pageNo,
		    		"resName":resName,
		    		"insertName":insertName,
		    		"bmType":bmType,
		    		"startTime":startTime,
		    		"endTime":endTime
		    		};
		    	sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
		    }
		    
		    function btnSearchCtt(){
				$("#pageNo").val(1);
				searchLog();
			}
			
			$(function(){
				//初始常量列表
				searchLog();
				$("#cttKey").unbind();
				$("#cttKey").bind("keydown",function(event){
					if(event.keyCode==13){
						btnSearchCtt();
					}
				});
			});
			
			function goPage(pageNo){
				if (null != pageNo){
					$("#pageNo").val(pageNo);
				}
				searchLog();
			}
			
			function updateCallBack(){
				searchLog();
			}
			
			function resetQuery(){
				$('#resName').val('');
				$('#insertName').val('');
				//searchLog();
			}
			
			/*导出excel*/
		function expLog(){
		    	var resName = $.trim($('#resNameHidden').val());
		    	var insertName = $.trim($('#insertNameHidden').val());
		    	var bmType = $("#bmTypeHidden").val();
				var startTime = $("#startTimeHidden").val();
				var endTime = $("#endTimeHidden").val();	
		    	
			var url="${ctx}/app/log/query/exportLog.act?resName="+resName+"&insertName="+insertName+"&bmType="+bmType+"&startTime="+startTime+"&endTime="+endTime;
			window.location.href=url;
		}
			
		</script>
	</head>
	<body>
		<input id="pageNo" value="1" type="hidden">
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp" ></jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div style="min-height: 300px">
				    <div class="fkdj_index">
					    <div class="images_left">
							<%@ include file="/common/pages/misc/cttLeft.jsp" %>
						</div>
						<div class="images_right">
							<div class="bgsgl_border_left">
							  <div class="ctt_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">操作日志</div>
							</div>
							<div class="bgsgl_conter">
								<div class="qbyj_top">
								<h:panel id="query_panel" width="100%" title="查询条件">
									<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
								        <tr>
								          <td width="10%" align="right">
								           	设备名称：
								          </td>
								          <td width="10%">
								          	<input type="text" id="resName" size="30" />
								          </td>
								          <td width="10%" align="right">
								           		开始时间
								          </td>
									      <td width="10%">
									      	<input class="qbyj_input_text" name="startTime" id="startTime" placeholder="年-月-日 时:分:秒" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'endTime\',{M:-0,d:0})||\'2020-10-01\'}',el:'startTime'})" />
									      </td>
								       	  <td width="10%" align="right">
								           		结束时间
								          </td>
									      <td width="10%">
									      	<input class="qbyj_input_text" name="endTime" id="endTime" placeholder="年-月-日 时:分:秒" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'startTime\',{M:-0,d:0})}',maxDate:'2020-10-01',el:'endTime'})"/>
									      </td>
								        </tr> 
								        <tr>
								        	<td width="10%" align="right">
								           		设备类型：
								          	</td>
									        <td width="10%">
									          	<select id="bmType">
									          		<option value="">请选择</option>
										          	<c:forEach items="${deviceTypeMap}" var="deviceType">
										          		<option value="${deviceType.key }">${deviceType.value }</option>
										          	</c:forEach>
										         </select>
									        </td>
									        <td width="10%" align="right">
								           		操作人：
								            </td>
								            <td width="10%">
								          		<input type="text" id="insertName" size="30" />
								            </td>
								        	<td colspan="4">
								               <a class="fkdj_botton_reset" href="javascript:void(0);" id="resetbutton"  class="images_search" onclick="resetQuery();">重&nbsp;&nbsp;置</a>
								               <a class="fkdj_botton_reset" id="searchbutton" href="javascript:void(0);" onclick="btnSearchCtt()">查&nbsp;&nbsp;询</a>
								          	</td>
								        </tr>    
								      </table>
								      </h:panel>
								</div>
								<div class="qbyj_conter" id="qbyj_conter_div">
									<%--<%@ include file="/app/upload/logMessageList1.jsp" --%>
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