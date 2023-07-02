package com.example.movieapp.utils

interface CallBacks {
    fun callbackObserver(obj: Any?)
    interface playerCallBack {
        fun onItemClickOnItem(albumId: Int?)
        fun onPlayingEnd()
    }
}