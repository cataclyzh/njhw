// 获取对象属性
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
function btn_hover(dom)
{
    dom.className = attr(dom, "name") + "_hover";
}
function btn_out(dom)
{
    dom.className = attr(dom, "name");
}
function list_hover(dom, num)
{
	if(dom.style.background.toLowerCase().indexOf("ffe2a2") != -1)
		return false;
    if(num == 0)
        dom.style.background = "#d6ddfc";
    else
        dom.style.background = "";
}