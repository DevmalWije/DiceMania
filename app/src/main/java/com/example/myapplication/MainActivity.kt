package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide the status bar
        val decorView: View = window.decorView
        val uiOptions: Int = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.setSystemUiVisibility(uiOptions)


        //get the values from the intent for win counts
         val userWinsValue = intent.getIntExtra("userWins", 0)
         val pcWinsValue = intent.getIntExtra("pcWins", 0)

        var userWins = userWinsValue
        var pcWins = pcWinsValue


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

        val targetScore = findViewById<EditText>(R.id.targetScore)
        val intent = Intent(this, NewGame::class.java)

        newGame.setOnClickListener{
            val targetScoreValue = targetScore.text.toString().toInt()
            intent.putExtra("targetScore", targetScoreValue)
            intent.putExtra("userWins", userWins)
            intent.putExtra("pcWins", pcWins)
            startActivity(intent)
        }
    }
}