package com.example.dicegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var playerWins = 0
    private var computerWins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val game = findViewById<Button>(R.id.newGame)
        game.setOnClickListener { newGameClick() }

        val about = findViewById<Button>(R.id.about)
        about.setOnClickListener { aboutClick(game) }

        // get the received number of wins and loses values and update the variables
        val intent = intent
        val pWins = intent.getIntExtra("pWins",0)
        val cWins = intent.getIntExtra("cWins",0)
        playerWins = pWins
        computerWins = cWins

    }

    private fun newGameClick(){
        // if user click on new game button, start the activity and pass the number of wins and loses values to that activity
        val game = Intent (this,NewGame::class.java)
        game.putExtra("pWins",playerWins)
        game.putExtra("cWins",computerWins)
        startActivity(game)

    }

    private fun aboutClick(loc : Button){
        // create a pop up window
        val window = PopupWindow(this)
        val view = layoutInflater.inflate(R.layout.about_popup_window,null)
        window.contentView = view
        val txt = view.findViewById<TextView>(R.id.txt)
        txt.setOnClickListener{  // if user click on the pop up window, disappear the pop up window
            window.dismiss()
        }
        window.showAtLocation(loc, Gravity.CENTER,0,0)  // centralized the pop up window
    }
}