package com.ko.jitsimeet.services

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import com.ko.jitsimeet.*
import com.ko.jitsimeet.models.ConferenceModel

class JitsiMeetActivityVideoConferenceService(private val currentActivity: Activity, private val conferenceBundle: Bundle, handler: Handler) : VideoConferenceService{

  class JitsiResultReceiver(handler: Handler?) : ResultReceiver(handler) {
    private var listener: VideoConferenceServiceListener? = null

    fun setListener(listener: VideoConferenceServiceListener) {
      this.listener = listener
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
      when(resultCode){
        RESULT_CONFERENCE_JOINED -> this.listener?.onJoined()
        RESULT_CONFERENCE_TERMINATED ->  this.listener?.onTerminated()
      }
    }
  }

  private var resultReceiver : JitsiResultReceiver = JitsiResultReceiver(handler)

  override fun join(conferenceModel: ConferenceModel, listener: VideoConferenceServiceListener) {
    resultReceiver.setListener(listener)
    this.currentActivity?.let { JitsiMeetActivity.launch(it, resultReceiver,  conferenceBundle) }
  }


}
