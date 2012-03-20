package co.uk.alt236.restclient4android;

import android.app.Application;
import co.uk.alt236.restclient4android.containers.NetworkRequest;

public class RestClient4AndroidApplication extends Application {
	private static NetworkRequest request = new NetworkRequest();
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		
	}

	public static NetworkRequest getRequest() {
		return request;
	}
}
