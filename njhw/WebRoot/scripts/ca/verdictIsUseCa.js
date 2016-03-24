/**
 * 是否使用签章,签章序列号是否正确Ajax调用
 * @param loginname 登录名
 * @param actioncode 按钮actioncode
 * @param ctx requestPath
 * @returns isuseca
 */
function isUseCa(loginname,actioncode,ctx){
	var isuseca = "";
	$.ajax({
		type : "POST",
		url : ctx+"/common/verdictIsUseCa/isUseCa.act",
		data : "loginname="+loginname+"&actioncode="+actioncode,
		async : false,
		success : function(later_isuseca) {
			isuseca = later_isuseca;
		},
		error : function(msg, status, e){
			alert("是否使用签章ajax调用出错！错误信息:"+status+e);
		}
	});
	return isuseca;
}
function isRightCa(loginname,signcert,ctx){
	var isrightca = "";
	$.ajax({
		type : "POST",
		url : ctx+"/common/verdictIsUseCa/isRightCa.act",
		data : "loginname="+loginname+"&cano="+signcert,
		async : false,
		success : function(later_isrightca) {
			isrightca = later_isrightca;
		},
		error : function(msg, status, e){
			alert("是否使用签章ajax调用出错！错误信息:"+status+e);
		}
	});
	return isrightca;
}
