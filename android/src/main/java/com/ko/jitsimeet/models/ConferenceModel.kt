package com.ko.jitsimeet.models

import java.io.Serializable

data class ConferenceModel(var url: String, var userInfo: UserInfoModel, var featureFlags: FeatureFlagsModel) : Serializable
