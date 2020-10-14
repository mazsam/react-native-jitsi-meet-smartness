package com.ko.jitsimeet.adapters

import com.facebook.react.bridge.ReadableMap
import com.ko.jitsimeet.models.FeatureFlagsModel
import com.ko.jitsimeet.models.UserInfoModel
import org.jitsi.meet.sdk.JitsiMeetUserInfo

typealias ConvertTo<T, U> = (T) -> U
typealias ConvertToUserInfoModel =  ConvertTo<ReadableMap, UserInfoModel>
typealias ConvertToFeatureFlagsModel =  ConvertTo<ReadableMap, FeatureFlagsModel>
typealias ConvertToJitsiMeetUserInfo =  ConvertTo<UserInfoModel,JitsiMeetUserInfo>

var toUserInfoModel : ConvertToUserInfoModel = { source ->
  val to = UserInfoModel()
  source.hasKey("email").let {
    to.email = source.getString("email")
  }
  source.hasKey("displayName").let {
    to.displayName = source.getString("displayName")
  }
  to
}

var toFeatureFlagsModel : ConvertToFeatureFlagsModel = { source ->
  val to = FeatureFlagsModel()
  val iterator =  source.keySetIterator()
  while (iterator.hasNextKey()) {
    val featureFlag: String = iterator.nextKey()
    to[featureFlag] = source.getBoolean(featureFlag)
  }
  to
}

var toJitsiMeetUserInfo : ConvertToJitsiMeetUserInfo = { source ->
  val to =  JitsiMeetUserInfo()
  to.email = source.email
  to.displayName = source.displayName
  to
}

