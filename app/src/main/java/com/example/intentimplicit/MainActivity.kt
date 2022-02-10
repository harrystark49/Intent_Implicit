package com.example.intentimplicit

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    var STORAGE_PERMISSION_CODE=1000
    var REQUEST_CODE=100
    var uri:Uri?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        select.setOnClickListener {
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                var intent= Intent(Intent.ACTION_PICK)
                intent.type="image/*"
                startActivityForResult(intent,REQUEST_CODE)
            }else{
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
            }

        }
        share.setOnClickListener {
            share(uri!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUEST_CODE){
            uri=data?.data
            var data=data?.data
            iv.setImageURI(data)
            var file= File(data?.path)



            var mActivity=this
//            val videoURI =if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                mActivity?.let { mActivity?.packageName?.let { it1 ->
//                    FileProvider.getUriForFile(it,
//                        it1, file)
//                } }
//            else
                Uri.fromFile(file)

        }
    }

    fun share(uri: Uri){
        this?.let {
            ShareCompat.IntentBuilder.from(it)
                .setStream(uri)
                .setType("image/*")
                .setText("hehee")
                .setChooserTitle("share")
                .startChooser()
        }
    }
}