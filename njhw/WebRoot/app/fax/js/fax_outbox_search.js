(function() {
	'use strict';

	var page_size = 25;
	var page_no = 1;
	var time_after = null;
	var time_before = null;

	var sid = null;
	var server_interface = "http://10.254.101.100/ipcom/index.php/Api/fo_download?";

	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("input[type=submit],input[type=button],input[type=reset],button").button().click(function(event) {
			// event.preventDefault();
		});
		// if ($.trim(getQueryString("start")) != "")
		time_after = getQueryString("start");
		time_before = getQueryString("end");
		
		$("#from").val(time_after);
		$("#to").val(time_before);
		$("#search")
				.click(
						function() {
							window.parent.frames["main_frame_right"].location.href = "fax_outbox_search.jsp?start="
									+ $("#from").val()
									+ "&end="
									+ $("#to").val();
						});
		init();
		//queryList();
		queryPagination();
	});
	/**
	 * 
	 */
	function init() {

		// post请求
		$.post(
		// 请求action
		"init.act", {
			// 参数设定
			page_size : page_size
		}, function(returnedData, textstatus) {
			// initList(returnedData);
			// alert(returnedData);
			if (returnedData.data == null) {
				window.parent.showMessageBox("登录失败！", function() {
					var url = _ctx + "/app/integrateservice/init.act";
					// window.open(url);
					window.parent.location.href = url;
				});
			}
			sid = "" + returnedData.data.session_id + "";
		}, "json");
	}
	/**
	 * 
	 */
	function queryList() {

		// post请求
		$.post(
		// 请求action
		"getSentFaxesList.act", {
			// 参数设定
			// is_read : '0',
			time_after : time_after,
			time_before : time_before,
			page_no : page_no,
		}, function(returnedData, textstatus) {
			createList(returnedData);
			// alert(returnedData);
		}, "json");
	}
	/**
	 * 
	 */
	function queryPagination() {

		// post请求
		$.post(
		// 请求action
		"getSentFaxesList.act", {
			// 参数设定
			// is_read : '0',
			time_after : time_after,
			time_before : time_before,
			page_no : page_no,
		}, function(returnedData, textstatus) {
			pagination(returnedData.data.total);
			// alert(returnedData);
		}, "json");
	}
	/**
	 * clean the user data table
	 * 
	 */
	function initList() {
		$("#fax_list_search").empty();
		// $("#pagination-account-user-container").hide();
	}
	/**
	 * 
	 */
	function createList(jsonArray) {
		initList();
		// 要替换的内容
		// 动态添加表格
		var tableLI = $(document.createElement("li"));
		var tableDIV = $(document.createElement("div"));

		var faxList = $("#fax_list_search");
		$("#fax_list_search_count").text(jsonArray.data.total);
		
		var jList = jsonArray.data.list;
		if (jList != null) {
			for ( var i = 0; i < jList.length; i++) {
				// 过滤已取消条目
				if (jList[i].status == 401)
					continue;
				var containerLI = tableLI.clone();
				var containerDIV = tableDIV.clone().addClass("main3_cen_sq");
				var subject = tableDIV.clone().addClass("tbody_outbox_col_1");
				var called = tableDIV.clone().addClass("tbody_outbox_col_2");
				var caller = tableDIV.clone().addClass("tbody_outbox_col_2").css("padding-left","5px");
				var created_on = tableDIV.clone()
						.addClass("tbody_outbox_col_2");
				var pages = tableDIV.clone().addClass("tbody_outbox_col_2");
				var download = tableDIV.clone().addClass("tbody_outbox_col_3");
				if ($.trim(jList[i].subject) != ""){
					var subjectStr = jList[i].subject.substr(0, 12);
					if(jList[i].subject.length > 12)
						subjectStr += "...";
					containerDIV.append(subject.html('<a  class="col1" title="'
							+ jList[i].subject + '">'
							+ subjectStr + '</a>'));
				}
				else {
					containerDIV.append(subject
							.html('<a class="col1" title="（无）">（无）</a>'));

				}
				
				if ($.trim(jList[i].caller) != "")
					containerDIV.append(caller.html('<span title="'+jList[i].caller+'">'+jList[i].caller+ '</span>'));
				else
					containerDIV.append(caller.text("（无）"));
				if ($.trim(jList[i].called) != "")
				{
					var calledStr = formatTelValueToText(jList[i].called).substr(0, 18);
					if(formatTelValueToText(jList[i].called).length > 18)
						calledStr += "...";
					containerDIV.append(called.html('<span title="'+formatTelValueToText(jList[i].called)+'">'+calledStr+ '</span>'));
				}
				else
					containerDIV.append(called.text("（无）"));
				containerDIV.append(created_on.text(jList[i].created_on));
				containerDIV.append(pages.css("color", jList[i].fg_color).attr(
						"title", jList[i].reason).html(jList[i].statusName));
				containerDIV.append(download.html('<a href="'
						+ server_interface + 'session_id=' + sid + '&id='
						+ jList[i].id + '" class="blue">[下载]</a>'));

				containerLI.append(containerDIV);
				faxList.append(containerLI);

			}

			if (jsonArray.length == 0) {

			} else {

			}
		}
		$("#fax_list_search").hide();
		$("#fax_list_search").fadeIn();
		// 控制父页面高度
		var parentBody = $("body", window.parent.document).height() - 220;
		if ($(".main_left_main1").height() > parentBody) {
			$("#main_frame_left", window.parent.document).height(
					$(".main_left_main1").height());
			$("#main_frame_right", window.parent.document).height(
					$(".main_left_main1").height());
		} else {
			$("#main_frame_left", window.parent.document).height(parentBody);
			$("#main_frame_right", window.parent.document).height(parentBody);
		}
	}
	
	function formatTelValueToText(tel) {
		var num = tel.replace(".","-");
		
		var telArray=num.split("-");
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
		var prefix = tel.substring(0, tel.length - num.length);
		if (prefix=="") {
			return  num;
		} else {
			return tel.substring(0, tel.length - num.length) + "-" + num;
		}
		
	}
	
	/**
	 * 
	 */
	function pagination(total) {
		// optional set
		pageNav.pre = "&lsaquo;";
		pageNav.next = "&rsaquo;";

		// p:current page number.
		// pn: page sum.
		pageNav.fn = function(p, pn) {
			// alert("Page:" + p + " of " + pn + " pages.");
			page_no = p;
			init();
			queryList();
			// document.getElementById("test").innerHTML ="Page:"+p+" of "+pn +
			// " pages.";
			// $("#test").text("Page:"+p+" of "+pn + " pages."); //for jquery
		};

		// goto the page 3 of 33.
		pageNav.go(page_no, Math.ceil(total / page_size));
	}
}()); // $(function()) 结束
