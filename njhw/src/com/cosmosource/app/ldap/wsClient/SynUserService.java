package com.cosmosource.app.ldap.wsClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * <p>
 * An example of how this class may be used:
 * 
 * <pre>
 * SynUserService service = new SynUserService();
 * SynUserDelegate portType = service.getSynUserPort();
 * portType.updateUser(...);
 * </pre>
 * 
 * </p>
 * 
 */
@WebServiceClient(name = "SynUserService", targetNamespace = "http://action.ldap.fujitsu.com/")
public class SynUserService extends Service {

	private final static URL SYNUSERSERVICE_WSDL_LOCATION;
	private static String LDAP_WS_WSDL=getProperties("LDAP_WS_WSDL");
	private final static Logger logger = Logger
			.getLogger(com.cosmosource.app.ldap.wsClient.SynUserService.class
					.getName());

	static {
		URL url = null;
		try {
			URL baseUrl;
			baseUrl = com.cosmosource.app.ldap.wsClient.SynUserService.class
					.getResource(".");
			url = new URL(baseUrl,
					LDAP_WS_WSDL);
		} catch (MalformedURLException e) {
			logger.warning("Failed to create URL for the wsdl Location: '"+LDAP_WS_WSDL+"', retrying as a local file");
			logger.warning(e.getMessage());
		}
		SYNUSERSERVICE_WSDL_LOCATION = url;
	}

	public SynUserService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public SynUserService() {
		super(SYNUSERSERVICE_WSDL_LOCATION, new QName(
				"http://action.ldap.fujitsu.com/", "SynUserService"));
	}

	/**
	 * 
	 * @return returns SynUserDelegate
	 */
	@WebEndpoint(name = "SynUserPort")
	public SynUserDelegate getSynUserPort() {
		return super.getPort(new QName("http://action.ldap.fujitsu.com/",
				"SynUserPort"), SynUserDelegate.class);
	}
	
	private static String getProperties(String name) {
		InputStream in = SynUserService.class.getResourceAsStream(
				"../config/db.properties");
		Properties pro = new Properties();
		String value = null;
		try {
			pro.load(in);
			value = pro.getProperty(name).trim();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

}