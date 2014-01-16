package twitter.token.patcher.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import twitter.token.patcher.Utils;
import twitter.token.patcher.XTokenPatcher;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Carbon {

	public static final String packageName = "com.dotsandlines.carbon";

	public static void handleLoadPackage(final LoadPackageParam lpparam)
			throws Throwable {

		if (!lpparam.packageName.equals(packageName)) {
			return;
		} else {
			Utils.xlog("Carbon: Hooking method");
		}

		try {
			final Class<?> carbonClass = XposedHelpers.findClass(packageName + ".core.Carbon", lpparam.classLoader);
			// XposedBridge.log("Carbon: carbonClass = " + carbonClass);

			Constructor<?> carbonConstructor = XposedHelpers.findConstructorBestMatch(carbonClass);
			// XposedBridge.log("Carbon: carbonConsrt = " + carbonConstructor);

			XposedBridge.hookMethod(carbonConstructor, new XC_MethodHook() {
				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {

					try {
						Utils.xlog("Carbon: Class location: " + carbonClass);

						Field dField = XposedHelpers.findField(carbonClass, "mApiConsuemerKey");

						//Utils.xlog("Carbon: Old field Value is: " + dField.get(param.thisObject));
						dField.set(null, XTokenPatcher.strConsumerKey);
						//Utils.xlog("Carbon: New field Value is: " + dField.get(param.thisObject));

						dField = XposedHelpers.findField(carbonClass, "mApiConsumerSecret");

						//Utils.xlog("Carbon: Old field Value is: " + dField.get(param.thisObject));
						dField.set(null, XTokenPatcher.strConsumerSecret);
						//Utils.xlog("Carbon: New field Value is: " + dField.get(param.thisObject));
					} catch (Exception e) {
						XposedBridge.log("Carbon: ERROR: " + e.getMessage());
						XposedBridge.log("Carbon: Stack: " + e.getStackTrace());
					}
				}
			});

		} catch (Throwable t) {
			XposedBridge.log(t);
		}
	}

}