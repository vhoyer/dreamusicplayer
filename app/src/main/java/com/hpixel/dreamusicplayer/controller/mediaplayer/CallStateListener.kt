package com.hpixel.dreamusicplayer.controller.mediaplayer

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager



/**
 * Created by vhoyer on 22/07/17.
 */
class CallStateListener(val host : MediaPlayerService) {
    //Handle incoming phone calls
    private var ongoingCall = false
    private val phoneStateListener: MyPhoneStateListener = MyPhoneStateListener()
    private val telephonyManager: TelephonyManager

    private inner class MyPhoneStateListener : PhoneStateListener() {

        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            when (state) {
            //if at least one call exists or the phone is ringing
            //pause the MediaPlayer
                TelephonyManager.CALL_STATE_OFFHOOK,
                TelephonyManager.CALL_STATE_RINGING -> {
                    host.pauseMedia()
                    ongoingCall = true
                }
                TelephonyManager.CALL_STATE_IDLE ->{
                // Phone idle. Start playing.
                    if (ongoingCall) {
                        ongoingCall = false
                        host.resumeMedia()
                    }
                }
            }
        }
    }

    init {
        val telephony_service = Context.TELEPHONY_SERVICE

        telephonyManager = host.getSystemService(telephony_service) as TelephonyManager

        // Register the listener with the telephony manager
        // Listen for changes to the device call state.
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE)
    }
}