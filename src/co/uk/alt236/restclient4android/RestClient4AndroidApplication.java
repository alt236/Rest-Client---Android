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
package co.uk.alt236.restclient4android;

import android.app.Application;
import co.uk.alt236.restclient4android.containers.NetworkRequest;
import co.uk.alt236.restclient4android.containers.NetworkResult;

public class RestClient4AndroidApplication extends Application {
	private static NetworkRequest netRequest = new NetworkRequest();
	private static NetworkResult netResult = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	public static NetworkRequest getRequest() {
		return netRequest;
	}
	
	public static void setResult(NetworkResult result) {
		netResult = result;
	}
	
	public static NetworkResult getResult() {
		return netResult;
	}
}
