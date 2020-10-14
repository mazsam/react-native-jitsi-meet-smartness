package com.ko.jitsimeet.activities

import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import androidx.fragment.app.FragmentActivity
import com.facebook.react.modules.core.PermissionListener
import com.ko.jitsimeet.adapters.toJitsiMeetUserInfo
import com.ko.jitsimeet.models.ConferenceModel
import com.ko.jitsimeet.navigator.EXTRA_RESULT_BUNDLE_DATA
import org.jitsi.meet.sdk.*
import java.lang.Error


const val EXTRA_JITSI_CONFERENCE_MODEL = "com.ko.jitsimeet.CONFERENCE_MODEL"
const val EXTRA_RESULT_RECEIVER = "com.ko.jitsimeet.RECEIVER"


const val RESULT_CONFERENCE_TERMINATED = 1002
const val RESULT_CONFERENCE_JOINED = 1003


const val EVENT_ON_CONFERENCE_TERMINATED = "onConferenceTerminated"
const val EVENT_ON_CONFERENCE_JOINED = "onConferenceJoined"


class JitsiMeetActivity : FragmentActivity(), JitsiMeetActivityInterface, JitsiMeetViewListener {

  private var view: JitsiMeetView? = null

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    JitsiMeetActivityDelegate.onActivityResult(
      this, requestCode, resultCode, data
    )
  }

  override fun onBackPressed() {
    JitsiMeetActivityDelegate.onBackPressed()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    /** Retrieve all the Jitsi params **/
    val conferenceBundle = intent.getBundleExtra(EXTRA_RESULT_BUNDLE_DATA)
    val conferenceModel : ConferenceModel = conferenceBundle.getSerializable(EXTRA_JITSI_CONFERENCE_MODEL) as ConferenceModel

    /** Build the options to feed Jitsi **/
    val optionsBuilder = JitsiMeetConferenceOptions.Builder()
      .setRoom(conferenceModel.url)
    optionsBuilder.setAudioMuted(false)
    optionsBuilder.setVideoMuted(true)
    optionsBuilder.setUserInfo( toJitsiMeetUserInfo(conferenceModel.userInfo))
    val featureIterator = conferenceModel.featureFlags.keys.iterator()
    while (featureIterator.hasNext()) {
      val featureFlag: String = featureIterator.next()
      optionsBuilder.setFeatureFlag(featureFlag, conferenceModel.featureFlags.get(featureFlag)!!)
    }
    val options = optionsBuilder.build()

    /** Prepare the jitsi view with options **/
    view = JitsiMeetView(this)
    view!!.listener = this
    view!!.join(options)
    setContentView(view)
  }
  override fun onDestroy() {
    super.onDestroy()
    view!!.listener = null
    view!!.dispose()
    view = null
    JitsiMeetActivityDelegate.onHostDestroy(this)
  }

  public override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    JitsiMeetActivityDelegate.onNewIntent(intent)
  }

  override fun requestPermissions(permissions: Array<String>, requestCode: Int, listener: PermissionListener) {
    JitsiMeetActivityDelegate.requestPermissions(this, permissions, requestCode, listener)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  override fun onResume() {
    super.onResume()
    JitsiMeetActivityDelegate.onHostResume(this)
  }

  override fun onPause() {
    super.onPause()
    JitsiMeetActivityDelegate.onHostPause(this)
  }

  override fun onConferenceTerminated(data: Map<String, Any>?) {
    val receiver : ResultReceiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
    val args = Bundle()
    val url : String = data?.get("url") as String
    val error : String? = data["error"] as String?
    args.putString("url", url)
    args.putString("error", error)
    receiver.send(RESULT_CONFERENCE_TERMINATED, args)
    finish()
  }

  override fun onConferenceJoined(data: Map<String, Any>?) {
    val receiver : ResultReceiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
    val args = Bundle()
    val url : String = data?.get("url") as String
    val error : String? = data["error"] as String?
    args.putString("url", url)
    args.putString("error", error)
    receiver.send(RESULT_CONFERENCE_JOINED, args)
  }

  override fun onConferenceWillJoin(param: Map<String, Any>?) {
  }


}

