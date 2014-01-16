package twitter.token.patcher.app;

import twitter.token.patcher.Utils;
import twitter.token.patcher.XTokenPatcher;
import android.content.Context;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class FalconPro {

	public static final String packageName = "com.jv.falcon.pro";

	public static void handleLoadPackage(final LoadPackageParam lpparam)
			throws Throwable {

		if (!lpparam.packageName.equals(packageName)) {
			return;
		} else {
			Utils.xlog("FalconPro: Hooking methods");
		}

		try {

			// Still does not work, NoSuchMethodError

			Utils.xlog("FalconPro: Hooking method for Consumer Key");
			XposedHelpers.findAndHookMethod("xr", lpparam.classLoader, "e",
					Context.class, XC_MethodReplacement
							.returnConstant(XTokenPatcher.strConsumerKey));

			Utils.xlog("FalconPro: Hooking method for Consumer Secret");
			XposedHelpers.findAndHookMethod("xr", lpparam.classLoader, "f",
					Context.class, XC_MethodReplacement
							.returnConstant(XTokenPatcher.strConsumerSecret));

			Utils.xlog("FalconPro: Done, hope this works");
		} catch (Throwable t) {
			XposedBridge.log(t);
		}
	}
}