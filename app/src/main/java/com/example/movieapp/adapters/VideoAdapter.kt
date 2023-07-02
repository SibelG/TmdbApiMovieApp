package com.example.movieapp.adapters


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.Network.videos.Video
import com.example.movieapp.R
import com.example.movieapp.activities.PlayVideoActivity
import com.example.movieapp.activities.TVShowDetailActivity
import com.example.movieapp.utils.Constants
import javax.inject.Inject


class VideoAdapter @Inject constructor(private val mContext: Context) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var mVideos = ArrayList<Video>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false)
        )
    }
    fun submitList(videoList: List<Video>) {
        this.mVideos = videoList as ArrayList<Video>
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {

        holder.container.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.translate)

        Glide.with(mContext.getApplicationContext())
            .load(Constants.YOUTUBE_THUMBNAIL_BASE_URL + mVideos[position].key + Constants.YOUTUBE_THUMBNAIL_IMAGE_QUALITY)
            .asBitmap()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.videoImageView)


        if (mVideos[position].name != null) holder.videoTextView.setText(mVideos[position].name) else holder.videoTextView.text =
            ""
        Log.d("img",mVideos[position].key)
    }

    override fun getItemCount(): Int {
        return mVideos.size
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoCard: CardView
        var videoImageView: ImageView
        var videoTextView: TextView
        lateinit var container: LinearLayout
        lateinit var playLayout: LinearLayout

        init {
            playLayout = itemView.findViewById(R.id.play_layout)
            videoCard = itemView.findViewById(R.id.card_view_video)
            videoImageView = itemView.findViewById(R.id.image_view_video) as ImageView
            videoTextView = itemView.findViewById(R.id.text_view_video_name)
            container = itemView.findViewById(R.id.container)
            videoCard.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val intent = Intent(mContext, PlayVideoActivity::class.java)
                    intent.putExtra(
                        Constants.VIDEO_URI,
                        mVideos[adapterPosition].key
                    )
                    mContext.startActivity(intent)

                }
            })

            playLayout.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                      val youtubeIntent = Intent(
                      Intent.ACTION_VIEW,
                      Uri.parse(Constants.YOUTUBE_WATCH_BASE_URL + mVideos[adapterPosition].key)
                  )
                  mContext.startActivity(youtubeIntent)
                }
            })
        }
    }
}