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
		业务字典
		</title>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/qbyj_index.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/table.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
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
		    
		    function searchDict(){
		    	var url = "${ctx}/common/dictMgr/list.act";
		    	var pageNo = $("#pageNo").val();
		    	var dicttypename = $.trim($("#dicttypename").val());
		    	var dicttypecode = $.trim($("#dicttypecode").val());
		    	
		    	var data = {
		    		"page.pageNo":pageNo,
		    		"dicttypename":dicttypename,
		    		"dicttypecode":dicttypecode
		    		};
		    	sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
		    }
		    
		    function searchPageDict(){
		    	var url = "${ctx}/common/dictMgr/list.act";
		    	var pageNo = $("#pageNo").val();
		    	var dicttypename = $.trim($("#dicttypenameHidden").val());
		    	var dicttypecode = $.trim($("#dicttypecodeHidden").val());
		    	
		    	var data = {
		    		"page.pageNo":pageNo,
		    		"dicttypename":dicttypename,
		    		"dicttypecode":dicttypecode
		    		};
		    	sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
		    }
		    
		    function btnSearchDict(){
				$("#pageNo").val(1);
				searchDict();
			}
			
			$(function(){
				//初始常量列表
				searchDict();
			});
			
			function goPage(pageNo){
				if (null != pageNo){
					$("#pageNo").val(pageNo);
				}
				searchPageDict();
			}
			
			function updateCallBack(){
				searchDict();
			}
			
			function resetQuery(){
				$('#dicttypename').val('');
				$('#dicttypecode').val('');
				//searchDict();
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
							  <div class="ctt_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">业务字典</div>
							</div>
							<div class="bgsgl_conter">
								<div class="qbyj_top">
							<h:panel id="query_panel" width="100%" title="查询条件">
									<%--<div class="bgsgl_right_list_border">
										<div class="bgsgl_right_list_left">
											查询条件
										</div>
									</div> --%>
									<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
								        <tr>
								          <td width="10%" align="right">
								           	类型名称：
								          </td>
								          <td width="20%">
								          	<input type="text" id="dicttypename" size="30" />
								          </td>
								          <td width="10%" align="right">
								           	类型编码：
								          </td>
								          <td width="20%">
								          	<input type="text" id="dicttypecode" size="30" />
								          </td>
								          <td colspan="4">
								               <a class="fkdj_botton_reset" href="javascript:void(0);" id="resetbutton"  class="images_search" onclick="resetQuery();">重&nbsp;&nbsp;置</a>
								               <a class="fkdj_botton_reset" id="searchbutton" href="javascript:void(0);" onclick="btnSearchDict()">查&nbsp;&nbsp;询</a>
								          </td>
								        </tr>     
								      </table>
								      </h:panel>
								</div>
								<div class="qbyj_conter" id="qbyj_conter_div">
									<%@ include file="/common/pages/acmgr/dicttypeList1.jsp" %>
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