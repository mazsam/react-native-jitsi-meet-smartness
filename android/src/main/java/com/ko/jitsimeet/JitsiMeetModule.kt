package com.ko.jitsimeet

import android.os.Bundle
import android.os.Handler
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.ko.jitsimeet.adapters.toFeatureFlagsModel
import com.ko.jitsimeet.adapters.toUserInfoModel
import com.ko.jitsimeet.models.ConferenceModel
import com.ko.jitsimeet.services.JitsiMeetActivityVideoConferenceService
import com.ko.jitsimeet.services.VideoConferenceServiceListener


const val MODULE_NAME = "JitsiMeet"

/**
 *
 */
class JitsiMeetModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {


  /**
   * Sends a given event name to Javascript
   */
  fun sendEvent(eventName: String){
    reactApplicationContext
      .getJSModule(RCTDeviceEventEmitter::class.java)
      .emit(eventName, null)
  }


  override fun getName(): String {
    return MODULE_NAME
  }

  @ReactMethod
  fun call(url: String, userInfoParams: ReadableMap, featureFlagsParams : ReadableMap) {
    UiThreadUtil.runOnUiThread {


      this.currentActivity?.let{
        val userInfoModel = toUserInfoModel(userInfoParams)
        val featureFlagsModel = toFeatureFlagsModel(featureFlagsParams)
        val conferenceModel = ConferenceModel(url, userInfoModel, featureFlagsModel)

        val conferenceBundle = Bundle()
        conferenceBundle.putSerializable(EXTRA_JITSI_CONFERENCE_MODEL, conferenceModel)
        val videoConferenceService = JitsiMeetActivityVideoConferenceService(this.currentActivity!!, conferenceBundle, Handler())
        val videoConferenceServiceListener = object: VideoConferenceServiceListener{
          override fun onJoined() {
            sendEvent(EVENT_ON_CONFERENCE_JOINED)
          }

          override fun onTerminated() {
            sendEvent(EVENT_ON_CONFERENCE_TERMINATED)
          }
        }
        videoConferenceService.join(conferenceModel, videoConferenceServiceListener)
      }

    }

  }




}
