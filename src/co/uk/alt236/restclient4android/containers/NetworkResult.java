package co.uk.alt236.restclient4android.containers;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

public class NetworkResult implements Parcelable {
	public static final int INVALID_REPONSE_CODE = -1;

	private int responseCode = INVALID_REPONSE_CODE;

	private String responseBody = "";
	private boolean completedOk = false;
	private ErrorType errorType = ErrorType.UNKNOWN;
	private ArrayList<Pair<String, String>> responseHeaders;

	public NetworkResult() {
		super();
	}

	public NetworkResult(int responseCode, String responseBody, boolean completedOk) {
		super();
		this.responseCode = responseCode;
		this.responseBody = responseBody;
		this.completedOk = completedOk;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public boolean isCompletedOk() {
		return completedOk;
	}
	//	public void setCompletedOk(boolean completedOk) {
	//		this.completedOk = completedOk;
	//	}
	
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public enum ErrorType{
		UNKNOWN, NO_ERROR, INVALID_URL, CONNECTION_EXCEPTION, JSON_EXCEPTION, IO_EXCEPTION, HTTP_PROTOCOL_ERROR
	}

	public void completedSuccesfully(){
		this.completedOk = true;
		this.errorType = ErrorType.NO_ERROR;
	}

	public void completedUnsuccesfully(ErrorType errorType){
		this.completedOk = false;
		this.errorType = errorType;
	}

	public ArrayList<Pair<String, String>> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(ArrayList<Pair<String, String>> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
