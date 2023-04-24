package com.mypackage.it314_health_center

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mypackage.it314_health_center.adapters.ValidationFunctions
import com.mypackage.it314_health_center.adapters.medical_history_reports_adapter
import com.mypackage.it314_health_center.helpers.dbPaths
import com.mypackage.it314_health_center.models.MedicalReport
import java.util.*


class ActivityUpdateMedicalHistory : AppCompatActivity() {
    private  val mdbRef=FirebaseDatabase.getInstance().reference
    private lateinit var  user_id:String
    private lateinit var reportAdapter:medical_history_reports_adapter
    private lateinit var reportList:ArrayList<MedicalReport>
    private lateinit var recylverView:RecyclerView
    private lateinit var fileChoosebutton:Button
    private lateinit var upLoadedImageView:ImageView
    private lateinit var progressBar:LinearProgressIndicator
    private lateinit var uploadButton:Button
    private lateinit var uploadSuccessView:LinearLayout
    private lateinit var  fileType:String
    private lateinit var FileURI:Uri
    private lateinit var reportname:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_medical_history)
        user_id=FirebaseAuth.getInstance().currentUser!!.uid


        // recyler view
        recylverView=findViewById(R.id.report_listView)
        reportList= ArrayList()
        reportAdapter= medical_history_reports_adapter(this,reportList)
        recylverView.layoutManager=LinearLayoutManager(this)
        recylverView.adapter=reportAdapter

        // views
        fileChoosebutton=findViewById(R.id.choose_file)
        upLoadedImageView=findViewById(R.id.uploaded_image)
        progressBar=findViewById(R.id.uploaded_progessbar)
        uploadButton=findViewById(R.id.btn_upload_file)
        uploadSuccessView=findViewById(R.id.uploaded)

        setup()


        // choose file
        fileChoosebutton.setOnClickListener {
              val options= arrayOf<CharSequence>("Choose Image","Choose PDF","Cancel")
            val myalert:AlertDialog.Builder=AlertDialog.Builder(this)
            myalert.setTitle("select type")
            myalert.setItems(options,DialogInterface.OnClickListener{dialog,item->
                when{
                    options[item]=="Choose Image"->{
                val intent=Intent(Intent.ACTION_PICK)
                            intent.type="image/*"
                            imageLauncher.launch(intent)
                    }
                    options[item]=="Choose PDF"->{

                    val intent=Intent(Intent.ACTION_GET_CONTENT)
                        intent.type="application/pdf"
                        intent.addCategory(Intent.CATEGORY_OPENABLE)
                        pdfLauncher.launch(intent)
                    }
                    options[item]=="Cancel"->{
                        dialog.dismiss()
                    }
                }
            })
            myalert.show()
        }

        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }

    private fun setup() {

        mdbRef.child(dbPaths.MedicalHistory).child(user_id)
            .addChildEventListener(object:ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        val report=snapshot.getValue(MedicalReport::class.java)
                        if(report!=null)
                        {
                            reportList.add(report)
                            reportAdapter.notifyItemInserted(reportList.size)
                        }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        uploadButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            val edittext = EditText(this)
            alert.setMessage("Enter report name")
            alert.setView(edittext)

            alert.setPositiveButton("Done"
            ) { dialog, _ -> //What ever you want to do with the value
                reportname=edittext.text.toString()
                dialog.dismiss()
                progressBar.visibility=View.VISIBLE
                uploadFile()
            }

            alert.setNegativeButton("Cancel"
            ) { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this,"Uploading cancelled",Toast.LENGTH_SHORT)
            }

            alert.show()
            edittext.requestFocus()
        }
    }

    private fun uploadFile()
    {
            val storageReference=FirebaseStorage.getInstance().reference
            val report=MedicalReport(reportname,ValidationFunctions.convertDateInMillisToString(System.currentTimeMillis()),ValidationFunctions.convertDateInMillisToString(System.currentTimeMillis()),fileType,"")
            storageReference.child(fileType+'s').child(FileURI.lastPathSegment!!)
                .putFile(FileURI).addOnSuccessListener { it ->

                    it.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                            val reportURL=it.toString()
                        report.reportURL=reportURL
                        report.reportId=UUID.randomUUID().toString()
                        mdbRef.child(dbPaths.MedicalHistory).child(user_id)
                            .child(report.reportId).setValue(report)
                            .addOnSuccessListener {
                                Log.d("123","Uploaded")
                            }
                        uploadSuccessView.visibility=View.VISIBLE
                        uploadButton.visibility=View.GONE
                        progressBar.visibility=View.GONE
                    }
                }.addOnProgressListener {
                    val progress: Double = (100.0 * it.bytesTransferred) / it.totalByteCount
                    progressBar.progress = progress.toInt()
                }
    }


    private var imageLauncher=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val fileuri = it.data?.data!!
                upLoadedImageView.setImageURI(fileuri)
                upLoadedImageView.visibility= View.VISIBLE
                fileType=dbPaths.REPORT_IMAGE
                FileURI=fileuri
                uploadButton.visibility=View.VISIBLE
                uploadSuccessView.visibility=View.GONE
            }
        }
    private var pdfLauncher=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val fileuri = it.data?.data!!
                upLoadedImageView.setImageResource(R.drawable.pdf_image)
                upLoadedImageView.visibility=View.VISIBLE
                fileType=dbPaths.REPORT_PDF
                FileURI=fileuri
                uploadButton.visibility=View.VISIBLE
                uploadSuccessView.visibility=View.GONE
            }
        }

}