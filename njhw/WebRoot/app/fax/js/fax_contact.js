var searchVal = "";
var sid = null;
/**
 * initial the page is fully loaded
 */
$(function($) {
	$("#tab-recent").click(function() {
		$("#tab-recent").attr('class', 'tips1');
		$("#tab-all").attr('class', 'tips2');
		$("#contact-all").hide();
		$("#contact-recent").hide();
		$("#contact-recent").fadeIn();

		init();
		queryFaxRecentList();
		// queryPersonalContactList();

	});
	$("#tab-all").click(function() {
		$("#tab-recent").attr('class', 'tips2');
		$("#tab-all").attr('class', 'tips1');
		$("#contact-recent").hide();
		$("#contact-all").hide();
		$("#contact-all").fadeIn();
		init();
		// queryFaxRecentList();
		queryPersonalContactList();
	});
	$("#search").click(function() {
		if ($("#searchTJ").val() != "请输入姓名或传真号")
			searchVal = $("#searchTJ").val();
		else
			searchVal = "";

		$("#tab-all").click();
	});
	$("#refresh_List").click(function() {
		/*
		 * if ($("#tab-recent").attr("class") == "tips1") window.location.href =
		 * window.location.pathname + "?tab=recent"; else if
		 * ($("#tab-all").attr("class") == "tips1") window.location.href =
		 * window.location.pathname + "?tab=all";
		 */
		refreshList();
		if ($("#tab-recent").attr("class") == "tips1") {
			$("#contact-recent").hide();
			$("#contact-recent").fadeIn();
		} else if ($("#tab-all").attr("class") == "tips1") {
			$("#contact-all").hide();
			$("#contact-all").fadeIn();
		}

	});
	$("#editTree").click(function() {

		//var url = "contactInit.act";
		var url = _ctx+"/app/paddressbook/init.act";
//		window.top.showIframeModal("联系人", 1010, 600, url, function() {
//			$("#refresh_List").click();
//		});
		window.top.showShelter('1010', '475', _ctx + '/app/paddressbook/init.act');
	});

	switch ($.trim(getQueryString("tab"))) {
	case "all":
		$("#tab-all").click();
		break;
	case "recent":
		$("#tab-recent").click();
		break;
	default:
		$("#tab-all").click();
		refreshList();
		break;
	}

});
function refreshList() {
	searchVal = "";
	init();
	queryFaxRecentList();
	queryPersonalContactList();
}
function init() {

	// post请求
	$.post(
	// 请求action
	"init.act", {
		// 参数设定
		page_size : 25
	}, function(returnedData, textstatus) {
		// initList(returnedData);
		// alert(returnedData);
		if (returnedData.data == null) {
			window.top.showMessageBox("登录失败！", function() {
				var url = _ctx + "/app/integrateservice/init.act";
				// window.open(url);
				window.top.location.href = url;
			});
		}

		sid = "" + returnedData.data.session_id + "";
		// alert(sid);

	}, "json");
}

function deleteContact(nuaid) {
	// alert(nuaid);
	// post请求
	$.post(
	// 请求action
	"deletePersonalContact.act", {
		// 参数设定
		nuaid : nuaid
	}, function(returnedData, textstatus) {
		if ($.trim(returnedData.result) == "success") {
			window.top.showMessageBox("删除成功！", function() {
				$("#refresh_List").click();
				// window.location.href = window.location.pathname + "?tab=all";
			});
		} else {
			window.top.showMessageBox("删除失败！");
		}

	}, "json");
}
/**
 * 
 */
function queryPersonalContactList() {

	// post请求
	$.post(
	// 请求action
	"getPersonalContactList.act", {
		// 参数设定
		searchVal : searchVal
	}, function(returnedData, textstatus) {
		createPersonalContactList(returnedData);
		// alert(returnedData);
	}, "json");
}
/**
 * 
 */
function queryFaxRecentList() {

	// post请求
	$.post(
	// 请求action
	"getFaxRecentContact.act", function(returnedData, textstatus) {
		createFaxRecentList(returnedData);
		// alert(returnedData.length);
	}, "json");
}

/**
 * 
 * @param jsonArray
 */
function createFaxRecentList(jsonArray) {
	$("#contact-recent").empty();
	// 要替换的内容
	// 动态添加表格
	var tableLI = $(document.createElement("li"));
	var tableDIV = $(document.createElement("div"));
	var tableSPAN = $(document.createElement("span"));

	var jList = jsonArray;
	// alert(searchVal+" "+jsonArray.length);
	if (jList != null) {
		for ( var i = 0; i < jList.length; i++) {
			var containerLI = tableLI.clone();
			var containerDIV = tableDIV.clone().addClass("main3_cen_list");

			var name = tableSPAN.clone().addClass("item");
			var fax = tableSPAN.clone().addClass("item");
			var edit = tableSPAN.clone().addClass("item");

			var strName=jList[i].name.substring(0, 5);
			if (jList[i].name.length > 5)
				strName += "...";
			containerDIV.append(name.html("<span title=\""
										+ jList[i].name
										+ "\">"+strName+ "</span>"));
			if (jList[i].fax != null){
				//限制最长显示13个字符
				var strFaxTitle = jList[i].fax.substring(0, 12);
				if (jList[i].fax.length > 12)
					strFaxTitle += "...";
				containerDIV
						.append(fax
								.html("<span class=\"recent-fax-link hand darkblue\" title=\""
										+ jList[i].fax
										+ "\" username=\""
										+ jList[i].name
										+ "\" fax=\""
										+ jList[i].fax
										+ "\">"
										+ strFaxTitle
										+ "</span>"));
			}
			else
				containerDIV.append(fax.html("（无）"));
			if (jList[i].name == "（未添加）") {
				containerDIV.append(edit.html("<span onclick=\"addPerson('"
						+ jList[i].fax
						+ "')\"  class=\"orange hand\" >[新增]</span>"));
			}

			containerLI.append(containerDIV);
			$("#contact-recent").append(containerLI);

		}

		if (jsonArray.length == 0) {

		} else {

		}

	}
	$(".recent-fax-link").unbind("click").click(
			function() {
				var fo_to = $.trim($("#fo_to", window.parent.document).val());
				var fax_hidden = $
						.trim($("#fax-hidden", window.parent.document).val());

				if (fo_to != "")
					$("#fo_to", window.parent.document).val($("#fo_to", window.parent.document).val() + ";");
				if (fax_hidden != "")
					$("#fax-hidden", window.parent.document).val($("#fax-hidden", window.parent.document).val() + ";");

				if ($(this).attr("username") != "（未添加）") {
					$("#fo_to", window.parent.document).val($("#fo_to", window.parent.document).val() + $(this).attr("username") + "(" + $(this).attr("fax") +")");
				} else {
					$("#fo_to", window.parent.document).val($("#fo_to", window.parent.document).val() + $(this).attr("fax"));
				}
				$("#fax-hidden", window.parent.document).val($("#fax-hidden", window.parent.document).val() + formatTelTextToValue($(this).attr("fax")));
				
			});
}
/**
 * 
 * @param jsonArray
 */
function createPersonalContactList(jsonArray) {
	$("#contact-all").empty();
	// 要替换的内容
	// 动态添加表格
	var tableLI = $(document.createElement("li"));
	var tableDIV = $(document.createElement("div"));
	var tableSPAN = $(document.createElement("span"));

	var jList = jsonArray;
	// alert(searchVal+" "+jsonArray.length);
	if (jList != null) {
		for ( var i = 0; i < jList.length; i++) {
			var containerLI = tableLI.clone();
			var containerDIV = tableDIV.clone().addClass("main3_cen_list");

			var name = tableSPAN.clone().addClass("item");
			var fax = tableSPAN.clone().addClass("item");
			var edit = tableSPAN.clone().addClass("item");
			var strName=jList[i].name.substring(0, 5);
			if (jList[i].name.length > 5)
				strName += "...";
			containerDIV.append(name.html("<span title=\""
					+ jList[i].name
					+ "\">"+strName+ "</span>"));
			if (jList[i].fax != null) {
				//限制最长显示13个字符
				var strFaxTitle = jList[i].fax.substring(0, 12);
				if (jList[i].fax.length > 12)
					strFaxTitle += "...";
				containerDIV
						.append(fax
								.html("<span class=\"all-fax-link hand darkblue\" title=\""
										+ jList[i].fax
										+ "\" username=\""
										+ jList[i].name
										+ "\" fax=\""
										+ jList[i].fax
										+ "\">"
										+ strFaxTitle
										+ "</span>"));
			} else
				containerDIV.append(fax.html("（无）"));
			containerDIV
					.append(edit
							.html("<span class=\"orange hand\" onclick=\"showInfo("
									+ jList[i].nuaid
									+ ")\" >[查]</span>&nbsp;<span onclick=\"editPerson("
									+ jList[i].nuaid
									+ ")\"  class=\"orange hand\" >[改]</span>&nbsp;<span onclick=\"deletePerson("
									+ jList[i].nuaid
									+ ")\" class=\"red hand\" >[删]</span>"));
			containerLI.append(containerDIV);
			$("#contact-all").append(containerLI);

		}

		if (jsonArray.length == 0) {

		} else {

		}

	}
	$(".all-fax-link").unbind("click").click(
			function() {
					
				//var faxNos=$(this).attr("fax").replaceAll(",", ";", false);
				var faxNosArr=$(this).attr("fax").split(",");
				for ( var i = 0; i < faxNosArr.length; i++) {
					if ($.trim($("#fo_to", window.parent.document).val()) != "")
						$("#fo_to", window.parent.document).val($("#fo_to", window.parent.document).val() + ";");
					if ($.trim($("#fax-hidden", window.parent.document).val()) != "")
						$("#fax-hidden", window.parent.document).val($("#fax-hidden", window.parent.document).val() + ";");
					
					if ($(this).attr("username") != "（未添加）") {
						/*if(faxNosArr.length==1)
							$("#fo_to", window.parent.document).val($("#fo_to", window.parent.document).val() + $(this).attr("username"));
						else*/
							$("#fo_to", window.parent.document).val($("#fo_to", window.parent.document).val() + $(this).attr("username") + "(" + faxNosArr[i] +")");
					} else {
						$("#fo_to", window.parent.document).val($("#fo_to", window.parent.document).val() + faxNosArr[i]);
					}
					//alert(formatTel(faxNosArr[i]));
					$("#fax-hidden", window.parent.document).val($("#fax-hidden", window.parent.document).val() + formatTelTextToValue(faxNosArr[i]));
				}
				
				
			});
}
function formatTelValueToText(tel) {
	var num = tel.replace(".","-");
	var telArray=tel.split("-");
	if(10 <= telArray[0].length && telArray[0].length <= 12){
		// 00853,00886,00852 not treated
		var regex1 = /^010[0-9]{7,8}/g;
		var regex2 = /^02[0-9]{8,9}/g; // 0207777777, 02988888888
		var regex3 = /^0[3-9][0-9]{9,10}/g; // 03947777777, 076912345678

		var regex1f = /010/g;
		var regex2f = /02[0-9]/g; // 020,021,022,023,024,025,027,028,029
		var regex3f = /0[3-9][0-9]{2}/g; // 0310,0769,0755,0999,...

		if (num.match(regex1)!=null) {
			num = num.replace(regex1f, "");
		} else if (num.match(regex2)!=null) {
			num = num.replace(regex2f, "");
		} else if (num.match(regex3)!=null) {
			num = num.replace(regex3f, "");
		}
		
	}
	//alert(num);
	var prefix = tel.substring(0, tel.length - num.length);
	//alert(prefix);
	if (prefix=="") {
		return  num;
	} else {
		return tel.substring(0, tel.length - num.length) + "-" + num;
	}
	/*// 00853,00886,00852 not treated
	var regex1 = /^010/g;
	var regex2 = /^02[0-9]{8,9}/g; // 0207777777, 02988888888
	var regex3 = /^0[3-9][0-9]{9,10}/g; // 03947777777, 076912345678

	var regex1f = /010/g;
	var regex2f = /02[0-9]/g; // 020,021,022,023,024,025,027,028,029
	var regex3f = /0[3-9][0-9]{2}/g; // 0310,0769,0755,0999,...

	//if (10 <= num.length && num.length <= 12) {
		num = num.replace(/010/g, "");
		num = num.replace(/02[0-9]/g, "");
		num = num.replace(/0[3-9][0-9]{2}/g, "");
	alert(num.match(regex1));
	alert(num.match(regex2));
	alert(num.match(regex3));
		if (num.match(regex1)!=null) {
			num = num.replace(regex1f, "");
		} else if (num.match(regex2)!=null) {
			num = num.replace(regex2f, "");
		} else if (num.match(regex3)!=null) {
			num = num.replace(regex3f, "");
		}
	//}
*/	
}
function formatTelTextToValue(tel){
	var telArray=tel.split("-");
	//无区号
	if(telArray[0].length > 5){
		return tel.replaceAll("-", ".", false);
	}
	//有区号
	else {
		//带分机号
		if(telArray.length >= 3){
			return telArray[0]+telArray[1]+"."+telArray[2];
		}
		//不带分机号
		else {
			return tel.replaceAll("-", "", false);
		}	
	}
	
}
/**
 * 添加人员
 */
function addPerson(fax_no) {

	var url = "contactEdit.act?gid=0&titleNum=add&fax_no=" + fax_no;
	// openEasyWin("winId","新增联系人",url,"600","355",true);
	// window.parent.parent.showShelter1('744', '536', url, "win");
	window.top.showContactEdit("新建联系人", 700, 500, url, function() {
		setTimeout(function() {
			$("#refresh_List").click();
		}, 500);
		// window.location.href = window.location.pathname + "?tab=recent";
	});

}
/**
 * 删除选定人员
 * 
 * @param nuaid
 */
function deletePerson(nuaid) {
	window.top.showConfirmBox("确定要删除吗？", function() {
		deleteContact(nuaid);
	});

}
/**
 * 编辑选定人员
 * 
 * @param nuaid
 */
function editPerson(nuaid) {
	var url = "contactEdit.act?nuaId=" + nuaid + "&titleNum=edit";
	// window.parent.parent.showShelter1("700", "540", url, "winId");
	window.top.showContactEdit("编辑联系人", 700, 500, url, function() {
		setTimeout(function() {
			$("#refresh_List").click();
		}, 500);
		// window.location.href = window.location.pathname + "?tab=all";
	});
}
/**
 * 查看人员
 * 
 * @param nuaid
 */
function showInfo(nuaid) {
	var url = "contactDetail.act?nuaId=" + nuaid;
	// window.parent.parent.showShelter1("600", "330", url, "winId");
	window.top.showIframeModal("查看人员", 600, 380, url);
}
/**
 * 查询输入框
 * 
 * @param dom
 * @param num
 */
function inputFun(dom, num) {
	if (num == 0)
		dom.value = "";
	if (num == 1 && dom.value == "")
		dom.value = "请输入姓名或传真号";
}
/**
 * 
 */
$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		$("#search").trigger("click");
	}
});
