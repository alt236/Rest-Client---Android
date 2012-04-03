/*******************************************************************************
 * Copyright 2012 Alexandros Schillings
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package co.uk.alt236.restclient4android.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import android.util.Pair;
import co.uk.alt236.restclient4android.containers.NetworkRequest;
import co.uk.alt236.restclient4android.containers.NetworkResult;
import co.uk.alt236.restclient4android.containers.NetworkResult.ErrorType;
import co.uk.alt236.restclient4android.util.Base64;

public class Connection {
	private final String TAG =  this.getClass().getName();

	public NetworkResult execute(NetworkRequest request){
		if(request == null){return null;}
		
		URI uri = null;
		int responseCode = NetworkResult.INVALID_REPONSE_CODE;
		
		
		HttpClient httpclient = new DefaultHttpClient();
		
		request.setUrl(firxUrl(request.getUrl()));
		NetworkResult result = new NetworkResult(request.getUrl());
		
		Log.i(TAG, "^ Connecting to: " + request.getUrl());
		
		try{
			uri = new URI(request.getUrl());
		}catch(URISyntaxException e){
			Log.e(TAG, "^ connect() - MalformedURLException.",e); 
			result.completedUnsuccesfully(ErrorType.INVALID_URL);
			return result;
		}
		
		Log.d(TAG, "^ URL OK!");
		
		HttpRequestBase httpRequest = setupHttpRequest(request, uri);
		
		// something went wrong...
		if(httpRequest == null){
			Log.e(TAG, "^ connect() - NULL after setupHttpRequest()");
			return null;
		}
		
		
		HttpResponse response = null;
		
		try {
			response = httpclient.execute(httpRequest);
		} catch (ClientProtocolException e) {
			Log.e(TAG, "^ connect() - ClientProtocolException.",e); 
			result.completedUnsuccesfully(ErrorType.HTTP_PROTOCOL_ERROR);
			return result;
		} catch (IOException e) {
			Log.e(TAG, "^ connect() - IOException.",e); 
			result.completedUnsuccesfully(ErrorType.IO_EXCEPTION);
			return result;
		}
		
		
		//HttpEntity entity = response.getEntity();
		
		responseCode = getResponseCode(response);
		result.setResponseCode(responseCode);
		result.setResponseBody(getBody(response));
		result.setResponseHeaders(getHeaders(response));

		if(responseCode != NetworkResult.INVALID_REPONSE_CODE){
			result.completedSuccesfully();
		} else {
			result.completedUnsuccesfully(ErrorType.UNKNOWN);
		}

		return result;
	}
	
	
	
	private HttpRequestBase setupHttpRequest(NetworkRequest request, URI uri){
		String method = request.getAction().toUpperCase();
		HttpRequestBase  httpReq = null;
		
		if("GET".equals(method)){
			httpReq = new HttpGet(uri);
		}else if("POST".equals(method)){
			httpReq = new HttpPost(uri);
		}else if("PUT".equals(method)){
			httpReq = new HttpPut(uri);
		}else if("DELETE".equals(method)){
			httpReq = new HttpDelete(uri);
		}else if("HEAD".equals(method)){
			httpReq = new HttpHead(uri);
		}else if("TRACE".equals(method)){
			httpReq = new HttpTrace(uri);
		}else if("OPTIONS".equals(method)){
			httpReq = new HttpOptions(uri);
		}
		
		
		setAuthentication(httpReq, request);
		setHeaders(httpReq, request);
		
		return httpReq;
	}
	
	private String firxUrl(String url){
		url = url.trim();
		
		if(!(url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://"))){
			return "http://" + url;
		}
		
		return url;
	}
	
	private void setAuthentication(HttpRequestBase con, NetworkRequest req){
		String reqString = req.getAuthenticationMethod();
		
		if(reqString != null && reqString.length() > 0){
			reqString = reqString.toLowerCase();
			if("none".equals(reqString)){
				return;
			}
			else if ("basic".equals(reqString)){
				byte[] token = (req.getUsername() + ":" + req.getPassword()).getBytes();
				String authorizationString = "Basic " + Base64.encodeBytes(token);
				con.addHeader("Authorization", authorizationString);
			} else {
				Log.w(TAG, "^ Unknown authentication method: '" + reqString + "'");
			}
			
		}
	}
	
	
	private void setHeaders(HttpRequestBase con, NetworkRequest req){
//		String reqString = req.getAuthenticationMethod();
//		
//		if(reqString != null && reqString.length() > 0){
//			reqString = reqString.toLowerCase();
//			if("none".equals(reqString)){
//				return;
//			}
//			else if ("basic".equals(reqString)){
//				byte[] token = (req.getUsername() + ":" + req.getPassword()).getBytes();
//				String authorizationString = "Basic " + Base64.encodeBytes(token);
//				con.addHeader("Authorization", authorizationString);
//			} else {
//				Log.w(TAG, "^ Unknown authentication method: '" + reqString + "'");
//			}
//			
//		}
	}
	
	private ArrayList<Pair<String, String>> getHeaders(HttpResponse response) {
		ArrayList<Pair<String, String>> headers = new ArrayList<Pair<String,String>>();
		Header[] httpHeaders = response.getAllHeaders();

		if(httpHeaders == null){
			return headers;
		}
		
		for (Header entry : httpHeaders)
		{
			String headerName = entry.getName();
			String headerValue = entry.getValue();

			if(headerName == null){
				headerName = "";
			}
			if(headerValue == null){
				headerValue = "";
			}
			
			headers.add(new Pair<String, String>(headerName, headerValue));
		}



		return headers;
	}

	private int getResponseCode(HttpResponse response){
		int res = NetworkResult.INVALID_REPONSE_CODE;

		StatusLine status = response.getStatusLine();
		
		if(status != null){
			res = status.getStatusCode();
		}
		
		return res;
	}


	private String getBody(HttpResponse response){
		HttpEntity entity = response.getEntity();
		
		InputStream responseStream;
		
		if(entity != null){
			try {
				responseStream = entity.getContent();
			} catch (IOException e) {
				Log.e(TAG, "^ getHttpResponseBody() - IOException when getting stream", e);
				return "";
			}
			
		} else {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		Header contentType = entity.getContentType();
		
		String charset = null;
		
		for (String param : contentType.getValue().replace(" ", "").split(";")) {
			if (param.startsWith("charset=")) {
				charset = param.split("=", 2)[1];
				break;
			}
		}

		if (charset != null) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(responseStream, charset));
				
				for (String line; (line = reader.readLine()) != null;) {
					sb.append(line);
					sb.append("\n");
				}
				
			}catch(IOException ioe){
				Log.e(TAG, "^ getHttpResponseBody() - IOException when reading stream", ioe);
			} finally {
				if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
			}
		} else {
			Log.i(TAG, "^ getHttpResponseBody() - The response seems to be binary.");
		}
		
		return sb.toString();
	}
}
