package com.example.movieapp.adapters

import android.content.Context
import android.content.Intent
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.Network.Movies.MovieBrief
import com.example.movieapp.Network.tvshows.TVShowBrief
import com.example.movieapp.R
import com.example.movieapp.activities.TVShowDetailActivity
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.Favourite.addTVShowToFav
import com.example.movieapp.utils.Favourite.isTVShowFav
import javax.inject.Inject


class TVShowBriefsSmallAdapter @Inject constructor(private val mContext: Context) :
    RecyclerView.Adapter<TVShowBriefsSmallAdapter.TVShowViewHolder>() {

    private var mTVShows = ArrayList<TVShowBrief>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        return TVShowViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_show_small, parent, false)
        )
    }
    fun submitList(tvList: List<TVShowBrief>) {
        this.mTVShows = tvList as ArrayList<TVShowBrief>
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        Glide.with(mContext.getApplicationContext())
            .load(Constants.IMAGE_LOADING_BASE_URL_342.toString() + mTVShows[position].posterPath)
            .asBitmap()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.tvShowPosterImageView)
        if (mTVShows[position].name != null) holder.tvShowTitleTextView.text =
            mTVShows[position].name else holder.tvShowTitleTextView.text =
            ""
        if (isTVShowFav(mContext, mTVShows[position].id)) {
            holder.tvShowFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp)
            holder.tvShowFavImageButton.isEnabled = false
        } else {
            holder.tvShowFavImageButton.setImageResource(R.mipmap.ic_favorite_border_black_18dp)
            holder.tvShowFavImageButton.isEnabled = true
        }
    }

    override fun getItemCount(): Int {
        return mTVShows.size
    }

    inner class TVShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvShowCard: CardView
        var tvShowPosterImageView: ImageView
        var tvShowTitleTextView: TextView
        var tvShowFavImageButton: ImageButton

        init {
            tvShowCard = itemView.findViewById(R.id.card_view_show_card)
            tvShowPosterImageView = itemView.findViewById(R.id.image_view_show_card) as ImageView
            tvShowTitleTextView = itemView.findViewById(R.id.text_view_title_show_card)
            tvShowFavImageButton = itemView.findViewById(R.id.image_button_fav_show_card)
            tvShowPosterImageView.getLayoutParams().width =
                ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31).toInt())
            tvShowPosterImageView.getLayoutParams().height =
                ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31 / 0.66).toInt())
            tvShowCard.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val intent = Intent(mContext, TVShowDetailActivity::class.java)
                    intent.putExtra(Constants.TV_SHOW_ID, mTVShows[adapterPosition].id)
                    mContext.startActivity(intent)
                }
            })
            tvShowFavImageButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    addTVShowToFav(
                        mContext,
                        mTVShows[adapterPosition].id,
                        mTVShows[adapterPosition].posterPath,
                        mTVShows[adapterPosition].name
                    )
                    tvShowFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp)
                    tvShowFavImageButton.isEnabled = false
                }
            })
        }
    }

}