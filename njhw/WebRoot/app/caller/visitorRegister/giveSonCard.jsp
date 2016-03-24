<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>发放临时卡</title>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script type="text/javascript">	
		  function easyAlert(title,msg,type,fn){
    	using('messager', function () {
    		$.messager.alert(title,msg,type,fn);
    	});	
    }
		
    //增加一行
	function addfield() {
	
		var tr = '<tr><td class="form_label"><font style="color:red">*</font>姓名：</td><td><input name="quserName" theme="simple" size="18" maxlength="20"/></td><td class="form_label"><font style="color:red">*</font>身份证号：</td><td><input name="qresidentNo" theme="simple" size="18" maxlength="20" onblur="javascript:validationCard();" /></td><td class="form_label"><font style="color:red">*</font>副卡号：</td><td><input name="qcardId" theme="simple" size="18" maxlength="20"/></td><td><a onclick="javascript:removeRow(this);" href="javascript:void(0);">取消</a></td></tr>';
		$("#fied_table").append(tr);

	}	
	function removeRow(obj){
		$(obj).parent().parent().remove();
	}	 

	//提交
		function saveData(){
		        
		   if(tableValue()){
		   
		    var  residentNonum = $("#residentNonum").val();
           var cardIdnum = $("#cardIdnum").val();
           var userNamenum =  $("#userNamenum").val();
           var vsid = $("#vsid").val();
        //   alert(residentNonum+"/"+cardIdnum+"/"+userNamenum);
		   		$.ajax({
	             type:"POST",
	             url:"${ctx}/app/visitor/saveSoncard.act",
	             data:{residentNonum:residentNonum,cardIdnum:cardIdnum,userNamenum:userNamenum,vsid:vsid},
	             dataType: 'json',
			     async : false,
			  
			     success: function(json){
		             if (json!=null) {  
		               $("#table_ajaxList").empty();
		                $("#fied_table tr:gt(0)").remove();
		              
		                 $("#fied_table").find("input").val("");  
		                 
		              easyAlert("提示信息","保存成功!");    
			           	$.each(json,function(i,item){
			           		var status = "";
			           		if (item.vaReturn=="1"){
			           		status="归还"
			           		}else {
			           		status="未归还"
			           		}
			           		var trContent = '<tr><td class="form_label">姓名 ：</td><td>'+item.vaBak1+'</td><td class="form_label"> '
			           		+ '身份证号 ：</td><td>'+item.residentNo+'</td><td class="form_label"> '
			           		+'副卡号 ：</td><td>'+item.cardId+'</td><td class="form_label">状态 ：</td><td><label>'+status+'</label></td>'
			           		+'<td class="form_label"><a href="javascript:delect('+item.vaId+')">删除</a></td></tr>'
			           		$("#table_ajaxList").append(trContent);
		 					
					  	});
		            
		          }
	          }
	     	 });
		   
		   }else{
		   easyAlert("提示信息","请输入值!");
		   }
		  
		}
    
    function tableValue(){
    		var res=true;
			var residentNo="";
			var cardId="";
			var userName="";
	
			$("#fied_table tr").each(function(i,item){
		if($($(item).find("td input")[0]).val()=="" || $($(item).find("td input")[1]).val()=="" || $($(item).find("td input")[2]).val()==""){
					res=false
					//alert(res);
		}
				userName += $($(item).find("td input")[0]).val() + ",";
				residentNo += $($(item).find("td input")[1]).val() + ",";
				cardId += $($(item).find("td input")[2]).val() + ",";
				//alert(userName+"/"+residentNo+"/"+cardId);
			});
		
			var userName1= userName.substr(0, userName.length - 1);
			var residentNo1 = residentNo.substr(0, residentNo.length - 1);
			var cardId1=cardId.substr(0, cardId.length - 1);
	     	//alert(userName1+"/"+residentNo1+"/"+cardId1);
	     	 $("#residentNonum").val(residentNo1);
              $("#cardIdnum").val(cardId1);
             $("#userNamenum").val(userName1);
	     	 	
			return res;
			//alert(res);
		}
		//验证省份证号;
		function validationCard(obj){
		var vaidRes=true;
		if(idCardNoUtil.checkIdCardNo(obj.value))
		{
          vaidRes;
           
		}else{	
		obj.value="";
		obj.focus();
		vaidRes=false;
		alert("请输入正确的省份证号!");
		
		}
		}
		
	</script>
	</head>

	<body>
		<table align="center" border="0" width="100%" class="form_table">
			<input type="hidden" name="viId" id="viId" value="${map.VI_ID}" />
			<input type="hidden" name="vsid" id="vsid" value="${map.VS_ID }" />

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
					访问时间：
				</td>
				<td>
					${map.VS_ST}
				</td>
				<td class="form_label">
					结束时间：
				</td>
				<td>
					${map.VS_ET}
				</td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${map.CARD_TYPE eq '2'}">

						<td class="form_label">
							卡类型：
						</td>
						<td>
							<label>
								临时卡
							</label>
						</td>
					</c:when>
					<c:otherwise>

						<td class="form_label">
							卡类型：
						</td>
						<td>
							<label>
								市民卡
							</label>
						</td>
					</c:otherwise>

				</c:choose>

				<td class="form_label">
					卡号：
				</td>
				<td>
					${map.CARD_ID}
				</td>
				<td class="form_label">
					来访人数：
				</td>
				<td>
					${map.VS_NUM}
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
				<td class="form_label"  align="center" colspan="8">
					副卡列表
				</td>
			</tr>
			
		</table>
		<table align="center" id="table_ajaxList" border="0" width="100%"
			class="form_table">
			<c:if test="${null!=vvaList}">
				<c:forEach items="${vvaList}" var="vva" varStatus="num">

					<tr>
						<td class="form_label">
							姓名 ：
						</td>
						<td>
							${vva.vaBak1}
						</td>
						<td class="form_label">
							身份证号 ：
						</td>
						<td>
							${vva.residentNo }
						</td>
						<td class="form_label">
							副卡号 ：
						</td>
						<td>
							${vva.cardId}
						</td>
						<c:choose>
							<c:when test="${vva.vaReturn eq '2'}">

								<td class="form_label">
									状态 ：
								</td>
								<td>
									<label>
										已归还
									</label>
								</td>
							</c:when>
							<c:otherwise>

								<td class="form_label">
									状态 ：
								</td>
								<td>
									<label>
										未归还
									</label>
								</td>
							</c:otherwise>

						</c:choose>
						<td class="form_label">
							<a href="javascript:delect('${vva.vaId }')">删除</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>

		</table>

		<div id="div_band_relative_card">
			
				<input type="hidden" name="residentNonum" id="residentNonum" />
				<input type="hidden" name="cardIdnum" id="cardIdnum" />
				<input type="hidden" name="userNamenum" id="userNamenum" />
				<input type="hidden" name="auxivsId" id="vsId" value="${map.VS_ID}" />
				<a href="javaScript:addfield()">增绑定附卡</a>
				<input type="hidden" name="vsNums" id="txt_Relative_num"
					value="${map.VS_NUM}" />

				<table align="center" border="0" width="100%" class="form_table"
					id="fied_table">
					<tr>
						<td class="form_label">
							<font style="color: red">*</font>姓名：
						</td>
						<td>
							<input name="quserName" theme="simple" size="18" maxlength="20" />
						</td>
						<td class="form_label">
							<font style="color: red">*</font>身份证号：
						</td>
						<td>
							<input name="qresidentNo" theme="simple" size="18" maxlength="20" onblur="javascript:validationCard(this);"/>
						</td>
						<td class="form_label">
							<font style="color: red">*</font>副卡号：
						</td>
						<td>
							<input name="qcardId" theme="simple" size="18" maxlength="20" />
						</td>
						<td>

						</td>
					</tr>
				</table>
		</div>

		<table align="center" border="0" width="100%" class="form_table">
			<tr class="form_bottom">
				<td colspan="6">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save"
						onclick="saveData();">绑定副卡</a>

				</td>
			</tr>
		</table>
		
	</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","发放成功!","info");
	
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","发放失败!","error");
	closeEasyWin('winId');
	
	 
</c:if>

function delect(obj){
		
		var fid = obj;
		var vsid = $("#vsid").val();
		//alert(vsid)
		//alert(obj)
		  $.ajax({
	             type:"POST",
	             url:"${ctx}/app/visitor/deleteVmVisitAuxi.act",
	             data:{fid:fid,vsids:vsid},
	             dataType: 'json',
			     async : false,
			     success: function(json){
	               $("#table_ajaxList").empty();    
		             if (json!=null) {      
		             	$("#table_ajaxList").empty();
			           	$.each(json,function(i,item){
			           		var status = "";
			           		if (item.vaReturn=="2"){
			           		status="归还"
			           		}else {
			           		status="归还"
			           		}
			           		var trContent = '<tr><td class="form_label">姓名 ：</td><td>'+item.vaBak1+'</td><td class="form_label"> '
			           		+ '身份证号 ：</td><td>'+item.residentNo+'</td><td class="form_label"> '
			           		+'副卡号 ：</td><td>'+item.cardId+'</td><td class="form_label">状态 ：</td><td><label>'+status+'</label></td>'
			           		+'<td class="form_label"><a href="javascript:delect('+item.vaId+')">删除</a></td></tr>'
			           		$("#table_ajaxList").append(trContent);
		 				
				  	});
		            
		          }
	          }
	      });
			
		}
		
</script>
