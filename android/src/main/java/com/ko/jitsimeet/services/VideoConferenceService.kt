package com.ko.jitsimeet.services

import android.os.Bundle

data class VideoConferenceResult(val url: String, val error: String?)


interface VideoConferenceServiceListener {
  fun onTerminated(result: VideoConferenceResult)
  fun onJoined(result: VideoConferenceResult)
}
interface VideoConferenceService {
  fun join(conferenceBundle: Bundle, listener : VideoConferenceServiceListener)
}
