package com.mypackage.it314_health_center.videocalling

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mypackage.it314_health_center.*
import com.mypackage.it314_health_center.R
import io.agora.rtc2.*
import io.agora.rtc2.video.VideoCanvas
import java.text.SimpleDateFormat

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

class PatientOnlineConsultation : AppCompatActivity() {

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

    private lateinit var mdbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    fun generate_token() {
        val uid = mAuth.currentUser!!.uid
        mdbRef.child("meeting_rooms").child(uid)
            .child("ChannelName").get().addOnSuccessListener {
                channelName = it.value.toString()
            }
        mdbRef.child("meeting_rooms").child(uid)
            .child("token").get().addOnSuccessListener {
                token = it.value.toString()
            }
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
    }

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
    private lateinit var noappointments_view: TextView
    private lateinit var appointment_view: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_consultation)
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        noappointments_view = findViewById(R.id.no_appointments)
        appointment_view = findViewById(R.id.current_appointment_view)
        val start_btn = appointment_view.findViewById<MaterialButton>(R.id.start_apt_button)
        mdbRef = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        start_btn.setOnClickListener {
            appointment_view.visibility = View.GONE
            findViewById<RelativeLayout>(R.id.video_calling_view).visibility = View.VISIBLE
        }
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }
        setupVideoSDKEngine();
        mdbRef.child("appointments").child("upcoming")
            .child(mAuth.currentUser!!.uid).get().addOnSuccessListener {
                if (it.exists()) {
                    for (aptx in it.children) {
                        if (aptx.child("type").value == "Offline")
                            return@addOnSuccessListener
                        noappointments_view.visibility = View.GONE
                        appointment_view.visibility = View.VISIBLE
                        var apt = aptx.getValue(BasicAppiontment::class.java)
                        if (apt != null) {
                            appointment_view.findViewById<TextView>(R.id.time_view).text = apt.time
                            appointment_view.findViewById<TextView>(R.id.date_view).text = apt.date
                            val fmillis =
                                (SimpleDateFormat("dd/MM/yyyy hh:mm").parse(apt.date + " " + apt.time)).time
                            val cmillis = System.currentTimeMillis()
                            Log.d("123", fmillis.toString())
                            Log.d("123", cmillis.toString())
                            Log.d("123", (fmillis - cmillis).toString())
                            if (fmillis > cmillis) {
                                val timer =
                                    object : CountDownTimer(fmillis - cmillis, fmillis - cmillis) {
                                        override fun onTick(millisUntilFinished: Long) {

                                        }

                                        override fun onFinish() {
                                            start_btn.isEnabled = true
                                        }
                                    }
                                timer.start()
                            } else {
                                start_btn.isEnabled = true
                            }
                        }

                    }


                } else {
                    noappointments_view.visibility = View.VISIBLE
                    appointment_view.visibility = View.GONE
                }
            }
        mdbRef.child("meeting_rooms").child(mAuth.currentUser!!.uid)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    generate_token()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    generate_token()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    generate_token()
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    generate_token()
                }

                override fun onCancelled(error: DatabaseError) {
                    generate_token()
                }

            })


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


}