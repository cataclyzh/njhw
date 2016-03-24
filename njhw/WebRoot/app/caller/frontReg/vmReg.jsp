
<%@page import="java.text.SimpleDateFormat"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ page import="java.util.*" %>
<%SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); %>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<script type="text/javascript">var _ctx = '${ctx}';</script>
<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
<script src="${ctx}/app/caller/frontReg/visitorjs.js"
			type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
 <style type="text/css"> 
		body{
			background:url("${ctx}/app/integrateservice/images/backgrund.jpg") left top repeat-x !important;
		}
		.fkdj_left1{
			width:46%;
			float:left;
		}
		
		.fkdj_lefts1{
			width:97%;
			float:left;
			padding:0 10px;
			background:#f6f5f1;
			margin-bottom:10px;
		}
		
		.fkdj_botton1{
			height:32px;
			background:#ecebe4;
			padding:6px;
			width:99%;
			margin: 0 0 10px 0;
		}
		
		.fkdj_bottons1{
			height:32px;
			background:#ecebe4;
			border-top: solid 4px #7f90b3;
			padding:6px;
			width:98%;
			margin: 0 0 10px 0;
		}
		
		.fkdj_right1{
			width:52%;
			float:right;
		}
		
		.fkdj_botton1{
			height:32px;
			background:#ecebe4;
			padding:6px;
			width:98%;
			margin: 0 0 10px 0;
		}
		
	</style>
<script type="text/javascript">
function setNull(){
		$("#certificateName").val('');
}
$(document).ready(function(){
    var str ;
    if(pageTimer!=null){
        clearInterval(pageTimer); 
    }
	//str = WebRiaReader.RiaReadCardEngravedNumber;
	try{
	if (!WebRiaReader.LinkReader()){
	    alert("读取市民卡异常!请检查市民卡驱动是否正确安装");
	    return;
	}else{
        pageTimer=setInterval(function(){ readCard(); }, 2000);
	}}catch(e){
	}
});
//定时读卡
function readCard(){
	var str ;
	str = WebRiaReader.RiaReadCardEngravedNumber;
	if ($.trim(str) == "FoundCard error!"){
	    //Do nothing
	}else{
	   judgeCard(str);//判断还卡还是登记
	   //validCityCard();
	}
}

function addprefix(id) {
			var val = $("#"+id).val().replace(/(^\s*)|(\s*$)/g, "");
			if (val == "") {
				if (id == "cityCard") $("#"+id).val("0000");
				else $("#"+id).val("苏A");
			}
		}
   	
   	
		 /*页面 实现预约登记*/
	function showResponse(responseText,statusText) {
	   if(responseText.status=="true"){
		 alert("保存成功！");
		 opeReset("init");
		 }else {
		  alert("保存失败！");
		 }
    }
	function showRequest(){
	 	
	}

	/**
		* 导航方法
		*/
		function linkFunction(){
			var linkUrl = "";
			var title = "";
			var id = "";

				title = "访客卡号分配";
				linkUrl = "${ctx}/app/visitor/frontReg/fatherCardIdDis.act";
			
			windowDialog(title,linkUrl,"500","450",false);
		}
		
		
		function windowDialog(title,url,w,h,refresh){
			 	var body =  window.document.body;
				var left =  body.clientWidth/2 - w/2;
				var top =  body.clientHeight/2+h/4;
				var scrollTop = document.body.scrollTop;
				//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
				top = top + scrollTop;
				// $("#companyWin").show();
			
		        openEasyWin("fatherCardIdDis",title,url,w,h,false);
				

			}
	
  $("#vm_ope_content").ready(function() {

    if($("#cur_vs_id").val()!=""&&$("#cur_vs_id").val()!=null){
      if(${null != vm && (vm.VS_FLAG=='02' || vm.VS_FLAG=='01' ) && vm.VS_TYPE!='3'}){
    	changeFatherType();
      }else{
    	//$("#vm_ope_content input").attr("disabled","disabled");
    	//$("#photoshops").attr("disabled","disabled");
    	//$("#vm_ope_content select").attr("disabled","disabled");
    	//$("#edvsEtadd").attr("disabled","disabled");
    	//$("#forDisable img").attr("disabled","disabled");
    	//$("#forDisable a").attr("disabled","disabled");
    	
    	var type=$("#typecard").val().toString();
	    if(type==1){
	       $("#certificate").css("display","none");
	       $("#resident").css("display","none");
	       $("#city").css("display","");
	       $("#certificateName").val("");
	    }else if(type==2){
	       $("#certificate").css("display","none");
	       $("#resident").css("display","");
	       $("#city").css("display","none");
	       $("#certificateName").val("");
	    }else{
	       $("#certificate").css("display","");
	       $("#resident").css("display","none");
	       $("#city").css("display","none");        
	    }
    	}
	}else{
	    changeFatherType();
	}
		
    
    jQuery.validator.methods.lengthValue = function(value, element) {
  		 if(value.length>20){
             return false;
         }else{
	         return true;
	     }
    };
		        //name长度
		            jQuery.validator.methods.namelengthValue = function(value, element) {
				
	      		 if(value.length>8){
	                 return false;
	                 }else{
	                     return true;
	               }
		        };
	    	

			 jQuery.validator.methods.validateLinShi = function(value, element,param) {
			 var type = jQuery(param).attr("checked");
        
	           if(type){
	        	   if(value==""){
	        	   	  return false;
	        	   }else{return true;}
	           }else{
	               return true;
	           }
   
	        };
			// 身份证号码验证 
			jQuery.validator.methods.isIdCardNo= function(value, element) { 
			 var type=$("#typecard").val().toString();
	         if(type==1){
	         var cityCardValue=$("#cityCard").val().toString();
	         if(cityCardValue==""){
	             return false;
	         }else{
	             return true;
	         }
	         }else if(type==2){
	         var cityCardValue=$("#residentNo").val().toString();
	         if(cityCardValue=="" ||!(idCardNoUtil.checkIdCardNo(cityCardValue))){
	             return false;
	         }else{
	             return true;
	         }
	         }
	         else if(type==3){
	         var cityCardValue=$("#certificateNo").val().toString();
	         if(cityCardValue==""){
	             return false;
	         }else{
	             return true;
	         }
	         }
	         return true;
	         }; 
			
			// 手机号码验证 
			jQuery.validator.addMethod("isMobile", function(value, element) { 
			  var length = value.length; 
			  var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
			  return this.optional(element) || (length == 11 && mobile.test(value)); 
			}, "请正确填写您的手机号码");	
			
			jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime <= value;
	        };
	        
	        	jQuery.validator.methods.checkVal = function(value, element, param) {
	            var typeCard = jQuery(param).val();
	            if(typeCard=="3"&&value==""){
	                return false;
	            }else{
	                return true;
	            }
	        };
	     
	        
				jQuery.validator.addMethod("checkNumber", function(value, element) { 
			var length = value.length; 
			var check = /^[0-9]*[1-9][0-9]*$/; 
			return this.optional(element) || (check.test(value)); 
		}, "请正确填写正确的证件号码");
		
			jQuery.validator.addMethod("checkOther", function(value, element) { 
			var length = value.length; 
			var check = /^[A-Za-z0-9]+$/; 
			return this.optional(element) || (check.test(value)); 
		}, "请正确填写正确的证件号码");
		
		
		
			//聚焦第一个输入框
			$("#userName").focus();
			//为inputForm注册validate函数
			$("#saveForm").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div", // 使用"div"标签标记错误， 默认:"label"
				rules: {
				    
				    residentNo: {
				        isIdCardNo:true
				     
				    },
				    cityCard: {
				        isIdCardNo:true,
				        maxlength:16,
				        checkNumber:true,
				        minlength:16,
				    },
				    certificateNo: {
				        isIdCardNo:true,
				        maxlength:20,
				        checkOther:true
				       
				    },
					userName: {
						required: true,	
						maxlength:8
					},
					outName: {
						required: true,
						maxlength:9
						},
					
					//fatherCardId: {
					//	required: true
					//	},
					pawn: {
						required: false,
						maxlength:8
						},
					locatorCard: {
						required: false
						//maxlength:16,
				        //checkNumber:true,
				        //minlength:16,
						},
					vmDepart:{
					required: false,
					maxlength:40
					},
					
					num:{
						required: true
						//digits:true,
						//min:1
					},
					phone:{
						required: true,
						isMobile:true
					},
					beginTime:{
						required:true
					},
					endTime:{
						required:true,
						compareDate: "#beginTime"
					},

					fatherCardId:{
	               	    required: true,
	               	    maxlength:16,
	               	     checkNumber:true,
				        minlength:0,
	               	},
	               	certificateName:{
	                  checkVal:"#typecard",
	                  maxlength:10
	               	
	               	}
					
				},
				messages:{ 
				
					userName:{
					 	required:"姓名不能为空",
					 	
					 	maxlength:"姓名字段长度过长"
					 },
					 outName:{
					 	required:"受访人不能为空",
					 	maxlength:"受访人字段长度过长"
					 },
					certificateNo: {
						isIdCardNo: "证件号不能为空",
						maxlength:"证件号过长",
				        checkNumber:"请填写正确的号码"
						},
					//fatherCardId: {
					//	required: "不能为空"
					//	},
					pawn: {
						required: "抵押物不能为空",
						maxlength:"抵押物字段长度过长"
						},
					locatorCard: {
						required: "定位卡号不能为空"
						//maxlength:"卡号过长",
				        //checkNumber:"请填写正确的号码",
				        //minlength:"请填写正确的号码"
						},
				    cityCard:{
							isIdCardNo: "市民卡号不能为空",
							maxlength:"市民卡号过长",
				        checkNumber:"请填写正确的号码",
				        minlength:"请填写正确的号码"
				 			},
				    residentNo:{
				 			isIdCardNo: "身份证号为空或者不合法"
				 	
						},
	               	num:{
	               	 required: "来访人数不能为空"
	               	},
	               	vmDepart:{
	               	  required:"单位名称不能为空", 
					  maxlength:"单位名称字段过长"
						},
					phone:{
						required:"手机号码不能为空",
						isMobile:"请填写正确的手机号码"
						},
					beginTime:{
					    required:"请填写开始时间"
						},
					endTime:{
							required:"请填写结束时间",
							compareDate: "开始时间必须早于结束时间"
						},
	                fatherCardId:{
	                	required: "访客卡号不能为空",
	                	maxlength:"访客卡号过长",
				        minlength:"请填写正确的号码",
				        checkNumber:"请填写正确的号码"
	                },
	                	certificateName:{
	               	    checkVal:"请输入证件类型",
	               	    maxlength:"输入的证件类型字符过长"
	               	
	               	}
				}
			});
		    
			//解决回车问题
			 $("input").keydown(function(e) {
			 	 if ($(this).attr("readonly")=='readonly') {//readonly
		         	e.preventDefault();
		         }
		     });
		});
		
		//机构树
	function selectRespondents() {
		window.parent.selectRespondents1();
	}
		
	
	function saveUnBeforeVM1vm(typeId) {
		if(!setEndTime("beginTime")){
		    	return;
		    }
		tableValuevm();
		$("#photoName").val(photoName);
		$("#typeId").val(typeId);
	
		var options = {
			type:'post',
			url:"${ctx}/app/visitor/frontReg/saveUnBeforeVM.act",
			dataType:'json',
			success: function(json){
	             if (json.status == "true") {
	                 alert("保存成功！");
		             opeReset("init");
	             }else{
	                alert(json.message);
	             }
	             }
		}; 
		//校验主卡
		    //var tte =true;
            //var today = $("#today").val();
            //var value = $("#beginTime").val();
        	//var rel = compareDate(today,value);
        	//  if(rel>0){
            // 	 alert("时间不能小于今天");
            // 	 return;
            //	 $("#beginTime").val(today);
            // 	tte = false;
            //      }
		 //if(!getTime()){
		 //    alert("结束日期必须大于开始日期!");
		 //    return;
		//	 tte = false;
		// }else{
		///	 tte = true;
		// }
	    //// $("#beginTime").val(sthour);
	    // $("#endTime").val(edhour);	
	    // if($("#carId").val()=="苏A"){
	    //	 $("#carId").val("");
		// }
		//校验副卡
		var res_name=true;
		var res_locator=true;
		var res_pawn=true;
		var res_name_length=true;
		var res_pawn_length=true;
		var res_locator_check=true;
		var res_tmpCardId=true;
		var res_tmpCardId_check=true;
		var res_shenfen=true;
		var res_nullshen=true;
		
		
		
	    var res_check = /^[0-9]*[1-9][0-9]*$/; 
		var i=0;
		var j=0;
		var k=0;
		var l=0;
		var m=0;
		
		jQuery("input[name='fuka_shenfen']").each(function(){
		m=m+1;
	         var value = $.trim(jQuery(this).val());
	         if(value=""){
	            res_nullshen=false;
	            return false;
	         }else if((idCardNoUtil.checkIdCardNo($.trim(jQuery(this).val())))==false){
	            res_shenfen=false;
	            return false;
	         }       
		});
		
		
		
		
		
		jQuery("input[name='fuka_name']").each(function(){
		i=i+1;
	         var value = $.trim(jQuery(this).val());
	         if(value==''){
	        	 res_name=false;
				return false;
		     }else if(value.length>8){
		         res_name_length=false;
		         return false;
		     }
	         
		});
			jQuery("input[name='fuka_linshi']").each(function(){
				l=l+1;
		
	         var value = $.trim(jQuery(this).val());
	         if(value==''){
	        	 res_tmpCardId=false;
				 return false;
		     }else if(value.length!=16||!res_check.test(value)){
		         res_tmpCardId_check=false;
		         return false;
		    
		     }
		     
	         
		});
		jQuery("input[name='tmpLocator']").each(function(){
		j=j+1;
	         var value = $.trim(jQuery(this).val());
	         if(value==''){
	        	 res_locator=false;
				 return false;
		     }else if(value.length!=16||!res_check.test(value)){
		         res_locator_check=false;
		         return false;
		     
		     }
	         
		});
		jQuery("input[name='tmpPawn']").each(function(){
				k=k+1;
		
	         var value = $.trim(jQuery(this).val());
	         if(value==''){
	        	 res_pawn=false;
				 return false;
		     }else if(value.length>8){
		         res_pawn_length=false;
		         return false;
		    
		     }
		     
	         
		});


        
		if(!res_name || !res_locator || !res_pawn||!res_tmpCardId||!res_nullshen){
	        alert("请填写正确的副卡：姓名、身份证号、卡号、定位卡号、抵押物！");
	        return;
		}
		if(!res_name_length){
		    alert("第"+i+"行姓名长度过长");
		    return;    
		}
		   if(!res_shenfen){
		    alert("第"+i+"行身份证号码不正确");
		    return;   
		}
			if(!res_tmpCardId_check){
		    alert("第"+i+"行卡号不正确");
		    return;
		}
		
			if(!res_locator_check){
		    alert("第"+i+"行定位卡号不正确");
		    return;
		}
		
		if(!res_pawn_length){
		    alert("第"+i+"行抵押物名称长度过长");
		    return;   
		}
	 
	
		 //if($("#saveForm").valid() && res_name && res_locator && res_pawn && tte==true ){
		 if($("#saveForm").valid() && res_name && res_locator && res_pawn ){
			$('#saveForm').ajaxSubmit(options);
		 }
	
	}
	
	function setEndTime(sartId){
			var startTime= $("#"+sartId).val();
			var  str=startTime.toString();
			str =  str.replace(/-/g,"/");
			var endDate = new Date(str);
			// var d=endDate.pattern("yyyy-mm-dd hh:mm:ss");
			var year=endDate.getFullYear();
			var month=endDate.getMonth()+1;
			var day=endDate.getDate();
			 $("#endTime").val(year+"-"+month+"-"+day+" 18:00:00");
			 //alert($("#endTime").val());
			 if(endDate.getHours()>=18)
			 {
			 	alert("预约时间不能超过18点");
			 	return false;
			 }
			 return true;
		}
	//++++=事件
	function txtRelativeChangevm1(str){
	   var newid=0;
       var trid = $("#card_right_bg2 tr").last().attr("id");
       if (trid == null || trid == 0) { 
			newid = 1;
		} else {
			var ind = trid.indexOf("_");
			var id = parseInt(trid.substr(ind + 3, trid.length-1));
			newid = id + 1;
		}

		var trContent="<tr id='num_tr"+newid+"' >"
				+"<td class='fkdj_check'><input type='hidden' name='auxiId'  value='-1'/><input name='checkbox' id='checkbox_"+newid+"' type='checkbox' /></td>"
				+"<td width='8%'><select id='type"+newid+"' name='' onChange='changeTmpCardType("+newid+")' >"
                +"<option value='1'>市民卡</option>"
                +"<option value='2' selected>临时卡</option>"
                +"</select></td>"
                +"<td width='8%'><input type='text' id='fuka_name"+newid+"' name='fuka_name' size='4' value=''/></td>"
                +"<td width='15%'><input type='text' id='fuka_shenfen"+newid+"' name='fuka_shenfen' size='16' value='' /></td>"
				+"<td width='15%'><input type='text' id='tmpCardId"+newid+"' name='fuka_linshi' size='16' value='"+str+"' onblur='isGivenBack(type"+newid+",this)'/></td>"
				+'<td width="15%"><input type="text" name="tmpLocator" size="16" value="" /></td>'
				+'<td width="8%"><input type="text" name="tmpPawn" size="4" value="" /></td>'
				+'<td width="6%"><label size="4">登记</label></td>'
				+"<td class='fkdj_check'><a href='#'><img src='${ctx}/app/integrateservice/images/fkdj_dele.jpg' width='15' height='15' border='0' onclick='delfield("+newid+")'/></a></td>"
			    +"</tr>";
	      	$("#card_right_bg2").append(trContent);
	      	$("#num").val(newid);
	      	
	    	//$("#num").val(newid);
		} 
	//-------事件
	function delfield(num) {
        $("#num_tr"+num).remove();
        $("#num").val($("#card_right_bg2 tr").length);
	}
	function delfieldAll() {
	    if($("input[name='checkbox']:checkbox:checked").length<=0){
	        alert("请选择要删除的随行人员！");
	        return;
	    }
	    $("input[name='checkbox']:checkbox:checked").each(function(){ 
	        $(this).parent().parent().remove();
	    })
	    $("#num").val($("#card_right_bg2 tr").length);
	}
	
	
     //根据号码判断基本信息是否存在
	 function selectVisitvm(){
         var residentId = "";   	
		 var type=$("#typecard").val().toString();
	     if(type==1){
	        residentId = $("#fatherCardId").val();
	     }
	     if(type==2){
	         residentId =  $("#residentNo").val();
	     }
	     if(residentId!=""){
	       $.ajax({
	             type:"POST",
	             url:"${ctx}/app/visitor/frontReg/loadVisiInfo.act",
	             data:{residentId:residentId,type:type},
	             dataType: 'json',
			     async : false,
			     success: function(json){
	             if (json.status == 0) {
	             //信息存在
	            	//$("#spUser").hide();
	                $("#userName").val(json.VIName);
					$("#phone").val(json.ViMobile);
					$("#carId").val(json.PlateNum);
					$("#vmDepart").val(json.ViOrigin);
					$("#cityCard").val($("#fatherCardId").val());
					if(json.imgSrc!=null){
					$("#photoshops").attr("src",json.imgSrc);
					$("#dbPhotoName").val(json.dbPhotoName);
					}					
	          }
              if(json.status == 9){
                  //内部人员
                  if(type==1){
                      $("#cityCard").val(json.map.UEP_BAK1);                
			      }
			      if(type==2){
                      $("#residentNo").val(json.map.RESIDENT_NO);                
			      }
			      $("#userName").val(json.map.DISPLAY_NAME);
                  $("#cityCard").val(json.map.UEP_BAK1);                
			      $("#phone").val(json.map.UEP_MOBILE);
			      $("#residentNo").val(json.map.RESIDENT_NO);
			     
			      $("#vmDepart").val(json.map.NAME);
			      $("#photoshops").attr("src",json.imgSrc1);
			      }

              if(json.status == 8){
              //市民卡没查到信息               
                    if(json.residentId==null||json.residentId==""){
                        alert(json.message);
                        $("#fatherCardId").val(json.number);
                        $("#typecard").val("1");
						$("#certificateName").val("");
						$("#userName").val("");
						$("#certificateNo").val("");
						$("#residentNo").val("");
						$("#vmDepart").val("");
						$("#pawn").val("");
						$("#cityCard").val("");
						$("#locatorCard").val("");
						$("#phone").val("");
						$("#outName").val("");
						$("#orgName").val("");
						$("#phonespan").val("");
                    }else{
                 	    $("#userName").val(json.getUserName);
                 	    $("#cityCard").val($("#fatherCardId").val().toString());           
                    }
              }
	          }
	      });
	      }
	     }
		 
		 function opeResetDo2(){
			$("#typecard").val("1");
			$("#certificateName").val("");
			$("#userName").val("");
			$("#certificateNo").val("");
			$("#residentNo").val("");
			$("#vmDepart").val("");
			$("#fatherCardId").val("");
			$("#pawn").val("");
			$("#cityCard").val("");
			$("#locatorCard").val("");
			$("#phone").val("");
			$("#beginTime").val("");
			$("#outName").val("");
			$("#orgName").val("");
			$("#phonespan").val("");
			$("#photoshops").attr("src","${ctx}/app/portal/visitcenter/images/card_right_img2.png");
			$("#fatherCardId").removeAttr("disabled");
			$("#beginTime").removeAttr("disabled");
			$("#outName").removeAttr("disabled");
			$("#orgName").removeAttr("disabled");
			$("#phonespan").removeAttr("disabled");
			$("#imgOutName").removeAttr("disabled");
			$("#imgPhonespan").removeAttr("disabled");
		}
		
		 
		 function tableValuevm(){
		    var cardType ="";
			var locatorCard="";
			var pawn="";
			var cardId="";
			var userName="";
            var auxiId = "";
            var residentNo = "";
			$("#card_right_bg2 tr").each(function() {
				var tr_id = $(this).attr("id");
				if($("#"+tr_id+" td:eq(0) :input").val()!=" "){
					
				 auxiId +=$("#"+tr_id+" td:eq(0) :input").val()+",";
				}else{
				
					auxiId +="";
				}
				cardType +=$("#"+tr_id+" td:eq(1) :selected").val()+",";
				userName +=$("#"+tr_id+" td:eq(2) :input").val()+",";
				residentNo +=$("#"+tr_id+" td:eq(3) :input").val()+",";
				cardId +=$("#"+tr_id+" td:eq(4) :input").val()+",";
				locatorCard +=$("#"+tr_id+" td:eq(5) :input").val()+",";
				pawn +=$("#"+tr_id+" td:eq(6) :input").val()+",";
				
			});
			if($("#card_right_bg2 tr").length > 0){
			    var userName1= userName.substr(0, userName.length - 1);
			    var locatorCard1 = locatorCard.substr(0, locatorCard.length - 1);
			    var residentNo1=residentNo.substr(0, residentNo.length - 1);
			    var cardId1=cardId.substr(0, cardId.length - 1);
			    var cardType1=cardType.substr(0, cardType.length - 1);
			    var auxiId1 = auxiId.substr(0, auxiId.length - 1);
			    var pawn1 = pawn.substr(0, pawn.length - 1);
	            $("#cardTypenum").val(cardType1);
	     	    $("#locatorIdNum").val(locatorCard1);
                $("#cardIdnum").val(cardId1);
                $("#residentNonum").val(residentNo1);
                $("#userNamenum").val(userName1);
                $("#pawnNum").val(pawn1);
                $("#auxiIdNum").val(auxiId1);
            }else{
                $("#cardTypenum").val("");
	     	    $("#locatorIdNum").val("");
                $("#cardIdnum").val("");
                $("#residentNonum").val("");
                $("#userNamenum").val("");
                $("#pawnNum").val("");
                $("#auxiIdNum").val("");
            }
             
		}
		

		 function getTime(){
		      
				var sthour = $("#sthour").val().toString()+$("#stminute").val().toString();
			    	
			    	var edhour = $("#edhour").val().toString()+$("#edminute").val().toString();
			    
			        if(parseInt(sthour)>=parseInt(edhour)){
			        
			        	return false;
			             }else{
			            	 return true;
			                }
			        
			        }
    function getTime1(){
        
    	var sthour = $("#sthour").val().toString()+$("#stminute").val().toString();
    	
    	var edhour = $("#edhour").val().toString()+$("#edminute").val().toString();
    
        if(parseInt(sthour)>=parseInt(edhour)){
        	alert("结束日期必须大于开始日期!")
            
        }
        
    }
        
	    function changeTmpCardType(id){
	        var residentId = "";
	        if($("#type"+id).val().toString()==1){
	            residentId=$("#tmpCardId"+id).val().toString();
	        }else{
	            return;
	        }
		    var type=1;
	        var displayName1 = $("#fuka_name"+id).val();
	       // if(residentId!=""){
	        $.ajax({
	             type:"POST",
	             url:"${ctx}/app/visitor/frontReg/loadVisiInfo.act",
	             data:{residentId:residentId,type:type,displayName1:displayName1},
	             dataType: 'json',
			     async : false,
			     success: function(json){
	             if (json.status == 0||json.status==9) {
	             //信息存在
	                $("#fuka_name"+id).val(json.VIName);			
	             }
                 if(json.status == 8){
                 //市民卡没查到信息               
                    if(json.residentId==null||json.residentId==""){
                        alert(json.message);
                        $("#tmpCardId"+id).val("");
                        $("#type"+id).val(2);
                    }else{
                 	    $("#fuka_name"+id).val(json.getUserName);            
                    }
                 }    
	          }
	      });
	    // }
	    }
	    

	/**
	 * 打电话
	 */
		function dialing() {
			var called = $("#phonespan").val();
			var url = "${ctx}/app/integrateservice/inputCallPhone.act?called="+called;
			//openEasyWinNotResizable("winId","拨号确认",url,"400","170",true);		
			showShelter('400','170',url); 
			/* var tel  = $("#phonespan").val();
			  if(tel!=null){
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
			  } */
	    }

		    function suA(){
		    	if ($("#carId").val() == "") $("#carId").val("苏A");
			}
			   

		    function compareDate(strDate1,strDate2) {
		   
		                    var date1 = new Date(strDate1.replace(/\-/g, "\/"));
		    
		                    var date2 = new Date(strDate2.replace(/\-/g, "\/"));
		   
		                    return date1-date2;
		    
		                }
		                
    function isGivenBack(type,card){
        var cardId=$(card).val();
        if(null!=cardId && ""!=cardId ){
            if(judgeTheSameId()){
                $(card).val("");
                alert("不要重复刷同一张卡");
            }else{
                if('1'!=$(type).val().toString){
                    judgeCardByInput(cardId);
                }
            
            }
        }
    }
    
    function isFatherGivenBack(card){
        var cardId=$(card).val();
        if(null!=cardId && ""!=cardId ){
            if(judgeTheSameId()){
                $(card).val("");
                alert("不要重复刷同一张卡");
                
            }else{
                if('1'!=$("#typecard").val().toString){
                    judgeCardByInput(cardId);
                }
            }
        }
    }
    
    function judgeTheSameId(){
	        var linshiNum ="";
		    jQuery("input[name='fuka_linshi']").each(function(){
	            var value =$.trim(jQuery(this).val());
	            if(value==''){
	                kong=false;
		        }else{
		    	    linshiNum +=value+","
			    }
		    });
		    if(null!=$("#fatherCardId").val().toString() && ""!=$("#fatherCardId").val().toString()){
		        linshiNum +=$("#fatherCardId").val().toString()+",";
		    }
		    
	   var ch = new Array;
	   linshiNum=linshiNum+str;
	   ch = linshiNum.split(",");
	   var nary=ch.sort();

	   for(i=0;i<ch.length;i++){
		 if(nary[i]==nary[i+1]){
              return true;
	     }
       }
       return false;
    }
    
    function judgeCardByInput(str){
	  $.ajax({
             type:"POST",
             url:"${ctx}/app/visitor/frontReg/giveBack.act",
             data:{cardId:str},
             dataType: 'json',
		     async : false,
		     success: function(json){
        
	         if (json!=null) {
	             if(json.result=="2"){//临时卡
	                 if(confirm("此卡尚未归还，确定不做当前操作,退卡吗?")){ 
                        giveBackCard(str);
				     }else{
				     //doNothing
				     }
	             }    
	         }
	         }
	   });       
 }  
     
    
    function judgeCard(str){
	  $.ajax({
             type:"POST",
             url:"${ctx}/app/visitor/frontReg/giveBack.act",
             data:{cardId:str},
             dataType: 'json',
		     async : false,
		     success: function(json){
        
	         if (json!=null) {
	             if(json.result=="1"){
	                 //if($("#cur_vs_id").val()==""||$("#cur_vs_id").val()==null){
	                         var cur_vs_id=$("#cur_vs_id").val();
	                         if($("#cur_vs_id").val()==""||$("#cur_vs_id").val()==null){
	                             register(str);
	                         }else if(${null != vm && (vm.VS_FLAG=='02' || vm.VS_FLAG=='01' ) && vm.VS_TYPE!='3'}){
	                             register(str);
	                         }
	                         
	                 //}
	             }else if(json.result=="2"){//临时卡
	                 if(confirm("此卡尚未归还，确定不做当前操作,退卡吗?")){ 
                        giveBackCard(str);
				     }else{
				     //doNothing
				     }
	             }else{
	                 alert("状态异常");
	             }     
	         }
	         }
	   });       
 }
 
 // 根据市民卡号，验证市民卡的有效性
	function validCityCard(){
		var isOk = true;
		var cards;
		var cityCard = $("#cityCard").val().replace(/(^\s*)|(\s*$)/g, "");
		if (cityCard.length == 16) {
			cards = cityCard; // "0000" +
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/visitor/frontReg/validCityCard.act",
	            data:{
	            	"cityCard":cards
	            } ,
	            dataType: 'json',
	            async : false,
	            success: function(json){
                    if(json.status == 2){
                    	isOK=false;
                    	//easyAlert("提示信息", "市民卡未开通,请办理市民卡激活业务.","info");   
                    	alert("市民卡未开通,请办理市民卡激活业务.");                 	
                    }
	            }
	         });
		}
		return isOk;
	}
 
 		function cardReset()
 		{
 			$("#fatherCardId").val('');
 		}
 		
 	   function register(str){
 	   
 	   var type=$("#typecard").val().toString();
	       $("#fatherCardId").val(str);
	       if(judgeTheSameId()){
	           $("#fatherCardId").val("");
	           alert("不要重复刷同一张卡");
	           return false;
	       }
	       else{
	           if(type==1){
	 	           //$("#fatherCardId").val(str);
	 	           selectVisitvm();
	 	       }else if(type==2){ 
	 	           //$("#fatherCardId").val(str);
	 	           selectVisitvm();
	 	       }else{
	 	           //$("#fatherCardId").val(str);
	 	       }
	 	   }
	 	   if($("#fatherCardId").val().toString()!=str)
	 	   {
	 	   		var kong=true;
	        var linshiNum ="";
		    jQuery("input[name='fuka_linshi']").each(function(){
	            var value =$.trim(jQuery(this).val());
	            if(value==''){
	                kong=false;
		        }else{
		    	    linshiNum +=value+","
			    }
		    });
       var fk = true;
	   var ch = new Array;
	   linshiNum=linshiNum+str;
	   ch = linshiNum.split(",");
	   var nary=ch.sort();

	   for(i=0;i<ch.length;i++){
		 if(nary[i]==nary[i+1]){
              alert("不要重复刷同一张卡");
              fk = false;
              break;
	     }
       }
       if(fk==true && kong==true){
           txtRelativeChangevm1(str);
       }else if(fk==true){
           jQuery("input[name='fuka_linshi']").each(function(){
	            var value =$.trim(jQuery(this).val());
	            if(value==''){
	               jQuery(this).val(str);
	               return false;
		        }
		    });
       }
	 	   }
 	   
 	   /*
	   if(""==$("#fatherCardId").val().toString() || null==$("#fatherCardId").val().toString()){
	       var type=$("#typecard").val().toString();
	       $("#fatherCardId").val(str);
	       if(judgeTheSameId()){
	           $("#fatherCardId").val("");
	           alert("不要重复刷同一张卡");
	           return false;
	       }
	       else{
	           if(type==1){
	 	           //$("#fatherCardId").val(str);
	 	           selectVisitvm();
	 	       }else if(type==2){ 
	 	           //$("#fatherCardId").val(str);
	 	           selectVisitvm();
	 	       }else{
	 	           //$("#fatherCardId").val(str);
	 	       }
	 	   }
	   }else if($("#fatherCardId").val().toString()==str){
	   var type=$("#typecard").val().toString();
	       $("#fatherCardId").val(str);
	       if(judgeTheSameId()){
	           $("#fatherCardId").val("");
	           alert("不要重复刷同一张卡");
	           return false;
	       }
	       else{
	           if(type==1){
	 	           //$("#fatherCardId").val(str);
	 	           selectVisitvm();
	 	       }else if(type==2){ 
	 	           //$("#fatherCardId").val(str);
	 	           selectVisitvm();
	 	       }else{
	 	           //$("#fatherCardId").val(str);
	 	       }
	 	   }
	   }else{
	   var kong=true;
	        var linshiNum ="";
		    jQuery("input[name='fuka_linshi']").each(function(){
	            var value =$.trim(jQuery(this).val());
	            if(value==''){
	                kong=false;
		        }else{
		    	    linshiNum +=value+","
			    }
		    });
       var fk = true;
	   var ch = new Array;
	   linshiNum=linshiNum+str;
	   ch = linshiNum.split(",");
	   var nary=ch.sort();

	   for(i=0;i<ch.length;i++){
		 if(nary[i]==nary[i+1]){
              alert("不要重复刷同一张卡");
              fk = false;
              break;
	     }
       }
       if(fk==true && kong==true){
           txtRelativeChangevm1(str);
       }else if(fk==true){
           jQuery("input[name='fuka_linshi']").each(function(){
	            var value =$.trim(jQuery(this).val());
	            if(value==''){
	               jQuery(this).val(str);
	               return false;
		        }
		    });
       }
	   }*/
	    
	}

function giveBackCard(str){
 	var vsid = $("#cur_vs_id").val();
 	if (str!=undefined && "" != str && $.trim(str) != "FoundCard error!"){
	  $.ajax({
             type:"POST",
             url:"${ctx}/app/visitor/frontReg/giveBackCards1.act",
             data:{vsid:vsid,cardId:str},
             dataType: 'json',
		     async : false,
		     success: function(json){            
	             if (json!=null) {    
	             	 if(json.vsReturn==null&&json.vsFlag==null){
		             	 if(json.guihuan==null){                	 
		           	         $.each(json,function(i,item){
		           	         var id=i+1;
		           		         if (item.vaReturn=="1"){
		           		             $("#back_"+id).replaceWith("归还");
		              	         }	
			  	             });
		                 }else{
                             alert("此卡已退");
			             } 	
	          	     }
	          }//json !=null
          }
      });
 	}
	}
	
	function checkAll(){    
	if($("#checkbox_all").is(':checked')){
	    $("input[type='checkbox']").attr("checked","true"); 
	}else{
	    $("input[type='checkbox']").removeAttr("checked"); 
	}
	}
	
	function changeFatherType(){
	    var type=$("#typecard").val().toString();
	    if(type==1){
	       $("#certificate").css("display","none");
	       $("#resident").css("display","none");
	       $("#city").css("display","");
	       //$("#cityCard").attr("readonly","readonly");
	       //$("#userName").attr("readonly","readonly");
	       $("#certificateName").val("");
	       $("#certificateName").css("display","none");
	       $("#typecardId").css("display","none");
	       
	    }else if(type==2){
	       $("#certificate").css("display","none");
	       $("#resident").css("display","");
	       $("#city").css("display","none");
	       $("#certificateName").val("");
	       $("#certificateName").css("display","none");
	       $("#typecardId").css("display","none");
	       //$("#userName").removeAttr("readonly");
	    }else{
	       $("#certificate").css("display","");
	       $("#resident").css("display","none");
	       $("#city").css("display","none");
	       $("#certificateName").css("display","");
	       $("#typecardId").css("display","");
	       $("#certificateName").val("请输入证件类型!").css("color","#808080");
	       //$("#userName").removeAttr("readonly");	        
	    }
	    //$("#fatherCardId").attr("readonly","readonly");
	}
</script>
<form id="saveForm" action="saveUnBeforeVM.act" method="post">
<div id="vm_ope_content" >
    	<div class="fkdj_left1" >
  <div class="fkdj_botton1">
  	<a class="fkdj_botton_left" href="javascript:void(0);" onclick="javascript:opeReset();">新建申请</a>
  	<a class="fkdj_botton_right" href="javascript:void(0);" onclick="opeResetDo2()">重新填写</a>
  </div>
  <div id="forDisable">
  	<div class="fkdj_lefts1">
	    <div class="bgsgl_right_list_border" >
		  <div class="bgsgl_right_list_left" >受访人信息</div>
		</div>
        <div class="fkdj_from" >
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  	<c:choose>
        <c:when test="${not empty vm}">
	       <tr>
			    <td class="fkdj_name"><span class="fkdj_input_span">*</span>受访人</td>
			    <td style="width:50%;float:left;">
			        <input type="text" disabled="disabled" class="fkdj_from_input" name="outName" id="outName" readonly="readonly" value="${vm.USER_NAME}" />
			        <input type="hidden" name="outName" id="outName" value="${vm.USER_NAME}" />
			        <input type="hidden" name="userid" id="userid" value="${vm.USER_ID}" />
			    </td>
			    <td style="width:50%;align:left;">
			    	<a href="javascript:void(0);" disabled="true"><img src="${ctx}/app/integrateservice/images/fkdj_pho.jpg" disabled="true" id="imgOutName" width="22" height="18" onclick="selectRespondents()" /></a>
			   	</td>
  			</tr>
			<tr>
			    <td class="fkdj_name">受访单位</td>
			    <td style="width:50%;float:left;">
			        <input type="text" disabled="disabled" class="fkdj_from_input" id="orgName" name="orgName" readonly="readonly" value="${vm.ORG_NAME}" />
			        <input  type="hidden" name="orgName" id="orgName" value="${vm.ORG_NAME}" />
			        <input  type="hidden" name="orgId" id="orgId" value="${vm.ORG_ID}" />
			    </td>
			    <td>&nbsp;</td>
			</tr>
			<tr>
			    <td class="fkdj_name">电话号码</td>
			    <td><input type="text" disabled="disabled" class="fkdj_from_input" name="phonespan" id="phonespan" readonly="readonly" value="${telnum}"/></td>
			    <input  type="hidden" name="phonespan" id="phonespan" value="${telnum}" />
			    <td style="width:50%;align:left;"><a href="javascript:void(0);" disabled="true"><img src="${ctx}/app/integrateservice/images/fkdj_phos.jpg" disabled="true" id="imgPhonespan" width="16" height="18" onclick="dialing()" /></a></td>
			</tr>
		</c:when>
	<c:otherwise>
			<tr>
			    <td class="fkdj_name"><span class="fkdj_input_span">*</span>受访人</td>
			    <td style="width:50%;float:left;">
			        <input type="text" class="fkdj_from_input" name="outName" id="outName" readonly="readonly" value="${vm.USER_NAME}" />
			        <input type="hidden" name="userid" id="userid" value="${vm.USER_ID}" />
			    </td>
			    <td style="width:50%;align:left;"><a href="javascript:void(0);"><img src="${ctx}/app/integrateservice/images/fkdj_pho.jpg" id="imgOutName" width="22" height="18" onclick="selectRespondents()" /></a></td>
			 </tr>
			 <tr>
			    <td class="fkdj_name">受访单位</td>
			    <td style="width:50%;float:left;">
			        <input type="text" class="fkdj_from_input" id="orgName" name="orgName" readonly="readonly" value="${vm.ORG_NAME}" />
			        <input  type="hidden" name="orgId" id="orgId" value="${vm.ORG_ID}" />
			    </td>
			 </tr>
			 <tr>
			    <td class="fkdj_name">电话号码</td>
			    <td><input type="text" class="fkdj_from_input" name="phonespan" id="phonespan" readonly="readonly" value="${telnum}"/></td>
			    <td style="width:50%;align:left;"><a href="javascript:void(0);" ><img src="${ctx}/app/integrateservice/images/fkdj_phos.jpg" width="16" id="imgPhonespan" height="18" onclick="dialing()" /></a></td>
			 </tr>
      </c:otherwise>
  </c:choose>
  
</table>

        </div>
          </div>
  <div class="fkdj_lefts1" style="height: 379px;">
	    <div class="bgsgl_right_list_border" >
		  <div class="bgsgl_right_list_left" >来访人信息</div>
		</div>
		<div class="fkdj_from" >
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<input type="hidden" name="cardTypenum" id="cardTypenum" />
		<input type="hidden" name="locatorIdNum" id="locatorIdNum" />
		<input type="hidden" name="pawnNum" id="pawnNum" />
		<input type="hidden" name="cardIdnum" id="cardIdnum" />
		<input type="hidden" name="residentNonum" id="residentNonum" />
		<input type="hidden" name="userNamenum" id="userNamenum" />
		<input type="hidden" name="auxiIdNum" id="auxiIdNum" />
		<input type="hidden" name="photoName" id="photoName" />
		<input type="hidden" name="dbPhotoName" id="dbPhotoName" />
		<input type="hidden" id="typeId" name="typeId" />
		<input type="hidden" id="judge" name="judge" value="0" />
		<input id="cur_vs_id" name="curvsid" type="hidden" value="${vm.VS_ID}" />
		<input id="cur_vs_flag" name="curvsflag" type="hidden" value="${vm.VS_FLAG}" />
		<input id="df" name="df" type="hidden" value="${time}" />
		<input type="hidden" id="isBack" name="isBack" />
		<input type="hidden"  id="num" name="num" value="0"/> 
	<tr>
	    <td class="fkdj_name"><span class="fkdj_input_span">*</span>访客姓名</td>
	    <td><input type="text" class="fkdj_from_input" name="userName" id="userName" value="${vm.VI_NAME}" /></td>
    <td rowspan="5">
    	<c:if test="${vm.PIC == null || vm.PIC == ''}">
			<img onclick="javascript:photograph();"	id="photoshops" src="${ctx}/app/portal/visitcenter/images/card_right_img2.png" width="145" height="175" />
		</c:if>
		<c:if test="${vm.PIC != null && vm.PIC != ''}">
			<img onclick="javascript:photograph();"	id="photoshops" src="${vm.PIC}" width="123" height="168"  />
		</c:if>
		<input id="today" name="today" type="hidden" value="${time1}" />
    </td>
    </tr>
  <tr>
    <td class="fkdj_name"><span class="fkdj_input_span">*</span>证件类型</td>
    <td colspan="1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="30%">
      <select class="fkdj_selects" style="height:27px" id="typecard" name="type" onchange="changeFatherType()">
        <option value="1"
              <c:if test="${vm.RES_BAK1 == '1'}">selected</c:if>>市民卡</option>
        <option value="2"
              <c:if test="${vm.RES_BAK1 == '2'}">selected</c:if>>身份证</option>
        <option value="3"
              <c:if test="${vm.RES_BAK1 == '3'}">selected</c:if>>其他</option>
      </select></td>
    
      </tr>
    </table></td>
    
  </tr>
  <tr id="typecardId" style="display:none">
  		<td class="fkdj_name"><span style="width:30px;color:#808080;"></span></td>
      	<td><input type="text" class="fkdj_from_input" name="certificateName" id="certificateName" value="${vm.CERTIFICATENAME}" onfocus="setNull();" /></td>
 </tr>
  <tr style="display:none" id="certificate">
    <td class="fkdj_name"><span class="fkdj_input_span">*</span>证件号码</td>
    <td><input type="text" class="fkdj_from_input" name="certificateNo" id="certificateNo" value="${vm.CERTIFICATE}"/></td>
    </tr>
  <tr style="display:none" id="resident">
    <td class="fkdj_name" ><span class="fkdj_input_span">*</span>证件号码</td>
	<td><input type="text" class="fkdj_from_input" name="residentNo" id="residentNo" value="${vm.RESIDENT_NO}" /></td>
	</tr>
  <tr id="city">
    <td class="fkdj_name" ><span class="fkdj_input_span">*</span>证件号码</td>
	<td><input type="text" class="fkdj_from_input" name="cityCard" id="cityCard" onclick="addprefix('cityCard')" value="${vm.CITY_CARD}" onmouseover="document.getElementById('card_prompt').style.display='block';" onmouseout="document.getElementById('card_prompt').style.display='none';" />
	<div id="card_prompt" class="prompt" style="display:none; padding: 0 10px;">
    		<div class="bgsgl_right_list_border">
			  <div class="bgsgl_right_list_left" style="width:100%;font-size:13px;">市民卡号帮助</div>
			</div>
    		<div style="width:210px;margin:0 auto;">
    			<div class="yellow_panel"></div>
    			<font style="color:#808080;font-size:11px;float:left;padding-top:2px;">黄色部分为市民卡号</font>
    			<font style="color:#808080;font-size:11px;float:left;padding-top:2px;">卡号开头默认添加0000</font>
    			<div style="clear:both;height:10px;"></div>
    			<div class="card_help" style="clear:both;"></div>
    		</div>
    	</div>
	</td>
	</tr>
  <tr>
    <td class="fkdj_name">单位名称</td>
    <td><input type="text" class="fkdj_from_input" name="vmDepart" id="vmDepart" value="${vm.VI_ORIGIN}" /></td>
    </tr>
  <tr>
  		<td class="fkdj_name"><span class="fkdj_input_span" style="width:30px;">*</span>手机</td>
      	<td><input type="text" class="fkdj_from_input" name="phone" id="phone" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'');" value="${vm.VI_MOBILE}" /></td>
 </tr>
  <tr>
    <td class="fkdj_name"><span class="fkdj_input_span">*</span>访客卡号</td>
    <td>
	    <%-- <c:choose>
	    <c:when test="${vm.RES_BAK1 == '1'}">
		    	<input type="text" disabled="true" class="fkdj_from_input" name="fatherCardId" id="fatherCardId" value="${vm.CARD_ID}" onblur="isFatherGivenBack(this);"/>
		    	<a class="fkdj_botton_right" disabled="true"  href="javascript:void(0);" onclick="javascript:cardReset();">读卡</a> 
	    </c:when>
	    <c:otherwise>
	   
		    	--%><input type="text" class="fkdj_from_input" name="fatherCardId" id="fatherCardId" value="${vm.IDCRADNUM}" onblur="isFatherGivenBack(this);"/>
		    	<%--<a class="fkdj_botton_right" href="javascript:void(0);" onclick="javascript:cardReset();">重读</a> --%>
	   <%-- </c:otherwise>
	    </c:choose>--%>
	    <input type="hidden" name="cardId" id="cardId" value="${vm.CARD_ID}"/>
	    <td><a class="infrom_a" style="float:left" href="javascript:void(0);" onclick="javascript:linkFunction()">分配</a></td>
	</td>
    <%--<td><a class="fkdj_botton_left" href="javascript:void(0);" onclick="javascript:cardReset();">读卡</a></td> --%>
    </tr>
  <tr style="display:none">
  		<td class="fkdj_name">访客卡号</td>
        <td><input type="text" class="fkdj_from_input" id="locatorCard" name="locatorCard" size="20" value="${vm.IDCRADNUM}"/></td>
  </tr>
  <td colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr>
    <td class="fkdj_name">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;抵押物</td>
    <td><input type="text" class="fkdj_from_input" name="pawn" id="pawn" value="${vm.RES_BAK2}"/></td>
    <td class="fkdj_name" style="visibility: hidden">车牌号</td>
    <td style="visibility: hidden"><input type="text" class="fkdj_input_mia" name="carId" id="carId" maxlength="11" onclick="suA()" value="${vm.PLATE_NUM}" size="10"/></td>
   </tr>
    </table></td>
    </tr>
  <tr>
      <tr>
        <td class="fkdj_name"><span class="fkdj_input_span">*</span>来访时间</td>
        <c:choose>
        <c:when test="${not empty vm}">
	        <td>
	        	<input type="text" disabled="disabled" class="fkdj_from_input" name="beginTime"  id="beginTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" value="${vm.VS_ST}" />
	        	<input type="hidden" id="beginTime" name="beginTime" value="${vm.VS_ST}"/>
	        </td>
        </c:when>
        <c:otherwise>
	        <td>
	        	<input type="text" class="fkdj_from_input" disabled="disabled" name="beginTime" id="beginTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" value="<%=sf.format(new Date()) %>" />
	        	<input type="hidden" id="beginTime" name="beginTime" value="<%=sf.format(new Date()) %>"/>
	        </td>
        </c:otherwise>
        </c:choose>
        <%--<td class=""><span style="width:30px;color:#808080;">至&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
        <td><input type="text" class="fkdj_input_mix" name="endTime" style="width:127px"  id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" value="${vm.VS_ET}"/></td> --%>
        <td class="fkdj_name">
        	&nbsp;
        	<input type="hidden" id="endTime" name="endTime"/>
        </td>
        <td>
        	&nbsp;
        	<input type="text" style="visibility: hidden;"/>
        </td>
      </tr>
    </tr>
</table>

</div>
</div>
        
    <div class="bgsgl_clear" ></div>
		<%--<div class="fkdj_lfrycx" >
	    <div class="bgsgl_right_list_border" >
		  <div class="bgsgl_right_list_left" >随行人员信息</div>
		</div>
		<div class="fkdj_sxry" >
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr class="fkdj_sxry_brack">
    <td class="fkdj_check"><input name="checkbox_all" id="checkbox_all" type="checkbox" value="" onclick="checkAll()"/></td>
    <td width="8%">卡类型</td>
    <td width="8%">姓名</td>
    <td width="15%">身份证号</td>
    <td width="15%">卡号</td>
    <td width="15%">定位卡号</td>
    <td width="8%">抵押物</td>
    <td width="8%" id="status">状态</td>
    <td class="fkdj_check">删除</td>
  </tr>
 </table>
 <div style="overflow-y:auto; height: 103px;">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" id="card_right_bg2" >
 <c:choose>
       <c:when test="${null != vm && null != vvaList && fn:length(vvaList) > 0 && (vm.VS_FLAG=='02' || vm.VS_FLAG=='01' ) && vm.VS_TYPE!='3'}">
<input type="hidden" id="svNum" value="${fn:length(vvaList)}" />
	  <c:forEach items="${vvaList}" var="vv" varStatus="index">
	  <tr id="num_tr${index.index+1}">
	      <td class="fkdj_check"><input type="hidden" name="auxiId" value="${vv.vaId}" /><input name="checkbox" id="checkbox_${index.index+1}" type="checkbox"/></td>
			 <td width="8%">
			 <select id="type${index.index+1}" name="" onchange="changeTmpCardType(${index.index+1})" >
                <option value='1' <c:if test="${vv.cardType eq '1'}">selected="selected"</c:if>>市民卡</option>
                <option value='2' <c:if test="${vv.cardType eq '2'}">selected="selected"</c:if>>临时卡</option>
               </select>
			 </td>
		  <td width="8%"><input type="text" id="fuka_name${index.index+1}" name="fuka_name" size="4" value="${vv.vaBak1}"/></td>
		  <td width="15%"><input type='text' id="fuka_shenfen${index.index+1}" name="fuka_shenfen" size="16" value="${vv.residentNo}"/></td>
		  <td width="15%"><input type='text' id="tmpCardId${index.index+1}" name="fuka_linshi" size="16" value="${vv.cardId}" onblur="isGivenBack(type${index.index+1},this);"/></td>
		  <td width="15%"><input type="text" name="tmpLocator" size="16" value="${vv.vaBak2}" /></td>
		  <td width="8%"><input type="text" name="tmpPawn" size="4" value="${vv.vaBak3}"/></td>
		  <td width="6%">
		   <label>登记</label>
		  </td>
		 <td class="fkdj_check"><img src="${ctx}/app/integrateservice/images/fkdj_dele.jpg" width="15" height="15" border="0" onclick="delfield(${index.index+1})"/></td>
	  </tr>
	  </c:forEach>
       </c:when>
       <c:otherwise>
       <input type="hidden" id="svNum" value="${fn:length(vvaList)}" />
	  <c:forEach items="${vvaList}" var="vv" varStatus="index">
	  <tr id="num_tr${index.index+1}">
	      <td class="fkdj_check"><input type="hidden" name="auxiId" value="${vv.vaId}" /><input name="checkbox" id="checkbox_${index.index+1}" type="checkbox"/></td>
		  <c:choose>
			<c:when test="${vv.cardType eq '2'}">
			 <td width="8%">
			 <label>临时卡</label>
			 </td>
			</c:when>
		    <c:otherwise>
		     <td width="8%">
			 <label>市民卡</label>
			 </td>
		    </c:otherwise>
		  </c:choose>
		  <td width="8%"><label >${vv.vaBak1}</label></td>
		  <td width="15%"><label >${vv.residentNo}</label></td>
		  <td width="15%"><label >${vv.cardId}</label></td>
		  <td width="15%"><label >${vv.vaBak2}</label></td>
		  <td width="8%"><label >${vv.vaBak3}</label></td>
		   <c:if test="${vv.vaReturn=='1'}">
		   <td width="8%">
		   <label id="back_${index.index+1}" >归还</label>
		   </td>
		   </c:if><c:if test="${vv.vaReturn=='2'}">
		   <td width="8%">
		   <label id="back_${index.index+1}">未归还</label>
		   </td>
		   </c:if>
		   <c:if test="${vv.vaReturn=='3'}">
		   <td width="6%">
		   <label id="back_${index.index+1}">未发卡</label>
		   </td>
		   </c:if>
		 <td class="fkdj_check"><a href="javascript:void(0);"><img src="${ctx}/app/integrateservice/images/fkdj_dele.jpg" width="15" height="15" border="0" /></a></td>
	  </tr>
	  </c:forEach>
       </c:otherwise>

</c:choose>


</table>
</div> 
<div class="fkdj_ch_table"><a href="javascript:void(0);" onclick="delfieldAll()">删除所选</a></div> 
        </div>
        </div>--%>
  <div class="fkdj_bottons1"><a class="fkdj_botton_left" href="javascript:void(0);" onclick="saveUnBeforeVM1vm('1')">确认提交</a></div>
</div>
        </div>

</div>
</form>	

