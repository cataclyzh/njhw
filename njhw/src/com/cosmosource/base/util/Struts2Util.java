
package com.cosmosource.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Struts2工具类.
 * 
 * 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 * 
 * @author WXJ
 */
public class Struts2Util {
	//-- header 常量定义 --//
	private static final String HEADER_ENCODING = "encoding";
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final boolean DEFAULT_NOCACHE = true;

	private static ObjectMapper mapper = new ObjectMapper();

	//-- 取得Request/Response/Session的简化函数 --//
	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession(boolean isNew) {
		return ServletActionContext.getRequest().getSession(isNew);
	}

	/**
	 * 取得HttpSession中Attribute的简化函数.
	 */
	public static Object getSessionAttribute(String name) {
		HttpSession session = getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpRequest中Parameter的简化方法.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * 取得HttpResponse的简化函数.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	//-- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * eg.
	 * render("text/plain", "hello", "encoding:gb2312");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:gb2312", "no-cache:false");
	 * 
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final String content, final String... headers) {
		HttpServletResponse response = initResponseHeader(contentType, headers);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出内容的简便函数.
	 * eg.
	 * render("text/plain", "hello", "encoding:gb2312");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:gb2312", "no-cache:false");
	 * 
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final int content, final String... headers) {
		HttpServletResponse response = initResponseHeader(contentType, headers);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 直接输出文本.
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render(ServletUtil.TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(ServletUtil.HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render(ServletUtil.XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString, final String... headers) {
		render(ServletUtil.JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON,使用Jackson转换Java对象.
	 * 
	 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object data, final String... headers) {
		HttpServletResponse response = initResponseHeader(ServletUtil.JSON_TYPE, headers);
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	/**
	 * 输出JSON数据到文件中,使用Jackson转换Java对象.
	 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 */
	public static void renderJsonFile(final String filePath,final Object data) {
		try {
		//	FileUtil.createDirs(filePath, true);
			
			File f = new File(filePath);
			if (!f.exists()) {
				f.createNewFile();
			}
			mapper.writeValue(f, data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param callbackName callback函数名.
	 * @param object Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
	 */
	public static void renderJsonp(final String callbackName, final Object object, final String... headers) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		String result = new StringBuilder().append(callbackName).append("(").append(jsonString).append(");").toString();

		//渲染Content-Type为javascript的返回内容,输出结果为javascript语句, 如callback197("{html:'Hello World!!!'}");
		render(ServletUtil.JS_TYPE, result, headers);
	}
	/**
	 * 输出JSON数据到文件中,使用Jackson转换Java对象.
	 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 */
	public static void renderStream(final InputStream is,final String fileName,final String... headers) {
		HttpServletResponse response = initResponseHeader(ServletUtil.STREAM_TYPE, headers);
		response.resetBuffer();
		ServletOutputStream out = null;
		try {
			if(fileName!=null){
				ServletUtil.setFileDownloadHeader(response, fileName);
			}
			out = response.getOutputStream();
			int i;
			while ((i = is.read()) != -1) {
				out.write(i);
			}
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out); 
			IOUtils.closeQuietly(is); 
		}
	}
	/**
	 * 输出JSON数据到文件中,使用Jackson转换Java对象.
	 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 */
	public static void renderStream(final byte[] is,final String fileName,final String... headers) {
		HttpServletResponse response = initResponseHeader(ServletUtil.STREAM_TYPE, headers);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			if(fileName!=null){
				ServletUtil.setFileDownloadHeader(response, fileName);
			}
			int i;
			while ((i = is.length) != -1) {
				out.write(i);
			}
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out); 
		}
	}
	/**
	 * 输出JSON数据到文件中,使用Jackson转换Java对象.
	 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 */
	public static void renderFile(final String fileDir,final String fileName,final String... headers) {
		HttpServletResponse response = initResponseHeader(ServletUtil.STREAM_TYPE, headers);
		FileInputStream is = null;
		ServletOutputStream out = null;
		try {
			is = new FileInputStream(fileDir);
			out = response.getOutputStream();
			if(fileName!=null){
				ServletUtil.setFileDownloadHeader(response, fileName);
			}
			int i;
			while ((i = is.read()) != -1) {
				out.write(i);
			}
			out.flush();
		} catch (IOException e) {
			//throw new RuntimeException(e.getMessage(), e);
			//System.out.println("找不到文件！！");
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out); 
			IOUtils.closeQuietly(is); 
		}
	}
	/**
	 * 分析并设置contentType与headers.
	 */
	private static HttpServletResponse initResponseHeader(final String contentType, final String... headers) {
		//分析headers参数
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		HttpServletResponse response = ServletActionContext.getResponse();
//		String fileName = null;
		for (String header : headers) {
			String headerName = StringUtils.substringBefore(header, ":");
			String headerValue = StringUtils.substringAfter(header, ":");

			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else{
				response.addHeader(headerName, headerValue);
			}
			
//			else {
//				throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
//			}
			
		}

		//设置headers参数
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			ServletUtil.setNoCacheHeader(response);
		}
		
		return response;
	}
	/**
	 * 取得ctx path
	 */
	public static String getContextPath() {
		return getRequest().getContextPath();
	}
    /**
     *Description : 根据spring配置文件中的名字取得生成的管理类,
     *				也就是“依赖注入”中的“接口注入”来取得
     *				管理类的实例对象，再通过此对象调用相应的方法来实现你的功能
     *
     * @param name
     * 
     * @return Object bean
     */
    public static Object getBean(String name) {
        ApplicationContext ctx = 
            WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        return ctx.getBean(name);
    }

}
