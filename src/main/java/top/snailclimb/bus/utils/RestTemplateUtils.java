package top.snailclimb.bus.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RestTemplateUtils {

	public static String delete(String url, Object param) {
		return delete(url, param, String.class);
	}

	public static <T> T delete(String url, Object req, Class<T> responseType) {
		return execute(url, req, responseType, HttpMethod.DELETE);
	}

	public static String put(String url, Object param) {
		return put(url, param, String.class);
	}

	public static <T> T put(String url, Object req, Class<T> responseType) {
		return execute(url, req, responseType, HttpMethod.PUT);
	}

	public static String post(String url, Object param) {
		return post(url, param, String.class);
	}

	public static <T> T post(String url, Object req, Class<T> responseType) {
		return execute(url, req, responseType, HttpMethod.POST);
	}
	
	public static String get(String url, Object param) {
		return get(url, param, String.class);
	}

	public static <T> T get(String url, Object req, Class<T> responseType) {
		return execute(url, req, responseType, HttpMethod.GET);
	}

	public static HttpHeaders head(String url) {
		return execute(url, new HashMap<String,String>(), HttpHeaders.class, HttpMethod.HEAD);
	}
	
	public static Set<HttpMethod> options(String url) {
		return execute(url, new HashMap<String,String>(), Set.class, HttpMethod.OPTIONS);
	}

	public static <T> T execute(String url, Object body, Class<T> responseType, HttpMethod method){
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYiIsImV4cCI6MTU4MzkxODA0NH0.RC0ghOvCB3f2MxQDPEufYKU3UwpGQKUM_7o55ty4qB0");
		if(body instanceof Map){
			Map<String, String> paramMap = (Map<String, String>) body;
			return execute2(url, paramMap, responseType, method, headerMap);
		}else{
			return execute(url, body, responseType, method, headerMap);
		}
	}

	private static <T> T execute2(String url, Map<String, String> param, Class<T> clazz, HttpMethod method, Map<String, String> headerMap) {
		RestTemplate restTemplate = new RestTemplate();
		T response = null;
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		param.forEach((String t, String u) -> {
				paramMap.add(t, u);
			}
		 );

		StringBuilder sb = new StringBuilder(url);
		sb.append("?");
		param.forEach((k,v) -> {
			sb.append(String.format("%s=%s&", k,v));
		});
		String reqUrlWithParam = sb.toString();

		HttpHeaders header = new HttpHeaders();
// 	    header.setContentType(MediaType.APPLICATION_JSON);
	    headerMap.forEach((k,v) -> {
	    	header.add(k, v);
		});

	    if(method == HttpMethod.DELETE || method == HttpMethod.GET || method == HttpMethod.PUT ) {
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(null, header);
		    ResponseEntity<T> res = restTemplate.exchange(reqUrlWithParam, method, requestEntity, clazz);
		    return res.getBody();
		}else if(method == HttpMethod.POST){
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, header);
			ResponseEntity<T> res = restTemplate.exchange(url, method, requestEntity, clazz);
			return res.getBody();
		}else if(method == HttpMethod.HEAD) {
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(null, header);
		    ResponseEntity<T> res = restTemplate.exchange(url, method, requestEntity, clazz);
		    return (T) res.getHeaders();
		}else if(method == HttpMethod.OPTIONS) {
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(null, header);
		    ResponseEntity<T> res = restTemplate.exchange(url, method, requestEntity, clazz);
		    return (T) res.getHeaders().getAllow();
		}
		return response; 
	}

	private static <T> T execute(String url, Object body, Class<T> clazz, HttpMethod method, Map<String, String> headerMap) {
		RestTemplate restTemplate = new RestTemplate();
		T response = null;
		Map<String, String> param = (Map<String, String>) JSONObject.parseObject(JSONObject.toJSONString(body), Map.class);

		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
		param.forEach((String t, String u) -> {
					paramMap.add(t, u);
				}
		);

		StringBuilder sb = new StringBuilder(url);
		sb.append("?");
		param.forEach((k,v) -> {
			sb.append(String.format("%s=%s&", k,v));
		});
		String reqUrlWithParam = sb.toString();

		HttpHeaders header = new HttpHeaders();
// 	    header.setContentType(MediaType.APPLICATION_JSON);
		headerMap.forEach((k,v) -> {
			header.add(k, v);
		});

		if(method == HttpMethod.DELETE || method == HttpMethod.GET || method == HttpMethod.PUT ) {
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(null, header);
			ResponseEntity<T> res = restTemplate.exchange(reqUrlWithParam, method, requestEntity, clazz);
			return res.getBody();
		}else if(method == HttpMethod.POST){
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(body, header);
			ResponseEntity<T> res = restTemplate.exchange(url, method, requestEntity, clazz);
			return res.getBody();
		}else if(method == HttpMethod.HEAD) {
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(null, header);
			ResponseEntity<T> res = restTemplate.exchange(url, method, requestEntity, clazz);
			return (T) res.getHeaders();
		}else if(method == HttpMethod.OPTIONS) {
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(null, header);
			ResponseEntity<T> res = restTemplate.exchange(url, method, requestEntity, clazz);
			return (T) res.getHeaders().getAllow();
		}
		return response;
	}
}
