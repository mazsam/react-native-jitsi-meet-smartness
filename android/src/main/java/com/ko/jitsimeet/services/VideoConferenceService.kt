package com.ko.jitsimeet.services

import com.ko.jitsimeet.models.ConferenceModel

interface VideoConferenceServiceListener {
  fun onTerminated()
  fun onJoined()
}
interface VideoConferenceService {
  fun join(conferenceModel: ConferenceModel, listener : VideoConferenceServiceListener)
}
