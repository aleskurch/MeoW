package com.example.meucats.adapter

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meucats.R
import com.example.meucats.database.CatTable
import com.example.meucats.mDb
import java.io.File

class Adapter(private val values: MutableList<String>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var image: ImageView? = null

        init {
            image = itemView?.findViewById(R.id.catIconRecyclerPart)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_for_recyclerview, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image?.let {
            Glide
                .with(holder.itemView.context)
                .load(values[position])
                .placeholder(R.drawable.spinercat)
                .into(it)
        }
        holder.image?.setOnClickListener {
            Thread {
                mDb.CatDao().insert(
                    CatTable(values[position])
                )
            }.start()
            Toast.makeText(holder.itemView.context, "❤️", Toast.LENGTH_SHORT).show()
        }
        holder.image?.setOnLongClickListener {
            downloadImage(values[position], holder.itemView.context)
            return@setOnLongClickListener true
        }
    }

    private fun downloadImage(url: String, context: Context) {
        val directory = File(Environment.DIRECTORY_DOWNLOADS)

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(url.substring(url.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }
        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                cursor.close()
            }
        }).start()
        Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()
    }
}
