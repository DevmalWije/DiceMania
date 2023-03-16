package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.ContextThemeWrapper
import android.widget.Button
import androidx.appcompat.app.AlertDialog



class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        button to open the about dialog box
        val aboutButton=findViewById<Button>(R.id.about_button)
        aboutButton.setOnClickListener{
            val builder = AlertDialog.Builder(ContextThemeWrapper(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert))
            val message = SpannableString(getString(R.string.about_message))
            message.setSpan(
                TextAppearanceSpan(this, R.style.aboutAlertBox), 0, message.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

//        button to open the new game activity
        val newGame=findViewById<Button>(R.id.new_game_button)
        val intent=Intent(this,NewGame::class.java)
        newGame.setOnClickListener{
            startActivity(intent)
        }
    }
}