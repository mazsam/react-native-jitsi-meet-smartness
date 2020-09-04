package com.ko.jitsimeet

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter


const val MODULE_NAME = "JitsiMeet"

/**
 *
 */
class JitsiMeetModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  class JitsiResultReceiver(handler: Handler?) : ResultReceiver(handler) {
    private var listener: JitsiListener? = null

    interface JitsiListener {
      fun onTerminated()
      fun onJoined()
    }

    fun setListener(listener: JitsiListener?) {
      this.listener = listener
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
      when(resultCode){
        RESULT_CONFERENCE_JOINED -> this.listener?.onJoined()
        RESULT_CONFERENCE_TERMINATED ->  this.listener?.onTerminated()
      }
    }
  }

  /**
   * Sends a given event name to Javascript
   */
  fun sendEvent(eventName: String){
    reactApplicationContext
      .getJSModule(RCTDeviceEventEmitter::class.java)
      .emit(eventName, null)
  }

  // Bridge between current module and Jitsi activity.
  // Maps result codes of Jitsi activity (eg. 1035) to Jitsi events (eg. "onJoined")
  private  lateinit var resultReceiver : JitsiResultReceiver


  // Forwards Jitsi events to Javascript
  private val eventListener = object : JitsiResultReceiver.JitsiListener {
    override fun onJoined() {
      sendEvent(EVENT_ON_CONFERENCE_JOINED)
    }

    override fun onTerminated() {
      sendEvent(EVENT_ON_CONFERENCE_TERMINATED)
    }
  }

  init {
    // JitsiResultReceiver need  a thread handler with a message queue
    // We use the main thread to handle this
    UiThreadUtil.runOnUiThread {
      resultReceiver = JitsiResultReceiver(Handler())
      resultReceiver.setListener(eventListener)
    }
  }


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
      this.currentActivity?.let { JitsiMeetCallingActivity.launch(it, resultReceiver,  url, userInfoBundle) }
    }

  }




}
