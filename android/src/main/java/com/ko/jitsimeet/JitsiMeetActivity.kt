package com.ko.jitsimeet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.facebook.react.modules.core.PermissionListener
import org.jitsi.meet.sdk.*

const val TAG = "RnJitsiMeetActivity"
const val EXTRA_JITSI_URL = "com.ko.jitsimeet.URL"
const val EXTRA_JITSI_USER_INFO = "com.ko.jitsimeet.USER_INFO"

class JitsiMeetActivity : FragmentActivity(), JitsiMeetActivityInterface, JitsiMeetViewListener {

  companion object {
    fun launch(context: Context, url: String, userInfo: Bundle) {
      val intent = Intent(context, JitsiMeetActivity::class.java).apply {
        putExtra(EXTRA_JITSI_URL, url)
        putExtra(EXTRA_JITSI_USER_INFO, userInfo)
      }
      context.startActivity(intent)
    }
  }

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

    /** Retrieve all params from caller module **/
    val url = intent.getStringExtra(EXTRA_JITSI_URL)
    var userInfo = JitsiMeetUserInfo(intent.getBundleExtra(EXTRA_JITSI_USER_INFO))

    /** Build the options to feed Jitsi **/
    val builder = JitsiMeetConferenceOptions.Builder()
      .setRoom(url)
    builder.setAudioMuted(false)
    builder.setVideoMuted(true)
    builder.setUserInfo(userInfo)
    val options = builder.build()

    /** Prepare the jitsi view with options **/
    view = JitsiMeetView(this)
    view!!.listener = this
    view!!.join(options)
    setContentView(view)
  }
  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy")
    view!!.listener = null
    view!!.dispose()
    view = null
    JitsiMeetActivityDelegate.onHostDestroy(this)
  }

  public override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    Log.d(TAG, "onNewIntent")
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
    Log.d(TAG, "onResume")
    JitsiMeetActivityDelegate.onHostResume(this)
  }

  override fun onPause() {
    super.onPause()
    JitsiMeetActivityDelegate.onHostPause(this)
  }

  override fun onConferenceTerminated(param: Map<String, Any>?) {
    Log.d(TAG,"onConferenceTerminated")
    finish()
  }

  override fun onConferenceJoined(param: Map<String, Any>?) {
    Log.d(TAG, "onConferenceJoined")
  }

  override fun onConferenceWillJoin(param: Map<String, Any>?) {
    Log.d(TAG, "onConferenceWillJoin")
  }


}
