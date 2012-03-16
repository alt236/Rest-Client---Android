package co.uk.alt236.restclient4android.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import android.util.Log;
import android.util.Pair;
import co.uk.alt236.restclient4android.containers.NetworkResult;
import co.uk.alt236.restclient4android.containers.NetworkResult.ErrorType;

import com.github.droidfu.http.BetterHttp;
import com.github.droidfu.http.BetterHttpRequest;
import com.github.droidfu.http.BetterHttpResponse;

public class Connection {
	private final String TAG =  this.getClass().getName();
	
	public NetworkResult connect(String url, ArrayList<Pair<String, String>> headers) {
		Log.i(TAG, "^ Connecting to: " + url);
		
		NetworkResult result = new NetworkResult();
		BetterHttpRequest req = BetterHttp.get(url);
		
		addRequestHeaders(req, headers);

		try {
			BetterHttpResponse res = req.send();
			int responseCode = res.getStatusCode();
			
			Log.i(TAG, "^ HTTP response: " + responseCode);

			result.setResponseCode(responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				
				result.setResponseBody(res.getResponseBodyAsString());
				result.completedSuccesfully();
			}

		} catch (ConnectException e1) {
			result.hadAnError(ErrorType.CONNECTION_EXCEPTION);
			e1.printStackTrace();
		} catch (IOException e) {
			result.hadAnError(ErrorType.IO_EXCEPTION);
			e.printStackTrace();
		} catch (Exception e){
			result.hadAnError(ErrorType.UNKNOWN);
			e.printStackTrace();
		}

		return result;
	}
	
	private void addRequestHeaders(BetterHttpRequest req, ArrayList<Pair<String, String>> headers) {
		if (req != null && req.unwrap() != null && headers != null) {

			for(Pair<String, String> header : headers){
				req.unwrap().addHeader(header.first, header.second);
			}
			
		}
	}
}
