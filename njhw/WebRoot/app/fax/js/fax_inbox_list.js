(function() {
	'use strict';
	var modalHeight = "617px";
	var modalWidth = "425px";
	var page_size = 25;
	var page_no = 1;
	var time_after = null;
	var time_before = null;

	var fax_list_today_count = 0;
	var fax_list_yesterday_count = 0;
	var fax_list_older_count = 0;

	var sid = null;
	var server_interface = "http://10.254.101.100/ipcom/index.php/Api/fi_download?";

	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("input[type=submit],input[type=button],input[type=reset],button").button().click(function(event) {
			// event.preventDefault();
		});
		/*
		 * $("#from").datepicker({ defaultDate : "+1w", dateFormat : "yy-mm-dd",
		 * changeMonth : true, // changeYear : true, numberOfMonths : 1,
		 * showButtonPanel : true, onClose : function(selectedDate) {
		 * $("#to").datepicker("option", "minDate", selectedDate); } });
		 * $("#to").datepicker({ defaultDate : "+1w", dateFormat : "yy-mm-dd",
		 * changeMonth : true, // changeYear : true, numberOfMonths : 1,
		 * buttonImageOnly : true, showButtonPanel : true, onClose :
		 * function(selectedDate) { $("#from").datepicker("option", "maxDate",
		 * selectedDate); } });
		 */
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
		// queryList();
		queryPagination();
		// getCount();

	});
	function init() {

		// post请求
		$.post(
		// 请求action
		"init.act", {
			// 参数设定
			page_size : page_size
		}, function(returnedData, textstatus) {
			// initList(returnedData);
			//alert(returnedData.hasWebFaxNum);
			if (returnedData.data == null) {
				//登录失败
				window.parent.showMessageBox("登录失败！", function() {
					var url = _ctx + "/app/integrateservice/init.act";
					// window.open(url);
					window.parent.location.href = url;
				});
			}else if (!returnedData.hasWebFaxNum){
				//登录成功但没有网络传真号码
				window.parent.showMessageBox("您尚未分配网络传真号码，此功能暂时无法使用！", function() {
					var url = _ctx + "/app/integrateservice/init.act";
					window.parent.location.href = url;
				});
			}

			sid = "" + returnedData.data.session_id + "";
			// alert(sid);

		}, "json");
	}
	function download(id) {

		// post请求
		$.post(
		// 请求action
		"fiDownload.act", {
			// 参数设定
			id : id
		}, function(returnedData, textstatus) {
			// initList(returnedData);
			// alert(returnedData);
			alert(id);
		}, "json");
	}
	/**
	 * 
	 */
	function queryList(fadeIn) {

		// post请求
		$.post(
		// 请求action
		"getReceivedFaxesList.act", {
			// 参数设定
			// is_read : '0',
			// time_after : time_after,
			// time_before : time_before,
			page_no : page_no,
		}, function(returnedData, textstatus) {
			createList(returnedData,fadeIn);
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
			// is_read : '0',
			// time_after : GetDateStr(0),
			// time_before : GetDateStr(1),
			page_no : page_no,
		}, function(returnedData, textstatus) {
			pagination(returnedData.data.total);
		}, "json");
	}
	/**
	 * 
	 */
	function queryTodayListCount() {

		// post请求
		$.post(
		// 请求action
		"getReceivedFaxesList.act", {
			// 参数设定
			// is_read : '0',
			time_after : GetDateStr(0),
			time_before : GetDateStr(1),
			page_no : page_no,
		}, function(returnedData, textstatus) {
			$("#fax_list_today_count").text(returnedData.data.total);
		}, "json");
	}
	/**
	 * 
	 */
	function queryYesterdayListCount() {

		// post请求
		$.post(
		// 请求action
		"getReceivedFaxesList.act", {
			// 参数设定
			// is_read : '0',
			time_after : GetDateStr(-1),
			time_before : GetDateStr(0),
			page_no : page_no,
		}, function(returnedData, textstatus) {
			$("#fax_list_yesterday_count").text(returnedData.data.total);
		}, "json");
	}
	/**
	 * 
	 */
	function queryOlderListCount() {

		// post请求
		$.post(
		// 请求action
		"getReceivedFaxesList.act", {
			// 参数设定
			// is_read : '0',
			// time_after : t_a,
			time_before : GetDateStr(-1),
			page_no : page_no,
		}, function(returnedData, textstatus) {
			$("#fax_list_older_count").text(returnedData.data.total);
		}, "json");
	}

	/**
	 * clean the user data table
	 * 
	 */
	function initList() {
		$("#fax_list_today").empty();
		$("#fax_list_yesterday").empty();
		$("#fax_list_older").empty();

	}

	function getCount() {
		queryTodayListCount();
		queryYesterdayListCount();
		queryOlderListCount();

	}
	/**
	 * 
	 */
	function createList(jsonArray,fadeIn) {
		initList();
		// 要替换的内容
		// 动态添加表格
		var tableLI = $(document.createElement("li"));
		var tableDIV = $(document.createElement("div"));

		var faxTodayList = $("#fax_list_today");
		var faxYesterdayList = $("#fax_list_yesterday");
		var faxOlderList = $("#fax_list_older");

		fax_list_today_count = 0;
		fax_list_yesterday_count = 0;
		fax_list_older_count = 0;

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
				containerDIV.append(download
						.html('<a class="btn_preview" href="#" sid="' + sid
								+ '" id="' + jList[i].id + '">[预览]</a>'
								+ '<a href="' + server_interface
								+ 'session_id=' + sid + '&id=' + jList[i].id
								+ '" class="blue btn_download">[下载]</a>'));
				containerLI.append(containerDIV);

				var today = GetDateStr(0);
				var yesterday = GetDateStr(-1);
				var myDate = stringToDate(jList[i].created_on);
				if (myDate.Format("yyyy-M-d") == today) {
					faxTodayList.append(containerLI);
					fax_list_today_count++;
				} else if (myDate.Format("yyyy-M-d") == yesterday) {
					faxYesterdayList.append(containerLI);
					fax_list_yesterday_count++;
				} else {
					faxOlderList.append(containerLI);
					fax_list_older_count++;
				}

			}

			if (jsonArray.length == 0) {

			} else {

			}

		}
		$(".btn_preview").unbind("click").click(
				function() {

					window.top.showInboxPreview($(this).attr("sid"), $(this).attr("id"),function() {
						init();
						queryList();
					});	
				});
		$(".btn_download").unbind("click").click(function() {
			setTimeout(function() {
				init();
				queryList();
			}, 500);
		});

		if (fax_list_today_count == 0)
			$("#fax_list_today_container").hide();
		else {
			$("#fax_list_today_container").hide();
			if(fadeIn==null||fadeIn==true)
				$("#fax_list_today_container").fadeIn();
			else
				$("#fax_list_today_container").show();
		}
		if (fax_list_yesterday_count == 0)
			$("#fax_list_yesterday_container").hide();
		else {
			$("#fax_list_yesterday_container").hide();
			if(fadeIn==null||fadeIn==true)
				$("#fax_list_yesterday_container").fadeIn();
			else
				$("#fax_list_yesterday_container").show();
		}
		if (fax_list_older_count == 0)
			$("#fax_list_older_container").hide();
		else {
			$("#fax_list_older_container").hide();
			if(fadeIn==null||fadeIn==true)
				$("#fax_list_older_container").fadeIn();
			else
				$("#fax_list_older_container").show();
		}
		if (fax_list_today_count == 0 && fax_list_yesterday_count == 0
				&& fax_list_older_count == 0 && page_no == 1) {
			if(fadeIn==null||fadeIn==true)
				$("#fax_list_today_container").fadeIn();
			else
				$("#fax_list_today_container").show();
			if(fadeIn==null||fadeIn==true)
				$("#fax_list_yesterday_container").fadeIn();
			else
				$("#fax_list_yesterday_container").show();
			if(fadeIn==null||fadeIn==true)
				$("#fax_list_older_container").fadeIn();
			else
				$("#fax_list_older_container").show();

		}

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
		// if ((window.parent.document.documentElement.clientHeight - 220) >=
		// document.body.scrollHeight) {
		// // alert(1);
		// $("#main_frame_left", window.parent.document).height(
		// window.parent.document.body.scrollHeight - 200);
		// $("#main_frame_right", window.parent.document).height(
		// window.parent.document.body.scrollHeight - 200);
		// } else {
		// // alert(2);
		// $("#main_frame_left", window.parent.document).height(
		// $("#main_left_main1").height());
		// $("#main_frame_right", window.parent.document).height(
		// $("#main_left_main1").height());
		// }
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
