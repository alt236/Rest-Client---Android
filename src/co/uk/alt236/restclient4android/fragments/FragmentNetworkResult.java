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
package co.uk.alt236.restclient4android.fragments;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import co.uk.alt236.restclient4android.R;
import co.uk.alt236.restclient4android.RestClient4AndroidApplication;
import co.uk.alt236.restclient4android.containers.NetworkRequest;
import co.uk.alt236.restclient4android.containers.NetworkResult;
import co.uk.alt236.restclient4android.net.Connection;
import co.uk.alt236.restclient4android.util.UsefulBits;

public class FragmentNetworkResult extends Fragment{
	private final String TAG = this.getClass().getName();
	
	private final static String TAB_BODY = "tab_body";
	private final static String TAB_HEADERS = "tab_headers";

	private NetworkRequest mNetworkRequest;
	
	private TabHost mTabHost;	

	
	private TextView mTvResponseCode;
	private TextView mTvResponseBody;
	private TextView mTvResponseHeaders;
	private TextView mTvUrl;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(RestClient4AndroidApplication.getResult() == null){
			(new HttpConnectionTask()).execute(mNetworkRequest);
		} else {
			displayNetworkResult(RestClient4AndroidApplication.getResult());
		}
	}

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setHasOptionsMenu(true);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_export:
			UsefulBits.share(getActivity(), getActivity().getString(R.string.share_result_title), this.toString());
			return true;
		}
		return false;
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_fragment_result, menu);
		super.onCreateOptionsMenu(menu, inflater);

	}

	public FragmentNetworkResult() {

	}
	
	public FragmentNetworkResult(NetworkRequest request) {
		this.mNetworkRequest = request;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved) {   	
		View v = inflater.inflate(R.layout.fragment_result, container, false);
		mTabHost = (TabHost)v.findViewById(android.R.id.tabhost);
		mTvResponseBody = (TextView) v.findViewById(R.id.tvResponseBody);
		mTvResponseCode = (TextView) v.findViewById(R.id.tvResponseCode);
		mTvResponseHeaders = (TextView) v.findViewById(R.id.tvResponseHeaders);
		mTvUrl = (TextView) v.findViewById(R.id.tvUrl);
		
		setupTabs();
		return v;
	}

	private void setupTabs() {
		mTabHost.setup(); // you must call this before adding your tabs!

		mTabHost.addTab(newTab(TAB_BODY, R.string.tab_label_body, R.id.tab_1));
		mTabHost.addTab(newTab(TAB_HEADERS, R.string.tab_label_headers, R.id.tab_2));
	}

	private TabSpec newTab(String tag, int labelId, int tabContentId) {
		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(getActivity().getString(labelId));
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	private class HttpConnectionTask extends AsyncTask<NetworkRequest, Integer, NetworkResult> {
		private ProgressDialog mDialog = new ProgressDialog(getActivity());

		@Override
		protected void onPreExecute() {
			mDialog.setMessage("Connecting to server...");
			mDialog.setCancelable(false);
			mDialog.show();
		}

		@Override
		protected NetworkResult doInBackground(NetworkRequest... requests) {
			Connection connection = new Connection();
			return connection.execute(requests[0]);
		}

		@Override
		protected void onPostExecute(NetworkResult result) {
			RestClient4AndroidApplication.setResult(result);
			
			displayNetworkResult(result);
			
			if(mDialog!=null){
				if (mDialog.isShowing()){
					mDialog.dismiss();
				}
				mDialog.cancel();
			}
		}
	}
	
	private void displayNetworkResult(NetworkResult data) {
		if(data != null){
			mTvResponseCode.setText(String.valueOf(data.getResponseCode()));
			mTvUrl.setText(data.getUrl());
			
			displayResultBody(data.getResponseBody());
			
			ArrayList<Pair<String, String>> headers = data.getResponseHeaders();
			StringBuffer sb = new StringBuffer();
			
			if(headers != null){
				for(Pair<String,String> pair : headers){       
					sb.append("<b>");
					sb.append(pair.first);
					sb.append("</b>");
					sb.append(": ");
					sb.append(pair.second);
					sb.append("<br>");
				}
			}
			
			mTvResponseHeaders.setText(Html.fromHtml(sb.toString()));
			
		} else {
			Log.e(TAG, "^ displayNetworkResult() Result was null!");
		}
	}
	
	
	
	private void displayResultBody(String body){
		if(body == null){
			mTvResponseBody.setText("");
			return;
		}
		
		mTvResponseBody.setText(body);
	}
}
