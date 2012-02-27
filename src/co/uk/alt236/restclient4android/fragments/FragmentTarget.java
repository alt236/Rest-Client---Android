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
import android.support.v4.view.MenuItem;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import co.uk.alt236.restclient4android.R;

public class FragmentTarget extends Fragment {
	Spinner methodSpinner;
	Spinner authSpinner;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

	}

	//@Override 
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Place an action bar item for searching.
		MenuItem item = menu.add("Go");
		item.setIcon(android.R.drawable.ic_menu_send);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_target, container, false);
		populateUI(inflater, v);

		return v;
	}

	private void populateUI(final LayoutInflater inflater, final View parent){
		methodSpinner = (Spinner) parent.findViewById(R.id.spinnerHttpMethod);
		authSpinner = (Spinner) parent.findViewById(R.id.spinnerAuth);

		authSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				if(selectedItemView == null){return;}

				String label = ((TextView) selectedItemView).getText().toString();
				View row1 = parent.findViewById(R.id.tableRowPassword);
				View row2 = parent.findViewById(R.id.tableRowUsername);

				if(label.equals(getActivity().getString(R.string.label_auth_disabled))){
					row1.setVisibility(View.GONE);
					row2.setVisibility(View.GONE);
				}else{
					row1.setVisibility(View.VISIBLE);
					row2.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
	}
}
