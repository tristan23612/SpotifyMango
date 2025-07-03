package io.github.cloudburst.spotifyex.patches

import android.util.Log
import de.robv.android.xposed.XposedBridge
import io.github.cloudburst.spotifyex.Module.TAG
import org.luckypray.dexkit.DexKitBridge

fun patchIntegrity(bridge: DexKitBridge, cl: ClassLoader) {
    val integrityMethod = bridge.findMethod {
        matcher {
            usingStrings("standard_pi_init", "outcome", "success")
        }
    }

    val loaded = integrityMethod.firstOrNull()?.getMethodInstance(cl) ?: return
    XposedBridge.hookMethod(loaded, object : de.robv.android.xposed.XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam?) {
            Log.d(TAG, "Integrity check bypassed")
            param?.result = true
        }
    })
}
