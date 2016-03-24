package com.cosmosource.base.util;
import java.util.List;      
import java.util.Map;
       
/**    
 * 响应对象    
 * @author WXJ   
 */     
public class HttpRespons {      
       
	 String urlString;      
     int defaultPort;      
     String file;      
     String host;      
     String path;      
     int port;      
     String protocol;      
     String query;      
     String ref;      
      String userInfo;      
     String contentEncoding;      
     String content;      
     String contentType;      
     int code;      
     String message;      
     String method;      
     int connectTimeout;      
     int readTimeout;      
     List<String> contentCollection;    
     Map<String,List<String>> headerFields;
     byte[] outBytes;
       
    public byte[] getOutBytes() {
		return outBytes;
	}

	public void setOutBytes(byte[] outBytes) {
		this.outBytes = outBytes;
	}

	public Map<String, List<String>> getHeaderFields() {
		return headerFields;
	}

	public void setHeaderFields(Map<String, List<String>> headerFields) {
		this.headerFields = headerFields;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public void setDefaultPort(int defaultPort) {
		this.defaultPort = defaultPort;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setContentCollection(List<String> contentCollection) {
		this.contentCollection = contentCollection;
	}

	public String getContent() {      
        return content;      
    }      
       
    public String getContentType() {      
        return contentType;      
    }      
       
    public int getCode() {      
        return code;      
    }      
       
    public String getMessage() {      
        return message;      
    }      
       
    public List<String> getContentCollection() {      
        return contentCollection;      
    }      
       
    public String getContentEncoding() {      
        return contentEncoding;      
    }      
       
    public String getMethod() {      
        return method;      
    }      
       
    public int getConnectTimeout() {      
        return connectTimeout;      
    }      
       
    public int getReadTimeout() {      
        return readTimeout;      
    }      
       
    public String getUrlString() {      
        return urlString;      
    }      
       
    public int getDefaultPort() {      
        return defaultPort;      
    }      
       
    public String getFile() {      
        return file;      
    }      
       
    public String getHost() {      
        return host;      
    }      
       
    public String getPath() {      
        return path;      
    }      
       
    public int getPort() {      
        return port;      
    }      
       
    public String getProtocol() {      
        return protocol;      
    }      
       
    public String getQuery() {      
        return query;      
    }      
       
    public String getRef() {      
        return ref;      
    }      
       
    public String getUserInfo() {      
        return userInfo;      
    }      
       
}     