(function() {
	'use strict';
	var page_size = 25;
	var page_no = 1;
	var time_after = null;
	var time_before = null;

	var fax_list_today_count = 0;
	var fax_list_yesterday_count = 0;
	var fax_list_older_count = 0;

	var sid = null;
	var server_interface = "http://10.254.101.100/ipcom/index.php/Api/fo_download?";

	var refresh;

	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("input[type=submit],input[type=button],input[type=reset],button").button().click(function(event) {
			// event.preventDefault();
		});
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
		queryListInterval();
		//getCount();
		// 若有未完成邮件，持续刷新

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
			// alert(returnedData.data.session_id);
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
	function download(id) {

		// post请求
		$.post(
		// 请求action
		"foDownload.act", {
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
		"getSentFaxesList.act", {
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
		"getSentFaxesList.act", {
			// 参数设定
			// is_read : '0',
			// time_after : time_after,
			// time_before : time_before,
			page_no : page_no,
		}, function(returnedData, textstatus) {
			pagination(returnedData.data.total);
			// alert(returnedData);
		}, "json");

	}
	/**
	 * 
	 */
	function queryListInterval() {

		refresh = window.setInterval(function() {
			// alert(refresh);
			// post请求
			$.post(
			// 请求action
			"getSentFaxesList.act", {
				// 参数设定
				// is_read : '0',
				// time_after : time_after,
				// time_before : time_before,
				page_no : page_no,
			}, function(returnedData, textstatus) {
				createList(returnedData,false);
				// alert(returnedData);
			}, "json");

		}, 3000);

	}
	/**
	 * 
	 */
	function queryTodayListCount() {

		// post请求
		$.post(
		// 请求action
		"getSentFaxesList.act", {
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
		"getSentFaxesList.act", {
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
		"getSentFaxesList.act", {
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
	function createList(jsonArray, fadeIn) {
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
		var needRefresh = false;
		if (jList != null) {
			for ( var i = 0; i < jList.length; i++) {
				// 过滤已取消条目
				if (jList[i].status == 401)
					continue;
				// 判断是否需要自动刷新
				// jList[i].status == 0 || jList[i].status == 10 ||
				// jList[i].status == 100 || jList[i].status == 180

				if (jList[i].status == 0 || jList[i].status == 10
						|| jList[i].status == 100 || jList[i].status == 180) {
					needRefresh = true;
				}
				//
				var containerLI = tableLI.clone();
				var containerDIV = tableDIV.clone().addClass("main3_cen_sq");
				var subject = tableDIV.clone().addClass("tbody_outbox_col_1");
				var called = tableDIV.clone().addClass("tbody_outbox_col_2");
				var caller = tableDIV.clone().addClass("tbody_outbox_col_2").css("padding-left","5px");
				var created_on = tableDIV.clone()
						.addClass("tbody_outbox_col_2");
				var status = tableDIV.clone().addClass("tbody_outbox_col_2");
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
				{
					containerDIV.append(caller.html('<span title="'+jList[i].caller+'">'+jList[i].caller+ '</span>'));
				}
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
				containerDIV.append(status.css("color", jList[i].fg_color)
						.attr("title", jList[i].reason).html(
								jList[i].statusName));
				containerDIV.append(download.html('<a href="'
						+ server_interface + 'session_id=' + sid + '&id='
						+ jList[i].id + '" class="blue">[下载]</a>'));
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
		// document.body.clientHeight) {
		// $("#main_frame_left", window.parent.document).height(
		// window.parent.document.documentElement.clientHeight - 220);
		// $("#main_frame_right", window.parent.document).height(
		// window.parent.document.documentElement.clientHeight - 220);
		// } else {
		// $("#main_frame_left", window.parent.document).height(
		// document.body.clientHeight);
		// $("#main_frame_right", window.parent.document).height(
		// document.body.clientHeight);
		// }
		//
		// alert(needRefresh);
		if (!needRefresh) {
			window.clearInterval(refresh);
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
		//alert(total);
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
