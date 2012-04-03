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
package co.uk.alt236.restclient4android.containers;

import java.util.ArrayList;

import android.util.Pair;

public class NetworkRequest {
	private ArrayList<Pair<String, String>> requestHeaders = new ArrayList<Pair<String, String>>();
	private String requestBody = "";
	private String action = "";
	private String authenticationMethod = "";
	private String url = "";
	private String username = "";
	private String password = "";
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAuthenticationMethod() {
		return authenticationMethod;
	}

	public void setAuthenticationMethod(String authenticationMethod) {
		this.authenticationMethod = authenticationMethod;
	}

	public boolean isValid() {
		if (url == null || url.trim().length() < 1) {
			return false;
		}
	
		return true;
	}
}
