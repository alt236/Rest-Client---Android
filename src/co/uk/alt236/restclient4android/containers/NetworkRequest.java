package co.uk.alt236.restclient4android.containers;

import java.util.ArrayList;

import android.util.Pair;

public class NetworkRequest {
	private String requestBody = "";
	private ArrayList<Pair<String, String>> requestHeaders = new ArrayList<Pair<String,String>>();
	private String authString = "";
	private String action = "";
	private String url = "";
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	public ArrayList<Pair<String, String>> getRequestHeaders() {
		return requestHeaders;
	}
	public void setRequestHeaders(ArrayList<Pair<String, String>> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}
	public String getAuthString() {
		return authString;
	}
	public void setAuthString(String authString) {
		this.authString = authString;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public boolean isValid(){
		if(url == null || url.trim().length() < 1){
			return false;
		}
		
		return true;
	}
}
