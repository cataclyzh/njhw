 function changeVedioByIp(ip) {
	var wndNum = parent.getWndSum();
	var checkbox = document.getElementById(ip);
	var wndId = parent.getSelWnd();
	var freeWndId = parent.getFreePreviewWnd();
	if (checkbox.checked) {
		var isWndPreview = parent.isWndPreview(wndId);
		if (-1 == freeWndId) {
			var textIP = parent.document.getElementById('TextIP' + wndId);
			textIP.value = ip;
			parent.StartPlayView(wndId);
			
		} else {
			var textIP = parent.document.getElementById('TextIP' + freeWndId);
			textIP.value = ip;
			parent.StartPlayView(freeWndId);
		}
	} else {
		parent.stopRealPlay(wndId);
	}
}