package twitter.token.patcher;

import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class MainActivity extends PreferenceActivity {

	Preference aboutArea;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.main_prefs);
		
		PreferenceScreen prefSet = getPreferenceScreen();
		
		aboutArea = (Preference) prefSet.findPreference("pref_about");
		String versionString = "";
		try {
			versionString += "version " + getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0).versionName;
		} catch (Exception e) {
			// nothing
		}
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), 0);
			ZipFile zf = new ZipFile(ai.sourceDir);
			ZipEntry ze = zf.getEntry("classes.dex");
			long time = ze.getTime();
			String s = SimpleDateFormat.getInstance().format(new java.util.Date(time));
			
			versionString += " (Built on " + s + ")";
		} catch (Exception e) {
			// do nothing
		}
		if (Utils.isDebug()) versionString += " [DEBUG]";
		
		aboutArea.setSummary(versionString);
	}

}