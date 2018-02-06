package com.example.este.musicplyr

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.este.musicplyr.adapters.SongListAdpt
import com.example.este.musicplyr.models.SongModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var sModelData:ArrayList<SongModel> = ArrayList()
    var sListAdapt:SongListAdpt?=null
    companion object {
        val PERMISSION_REQUEST_CODE = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE)
        }else{
            pushData()

        }

    }

    fun pushData(){
        var songBar:Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null,null,null)

        while (songBar !=null && songBar.moveToNext()){
            var songTitle = songBar.getString(songBar.getColumnIndex(MediaStore.Audio.Media.TITLE))
            var songDuration = songBar.getString(songBar.getColumnIndex((MediaStore.Audio.Media.DURATION)))
            sModelData.add(SongModel(songTitle,songDuration))

        }
        sListAdapt = SongListAdpt(sModelData,applicationContext)
        var layoutManager = LinearLayoutManager(applicationContext)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = sListAdapt
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
       if(requestCode == PERMISSION_REQUEST_CODE){
           if(grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               Toast.makeText(applicationContext,"Permission Granted",Toast.LENGTH_SHORT).show()
               pushData()
           }
       }
    }
}
