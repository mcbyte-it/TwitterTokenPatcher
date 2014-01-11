package twitter.token.patcher;

import twitter.token.patcher.app.Carbon;
import twitter.token.patcher.app.FalconPro;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XTokenPatcher implements IXposedHookLoadPackage, IXposedHookZygoteInit {

	public static XSharedPreferences prefs;
	public static String strConsumerKey;
	public static String strConsumerSecret; 
	
	@Override
	public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
		loadPrefs();
		
		strConsumerKey = prefs.getString(Common.CONSUMER_KEY, "");
		strConsumerSecret = prefs.getString(Common.CONSUMER_SECRET, "");
	}
	
	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		
		if (!lpparam.packageName.equals(Carbon.packageName)
				&& !lpparam.packageName.equals(FalconPro.packageName)) {
			return;
		}
		
		if (Common.isDebug()) XposedBridge.log("strConsumerKey: " + strConsumerKey + "  |  strConsumerSecret: " + strConsumerSecret);
		
		if (strConsumerKey.isEmpty() || strConsumerSecret.isEmpty()) {
			//Toast.makeText(this, "Twitter Token Patcher is not configured", Toast.LENGTH_LONG).show();
			XposedBridge.log("Twitter Token Patcher is not configured properly, please insert the Consumer Key and Secret");
			return;
		}
		
		if (lpparam.packageName.equals(Carbon.packageName) && isActive(Carbon.packageName)) {
			if (Common.isDebug()) XposedBridge.log("Twitter Token Patcher: Patching Carbon...");
			Carbon.handleLoadPackage(lpparam);
		} else if (lpparam.packageName.equals(FalconPro.packageName) && isActive(FalconPro.packageName)) {
			if (Common.isDebug()) XposedBridge.log("Twitter Token Patcher: Patching Falcon Pro...");
			FalconPro.handleLoadPackage(lpparam);
		}
	}

	public static void loadPrefs() {
		prefs = new XSharedPreferences(Common.MY_PACKAGE_NAME);
	}
	
	public static boolean isActive(String packageName) {
		return prefs.getBoolean(packageName, false);
	}
}