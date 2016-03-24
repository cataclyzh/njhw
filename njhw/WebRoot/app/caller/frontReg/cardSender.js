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
	             url:str+"/app/visitor/saveSoncard.act",
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
		function delect(obj){
			
			var fid = obj;
			var vsid = $("#vsid").val();
			//alert(vsid)
			//alert(obj)
			  $.ajax({
		             type:"POST",
		             url:str+"/app/visitor/deleteVmVisitAuxi.act",
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
			
		