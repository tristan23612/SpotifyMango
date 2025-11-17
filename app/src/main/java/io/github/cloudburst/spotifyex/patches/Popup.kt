package io.github.tristan23612.spotifymango.patches

import android.util.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import org.luckypray.dexkit.DexKitBridge
import org.luckypray.dexkit.query.matchers.base.OpCodesMatcher
import io.github.tristan23612.spotifymango.Module.TAG
import org.luckypray.dexkit.result.MethodData


fun removePopups(bridge: DexKitBridge, cl: ClassLoader) {
    val apply = bridge.findMethod {
        matcher {
            name = "apply"
            invokeMethods {
                add {
                    declaredClass("com.spotify.messaging.clientmessagingplatform.clientmessagingplatformsdk.data.models.network.FetchMessageListRequest")
                }
            }
        }
    }

    val responseClazz = cl.loadClass("com.spotify.messaging.clientmessagingplatform.clientmessagingplatformsdk.data.models.network.FetchMessageListResponse")
    val messagesList = responseClazz.getDeclaredField("messages")
    messagesList.isAccessible = true

    XposedBridge.hookMethod(apply.firstOrNull()?.getMethodInstance(cl), object : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam?) {
            val arg1 = param?.args?.get(0)
            if (arg1?.javaClass != responseClazz)
                return
            Log.d(TAG, "Removing popups: $arg1")
            messagesList.set(arg1, emptyList<Any>())
        }
    })

}
