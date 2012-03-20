package co.uk.alt236.restclient4android.fragments;


public interface RestRequestFragmentInterface{
	public static final int FRAGMENT_TYPE_TARGET = 0;
	public static final int FRAGMENT_TYPE_HEADERS = 1;
	public static final int FRAGMENT_TYPE_BODY = 2;
	
	public int getType();
	public void updateRequest();
}
