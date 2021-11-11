package com.comingm.roomcheck.common.util;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import javax.net.ssl.SSLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Component
public class WebClientUtil {

	@Value("${xrcloud.host}")
	private String apiServer;
	
	@Value("${spring.profiles.active}")
	private String codeLevel;
	
	public Object post(String uri,String xrcloudApiKey) throws ParseException {
		
		WebClient client;
		
		if(codeLevel.equals("dev")) {
			HttpClient httpClient = HttpClient.create().secure(t -> { 
				try { 
					t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build()); 
				} catch (SSLException e) { 
					e.printStackTrace();
				} 
			});		
							
			 	client = WebClient.builder()
								.baseUrl(apiServer)
								.clientConnector(new ReactorClientHttpConnector(httpClient)) 
								.build();
			}else {
				
				client = WebClient.create(apiServer);
								
			}
				
	
		
		
		JSONParser jsonParser = new JSONParser();
		String str =  client.post()
						.uri(uri)
						.accept(APPLICATION_JSON)
						.headers(headers ->{
							headers.add("xrcloud-api-key", xrcloudApiKey);
						})			
						.retrieve()
						.bodyToMono(String.class)
						.block();
		
		Object obj = jsonParser.parse(str);
		JSONObject jsonObj = (JSONObject) obj;
		return jsonObj.get("data");	
	}
	
		
	public Object post(String uri, String xrcloudApiKey, Object dto) throws ParseException {
		

		WebClient client;
		
		if(codeLevel.equals("dev")) {
			HttpClient httpClient = HttpClient.create().secure(t -> { 
				try { 
					t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build()); 
				} catch (SSLException e) { 
					e.printStackTrace();
				} 
			});		
							
			 	client = WebClient.builder()
								.baseUrl(apiServer)
								.clientConnector(new ReactorClientHttpConnector(httpClient)) 
								.build();
			}else {
				
				client = WebClient.create(apiServer);
								
			}
		
		
		JSONParser jsonParser = new JSONParser();

		Object obj =  client.post()
						.uri(uri)
						.accept(APPLICATION_JSON)
						.headers(headers ->{
							headers.add("xrcloud-api-key", xrcloudApiKey);
						})			
						.body(BodyInserters.fromObject(dto))
						.retrieve()
						.bodyToMono(String.class)
						.block();
		
	
		return obj;
	}
	
}
