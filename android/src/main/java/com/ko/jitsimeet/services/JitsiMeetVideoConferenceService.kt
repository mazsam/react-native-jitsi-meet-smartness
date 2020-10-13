package com.ko.jitsimeet.services

import android.os.Bundle
import com.ko.jitsimeet.activities.JitsiMeetActivity
import com.ko.jitsimeet.activities.RESULT_CONFERENCE_JOINED
import com.ko.jitsimeet.activities.RESULT_CONFERENCE_TERMINATED
import com.ko.jitsimeet.navigator.ActivityNavigator
import com.ko.jitsimeet.navigator.ActivityNavigatorListener

class JitsiMeetVideoConferenceService(private val activityNavigator: ActivityNavigator) : VideoConferenceService {

  fun toResult(data: Bundle) : VideoConferenceResult{
    val url = data.getString("url")!!  // url is mandatory
    val error = data.getString("error")
    return VideoConferenceResult(url, error)
  }
  override fun join(conferenceBundle: Bundle, listener: VideoConferenceServiceListener) {
    activityNavigator.navigateWithListener(JitsiMeetActivity::class.java, conferenceBundle, object : ActivityNavigatorListener {
      override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
        val result = toResult(resultData)
        when (resultCode) {
          RESULT_CONFERENCE_JOINED -> listener.onJoined(result)
          RESULT_CONFERENCE_TERMINATED -> listener.onTerminated(result)
        }
      }
    })
  }

}
