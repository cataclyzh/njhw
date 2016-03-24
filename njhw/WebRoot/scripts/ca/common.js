function isInt(b) {
	b = $.trim(b);
	var a = /^(0|(\-?)([1-9]{1}(\d*)))$/;
	return a.test(b)
}
document.onkeydown = function(c) {
	var d = window.event || c;
	var b = d.srcElement ? d.srcElement : d.target;
	var a = d.keyCode || d.which;
	if (a == 8 && (!(b.type == "textarea" || b.type == "text") || b.readOnly)) {
		d.returnValue = false;
		return false
	}
};
function changebasiccomponentstyle() {
	$("input").hover(function() {
		$(this).addClass("form_hover_label")
	}, function() {
		$(this).removeClass("form_hover_label")
	});
	$("textarea").hover(function() {
		$(this).addClass("form_hover_label")
	}, function() {
		$(this).removeClass("form_hover_label")
	})
}
function changeradiostyle() {
	$(":radio").hover(function() {
		$(this).addClass("radio_hover_label")
	}, function() {
		$(this).removeClass("radio_hover_label")
	})
}
function changeselectstyle() {
	$("select").hover(function() {
		$(this).css("background", "#ffffff")
	}, function() {
	})
}
function changedisplaytagstyle() {
	$(".table tbody tr").hover(function() {
		if ($(this).attr("class") == "odd") {
			$(this).attr("class", "oddhighlight")
		} else {
			if ($(this).attr("class") == "even") {
				$(this).attr("class", "evenhighlight")
			}
		}
	}, function() {
		if ($(this).attr("class") == "oddhighlight") {
			$(this).attr("class", "odd")
		} else {
			if ($(this).attr("class") == "evenhighlight") {
				$(this).attr("class", "even")
			}
		}
	})
}
function changecheckboxstyle() {
	var a = $("input:checked[name='_chk']");
	$.each(a, function(b, c) {
		if ($(this).parent().parent().attr("class") == "odd") {
			$(c).parent().parent().attr("class", "oddselected")
		}
		if ($(this).parent().parent().attr("class") == "even") {
			$(c).parent().parent().attr("class", "evenselected")
		}
	});
	$("#checkAll").click(function() {
		if ($(this).prop("checked") == true) {
			var b = $("input:checked[name='_chk']");
			$.each(b, function(c, d) {
				if ($(this).parent().parent().attr("class") == "odd") {
					$(d).parent().parent().attr("class", "oddselected")
				}
				if ($(this).parent().parent().attr("class") == "even") {
					$(d).parent().parent().attr("class", "evenselected")
				}
			})
		}
		if ($(this).prop("checked") == false) {
			var b = $(":checkbox");
			$.each(b, function(c, d) {
				if ($(this).parent().parent().attr("class") == "oddselected") {
					$(d).parent().parent().attr("class", "odd")
				}
				if ($(this).parent().parent().attr("class") == "evenselected") {
					$(d).parent().parent().attr("class", "even")
				}
			})
		}
	});
	$(":checkbox").click(function() {
		if ($(this).prop("checked") == true) {
			if ($(this).parent().parent().attr("class") == "oddselected") {
				$(this).parent().parent().attr("class", "odd")
			}
			if ($(this).parent().parent().attr("class") == "evenselected") {
				$(this).parent().parent().attr("class", "even")
			}
			if ($(this).parent().parent().attr("class") == "oddhighlight") {
				$(this).parent().parent().attr("class", "oddselected")
			}
			if ($(this).parent().parent().attr("class") == "evenhighlight") {
				$(this).parent().parent().attr("class", "evenselected")
			}
			if ($(this).parent().parent().attr("class") == "odd") {
				$(this).parent().parent().attr("class", "evenselected")
			}
			if ($(this).parent().parent().attr("class") == "even") {
				$(this).parent().parent().attr("class", "evenselected")
			}
		} else {
			$("#checkAll").attr("checked", false);
			if ($(this).parent().parent().attr("class") == "oddselected") {
				$(this).parent().parent().attr("class", "odd")
			}
			if ($(this).parent().parent().attr("class") == "evenselected") {
				$(this).parent().parent().attr("class", "even")
			}
		}
	})
}
function checkPageSize() {
	var a = $("#pageSize").val();
	if (!isInt(a) || a < 5 || a > 100) {
		easyAlert("提示", "您好!请在每页显示记录数中输入5-100之间的数字！", "warning");
		return false
	}
	return true
}
function checkJumpNumber(c) {
	var b = $("#pageSize").val();
	if (!isInt(b) || b < 5 || b > 100) {
		easyAlert("提示", "您好!请在每页显示记录数中输入5-100之间的数字！", "warning");
		return false
	}
	var a = $("#jumpNumber").val();
	if (typeof (a) != "undefined") {
		var d = $("#totalPages").val();
		if (!isInt(a)) {
			easyAlert("提示", "您好!跳转页数只能输入整数", "warning");
			return false
		}
		if (Number(a) < 1 || Number(a) > Number(d)) {
			easyAlert("提示", "跳转页数不在总页数之内，请重新输入!", "warning");
			return false
		}
	}
	jumpPage(c)
}
function jumpPage(a) {
	$("#pageNo").val(a);
	$("#queryForm").submit()
}
function querySubmit() {
	if (checkPageSize()) {
		$("#pageNo").val("1");
		$("#queryForm").submit()
	}
}
function replaceSpace(b) {
	var a = b.id;
	document.getElementById(a).value = document.getElementById(a).value
			.replace(/\s/ig, "")
}
function checkMw(a) {
	if (a == 84) {
		$("#108").hide();
		$("#84").show()
	}
	if (a == 108) {
		$("#84").hide();
		$("#108").show()
	}
}
function ToggelPanel(buttonId, tableId) {
	$("#" + buttonId).click(function() {
		if ($("#" + buttonId).attr("class") == "PANEL_OPEN_BUTTON") {
			$("#" + buttonId).removeClass();
			$("#" + buttonId).addClass("PANEL_CLOSE_BUTTON");
			$("#togglestatus").attr("value", "hide");
		} else if ($("#" + buttonId).attr("class") == "PANEL_CLOSE_BUTTON") {
			$("#" + buttonId).removeClass();
			$("#" + buttonId).addClass("PANEL_OPEN_BUTTON");
			$("#togglestatus").attr("value", "show");
		}
		;
		$("#" + tableId).toggle();
	});
	freshButton(buttonId, tableId);
}
function freshButton(buttonId, tableId) {
	if ($("#togglestatus").val() == "hide") {
		$("#" + buttonId).removeClass();
		$("#" + buttonId).addClass("PANEL_CLOSE_BUTTON");
		$("#" + tableId).toggle();
	}
}
function HidePanel() {
	$("#hide_table").hide()
}
function ShowPanel() {
	$("#hide_table").show()
}
function disableButton() {
	if (!($.browser.msie && parseInt($.browser.version) <= 6)) {
		$("a.easyui-linkbutton").linkbutton("disable")
	}
}
function enableButton() {
	if (!($.browser.msie && parseInt($.browser.version) <= 6)) {
		$("a.easyui-linkbutton").linkbutton("enable")
	}
}
function resetCheckboxForm(b) {
	var a = $("input:checked[name='_chk']");
	$.each(a, function(c, d) {
		if ($(this).parent().parent().attr("class") == "oddselected") {
			$(d).parent().parent().attr("class", "odd")
		}
		if ($(this).parent().parent().attr("class") == "evenselected") {
			$(d).parent().parent().attr("class", "even")
		}
	});
	$(b).resetForm()
}
jQuery.extend( {
	round2 : function(obj, fractionDigits) {
		number = obj.value.replace(/\s/ig, "");
		with (Math) {
			var new_number = round(number * pow(10, fractionDigits))
					/ pow(10, fractionDigits);
			if (!isNaN(new_number)) {
				obj.value = new_number.toFixed(fractionDigits).toString()
			} else {
				return
			}
		}
	}
});
jQuery.extend( {
	trunForcus : function() {
		$("input:text:first").focus();
		var a = $("input:text");
		a.bind("keydown", function(d) {
			var b = d.which;
			if (b == 13) {
				d.preventDefault();
				var c = a.index(this) + 1;
				$(":input:text:eq(" + c + ")").focus()
			}
		})
	}
});
jQuery.extend( {
	formatValue : function(e, a) {
		if (e.value.length < 8 && e.value.length > 0) {
			var d = "0";
			for ( var b = 1; b < (a - e.value.length); b++) {
				d = d + "0"
			}
			var c = d.concat(e.value);
			e.value = c
		}
	}
});
function getCheck() {
	var b = $("input:checked[name='_chk']");
	var a = 0;
	$.each(b, function(c, d) {
		a++
	});
	if (a < 1) {
		easyAlert("提示", "您好!至少选择一条记录!", "warning");
		return false
	}
	return true
}
jQuery.extend( {
	showLocale : function(f) {
		var h, a, d;
		var g = f.getYear();
		if (g < 1900) {
			g = g + 1900
		}
		var i = f.getMonth() + 1;
		if (i < 10) {
			i = "0" + i
		}
		var j = f.getDate();
		if (j < 10) {
			j = "0" + j
		}
		var b = f.getHours();
		if (b < 10) {
			b = "0" + b
		}
		var c = f.getMinutes();
		if (c < 10) {
			c = "0" + c
		}
		var k = f.getSeconds();
		if (k < 10) {
			k = "0" + k
		}
		var e = f.getDay();
		if (e == 0) {
			a = '<font color="#FF0000">'
		}
		if (e > 0 && e < 6) {
			a = '<font color="#373737">'
		}
		if (e == 6) {
			a = '<font color="#008000">'
		}
		if (e == 0) {
			e = "星期日"
		}
		if (e == 1) {
			e = "星期一"
		}
		if (e == 2) {
			e = "星期二"
		}
		if (e == 3) {
			e = "星期三"
		}
		if (e == 4) {
			e = "星期四"
		}
		if (e == 5) {
			e = "星期五"
		}
		if (e == 6) {
			e = "星期六"
		}
		d = "</font>";
		h = g + "-" + i + "-" + j + " " + b + ":" + c + ":" + k + "  " + e;
		return (h)
	}
});
jQuery.extend( {
	formatnumber : function(obj, fractionDigits) {
		number = obj.replace(/\s/ig, "");
		with (Math) {
			var new_number = round(number * pow(10, fractionDigits))
					/ pow(10, fractionDigits);
			if (!isNaN(new_number)) {
				obj = new_number.toFixed(fractionDigits).toString()
			}
			return obj
		}
	}
});
function easyAlert(d, c, b, a) {
	top.easyAlert(d, c, b, a)
}
function easyConfirm(c, b, a) {
	top.easyConfirm(c, b, a)
}
function openEasyWin(f, i, a, d, k, c, j) {
	var e = (screen.availWidth - d) / 2;
	var g = (screen.availHeight - k) / 2;
	var b = '<div id="'
			+ f
			+ '" class="easyui-window" modal="true"  collapsible="false" minimizable="false" maximizable="true" style="top:100px;300px;width:400px;height:300px;padding:0px;background:#fafafa;overflow:hidden;" closed="true" ><Iframe id="ifrmComWin" name="ifrmComWin" style="width:100%;height:100%;" src="'
			+ a + '"  frameborder="0"></iframe></div>';
	var h = window.top.$(b).appendTo(window.top.document.body);
	h.window( {
		title : i,
		showType : "slide",
		onClose : function() {
			h.window("destroy");
			if (typeof (c) != "undefined" && c != null) {
				if (c) {
					document.forms[0].submit()
				} else {
					if (typeof (j) != "undefined" && j != null) {
						document.getElementById(j).onclick()
					}
				}
			}
		},
		width : d,
		height : k,
		shadow : false,
		closed : false,
		top : g,
		left : e
	})
}
function openEasyWinForPost(f, i, a, d, k, c, j) {
	var e = (screen.availWidth - d) / 2;
	var g = (screen.availHeight - k) / 2;
	var b = '<div id="'
			+ f
			+ '" class="easyui-window" modal="true"  collapsible="false" minimizable="false" maximizable="true" style="top:100px;300px;width:400px;height:300px;padding:0px;background:#fafafa;overflow:hidden;" closed="true" ><Iframe id="ifrmComWin" name="ifrmComWin" style="width:100%;height:100%;"'
			+ '"  frameborder="0"></iframe></div>';
	var h = window.top.$(b).appendTo(window.top.document.body);
	
	h.window( {
		title : i,
		showType : "slide",
		onClose : function() {
			h.window("destroy");
			if (typeof (c) != "undefined" && c != null) {
				if (c) {
					document.forms[0].submit()
				} else {
					if (typeof (j) != "undefined" && j != null) {
						document.getElementById(j).onclick()
					}
				}
			}
		},
		onOpen : function() {
			$("#" + a).submit();
		},
		width : d,
		height : k,
		shadow : false,
		closed : false,
		top : g,
		left : e
	})
}
function closeEasyWin(a) {
	window.top.$("#" + a).window("close")
};
(function(a) {
	a.fn.chk_init = function(b) {
		a(b.all).click(function() {
			a(this).checkAll(b)
		});
		a(b.item).click(function() {
			if (a(b.item + ":checked").size() == a(b.item).size()) {
				a(b.all)[0].checked = true
			} else {
				a(b.all)[0].checked = false
			}
		})
	};
	a.fn.checkAll = function(b) {
		a(b.item).each(function(c) {
			this.checked = a(b.all)[0].checked
		})
	}
})(jQuery);
Calendar = function(d, c, f, a) {
	this.activeDiv = null;
	this.currentDateEl = null;
	this.getDateStatus = null;
	this.getDateToolTip = null;
	this.getDateText = null;
	this.timeout = null;
	this.onSelected = f || null;
	this.onClose = a || null;
	this.dragging = false;
	this.hidden = false;
	this.minYear = 1970;
	this.maxYear = 2050;
	this.dateFormat = Calendar._TT.DEF_DATE_FORMAT;
	this.ttDateFormat = Calendar._TT.TT_DATE_FORMAT;
	this.isPopup = true;
	this.weekNumbers = true;
	this.firstDayOfWeek = typeof d == "number" ? d : Calendar._FD;
	this.showsOtherMonths = false;
	this.dateStr = c;
	this.ar_days = null;
	this.showsTime = false;
	this.time24 = true;
	this.yearStep = 2;
	this.hiliteToday = true;
	this.multiple = null;
	this.table = null;
	this.element = null;
	this.tbody = null;
	this.firstdayname = null;
	this.monthsCombo = null;
	this.yearsCombo = null;
	this.hilitedMonth = null;
	this.activeMonth = null;
	this.hilitedYear = null;
	this.activeYear = null;
	this.dateClicked = false;
	if (typeof Calendar._SDN == "undefined") {
		if (typeof Calendar._SDN_len == "undefined") {
			Calendar._SDN_len = 3
		}
		var b = new Array();
		for ( var e = 8; e > 0;) {
			b[--e] = Calendar._DN[e].substr(0, Calendar._SDN_len)
		}
		Calendar._SDN = b;
		if (typeof Calendar._SMN_len == "undefined") {
			Calendar._SMN_len = 3
		}
		b = new Array();
		for ( var e = 12; e > 0;) {
			b[--e] = Calendar._MN[e].substr(0, Calendar._SMN_len)
		}
		Calendar._SMN = b
	}
};
Calendar._C = null;
Calendar.is_ie = (/msie/i.test(navigator.userAgent) && !/opera/i
		.test(navigator.userAgent));
Calendar.is_ie5 = (Calendar.is_ie && /msie 5\.0/i.test(navigator.userAgent));
Calendar.is_opera = /opera/i.test(navigator.userAgent);
Calendar.is_khtml = /Konqueror|Safari|KHTML/i.test(navigator.userAgent);
Calendar.getAbsolutePos = function(e) {
	var a = 0, d = 0;
	var c = /^div$/i.test(e.tagName);
	if (c && e.scrollLeft) {
		a = e.scrollLeft
	}
	if (c && e.scrollTop) {
		d = e.scrollTop
	}
	var f = {
		x : e.offsetLeft - a,
		y : e.offsetTop - d
	};
	if (e.offsetParent) {
		var b = this.getAbsolutePos(e.offsetParent);
		f.x += b.x;
		f.y += b.y
	}
	return f
};
Calendar.isRelated = function(c, a) {
	var d = a.relatedTarget;
	if (!d) {
		var b = a.type;
		if (b == "mouseover") {
			d = a.fromElement
		} else {
			if (b == "mouseout") {
				d = a.toElement
			}
		}
	}
	while (d) {
		if (d == c) {
			return true
		}
		d = d.parentNode
	}
	return false
};
Calendar.removeClass = function(e, d) {
	if (!(e && e.className)) {
		return
	}
	var a = e.className.split(" ");
	var b = new Array();
	for ( var c = a.length; c > 0;) {
		if (a[--c] != d) {
			b[b.length] = a[c]
		}
	}
	e.className = b.join(" ")
};
Calendar.addClass = function(b, a) {
	Calendar.removeClass(b, a);
	b.className += " " + a
};
Calendar.getElement = function(a) {
	var b = Calendar.is_ie ? window.event.srcElement : a.currentTarget;
	while (b.nodeType != 1 || /^div$/i.test(b.tagName)) {
		b = b.parentNode
	}
	return b
};
Calendar.getTargetElement = function(a) {
	var b = Calendar.is_ie ? window.event.srcElement : a.target;
	while (b.nodeType != 1) {
		b = b.parentNode
	}
	return b
};
Calendar.stopEvent = function(a) {
	a || (a = window.event);
	if (Calendar.is_ie) {
		a.cancelBubble = true;
		a.returnValue = false
	} else {
		a.preventDefault();
		a.stopPropagation()
	}
	return false
};
Calendar.addEvent = function(a, c, b) {
	if (a.attachEvent) {
		a.attachEvent("on" + c, b)
	} else {
		if (a.addEventListener) {
			a.addEventListener(c, b, true)
		} else {
			a["on" + c] = b
		}
	}
};
Calendar.removeEvent = function(a, c, b) {
	if (a.detachEvent) {
		a.detachEvent("on" + c, b)
	} else {
		if (a.removeEventListener) {
			a.removeEventListener(c, b, true)
		} else {
			a["on" + c] = null
		}
	}
};
Calendar.createElement = function(c, b) {
	var a = null;
	if (document.createElementNS) {
		a = document.createElementNS("http://www.w3.org/1999/xhtml", c)
	} else {
		a = document.createElement(c)
	}
	if (typeof b != "undefined") {
		b.appendChild(a)
	}
	return a
};
Calendar._add_evs = function(el) {
	with (Calendar) {
		addEvent(el, "mouseover", dayMouseOver);
		addEvent(el, "mousedown", dayMouseDown);
		addEvent(el, "mouseout", dayMouseOut);
		if (is_ie) {
			addEvent(el, "dblclick", dayMouseDblClick);
			el.setAttribute("unselectable", true)
		}
	}
};
Calendar.findMonth = function(a) {
	if (typeof a.month != "undefined") {
		return a
	} else {
		if (typeof a.parentNode.month != "undefined") {
			return a.parentNode
		}
	}
	return null
};
Calendar.findYear = function(a) {
	if (typeof a.year != "undefined") {
		return a
	} else {
		if (typeof a.parentNode.year != "undefined") {
			return a.parentNode
		}
	}
	return null
};
Calendar.showMonthsCombo = function() {
	var e = Calendar._C;
	if (!e) {
		return false
	}
	var e = e;
	var f = e.activeDiv;
	var d = e.monthsCombo;
	if (e.hilitedMonth) {
		Calendar.removeClass(e.hilitedMonth, "hilite")
	}
	if (e.activeMonth) {
		Calendar.removeClass(e.activeMonth, "active")
	}
	var c = e.monthsCombo.getElementsByTagName("div")[e.date.getMonth()];
	Calendar.addClass(c, "active");
	e.activeMonth = c;
	var b = d.style;
	b.display = "block";
	if (f.navtype < 0) {
		b.left = f.offsetLeft + "px"
	} else {
		var a = d.offsetWidth;
		if (typeof a == "undefined") {
			a = 50
		}
		b.left = (f.offsetLeft + f.offsetWidth - a) + "px"
	}
	b.top = (f.offsetTop + f.offsetHeight) + "px"
};
Calendar.showYearsCombo = function(d) {
	var a = Calendar._C;
	if (!a) {
		return false
	}
	var a = a;
	var c = a.activeDiv;
	var f = a.yearsCombo;
	if (a.hilitedYear) {
		Calendar.removeClass(a.hilitedYear, "hilite")
	}
	if (a.activeYear) {
		Calendar.removeClass(a.activeYear, "active")
	}
	a.activeYear = null;
	var b = a.date.getFullYear() + (d ? 1 : -1);
	var j = f.firstChild;
	var h = false;
	for ( var e = 12; e > 0; --e) {
		if (b >= a.minYear && b <= a.maxYear) {
			j.innerHTML = b;
			j.year = b;
			j.style.display = "block";
			h = true
		} else {
			j.style.display = "none"
		}
		j = j.nextSibling;
		b += d ? a.yearStep : -a.yearStep
	}
	if (h) {
		var k = f.style;
		k.display = "block";
		if (c.navtype < 0) {
			k.left = c.offsetLeft + "px"
		} else {
			var g = f.offsetWidth;
			if (typeof g == "undefined") {
				g = 50
			}
			k.left = (c.offsetLeft + c.offsetWidth - g) + "px"
		}
		k.top = (c.offsetTop + c.offsetHeight) + "px"
	}
};
Calendar.tableMouseUp = function(ev) {
	var cal = Calendar._C;
	if (!cal) {
		return false
	}
	if (cal.timeout) {
		clearTimeout(cal.timeout)
	}
	var el = cal.activeDiv;
	if (!el) {
		return false
	}
	var target = Calendar.getTargetElement(ev);
	ev || (ev = window.event);
	Calendar.removeClass(el, "active");
	if (target == el || target.parentNode == el) {
		Calendar.cellClick(el, ev)
	}
	var mon = Calendar.findMonth(target);
	var date = null;
	if (mon) {
		date = new Date(cal.date);
		if (mon.month != date.getMonth()) {
			date.setMonth(mon.month);
			cal.setDate(date);
			cal.dateClicked = false;
			cal.callHandler()
		}
	} else {
		var year = Calendar.findYear(target);
		if (year) {
			date = new Date(cal.date);
			if (year.year != date.getFullYear()) {
				date.setFullYear(year.year);
				cal.setDate(date);
				cal.dateClicked = false;
				cal.callHandler()
			}
		}
	}
	with (Calendar) {
		removeEvent(document, "mouseup", tableMouseUp);
		removeEvent(document, "mouseover", tableMouseOver);
		removeEvent(document, "mousemove", tableMouseOver);
		cal._hideCombos();
		_C = null;
		return stopEvent(ev)
	}
};
Calendar.tableMouseOver = function(n) {
	var a = Calendar._C;
	if (!a) {
		return
	}
	var c = a.activeDiv;
	var j = Calendar.getTargetElement(n);
	if (j == c || j.parentNode == c) {
		Calendar.addClass(c, "hilite active");
		Calendar.addClass(c.parentNode, "rowhilite")
	} else {
		if (typeof c.navtype == "undefined"
				|| (c.navtype != 50 && (c.navtype == 0 || Math.abs(c.navtype) > 2))) {
			Calendar.removeClass(c, "active")
		}
		Calendar.removeClass(c, "hilite");
		Calendar.removeClass(c.parentNode, "rowhilite")
	}
	n || (n = window.event);
	if (c.navtype == 50 && j != c) {
		var m = Calendar.getAbsolutePos(c);
		var p = c.offsetWidth;
		var o = n.clientX;
		var q;
		var l = true;
		if (o > m.x + p) {
			q = o - m.x - p;
			l = false
		} else {
			q = m.x - o
		}
		if (q < 0) {
			q = 0
		}
		var f = c._range;
		var h = c._current;
		var g = Math.floor(q / 10) % f.length;
		for ( var e = f.length; --e >= 0;) {
			if (f[e] == h) {
				break
			}
		}
		while (g-- > 0) {
			if (l) {
				if (--e < 0) {
					e = f.length - 1
				}
			} else {
				if (++e >= f.length) {
					e = 0
				}
			}
		}
		var b = f[e];
		c.innerHTML = b;
		a.onUpdateTime()
	}
	var d = Calendar.findMonth(j);
	if (d) {
		if (d.month != a.date.getMonth()) {
			if (a.hilitedMonth) {
				Calendar.removeClass(a.hilitedMonth, "hilite")
			}
			Calendar.addClass(d, "hilite");
			a.hilitedMonth = d
		} else {
			if (a.hilitedMonth) {
				Calendar.removeClass(a.hilitedMonth, "hilite")
			}
		}
	} else {
		if (a.hilitedMonth) {
			Calendar.removeClass(a.hilitedMonth, "hilite")
		}
		var k = Calendar.findYear(j);
		if (k) {
			if (k.year != a.date.getFullYear()) {
				if (a.hilitedYear) {
					Calendar.removeClass(a.hilitedYear, "hilite")
				}
				Calendar.addClass(k, "hilite");
				a.hilitedYear = k
			} else {
				if (a.hilitedYear) {
					Calendar.removeClass(a.hilitedYear, "hilite")
				}
			}
		} else {
			if (a.hilitedYear) {
				Calendar.removeClass(a.hilitedYear, "hilite")
			}
		}
	}
	return Calendar.stopEvent(n)
};
Calendar.tableMouseDown = function(a) {
	if (Calendar.getTargetElement(a) == Calendar.getElement(a)) {
		return Calendar.stopEvent(a)
	}
};
Calendar.calDragIt = function(b) {
	var c = Calendar._C;
	if (!(c && c.dragging)) {
		return false
	}
	var e;
	var d;
	if (Calendar.is_ie) {
		d = window.event.clientY + document.body.scrollTop;
		e = window.event.clientX + document.body.scrollLeft
	} else {
		e = b.pageX;
		d = b.pageY
	}
	c.hideShowCovered();
	var a = c.element.style;
	a.left = (e - c.xOffs) + "px";
	a.top = (d - c.yOffs) + "px";
	return Calendar.stopEvent(b)
};
Calendar.calDragEnd = function(ev) {
	var cal = Calendar._C;
	if (!cal) {
		return false
	}
	cal.dragging = false;
	with (Calendar) {
		removeEvent(document, "mousemove", calDragIt);
		removeEvent(document, "mouseup", calDragEnd);
		tableMouseUp(ev)
	}
	cal.hideShowCovered()
};
Calendar.dayMouseDown = function(ev) {
	var el = Calendar.getElement(ev);
	if (el.disabled) {
		return false
	}
	var cal = el.calendar;
	cal.activeDiv = el;
	Calendar._C = cal;
	if (el.navtype != 300) {
		with (Calendar) {
			if (el.navtype == 50) {
				el._current = el.innerHTML;
				addEvent(document, "mousemove", tableMouseOver)
			} else {
				addEvent(document, Calendar.is_ie5 ? "mousemove" : "mouseover",
						tableMouseOver)
			}
			addClass(el, "hilite active");
			addEvent(document, "mouseup", tableMouseUp)
		}
	} else {
		if (cal.isPopup) {
			cal._dragStart(ev)
		}
	}
	if (el.navtype == -1 || el.navtype == 1) {
		if (cal.timeout) {
			clearTimeout(cal.timeout)
		}
		cal.timeout = setTimeout("Calendar.showMonthsCombo()", 250)
	} else {
		if (el.navtype == -2 || el.navtype == 2) {
			if (cal.timeout) {
				clearTimeout(cal.timeout)
			}
			cal.timeout = setTimeout(
					(el.navtype > 0) ? "Calendar.showYearsCombo(true)"
							: "Calendar.showYearsCombo(false)", 250)
		} else {
			cal.timeout = null
		}
	}
	return Calendar.stopEvent(ev)
};
Calendar.dayMouseDblClick = function(a) {
	Calendar.cellClick(Calendar.getElement(a), a || window.event);
	if (Calendar.is_ie) {
		document.selection.empty()
	}
};
Calendar.dayMouseOver = function(b) {
	var a = Calendar.getElement(b);
	if (Calendar.isRelated(a, b) || Calendar._C || a.disabled) {
		return false
	}
	if (a.ttip) {
		if (a.ttip.substr(0, 1) == "_") {
			a.ttip = a.caldate.print(a.calendar.ttDateFormat)
					+ a.ttip.substr(1)
		}
		a.calendar.tooltips.innerHTML = a.ttip
	}
	if (a.navtype != 300) {
		Calendar.addClass(a, "hilite");
		if (a.caldate) {
			Calendar.addClass(a.parentNode, "rowhilite")
		}
	}
	return Calendar.stopEvent(b)
};
Calendar.dayMouseOut = function(ev) {
	with (Calendar) {
		var el = getElement(ev);
		if (isRelated(el, ev) || _C || el.disabled) {
			return false
		}
		removeClass(el, "hilite");
		if (el.caldate) {
			removeClass(el.parentNode, "rowhilite")
		}
		if (el.calendar) {
			el.calendar.tooltips.innerHTML = _TT.SEL_DATE
		}
		return stopEvent(ev)
	}
};
Calendar.cellClick = function(e, o) {
	var c = e.calendar;
	var h = false;
	var l = false;
	var f = null;
	if (typeof e.navtype == "undefined") {
		if (c.currentDateEl) {
			Calendar.removeClass(c.currentDateEl, "selected");
			Calendar.addClass(e, "selected");
			h = (c.currentDateEl == e);
			if (!h) {
				c.currentDateEl = e
			}
		}
		c.date.setDateOnly(e.caldate);
		f = c.date;
		var b = !(c.dateClicked = !e.otherMonth);
		if (!b && !c.currentDateEl) {
			c._toggleMultipleDate(new Date(f))
		} else {
			l = !e.disabled
		}
		if (b) {
			c._init(c.firstDayOfWeek, f)
		}
	} else {
		if (e.navtype == 200) {
			Calendar.removeClass(e, "hilite");
			c.callCloseHandler();
			return
		}
		f = new Date(c.date);
		if (e.navtype == 0) {
			f.setDateOnly(new Date())
		}
		c.dateClicked = false;
		var n = f.getFullYear();
		var g = f.getMonth();
		function a(q) {
			var r = f.getDate();
			var i = f.getMonthDays(q);
			if (r > i) {
				f.setDate(i)
			}
			f.setMonth(q)
		}
		switch (e.navtype) {
		case 400:
			Calendar.removeClass(e, "hilite");
			var p = Calendar._TT.ABOUT;
			if (typeof p != "undefined") {
				p += c.showsTime ? Calendar._TT.ABOUT_TIME : ""
			} else {
				p = 'Help and about box text is not translated into this language.\nIf you know this language and you feel generous please update\nthe corresponding file in "lang" subdir to match calendar-en.js\nand send it back to <mihai_bazon@yahoo.com> to get it into the distribution  ;-)\n\nThank you!\nhttp://dynarch.com/mishoo/calendar.epl\n'
			}
			alert(p);
			return;
		case -2:
			if (n > c.minYear) {
				f.setFullYear(n - 1)
			}
			break;
		case -1:
			if (g > 0) {
				a(g - 1)
			} else {
				if (n-- > c.minYear) {
					f.setFullYear(n);
					a(11)
				}
			}
			break;
		case 1:
			if (g < 11) {
				a(g + 1)
			} else {
				if (n < c.maxYear) {
					f.setFullYear(n + 1);
					a(0)
				}
			}
			break;
		case 2:
			if (n < c.maxYear) {
				f.setFullYear(n + 1)
			}
			break;
		case 100:
			c.setFirstDayOfWeek(e.fdow);
			return;
		case 50:
			var k = e._range;
			var m = e.innerHTML;
			for ( var j = k.length; --j >= 0;) {
				if (k[j] == m) {
					break
				}
			}
			if (o && o.shiftKey) {
				if (--j < 0) {
					j = k.length - 1
				}
			} else {
				if (++j >= k.length) {
					j = 0
				}
			}
			var d = k[j];
			e.innerHTML = d;
			c.onUpdateTime();
			return;
		case 0:
			if ((typeof c.getDateStatus == "function")
					&& c.getDateStatus(f, f.getFullYear(), f.getMonth(), f
							.getDate())) {
				return false
			}
			break
		}
		if (!f.equalsTo(c.date)) {
			c.setDate(f);
			l = true
		} else {
			if (e.navtype == 0) {
				l = h = true
			}
		}
	}
	if (l) {
		o && c.callHandler()
	}
	if (h) {
		Calendar.removeClass(e, "hilite");
		o && c.callCloseHandler()
	}
};
Calendar.prototype.create = function(n) {
	var m = null;
	if (!n) {
		m = document.getElementsByTagName("body")[0];
		this.isPopup = true
	} else {
		m = n;
		this.isPopup = false
	}
	this.date = this.dateStr ? new Date(this.dateStr) : new Date();
	var q = Calendar.createElement("table");
	this.table = q;
	q.cellSpacing = 0;
	q.cellPadding = 0;
	q.calendar = this;
	Calendar.addEvent(q, "mousedown", Calendar.tableMouseDown);
	var a = Calendar.createElement("div");
	this.element = a;
	a.className = "calendar";
	if (this.isPopup) {
		a.style.position = "absolute";
		a.style.display = "none"
	}
	a.appendChild(q);
	var k = Calendar.createElement("thead", q);
	var o = null;
	var r = null;
	var b = this;
	var e = function(s, j, i) {
		o = Calendar.createElement("td", r);
		o.colSpan = j;
		o.className = "button";
		if (i != 0 && Math.abs(i) <= 2) {
			o.className += " nav"
		}
		Calendar._add_evs(o);
		o.calendar = b;
		o.navtype = i;
		o.innerHTML = "<div unselectable='on'>" + s + "</div>";
		return o
	};
	r = Calendar.createElement("tr", k);
	var c = 6;
	(this.isPopup) && --c;
	(this.weekNumbers) && ++c;
	e("?", 1, 400).ttip = Calendar._TT.INFO;
	this.title = e("", c, 300);
	this.title.className = "title";
	if (this.isPopup) {
		this.title.ttip = Calendar._TT.DRAG_TO_MOVE;
		this.title.style.cursor = "move";
		e("&#x00d7;", 1, 200).ttip = Calendar._TT.CLOSE
	}
	r = Calendar.createElement("tr", k);
	r.className = "headrow";
	this._nav_py = e("&#x00ab;", 1, -2);
	this._nav_py.ttip = Calendar._TT.PREV_YEAR;
	this._nav_pm = e("&#x2039;", 1, -1);
	this._nav_pm.ttip = Calendar._TT.PREV_MONTH;
	this._nav_now = e(Calendar._TT.TODAY, this.weekNumbers ? 4 : 3, 0);
	this._nav_now.ttip = Calendar._TT.GO_TODAY;
	this._nav_nm = e("&#x203a;", 1, 1);
	this._nav_nm.ttip = Calendar._TT.NEXT_MONTH;
	this._nav_ny = e("&#x00bb;", 1, 2);
	this._nav_ny.ttip = Calendar._TT.NEXT_YEAR;
	r = Calendar.createElement("tr", k);
	r.className = "daynames";
	if (this.weekNumbers) {
		o = Calendar.createElement("td", r);
		o.className = "name wn";
		o.innerHTML = Calendar._TT.WK
	}
	for ( var h = 7; h > 0; --h) {
		o = Calendar.createElement("td", r);
		if (!h) {
			o.navtype = 100;
			o.calendar = this;
			Calendar._add_evs(o)
		}
	}
	this.firstdayname = (this.weekNumbers) ? r.firstChild.nextSibling
			: r.firstChild;
	this._displayWeekdays();
	var g = Calendar.createElement("tbody", q);
	this.tbody = g;
	for (h = 6; h > 0; --h) {
		r = Calendar.createElement("tr", g);
		if (this.weekNumbers) {
			o = Calendar.createElement("td", r)
		}
		for ( var f = 7; f > 0; --f) {
			o = Calendar.createElement("td", r);
			o.calendar = this;
			Calendar._add_evs(o)
		}
	}
	if (this.showsTime) {
		r = Calendar.createElement("tr", g);
		r.className = "time";
		o = Calendar.createElement("td", r);
		o.className = "time";
		o.colSpan = 2;
		o.innerHTML = Calendar._TT.TIME || "&nbsp;";
		o = Calendar.createElement("td", r);
		o.className = "time";
		o.colSpan = this.weekNumbers ? 4 : 3;
		(function() {
			function t(C, E, D, F) {
				var A = Calendar.createElement("span", o);
				A.className = C;
				A.innerHTML = E;
				A.calendar = b;
				A.ttip = Calendar._TT.TIME_PART;
				A.navtype = 50;
				A._range = [];
				if (typeof D != "number") {
					A._range = D
				} else {
					for ( var B = D; B <= F; ++B) {
						var z;
						if (B < 10 && F >= 10) {
							z = "0" + B
						} else {
							z = "" + B
						}
						A._range[A._range.length] = z
					}
				}
				Calendar._add_evs(A);
				return A
			}
			var x = b.date.getHours();
			var i = b.date.getMinutes();
			var y = !b.time24;
			var j = (x > 12);
			if (y && j) {
				x -= 12
			}
			var v = t("hour", x, y ? 1 : 0, y ? 12 : 23);
			var u = Calendar.createElement("span", o);
			u.innerHTML = ":";
			u.className = "colon";
			var s = t("minute", i, 0, 59);
			var w = null;
			o = Calendar.createElement("td", r);
			o.className = "time";
			o.colSpan = 2;
			if (y) {
				w = t("ampm", j ? "pm" : "am", [ "am", "pm" ])
			} else {
				o.innerHTML = "&nbsp;"
			}
			b.onSetTime = function() {
				var A, z = this.date.getHours(), B = this.date.getMinutes();
				if (y) {
					A = (z >= 12);
					if (A) {
						z -= 12
					}
					if (z == 0) {
						z = 12
					}
					w.innerHTML = A ? "pm" : "am"
				}
				v.innerHTML = (z < 10) ? ("0" + z) : z;
				s.innerHTML = (B < 10) ? ("0" + B) : B
			};
			b.onUpdateTime = function() {
				var A = this.date;
				var B = parseInt(v.innerHTML, 10);
				if (y) {
					if (/pm/i.test(w.innerHTML) && B < 12) {
						B += 12
					} else {
						if (/am/i.test(w.innerHTML) && B == 12) {
							B = 0
						}
					}
				}
				var C = A.getDate();
				var z = A.getMonth();
				var D = A.getFullYear();
				A.setHours(B);
				A.setMinutes(parseInt(s.innerHTML, 10));
				A.setFullYear(D);
				A.setMonth(z);
				A.setDate(C);
				this.dateClicked = false;
				this.callHandler()
			}
		})()
	} else {
		this.onSetTime = this.onUpdateTime = function() {
		}
	}
	var l = Calendar.createElement("tfoot", q);
	r = Calendar.createElement("tr", l);
	r.className = "footrow";
	o = e(Calendar._TT.SEL_DATE, this.weekNumbers ? 8 : 7, 300);
	o.className = "ttip";
	if (this.isPopup) {
		o.ttip = Calendar._TT.DRAG_TO_MOVE;
		o.style.cursor = "move"
	}
	this.tooltips = o;
	a = Calendar.createElement("div", this.element);
	this.monthsCombo = a;
	a.className = "combo";
	for (h = 0; h < Calendar._MN.length; ++h) {
		var d = Calendar.createElement("div");
		d.className = Calendar.is_ie ? "label-IEfix" : "label";
		d.month = h;
		d.innerHTML = Calendar._SMN[h];
		a.appendChild(d)
	}
	a = Calendar.createElement("div", this.element);
	this.yearsCombo = a;
	a.className = "combo";
	for (h = 12; h > 0; --h) {
		var p = Calendar.createElement("div");
		p.className = Calendar.is_ie ? "label-IEfix" : "label";
		a.appendChild(p)
	}
	this._init(this.firstDayOfWeek, this.date);
	m.appendChild(this.element)
};
Calendar._keyEvent = function(k) {
	var a = window._dynarch_popupCalendar;
	if (!a || a.multiple) {
		return false
	}
	(Calendar.is_ie) && (k = window.event);
	var i = (Calendar.is_ie || k.type == "keypress"), l = k.keyCode;
	if (k.ctrlKey) {
		switch (l) {
		case 37:
			i && Calendar.cellClick(a._nav_pm);
			break;
		case 38:
			i && Calendar.cellClick(a._nav_py);
			break;
		case 39:
			i && Calendar.cellClick(a._nav_nm);
			break;
		case 40:
			i && Calendar.cellClick(a._nav_ny);
			break;
		default:
			return false
		}
	} else {
		switch (l) {
		case 32:
			Calendar.cellClick(a._nav_now);
			break;
		case 27:
			i && a.callCloseHandler();
			break;
		case 37:
		case 38:
		case 39:
		case 40:
			if (i) {
				var e, m, j, g, c, d;
				e = l == 37 || l == 38;
				d = (l == 37 || l == 39) ? 1 : 7;
				function b() {
					c = a.currentDateEl;
					var n = c.pos;
					m = n & 15;
					j = n >> 4;
					g = a.ar_days[j][m]
				}
				b();
				function f() {
					var n = new Date(a.date);
					n.setDate(n.getDate() - d);
					a.setDate(n)
				}
				function h() {
					var n = new Date(a.date);
					n.setDate(n.getDate() + d);
					a.setDate(n)
				}
				while (1) {
					switch (l) {
					case 37:
						if (--m >= 0) {
							g = a.ar_days[j][m]
						} else {
							m = 6;
							l = 38;
							continue
						}
						break;
					case 38:
						if (--j >= 0) {
							g = a.ar_days[j][m]
						} else {
							f();
							b()
						}
						break;
					case 39:
						if (++m < 7) {
							g = a.ar_days[j][m]
						} else {
							m = 0;
							l = 40;
							continue
						}
						break;
					case 40:
						if (++j < a.ar_days.length) {
							g = a.ar_days[j][m]
						} else {
							h();
							b()
						}
						break
					}
					break
				}
				if (g) {
					if (!g.disabled) {
						Calendar.cellClick(g)
					} else {
						if (e) {
							f()
						} else {
							h()
						}
					}
				}
			}
			break;
		case 13:
			if (i) {
				Calendar.cellClick(a.currentDateEl, k)
			}
			break;
		default:
			return false
		}
	}
	return Calendar.stopEvent(k)
};
Calendar.prototype._init = function(n, x) {
	var w = new Date(), r = w.getFullYear(), z = w.getMonth(), b = w.getDate();
	this.table.style.visibility = "hidden";
	var h = x.getFullYear();
	if (h < this.minYear) {
		h = this.minYear;
		x.setFullYear(h)
	} else {
		if (h > this.maxYear) {
			h = this.maxYear;
			x.setFullYear(h)
		}
	}
	this.firstDayOfWeek = n;
	this.date = new Date(x);
	var y = x.getMonth();
	var B = x.getDate();
	var A = x.getMonthDays();
	x.setDate(1);
	var s = (x.getDay() - this.firstDayOfWeek) % 7;
	if (s < 0) {
		s += 7
	}
	x.setDate(-s);
	x.setDate(x.getDate() + 1);
	var e = this.tbody.firstChild;
	var l = Calendar._SMN[y];
	var p = this.ar_days = new Array();
	var o = Calendar._TT.WEEKEND;
	var d = this.multiple ? (this.datesCells = {}) : null;
	for ( var u = 0; u < 6; ++u, e = e.nextSibling) {
		var a = e.firstChild;
		if (this.weekNumbers) {
			a.className = "day wn";
			a.innerHTML = x.getWeekNumber();
			a = a.nextSibling
		}
		e.className = "daysrow";
		var v = false, f, c = p[u] = [];
		for ( var t = 0; t < 7; ++t, a = a.nextSibling, x.setDate(f + 1)) {
			f = x.getDate();
			var g = x.getDay();
			a.className = "day";
			a.pos = u << 4 | t;
			c[t] = a;
			var m = (x.getMonth() == y);
			if (!m) {
				if (this.showsOtherMonths) {
					a.className += " othermonth";
					a.otherMonth = true
				} else {
					a.className = "emptycell";
					a.innerHTML = "&nbsp;";
					a.disabled = true;
					continue
				}
			} else {
				a.otherMonth = false;
				v = true
			}
			a.disabled = false;
			a.innerHTML = this.getDateText ? this.getDateText(x, f) : f;
			if (d) {
				d[x.print("%Y%m%d")] = a
			}
			if (this.getDateStatus) {
				var q = this.getDateStatus(x, h, y, f);
				if (this.getDateToolTip) {
					var k = this.getDateToolTip(x, h, y, f);
					if (k) {
						a.title = k
					}
				}
				if (q === true) {
					a.className += " disabled";
					a.disabled = true
				} else {
					if (/disabled/i.test(q)) {
						a.disabled = true
					}
					a.className += " " + q
				}
			}
			if (!a.disabled) {
				a.caldate = new Date(x);
				a.ttip = "_";
				if (!this.multiple && m && f == B && this.hiliteToday) {
					a.className += " selected";
					this.currentDateEl = a
				}
				if (x.getFullYear() == r && x.getMonth() == z && f == b) {
					a.className += " today";
					a.ttip += Calendar._TT.PART_TODAY
				}
				if (o.indexOf(g.toString()) != -1) {
					a.className += a.otherMonth ? " oweekend" : " weekend"
				}
			}
		}
		if (!(v || this.showsOtherMonths)) {
			e.className = "emptyrow"
		}
	}
	this.title.innerHTML = Calendar._MN[y] + ", " + h;
	this.onSetTime();
	this.table.style.visibility = "visible";
	this._initMultipleDates()
};
Calendar.prototype._initMultipleDates = function() {
	if (this.multiple) {
		for ( var b in this.multiple) {
			var a = this.datesCells[b];
			var c = this.multiple[b];
			if (!c) {
				continue
			}
			if (a) {
				a.className += " selected"
			}
		}
	}
};
Calendar.prototype._toggleMultipleDate = function(b) {
	if (this.multiple) {
		var c = b.print("%Y%m%d");
		var a = this.datesCells[c];
		if (a) {
			var e = this.multiple[c];
			if (!e) {
				Calendar.addClass(a, "selected");
				this.multiple[c] = b
			} else {
				Calendar.removeClass(a, "selected");
				delete this.multiple[c]
			}
		}
	}
};
Calendar.prototype.setDateToolTipHandler = function(a) {
	this.getDateToolTip = a
};
Calendar.prototype.setDate = function(a) {
	if (!a.equalsTo(this.date)) {
		this._init(this.firstDayOfWeek, a)
	}
};
Calendar.prototype.refresh = function() {
	this._init(this.firstDayOfWeek, this.date)
};
Calendar.prototype.setFirstDayOfWeek = function(a) {
	this._init(a, this.date);
	this._displayWeekdays()
};
Calendar.prototype.setDateStatusHandler = Calendar.prototype.setDisabledHandler = function(
		a) {
	this.getDateStatus = a
};
Calendar.prototype.setRange = function(b, c) {
	this.minYear = b;
	this.maxYear = c
};
Calendar.prototype.callHandler = function() {
	if (this.onSelected) {
		this.onSelected(this, this.date.print(this.dateFormat))
	}
};
Calendar.prototype.callCloseHandler = function() {
	if (this.onClose) {
		this.onClose(this)
	}
	this.hideShowCovered()
};
Calendar.prototype.destroy = function() {
	var a = this.element.parentNode;
	a.removeChild(this.element);
	Calendar._C = null;
	window._dynarch_popupCalendar = null
};
Calendar.prototype.reparent = function(b) {
	var a = this.element;
	a.parentNode.removeChild(a);
	b.appendChild(a)
};
Calendar._checkCalendar = function(b) {
	var c = window._dynarch_popupCalendar;
	if (!c) {
		return false
	}
	var a = Calendar.is_ie ? Calendar.getElement(b) : Calendar
			.getTargetElement(b);
	for (; a != null && a != c.element; a = a.parentNode) {
	}
	if (a == null) {
		window._dynarch_popupCalendar.callCloseHandler();
		return Calendar.stopEvent(b)
	}
};
Calendar.prototype.show = function() {
	var e = this.table.getElementsByTagName("tr");
	for ( var d = e.length; d > 0;) {
		var f = e[--d];
		Calendar.removeClass(f, "rowhilite");
		var c = f.getElementsByTagName("td");
		for ( var b = c.length; b > 0;) {
			var a = c[--b];
			Calendar.removeClass(a, "hilite");
			Calendar.removeClass(a, "active")
		}
	}
	this.element.style.display = "block";
	this.hidden = false;
	if (this.isPopup) {
		window._dynarch_popupCalendar = this;
		Calendar.addEvent(document, "keydown", Calendar._keyEvent);
		Calendar.addEvent(document, "keypress", Calendar._keyEvent);
		Calendar.addEvent(document, "mousedown", Calendar._checkCalendar)
	}
	this.hideShowCovered()
};
Calendar.prototype.hide = function() {
	if (this.isPopup) {
		Calendar.removeEvent(document, "keydown", Calendar._keyEvent);
		Calendar.removeEvent(document, "keypress", Calendar._keyEvent);
		Calendar.removeEvent(document, "mousedown", Calendar._checkCalendar)
	}
	this.element.style.display = "none";
	this.hidden = true;
	this.hideShowCovered()
};
Calendar.prototype.showAt = function(a, c) {
	var b = this.element.style;
	b.left = a + "px";
	b.top = c + "px";
	this.show()
};
Calendar.prototype.showAtElement = function(c, d) {
	var a = this;
	var e = Calendar.getAbsolutePos(c);
	if (!d || typeof d != "string") {
		this.showAt(e.x, e.y + c.offsetHeight);
		return true
	}
	function b(i) {
		if (i.x < 0) {
			i.x = 0
		}
		if (i.y < 0) {
			i.y = 0
		}
		var j = document.createElement("div");
		var h = j.style;
		h.position = "absolute";
		h.right = h.bottom = h.width = h.height = "0px";
		document.body.appendChild(j);
		var g = Calendar.getAbsolutePos(j);
		document.body.removeChild(j);
		if (Calendar.is_ie) {
			g.y += document.body.scrollTop;
			g.x += document.body.scrollLeft
		} else {
			g.y += window.scrollY;
			g.x += window.scrollX
		}
		var f = i.x + i.width - g.x;
		if (f > 0) {
			i.x -= f
		}
		f = i.y + i.height - g.y;
		if (f > 0) {
			i.y -= f
		}
	}
	this.element.style.display = "block";
	Calendar.continuation_for_the_fucking_khtml_browser = function() {
		var f = a.element.offsetWidth;
		var i = a.element.offsetHeight;
		a.element.style.display = "none";
		var g = d.substr(0, 1);
		var j = "l";
		if (d.length > 1) {
			j = d.substr(1, 1)
		}
		switch (g) {
		case "T":
			e.y -= i;
			break;
		case "B":
			e.y += c.offsetHeight;
			break;
		case "C":
			e.y += (c.offsetHeight - i) / 2;
			break;
		case "t":
			e.y += c.offsetHeight - i;
			break;
		case "b":
			break
		}
		switch (j) {
		case "L":
			e.x -= f;
			break;
		case "R":
			e.x += c.offsetWidth;
			break;
		case "C":
			e.x += (c.offsetWidth - f) / 2;
			break;
		case "l":
			e.x += c.offsetWidth - f;
			break;
		case "r":
			break
		}
		e.width = f;
		e.height = i + 40;
		a.monthsCombo.style.display = "none";
		b(e);
		a.showAt(e.x, e.y)
	};
	if (Calendar.is_khtml) {
		setTimeout("Calendar.continuation_for_the_fucking_khtml_browser()", 10)
	} else {
		Calendar.continuation_for_the_fucking_khtml_browser()
	}
};
Calendar.prototype.setDateFormat = function(a) {
	this.dateFormat = a
};
Calendar.prototype.setTtDateFormat = function(a) {
	this.ttDateFormat = a
};
Calendar.prototype.parseDate = function(b, a) {
	if (!a) {
		a = this.dateFormat
	}
	this.setDate(Date.parseDate(b, a))
};
Calendar.prototype.hideShowCovered = function() {
	if (!Calendar.is_ie && !Calendar.is_opera) {
		return
	}
	function b(k) {
		var i = k.style.visibility;
		if (!i) {
			if (document.defaultView
					&& typeof (document.defaultView.getComputedStyle) == "function") {
				if (!Calendar.is_khtml) {
					i = document.defaultView.getComputedStyle(k, "")
							.getPropertyValue("visibility")
				} else {
					i = ""
				}
			} else {
				if (k.currentStyle) {
					i = k.currentStyle.visibility
				} else {
					i = ""
				}
			}
		}
		return i
	}
	var s = new Array("applet", "iframe", "select");
	var c = this.element;
	var a = Calendar.getAbsolutePos(c);
	var f = a.x;
	var d = c.offsetWidth + f;
	var r = a.y;
	var q = c.offsetHeight + r;
	for ( var h = s.length; h > 0;) {
		var g = document.getElementsByTagName(s[--h]);
		var e = null;
		for ( var l = g.length; l > 0;) {
			e = g[--l];
			a = Calendar.getAbsolutePos(e);
			var o = a.x;
			var n = e.offsetWidth + o;
			var m = a.y;
			var j = e.offsetHeight + m;
			if (this.hidden || (o > d) || (n < f) || (m > q) || (j < r)) {
				if (!e.__msh_save_visibility) {
					e.__msh_save_visibility = b(e)
				}
				e.style.visibility = e.__msh_save_visibility
			} else {
				if (!e.__msh_save_visibility) {
					e.__msh_save_visibility = b(e)
				}
				e.style.visibility = "hidden"
			}
		}
	}
};
Calendar.prototype._displayWeekdays = function() {
	var b = this.firstDayOfWeek;
	var a = this.firstdayname;
	var d = Calendar._TT.WEEKEND;
	for ( var c = 0; c < 7; ++c) {
		a.className = "day name";
		var e = (c + b) % 7;
		if (c) {
			a.ttip = Calendar._TT.DAY_FIRST.replace("%s", Calendar._DN[e]);
			a.navtype = 100;
			a.calendar = this;
			a.fdow = e;
			Calendar._add_evs(a)
		}
		if (d.indexOf(e.toString()) != -1) {
			Calendar.addClass(a, "weekend")
		}
		a.innerHTML = Calendar._SDN[(c + b) % 7];
		a = a.nextSibling
	}
};
Calendar.prototype._hideCombos = function() {
	this.monthsCombo.style.display = "none";
	this.yearsCombo.style.display = "none"
};
Calendar.prototype._dragStart = function(ev) {
	if (this.dragging) {
		return
	}
	this.dragging = true;
	var posX;
	var posY;
	if (Calendar.is_ie) {
		posY = window.event.clientY + document.body.scrollTop;
		posX = window.event.clientX + document.body.scrollLeft
	} else {
		posY = ev.clientY + window.scrollY;
		posX = ev.clientX + window.scrollX
	}
	var st = this.element.style;
	this.xOffs = posX - parseInt(st.left);
	this.yOffs = posY - parseInt(st.top);
	with (Calendar) {
		addEvent(document, "mousemove", calDragIt);
		addEvent(document, "mouseup", calDragEnd)
	}
};
Date._MD = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
Date.SECOND = 1000;
Date.MINUTE = 60 * Date.SECOND;
Date.HOUR = 60 * Date.MINUTE;
Date.DAY = 24 * Date.HOUR;
Date.WEEK = 7 * Date.DAY;
Date.parseDate = function(l, c) {
	var n = new Date();
	var o = 0;
	var e = -1;
	var k = 0;
	var q = l.split(/\W+/);
	var p = c.match(/%./g);
	var h = 0, g = 0;
	var r = 0;
	var f = 0;
	for (h = 0; h < q.length; ++h) {
		if (!q[h]) {
			continue
		}
		switch (p[h]) {
		case "%d":
		case "%e":
			k = parseInt(q[h], 10);
			break;
		case "%m":
			e = parseInt(q[h], 10) - 1;
			break;
		case "%Y":
		case "%y":
			o = parseInt(q[h], 10);
			(o < 100) && (o += (o > 29) ? 1900 : 2000);
			break;
		case "%b":
		case "%B":
			for (g = 0; g < 12; ++g) {
				if (Calendar._MN[g].substr(0, q[h].length).toLowerCase() == q[h]
						.toLowerCase()) {
					e = g;
					break
				}
			}
			break;
		case "%H":
		case "%I":
		case "%k":
		case "%l":
			r = parseInt(q[h], 10);
			break;
		case "%P":
		case "%p":
			if (/pm/i.test(q[h]) && r < 12) {
				r += 12
			} else {
				if (/am/i.test(q[h]) && r >= 12) {
					r -= 12
				}
			}
			break;
		case "%M":
			f = parseInt(q[h], 10);
			break
		}
	}
	if (isNaN(o)) {
		o = n.getFullYear()
	}
	if (isNaN(e)) {
		e = n.getMonth()
	}
	if (isNaN(k)) {
		k = n.getDate()
	}
	if (isNaN(r)) {
		r = n.getHours()
	}
	if (isNaN(f)) {
		f = n.getMinutes()
	}
	if (o != 0 && e != -1 && k != 0) {
		return new Date(o, e, k, r, f, 0)
	}
	o = 0;
	e = -1;
	k = 0;
	for (h = 0; h < q.length; ++h) {
		if (q[h].search(/[a-zA-Z]+/) != -1) {
			var s = -1;
			for (g = 0; g < 12; ++g) {
				if (Calendar._MN[g].substr(0, q[h].length).toLowerCase() == q[h]
						.toLowerCase()) {
					s = g;
					break
				}
			}
			if (s != -1) {
				if (e != -1) {
					k = e + 1
				}
				e = s
			}
		} else {
			if (parseInt(q[h], 10) <= 12 && e == -1) {
				e = q[h] - 1
			} else {
				if (parseInt(q[h], 10) > 31 && o == 0) {
					o = parseInt(q[h], 10);
					(o < 100) && (o += (o > 29) ? 1900 : 2000)
				} else {
					if (k == 0) {
						k = q[h]
					}
				}
			}
		}
	}
	if (o == 0) {
		o = n.getFullYear()
	}
	if (e != -1 && k != 0) {
		return new Date(o, e, k, r, f, 0)
	}
	return n
};
Date.prototype.getMonthDays = function(b) {
	var a = this.getFullYear();
	if (typeof b == "undefined") {
		b = this.getMonth()
	}
	if (((0 == (a % 4)) && ((0 != (a % 100)) || (0 == (a % 400)))) && b == 1) {
		return 29
	} else {
		return Date._MD[b]
	}
};
Date.prototype.getDayOfYear = function() {
	var a = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0,
			0);
	var c = new Date(this.getFullYear(), 0, 0, 0, 0, 0);
	var b = a - c;
	return Math.floor(b / Date.DAY)
};
Date.prototype.getWeekNumber = function() {
	var c = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0,
			0);
	var b = c.getDay();
	c.setDate(c.getDate() - (b + 6) % 7 + 3);
	var a = c.valueOf();
	c.setMonth(0);
	c.setDate(4);
	return Math.round((a - c.valueOf()) / (7 * 86400000)) + 1
};
Date.prototype.equalsTo = function(a) {
	return ((this.getFullYear() == a.getFullYear())
			&& (this.getMonth() == a.getMonth())
			&& (this.getDate() == a.getDate())
			&& (this.getHours() == a.getHours()) && (this.getMinutes() == a
			.getMinutes()))
};
Date.prototype.setDateOnly = function(a) {
	var b = new Date(a);
	this.setDate(1);
	this.setFullYear(b.getFullYear());
	this.setMonth(b.getMonth());
	this.setDate(b.getDate())
};
Date.prototype.print = function(l) {
	var b = this.getMonth();
	var k = this.getDate();
	var n = this.getFullYear();
	var p = this.getWeekNumber();
	var q = this.getDay();
	var v = {};
	var r = this.getHours();
	var c = (r >= 12);
	var h = (c) ? (r - 12) : r;
	var u = this.getDayOfYear();
	if (h == 0) {
		h = 12
	}
	var e = this.getMinutes();
	var j = this.getSeconds();
	v["%a"] = Calendar._SDN[q];
	v["%A"] = Calendar._DN[q];
	v["%b"] = Calendar._SMN[b];
	v["%B"] = Calendar._MN[b];
	v["%C"] = 1 + Math.floor(n / 100);
	v["%d"] = (k < 10) ? ("0" + k) : k;
	v["%e"] = k;
	v["%H"] = (r < 10) ? ("0" + r) : r;
	v["%I"] = (h < 10) ? ("0" + h) : h;
	v["%j"] = (u < 100) ? ((u < 10) ? ("00" + u) : ("0" + u)) : u;
	v["%k"] = r;
	v["%l"] = h;
	v["%m"] = (b < 9) ? ("0" + (1 + b)) : (1 + b);
	v["%M"] = (e < 10) ? ("0" + e) : e;
	v["%n"] = "\n";
	v["%p"] = c ? "PM" : "AM";
	v["%P"] = c ? "pm" : "am";
	v["%s"] = Math.floor(this.getTime() / 1000);
	v["%S"] = (j < 10) ? ("0" + j) : j;
	v["%t"] = "\t";
	v["%U"] = v["%W"] = v["%V"] = (p < 10) ? ("0" + p) : p;
	v["%u"] = q + 1;
	v["%w"] = q;
	v["%y"] = ("" + n).substr(2, 2);
	v["%Y"] = n;
	v["%%"] = "%";
	var t = /%./g;
	if (!Calendar.is_ie5 && !Calendar.is_khtml) {
		return l.replace(t, function(a) {
			return v[a] || a
		})
	}
	var o = l.match(t);
	for ( var g = 0; g < o.length; g++) {
		var f = v[o[g]];
		if (f) {
			t = new RegExp(o[g], "g");
			l = l.replace(t, f)
		}
	}
	return l
};
Date.prototype.__msh_oldSetFullYear = Date.prototype.setFullYear;
Date.prototype.setFullYear = function(b) {
	var a = new Date(this);
	a.__msh_oldSetFullYear(b);
	if (a.getMonth() != this.getMonth()) {
		this.setDate(28)
	}
	this.__msh_oldSetFullYear(b)
};
window._dynarch_popupCalendar = null;
Calendar.setup = function(g) {
	function f(h, i) {
		if (typeof g[h] == "undefined") {
			g[h] = i
		}
	}
	f("inputField", null);
	f("displayArea", null);
	f("button", null);
	f("eventName", "click");
	f("ifFormat", "%Y/%m/%d");
	f("daFormat", "%Y/%m/%d");
	f("singleClick", true);
	f("disableFunc", null);
	f("dateStatusFunc", g.disableFunc);
	f("dateText", null);
	f("firstDay", null);
	f("align", "Br");
	f("range", [ 1900, 2999 ]);
	f("weekNumbers", false);
	f("flat", null);
	f("flatCallback", null);
	f("onSelect", null);
	f("onClose", null);
	f("onUpdate", null);
	f("date", null);
	f("showsTime", false);
	f("timeFormat", "24");
	f("electric", true);
	f("step", 2);
	f("position", null);
	f("cache", false);
	f("showOthers", false);
	f("multiple", null);
	var c = [ "inputField", "displayArea", "button" ];
	for ( var b in c) {
		if (typeof g[c[b]] == "string") {
			g[c[b]] = document.getElementById(g[c[b]])
		}
	}
	if (!(g.flat || g.multiple || g.inputField || g.displayArea || g.button)) {
		alert("Calendar.setup:\n  Nothing to setup (no fields found).  Please check your code");
		return false
	}
	function a(i) {
		var h = i.params;
		var j = (i.dateClicked || h.electric);
		if (j && h.inputField) {
			h.inputField.value = i.date.print(h.ifFormat);
			if (typeof h.inputField.onchange == "function") {
				h.inputField.onchange()
			}
		}
		if (j && h.displayArea) {
			h.displayArea.innerHTML = i.date.print(h.daFormat)
		}
		if (j && typeof h.onUpdate == "function") {
			h.onUpdate(i)
		}
		if (j && h.flat) {
			if (typeof h.flatCallback == "function") {
				h.flatCallback(i)
			}
		}
		if (j && h.singleClick && i.dateClicked) {
			i.callCloseHandler()
		}
	}
	if (g.flat != null) {
		if (typeof g.flat == "string") {
			g.flat = document.getElementById(g.flat)
		}
		if (!g.flat) {
			alert("Calendar.setup:\n  Flat specified but can't find parent.");
			return false
		}
		var e = new Calendar(g.firstDay, g.date, g.onSelect || a);
		e.showsOtherMonths = g.showOthers;
		e.showsTime = g.showsTime;
		e.time24 = (g.timeFormat == "24");
		e.params = g;
		e.weekNumbers = g.weekNumbers;
		e.setRange(g.range[0], g.range[1]);
		e.setDateStatusHandler(g.dateStatusFunc);
		e.getDateText = g.dateText;
		if (g.ifFormat) {
			e.setDateFormat(g.ifFormat)
		}
		if (g.inputField && typeof g.inputField.value == "string") {
			e.parseDate(g.inputField.value)
		}
		e.create(g.flat);
		e.show();
		return false
	}
	var d = g.button || g.displayArea || g.inputField;
	d["on" + g.eventName] = function() {
		var h = g.inputField || g.displayArea;
		var k = g.inputField ? g.ifFormat : g.daFormat;
		var o = false;
		var m = window.calendar;
		if (h) {
			g.date = Date.parseDate(h.value || h.innerHTML, k)
		}
		if (!(m && g.cache)) {
			window.calendar = m = new Calendar(g.firstDay, g.date, g.onSelect
					|| a, g.onClose || function(i) {
				i.hide()
			});
			m.showsTime = g.showsTime;
			m.time24 = (g.timeFormat == "24");
			m.weekNumbers = g.weekNumbers;
			o = true
		} else {
			if (g.date) {
				m.setDate(g.date)
			}
			m.hide()
		}
		if (g.multiple) {
			m.multiple = {};
			for ( var j = g.multiple.length; --j >= 0;) {
				var n = g.multiple[j];
				var l = n.print("%Y%m%d");
				m.multiple[l] = n
			}
		}
		m.showsOtherMonths = g.showOthers;
		m.yearStep = g.step;
		m.setRange(g.range[0], g.range[1]);
		m.params = g;
		m.setDateStatusHandler(g.dateStatusFunc);
		m.getDateText = g.dateText;
		m.setDateFormat(k);
		if (o) {
			m.create()
		}
		m.refresh();
		if (!g.position) {
			m.showAtElement(g.button || g.displayArea || g.inputField, g.align)
		} else {
			m.showAt(g.position[0], g.position[1])
		}
		return false
	};
	return e
};
Calendar._DN = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日");
Calendar._SDN = new Array("日", "一", "二", "三", "四", "五", "六", "日");
Calendar._MN = new Array("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月",
		"十月", "十一月", "十二月");
Calendar._SMN = new Array("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月",
		"十月", "十一月", "十二月");
Calendar._TT = {};
Calendar._TT.INFO = "帮助";
Calendar._TT.ABOUT = "选择日期:\n- 点击 \xab, \xbb 按钮选择年份\n- 点击 "
		+ String.fromCharCode(8249) + ", " + String.fromCharCode(8250)
		+ " 按钮选择月份\n- 长按以上按钮可从菜单中快速选择年份或月份";
Calendar._TT.ABOUT_TIME = "\n\n选择时间:\n- 点击小时或分钟可使改数值加一\n- 按住Shift键点击小时或分钟可使改数值减一\n- 点击拖动鼠标可进行快速选择";
Calendar._TT.PREV_YEAR = "上一年 (按住出菜单)";
Calendar._TT.PREV_MONTH = "上一月 (按住出菜单)";
Calendar._TT.GO_TODAY = "转到今日";
Calendar._TT.NEXT_MONTH = "下一月 (按住出菜单)";
Calendar._TT.NEXT_YEAR = "下一年 (按住出菜单)";
Calendar._TT.SEL_DATE = "选择日期";
Calendar._TT.DRAG_TO_MOVE = "拖动";
Calendar._TT.PART_TODAY = " (今日)";
Calendar._TT.DAY_FIRST = "最左边显示%s";
Calendar._TT.WEEKEND = "0,6";
Calendar._TT.CLOSE = "关闭";
Calendar._TT.TODAY = "今日";
Calendar._TT.TIME_PART = "(Shift-)点击鼠标或拖动改变值";
Calendar._TT.DEF_DATE_FORMAT = "%Y-%m-%d";
Calendar._TT.TT_DATE_FORMAT = "%A, %b %e日";
Calendar._TT.WK = "周";
Calendar._TT.TIME = "时间:";
Calendar._FD = 0;
(function(b) {
	b.fn.ajaxSubmit = function(s) {
		if (!this.length) {
			a("ajaxSubmit: skipping submit process - no element selected");
			return this
		}
		if (typeof s == "function") {
			s = {
				success : s
			}
		}
		var e = b.trim(this.attr("action"));
		if (e) {
			e = (e.match(/^([^#]+)/) || [])[1]
		}
		e = e || window.location.href || "";
		s = b
				.extend(
						{
							url : e,
							type : this.attr("method") || "GET",
							iframeSrc : /^https/i.test(window.location.href
									|| "") ? "javascript:false" : "about:blank"
						}, s || {});
		var u = {};
		this.trigger("form-pre-serialize", [ this, s, u ]);
		if (u.veto) {
			a("ajaxSubmit: submit vetoed via form-pre-serialize trigger");
			return this
		}
		if (s.beforeSerialize && s.beforeSerialize(this, s) === false) {
			a("ajaxSubmit: submit aborted via beforeSerialize callback");
			return this
		}
		var m = this.formToArray(s.semantic);
		if (s.data) {
			s.extraData = s.data;
			for ( var f in s.data) {
				if (s.data[f] instanceof Array) {
					for ( var g in s.data[f]) {
						m.push( {
							name : f,
							value : s.data[f][g]
						})
					}
				} else {
					m.push( {
						name : f,
						value : s.data[f]
					})
				}
			}
		}
		if (s.beforeSubmit && s.beforeSubmit(m, this, s) === false) {
			a("ajaxSubmit: submit aborted via beforeSubmit callback");
			return this
		}
		this.trigger("form-submit-validate", [ m, this, s, u ]);
		if (u.veto) {
			a("ajaxSubmit: submit vetoed via form-submit-validate trigger");
			return this
		}
		var d = b.param(m);
		if (s.type.toUpperCase() == "GET") {
			s.url += (s.url.indexOf("?") >= 0 ? "&" : "?") + d;
			s.data = null
		} else {
			s.data = d
		}
		var t = this, l = [];
		if (s.resetForm) {
			l.push(function() {
				t.resetForm()
			})
		}
		if (s.clearForm) {
			l.push(function() {
				t.clearForm()
			})
		}
		if (!s.dataType && s.target) {
			var p = s.success || function() {
			};
			l.push(function(j) {
				b(s.target).html(j).each(p, arguments)
			})
		} else {
			if (s.success) {
				l.push(s.success)
			}
		}
		s.success = function(q, k) {
			for ( var n = 0, j = l.length; n < j; n++) {
				l[n].apply(s, [ q, k, t ])
			}
		};
		var c = b("input:file", this).fieldValue();
		var r = false;
		for ( var i = 0; i < c.length; i++) {
			if (c[i]) {
				r = true
			}
		}
		var h = false;
		if ((c.length && s.iframe !== false) || s.iframe || r || h) {
			if (s.closeKeepAlive) {
				b.get(s.closeKeepAlive, o)
			} else {
				o()
			}
		} else {
			b.ajax(s)
		}
		this.trigger("form-submit-notify", [ this, s ]);
		return this;
		function o() {
			var w = t[0];
			if (b(":input[name=submit]", w).length) {
				alert('Error: Form elements must not be named "submit".');
				return
			}
			var q = b.extend( {}, b.ajaxSettings, s);
			var G = b.extend(true, {}, b.extend(true, {}, b.ajaxSettings), q);
			var v = "jqFormIO" + (new Date().getTime());
			var C = b('<iframe id="' + v + '" name="' + v + '" src="'
					+ q.iframeSrc + '" />');
			var E = C[0];
			C.css( {
				position : "absolute",
				top : "-1000px",
				left : "-1000px"
			});
			var F = {
				aborted : 0,
				responseText : null,
				responseXML : null,
				status : 0,
				statusText : "n/a",
				getAllResponseHeaders : function() {
				},
				getResponseHeader : function() {
				},
				setRequestHeader : function() {
				},
				abort : function() {
					this.aborted = 1;
					C.attr("src", q.iframeSrc)
				}
			};
			var D = q.global;
			if (D && !b.active++) {
				b.event.trigger("ajaxStart")
			}
			if (D) {
				b.event.trigger("ajaxSend", [ F, q ])
			}
			if (G.beforeSend && G.beforeSend(F, G) === false) {
				G.global && b.active--;
				return
			}
			if (F.aborted) {
				return
			}
			var k = 0;
			var z = 0;
			var j = w.clk;
			if (j) {
				var x = j.name;
				if (x && !j.disabled) {
					s.extraData = s.extraData || {};
					s.extraData[x] = j.value;
					if (j.type == "image") {
						s.extraData[name + ".x"] = w.clk_x;
						s.extraData[name + ".y"] = w.clk_y
					}
				}
			}
			setTimeout(function() {
				var J = t.attr("target"), H = t.attr("action");
				w.setAttribute("target", v);
				if (w.getAttribute("method") != "POST") {
					w.setAttribute("method", "POST")
				}
				if (w.getAttribute("action") != q.url) {
					w.setAttribute("action", q.url)
				}
				if (!s.skipEncodingOverride) {
					t.attr( {
						encoding : "multipart/form-data",
						enctype : "multipart/form-data"
					})
				}
				if (q.timeout) {
					setTimeout(function() {
						z = true;
						A()
					}, q.timeout)
				}
				var I = [];
				try {
					if (s.extraData) {
						for ( var K in s.extraData) {
							I.push(b(
									'<input type="hidden" name="' + K
											+ '" value="' + s.extraData[K]
											+ '" />').appendTo(w)[0])
						}
					}
					C.appendTo("body");
					E.attachEvent ? E.attachEvent("onload", A) : E
							.addEventListener("load", A, false);
					w.submit()
				} finally {
					w.setAttribute("action", H);
					J ? w.setAttribute("target", J) : t.removeAttr("target");
					b(I).remove()
				}
			}, 10);
			var y = 50;
			function A() {
				if (k++) {
					return
				}
				E.detachEvent ? E.detachEvent("onload", A) : E
						.removeEventListener("load", A, false);
				var H = true;
				try {
					if (z) {
						throw "timeout"
					}
					var I, L;
					L = E.contentWindow ? E.contentWindow.document
							: E.contentDocument ? E.contentDocument
									: E.document;
					var M = q.dataType == "xml" || L.XMLDocument
							|| b.isXMLDoc(L);
					a("isXml=" + M);
					if (!M && (L.body == null || L.body.innerHTML == "")) {
						if (--y) {
							k = 0;
							setTimeout(A, 100);
							return
						}
						a("Could not access iframe DOM after 50 tries.");
						return
					}
					F.responseText = L.body ? L.body.innerHTML : null;
					F.responseXML = L.XMLDocument ? L.XMLDocument : L;
					F.getResponseHeader = function(O) {
						var N = {
							"content-type" : q.dataType
						};
						return N[O]
					};
					if (q.dataType == "json" || q.dataType == "script") {
						var n = L.getElementsByTagName("textarea")[0];
						if (n) {
							F.responseText = n.value
						} else {
							var K = L.getElementsByTagName("pre")[0];
							if (K) {
								F.responseText = K.innerHTML
							}
						}
					} else {
						if (q.dataType == "xml" && !F.responseXML
								&& F.responseText != null) {
							F.responseXML = B(F.responseText)
						}
					}
					I = b.httpData(F, q.dataType)
				} catch (J) {
					H = false;
					b.handleError(q, F, "error", J)
				}
				if (H) {
					q.success(I, "success");
					if (D) {
						b.event.trigger("ajaxSuccess", [ F, q ])
					}
				}
				if (D) {
					b.event.trigger("ajaxComplete", [ F, q ])
				}
				if (D && !--b.active) {
					b.event.trigger("ajaxStop")
				}
				if (q.complete) {
					q.complete(F, H ? "success" : "error")
				}
				setTimeout(function() {
					C.remove();
					F.responseXML = null
				}, 100)
			}
			function B(n, H) {
				if (window.ActiveXObject) {
					H = new ActiveXObject("Microsoft.XMLDOM");
					H.async = "false";
					H.loadXML(n)
				} else {
					H = (new DOMParser()).parseFromString(n, "text/xml")
				}
				return (H && H.documentElement && H.documentElement.tagName != "parsererror") ? H
						: null
			}
		}
	};
	b.fn.ajaxForm = function(c) {
		return this.ajaxFormUnbind().bind("submit.form-plugin", function() {
			b(this).ajaxSubmit(c);
			return false
		}).bind("click.form-plugin", function(i) {
			var h = i.target;
			var f = b(h);
			if (!(f.is(":submit,input:image"))) {
				var d = f.closest(":submit");
				if (d.length == 0) {
					return
				}
				h = d[0]
			}
			var g = this;
			g.clk = h;
			if (h.type == "image") {
				if (i.offsetX != undefined) {
					g.clk_x = i.offsetX;
					g.clk_y = i.offsetY
				} else {
					if (typeof b.fn.offset == "function") {
						var j = f.offset();
						g.clk_x = i.pageX - j.left;
						g.clk_y = i.pageY - j.top
					} else {
						g.clk_x = i.pageX - h.offsetLeft;
						g.clk_y = i.pageY - h.offsetTop
					}
				}
			}
			setTimeout(function() {
				g.clk = g.clk_x = g.clk_y = null
			}, 100)
		})
	};
	b.fn.ajaxFormUnbind = function() {
		return this.unbind("submit.form-plugin click.form-plugin")
	};
	b.fn.formToArray = function(q) {
		var p = [];
		if (this.length == 0) {
			return p
		}
		var d = this[0];
		var h = q ? d.getElementsByTagName("*") : d.elements;
		if (!h) {
			return p
		}
		for ( var k = 0, m = h.length; k < m; k++) {
			var e = h[k];
			var f = e.name;
			if (!f) {
				continue
			}
			if (q && d.clk && e.type == "image") {
				if (!e.disabled && d.clk == e) {
					p.push( {
						name : f,
						value : b(e).val()
					});
					p.push( {
						name : f + ".x",
						value : d.clk_x
					}, {
						name : f + ".y",
						value : d.clk_y
					})
				}
				continue
			}
			var r = b.fieldValue(e, true);
			if (r && r.constructor == Array) {
				for ( var g = 0, c = r.length; g < c; g++) {
					p.push( {
						name : f,
						value : r[g]
					})
				}
			} else {
				if (r !== null && typeof r != "undefined") {
					p.push( {
						name : f,
						value : r
					})
				}
			}
		}
		if (!q && d.clk) {
			var l = b(d.clk), o = l[0], f = o.name;
			if (f && !o.disabled && o.type == "image") {
				p.push( {
					name : f,
					value : l.val()
				});
				p.push( {
					name : f + ".x",
					value : d.clk_x
				}, {
					name : f + ".y",
					value : d.clk_y
				})
			}
		}
		return p
	};
	b.fn.formSerialize = function(c) {
		return b.param(this.formToArray(c))
	};
	b.fn.fieldSerialize = function(d) {
		var c = [];
		this.each(function() {
			var h = this.name;
			if (!h) {
				return
			}
			var f = b.fieldValue(this, d);
			if (f && f.constructor == Array) {
				for ( var g = 0, e = f.length; g < e; g++) {
					c.push( {
						name : h,
						value : f[g]
					})
				}
			} else {
				if (f !== null && typeof f != "undefined") {
					c.push( {
						name : this.name,
						value : f
					})
				}
			}
		});
		return b.param(c)
	};
	b.fn.fieldValue = function(h) {
		for ( var g = [], e = 0, c = this.length; e < c; e++) {
			var f = this[e];
			var d = b.fieldValue(f, h);
			if (d === null || typeof d == "undefined"
					|| (d.constructor == Array && !d.length)) {
				continue
			}
			d.constructor == Array ? b.merge(g, d) : g.push(d)
		}
		return g
	};
	b.fieldValue = function(c, j) {
		var e = c.name, p = c.type, q = c.tagName.toLowerCase();
		if (typeof j == "undefined") {
			j = true
		}
		if (j
				&& (!e || c.disabled || p == "reset" || p == "button"
						|| (p == "checkbox" || p == "radio") && !c.checked
						|| (p == "submit" || p == "image") && c.form
						&& c.form.clk != c || q == "select"
						&& c.selectedIndex == -1)) {
			return null
		}
		if (q == "select") {
			var k = c.selectedIndex;
			if (k < 0) {
				return null
			}
			var m = [], d = c.options;
			var g = (p == "select-one");
			var l = (g ? k + 1 : d.length);
			for ( var f = (g ? k : 0); f < l; f++) {
				var h = d[f];
				if (h.selected) {
					var o = h.value;
					if (!o) {
						o = (h.attributes && h.attributes.value && !(h.attributes.value.specified)) ? h.text
								: h.value
					}
					if (g) {
						return o
					}
					m.push(o)
				}
			}
			return m
		}
		return c.value
	};
	b.fn.clearForm = function() {
		return this.each(function() {
			b("input,select,textarea", this).clearFields()
		})
	};
	b.fn.clearFields = b.fn.clearInputs = function() {
		return this.each(function() {
			var d = this.type, c = this.tagName.toLowerCase();
			if (d == "text" || d == "password" || c == "textarea") {
				this.value = ""
			} else {
				if (d == "checkbox" || d == "radio") {
					this.checked = false
				} else {
					if (c == "select") {
						this.selectedIndex = -1
					}
				}
			}
		})
	};
	b.fn.resetForm = function() {
		return this
				.each(function() {
					if (typeof this.reset == "function"
							|| (typeof this.reset == "object" && !this.reset.nodeType)) {
						this.reset()
					}
				})
	};
	b.fn.enable = function(c) {
		if (c == undefined) {
			c = true
		}
		return this.each(function() {
			this.disabled = !c
		})
	};
	b.fn.selected = function(c) {
		if (c == undefined) {
			c = true
		}
		return this.each(function() {
			var d = this.type;
			if (d == "checkbox" || d == "radio") {
				this.checked = c
			} else {
				if (this.tagName.toLowerCase() == "option") {
					var e = b(this).parent("select");
					if (c && e[0] && e[0].type == "select-one") {
						e.find("option").selected(false)
					}
					this.selected = c
				}
			}
		})
	};
	function a() {
		if (b.fn.ajaxSubmit.debug && window.console && window.console.log) {
			window.console.log("[jquery.form] "
					+ Array.prototype.join.call(arguments, ""))
		}
	}
})(jQuery);
(function(c) {
	c
			.extend(
					c.fn,
					{
						validate : function(a) {
							if (this.length) {
								var b = c.data(this[0], "validator");
								if (b)
									return b;
								b = new c.validator(a, this[0]);
								c.data(this[0], "validator", b);
								if (b.settings.onsubmit) {
									this.find("input, button")
											.filter(".cancel").click(
													function() {
														b.cancelSubmit = true
													});
									b.settings.submitHandler
											&& this.find("input, button")
													.filter(":submit")
													.click(function() {
														b.submitButton = this
													});
									this
											.submit(function(d) {
												function e() {
													if (b.settings.submitHandler) {
														if (b.submitButton)
															var f = c(
																	"<input type='hidden'/>")
																	.attr(
																			"name",
																			b.submitButton.name)
																	.val(
																			b.submitButton.value)
																	.appendTo(
																			b.currentForm);
														b.settings.submitHandler
																.call(
																		b,
																		b.currentForm);
														b.submitButton
																&& f.remove();
														return false
													}
													return true
												}
												b.settings.debug
														&& d.preventDefault();
												if (b.cancelSubmit) {
													b.cancelSubmit = false;
													return e()
												}
												if (b.form()) {
													if (b.pendingRequest) {
														b.formSubmitted = true;
														return false
													}
													return e()
												} else {
													b.focusInvalid();
													return false
												}
											})
								}
								return b
							} else
								a
										&& a.debug
										&& window.console
										&& console
												.warn("nothing selected, can't validate, returning nothing")
						},
						valid : function() {
							if (c(this[0]).is("form"))
								return this.validate().form();
							else {
								var a = true, b = c(this[0].form).validate();
								this.each(function() {
									a &= b.element(this)
								});
								return a
							}
						},
						removeAttrs : function(a) {
							var b = {}, d = this;
							c.each(a.split(/\s/), function(e, f) {
								b[f] = d.attr(f);
								d.removeAttr(f)
							});
							return b
						},
						rules : function(a, b) {
							var d = this[0];
							if (a) {
								var e = c.data(d.form, "validator").settings, f = e.rules, g = c.validator
										.staticRules(d);
								switch (a) {
								case "add":
									c.extend(g, c.validator.normalizeRule(b));
									f[d.name] = g;
									if (b.messages)
										e.messages[d.name] = c.extend(
												e.messages[d.name], b.messages);
									break;
								case "remove":
									if (!b) {
										delete f[d.name];
										return g
									}
									var h = {};
									c.each(b.split(/\s/), function(j, i) {
										h[i] = g[i];
										delete g[i]
									});
									return h
								}
							}
							d = c.validator.normalizeRules(c.extend( {},
									c.validator.metadataRules(d), c.validator
											.classRules(d), c.validator
											.attributeRules(d), c.validator
											.staticRules(d)), d);
							if (d.required) {
								e = d.required;
								delete d.required;
								d = c.extend( {
									required : e
								}, d)
							}
							return d
						}
					});
	c.extend(c.expr[":"], {
		blank : function(a) {
			return !c.trim("" + a.value)
		},
		filled : function(a) {
			return !!c.trim("" + a.value)
		},
		unchecked : function(a) {
			return !a.checked
		}
	});
	c.validator = function(a, b) {
		this.settings = c.extend(true, {}, c.validator.defaults, a);
		this.currentForm = b;
		this.init()
	};
	c.validator.format = function(a, b) {
		if (arguments.length == 1)
			return function() {
				var d = c.makeArray(arguments);
				d.unshift(a);
				return c.validator.format.apply(this, d)
			};
		if (arguments.length > 2 && b.constructor != Array)
			b = c.makeArray(arguments).slice(1);
		if (b.constructor != Array)
			b = [ b ];
		c.each(b, function(d, e) {
			a = a.replace(RegExp("\\{" + d + "\\}", "g"), e)
		});
		return a
	};
	c
			.extend(
					c.validator,
					{
						defaults : {
							messages : {},
							groups : {},
							rules : {},
							errorClass : "error",
							validClass : "valid",
							errorElement : "label",
							focusInvalid : true,
							errorContainer : c( []),
							errorLabelContainer : c( []),
							onsubmit : true,
							ignore : [],
							ignoreTitle : false,
							onfocusin : function(a) {
								this.lastActive = a;
								if (this.settings.focusCleanup
										&& !this.blockFocusCleanup) {
									this.settings.unhighlight
											&& this.settings.unhighlight.call(
													this, a,
													this.settings.errorClass,
													this.settings.validClass);
									this.addWrapper(this.errorsFor(a)).hide()
								}
							},
							onfocusout : function(a) {
								if (!this.checkable(a)
										&& (a.name in this.submitted || !this
												.optional(a)))
									this.element(a)
							},
							onkeyup : function(a) {
								if (a.name in this.submitted
										|| a == this.lastElement)
									this.element(a)
							},
							onclick : function(a) {
								if (a.name in this.submitted)
									this.element(a);
								else
									a.parentNode.name in this.submitted
											&& this.element(a.parentNode)
							},
							highlight : function(a, b, d) {
								a.type === "radio" ? this.findByName(a.name)
										.addClass(b).removeClass(d) : c(a)
										.addClass(b).removeClass(d)
							},
							unhighlight : function(a, b, d) {
								a.type === "radio" ? this.findByName(a.name)
										.removeClass(b).addClass(d) : c(a)
										.removeClass(b).addClass(d)
							}
						},
						setDefaults : function(a) {
							c.extend(c.validator.defaults, a)
						},
						messages : {
							required : "This field is required.",
							remote : "Please fix this field.",
							email : "Please enter a valid email address.",
							url : "Please enter a valid URL.",
							date : "Please enter a valid date.",
							dateISO : "Please enter a valid date (ISO).",
							number : "Please enter a valid number.",
							digits : "Please enter only digits.",
							creditcard : "Please enter a valid credit card number.",
							equalTo : "Please enter the same value again.",
							accept : "Please enter a value with a valid extension.",
							maxlength : c.validator
									.format("Please enter no more than {0} characters."),
							minlength : c.validator
									.format("Please enter at least {0} characters."),
							rangelength : c.validator
									.format("Please enter a value between {0} and {1} characters long."),
							range : c.validator
									.format("Please enter a value between {0} and {1}."),
							max : c.validator
									.format("Please enter a value less than or equal to {0}."),
							min : c.validator
									.format("Please enter a value greater than or equal to {0}.")
						},
						autoCreateRanges : false,
						prototype : {
							init : function() {
								function a(e) {
									var f = c.data(this[0].form, "validator");
									e = "on" + e.type.replace(/^validate/, "");
									f.settings[e]
											&& f.settings[e].call(f, this[0])
								}
								this.labelContainer = c(this.settings.errorLabelContainer);
								this.errorContext = this.labelContainer.length
										&& this.labelContainer
										|| c(this.currentForm);
								this.containers = c(
										this.settings.errorContainer).add(
										this.settings.errorLabelContainer);
								this.submitted = {};
								this.valueCache = {};
								this.pendingRequest = 0;
								this.pending = {};
								this.invalid = {};
								this.reset();
								var b = this.groups = {};
								c.each(this.settings.groups, function(e, f) {
									c.each(f.split(/\s/), function(g, h) {
										b[h] = e
									})
								});
								var d = this.settings.rules;
								c.each(d, function(e, f) {
									d[e] = c.validator.normalizeRule(f)
								});
								c(this.currentForm)
										.validateDelegate(
												":text, :password, :file, select, textarea",
												"focusin focusout keyup", a)
										.validateDelegate(
												":radio, :checkbox, select, option",
												"click", a);
								this.settings.invalidHandler
										&& c(this.currentForm).bind(
												"invalid-form.validate",
												this.settings.invalidHandler)
							},
							form : function() {
								this.checkForm();
								c.extend(this.submitted, this.errorMap);
								this.invalid = c.extend( {}, this.errorMap);
								this.valid()
										|| c(this.currentForm).triggerHandler(
												"invalid-form", [ this ]);
								this.showErrors();
								return this.valid()
							},
							checkForm : function() {
								this.prepareForm();
								for ( var a = 0, b = this.currentElements = this
										.elements(); b[a]; a++)
									this.check(b[a]);
								return this.valid()
							},
							element : function(a) {
								this.lastElement = a = this.clean(a);
								this.prepareElement(a);
								this.currentElements = c(a);
								var b = this.check(a);
								if (b)
									delete this.invalid[a.name];
								else
									this.invalid[a.name] = true;
								if (!this.numberOfInvalids())
									this.toHide = this.toHide
											.add(this.containers);
								this.showErrors();
								return b
							},
							showErrors : function(a) {
								if (a) {
									c.extend(this.errorMap, a);
									this.errorList = [];
									for ( var b in a)
										this.errorList.push( {
											message : a[b],
											element : this.findByName(b)[0]
										});
									this.successList = c.grep(this.successList,
											function(d) {
												return !(d.name in a)
											})
								}
								this.settings.showErrors ? this.settings.showErrors
										.call(this, this.errorMap,
												this.errorList)
										: this.defaultShowErrors()
							},
							resetForm : function() {
								c.fn.resetForm
										&& c(this.currentForm).resetForm();
								this.submitted = {};
								this.prepareForm();
								this.hideErrors();
								this.elements().removeClass(
										this.settings.errorClass)
							},
							numberOfInvalids : function() {
								return this.objectLength(this.invalid)
							},
							objectLength : function(a) {
								var b = 0, d;
								for (d in a)
									b++;
								return b
							},
							hideErrors : function() {
								this.addWrapper(this.toHide).hide()
							},
							valid : function() {
								return this.size() == 0
							},
							size : function() {
								return this.errorList.length
							},
							focusInvalid : function() {
								if (this.settings.focusInvalid)
									try {
										c(
												this.findLastActive()
														|| this.errorList.length
														&& this.errorList[0].element
														|| []).filter(
												":visible").focus().trigger(
												"focusin")
									} catch (a) {
									}
							},
							findLastActive : function() {
								var a = this.lastActive;
								return a && c.grep(this.errorList, function(b) {
									return b.element.name == a.name
								}).length == 1 && a
							},
							elements : function() {
								var a = this, b = {};
								return c(this.currentForm)
										.find("input, select, textarea")
										.not(
												":submit, :reset, :image, [disabled]")
										.not(this.settings.ignore)
										.filter(
												function() {
													!this.name
															&& a.settings.debug
															&& window.console
															&& console
																	.error(
																			"%o has no name assigned",
																			this);
													if (this.name in b
															|| !a
																	.objectLength(c(
																			this)
																			.rules()))
														return false;
													return b[this.name] = true
												})
							},
							clean : function(a) {
								return c(a)[0]
							},
							errors : function() {
								return c(this.settings.errorElement + "."
										+ this.settings.errorClass,
										this.errorContext)
							},
							reset : function() {
								this.successList = [];
								this.errorList = [];
								this.errorMap = {};
								this.toShow = c( []);
								this.toHide = c( []);
								this.currentElements = c( [])
							},
							prepareForm : function() {
								this.reset();
								this.toHide = this.errors()
										.add(this.containers)
							},
							prepareElement : function(a) {
								this.reset();
								this.toHide = this.errorsFor(a)
							},
							check : function(a) {
								a = this.clean(a);
								if (this.checkable(a))
									a = this.findByName(a.name).not(
											this.settings.ignore)[0];
								var b = c(a).rules(), d = false, e;
								for (e in b) {
									var f = {
										method : e,
										parameters : b[e]
									};
									try {
										var g = c.validator.methods[e].call(
												this, a.value
														.replace(/\r/g, ""), a,
												f.parameters);
										if (g == "dependency-mismatch")
											d = true;
										else {
											d = false;
											if (g == "pending") {
												this.toHide = this.toHide
														.not(this.errorsFor(a));
												return
											}
											if (!g) {
												this.formatAndAdd(a, f);
												return false
											}
										}
									} catch (h) {
										this.settings.debug
												&& window.console
												&& console
														.log(
																"exception occured when checking element "
																		+ a.id
																		+ ", check the '"
																		+ f.method
																		+ "' method",
																h);
										throw h;
									}
								}
								if (!d) {
									this.objectLength(b)
											&& this.successList.push(a);
									return true
								}
							},
							customMetaMessage : function(a, b) {
								if (c.metadata) {
									var d = this.settings.meta ? c(a)
											.metadata()[this.settings.meta]
											: c(a).metadata();
									return d && d.messages && d.messages[b]
								}
							},
							customMessage : function(a, b) {
								var d = this.settings.messages[a];
								return d
										&& (d.constructor == String ? d : d[b])
							},
							findDefined : function() {
								for ( var a = 0; a < arguments.length; a++)
									if (arguments[a] !== undefined)
										return arguments[a]
							},
							defaultMessage : function(a, b) {
								return this.findDefined(this.customMessage(
										a.name, b), this
										.customMetaMessage(a, b),
										!this.settings.ignoreTitle && a.title
												|| undefined,
										c.validator.messages[b],
										"<strong>Warning: No message defined for "
												+ a.name + "</strong>")
							},
							formatAndAdd : function(a, b) {
								var d = this.defaultMessage(a, b.method), e = /\$?\{(\d+)\}/g;
								if (typeof d == "function")
									d = d.call(this, b.parameters, a);
								else if (e.test(d))
									d = jQuery.format(d.replace(e, "{$1}"),
											b.parameters);
								this.errorList.push( {
									message : d,
									element : a
								});
								this.errorMap[a.name] = d;
								this.submitted[a.name] = d
							},
							addWrapper : function(a) {
								if (this.settings.wrapper)
									a = a.add(a.parent(this.settings.wrapper));
								return a
							},
							defaultShowErrors : function() {
								for ( var a = 0; this.errorList[a]; a++) {
									var b = this.errorList[a];
									this.settings.highlight
											&& this.settings.highlight.call(
													this, b.element,
													this.settings.errorClass,
													this.settings.validClass);
									this.showLabel(b.element, b.message)
								}
								if (this.errorList.length)
									this.toShow = this.toShow
											.add(this.containers);
								if (this.settings.success)
									for (a = 0; this.successList[a]; a++)
										this.showLabel(this.successList[a]);
								if (this.settings.unhighlight) {
									a = 0;
									for (b = this.validElements(); b[a]; a++)
										this.settings.unhighlight.call(this,
												b[a], this.settings.errorClass,
												this.settings.validClass)
								}
								this.toHide = this.toHide.not(this.toShow);
								this.hideErrors();
								this.addWrapper(this.toShow).show()
							},
							validElements : function() {
								return this.currentElements.not(this
										.invalidElements())
							},
							invalidElements : function() {
								return c(this.errorList).map(function() {
									return this.element
								})
							},
							showLabel : function(a, b) {
								var d = this.errorsFor(a);
								if (d.length) {
									d.removeClass().addClass(
											this.settings.errorClass);
									d.attr("generated") && d.html(b)
								} else {
									d = c(
											"<" + this.settings.errorElement
													+ "/>").attr( {
										"for" : this.idOrName(a),
										generated : true
									}).addClass(this.settings.errorClass).html(
											b || "");
									if (this.settings.wrapper)
										d = d.hide().show().wrap(
												"<" + this.settings.wrapper
														+ "/>").parent();
									this.labelContainer.append(d).length
											|| (this.settings.errorPlacement ? this.settings
													.errorPlacement(d, c(a))
													: d.insertAfter(a))
								}
								if (!b && this.settings.success) {
									d.text("");
									typeof this.settings.success == "string" ? d
											.addClass(this.settings.success)
											: this.settings.success(d)
								}
								this.toShow = this.toShow.add(d)
							},
							errorsFor : function(a) {
								var b = this.idOrName(a);
								return this.errors().filter(function() {
									return c(this).attr("for") == b
								})
							},
							idOrName : function(a) {
								return this.groups[a.name]
										|| (this.checkable(a) ? a.name : a.id
												|| a.name)
							},
							checkable : function(a) {
								return /radio|checkbox/i.test(a.type)
							},
							findByName : function(a) {
								var b = this.currentForm;
								return c(document.getElementsByName(a)).map(
										function(d, e) {
											return e.form == b && e.name == a
													&& e || null
										})
							},
							getLength : function(a, b) {
								switch (b.nodeName.toLowerCase()) {
								case "select":
									return c("option:selected", b).length;
								case "input":
									if (this.checkable(b))
										return this.findByName(b.name).filter(
												":checked").length
								}
								return a.length
							},
							depend : function(a, b) {
								return this.dependTypes[typeof a] ? this.dependTypes[typeof a]
										(a, b)
										: true
							},
							dependTypes : {
								"boolean" : function(a) {
									return a
								},
								string : function(a, b) {
									return !!c(a, b.form).length
								},
								"function" : function(a, b) {
									return a(b)
								}
							},
							optional : function(a) {
								return !c.validator.methods.required.call(this,
										c.trim(a.value), a)
										&& "dependency-mismatch"
							},
							startRequest : function(a) {
								if (!this.pending[a.name]) {
									this.pendingRequest++;
									this.pending[a.name] = true
								}
							},
							stopRequest : function(a, b) {
								this.pendingRequest--;
								if (this.pendingRequest < 0)
									this.pendingRequest = 0;
								delete this.pending[a.name];
								if (b && this.pendingRequest == 0
										&& this.formSubmitted && this.form()) {
									c(this.currentForm).submit();
									this.formSubmitted = false
								} else if (!b && this.pendingRequest == 0
										&& this.formSubmitted) {
									c(this.currentForm).triggerHandler(
											"invalid-form", [ this ]);
									this.formSubmitted = false
								}
							},
							previousValue : function(a) {
								return c.data(a, "previousValue")
										|| c.data(a, "previousValue", {
											old : null,
											valid : true,
											message : this.defaultMessage(a,
													"remote")
										})
							}
						},
						classRuleSettings : {
							required : {
								required : true
							},
							email : {
								email : true
							},
							url : {
								url : true
							},
							date : {
								date : true
							},
							dateISO : {
								dateISO : true
							},
							dateDE : {
								dateDE : true
							},
							number : {
								number : true
							},
							numberDE : {
								numberDE : true
							},
							digits : {
								digits : true
							},
							creditcard : {
								creditcard : true
							}
						},
						addClassRules : function(a, b) {
							a.constructor == String ? this.classRuleSettings[a] = b
									: c.extend(this.classRuleSettings, a)
						},
						classRules : function(a) {
							var b = {};
							(a = c(a).attr("class"))
									&& c
											.each(
													a.split(" "),
													function() {
														this in c.validator.classRuleSettings
																&& c
																		.extend(
																				b,
																				c.validator.classRuleSettings[this])
													});
							return b
						},
						attributeRules : function(a) {
							var b = {};
							a = c(a);
							for ( var d in c.validator.methods) {
								var e = a.attr(d);
								if (e)
									b[d] = e
							}
							b.maxlength
									&& /-1|2147483647|524288/.test(b.maxlength)
									&& delete b.maxlength;
							return b
						},
						metadataRules : function(a) {
							if (!c.metadata)
								return {};
							var b = c.data(a.form, "validator").settings.meta;
							return b ? c(a).metadata()[b] : c(a).metadata()
						},
						staticRules : function(a) {
							var b = {}, d = c.data(a.form, "validator");
							if (d.settings.rules)
								b = c.validator
										.normalizeRule(d.settings.rules[a.name])
										|| {};
							return b
						},
						normalizeRules : function(a, b) {
							c.each(a, function(d, e) {
								if (e === false)
									delete a[d];
								else if (e.param || e.depends) {
									var f = true;
									switch (typeof e.depends) {
									case "string":
										f = !!c(e.depends, b.form).length;
										break;
									case "function":
										f = e.depends.call(b, b)
									}
									if (f)
										a[d] = e.param !== undefined ? e.param
												: true;
									else
										delete a[d]
								}
							});
							c.each(a, function(d, e) {
								a[d] = c.isFunction(e) ? e(b) : e
							});
							c.each( [ "minlength", "maxlength", "min", "max" ],
									function() {
										if (a[this])
											a[this] = Number(a[this])
									});
							c.each( [ "rangelength", "range" ], function() {
								if (a[this])
									a[this] = [ Number(a[this][0]),
											Number(a[this][1]) ]
							});
							if (c.validator.autoCreateRanges) {
								if (a.min && a.max) {
									a.range = [ a.min, a.max ];
									delete a.min;
									delete a.max
								}
								if (a.minlength && a.maxlength) {
									a.rangelength = [ a.minlength, a.maxlength ];
									delete a.minlength;
									delete a.maxlength
								}
							}
							a.messages && delete a.messages;
							return a
						},
						normalizeRule : function(a) {
							if (typeof a == "string") {
								var b = {};
								c.each(a.split(/\s/), function() {
									b[this] = true
								});
								a = b
							}
							return a
						},
						addMethod : function(a, b, d) {
							c.validator.methods[a] = b;
							c.validator.messages[a] = d != undefined ? d
									: c.validator.messages[a];
							b.length < 3
									&& c.validator.addClassRules(a, c.validator
											.normalizeRule(a))
						},
						methods : {
							required : function(a, b, d) {
								if (!this.depend(d, b))
									return "dependency-mismatch";
								switch (b.nodeName.toLowerCase()) {
								case "select":
									return (a = c(b).val()) && a.length > 0;
								case "input":
									if (this.checkable(b))
										return this.getLength(a, b) > 0;
								default:
									return c.trim(a).length > 0
								}
							},
							remote : function(a, b, d) {
								if (this.optional(b))
									return "dependency-mismatch";
								var e = this.previousValue(b);
								this.settings.messages[b.name]
										|| (this.settings.messages[b.name] = {});
								e.originalMessage = this.settings.messages[b.name].remote;
								this.settings.messages[b.name].remote = e.message;
								d = typeof d == "string" && {
									url : d
								} || d;
								if (this.pending[b.name])
									return "pending";
								if (e.old === a)
									return e.valid;
								e.old = a;
								var f = this;
								this.startRequest(b);
								var g = {};
								g[b.name] = a;
								c
										.ajax(c
												.extend(
														true,
														{
															url : d,
															mode : "abort",
															port : "validate"
																	+ b.name,
															dataType : "json",
															data : g,
															success : function(
																	h) {
																f.settings.messages[b.name].remote = e.originalMessage;
																var j = h === true;
																if (j) {
																	var i = f.formSubmitted;
																	f
																			.prepareElement(b);
																	f.formSubmitted = i;
																	f.successList
																			.push(b);
																	f
																			.showErrors()
																} else {
																	i = {};
																	h = h
																			|| f
																					.defaultMessage(
																							b,
																							"remote");
																	i[b.name] = e.message = c
																			.isFunction(h) ? h(a)
																			: h;
																	f
																			.showErrors(i)
																}
																e.valid = j;
																f.stopRequest(
																		b, j)
															}
														}, d));
								return "pending"
							},
							minlength : function(a, b, d) {
								return this.optional(b)
										|| this.getLength(c.trim(a), b) >= d
							},
							maxlength : function(a, b, d) {
								return this.optional(b)
										|| this.getLength(c.trim(a), b) <= d
							},
							rangelength : function(a, b, d) {
								a = this.getLength(c.trim(a), b);
								return this.optional(b) || a >= d[0]
										&& a <= d[1]
							},
							min : function(a, b, d) {
								return this.optional(b) || a >= d
							},
							max : function(a, b, d) {
								return this.optional(b) || a <= d
							},
							range : function(a, b, d) {
								return this.optional(b) || a >= d[0]
										&& a <= d[1]
							},
							email : function(a, b) {
								return this.optional(b)
										|| /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
												.test(a)
							},
							url : function(a, b) {
								return this.optional(b)
										|| /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
												.test(a)
							},
							date : function(a, b) {
								return this.optional(b)
										|| !/Invalid|NaN/.test(new Date(a))
							},
							dateISO : function(a, b) {
								return this.optional(b)
										|| /^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/
												.test(a)
							},
							number : function(a, b) {
								return this.optional(b)
										|| /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/
												.test(a)
							},
							digits : function(a, b) {
								return this.optional(b) || /^\d+$/.test(a)
							},
							creditcard : function(a, b) {
								if (this.optional(b))
									return "dependency-mismatch";
								if (/[^0-9-]+/.test(a))
									return false;
								var d = 0, e = 0, f = false;
								a = a.replace(/\D/g, "");
								for ( var g = a.length - 1; g >= 0; g--) {
									e = a.charAt(g);
									e = parseInt(e, 10);
									if (f)
										if ((e *= 2) > 9)
											e -= 9;
									d += e;
									f = !f
								}
								return d % 10 == 0
							},
							accept : function(a, b, d) {
								d = typeof d == "string" ? d.replace(/,/g, "|")
										: "png|jpe?g|gif";
								return this.optional(b)
										|| a
												.match(RegExp(".(" + d + ")$",
														"i"))
							},
							equalTo : function(a, b, d) {
								d = c(d).unbind(".validate-equalTo").bind(
										"blur.validate-equalTo", function() {
											c(b).valid()
										});
								return a == d.val()
							}
						}
					});
	c.format = c.validator.format
})(jQuery);
(function(c) {
	var a = {};
	if (c.ajaxPrefilter)
		c.ajaxPrefilter(function(d, e, f) {
			e = d.port;
			if (d.mode == "abort") {
				a[e] && a[e].abort();
				a[e] = f
			}
		});
	else {
		var b = c.ajax;
		c.ajax = function(d) {
			var e = ("port" in d ? d : c.ajaxSettings).port;
			if (("mode" in d ? d : c.ajaxSettings).mode == "abort") {
				a[e] && a[e].abort();
				return a[e] = b.apply(this, arguments)
			}
			return b.apply(this, arguments)
		}
	}
})(jQuery);
(function(c) {
	!jQuery.event.special.focusin && !jQuery.event.special.focusout
			&& document.addEventListener && c.each( {
				focus : "focusin",
				blur : "focusout"
			}, function(a, b) {
				function d(e) {
					e = c.event.fix(e);
					e.type = b;
					return c.event.handle.call(this, e)
				}
				c.event.special[b] = {
					setup : function() {
						this.addEventListener(a, d, true)
					},
					teardown : function() {
						this.removeEventListener(a, d, true)
					},
					handler : function(e) {
						arguments[0] = c.event.fix(e);
						arguments[0].type = b;
						return c.event.handle.apply(this, arguments)
					}
				}
			});
	c.extend(c.fn, {
		validateDelegate : function(a, b, d) {
			return this.bind(b, function(e) {
				var f = c(e.target);
				if (f.is(a))
					return d.apply(f, arguments)
			})
		}
	})
})(jQuery);
jQuery.validator.addMethod("byteRangeLength", function(d, b, e) {
	var c = d.length;
	for ( var a = 0; a < d.length; a++) {
		if (d.charCodeAt(a) > 127) {
			c++
		}
	}
	return this.optional(b) || (c >= e[0] && c <= e[1])
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));
jQuery.validator.addMethod("isZipCode", function(c, b) {
	var a = /^[0-9]{6}$/;
	return this.optional(b) || (a.test(c))
}, "请正确填写您的邮政编码");
jQuery.validator.addMethod("ip", function(b, a) {
	return this.optional(a)
			|| (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(b) && (RegExp.$1 < 256
					&& RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256))
}, "请输入合法的IP信息");
jQuery.validator.addMethod("onlyMoney", function(c, a) {
	c = c.replace(/\s/ig, "");
	var b = /^-?\d+\.?\d{0,}$/;
	return this.optional(a) || (b.test(c))
}, " 请输入合法的金额信息 ! ");
jQuery.validator.addMethod("onlyMw", function(b, a) {
	var c = /[^\d\+\-\*\/\<\>]/;
	return this.optional(a) || (!c.test(b))
}, " 请输入0-9或+-*/<> ");
jQuery.validator.addMethod("onlyNsrsbh", function(c, b) {
	var a = /^[0-9XDKGg]+$/;
	return this.optional(b) || (a.test(c))
}, " 请输入数字或XDKGg ! ");
jQuery.validator.addMethod("noSpecialCaracters", function(b, a) {
	var c = /^\w+$/;
	return this.optional(a) || (c.test(b))
}, " 请输入英文字母或_ ! ");
jQuery.validator.addMethod("onlyNumber", function(b, a) {
	var c = /^\d+$/;
	return this.optional(a) || (c.test(b))
}, " 请输入数字英文字母或_ ! ");
jQuery.validator
		.addMethod(
				"onlyDate",
				function(c, a) {
					var b = new RegExp(
							"^(?:(?:([0-9]{4}(-)(?:(?:0?[1,3-9]|1[0-2])(-)(?:29|30)|((?:0?[13578]|1[02])(-)31)))|([0-9]{4}(-)(?:0?[1-9]|1[0-2])(-)(?:0?[1-9]|1\\d|2[0-8]))|(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|(?:0[48]00|[2468][048]00|[13579][26]00))(-)0?2(-)29))))$");
					return b.test(c)
				}, " 请输规定格式yyyy-mm-dd的正确日期 ");
jQuery.validator
		.addMethod(
				"sbhLength",
				function(b, a) {
					return (b.length == 15 || b.length == 17 || b.length == 18 || b.length == 20)
				}, "纳税人识别号长度应为15、17、18或20位");
jQuery.validator
		.addMethod(
				"dksbhLength",
				function(b, a) {
					return (b.length == 15 || b.length == 17 || b.length == 18 || b.length == 20)
				}, "代开纳税人识别号长度应为15、17、18或20位");
jQuery.validator.addMethod("onlyDksbh", function(c, b) {
	var a = c.indexOf("DK");
	return (a > 0 && c.indexOf("DK", a + 1) < 0)
}, "代开识别号必须且只能包含一组DK");
jQuery.validator.addMethod("isMobileNumber", function(b, a) {
	var c = /(^[1][3,5,8][0-9]{9}$)|(^0[1][3][0-9]{9}$)/;
	return this.optional(a) || (c.test(b))
}, " 请输入正确的手机号码! ");
jQuery.validator.addMethod("isPhoneNumber", function(b, a) {
	var c = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	return this.optional(a) || (c.test(b))
}, " 请输入正确的电话号码! ");
jQuery.validator.addMethod("isTelPhone", function(b, a) {
	var c = /(^[1][3,5,8][0-9]{9}$)|(^0[1][3][0-9]{9}$)/;
	var d = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	return this.optional(a) || (d.test(b)) || (c.test(b))
}, " 请输入正确的手机或电话号码! ");
jQuery.validator.addMethod("notZero", function(b, a) {
	return !(b == "0" || b == "0.00")
}, "金额不能为零！");
jQuery.validator.addMethod("isDateBefore", function(d, a) {
	var c = new Date();
	var b = new Date(d.replace(/-/g, "/"));
	return this.optional(a) || (Date.parse(c) - Date.parse(b)) > 0
}, " 不能大于当前日期! ");
jQuery.validator.addMethod("isNumOrLetter", function(b, a) {
	var c = /^[A-Za-z0-9]+$/;
	return this.optional(a) || (c.test(b))
}, " 请输入数字或字母! ");
jQuery.validator.addMethod("expkindnoLength", function(b, a) {
	return (b.length == 0 || b.length == 8 || b.length == 12)
}, "发票代码长度应为8、或12位");
jQuery.validator.addMethod("checkPass", function(c, a) {
	var b = 0;
	if (c.match(/([a-zA-Z])+/)) {
		b++
	}
	if (c.match(/([0-9])+/)) {
		b++
	}
	if (c.match(/[^a-zA-Z0-9]+/)) {
		b++
	}
	return b >= 2
}, "密码复杂度不够!");
$.extend( {
	getLeft : function(b) {
		var c = b;
		var d, a = c.offsetLeft;
		while (c.offsetParent != null) {
			d = c.offsetParent;
			a += d.offsetLeft;
			c = d
		}
		return a
	},
	getTop : function(b) {
		var c = b;
		var e = c.height;
		var d, a = c.offsetTop;
		while (c.offsetParent != null) {
			d = c.offsetParent;
			a += d.offsetTop;
			c = d
		}
		return a + 22
	}
});
function showErrors() {
	var e = this;
	for ( var d = 0; this.errorList[d]; d++) {
		var c = this.errorList[d];
		this.settings.highlight
				&& this.settings.highlight.call(this, c.element,
						this.settings.errorClass, this.settings.validClass);
		var g = this.idOrName(c.element);
		var a = $("div[htmlfor=" + g + "]");
		var b = $("img[htmlfor=" + g + "]");
		if (b.length == 0) {
			b = $('<img alt="提示" src=' + _ctx + '"/images/unchecked.gif"  width="14" height="14">');
			b.attr( {
				"for" : this.idOrName(c.element),
				generated : true
			});
			b.insertAfter(c.element)
		}
		b.show();
		if (a.length == 0) {
			a = $('<div><div class="errmsgdiv fl errmsg"></div></div>');
			callerTopPosition = $(c.element).offset().top;
			callerleftPosition = $(c.element).offset().left;
			callerWidth = $(c.element).width();
			inputHeight = $(a).height();
			callerleftPosition += callerWidth + 16;
			a.attr( {
				"for" : this.idOrName(c.element),
				generated : true
			}).addClass(this.settings.errorClass);
			a.css( {
				left : callerleftPosition,
				top : callerTopPosition,
				opacity : 100
			});
			a.appendTo($("body"))
		}
		a.show();
		a.find(".errmsg").html(c.message || "")
	}
	for ( var d = 0; this.successList[d]; d++) {
		$('div[htmlfor="' + this.idOrName(this.successList[d]) + '"]').remove();
		$("img[htmlfor=" + this.idOrName(this.successList[d]) + "]").hide()
	}
	if (this.settings.unhighlight) {
		for ( var d = 0, f = this.validElements(); f[d]; d++) {
			this.settings.unhighlight.call(this, f[d],
					this.settings.errorClass, this.settings.validClass)
		}
	}
}
jQuery.validator
		.addMethod(
				"isFriTaxNo",
				function(b, a) {
					var c = /(^\+?[A-Za-z0-9]{15}$)|(^\+?[A-Za-z0-9]{17}$)|(^\+?[A-Za-z0-9]{18}$)|(^\+?[A-Za-z0-9]{20}$)/;
					return this.optional(a) || (c.test(b))
				}, " 运输发票税号应为15,17,18或20位数字或字母! ");
jQuery.validator.addMethod("notEqualTo", function(b, a, d) {
	var c = $(d).unbind(".validate-notEqualTo").bind(
			"blur.validate-notEqualTo", function() {
				$(a).valid()
			});
	return b != c.val()
}, " 请输入不同值! ");
jQuery.validator.addMethod("isInvkindMatchType", function(b, a, c) {
	if (b.substring(7, 8) == "6") {
		if ($(c).val() == "s") {
			return false
		} else {
			return true
		}
	} else {
		if ($(c).val() == "c") {
			return false
		} else {
			return true
		}
	}
}, "发票代码与发票类型不符，请重新输入! ");
jQuery.extend(jQuery.validator.messages, {
	required : "必选字段",
	remote : "请修正该字段",
	email : "请输入正确格式的电子邮件",
	url : "请输入合法的网址",
	date : "请输入合法的日期",
	dateISO : "请输入合法的日期 (ISO).",
	number : " 只能输入0-9间的数字! ",
	digits : "只能输入整数",
	creditcard : "请输入合法的信用卡号",

	equalTo : "请再次输入相同的值",
	accept : "请输入拥有合法后缀名的字符串",
	maxlength : jQuery.validator.format(" 最大长度应为 {0} !"),
	minlength : jQuery.validator.format(" 最小长度应为 {0} !"),
	rangelength : jQuery.validator.format(" 长度应为 {0}-{1} 位 !"),
	range : jQuery.validator.format(" 值介于 {0} 和 {1} 之间 !"),
	max : jQuery.validator.format(" 最大值为 {0} !"),
	min : jQuery.validator.format(" 最小值为 {0} !")
});
(function(a) {
	a.fn.extend( {
		autocomplete : function(b, c) {
			var d = typeof b == "string";
			c = a.extend( {}, a.Autocompleter.defaults, {
				url : d ? b : null,
				data : d ? null : b,
				delay : d ? a.Autocompleter.defaults.delay : 10,
				max : c && !c.scroll ? 10 : 150
			}, c);
			c.highlight = c.highlight || function(e) {
				return e
			};
			c.formatMatch = c.formatMatch || c.formatItem;
			return this.each(function() {
				new a.Autocompleter(this, c)
			})
		},
		result : function(b) {
			return this.bind("result", b)
		},
		search : function(b) {
			return this.trigger("search", [ b ])
		},
		flushCache : function() {
			return this.trigger("flushCache")
		},
		setOptions : function(b) {
			return this.trigger("setOptions", [ b ])
		},
		unautocomplete : function() {
			return this.trigger("unautocomplete")
		}
	});
	a.Autocompleter = function(l, g) {
		var c = {
			UP : 38,
			DOWN : 40,
			DEL : 46,
			TAB : 9,
			RETURN : 13,
			ESC : 27,
			COMMA : 188,
			PAGEUP : 33,
			PAGEDOWN : 34,
			BACKSPACE : 8
		};
		var b = a(l).attr("autocomplete", "off").addClass(g.inputClass);
		var j;
		var p = "";
		var m = a.Autocompleter.Cache(g);
		var e = 0;
		var u;
		var x = {
			mouseDownOnSelect : false
		};
		var r = a.Autocompleter.Select(g, l, d, x);
		var w;
		a.browser.opera && a(l.form).bind("submit.autocomplete", function() {
			if (w) {
				w = false;
				return false
			}
		});
		b.bind(
				(a.browser.opera ? "keypress" : "keydown") + ".autocomplete",
				function(y) {
					e = 1;
					u = y.keyCode;
					switch (y.keyCode) {
					case c.UP:
						y.preventDefault();
						if (r.visible()) {
							r.prev()
						} else {
							t(0, true)
						}
						break;
					case c.DOWN:
						y.preventDefault();
						if (r.visible()) {
							r.next()
						} else {
							t(0, true)
						}
						break;
					case c.PAGEUP:
						y.preventDefault();
						if (r.visible()) {
							r.pageUp()
						} else {
							t(0, true)
						}
						break;
					case c.PAGEDOWN:
						y.preventDefault();
						if (r.visible()) {
							r.pageDown()
						} else {
							t(0, true)
						}
						break;
					case g.multiple && a.trim(g.multipleSeparator) == ","
							&& c.COMMA:
					case c.TAB:
					case c.RETURN:
						if (d()) {
							y.preventDefault();
							w = true;
							return false
						}
						break;
					case c.ESC:
						r.hide();
						break;
					default:
						clearTimeout(j);
						j = setTimeout(t, g.delay);
						break
					}
				}).focus(function() {
			e++
		}).blur(function() {
			e = 0;
			if (!x.mouseDownOnSelect) {
				s()
			}
		}).click(function() {
			if (e++ > 1 && !r.visible()) {
				t(0, true)
			}
		}).bind("search", function() {
			var y = (arguments.length > 1) ? arguments[1] : null;
			function z(D, C) {
				var A;
				if (C && C.length) {
					for ( var B = 0; B < C.length; B++) {
						if (C[B].result.toLowerCase() == D.toLowerCase()) {
							A = C[B];
							break
						}
					}
				}
				if (typeof y == "function") {
					y(A)
				} else {
					b.trigger("result", A && [ A.data, A.value ])
				}
			}
			a.each(h(b.val()), function(A, B) {
				f(B, z, z)
			})
		}).bind("flushCache", function() {
			m.flush()
		}).bind("setOptions", function() {
			a.extend(g, arguments[1]);
			if ("data" in arguments[1]) {
				m.populate()
			}
		}).bind("unautocomplete", function() {
			r.unbind();
			b.unbind();
			a(l.form).unbind(".autocomplete")
		}).bind("input", function() {
			t(0, true)
		});
		function d() {
			var B = r.selected();
			if (!B) {
				return false
			}
			var y = B.result;
			p = y;
			if (g.multiple) {
				var E = h(b.val());
				if (E.length > 1) {
					var A = g.multipleSeparator.length;
					var D = a(l).selection().start;
					var C, z = 0;
					a.each(E, function(F, G) {
						z += G.length;
						if (D <= z) {
							C = F;
							return false
						}
						z += A
					});
					E[C] = y;
					y = E.join(g.multipleSeparator)
				}
				y += g.multipleSeparator
			}
			b.val(y);
			v();
			b.trigger("result", [ B.data, B.value ]);
			return true
		}
		function t(A, z) {
			if (u == c.DEL) {
				r.hide();
				return
			}
			var y = b.val();
			if (!z && y == p) {
				return
			}
			p = y;
			y = i(y);
			if (y.length >= g.minChars) {
				b.addClass(g.loadingClass);
				if (!g.matchCase) {
					y = y.toLowerCase()
				}
				f(y, k, v)
			} else {
				n();
				r.hide()
			}
		}
		function h(y) {
			if (!y) {
				return [ "" ]
			}
			if (!g.multiple) {
				return [ a.trim(y) ]
			}
			return a.map(y.split(g.multipleSeparator), function(z) {
				return a.trim(y).length ? a.trim(z) : null
			})
		}
		function i(y) {
			if (!g.multiple) {
				return y
			}
			var A = h(y);
			if (A.length == 1) {
				return A[0]
			}
			var z = a(l).selection().start;
			if (z == y.length) {
				A = h(y)
			} else {
				A = h(y.replace(y.substring(z), ""))
			}
			return A[A.length - 1]
		}
		function q(y, z) {
			if (g.autoFill && (i(b.val()).toLowerCase() == y.toLowerCase())
					&& u != c.BACKSPACE) {
				b.val(b.val() + z.substring(i(p).length));
				a(l).selection(p.length, p.length + z.length)
			}
		}
		function s() {
			clearTimeout(j);
			j = setTimeout(v, 200)
		}
		function v() {
			var y = r.visible();
			r.hide();
			clearTimeout(j);
			n();
			if (g.mustMatch) {
				b.search(function(z) {
					if (!z) {
						if (g.multiple) {
							var A = h(b.val()).slice(0, -1);
							b.val(A.join(g.multipleSeparator)
									+ (A.length ? g.multipleSeparator : ""))
						} else {
							b.val("");
							b.trigger("result", null)
						}
					}
				})
			}
		}
		function k(z, y) {
			if (y && y.length && e) {
				n();
				r.display(y, z);
				q(z, y[0].value);
				r.show()
			} else {
				v()
			}
		}
		function f(z, B, y) {
			if (!g.matchCase) {
				z = z.toLowerCase()
			}
			var A = m.load(z);
			if (A && A.length) {
				B(z, A)
			} else {
				if ((typeof g.url == "string") && (g.url.length > 0)) {
					var C = {
						timestamp : +new Date()
					};
					a.each(g.extraParams, function(D, E) {
						C[D] = typeof E == "function" ? E() : E
					});
					a.ajax( {
						mode : "abort",
						port : "autocomplete" + l.name,
						dataType : g.dataType,
						url : g.url,
						data : a.extend( {
							q : i(z),
							limit : g.max
						}, C),
						success : function(E) {
							var D = g.parse && g.parse(E) || o(E);
							m.add(z, D);
							B(z, D)
						}
					})
				} else {
					r.emptyList();
					y(z)
				}
			}
		}
		function o(B) {
			var y = [];
			var A = B.split("\n");
			for ( var z = 0; z < A.length; z++) {
				var C = a.trim(A[z]);
				if (C) {
					C = C.split("|");
					y[y.length] = {
						data : C,
						value : C[0],
						result : g.formatResult && g.formatResult(C, C[0])
								|| C[0]
					}
				}
			}
			return y
		}
		function n() {
			b.removeClass(g.loadingClass)
		}
	};
	a.Autocompleter.defaults = {
		inputClass : "ac_input",
		resultsClass : "ac_results",
		loadingClass : "ac_loading",
		minChars : 1,
		delay : 400,
		matchCase : false,
		matchSubset : true,
		matchContains : false,
		cacheLength : 10,
		resultSearch : null,
		max : 100,
		mustMatch : false,
		extraParams : {},
		selectFirst : true,
		formatItem : function(b) {
			return b[0]
		},
		formatMatch : null,
		autoFill : false,
		width : 0,
		multiple : false,
		multipleSeparator : ", ",
		highlight : function(c, b) {
			return c.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)("
					+ b.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi, "\\$1")
					+ ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>")
		},
		scroll : true,
		scrollHeight : 180
	};
	a.Autocompleter.Cache = function(c) {
		var g = {};
		var e = 0;
		var d = new Array();
		function i(l, k) {
			if (!c.matchCase) {
				l = l.toLowerCase()
			}
			var j = l.indexOf(k);
			if (c.matchContains == "word") {
				j = l.toLowerCase().search("\\b" + k.toLowerCase())
			}
			if (j == -1) {
				return false
			}
			return j == 0 || c.matchContains
		}
		function h(k, j) {
			if (e > c.cacheLength) {
				b()
			}
			if (!g[k]) {
				e++
			}
			g[k] = j
		}
		function f() {
			if (!c.data) {
				return false
			}
			var k = {}, j = 0;
			if (!c.url) {
				c.cacheLength = 1
			}
			k[""] = [];
			for ( var m = 0, l = c.data.length; m < l; m++) {
				var p = c.data[m];
				p = (typeof p == "string") ? [ p ] : p;
				var o = c.formatMatch(p, m + 1, c.data.length);
				if (o === false) {
					continue
				}
				var n = o.charAt(0).toLowerCase();
				if (!k[n]) {
					k[n] = []
				}
				var q = {
					value : o,
					data : p,
					result : c.formatResult && c.formatResult(p) || o
				};
				k[n].push(q);
				d.push(q);
				if (j++ < c.max) {
					k[""].push(q)
				}
			}
			a.each(k, function(r, s) {
				c.cacheLength++;
				h(r, s)
			})
		}
		setTimeout(f, 25);
		function b() {
			g = {};
			e = 0
		}
		return {
			flush : b,
			add : h,
			populate : f,
			load : function(n) {
				if (!c.cacheLength || !e) {
					return null
				}
				if (!c.url && c.matchContains) {
					var m = [];
					for ( var j in g) {
						if (j.length > 0) {
							var o = g[j];
							a.each(o, function(p, k) {
								if (i(k.value, n)) {
									m.push(k)
								}
							})
						}
					}
					return m
				} else {
					if (typeof (c.resultSearch) == "function") {
						var m = [];
						a.each(d, function(p, q) {
							var k = c.resultSearch(q, n);
							if (k != null && k != false) {
								m.push(k)
							}
						});
						return m
					} else {
						if (g[n]) {
							return g[n]
						} else {
							if (c.matchSubset) {
								for ( var l = n.length - 1; l >= c.minChars; l--) {
									var o = g[n.substr(0, l)];
									if (o) {
										var m = [];
										a.each(o, function(p, k) {
											if (i(k.value, n)) {
												m[m.length] = k
											}
										});
										return m
									}
								}
							}
						}
					}
				}
				return null
			}
		}
	};
	a.Autocompleter.Select = function(e, j, l, p) {
		var i = {
			ACTIVE : "ac_over"
		};
		var k, f = -1, r, m = "", s = true, c, o;
		function n() {
			if (!s) {
				return
			}
			if (a.browser.msie && parseInt(a.browser.version) <= 6) {
				c = a(
						"<div><iframe style='position: absolute; z-index: -1; width: 100%;height: 100%; top: 0;left: 0; scrolling: no;' frameborder='0' src='about:blank'></iframe></div>")
						.hide().addClass(e.resultsClass).css("position",
								"absolute").appendTo(document.body)
			} else {
				c = a("<div/>").hide().addClass(e.resultsClass).css("position",
						"absolute").appendTo(document.body)
			}
			o = a("<ul/>").appendTo(c).mouseover(function(t) {
				if (q(t).nodeName && q(t).nodeName.toUpperCase() == "LI") {
					f = a("li", o).removeClass(i.ACTIVE).index(q(t));
					a(q(t)).addClass(i.ACTIVE)
				}
			}).click(function(t) {
				a(q(t)).addClass(i.ACTIVE);
				l();
				j.focus();
				return false
			}).mousedown(function() {
				p.mouseDownOnSelect = true
			}).mouseup(function() {
				p.mouseDownOnSelect = false
			});
			if (e.width > 0) {
				c.css("width", e.width)
			}
			s = false
		}
		function q(u) {
			var t = u.target;
			while (t && t.tagName != "LI") {
				t = t.parentNode
			}
			if (!t) {
				return []
			}
			return t
		}
		function h(t) {
			k.slice(f, f + 1).removeClass(i.ACTIVE);
			g(t);
			var v = k.slice(f, f + 1).addClass(i.ACTIVE);
			if (e.scroll) {
				var u = 0;
				k.slice(0, f).each(function() {
					u += this.offsetHeight
				});
				if ((u + v[0].offsetHeight - o.scrollTop()) > o[0].clientHeight) {
					o.scrollTop(u + v[0].offsetHeight - o.innerHeight())
				} else {
					if (u < o.scrollTop()) {
						o.scrollTop(u)
					}
				}
			}
		}
		function g(t) {
			f += t;
			if (f < 0) {
				f = k.size() - 1
			} else {
				if (f >= k.size()) {
					f = 0
				}
			}
		}
		function b(t) {
			return e.max && e.max < t ? e.max : t
		}
		function d() {
			o.empty();
			var u = b(r.length);
			for ( var v = 0; v < u; v++) {
				if (!r[v]) {
					continue
				}
				var w = e.formatItem(r[v].data, v + 1, u, r[v].value, m);
				if (w === false) {
					continue
				}
				var t = a("<li/>").html(e.highlight(w, m)).addClass(
						v % 2 == 0 ? "ac_even" : "ac_odd").appendTo(o)[0];
				a.data(t, "ac_data", r[v])
			}
			k = o.find("li");
			if (e.selectFirst) {
				k.slice(0, 1).addClass(i.ACTIVE);
				f = 0
			}
			if (a.fn.bgiframe) {
				o.bgiframe()
			}
		}
		return {
			display : function(u, t) {
				n();
				r = u;
				m = t;
				d()
			},
			next : function() {
				h(1)
			},
			prev : function() {
				h(-1)
			},
			pageUp : function() {
				if (f != 0 && f - 8 < 0) {
					h(-f)
				} else {
					h(-8)
				}
			},
			pageDown : function() {
				if (f != k.size() - 1 && f + 8 > k.size()) {
					h(k.size() - 1 - f)
				} else {
					h(8)
				}
			},
			hide : function() {
				c && c.hide();
				k && k.removeClass(i.ACTIVE);
				f = -1
			},
			visible : function() {
				return c && c.is(":visible")
			},
			current : function() {
				return this.visible()
						&& (k.filter("." + i.ACTIVE)[0] || e.selectFirst
								&& k[0])
			},
			show : function() {
				var v = a(j).offset();
				c
						.css(
								{
									width : typeof e.width == "string"
											|| e.width > 0 ? e.width : a(j)
											.width(),
									top : v.top + j.offsetHeight,
									left : v.left
								}).show();
				if (e.scroll) {
					o.scrollTop(0);
					o.css( {
						maxHeight : e.scrollHeight,
						overflow : "auto"
					});
					if (a.browser.msie
							&& typeof document.body.style.maxHeight === "undefined") {
						var t = 0;
						k.each(function() {
							t += this.offsetHeight
						});
						var u = t > e.scrollHeight;
						o.css("height", u ? e.scrollHeight : t);
						if (!u) {
							k.width(o.width() - parseInt(k.css("padding-left"))
									- parseInt(k.css("padding-right")))
						}
					}
				}
			},
			selected : function() {
				var t = k && k.filter("." + i.ACTIVE).removeClass(i.ACTIVE);
				return t && t.length && a.data(t[0], "ac_data")
			},
			emptyList : function() {
				o && o.empty()
			},
			unbind : function() {
				c && c.remove()
			}
		}
	};
	a.fn.selection = function(i, b) {
		if (i !== undefined) {
			return this.each(function() {
				if (this.createTextRange) {
					var j = this.createTextRange();
					if (b === undefined || i == b) {
						j.move("character", i);
						j.select()
					} else {
						j.collapse(true);
						j.moveStart("character", i);
						j.moveEnd("character", b);
						j.select()
					}
				} else {
					if (this.setSelectionRange) {
						this.setSelectionRange(i, b)
					} else {
						if (this.selectionStart) {
							this.selectionStart = i;
							this.selectionEnd = b
						}
					}
				}
			})
		}
		var g = this[0];
		if (g.createTextRange) {
			var c = document.selection.createRange(), h = g.value, f = "<->", d = c.text.length;
			c.text = f;
			var e = g.value.indexOf(f);
			g.value = h;
			this.selection(e, e + d);
			return {
				start : e,
				end : e + d
			}
		} else {
			if (g.selectionStart !== undefined) {
				return {
					start : g.selectionStart,
					end : g.selectionEnd
				}
			}
		}
	}
})(jQuery);
