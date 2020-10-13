package com.ko.jitsimeet

import android.os.Bundle
import android.os.Handler
import androidx.annotation.Nullable
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.ko.jitsimeet.activities.EVENT_ON_CONFERENCE_JOINED
import com.ko.jitsimeet.activities.EVENT_ON_CONFERENCE_TERMINATED
import com.ko.jitsimeet.activities.EXTRA_JITSI_CONFERENCE_MODEL
import com.ko.jitsimeet.adapters.toFeatureFlagsModel
import com.ko.jitsimeet.adapters.toUserInfoModel
import com.ko.jitsimeet.models.ConferenceModel
import com.ko.jitsimeet.navigator.ActivityResultReceiver
import com.ko.jitsimeet.navigator.JitsiMeetModuleNavigator
import com.ko.jitsimeet.services.*


const val MODULE_NAME = "JitsiMeet"

/**
 *
 */
class JitsiMeetModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  /**
   * Sends a given event name to Javascript
   */
  fun sendEvent(eventName: String, params: WritableMap){
    reactApplicationContext
      .getJSModule(RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }


  override fun getName(): String {
    return MODULE_NAME
  }

  @ReactMethod
  fun join(url: String, userInfoParams: ReadableMap){
    joinJitsi(url, userInfoParams, Arguments.createMap())
  }

  @ReactMethod
  fun joinWithFeatures(url: String, userInfoParams: ReadableMap, featureFlagsParams : ReadableMap = Arguments.createMap()){
    joinJitsi(url, userInfoParams, featureFlagsParams)
  }

  private fun joinJitsi(url: String, userInfoParams: ReadableMap, featureFlagsParams : ReadableMap) {
    UiThreadUtil.runOnUiThread {

      this.currentActivity?.let{
        val userInfoModel = toUserInfoModel(userInfoParams)
        val featureFlagsModel = toFeatureFlagsModel(featureFlagsParams)
        val conferenceModel = ConferenceModel(url, userInfoModel, featureFlagsModel)

        val conferenceBundle = Bundle()
        conferenceBundle.putSerializable(EXTRA_JITSI_CONFERENCE_MODEL, conferenceModel)

        val activityResultReceiver = ActivityResultReceiver(Handler())
        val activityNavigator = JitsiMeetModuleNavigator(this.currentActivity!!, activityResultReceiver)
        val videoConferenceService = JitsiMeetVideoConferenceService(activityNavigator)
        val videoConferenceServiceListener = object: VideoConferenceServiceListener{
          override fun onJoined(result: VideoConferenceResult) {
            val params = Arguments.createMap()
            params.putString("url", result.url)
            params.putString("error", result.error)
            sendEvent(EVENT_ON_CONFERENCE_JOINED, params)
          }
          override fun onTerminated(result: VideoConferenceResult) {
            val params = Arguments.createMap()
            params.putString("url", result.url)
            params.putString("error", result.error)
            sendEvent(EVENT_ON_CONFERENCE_TERMINATED, params)
          }
        }
        videoConferenceService.join(conferenceBundle, videoConferenceServiceListener)
      }

    }

  }




}
