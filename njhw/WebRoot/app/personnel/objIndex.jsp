<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>功能菜单管理</title>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
			rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/qbyj_index.css"
			rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/table.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/common/pages/misc/css/admin_css.css"
			rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/integrateservice/css/wizard_css.css"
			rel="stylesheet" type="text/css" />
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>		
	<script src="${ctx}/common/pages/misc/js/ctt.js" type="text/javascript"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script type="text/javascript">
			var _ctx = '${ctx}';
			function selectCheckbox(obj){
				var items = $("input[name='_chk']:checked");
				if(items){
					if(items.length==1){
						$("#chaifen").show();
						$("#hebing").hide();
						$("input[name='_chk']").attr("disabled",false);
					}else if(items.length==2){
						$("#hebing").show();
						$("#chaifen").hide();
						var tempItems = $("input[name='_chk']").not("input[name='_chk']:checked");
						for(var i=0;i<tempItems.size();i++){
							tempItems.eq(i).attr("disabled",false);
						}
					}else if(items.length>2){
						$(obj).checked=false;
						$("input[name='_chk']").attr("disabled",false);
					}else{
						$("#chaifen").hide();
						$("#hebing").hide();
						$("input[name='_chk']").attr("disabled",false);
					}
				}else{
					$("#chaifen").hide();
					$("#hebing").hide();
					$("input[name='_chk']").attr("disabled",false);
				}
			}
			
			function addRecord(){
				var url = "${ctx}/app/per/objInput.act?PId=" +$('#PId').val() +"&res='M'" ;
				//showShelter("800","450",url);
				showShelter("620","330",url);
			}
			function updateRecord(id){
				var url = "${ctx}/app/per/objInput.act?nodeId="+id +"&res="+$('#res').val();
				//showShelter("800","450",url);
				showShelter("620","330",url);
			}
		    function searchRes(){
		    	var url = "";
		    	if($("#PId")&&$("#PId").val()){
		    		url = "${ctx}/app/per/menuList.act?PId="+$("#PId").val()+"&resType=D";
		    	}else{
		    		if($("#nodeId")&&$("#nodeId").val()){
		    			url = "${ctx}/app/per/menuList.act?nodeId="+$("nodeId").val()+"&resType=D";
		    		}else{
		    			url = "${ctx}/app/per/menuList.act?nodeId=30&resType=M";
		    		}
		    	}
		    	var title = $.trim($("#title").val());
		    	var name = $.trim($("#name").val());
		    	var keyword = $.trim($("#keyword").val());
		    	var pageNo = $("#pageNo").val();
		    	var data = {"title":title,"name":name,"keyword":keyword,"page.pageNo":pageNo};
		    	sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
		    }

		    function searchPageRes(){
		    	var url = "";
		    	if($("#PId")&&$("#PId").val()){
		    		url = "${ctx}/app/per/menuList.act?PId="+$("#PId").val()+"&resType=D";
		    	}else{
		    		if($("#nodeId")&&$("#nodeId").val()){
		    			url = "${ctx}/app/per/menuList.act?nodeId="+$("nodeId").val()+"&resType=D";
		    		}else{
		    			url = "${ctx}/app/per/menuList.act?nodeId=30&resType=M";
		    		}
		    	}
		    	var title = $("#titleHidden").val();
		    	var name = $.trim($("#name").val());
		    	var keyword = $.trim($("#keyword").val());
		    	var pageNo = $("#pageNo").val();
		    	var data = {"title":title,"name":name,"keyword":keyword,"page.pageNo":pageNo};
		    	sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
		    }
		    
		      function resetListRes(url){
		    	var data = {"page.pageNo":pageNo};
		    	sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
		    }
		    
		    function resetListRes(url){
		    	$('#queryForm').get(0).reset()
		    	var data = {};
		    	sucFun = function (data){
					$("#qbyj_conter_div").html(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
		    }
		    
		    function btnSearchRes(){
				$("#pageNo").val(1);
				searchRes();
			}
			
			$(function(){
				//初始常量列表
				searchRes();
			});
			
			function goPage(pageNo){
				if (null != pageNo){
					$("#pageNo").val(pageNo);
				}
				searchPageRes();
			}
			
			function frameDialogClose (responseText,sugid){
				deleteDiv("fade");
				deleteDiv("Main_fade_iframe");
				searchRes();
			}
			
			function resetQuery(){
				$('#title').val('');
			}
			
		</script>
	</head>
	<body>
		<input id="pageNo" value="1" type="hidden">
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="0"
					name="gotoParentPath" />
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;height:550px;">
				<div style="min-height: 300px">
					<div class="fkdj_index">
						<div class="images_left">
							<%@ include file="/common/pages/misc/cttLeft.jsp"%>
						</div>
						<div class="images_right"style="height:550px;">
							<div class="bgsgl_border_left">
								<div class="ctt_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">
									功能菜单管理
								</div>
							</div>
							<div class="bgsgl_conter" style="height:500px;">
								<div class="admin_conter_left" style="height:100%">
									<div class="bgsgl_right_list_border">
										<div class="bgsgl_right_list_left">
											功能菜单
										</div>
									</div>
									<div class="admin_height_10">
<!--										<div class="admin_left_title">-->
<!--											功能树-->
<!--										</div>-->
										<iframe scrolling="auto" frameborder="0" name="ifrmOrgTree" id="ifrmObjTree" src="${ctx}/app/per/objTreeMenu.act?resType=${resType}"
											border="0" marginheight="0" marginwidth="0" frameborder="0" 
											style="width:100%;height:450px;padding:0px;min-height: 450px"></iframe>
									</div>
								</div>
								<div class="admin_conter_right">
									<%--<div class="bgsgl_right_list_border">
										<div class="bgsgl_right_list_left">
											查询条件
										</div>
									</div>--%>
									<div class="qbyj_top admin_height_10">
									<h:panel id="query_panel" width="100%" title="查询条件">
										<form id="queryForm">
										<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
									        <tr>
									        <td class="form_label" style="width: 25px;" align="left">
									           	菜单名称：
									        </td>
									        <td width="10%">
												<input type="text" name="title" id="title" size="12"/>
									        </td>
									        <td class="form_label" style="width: 25px;display:none;" align="left">
									       	  	资源编码：       	  	
									        </td>
									        <td width="10%" style="display:none;">
									        	<input type="text" name="name" id="name" size="12"/>
									        </td>
									        <td class="form_label" style="width: 25px;display:none;" align="left">
									           	资源KEY：
									        </td>
									        <td  width="10%" style="display:none;">
									           <input type="text" name="keyword" id="keyword" size="12"/>
									        </td>
									        <td width="25%" style="text-align: center;">
									          	<a href="javascript:void(0);" class="fkdj_botton_reset" id="resetbutton" iconCls="icon-reload" onclick="resetQuery();">重置</a>
									        	<a href="javascript:void(0);" class="fkdj_botton_reset" id="searchbutton" iconCls="icon-search"  onclick="btnSearchRes();">查询</a>
									        </td>
									       
									        </tr>     
									      </table>
									      </form>
									      </h:panel>
									</div>
									<div class="qbyj_conter" id="qbyj_conter_div" style="height:500px;">
									</div>
								</div>
								<div class="bgsgl_clear"></div>
							</div>
						</div>
						<div class="bgsgl_clear"></div>
					</div>
				</div>
			</div
			<div class="bgsgl_clear"></div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>

	</body>
</html>