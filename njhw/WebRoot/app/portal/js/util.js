// 兼容浏览器: 删除事件
function DetachEvent(target, eventName, handler, argsObject)   
{   
    var eventHandler = handler;   
    if(argsObject)   
    {   
        eventHander = function(e)   
        {   
            handler.call(argsObject, e);   
        }   
    }  
    eventName = eventName.replace('on', '');
    if(window.attachEvent)//IE   
        target.detachEvent("on" + eventName, eventHandler);   
    else//FF   
        target.removeEventListener(eventName, eventHandler, false);   
}
// 兼容浏览器: 加入事件
function AttachEvent(target, eventName, handler, argsObject)   
{   
    var eventHandler = handler;   
    if(argsObject)   
    {   
        eventHander = function(e)   
        {   
            handler.call(argsObject, e);   
        }   
    }  
    eventName = eventName.replace('on', '');
    if(window.attachEvent)//IE   
        target.attachEvent("on" + eventName, eventHandler);   
    else//FF   
        target.addEventListener(eventName, eventHandler, false);   
}
// 获得链接字串 
var Request = new function()
{
	// strQueryString 由服务器生成，如?objpaht=xxxx&tmp=cccc，由服务器处理中文读取乱码
	if (typeof(strQueryString) != 'undefined')
	{
		// view时，处理中文。
		this.search = strQueryString;
	}
	else
	{
		this.search = window.location.search;
	}
	
	// 访问字典
	this.QueryString = new Array();
	this.Key = new Array();
	
	// 写入数据字典
	var tmparray = this.search.substr(1,this.search.length).split("&")
	var tmp = '';
	for(var i = 0;i<tmparray.length;i++)
	{
		var reg = /[=|^==]/;    // 用=进行拆分，但不包括==
		var set1 = tmparray[i].replace(reg,'&');
		var tmpStr2 = set1.split('&');

		this.QueryString[tmpStr2[0]] = tmpStr2[1];
		this.Key[this.Key.length] = tmpStr2[0];
		
		if (tmpStr2[0] != undefined)
			tmp += "&"+ tmpStr2[0] + "=" + (tmpStr2[1]);
	}	
	this.search = "?" + tmp;
	
	// 重新读取URL
	this.ReSet = function()
	{
		var tmp = "";
		for(var i=0; i<this.Key.length; i++)
		{
			tmp += "&" + this.Key[i] + "=" + this.QueryString[this.Key[i]]; 
		}
		
		this.search = "?" + tmp;
	}
}
// 兼容浏览器: 属性设置
function attr(obj, key, val)
{
	if(val != null)
	{
		obj.setAttribute(key,val);
		return null;
	}
	if(obj.attributes == null)
		return null;
	if(obj.attributes[key] != null)
	{
		return obj.attributes[key].value;
	}
	return null;
}
// 异步操作类
// url:访问地址
// callback:回调函数
function Ajax(url, callback) 
{  
	// 初始化访问对象
    var req = init();  
    var ansy = false;
	if(callback != null)
	{
		req.onreadystatechange = processRequest;  
		ansy = true;
	}
	
	// 初始化访问对象
	function init() 
	{        
		if (window.XMLHttpRequest)  
		return new XMLHttpRequest();  
		else if (window.ActiveXObject)   
		return new ActiveXObject("Microsoft.XMLHTTP");  
	}  
	// 访问状态
	function processRequest() 
	{  
		if (req.readyState == 4)   
		{  if (req.status == 200)
			if (callback) 
			{
				callback(req.responseText);
			}  
		}
		if(req.readyState == 1) 
		{
			//if(onloading) onloading();
		}
	} 
	// 得到请求(异步)
	this.doGet = function() 
	{  
		req.open("GET", url, ansy);  
		req.setRequestHeader("Cache-Control", "no-cache"); 
		req.setRequestHeader("Pragma", "no-cache");
		req.send(null); 
		if(callback == null)
			return req.responseText;         
	}  
	// 发送请求(异步)
	this.doPost = function(body) 
	{  
		req.open("POST", url, ansy);  
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		req.send(body); 
		if(callback == null)
			return req.responseText;     
	}  
} 


// 从服务器端得到 数据
// 返回服务器输出信息text
function GetInfoFromServer(url)
{
	var httpobj = GetHttpSvr();
	httpobj.open("POST",url,false);
	httpobj.setRequestHeader("Cache-Control","no-cache"); 
    httpobj.setRequestHeader("Pragma","no-cache");
	httpobj.send();
	return httpobj.responseText;
}
function GetHttpSvr()
{
	if (window.XMLHttpRequest)  
	return new XMLHttpRequest();  
	else if (window.ActiveXObject)   
	return new ActiveXObject("MSXML2.XMLHTTP");  
}

// 发送信息 得到数据集
// url:读取的地址
// strsend:向服务器发送的内容
// 返回服务器输出信息text
function SendInfoToSever(url, strsend)
{
	var httpobj = GetHttpSvr();
	httpobj.open("POST", url, false);

	httpobj.setRequestHeader("Content-Length",strsend.length);
	httpobj.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded; charset=gb2312");

	httpobj.send(strsend);
	
	return httpobj.responseText;
}
// 从服务器端得到 数据
// callback:异步回调函数
// url:服务器端访问的url
function GetInfoFromServerAsyn(url, callback)
{
	function processRequest() 
	{  
       if (httpobj.readyState == 4)   
       {  if (httpobj.status == 200)
          if (callback) 
          {
			callback(httpobj.responseText);
          }  
       }
       if(httpobj.readyState ==1) 
       {
			//if(onloading) onloading();
       }
    } 
	var httpobj;  
	httpobj= GetHttpSvr(); 
	httpobj.onreadystatechange = processRequest;
	httpobj.open("POST", url, true);
	httpobj.send();
	//return httpobj.responseText;
}
// 通得字符串得到xml对象
// xml:xml格式的字符串
// 返回值：xml格式对象
function LoadXml(xml)
{
	if(xml.indexOf('<') == -1)return null;
	if (window.ActiveXObject)   
	{
		var xmlDoc = new ActiveXObject("Msxml2.DOMDocument");
		xmlDoc.async = false;
		xmlDoc.loadXML(xml);
		var obj = xmlDoc.documentElement;
		return obj;
	} 
	else if (window.XMLHttpRequest)  
	{
		var oParser = new DOMParser();
		var oXmlDom = oParser.parseFromString(xml,"text/xml");
		commXmlDoc(oXmlDom.childNodes[0]);
		return oXmlDom.childNodes[0];	
	} 
}
// 兼容浏览器:返回xml对象
function getXmlDoc()
{
	var xmlDoc;
	if (window.ActiveXObject)   
	{
		xmlDoc = new ActiveXObject("Msxml2.DOMDocument");
	}
	else if (window.XMLHttpRequest)  
	{
		xmlDoc = new DOMParser();
	}
	return xmlDoc;
}
if(window.Element){
  !Element.prototype.clearAttribute && (Element.prototype.clearAttributes = function(){
    var attrs = this.attributes,
      i = attrs.length - 1;
    for(;i>=0;i--){
      var name = attrs[i].name.toLowerCase();
      if(/id|style/.test(name) || (/on[a-zA-Z]+/.test(name) && typeof this[name] === 'function'))
        continue;
      this.removeAttribute(name);
    }
  })
  
  !Element.prototype.mergeAttribute && (Element.prototype.mergeAttributes = function(src){
    var bPreserve = arguments[1] === undefined ? true : arguments[1],
      attrs = src.attributes,
      i = attrs.length - 1;
    for(;i>=0;i--){
      var name = attrs[i].name;
      if(bPreserve && name.toLowerCase() === 'id')
        continue;
      this.setAttribute(name, attrs[i].value);
    }
  })
} 
// 兼容浏览器: 设置属性
// 为XML结构设置xml和text属性
function commXmlDoc(xItems)
{
	var xml = '';
	if(xItems.nodeName.indexOf('#') != -1) return xItems.textContent;
	if(xItems.nodeName.indexOf('-') != -1) return xItems.textContent;
	xml = '<' + xItems.nodeName + " "
	for(var i=0; i<xItems.attributes.length; i++)
	{
		var attr = xItems.attributes[i];
		xml += attr.nodeName + '="' + attr.nodeValue + '" ';
	}
	xml += '>';
	for(var m=0; m<xItems.childNodes.length; m++)
	{
		xml += commXmlDoc(xItems.childNodes[m]);
	}
	xml += '</' + xItems.nodeName + ">";
	xItems.xml = xml;
	xItems.text = xItems.textContent;
	return xml;
}
if( document.implementation.hasFeature("XPath", "3.0") ) 
{ 
	// prototying the XMLDocument 
	XMLDocument.prototype.selectNodes = function(cXPathString, xNode) 
	{ 
		if( !xNode ) 
		{ xNode = this; } 
		var oNSResolver = this.createNSResolver(this.documentElement);
		var aItems = this.evaluate(cXPathString, xNode, oNSResolver, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null) ;
		var aResult = []; 
		for( var i = 0; i < aItems.snapshotLength; i++) { aResult[i] = aItems.snapshotItem(i); } 
		return aResult; 
	} 
	// prototying the Element 
	Element.prototype.selectNodes = function(cXPathString) 
	{ 
		if(this.ownerDocument.selectNodes) 
		{ return this.ownerDocument.selectNodes(cXPathString, this); } 
		else{ throw "For XML Elements Only"; } 
	} 
	Element.prototype.xml = "";
	Element.prototype.text = "";
}
