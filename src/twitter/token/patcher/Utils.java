package twitter.token.patcher;

import de.robv.android.xposed.XposedBridge;

public class Utils {

	public static boolean isDebug() {
		return BuildConfig.DEBUG;
	}

	public static void xlog(String text) {
		if (isDebug()) XposedBridge.log("[Twitter Token Patcher] " + text);
	}
	
	public static void xlog(Throwable t) {
		if (isDebug()) XposedBridge.log(t);
	}

}
