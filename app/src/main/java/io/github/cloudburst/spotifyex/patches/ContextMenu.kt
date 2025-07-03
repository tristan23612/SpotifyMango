package io.github.cloudburst.spotifyex.patches

import android.util.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import org.luckypray.dexkit.DexKitBridge
import io.github.cloudburst.spotifyex.Module.TAG


fun patchContextMenu(bridge: DexKitBridge, cl: ClassLoader) {
    val booleanFlags = bridge.findClass {
        matcher {
            usingStrings(
                "hide_playlist_radio",
                "premium_upsell_panel_enabled",
                "remove_ads_upsell_enabled"
            )
        }
    }.findMethod {
        matcher {
            returnType = "boolean"
        }
    }.filter {
        it.getClass()?.getMethods()?.any { method ->
            method.name == "equals"
        } != true
    }

    if (booleanFlags.isEmpty()) {
        Log.w(TAG, "Failed to find boolean flags")
        return
    }

    for (method in booleanFlags) {
        XposedBridge.hookMethod(method.getMethodInstance(cl), object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                param?.result = false
            }
        })
    }
}