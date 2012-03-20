package co.uk.alt236.restclient4android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import co.uk.alt236.restclient4android.R;
import co.uk.alt236.restclient4android.containers.NetworkRequest;
import co.uk.alt236.restclient4android.util.UsefulBits;

public class FragmentNetworkResult extends Fragment{
	private final static String TAB_BODY = "tab_body";
	private final static String TAB_HEADERS = "tab_headers";
	
	private NetworkRequest mNetworkRequest;
	private TabHost mTabHost;
	
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
    
	public FragmentNetworkResult(NetworkRequest request) {
		this.mNetworkRequest = request;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved) {   	
		View v = inflater.inflate(R.layout.fragment_result, container, false);
		mTabHost = (TabHost)v.findViewById(android.R.id.tabhost);
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
}
