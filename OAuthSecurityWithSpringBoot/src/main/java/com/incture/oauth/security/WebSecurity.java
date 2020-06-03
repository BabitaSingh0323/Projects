package com.incture.oauth.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Service;

@Service
public class WebSecurity {

	public String getAssertion(String clientId,String userId,String tokenURL,String privateKey){
		
		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(HttpClientContext.COOKIE_STORE, new BasicCookieStore());
		HttpGet httpGet = null;
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		CloseableHttpResponse responsePost = null;// Babita please check this.
		CloseableHttpClient httpClient = null;
		httpClient = getHTTPClient();
		System.out.println("getassertion");
		try {
			URIBuilder uriBuilder = new URIBuilder(SecurityConstants.IDP);
			String userpass = null + ":"+ null;
			httpPost.setHeader(HttpHeaders.AUTHORIZATION,
					"Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes()));
			System.out.println(uriBuilder);
			String postRespBody = "";
			
			ByteArrayOutputStream bytesForPost = new ByteArrayOutputStream();
			InputStream inputStreamPost = responsePost.getEntity().getContent();
			byte[] postData = new byte[1024];
			int postLength = 0;
			while ((postLength = inputStreamPost.read(postData)) > 0) {
				bytesForPost.write(postData, 0, postLength);
			}
			
			postRespBody=new String(bytesForPost.toByteArray(), "UTF-8");
			
			System.out.println(postRespBody);
			String node =null;
			
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		} catch (IllegalStateException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return privateKey;
		
	}
	private static CloseableHttpClient getHTTPClient() {

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = clientBuilder.build();
		return httpClient;
	}
}
