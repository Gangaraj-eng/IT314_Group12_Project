package com.mypackage.it314_health_center.adapters

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.models.LabReport


class LabReportAdapter(
    val context: Context?,
    val report_list: ArrayList<LabReport>
) : RecyclerView.Adapter<LabReportAdapter.ReportViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_downloadreport_content, parent, false)
        return ReportViewHolder(view)
    }

    class ReportViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val testName: TextView = itemview.findViewById(R.id.test_name)
        val issuedBy: TextView = itemview.findViewById(R.id.issued_by_name)
        val tdate: TextView = itemview.findViewById(R.id.tdate)
        val download: ImageView = itemview.findViewById(R.id.download_button)
    }

    override fun getItemCount(): Int {
        return report_list.size
    }

    private var download_id = 0L
    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.testName.text = report_list[position].test_name
        holder.issuedBy.text = report_list[position].issued_by
        holder.tdate.text = "${report_list[position].testDate}/${report_list[position].issuedDate}"
        holder.download.setOnClickListener {
            val url = report_list[position].ReportURL
            val fileName = report_list[position].test_name + report_list[position].issuedDate
            if (context != null) {
                downloadPdf(
                    context, fileName, ".pdf",
                    Environment.DIRECTORY_DOWNLOADS, url
                )
            }
        }
    }

    fun downloadPdf(
        context: Context,
        fileName: String,
        fileExtensions: String,
        destinationDirectory: String?,
        url: String?
    ) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(
            context,
            destinationDirectory,
            fileName + fileExtensions
        )
        download_id = downloadManager.enqueue(request)
    }


}