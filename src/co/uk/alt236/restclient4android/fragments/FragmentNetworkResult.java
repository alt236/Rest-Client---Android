package co.uk.alt236.restclient4android.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import co.uk.alt236.restclient4android.R;
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
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setHasOptionsMenu(true);
		(new HttpConnectionTask()).execute(mNetworkRequest);
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
			return connection.connect(requests[0]);
		}

		@Override
		protected void onPostExecute(NetworkResult result) {
			if(mDialog!=null){
				if (mDialog.isShowing()){
					mDialog.dismiss();
				}
				mDialog.cancel();
			}
			
			displayNetworkResult(result);
		}
	}
	private void displayNetworkResult(NetworkResult data) {
		if(data != null){
			mTvResponseCode.setText(String.valueOf(data.getResponseCode()));
			mTvResponseBody.setText(data.getResponseBody());
		} else {
			Log.e(TAG, "^ displayNetworkResult() Result was null!");
		}
	}
}
