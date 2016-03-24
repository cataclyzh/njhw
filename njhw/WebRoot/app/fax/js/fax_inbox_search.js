(function() {
	'use strict';
	var modalHeight = "617px";
	var modalWidth = "425px";
	var page_size = 25;
	var page_no = 1;
	var time_after = null;
	var time_before = null;
	var is_read = null;

	var sid = null;
	var server_interface = "http://10.254.101.100/ipcom/index.php/Api/fi_download?";

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
		is_read = getQueryString("is_read");
		
		$("#is_read").val(is_read);
		$("#from").val(time_after);
		$("#to").val(time_before);
		$("#search")
		.click(
				function() {
					window.parent.frames["main_frame_right"].location.href = "fax_inbox_search.jsp?is_read="
							+ $("#is_read").val()
							+ "&start="
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
		"getReceivedFaxesList.act", {
			// 参数设定
			is_read : is_read,
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
		"getReceivedFaxesList.act", {
			// 参数设定
			is_read : is_read,
			time_after : time_after,
			time_before : time_before,
			page_no : page_no,
		}, function(returnedData, textstatus) {
			pagination(returnedData.data.total);
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
				var containerLI = tableLI.clone();
				var containerDIV = tableDIV.clone().addClass("main3_cen_sq");
				var caller = tableDIV.clone().addClass("tbody_inbox_col_1");
				// var called = tableDIV.clone().addClass("tbody_inbox_col_2");
				var userName = tableDIV.clone().addClass("tbody_inbox_col_2").css("padding-left","5px");
				var created_on = tableDIV.clone().addClass("tbody_inbox_col_2");
				var pages = tableDIV.clone().addClass("tbody_inbox_col_2");
				var status = tableDIV.clone().addClass("tbody_inbox_col_2");
				var download = tableDIV.clone().addClass("tbody_inbox_col_3");

				containerDIV.append(caller.html("<a class='col1' title='"+jList[i].caller+"'>"
						+ jList[i].caller + "</a>"));
				// containerDIV.append(called.text(jList[i].called));
				if (jList[i].userName != null)
				{
					var userNameStr = jList[i].userName.substr(0, 10);
					if(jList[i].userName.length > 10)
						userNameStr += "...";
					containerDIV.append(userName.html('<span title="'+jList[i].userName+'">'+userNameStr+'</span>'));
				}
				else {
					containerDIV.append(userName.text("（无）"));

				}

				containerDIV.append(created_on.text(jList[i].created_on));
				containerDIV.append(pages.html(jList[i].pages + "页"));
				if (jList[i].status == "200") {
					if (jList[i].is_read == 1) {
						containerDIV.append(status
								.html('[<span class="grey">已读</span>]'));
					} else if (jList[i].is_read == 0) {
						containerDIV.append(status
								.html('[<span class="orange">未读</span>]'));
					}

					/*
					 * containerDIV .append(pages .html(jList[i].pages + "页" + '[<span
					 * class="main3_tab4"
					 * onClick="window.showModalDialog(\'fax_preview.jsp?id=' +
					 * jList[i].id +
					 * '\',\'title\',\'scrollbars=no;resizable=no;help=no;status=no;dialogHeight=' +
					 * modalHeight + ';dialogwidth=' + modalWidth + ';\');">预览</span>]'));
					 */
				} else {
					containerDIV
							.append(status.css("color", "red").attr("title",
									jList[i].reason).html(jList[i].statusName));
				}
				containerDIV
						.append(download
								.html('<a href="javascript:window.parent.showInboxPreview(\''
										+ sid
										+ '\',\''
										+ jList[i].id
										+ '\');">[预览]</a>'
										+ '<a href="'
										+ server_interface
										+ 'session_id='
										+ sid
										+ '&id='
										+ jList[i].id
										+ '" class="blue">[下载]</a>'));
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
