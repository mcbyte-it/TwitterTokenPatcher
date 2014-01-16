package twitter.token.patcher.app;

import twitter.token.patcher.Utils;
import twitter.token.patcher.XTokenPatcher;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Janetter {

	public static final String packageName = "net.janesoft.janetter.android.free";

	public static void handleLoadPackage(final LoadPackageParam lpparam)
			throws Throwable {

		if (!lpparam.packageName.equals(packageName)) {
			return;
		} else {
			Utils.xlog("Hooking App Janetter...");
		}

		try {
			Utils.xlog("Janetter: Hooking method for Consumer Key");
			XposedHelpers.findAndHookMethod(
					"net.janesoft.janetter.android.core.c.a.bw",
					lpparam.classLoader, "a", int.class, XC_MethodReplacement.returnConstant(XTokenPatcher.strConsumerKey));

			Utils.xlog("Janetter: Hooking method for Consumer Secret");
			XposedHelpers.findAndHookMethod(
					"net.janesoft.janetter.android.core.c.a.bw",
					lpparam.classLoader, "b", int.class, XC_MethodReplacement.returnConstant(XTokenPatcher.strConsumerSecret));

		} catch (Throwable t) {
			XposedBridge.log(t);
		}
	}

}