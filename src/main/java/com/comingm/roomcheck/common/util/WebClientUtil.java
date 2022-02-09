package com.comingm.roomcheck.common.util;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientUtil {

	
	
public String get(String apiServer,String uri, String name, String value) throws ParseException {
		

		WebClient client;	
				
		client = WebClient.create(apiServer);
		
		JSONParser jsonParser = new JSONParser();

		String str =  client.get()
						.uri(uriBuilder ->uriBuilder
								.path(uri)
								.queryParam(name, value)
								.build())
						.accept(APPLICATION_JSON)
						.retrieve()
						.bodyToMono(String.class)
						.block();
		
		return str;
	}
	
	
	
	
	public Object post(String apiServer,String uri) throws ParseException {
		
		WebClient client;
			
		client = WebClient.create(apiServer);
		
		JSONParser jsonParser = new JSONParser();
		String str =  client.post()
						.uri(uri)
						.accept(APPLICATION_JSON)	
						.retrieve()
						.bodyToMono(String.class)
						.block();
		
		Object obj = jsonParser.parse(str);
		JSONObject jsonObj = (JSONObject) obj;
		return jsonObj.get("data");	
	}
	
		
	public Object post(String apiServer,String uri, Object dto) throws ParseException {
		

		WebClient client;	
				
		client = WebClient.create(apiServer);
		
		JSONParser jsonParser = new JSONParser();

		String str =  client.post()
						.uri(uri)
						.accept(APPLICATION_JSON)
						.body(BodyInserters.fromObject(dto))
						.retrieve()
						.bodyToMono(String.class)
						.block();
		if(str != null) {
			Object obj = jsonParser.parse(str);
		
			JSONObject jsonObj = (JSONObject) obj;
			return jsonObj;
		}else {
			
			return null;
		}
	}
	
	
	
	
	
}
