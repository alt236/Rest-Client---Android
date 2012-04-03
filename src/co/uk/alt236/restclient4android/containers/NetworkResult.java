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

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

public class NetworkResult {
	public static final int INVALID_REPONSE_CODE = -1;

	private int mResponseCode = INVALID_REPONSE_CODE;

	private String mUrl = "";
	private String mResponseBody = "";
	private boolean mCompletedOk = false;
	private ErrorType mErrorType = ErrorType.UNKNOWN;
	private ArrayList<Pair<String, String>> mResponseHeaders;

	public NetworkResult(String url) {
		super();
		this.mUrl = url;
	}

	public NetworkResult(String url, int responseCode, String responseBody, boolean completedOk) {
		super();
		this.mResponseCode = responseCode;
		this.mResponseBody = responseBody;
		this.mCompletedOk = completedOk;
		this.mUrl = url;
	}

	public ErrorType getErrorType() {
		return mErrorType;
	}

	public String getResponseBody() {
		return mResponseBody;
	}

	public int getResponseCode() {
		return mResponseCode;
	}

	public boolean isCompletedOk() {
		return mCompletedOk;
	}
	//	public void setCompletedOk(boolean completedOk) {
	//		this.completedOk = completedOk;
	//	}
	
	public void setErrorType(ErrorType errorType) {
		this.mErrorType = errorType;
	}
	public void setResponseBody(String responseBody) {
		this.mResponseBody = responseBody;
	}

	public void setResponseCode(int responseCode) {
		this.mResponseCode = responseCode;
	}

	public enum ErrorType{
		UNKNOWN, NO_ERROR, INVALID_URL, CONNECTION_EXCEPTION, JSON_EXCEPTION, IO_EXCEPTION, HTTP_PROTOCOL_ERROR
	}

	public void completedSuccesfully(){
		this.mCompletedOk = true;
		this.mErrorType = ErrorType.NO_ERROR;
	}

	public void completedUnsuccesfully(ErrorType errorType){
		this.mCompletedOk = false;
		this.mErrorType = errorType;
	}

	public ArrayList<Pair<String, String>> getResponseHeaders() {
		return mResponseHeaders;
	}

	public void setResponseHeaders(ArrayList<Pair<String, String>> responseHeaders) {
		this.mResponseHeaders = responseHeaders;
	}

	public String getUrl() {
		return mUrl;
	}
}
