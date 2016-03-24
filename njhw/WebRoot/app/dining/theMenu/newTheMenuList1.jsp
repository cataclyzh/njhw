<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 菜单发布管理页面
--%>

<style>
#tooltip{
	position:absolute;
	
	color:#333;
	display:none;
    width:98px;
    height: 25px;
    background-color:#77DDFF;
    z-index: 1500;
}

img{ cursor:pointer;}
</style>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<style>
.table01 td ,.table01 th{border:1px solid #e4e4e4; font-size: 14px; }
.table01 th{font-size: 18px;}
</style>

	<title>菜单发布列表</title>	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
		<%@ include file="/common/include/meta.jsp"%>
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
		$(document).ready(function(){
			  setTimeout("show_hide_span()", 1000);

		});

		var TOPSHOW = 0;	// 判断提示框是否显示
		var fn;				// 定时器全局变量
		var fn2;
		var fn3;
		
		var editMenu = "", listMenu=""; 
		
		function initBindTag(){
		    var x = 0, y = 0;
			$("div.tooltip").mouseover(function(e){
				
				editMenu = $(this).attr("editMenu");
				listMenu = $(this).attr("listMenu");
			
				
		        if($('#tooltip')){
		            $('#tooltip').remove();
		          
		            clearTimeout(fn);
		        }
		        var DomLeft = mousePos(e).x;
	       		var DomTop = mousePos(e).y;
	        	var tooltip ="<div id='tooltip'><img src='${ctx}/app/dining/theMenu/images/bianji.gif' onclick='"+editMenu+"();'/>"
				+"<img src='${ctx}/styles/icons/up.gif'  onclick='up(&quot;"+listMenu+"&quot;)' title='上移'/>"
				+"<img src='${ctx}/styles/icons/down.gif' onclick='down(&quot;"+listMenu+"&quot;)' title='下移'/></div>"
			  		     
				$("body").append(tooltip);	//把它追加到文档中
		     
				$("#tooltip").css({"top": (DomTop) + "px","left": (DomLeft+20) + "px" //设置x坐标和y坐标，并且显示
				}).show("fast").mouseover(function(){
		                TOPSHOW = 1;
		        });
		       
	           
		        
		    }).mouseout(function(){
		        fn = setTimeout("if(TOPSHOW == 0) {$('#tooltip').remove();}", 1000);//定时器，一秒后如果未点击，自动消失
		    });
		}
		
		function mousePos(e){
			var x,y;
			var e = e||window.event;
			return {
				x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,
				y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop
			};
		};


		

		function reset(){
			var cbox=$("input:checked[name='_chk']");        
			$.each(cbox, function(i,item){
			   	if($(this).parent().parent().attr("class") == "oddselected"){
			   		$(item).parent().parent().attr("class","odd");
			   	}
				if($(this).parent().parent().attr("class") == "evenselected"){
			 		$(item).parent().parent().attr("class","even");
				}					    
		    });	
		}

		// 选中全部
		function chk_all() {
			$("#pageDiv input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("disabled")!="disabled")	{		
					if ($("#phone_check_all").attr("checked") == "checked"){
						$(item).attr("checked", "true");
					} else {
						$(item).removeAttr("checked");
					}
				}
			});
		}

		function editMenu1(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=1&fdiType=1" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu2(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=1&fdiType=2" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu3(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=1&fdiType=3" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu4(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=1&fdiType=4" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu5(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=1&fdiType=5" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu6(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=1&fdiType=6" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu7(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=1&fdiType=7" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu8(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=2&fdiType=1" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu9(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=2&fdiType=2" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu10(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=2&fdiType=3" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu11(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=2&fdiType=4" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu12(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=2&fdiType=5" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu13(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=2&fdiType=6" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu14(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=2&fdiType=7" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu15(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=3&fdiType=1" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu16(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=3&fdiType=2" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu17(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=3&fdiType=3" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu18(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=3&fdiType=4" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu19(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=3&fdiType=5" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu20(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=3&fdiType=6" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}
		function editMenu21(){
			var url = "${ctx}/app/din/addOrUpdateNewTheMenu.act?&fdiFlag=3&fdiType=7" ; 
			openEasyWin("winDining","请选择菜单",url,"900","500",true);
		}

     function up(id){      
    	var fdiId = $("input:radio[name='"+id+"']:checked").val();
    	if(fdiId!=null&&fdiId!=undefined){
	    	$.ajax({
	            type:"POST",
	            url:"${ctx}/app/din/upMenu.act",
	            data:{fdiId:fdiId},
	            dataType: 'json',
			     async : false,
			     success: function(json){
		             if (json!=null) {  
			             if(json.status==null){
				            $("#"+id).empty();
					           	$.each(json,function(i,item){
					           		var trContent="";
							        if(fdiId==item.FDI_ID){
							        	trContent ="<input type='radio' checked='checked' name='"+id+"' id='"+item.FDI_ID+"' value='"+item.FDI_ID+"'/>"+item.FD_NAME+"："+item.FD_BAK1+"<br/>";
								    }else{
								    	trContent ="<input type='radio' name='"+id+"' id='"+item.FDI_ID+"' value='"+item.FDI_ID+"'/>"+item.FD_NAME+"："+item.FD_BAK1+"<br/>";
									}
					           		$("#"+id).append(trContent);		
							  	});
				            
				          }else{
			                  // alert("不能上移！");
					          }
		             }     
	            }
	    	 });
	    }
      }

function down(id){
	var fdiId = $("input:radio[name='"+id+"']:checked").val();
 	if(fdiId!=null&&fdiId!=undefined){
 	$.ajax({
         type:"POST",
         url:"${ctx}/app/din/downMenu.act",
         data:{fdiId:fdiId},
         dataType: 'json',
		     async : false,
		     success: function(json){
	             if(json!=null) {
	            	if(json.status==null){
	            	$("#"+id).empty(); 
		           	$.each(json,function(i,item){
		           		var trContent="";
				        if(fdiId==item.FDI_ID){
				        	trContent ="<input type='radio' checked='checked' name='"+id+"' id='"+item.FDI_ID+"' value='"+item.FDI_ID+"'/>"+item.FD_NAME+"："+item.FD_BAK1+"<br/>";
					    }else{
					    	trContent ="<input type='radio' name='"+id+"' id='"+item.FDI_ID+"' value='"+item.FDI_ID+"'/>"+item.FD_NAME+"："+item.FD_BAK1+"<br/>";
						}
		           		$("#"+id).append(trContent);		
				  	});
	            	}else{
	                  //  alert("不能下移！");
			          }
	          }       
      	}
 	});
}

	
}	
	/*
	* 鼠标移过不同的div显示操作按键
	*
	*/
	function show_hide_span(id){
		var m_div="";
		var m_span="_span";
		var tempId="";
		$("div.tooltip").mouseover(function(e){
			m_div = $(this).attr("editMenu");
			$("#"+m_div+m_span).show();
			var list_m_div = $(this).attr("listMenu");
			$("input:radio[name!='"+list_m_div+"']").attr("checked",false);
	    }).mouseout(function(){
	    	m_div = $(this).attr("editMenu");
	    	$("#"+m_div+m_span).hide();
	    	var list_m_div = $(this).attr("listMenu");
			$("input:radio[name!='"+list_m_div+"']").attr("checked",false);
	    });
	}
		
	</script>
<style>
</style>
</head>
<body>
<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						菜单管理
					</div>
				</div> 
				<div class="bgsgl_conter" style="min-height: 600px" width="100%" height="100%">
					<form id="queryForm" action="initNewTheMenu.act" method="post"  autocomplete="off" style="min-height: 600px;width:100%; height:100%;">
						<table width="100%" height="100%"  border="0" cellpadding=0 cellspacing="0" style="text-align:left;  margin:0 auto; line-height:22px; font-size:14px; color:#333; border-collapse:collapse; border:1px solid #e4e4e4;">
							<thead style="width:100%;height:50px;border:1px solid #e4e4e4; background-color:#eeeef0; font-weight: bold;">
								
								  <th style="height:100%; width:6%; " align="left"></th>
								  <th style="height:100%; width:15%;" align="left">一周菜单发布&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="javascript:showShelter('1150' ,'410' ,'${ctx}/app/din/queryDishes.act');"><span style="color: blue;">菜单管理</span></a></th>
								  <th style="height:100%; width:13%;" align="left"></th>
								  <th style="height:100%; width:13%;" align="left">&nbsp;</th>
								  <th style="height:100%; width:13%;" align="left">&nbsp;</th>
								  <th style="height:100%; width:13%;" align="left">&nbsp;</th>
								  <th style="height:100%; width:13%;" align="left">&nbsp;</th>
								  <th style="height:100%;" align="left">&nbsp;</th>
								  
							</thead>
							<thead style=" width:100%;height:50px;border:1px solid #e4e4e4; background-color:#eeeef0; font-weight: bold;">
								<th style="height:100%; width:6%;"> </th>
								<th style="height:100%; width:15%;" align="center">星期一</th>
								<th style="height:100%; width:13%;" align="center">星期二</th>
								<th style="height:100%; width:13%;" align="center">星期三</th>
								<th style="height:100%; width:13%;" align="center">星期四</th>
								<th style="height:100%; width:13%;" align="center">星期五</th>
								<th style="height:100%; width:13%; color: green" align="center">星期六</th>
								<th style="height:100%; color: red" align="center">星期日</th>
							</thead>
							<tr valign="top" style="width:100%;background: #f4fbff;height:150px;" >
								<th align="center" valign="middle" style="width:6%;height:100%;">早餐</th>
								<td style="height:100%; width:15%;">
									<div class="tooltip" editMenu="editMenu1" listMenu="list0" style="height:100%; width:100%;" >
									<div style="height:70%; width:100%;overflow:auto;" id="list0">
									<c:forEach items="${typeList[0]}" var="typeList">
									<input type="radio" name="list0" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu1_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu1();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list0')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list0')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu2" listMenu="list3" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list3" >
									<c:forEach items="${typeList[3]}" var="typeList">
									<input type="radio" name="list3" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu2_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu2();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list3')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list3')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>	
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu3" listMenu="list6" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list6">
									<c:forEach items="${typeList[6]}" var="typeList">
									<input type="radio" name="list6" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu3_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu3();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list6')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list6')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu4" listMenu="list9" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list9">
									<c:forEach items="${typeList[9]}" var="typeList">
									<input type="radio" name="list9" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu4_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu4();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list9')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list9')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu5" listMenu="list12" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list12">
									<c:forEach items="${typeList[12]}" var="typeList">
									<input type="radio" name="list12" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu5_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu5();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list12')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list12')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu6" listMenu="list15" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list15">
									<c:forEach items="${typeList[15]}" var="typeList" >
									<input type="radio" name="list15" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu6_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu6();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list15')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list15')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td>
									<div class="tooltip" editMenu="editMenu7" listMenu="list18" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list18">
									<c:forEach items="${typeList[18]}" var="typeList">
									<input type="radio" name="list18" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu7_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu7();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list18')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list18')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
							</tr>
							<tr valign="top" style="width:100%;height:150px; background-color:#eef3ff;" class="tooltip" editMenu="editMenu8" listMenu="list1">
								<th align="center" style="width:6%;height:100%;"  valign="middle" >午餐</th>
								<td style="height:100%; width:15%;">
									<div class="tooltip" editMenu="editMenu8" listMenu="list1" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list1">
									<c:forEach items="${typeList[1]}" var="typeList">
									<input type="radio" name="list1" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu8_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu8();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list1')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list1')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu9" listMenu="list4" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list4">
									<c:forEach items="${typeList[4]}" var="typeList" >
									<input type="radio" name="list4" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu9_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu9();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list4')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list4')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu10" listMenu="list7" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list7">
									<c:forEach items="${typeList[7]}" var="typeList">
									<input type="radio" name="list7" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu10_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu10();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list7')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list7')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu11" listMenu="list10" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list10">
									<c:forEach items="${typeList[10]}" var="typeList">
									  <input  type="radio" name="list10" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu11_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu11();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list10')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list10')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu12" listMenu="list13" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list13">
									<c:forEach items="${typeList[13]}" var="typeList">
								     <input type="radio" name="list13" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
								     ${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu12_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu12();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list13')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list13')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu13" listMenu="list16" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list16">
									<c:forEach items="${typeList[16]}" var="typeList">
									<input type="radio" name="list16" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu13_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu13();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list16')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list16')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td>
									<div class="tooltip" editMenu="editMenu14" listMenu="list19" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list19">
									<c:forEach items="${typeList[19]}" var="typeList">
									<input type="radio" name="list19" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu14_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu14();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list19')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list19')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
							</tr>
							<tr valign="top" style="width:100%;height:150px; background-color:#eef3ff;" class="tooltip" editMenu="editMenu8" listMenu="list1">
								<th align="center" style="width:6%;height:100%;"  valign="middle" >晚餐</th>
								<td style="height:100%; width:15%;">
									<div class="tooltip" editMenu="editMenu15" listMenu="list2" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list2">
									<c:forEach items="${typeList[2]}" var="typeList">
									<input type="radio" name="list2" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu15_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu15();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list2')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list2')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu16" listMenu="list5" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list5">
									<c:forEach items="${typeList[5]}" var="typeList" >
									<input type="radio" name="list5" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu16_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu16();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list5')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list5')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu17" listMenu="list8" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list8">
									<c:forEach items="${typeList[8]}" var="typeList">
									<input type="radio" name="list8" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu17_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu17();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list8')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list8')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu18" listMenu="list11" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list11">
									<c:forEach items="${typeList[11]}" var="typeList">
									  <input  type="radio" name="list11" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu18_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu18();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list11')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list11')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu19" listMenu="list14" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list14">
									<c:forEach items="${typeList[14]}" var="typeList">
								     <input type="radio" name="list14" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
								     ${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu19_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu19();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list14')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list14')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td style="height:100%; width:13%;">
									<div class="tooltip" editMenu="editMenu20" listMenu="list17" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list17">
									<c:forEach items="${typeList[17]}" var="typeList">
									<input type="radio" name="list17" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu20_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu20();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list17')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list17')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
								<td>
									<div class="tooltip" editMenu="editMenu21" listMenu="list20" style="height:100%; width:100%;">
									<div style=" height:70%; width:100%; overflow:auto;" id="list20">
									<c:forEach items="${typeList[20]}" var="typeList">
									<input type="radio" name="list20" id="${typeList.FDI_ID}" value="${typeList.FDI_ID}"/>
										${typeList.FD_NAME}<c:if test="${typeList.FD_BAK1 != '' &&  typeList.FD_BAK1 != null}">：${typeList.FD_BAK1}</c:if><br/>
									</c:forEach>
									</div>
									<span id="editMenu21_span" style=" width:100%;display: none;">
										<img src="${ctx}/app/dining/theMenu/images/bianji.gif"  onclick="javaScript:editMenu21();"/>
										<img src="${ctx}/styles/icons/up.gif"  onclick="up('list20')" title="上移" style="margin-top: 20px;"/>
										<img src="${ctx}/styles/icons/down.gif" onclick="down('list20')" title="下移" style="margin-top: 20px;"/>
									</span>
									</div>
								</td>
							</tr>
						</table>
					</form>
				
				    <!--此处写页面的内容 -->
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>

</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>