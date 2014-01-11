package twitter.token.patcher;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class MainPrefs extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_prefs);
	}
}
