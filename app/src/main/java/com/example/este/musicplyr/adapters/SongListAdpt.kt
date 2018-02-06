package com.example.este.musicplyr.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.example.este.musicplyr.R
import com.example.este.musicplyr.input_interface.ItemClickListener
import com.example.este.musicplyr.models.SongModel
import java.util.concurrent.TimeUnit


/**
 * Created by este on 2/6/18.
 */
class SongListAdpt(SongModel:ArrayList<SongModel>,context:Context):RecyclerView.Adapter<SongListAdpt.SongListViewQ>(){
        var mContext = context
        var sModel = SongModel

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongListViewQ {
            var view = LayoutInflater.from(parent!!.context).inflate(R.layout.tunes_row,parent,false)
            return SongListViewQ(view)
    }

    override fun onBindViewHolder(holder: SongListViewQ?, position: Int) {
        var model = sModel[position]
        var songTile = model.modelName
        var songLength = toMandS(model.modeLength.toLong())
        holder!!.songText.text = songTile
        holder.durationText.text = songLength
        holder.setOnItemClickListener(object:ItemClickListener{
            override fun itemClicker(view: View, pos: Int) {
                Toast.makeText(mContext,"SongTitle: "+songTile,Toast.LENGTH_SHORT).show()
            }
        })

    }
    fun toMandS(milsec:Long):String{
        var sLength = String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(milsec),
                TimeUnit.MILLISECONDS.toSeconds(milsec)-TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(milsec)
                    ))
        return sLength
    }

    override fun getItemCount(): Int {
        return sModel.size
    }

    class SongListViewQ(objView: View):RecyclerView.ViewHolder(objView),View.OnClickListener{
            var songText:TextView
            var durationText:TextView
            var albumArt:ImageView
            var mItemClickListener:ItemClickListener?=null
            init {
                songText = itemView.findViewById(R.id.song_title)
                durationText = itemView.findViewById(R.id.song_length)
                albumArt = itemView.findViewById(R.id.al_img_view)
                itemView.setOnClickListener(this)
            }
            fun setOnItemClickListener(itemclicklistener:ItemClickListener){
                this.mItemClickListener = itemclicklistener
            }
            override fun onClick(view: View?) {
                this.mItemClickListener!!.itemClicker(view!!,adapterPosition)
            }
        }
}