/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.uk.alt236.restclient4android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.TabHost;
import android.widget.Toast;
import co.uk.alt236.restclient4android.R;
import co.uk.alt236.restclient4android.RestClient4AndroidApplication;
import co.uk.alt236.restclient4android.adapters.RequestTabsAdapter;
import co.uk.alt236.restclient4android.fragments.FragmentBody;
import co.uk.alt236.restclient4android.fragments.FragmentHeaders;
import co.uk.alt236.restclient4android.fragments.FragmentNetworkResult;
import co.uk.alt236.restclient4android.fragments.FragmentTarget;
import co.uk.alt236.restclient4android.fragments.RestRequestFragmentInterface;

public class ActivityMain extends FragmentActivity {
	private final String TAG = this.getClass().getName();
	
	private static final String TAB_TARGET = "tab_target";
	private static final String TAB_HEADERS = "tab_headers";
	private static final String TAB_BODY = "tab_body";

	TabHost mTabHost;
	ViewPager  mViewPager;
	RequestTabsAdapter mTabsAdapter;

	private void addTab(TabHost tabHost, RequestTabsAdapter tabAdapter, String tabSpec, String title, Class<? extends RestRequestFragmentInterface> clss, Bundle args){
		tabAdapter.addTab(tabHost.newTabSpec(tabSpec).setIndicator(title), clss, args);
	}

	//@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_execute_rest_call:
			executeRequest();
			return true;
		}

		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager)findViewById(R.id.pager);

		mTabsAdapter = new RequestTabsAdapter(this, mTabHost, mViewPager);

		addTab(mTabHost, mTabsAdapter, TAB_TARGET, getString(R.string.tab_label_target), 
				FragmentTarget.class, null);
		addTab(mTabHost, mTabsAdapter, TAB_HEADERS, getString(R.string.tab_label_headers), 
				FragmentHeaders.class, null);
		addTab(mTabHost, mTabsAdapter, TAB_BODY, getString(R.string.tab_label_body), 
				FragmentBody.class, null);         
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}

	private void executeRequest(){
		for(int i =0; i< mTabsAdapter.getCount(); i++){
			((RestRequestFragmentInterface) mTabsAdapter.getItem(i)).updateRequest();
		}

		if(RestClient4AndroidApplication.getRequest().isValid()){
			if(isSmallScreen()){
				Intent i = new Intent(getApplicationContext(), ActivityResult.class);
	            startActivity(i);
			} else {
				Fragment f = new FragmentNetworkResult(RestClient4AndroidApplication.getRequest());
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.fragment_container, f);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.commit();
			}
		} else {
			Toast.makeText(this, "Invalid request", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	private boolean isSmallScreen(){
		Boolean res;
		if(findViewById(R.id.fragment_container) == null){
			res = true;
		} else {
			res = false;
		}
		Log.d(TAG, "^ Is this device a small screen? " + res);
		return res;
	}
}