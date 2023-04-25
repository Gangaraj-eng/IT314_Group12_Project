package com.mypackage.it314_health_center.videocalling

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.*
import com.mypackage.it314_health_center.videocalling.AroraFiles.RtcTokenBuilder2
import io.agora.rtc2.*
import io.agora.rtc2.video.VideoCanvas

// Fill the App ID of your project generated on Agora Console.
private const val appId = "07de1a5578dd41adbd69d4ee74558a2e"
private const val appCertificate = "527a1a65cfc34f18bb622b961117fa98"

// An integer that identifies the local user.
private const val uid = 0
private var isJoined = false

private var agoraEngine: RtcEngine? = null

//SurfaceView to render local video in a Container.
@SuppressLint("StaticFieldLeak")
private var localSurfaceView: SurfaceView? = null

//SurfaceView to render Remote video in a Container.
@SuppressLint("StaticFieldLeak")
private var remoteSurfaceView: SurfaceView? = null

class DoctorSideOnlineConference : AppCompatActivity() {

    private lateinit var token: String
    private lateinit var channelName: String
    private val PERMISSION_REQ_ID: Int = 22
    private val REQUESTED_PERMISSIONS: Array<String> = arrayOf(
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.CAMERA
    )

    private fun checkSelfPermission(): kotlin.Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                REQUESTED_PERMISSIONS.get(0)
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                REQUESTED_PERMISSIONS.get(1)
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    private fun setupVideoSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            // By default, the video module is disabled, call enableVideo to enable it.
            agoraEngine?.enableVideo()
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }

    private fun setupRemoteVideo(uid: Int) {
        val container = findViewById<FrameLayout>(R.id.remote_video_view_container)
        remoteSurfaceView = SurfaceView(baseContext)
        remoteSurfaceView?.setZOrderMediaOverlay(true)
        container.addView(remoteSurfaceView)
        agoraEngine!!.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
        // Display RemoteSurfaceView.
        remoteSurfaceView?.visibility = View.VISIBLE
    }

    private fun setupLocalVideo() {
        val container = findViewById<FrameLayout>(R.id.local_video_view_container)
        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = SurfaceView(baseContext)
        container.addView(localSurfaceView)
        // Call setupLocalVideo with a VideoCanvas having uid set to 0.
        agoraEngine!!.setupLocalVideo(
            VideoCanvas(
                localSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                0
            )
        )
    }

    fun generate_token() {
        val tokenbuilder =
            RtcTokenBuilder2()
        // time it is valid for
        val timestamp = (System.currentTimeMillis() / 1000 + 30 * 60).toInt()
        val uid = 0
        channelName = "RandomChannel" + mAuth.currentUser!!.uid.toString().substring(0, 5)
        token = tokenbuilder.buildTokenWithUid(
            appId, appCertificate, channelName, uid,
            RtcTokenBuilder2.Role.ROLE_PUBLISHER, timestamp, timestamp
        )
        Log.d("123", token)
        mdbref.child("meeting_rooms").child(patient_ID)
            .child("ChannelName").setValue(channelName)
        mdbref.child("meeting_rooms").child(patient_ID)
            .child("token").setValue(token)
    }

    fun joinChannel(view: View?) {

        if (checkSelfPermission()) {
            val options = ChannelMediaOptions()

            // For a Video call, set the channel profile as COMMUNICATION.
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            // Display LocalSurfaceView.
            setupLocalVideo()
            localSurfaceView!!.visibility = View.VISIBLE
            // Start local preview.
            agoraEngine!!.startPreview()
            // Join the channel with a temp token.
            // You need to specify the user ID yourself, and ensure that it is unique in the channel.
            agoraEngine!!.joinChannel(token, channelName, uid, options)
        } else {
            Toast.makeText(applicationContext, "Permissions was not granted", Toast.LENGTH_SHORT)
                .show()
        }
        findViewById<FloatingActionButton>(R.id.LeaveButton).isEnabled = true
        findViewById<FloatingActionButton>(R.id.JoinButton).isEnabled = false
    }

    private lateinit var mdbref: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var patient_ID: String
    fun leaveChannel(view: View?) {
        if (!isJoined) {
            showMessage("Join a channel first")
        } else {
            agoraEngine!!.leaveChannel()
            showMessage("You left the channel")
            // Stop remote video rendering.
            if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
            // Stop local video rendering.
            if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
            isJoined = false
        }
        mdbref.child("meeting_rooms").child(patient_ID).removeValue()
        findViewById<FloatingActionButton>(R.id.LeaveButton).isEnabled = false
        findViewById<FloatingActionButton>(R.id.JoinButton).isEnabled = true
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote host joining the channel to get the uid of the host.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            showMessage("Remote user joined $uid")

            // Set the remote video view
            runOnUiThread { setupRemoteVideo(uid) }
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            isJoined = true
            showMessage("Joined Channel $channel")

        }

        override fun onUserOffline(uid: Int, reason: Int) {
            showMessage("Remote user offline $uid $reason")
            runOnUiThread { remoteSurfaceView!!.visibility = View.GONE }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_side_online_conference)

        window.statusBarColor = Color.WHITE
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
            true

        patient_ID = intent.getStringExtra("patient_id") as String
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        Log.d("123", "receiver=" + patient_ID)
        findViewById<ImageButton>(R.id.back_btn)
            .setOnClickListener {
                mdbref.child("meeting_rooms").child(patient_ID)
                    .removeValue().addOnCompleteListener {
                        finish()
                    }
            }
        mdbref = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        setupVideoSDKEngine();
        generate_token()

    }

    fun showMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        agoraEngine!!.stopPreview()
        agoraEngine!!.leaveChannel()

        // Destroy the engine in a sub-thread to avoid congestion
        Thread {
            RtcEngine.destroy()
            agoraEngine = null
        }.start()
    }

    override fun finish() {
        super.finish()
        mdbref.child("meeting_rooms").child(patient_ID)
            .removeValue().addOnCompleteListener {
            }
    }

}