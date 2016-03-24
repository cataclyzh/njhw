function tableValue() {
	var res = true;
	var residentNo = "";
	var cardId = "";
	var userName = "";

	$("#fied_table tr").each(
			function(i, item) {
				if ($($(item).find("td input")[0]).val() == ""
						|| $($(item).find("td input")[1]).val() == ""
						|| $($(item).find("td input")[2]).val() == "") {
					res = false
					// alert(res);
				}
				userName += $($(item).find("td input")[0]).val() + ",";
				residentNo += $($(item).find("td input")[1]).val() + ",";
				cardId += $($(item).find("td input")[2]).val() + ",";
			});

	var userName1 = userName.substr(0, userName.length - 1);
	var residentNo1 = residentNo.substr(0, residentNo.length - 1);
	var cardId1 = cardId.substr(0, cardId.length - 1);
	// alert(userName1+"/"+residentNo1+"/"+cardId1);
	$("#residentNonum").val(residentNo1);
	$("#cardIdnum").val(cardId1);
	$("#userNamenum").val(userName1);
	return res;

}

function saveData() {
	// var orgname =$("#orgId").find("option:selected").text();
	// var outname = $("#outId").find("option:selected").text();
	// alert(orgname);
	// alert(outname);
	// $("#orgName").val(orgname);
	// $("#outName").val(outname);

	if ($("#comeNum").val() > 1) {
		
		if (tableValue()) {
			$("#comeNum").val($("#fied_table tr").length + 1);
			var frm = $('#inputForm');
			frm.submit();
		} else {
			easyAlert("提示信息", "请填写完整的绑卡参数!");
		}
	} else {
		
		var frm = $('#inputForm');
		frm.submit();
	}
}

// 组织机构得到用户
function orgUser() {
	var orgId = $("#orgId").val();
	if (orgId > 0) {
		$.ajax( {
			type : "POST",
			url : str + "/app/visitor/getUsersByOrgId.act",
			data : {
				orgId : orgId
			},
			dataType : 'json',
			async : false,
			success : function(json) {

				if (json.status == 0) {

					$("#outId").empty();

					$.each(json.list, function(i) {
						$("#outId").append(
								"<option value=" + json.list[i][0] + ">"
										+ json.list[i][1] + "</option>");
					});
				}
			}
		});
	}
}

// 验证市民卡
function ajaxNvrCard(id) {
	var res = true;
	if (id != "") {
		var url = str + "/app/caller/checkCityCar.act";
		var data = {
			nvrCard : id
		};
		sucFun = function(data) {
			if (data.state == "error") {

				res = false;
			} else {
				return true;
			}
		};
		errFun = function(data) {

		};
		ajaxQueryJSON(url, data, sucFun, errFun);
	}
	return res;
}
//

/**
 * 人数改变事件
 */
function txtRelativeChange() {
	var txtRelativeNum = $("#comeNum").val();
	if (txtRelativeNum > 1) {
		$("#div_band_relative_card").show();
	} else {

		$("#div_band_relative_card").hide();
		// $("#div_band_relative_card input").clear();

	}
}

function selectVisit() {
	var residentId = $("#cardId").val();
	$.ajax( {
		type : "POST",
		url : str + "/app/visitor/loadVisiInfo.act",
		data : {
			residentId : residentId
		},
		dataType : 'json',
		async : false,
		success : function(json) {
			if (json.status == 0) {
				$("#userName").val(json.VIName);
				$("#phone").val(json.ViMobile);
				$("#carId").val(json.PlateNum);

			}

		}
	});
}

// 验证省份证号;
function validationCard(obj) {
	var vaidRes = true;
	if (idCardNoUtil.checkIdCardNo(obj.value)) {
		vaidRes;

	} else {
		obj.value = "";
		obj.focus();
		vaidRes = false;
		alert("请输入正确的省份证号!");

	}
}


//根据事务id信息,得到事务全部基本信息
function readInfoval(id){
	var resBak1="";
	var url = str + "/app/visitor/frontReg/getAllInfor.act";
	var sucFun = function(data) {
		$("#vm_ope_content").empty().html(data);
	};
	var errFun = function() {
		alert("加载数据出错!");
	};
	var data = "vsId=" + id;
	ajaxQueryHTML(url, data, sucFun, errFun);
}

//取消预约

function cancelVistor(vsId) {
	easyConfirm('提示', '确定取消？', function(r) {
		if (r) {
			url = str + "/app/visitor/frontReg/cancelVistor.act?vsId=" + vsId;
			var viName = $("#viName").val();
			var resBak1 = $("#resBak1").val();
			var vsFlag = $("#vsFlag").val();
			var vsSt = $("#vsSt").val();
			var vsEt = $("#vsEt").val();
			var pageNo = 1;
			//var url = str + "/app/visitor/frontReg/visitorList.act";
			var sucFun = function(data) {
				$("#left_below").empty().html(data);
			};
			var errFun = function() {
				alert("加载数据出错!");
			};
			var data = "viName=" + viName + "&resBak1=" + resBak1 + "&vsFlag="
			+ vsFlag + "&vsSt="+ vsSt+ "&vsEt=" + vsEt  + "&pageNo=" + pageNo;
			ajaxQueryHTML(url, data, sucFun, errFun);
		}
	});
}

// new查询访客
function queryData() {
	var viName = $.trim($("#viName").val());
	var resBak1 = $("#resBak1").val();
	var vsFlag = $("#vsFlag").val();
	var vsSt = $("#vsSt").val();
	var vsEt = $("#vsEt").val();
	var pageNo = 1;
	var url = str + "/app/visitor/frontReg/visitorList.act";
	var sucFun = function(data) {
		$("#left_below").empty().html(data);
	};
	var errFun = function() {
		alert("加载数据出错!");
	};
	var data = "viName=" + viName + "&resBak1=" + resBak1 + "&vsFlag="
	+ vsFlag + "&vsSt="+ vsSt+ "&vsEt=" + vsEt  + "&pageNo=" + pageNo;
	ajaxQueryHTML(url, data, sucFun, errFun);

}

function jumpPage(a) {
	$("#pageNo").val(a);
	var viName = $("#viName").val();
	var resBak1 = $("#resBak1").val();
	var vsFlag = $("#vsFlag").val();
	var vsSt = $("#vsSt").val();
	var vsEt = $("#vsEt").val();
	var pageNo = $("#pageNo").val();
	var url = str + "/app/visitor/frontReg/visitorList.act";
	var sucFun = function(data) {
		$("#left_below").empty().html(data);
	};
	var errFun = function() {
		alert("加载数据出错!");
	};
	var data = "viName=" + viName + "&resBak1=" + resBak1 + "&vsFlag="
	+ vsFlag + "&vsSt="+ vsSt+ "&vsEt=" + vsEt  + "&pageNo=" + pageNo;
	ajaxQueryHTML(url, data, sucFun, errFun);
}

//new分页
function goPage(pageNo) {
	var viName = $("#viName").val();
	var resBak1 = $("#resBak1").val();
	var viMobile = $("#viMobile").val();
	var url = str + "/app/visitor/frontReg/visitorList.act";
	var sucFun = function(data) {
		$("#left_below").empty().html(data);
	};
	var errFun = function() {
		alert("加载数据出错!");
	};
	var data = "viName=" + viName + "&resBak1=" + resBak1 + "&viMobile="
			+ viMobile + "&pageNo=" + pageNo;
	ajaxQueryHTML(url, data, sucFun, errFun);
}

