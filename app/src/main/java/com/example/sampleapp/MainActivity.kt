package com.example.sampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import io.verloop.sdk.LiveChatButtonClickListener
import io.verloop.sdk.LiveChatUrlClickListener
import io.verloop.sdk.Verloop
import io.verloop.sdk.VerloopConfig
import io.verloop.sdk.enum.Position
import io.verloop.sdk.model.HeaderConfig
import org.json.JSONException
import org.json.JSONObject
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent != null) {
            onNewIntent(intent)
        }
        var FCM = ""
        val uid = UUID.randomUUID().toString()
//        getting FCM token
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful){
                FCM = it.result.toString()
                Log.d("FCM Token", it.result.toString())
            }
        }
//        capturing verloop button click event
        val click = findViewById<Button>(R.id.click_verloop);
        click.setOnClickListener {
            Toast.makeText(this, "Verloop Button Clicked", Toast.LENGTH_SHORT).show()
//            adding custom fields
            var customFields: ArrayList<VerloopConfig.CustomField> = ArrayList()
            customFields.add(
                VerloopConfig.CustomField(
                    "name",
                    "androidUser",
                    scope = VerloopConfig.Scope.ROOM
                )
            )
//            header config - for customizing title, bot logo color etc.
            val headerConfig = HeaderConfig.Builder()
                .brandLogo("https://verloop.io/wp-content/uploads/2020/09/512x512-Verloop-GV.png")
                .title("cloudxero.in")
                .titleColor("#FFFFFF")
                .titlePosition(Position.CENTER)
                .titleFontSize(18.0f)
                .subtitle("BOT for BOT\'s")
                .subtitleColor("#FFFFFF")
                .subtitlePosition(Position.CENTER)
                .subtitleFontSize(12.0f)
                .backgroundColor("#2E86AB")
                .build()

            val config = VerloopConfig.Builder()
                .clientId("subhadipmondal")
                .userId(uid)
                .fcmToken(FCM)
                .recipeId("")
                .userName("Android User - $uid")
                .userEmail("$uid@gmail.com")
                .userPhone(uid)
                .isStaging(false)
                .closeExistingChat(false)
                .fields(customFields)
                .overrideHeaderLayout(false)
                .headerConfig(headerConfig)
                .build()

            val verloop = Verloop(this,config)
            verloop.showChat(
            )
//            setting button click listeners
            config?.setButtonClickListener(object : LiveChatButtonClickListener {
                override fun buttonClicked(title: String?, type: String?, payload: String?) {
                    // Add the app logic for button click
                    Log.d("buttonClickLIstner",title.toString())
                    Log.d("buttonClickListner",type.toString())
                    Log.d("buttonClickListner",payload.toString())
                }
            })
//            set button url click listners
            config?.setUrlClickListener(object : LiveChatUrlClickListener {
                override fun urlClicked(url: String?) {
                    Log.d("urlClickListner", url.toString())
                }
            }, false)

        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.extras != null && intent.extras?.containsKey("verloop") == true) {
            val json = intent.extras?.getString("verloop")
            try {
                val jsonObject = JSONObject(json)
                if (jsonObject.has("client_id")) {
                    var clientId = jsonObject.getString("client_id")
                    if (clientId != null) {
                        var config = VerloopConfig.Builder().clientId(clientId).build()
                        Verloop(this, config).showChat()
                    }
                }
            } catch (e: JSONException) {
                Log.e("inside onnewIntent", e.message.toString())
            }
        }
    }



}


