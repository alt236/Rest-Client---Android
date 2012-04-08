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
/*
 * Copyright (C) 2010 The Android Open Source Project
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

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.util.Pair;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import co.uk.alt236.restclient4android.R;
import co.uk.alt236.restclient4android.RestClient4AndroidApplication;
import co.uk.alt236.restclient4android.adapters.HeadersListAdapter;

public class FragmentHeaders extends ListFragment implements RestRequestFragmentInterface{
	private final String TAG = this.getClass().getName();
	// This is the Adapter being used to display the list's data.
	HeadersListAdapter mAdapter;

	@Override
	public int getType() {
		return FRAGMENT_TYPE_HEADERS;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.menu_add_header:			
			URLPreviewDialog(-1, true);
			return true;
		}

        return false;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setEmptyText("No headers");

		// We have a menu item to show in action bar.
		setHasOptionsMenu(true);


		mAdapter = new HeadersListAdapter(
				getActivity(),
				android.R.id.text1,
				RestClient4AndroidApplication.getRequest().getRequestHeaders());
		
		setListAdapter(mAdapter);
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	inflater.inflate(R.menu.menu_fragment_headers, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		URLPreviewDialog((Integer) v.getTag(), false);
	}

	@Override
	public void updateRequest() {
		Log.d(TAG, "^ updateRequest()");
	}
	
	Dialog headerDialog;
	
	private void URLPreviewDialog(final int headerIndex, final boolean newHeader) {

		headerDialog = new Dialog(getActivity());
		headerDialog.setContentView(R.layout.dialog_add_header);
		
		final EditText editHeaderName = (EditText) headerDialog.findViewById(R.id.editHeaderName);
		final EditText editHeaderValue = (EditText) headerDialog.findViewById(R.id.editHeaderValue);
		
		Button cancel = (Button) headerDialog.findViewById(R.id.btnCancel);
		Button save = (Button) headerDialog.findViewById(R.id.btnSave);
		Button delete = (Button) headerDialog.findViewById(R.id.btnDelete);

		if(newHeader){
			delete.setVisibility(View.GONE);
			headerDialog.setTitle("New header");
		} else {
			headerDialog.setTitle("Edit header");
			Pair<String, String> header = RestClient4AndroidApplication.getRequest().getRequestHeaders().get(headerIndex);
			
			editHeaderName.setText(header.first);
			editHeaderValue.setText(header.second);
		}

		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean res = saveHeader(
						headerIndex, 
						editHeaderName.getText().toString(), 
						editHeaderValue.getText().toString());
				if(res){
					headerDialog.dismiss();
				}
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				headerDialog.dismiss();
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RestClient4AndroidApplication.getRequest().getRequestHeaders().remove(headerIndex);
				mAdapter.notifyDataSetChanged();
				Log.d(TAG, "^ HEADERS: " + mAdapter.getCount());
				headerDialog.dismiss();
			}
		});

		headerDialog.show();
	}
	
	private boolean saveHeader(int index, String name, String value){
		name = name.trim();
		value = value.trim();
		
		if(name.length()>0){
			Pair <String, String> header = new Pair<String, String>(name, value);
			
			if(index > 0){
				RestClient4AndroidApplication.getRequest().getRequestHeaders().set(index, header);
			}else{
				RestClient4AndroidApplication.getRequest().getRequestHeaders().add(header);
			}
			
			mAdapter.notifyDataSetChanged();
			Log.d(TAG, "^ HEADERS: " + mAdapter.getCount());
			
			return true;
		}else{
			Toast.makeText(
					getActivity(), 
					"A header must have a name!", 
					Toast.LENGTH_SHORT);
			return false;
		}
	}
	
}
