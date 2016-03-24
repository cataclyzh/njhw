/*
function test()
{
	var url = 'resource/server/view.aspx?command=objlist&p_id=4&filter=F';
	var ajax =  new Ajax(url, callback_test); 
	var xml = ajax.doGet();
	//ajax.doPost(xml);
	
}
function callback_test(xml)
{alert(xml)
	var xmlobj = DataSet(xml);
}
*/
// xml��ʽ����
function take_error(xml)
{
	if(xml == "" ) return true ; 
	if(xml.substr(0,2) == "����")
	{
		alert(xml);
		return false;
	}
	if(xml.substr(0,4) == "�Ự����" || xml.substr(0,3) == "�û���")
	{
		alert(xml);
		// ��תlogin
		var obj = get_page_root(window);
		// ��ȡ��ǰĿ¼·��
		var nowhref = window.location.href;
		var hrefArr = nowhref.split("/");
		// ��ǰĿ¼
		var newhref = "";
		for(var i = 0; i < hrefArr.length; i++)
		{
			newhref += hrefArr[i] + "/";
			// ȡ��Ŀ¼���һ��
			if(i == 3)
				break;
		}
		obj.location.href = newhref + "mxe/login.htm";
		return false
	}
	var xmldom = LoadXml(xml);
	if(xmldom == null)
	{
		alert(xml);
		return false
	}
	return true ; 
}

// ����ģʽ�������������ʱ�Ĵ�����ʾ���Ự����
function take_error_md(xml)
{
	if(xml == "" ) return true ; 
	if(xml.substr(0,2) == "����")
	{
		alert(xml);
		return false;
	}
	if(xml.substr(0,4) == "�Ự����" || xml.substr(0,3) == "�û���")
	{
		alert(xml);
		return false
	}
	var xmldom = LoadXml(xml);
	if(xmldom == null)
	{
		alert(xml);
		return false
	}
	return true ; 
}

// ������ͨ�ִ�
function take_error_simp(xml)
{
	if(xml == "" ) return true ; 
	if(xml.substr(0,2) == "����")
	{
		alert(xml);
		return false;
	}
	if(xml.substr(0,4) == "�Ự����" || xml.substr(0,3) == "�û���")
	{
		alert(xml);
		return false
	}
	return true ; 
}
// ƽ̨����Ȩ��
function check_func_mdp_mgt()
{
	check_func('mdp_mgt');
}
// ����Ȩ��
function check_func(perm_code)
{
	// ��ȡ��ǰĿ¼·��
	var nowhref = window.location.href;
	var hrefArr = nowhref.split("/");
	// ��ǰĿ¼
	var newhref = "";
	for(var i = 0; i < hrefArr.length; i++)
	{
		newhref += hrefArr[i] + "/";
		// ȡ��Ŀ¼���һ��
		if(i == 3)
			break;
	}
	// �˶Թ���Ȩ��
	url = newhref + "mxe/permissions/server/view.aspx?command=checkfunc&perm_code=" + perm_code + "&t=" + Math.random();
	var ajax =  new Ajax(url);
	var xml = ajax.doGet();
	if(xml == 1)
		return true;	
	else
	{
		if(xml == "" )
			return true ; 
		if(xml.substr(0,2) == "����")
		{
			alert(xml);
			return false;
		}
		if(xml.substr(0,4) == "�Ự����" || xml.substr(0,3) == "�û���")
		{
			alert(xml);
			// ��תlogin
			var obj = get_page_root(window);
			// ��ȡ��ǰĿ¼·��
			var nowhref = window.location.href;
			var hrefArr = nowhref.split("/");
			// ��ǰĿ¼
			var newhref = "";
			for(var i = 0; i < hrefArr.length; i++)
			{
				newhref += hrefArr[i] + "/";
				// ȡ��Ŀ¼���һ��
				if(i == 3)
					break;
			}
			obj.location.href = newhref + "mxe/login.htm";
			return false
		}
		window.location.href = newhref + "mxe/permissions/error_info.htm?perm_name=" + escape(document.title) + "&t=" + Math.random();
	}
}


// �õ������iframe	
function get_page_root(obj)
{
	if(obj.parent == null || obj.parent == obj)
		return obj;
	else
		return get_page_root(obj.parent);
}

// �����ļ�ͼƬ����
function ImageSet(table_type)
{
	var img = "jpg,bmp,gif,png,jpeg,ico,icon,img";
	table_type = table_type.replace('.', '');
	if(table_type == "f")
		imgtype = "folder.gif";
	else if(table_type == "mxr")
		imgtype = "report.gif";
	else if(table_type == "xls" || table_type == "xlsx")
		imgtype = "excel.gif";
	else if(table_type == "pdf")
		imgtype = "pdf.gif";
	else if(table_type == "doc" || table_type == "docx")
		imgtype = "word.gif";
	else if(table_type == "ppt" || table_type == "pptx")
		imgtype = "powerpoint.gif";
	else if(table_type == "txt")
		imgtype = "txt.gif";
	else if(img.indexOf(table_type) != -1)
		imgtype = "pic.gif";
	else if(table_type == "css")
		imgtype = "css.gif";
	else if(table_type == "htm" || table_type == "html")
		imgtype = "html.gif";
	else if(table_type == "js")
		imgtype = "script.gif";
	else if(table_type == "menu")
		imgtype = "menu.gif";
	else if(table_type == "map")
		imgtype = "map.gif";
	else if(table_type == "mapt")
		imgtype = "maps.gif";
	else if(table_type == "olap")
		imgtype = "olap.gif";
	else if(table_type == "websvr")
		imgtype = "websvr.gif";
	else if(table_type == "link")
		imgtype = "link.gif";
	else if(table_type == "mind")
		imgtype = "mind.gif";
	else if(table_type == "mxls")
		imgtype = "mxls.gif";
	else if(table_type == "eid")
		imgtype = "eid.gif";
	else if(table_type == "mxr")
		imgtype = "report.gif";
	else if(table_type == "mswc")
		imgtype = "chart.gif";
	else if(table_type == "mswg")
		imgtype = "gauge_main.gif";
	else if(table_type == "uboard")
		imgtype = "userboard.gif";
	else if(table_type == "board")
		imgtype = "dashboard.gif";
	else if(table_type == "appt")
		imgtype = "ppt.gif";
	else if(table_type == "fpd")
		imgtype = "ded.gif";
	else
		imgtype = "object.gif";
	return imgtype;
}
// ��׼��ʽxmlת��ݶ���
// arry[0].nodeid
function DataSet(xml)
{
	if(!xml) return null ; 
	else
	{
		var xmlobj = LoadXml(xml);
		var arry = new Array();
		for(var i=0; i<xmlobj.getElementsByTagName("Table").length; i++)
		{
			var tab = xmlobj.getElementsByTagName("Table")[i];
		    arry[i] = new Array();
			for(var j=0; j<tab.childNodes.length; j++)
			{
				var node = tab.childNodes[j];
			    arry[i][node.tagName] = node.text;
			}
		}
		return arry;
	}
}


// �첽������
// url:���ʵ�ַ
// callback:�ص�����
function Ajax(url, callback) 
{  
	// ��ʼ�����ʶ���
    var req = init();  
    var ansy = false;
	if(callback != null)
	{
		req.onreadystatechange = processRequest;  
		ansy = true;
	}
	
	// ��ʼ�����ʶ���
	function init() 
	{        
		if (window.XMLHttpRequest)  
		return new XMLHttpRequest();  
		else if (window.ActiveXObject)   
		return new ActiveXObject("Microsoft.XMLHTTP");  
	}  
	// ����״̬
	function processRequest() 
	{  
		if (req.readyState == 4)   
		{  
			if(req.status == 200)
			{
				if (callback) 
				{
					callback(req.responseText);
				}  
			}
			else if(req.status == 404)
			{
				alert("HTTP ���� 404 - �ļ���Ŀ¼δ�ҵ���");
				if (callback) 
				{
					callback("false");
				}  
				return false;
			}
			else
			{
				alert("HTTP ����");
				if (callback) 
				{
					callback("false");
				} 
				return false;
			}
		}
		if(req.readyState == 1) 
		{
			//if(onloading) onloading();
		}
	} 
	// �õ�����(�첽)
	this.doGet = function() 
	{
		req.open("GET", url, ansy);  
		req.setRequestHeader("Cache-Control", "no-cache"); 
		req.setRequestHeader("Pragma", "no-cache");
		req.send(null); 
		if(callback == null)
			return req.responseText;         
	}  
	// ��������(�첽)
	this.doPost = function(body) 
	{  
		req.open("POST", url, ansy);  
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
		req.send(body); 
		if(callback == null)
			return req.responseText;     
	}  
} 

// ��������ִ� 
var Request = new function()
{
	// strQueryString �ɷ�������ɣ���?objpaht=xxxx&tmp=cccc���ɷ������������Ķ�ȡ����
	if (typeof(strQueryString) != 'undefined')
	{
		// viewʱ���������ġ�
		this.search = strQueryString;
	}
	else
	{
		this.search = window.location.search;
	}
	
	// �����ֵ�
	this.QueryString = new Array();
	this.Key = new Array();
	
	// д������ֵ�
	var tmparray = this.search.substr(1,this.search.length).split("&")
	var tmp = '';
	for(var i = 0;i<tmparray.length;i++)
	{
		var reg = /[=|^==]/;    // ��=���в�֣���������==
		var set1 = tmparray[i].replace(reg,'&');
		var tmpStr2 = set1.split('&');

		this.QueryString[tmpStr2[0]] = tmpStr2[1];
		this.Key[this.Key.length] = tmpStr2[0];
		
		if (tmpStr2[0] != undefined)
			tmp += "&"+ tmpStr2[0] + "=" + (tmpStr2[1]);
	}	
	this.search = "?" + tmp;
	
	// ���¶�ȡURL
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

// ͨ���ַ�õ�xml����
// xml:xml��ʽ���ַ�
// ����ֵ��xml��ʽ����
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

// ���������: ��������
// ΪXML�ṹ����xml��text����
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


// ���������: ��������
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

// ���������: �����¼�
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


// ���������: ɾ���¼�
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

// ȥ�ַ�ո�
String.prototype.trim = function() {    
    return this.replace(/^\s+/g,"").replace(/\s+$/g,"");    
} 

function GetHttpSvr()
{
	if (window.XMLHttpRequest)  
	return new XMLHttpRequest();  
	else if (window.ActiveXObject)   
	return new ActiveXObject("MSXML2.XMLHTTP");  
}

//jsȥ��ո�
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
String.prototype.LTrim = function() 
{ 
	return this.replace(/(^\s*)/g, ""); 
} 
String.prototype.RTrim = function() 
{ 
	return this.replace(/(\s*$)/g, ""); 
}

// getElementsByName
function fixedGetElementsByName(tag, name) {
    var tagArr = document.getElementsByTagName(tag);
    var elements = new Array();     
    for (var i = 0; i < tagArr.length; i++)
    {
        var att = attr(tagArr[i] , "name");
        if (att == name) {
            elements.push(tagArr[i]);
        }
    }
    return elements;
}

// �����ͻ�ȡinputԪ��
function fixedGetElementsByType(tag , type)
{
	var tagArr = document.getElementsByTagName(tag);
    var elements = new Array();     
    for (var i = 0; i < tagArr.length; i++)
    {
        if (tagArr[i].type == type) {
            elements.push(tagArr[i]);
        }
    }
    return elements;
}