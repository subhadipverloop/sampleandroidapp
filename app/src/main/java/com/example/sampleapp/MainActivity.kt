package com.example.sampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import io.verloop.sdk.Verloop
import io.verloop.sdk.VerloopConfig
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val uid = UUID.randomUUID().toString()

//        capturing click event
        val click = findViewById<Button>(R.id.click_verloop);

        click.setOnClickListener {
            Toast.makeText(this, "Verloop Button Clicked", Toast.LENGTH_SHORT).show()
//            var uid = uid
            val uid = UUID.randomUUID().toString()

            var customFields: ArrayList<VerloopConfig.CustomField> = ArrayList()
            customFields.add(
                VerloopConfig.CustomField(
                    "name",
                    "androidUser",
                    scope = VerloopConfig.Scope.ROOM
                )
            )
//            val config = VerloopConfig.Builder()
//                .clientId("subhadipmondal")
//                .userId(uid)
//                .userName("android User")
//                .userEmail("androiduser@gmail.com")
//                .closeExistingChat(false)
//                .fields(customFields)
//                .build()
            val config = VerloopConfig.Builder()
                .clientId("prestigeconstructions")
                .recipeId("Bbviwo748MxhkrruP")
                .userId(uid)
                .userName("android User")
                .userEmail("androiduser@gmail.com")
                .closeExistingChat(false)
                .fields(customFields)
                .build()
            val verloop = Verloop(this,config)
            verloop.showChat()
        }
    }
}