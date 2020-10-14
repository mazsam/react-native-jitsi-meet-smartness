package com.ko.jitsimeet.navigator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import kotlin.reflect.KClass
const val EXTRA_RESULT_RECEIVER = "com.ko.jitsimeet.RECEIVER"
const val EXTRA_RESULT_BUNDLE_DATA = "com.ko.jitsimeet.BUNDLE_DATE"


interface ActivityNavigatorListener {
  fun onReceiveResult(resultCode: Int, resultData: Bundle)
}

interface ActivityNavigator {
  fun navigateWithListener(ActivityClass : Class<*>, bundle: Bundle, listener: ActivityNavigatorListener)
}

class ActivityResultReceiver(handler: Handler?) : ResultReceiver(handler) {
  private var listener: ActivityNavigatorListener? = null

  fun setListener(listener: ActivityNavigatorListener) {
    this.listener = listener
  }

  override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
    this.listener?.onReceiveResult(resultCode, resultData)
  }
}

class JitsiMeetModuleNavigator(private val context: Context, private val resultReceiver: ActivityResultReceiver) : ActivityNavigator{
  override fun navigateWithListener(ActivityClass : Class<*>, bundle: Bundle, listener: ActivityNavigatorListener) {
    resultReceiver.setListener(listener)
    val intent = Intent(context, ActivityClass).apply {
      putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
      putExtra(EXTRA_RESULT_BUNDLE_DATA, bundle)
    }
    context.startActivity(intent)
  }

}
