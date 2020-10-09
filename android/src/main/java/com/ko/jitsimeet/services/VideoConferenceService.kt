package com.ko.jitsimeet.services

import android.os.Bundle

interface VideoConferenceServiceListener {
  fun onTerminated()
  fun onJoined()
}
interface VideoConferenceService {
  fun join(conferenceBundle: Bundle, listener : VideoConferenceServiceListener)
}
