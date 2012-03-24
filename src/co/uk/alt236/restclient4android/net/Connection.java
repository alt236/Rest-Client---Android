package co.uk.alt236.restclient4android.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;
import android.util.Pair;
import co.uk.alt236.restclient4android.containers.NetworkRequest;
import co.uk.alt236.restclient4android.containers.NetworkResult;
import co.uk.alt236.restclient4android.containers.NetworkResult.ErrorType;

import com.github.droidfu.http.BetterHttpRequest;

public class Connection {
	private final String TAG =  this.getClass().getName();

	public NetworkResult connect(NetworkRequest request){
		if(request == null){return null;}
		
		String urlString = request.getUrl();

		if(!(urlString.toLowerCase().startsWith("http://") || urlString.toLowerCase().startsWith("https://"))){
			urlString = "http://" + urlString;
		}


		Log.i(TAG, "^ Connecting to: " + urlString);

		URL url = null;
		HttpURLConnection  httpCon = null;
		int responseCode = NetworkResult.INVALID_REPONSE_CODE;
		NetworkResult result = new NetworkResult();

		try{
			url = new URL(urlString);
		}catch(MalformedURLException mue){
			Log.e(TAG, "^ connect() - MalformedURLException.",mue); 
			result.completedUnsuccesfully(ErrorType.INVALID_URL);
			return result;
		}
		Log.d(TAG, "^ URL OK!");

		try{
			httpCon = (HttpURLConnection) url.openConnection(); 
			httpCon.setConnectTimeout(5 * 1000);
		}catch(IOException ioe){
			Log.e(TAG, "^ connect() - IOException.",ioe); 
			result.completedUnsuccesfully(ErrorType.IO_EXCEPTION);
			return result;
		}

		responseCode = getHttpResponseCode(httpCon);
		result.setResponseCode(responseCode);
		result.setResponseBody(getHttpResponseBody(httpCon));
		result.setResponseHeaders(getHttpResponseHeaders(httpCon));

		if(responseCode != NetworkResult.INVALID_REPONSE_CODE){
			result.completedSuccesfully();
		} else {
			result.completedUnsuccesfully(ErrorType.UNKNOWN);
		}

		return result;
	}

	private ArrayList<Pair<String, String>> getHttpResponseHeaders(URLConnection urlc) {
		ArrayList<Pair<String, String>> headers = new ArrayList<Pair<String,String>>();
		Map<String, List<String>> map = urlc.getHeaderFields();

		for (Entry<String, List<String>> entry : map.entrySet())
		{
			String headerName = entry.getKey();
			List<String> valueList = entry.getValue();

			if(headerName == null){
				headerName = "";
			}

			if(valueList != null){

				for(String headerValue : entry.getValue()){
					headers.add(new Pair<String, String>(headerName, headerValue));
				}
			} else {
				headers.add(new Pair<String, String>(headerName, ""));
			}
		}



		return headers;
	}

	private int getHttpResponseCode(HttpURLConnection connection){
		int res = NetworkResult.INVALID_REPONSE_CODE;

		HttpURLConnection httpConnection = (HttpURLConnection) connection;
		try {
			res = httpConnection.getResponseCode();
		} catch (IOException e) {
			Log.e(TAG, "^ getHttpResponseCode() IOException.", e);
		}

		return res;
	}


	private String getHttpResponseBody(HttpURLConnection connection){
		InputStream response;
		try {
			response = connection.getInputStream();
		} catch (IOException e) {
			Log.e(TAG, "^ getHttpResponseBody() - IOException when getting stream", e);
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		String contentType = connection.getHeaderField("Content-Type");
		String charset = null;
		
		for (String param : contentType.replace(" ", "").split(";")) {
			if (param.startsWith("charset=")) {
				charset = param.split("=", 2)[1];
				break;
			}
		}

		if (charset != null) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(response, charset));
				
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


	private void addRequestHeaders(BetterHttpRequest req, ArrayList<Pair<String, String>> headers) {
		if (req != null && req.unwrap() != null && headers != null) {

			for(Pair<String, String> header : headers){
				req.unwrap().addHeader(header.first, header.second);
			}

		}
	}
}
