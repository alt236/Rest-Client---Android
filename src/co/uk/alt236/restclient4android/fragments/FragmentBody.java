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

package co.uk.alt236.restclient4android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.Menu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import co.uk.alt236.restclient4android.R;
import co.uk.alt236.restclient4android.RestClient4AndroidApplication;

public class FragmentBody extends Fragment implements RestRequestFragmentInterface{
	private final String TAG = this.getClass().getName();
	
	Spinner methodSpinner;
	Spinner authSpinner;
	EditText body;
	
	@Override
	public int getType() {
		return FRAGMENT_TYPE_BODY;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	//@Override 
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Place an action bar item for searching.
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_body, container, false);
		populateUI(inflater, v);

		return v;
	}

	private void populateUI(final LayoutInflater inflater, final View parent){
		body = (EditText) parent.findViewById(R.id.editBody);
	}

	@Override
	public void updateRequest() {
		Log.d(TAG, "^ updateRequest()");
		if(body != null) { RestClient4AndroidApplication.getRequest().setRequestBody(body.getText().toString());}
	}
}
