window.onload = inits;
function inits()
{
	//document.getElementById("loginframe").src = loginurl;
	if(menuKeywordStr)
	{
		var wordStr = menuKeywordStr;
		var wordarr = wordStr.split("@_@");
	}
	if(urlStr)
	{
		var linkStr = urlStr;
		var linkarr = linkStr.split("@_@");
	}
	var LinkDom = document.getElementsByTagName("a");
	var len = LinkDom.length;
	for(var i = 0; i < len; i++)
	{
		var judge = 0;
		if(menuKeywordStr && urlStr)
		{
			for(var j = 0; j < wordarr.length; j++)
			{
				if(wordarr[j] == LinkDom[i].id)
				{
					citylink = linkarr[j];
					var judge = 1;
					break;
				}
			}
		}
		if(judge == 0)
		{
			LinkDom[i].style.background = "url(images/" + LinkDom[i].className + "_Grey" + ".png)";
			LinkDom[i].style.cursor = "default";
			LinkDom[i].href = "javascript:void(0)"
			continue;
		}
		LinkDom[i].href = citylink;
		LinkDom[i].onmouseover = function()
		{
			this.style.background = "url(images/highlight/" + this.className + "_hover.png)";
		}
		LinkDom[i].onmouseout = function()
		{
			this.style.background = "url(images/" + this.className + ".png)";
		}
	}
}

