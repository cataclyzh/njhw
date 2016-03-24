<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="${ctx}/app/caller/frontReg/cardSender.js"
	type="text/javascript"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	/*页面 实现预约登记*/


	/* 页面校验 */
	$(document).ready(function() {
		//校验副卡上的信息
		jQuery.validator.methods.fukaName = function(value, element) {
			jQuery("input[name='fuka_name']").each(function(){
		         alert(jQuery(this).val());
			});
	    };
	    
	    // 身份证号码验证 
		jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
		  	return this.optional(element) || idCardNoUtil.checkIdCardNo(value);
		}, "请填写正确的身份证号码"); 
		
		$("#bangding_form").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				comeNum:{
					required: true,
					digits:true,
					min:1
				},
				vm_card_no:{
					required: true
				}
			},
			messages:{ 
	            cityCard:{
	            	//chooseOneCityCard:"市民卡无效!",
	            	//blacklistForCityCard:"您加入黑名单无法预约"
		        }
			}
		});
	});

	/*
	 * 校验副卡的信息
	 */
	function validate_fuka_name(value){
		if(value==''){
			return false;
		}else{
			return true;
		}
	}
	function validate_fuka_shenfei(value){
		if(value==''){
			return false;
		}else{
			return true;
		}
	}
	function validate_fuka_linshi(value){
		if(value==''){
			return false;
		}else{
			return true;
		}
	}
		
	
	/**
	* 访客人数变化函数
	*/
	function visitorNumChange() {
			var comeNum = $("#comeNum").val();
			var firstNum = $("#card_right_bg2>div").length;
			var addNum = comeNum - firstNum;
			if (addNum > 0){
				//$("#card_right_bg2").html("");
				$("#card_right_bg2").show();
				for(var i = 1; i < addNum; i++){
					var j = firstNum + i;
					var trContent = '<div class="card_right_table2_table11">'
					    +'<span class="card_span1">副卡'+ j +'</span>'
					    +'<div class="card_div1">'
						+'<div class="card_div_more1">'
						+'<input type="text" name="fuka_name" value="姓名" style="width:66px;height:26px;line-height:26px">'
						+'<input type="text" name="fuka_shenfen" value="身份证号" style="width:130px;height:26px;line-height:26px">'
						+'<input type="text" name="fuka_linshi"value="临时卡号"  style="width:130px;height:26px;line-height:26px">'
						+'<img src="${ctx}/images/zhucard.png" style="margin-top: 16px;" onclick="readSubCard(this);">'
					    +'</div>'
					    +'</div>'
				        +'</div>'
				       
			    	$("#card_right_bg2").append(trContent);
				}
				$("#card_right_bg2 input[broomcard='broomcard']").click(function() {  
					var str;
					str = WebRiaReader.RiaReadCardEngravedNumber();
					$(this).val(str);
				}); 
			} else {
				var removeNum = firstNum - comeNum + 1;
				if (removeNum > 0) {
					var divs = $("#card_right_bg2>div");
					var divLength = $("#card_right_bg2>div").length;
					for (var i = 0;i < removeNum;i++) {
						$($(divs).get(divLength-i-1)).remove();
					}
				}
				
			}
			if ($("#card_right_bg2>div").length < 1) {
				$("#card_right_bg2").hide();
			}
	}
	
	/*
	* 保存预约登记
	*/	
	function saveVMVisit(){
		var url = "${ctx}/app/visitor/frontReg/saveVMVisit.act";
		var cards = "";
		var names = "";
		var pnos = "";
		$("#card_right_bg2").each(function(i,n){
		    var objs = $(n).find(".card_div_more1");
		    $(objs).each(function(j,m){
		    	var inputs = $(m).find("input");
		    	if ($.trim(inputs[0].value)!=""){
			    	names += inputs[0].value + ",";
			    	pnos += inputs[1].value + ",";
			    	cards += inputs[2].value + ",";
		    	}
		    }); 
		});
		//alert(names+"||"+pnos+"||"+cards);
		var vsId = $("#cur_vs_id").val();
		var cardType = $("#vm_card_type").val();
		var cardNo = $("#vm_card_no").val();
		var data = "names="+names+"&pnos="+pnos+"&cards="+cards+"&vsId="+vsId+"&cardType="+cardType+"&cardNo="+cardNo+"&photoName="+photoName;
		var sucFun = function(data) {
			alert("保存成功!");
			opeReset("init");
		};
		var errFun = function() {
			alert("提示信息','加载数据出错!");
		};
		var res_name=true;
		var res_shenfen=true;
		var res_linshi=true;
		//校验副卡姓名
		jQuery("input[name='fuka_name']").each(function(){
	         var value = jQuery(this).val();
	         if(value=='姓名' || value==''){
	        	 res_name=false;
	        	 alert('姓名必填!');
				return false;
		     }
	         
		});
		//校验身份证号
		jQuery("input[name='fuka_shenfen']").each(function(){
	         var value = jQuery(this).val();
	         if(value=='身份证号' || value==''){
	        	 res_shenfen=false;
				alert('身份证号必填!');
				return false;
		     }else{
		    	 if(!idCardNoUtil.checkIdCardNo(value)){
		    		 res_shenfen=idCardNoUtil.checkIdCardNo(value);
		    		 alert('身份证号不正确!');
		    		 return false;
				  };
			 }
	         
		});
		//校验临时卡号
		jQuery("input[name='fuka_linshi']").each(function(){
	         var value = jQuery(this).val();
	         if(value=='临时卡号' || value==''){
	        	 res_linshi=false;
	        	  alert('临时卡号必填!');
				return false;
		     }
	         
		});
		if($("#bangding_form").valid() && res_name && res_shenfen && res_linshi){
			ajaxQueryJSON(url,data,sucFun,errFun);
		}
		//alert($("#bangding_form").valid());
		//ajaxQueryJSON(url,data,sucFun,errFun);
	}
	
	$("#vm_ope_content")
				.ready(function() {
					//绑定刷卡
					$("input[broomcard='broomcard']").click(function() {  
					    var str;
						str = WebRiaReader.RiaReadCardEngravedNumber();
						$(this).val(str);
					});
					initCardCheck(); 
				});
	/**
	* 初始化check 按钮状态
	*/
	function initCardCheck(){
		var cardNo = $("#cur_broom_card_no").val();
		$("input [value='"+cardNo+"']").attr("checked","checked");
	}
	/**
	* 退卡
	*/ 
	function giveBackCard(){
		var inputs = $("#ready_card_table input");
		var backCards = "";
		$(inputs).each(function(i,n){
			if ($(n)[0].checked == true && $(n)[0].disabled == false ) {
				var card = $(n).val();
		    	backCards += card+",";
			} 
		});
		var vsId = $("#cur_vs_id").val();
		var url = "${ctx}/app/visitor/frontReg/giveBackCards.act";
		var data = "vsId="+vsId+"&backCards="+backCards;
		var sucFun = function(data) {
			alert('退卡成功!');
			readInfoval(vsId)
		};
		var errFun = function() {
			alert('退卡失败!');
		};
		
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	
	/**
	* 读主卡
	*/
	function readCard(){
		var str;
	 	str = WebRiaReader.RiaReadCardEngravedNumber;
	 	if (str == undefined || null == str || $.trim(str) == "FoundCard error!"){
	 		alert("读取市民卡异常!请检查市民驱动是否正确安装.");
	 		return;
	 	}
		$("#vm_card_no").val(str);
	}
	/**
	* 读附卡
	*/
	function readSubCard(obj){
		var str;
	 	str = WebRiaReader.RiaReadCardEngravedNumber;
	 	if (str == undefined || null == str || $.trim(str) == "FoundCard error!"){
	 		alert("读取市民卡异常!请检查市民驱动是否正确安装.");
	 		//easyAlert('提示信息','读取市民卡异常!请检查市民驱动是否正确安装.','info');
	 		return;
	 	}
		$($(obj).parent().find("input")[2]).val(str);
	}
	
	/**
	 * 打电话
	 */
	function dialing() {
		var tel  = $("#phonespan").text();
		tel = $.trim(tel);
		if(tel!=null && ""!= tel){
			  	$.ajax({
					type: "POST",
					url:  "${ctx}/app/visitor/frontReg/ipCall.act",
					data: {tel: tel},
					dataType: 'text',
					async : false,
					success: function(msg){
						if(msg != "success")  alert("拨号失败");
					 },
					 error: function(msg, status, e){
						 alert("拨号出错");
					 }
			  
				});
		} else {
			alert("号码为空!");
		}
	}
</script>
<form id="bangding_form">
	<div id="vm_ope_content"
		style="position: relative; left: 0; top: 0; border: 1px solid #90b3ed; width: 100%; margin: 2px 2px 0 4px; background: #b1d5fc">
		<div class="show_hide1" id="show_hide" title="隐藏"
			onclick="show_hide()"></div>
		<div class="show_hide21" id="show_hide2" title="显示"
			onclick="show_hide2()"></div>
		<div class="card_right1">
			<div class="card_right_main11" onclick="javascript:broomCard();"
				title="刷卡查询"></div>
		</div>
		<div class="card_right_bg1">
			<div class="card_right_ok1" id="ope_btn_reset"
				onclick="javascript:opeReset();"></div>
			<div class="card_right_main12" style="width: 570px">
				<c:if test="${vm.PIC == null || vm.PIC == ''}">
					<img class="card_right_img1" onclick="javascript:photograph();"
						id="photoshops"
						src="${ctx}/app/portal/visitcenter/images/card_right_img2.png" />
				</c:if>
				<c:if test="${vm.PIC != null && vm.PIC != ''}">
					<img class="card_right_img1" onclick="javascript:photograph();"
						id="photoshops" src="${vm.PIC}" />
				</c:if>
				<table class="card_right_table1">
					<tr>
						<td>
							<span>姓名</span>
						</td>
						<td class="td_card21">
							<input id="cur_vs_id" type="hidden" value="${vm.VS_ID}" />
							<input type="text"
								style="width: 76px; height: 26px; line-height: 26px" readonly
								value="${vm.VI_NAME}" />
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>
							<span>身份证号</span>
						</td>
						<td colspan="3">
							<input type="text" style="width: 100%; height: 26px" readonly
								value="${vm.RESIDENT_NO}" />
						</td>
					</tr>
					<tr>
						<td>
							<span>主卡类型</span>
						</td>
						<td>
							<select name="cardType" id="vm_card_type"
								style="width: 76px; height: 26px; border: 1px solid #6da4cd;">
								<option value="1"
									<c:if test="${vm.CARD_TYPE != '1'}">selected</c:if>>
									市民卡
								</option>
								<option value="2"
									<c:if test="${vm.CARD_TYPE == '2'}">selected</c:if>>
									临时卡
								</option>
							</select>
						</td>
						<td style="padding-left: 10px;">
							<span>卡号</span>
						</td>
						<td>
							<input type="text" name="vm_card_no" id="vm_card_no"
								style="float: left; width: 130px; height: 26px"
								value="${vm.CARD_ID}" />
							<img src="${ctx}/images/zhucard.png" style="margin-top: 10px;"
								onclick="readCard()">
						</td>
					</tr>
					<tr>
						<td>
							<span>人数</span>
						</td>
						<td>
							<input type="text" name="comeNum"
								style="width: 76px; height: 26px" id="comeNum"
								onblur="javascript:visitorNumChange();"
								<c:if test="${vm.VS_FLAG != '02'}">readonly</c:if>
								value="${vm.VS_NUM}" />
						</td>
						<td style="padding-left: 10px;">
							<span>单位</span>
						</td>
						<td>
							<input type="text" style="width: 105px; height: 26px" readonly
								value="${vm.VI_ORIGIN}" />
						</td>
					</tr>
					<tr>
						<td>
							<span>车牌</span>
						</td>
						<td>
							<input type="text" style="width: 76px; height: 26px" readonly
								value="${vm.PLATE_NUM}" />
						</td>
						<td style="padding-left: 10px;">
							<span>手机</span>
						</td>
						<td>
							<input type="text" style="width: 155px; height: 26px" readonly
								value="${vm.VI_MOBILE}" />
						</td>
					</tr>
				</table>
			</div>
			<div class="card_right_table21">
				<table class="card_right_table2_table1">
					<tr>
						<td>
							<span>来访日期</span>
						</td>
						<td>
							<input type="text" value='${fn:substring(vm.VS_ST,0,10)}' readonly style="width: 85px; height: 26px; line-height: 26px" />
						</td>
						<td>
							<span>时间</span>
						</td>
						<td  style="text-align:left;">
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" value='${fn:substring(vm.VS_ST,11,16)}' 
								readonly style="width: 80px; height: 26px;"/>
								<span>至</span><input type="text" value='${fn:substring(vm.VS_ET,11,16)}'
								readonly style="width: 97px; height: 26px; line-height: 26px" />
						</td>
					</tr>
					<tr>
						<td>
							<span>受访人单位</span>
						</td>
						<td>
							<input type="text" style="width: 85px; height: 26px" readonly
								value="${vm.ORG_NAME}" />
						</td>
						<td style="padding-left: 10px;">
							<span>姓名</span>
						</td>
						<td style="text-align:left;">
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="width: 97px; height: 26px" readonly
								value="${vm.USER_NAME}" />
							<span style="padding-left: 10px;">Tel:&nbsp</span><span id ="phonespan">${userExp.telNum}</span><img onclick="dialing()" style="cursor: pointer;" src="/smartcity/images/tel.png">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="card_right_bg2" class="card_right_bg21">
			<c:if
				test="${null != vm && null != vvaList && vm.VS_FLAG == '02' && fn:length(vvaList) > 0}">
				<c:forEach items="${vvaList}" var="vv">
					<div class="card_right_table2_table11">
						<span class="card_span1">副卡1</span>
						<div class="card_div1">
							<div class="card_div_more1">
								<input type="text" value="${vv.vaBak1}"
									style="width: 66px; height: 26px; line-height: 26px">
								<input type="text" value="${vv.residentNo}"
									style="width: 130px; height: 26px; line-height: 26px">
								<input type="text" value="${vv.cardId}"
									style="width: 130px; height: 26px; line-height: 26px">
								<img src="${ctx}/images/zhucard.png" style="margin-top: 16px;"
									onclick="readSubCard(this);">
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>

			<c:if
				test="${null != vm && null != vvaList && vm.VS_FLAG != '02' && fn:length(vvaList) > 0}">
				<table class="main_table" id="ready_card_table" border="1"
					bordercolor="#e4e4e4"
					style="border-collapse: collapse; display: block; width: 500px; margin-left: 10%; card_right_bg2; margin: 0 auto 20px auto;">
					<tr class="table_header">
						<!-- <td>序号<br /><br /><br /></td>  -->
						<td>
							类型
						</td>
						<td>
							姓名
						</td>
						<td>
							身份证号
						</td>
						<td>
							卡号
						</td>
						<td>
							归还状态
						</td>
						<td>
							选中
						</td>
					</tr>
					<c:if test="${vm.CARD_TYPE == '2'}">
						<tr class="table_header">
							<!-- <td>序号<br /><br /><br /></td>  -->
							<td>
								主卡
							</td>
							<td>
								${vm.VI_NAME}
							</td>
							<td>
								${vm.RESIDENT_NO}
							</td>
							<td>
								${vm.CARD_ID}
							</td>
							<td>
								<c:if test="${vm.VS_RETURN=='1'}">归还</c:if>
								<c:if test="${vm.VS_RETURN=='2'}">未归还</c:if>
							</td>
							<td>
								<input type="checkbox"
									<c:if test="${vm.VS_RETURN=='1'}">checked="checked" disabled="disabled" </c:if>
									value="${vm.CARD_ID}" />
							</td>
						</tr>
					</c:if>
					<c:forEach items="${vvaList}" var="vv">
						<tr class="table_header">
							<!-- <td>序号<br /><br /><br /></td>  -->
							<td>
								附卡
							</td>
							<td>
								${vv.vaBak1}
							</td>
							<td>
								${vv.residentNo}
							</td>
							<td>
								${vv.cardId}
							</td>
							<td>
								<c:if test="${vv.vaReturn=='1'}">归还</c:if>
								<c:if test="${vv.vaReturn=='2'}">未归还</c:if>
							</td>
							<td>
								<input type="checkbox"
									<c:if test="${vv.vaReturn == '1'}">checked="checked" disabled="disabled" </c:if>
									value="${vv.cardId}" />
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
		<div class="card_right_bg31">
			<div class="card_right_bg3_main1"
				style="width: 400px; margin: 0 auto;">
				<c:if
					test="${(vm.VS_FLAG == '04' || vm.VS_FLAG == '05' || vm.VS_FLAG == '06') && (vm.VS_RETURN == '2' ||  vm.VS_RET_SUB=='2')}">
					<div class="card_right_bg3_btn1" diabled
						style="display: block; float: left"
						onclick="javascript:giveBackCard();"></div>
				</c:if>
				<div class="card_right_bg3_btn31"></div>
				<div class="card_right_bg3_btn41"></div>
				<c:if test="${vm.VS_FLAG == '02'}">
					<div class="card_right_bg3_btn51"
						style="display: block; float: left"
						onclick="javascript:saveVMVisit();"></div>
				</c:if>
			</div>
		</div>
	</div>
</form>