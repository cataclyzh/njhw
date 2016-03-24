<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>停车场监控</title>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
			<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
			
<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>

<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
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

<script type="text/javascript">

function queryVehicles(){
	
	/**
	*停车场监控信息查询
	* 1:全部车辆
	* 2:入闸车辆
	* 3:出闸车辆
	*/
	var carType = $("#deviceType").val();
	var carNo = $("#reportUserName").val();
	
	if (carNo == ""){
		if (carType == 1){
			//所有车辆
			$("#interfaceType").val("allParking");
		}else if (carType == 2){
			$("#interfaceType").val("intoParking");
		}else{
			$("#interfaceType").val("outOfParking");
		}
	}else{
		//有车牌号
		if (carType == 1){
			//所有车辆
			$("#interfaceType").val("allParking");
		}else if (carType == 2){
			$("#interfaceType").val("intoParking");
		}else{
			$("#interfaceType").val("outOfParking");
		}
		$("#carNo").val(carNo);;
	}
	querySubmit();
}

function test(){
	/**
	*停车场监控信息查询
	* 1:全部车辆
	* 2:入闸车辆
	* 3:出闸车辆
	*/
	var carType = $("#deviceType").val();
	var carNum = $("#reportUserName").val();
	
	//没有输入车牌号
	if ("" == carNum){
		if (carType == 1 ){
			$.post(
				"${ctx}/app/parkinglot/parkinglotInfoForCarType.act",
				{
					carType:'entire'
				},
				function(returnedData,status){
					if (returnedData){
						//页面中展示数据
						showTable(returnedData);
					}
				});
			} else if (carType == 2 ){
				$.post(
					"${ctx}/app/parkinglot/parkinglotInfoForCarType.act",
					{
						carType:'intoParking'
					},
					function(returnedData,status){
						if (returnedData){
							alert(returnedData);
						}
					});
				} else if (carType == 3 ){
					$.post(
						"${ctx}/app/parkinglot/parkinglotInfoForCarType.act",
						{
							carType:'outOfParking'
						},
						function(returnedData,status){
							if (returnedData){
								alert(returnedData);
							}
						});
					} 
				}else{
					//有输入车牌号
					if (carType == 1 ){
						$.post(
							"${ctx}/app/parkinglot/queryParkingLotInfoForCarNum.act",
							{
								carType:'entire',
								carNum:carNum //车牌号
							},
							function(returnedData,status){
								if (returnedData){
									alert(returnedData);
								}
							});
						} else if (carType == 2 ){
							$.post(
								"${ctx}/app/parkinglot/queryParkingLotInfoForCarNum.act",
								{
									carType:'intoParking', //入闸车辆中查询
									carNum:carNum //车牌号
								},
								function(returnedData,status){
									if (returnedData){
										alert(returnedData);
									}
								});
							} else if (carType == 3 ){
								$.post(
									"${ctx}/app/parkinglot/queryParkingLotInfoForCarNum.act",
									{
										carType:'outOfParking', //出闸车辆中查询
										carNum:carNum //车牌号
									},
									function(returnedData,status){
										if (returnedData){
											alert(returnedData);
										}
									});
								} 
				}
		}

</script>
	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div style="overflow-y:auto; height:600px">
		<div class="fkdj_index">
			<div class="oper_border_right">
				<div class="oper_border_left">
					停车场监控信息
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
                      <form id="queryForm" name="queryForm" action="${ctx}/app/omc/vehicleAccessInfo.act" method="post" >				
                          <h:panel id="query_panel" width="100%" title="查询条件">
								<table align="center" id="hide_table" border="0" width="100%">
									<tr>
										<td class="fkdj_name">
											出入闸车辆
										</td>
										<td>
										<select  class="fkdj_from_input" name="interfaceType" id="deviceType" style="height:30px;font-size:15px;" >
										<option value="1" >全部</option>
										<option value="intoParking" <c:if test="${interfaceType=='intoParking'}">selected="selected"</c:if>>入闸车辆</option>
										<option value="outOfParking" <c:if test="${interfaceType=='outOfParking'}">selected="selected"</c:if>>出闸车辆</option>
										</select>
										</td>
										<td class="fkdj_name">
											内外部车辆
										</td>
										<td>
										<select  class="fkdj_from_input" id="status" name="carBelongs" style="height:30px;font-size:15px;" >
												<option value="1">全部</option>
												<option value="Internal" <c:if test="${carBelongs=='Internal'}">selected="selected"</c:if>>内部车辆</option>
												<option value="External" <c:if test="${carBelongs=='External'}">selected="selected"</c:if>>外部车辆</option>
										</select>
										</td> 
									</tr>
									<tr>
										<td class="fkdj_name">
											车牌号码
										</td>
										<td>
										    <input type="text" id="reportUserName" name="carNo" cssClass="fkdj_from_input" style="height:30px;font-size:15px" value='${carNo}' />
										</td>
										<td colspan="2" align="center">
												<a class="botton_oper_a"
													
													onclick="querySubmit();"
													style="cursor:pointer;margin-right:40px;"
													>查询 </a>
										</td>
									</tr>
								</table>
							</h:panel>
							<h:page id="list_panel" width="100%" title="结果列表">
								<s:textfield name="page.pageSize" id="pageSize" theme="simple"
											cssStyle="display:none" onblur="return checkPageSize();"
											onkeyup="value=value.replace(/[^\d]/g,'');" />
								<d:table name="page.result" id="row" uid="row" export="false" class="table">	
								    <d:col class="display_leftalign" title="车牌号码">
										<c:choose>
										   <c:when test="${empty row.CAR_NO}">
										 		  无
										   </c:when>
										   <c:otherwise>  
										   		${row.CAR_NO}
										   </c:otherwise>
										</c:choose>
									</d:col>
									<d:col property="TIME" class="display_leftalign" title="出入日期">
									</d:col>
									<d:col class="display_leftalign" title="出入闸" >
										<c:if test="${row.INTERFACE_TYPE  == 'getCarType'}">
												<span class="display_centeralign">入闸</span>
										</c:if>
										<c:if test="${row.INTERFACE_TYPE == 'checkFee'}">
												<span class="display_centeralign">出闸</span>
										</c:if>
										<c:if test="${row.INTERFACE_TYPE == 'carIsAailable'}">
												<span class="display_centeralign">入闸</span>
										</c:if>
									</d:col>
									</d:table>
							</h:page>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
