package com.example.movieapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.example.movieapp.Network.Movies.MovieBrief
import com.example.movieapp.Network.Movies.MovieCastBrief
import com.example.movieapp.R
import com.example.movieapp.activities.PersonDetailActivity
import com.example.movieapp.utils.Constants
import javax.inject.Inject


class MovieCastsAdapter @Inject constructor(private val mContext: Context) :
    RecyclerView.Adapter<MovieCastsAdapter.CastViewHolder>() {
    private var mCasts = ArrayList<MovieCastBrief>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_cast, parent, false)
        )
    }
    fun submitList(castList: List<MovieCastBrief>) {
        this.mCasts = castList as ArrayList<MovieCastBrief>
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        Glide.with(mContext.getApplicationContext())
            .load(Constants.IMAGE_LOADING_BASE_URL_342 + mCasts[position].profilePath)
            .asBitmap()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.castImageView)
        if (mCasts[position].name != null) holder.nameTextView.text =
            mCasts[position].name else holder.nameTextView.text =
            ""
        if (mCasts[position].character != null) holder.characterTextView.text =
            mCasts[position].character else holder.characterTextView.text =
            ""
    }

    override fun getItemCount(): Int {
        return mCasts.size
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var castImageView: ImageView
        var nameTextView: TextView
        var characterTextView: TextView

        init {
            castImageView = itemView.findViewById(R.id.image_view_cast) as ImageView
            nameTextView = itemView.findViewById(R.id.text_view_cast_name)
            characterTextView = itemView.findViewById(R.id.text_view_cast_as)
            castImageView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val intent = Intent(mContext, PersonDetailActivity::class.java)
                    intent.putExtra(Constants.PERSON_ID, mCasts[adapterPosition].id)
                    mContext.startActivity(intent)
                }
            })
        }
    }

}