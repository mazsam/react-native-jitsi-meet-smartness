package com.ko.jitsimeet

import android.os.Bundle
import com.facebook.react.bridge.*
import java.net.URL

const val MODULE_NAME = "JitsiMeet"

class JitsiMeetModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  override fun getName(): String {
    return MODULE_NAME
  }

  @ReactMethod
  fun call(url: String, userInfoParams: ReadableMap) {
    UiThreadUtil.runOnUiThread {
      val userInfoBundle = Bundle()
      userInfoParams.hasKey("displayName")?.let {
        userInfoBundle.putString("displayName", userInfoParams.getString("displayName"))
      }
      userInfoParams.hasKey("email")?.let {
        userInfoBundle.putString("email", userInfoParams.getString("email"))
      }
      if (userInfoParams.hasKey("avatarURL")){
        userInfoBundle.putString("avatarURL", userInfoParams.getString("avatarURL"))
      }

      this.currentActivity?.let { JitsiMeetActivity.launch(it, url, userInfoBundle) }
    }

  }


}
