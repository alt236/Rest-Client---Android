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
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ListFragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import co.uk.alt236.restclient4android.R;
import co.uk.alt236.restclient4android.RestClient4AndroidApplication;
import co.uk.alt236.restclient4android.adapters.HeadersListAdapter;

public class FragmentHeaders extends ListFragment implements RestRequestFragmentInterface{
	private final String TAG = this.getClass().getName();
	// This is the Adapter being used to display the list's data.
	HeadersListAdapter mAdapter;

	// If non-null, this is the current filter the user has provided.
	String mCurFilter;

	// These are the Contacts rows that we will retrieve.
	static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
		Contacts._ID, Contacts.DISPLAY_NAME, 
		Contacts.CONTACT_STATUS,
		Contacts.CONTACT_PRESENCE, 
		Contacts.PHOTO_ID,
		Contacts.LOOKUP_KEY, };

	@Override
	public int getType() {
		return FRAGMENT_TYPE_HEADERS;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.menu_add_header:			
	        
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
		// Insert desired behaviour here.
		Log.i("FragmentComplexList", "Item clicked: " + id);
	}

	@Override
	public void updateRequest() {
		Log.d(TAG, "^ updateRequest()");
		// TODO Auto-generated method stub
	}
	
	Dialog headerDialog;
	
	private void URLPreviewDialog(final int headerIndex, final boolean newHeader) {

		headerDialog = new Dialog(getActivity());
		headerDialog.setContentView(R.layout.dialog_add_header);
		
		EditText editHeaderName = (EditText) headerDialog.findViewById(R.id.editHeaderName);
		EditText editHeaderValue = (EditText) headerDialog.findViewById(R.id.editHeaderName);
		
//		Button cancel = (Button) urlDialog.findViewById(R.id.header_cancel);
//		Button save = (Button) urlDialog.findViewById(R.id.header_save);
//		Button delete = (Button) urlDialog.findViewById(R.id.header_delete);

//		if(newHeader){
//			delete.setVisibility(View.GONE);
//			headerDialog.setTitle("New header");
//		} else {
//			headerDialog.setTitle("Edit header");
//		}
//
//		save.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				processURL(barCode);
//			}
//		});
//
//		cancel.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//
//				headerDialog.dismiss();
//
//			}
//		});
//
//		delete.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				saveURL(barCode);
//				urlDialog.dismiss();
//			}
//		});

		headerDialog.show();
	}
}
