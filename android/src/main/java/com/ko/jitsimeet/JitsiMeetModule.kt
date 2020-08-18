package com.ko.jitsimeet

import com.facebook.react.bridge.*

const val MODULE_NAME = "JitsiMeet"
class JitsiMeetModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  override fun getName(): String {
    return MODULE_NAME
  }

  @ReactMethod
  fun multiply(a: Int, b: Int, promise: Promise) {
    promise.resolve(a * b)
  }

  @ReactMethod
  fun call(url: String){
     UiThreadUtil.runOnUiThread {
       this.currentActivity?.let { RnJitsiMeetActivity.launch(it, url) }
     }

  }



}
