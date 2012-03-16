package co.uk.alt236.restclient4android.containers;

public class NetworkResult {
	public static final int INVALID_REPONSE_CODE = -1;
	
	private int responseCode = INVALID_REPONSE_CODE;

	private String responseBody = "";
	private boolean completedOk = false;
	private ErrorType errorType = ErrorType.UNKNOWN;
	
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
	public void setCompletedOk(boolean completedOk) {
		this.completedOk = completedOk;
	}
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
		UNKNOWN, NO_ERROR, CONNECTION_EXCEPTION, JSON_EXCEPTION, IO_EXCEPTION
	}

	public void completedSuccesfully(){
		this.completedOk = true;
		this.errorType = ErrorType.NO_ERROR;
	}

	public void hadAnError(ErrorType errorType){
		this.completedOk = false;
		this.errorType = errorType;
	}
}
