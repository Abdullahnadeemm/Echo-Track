package com.example.echotrack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.example.echotrack.databinding.FavouriteViewBinding  // Replace with your actual layout binding class
import com.example.echotrack.databinding.MoreFeaturesBinding // Replace with your actual layout binding class

class FavouriteAdapter(
    private val context: Context,
    private var musicList: ArrayList<Music>,
    val playNext: Boolean = false
) : RecyclerView.Adapter<FavouriteAdapter.MyHolder>() {

    class MyHolder(val binding: FavouriteViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.songImgFV
        val name = binding.songNameFV
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = FavouriteViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = musicList[position].title
        Glide.with(context)
            .load(musicList[position].artUri)
            .apply(RequestOptions.placeholder(R.drawable.music_player_icon_slash_screen).centerCrop())
            .into(holder.image)

        if (playNext) {
            holder.root.setOnClickListener {
                val intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("class", "PlayNext")
                ContextCompat.startActivity(context, intent, null)
            }
            holder.root.setOnLongClickListener {
                val inflater = LayoutInflater.from(context)
                val customDialog = MoreFeaturesBinding.inflate(inflater, holder.root, false)
                val dialog = MaterialAlertDialogBuilder(context)
                    .setView(customDialog.root)
                    .create()
                dialog.show()
                dialog.window?.setBackgroundDrawable(ColorDrawable(0x99000000.toInt()))
                customDialog.addToPNBtn.text = "Remove"
                customDialog.addToPNBtn.setOnClickListener {
                    if (position == PlayerActivity.songPosition) {
                        Snackbar.make(
                            (context as Activity).findViewById(R.id.linearLayoutPN),
                            "Can't Remove Currently Playing Song.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        if (PlayerActivity.songPosition < position && PlayerActivity.songPosition != 0) {
                            --PlayerActivity.songPosition
                        }
                        PlayNext.playNextList.removeAt(position)
                        PlayerActivity.musicListPA.removeAt(position)
                        notifyItemRemoved(position)
                    }
                    dialog.dismiss()
                }
                true
            }
        } else {
            holder.root.setOnClickListener {
                val intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("class", "FavouriteAdapter")
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int = musicList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavourites(newList: ArrayList<Music>) {
        musicList.clear()
        musicList.addAll(newList)
        notifyDataSetChanged()
    }
}
