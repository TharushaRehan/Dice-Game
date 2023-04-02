package com.example.dicegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog

class NewGame : AppCompatActivity() {

    private lateinit var pDice1 : ImageView
    private lateinit var pDice2 : ImageView
    private lateinit var pDice3 : ImageView
    private lateinit var pDice4 : ImageView
    private lateinit var pDice5 : ImageView
    private lateinit var cDice1 : ImageView
    private lateinit var cDice2 : ImageView
    private lateinit var cDice3 : ImageView
    private lateinit var cDice4 : ImageView
    private lateinit var cDice5 : ImageView
    private lateinit var wins : TextView
    private lateinit var pScore : TextView
    private lateinit var cScore : TextView
    private var pDice1Num : Int = 0
    private var pDice2Num : Int = 0
    private var pDice3Num : Int = 0
    private var pDice4Num : Int = 0
    private var pDice5Num : Int = 0
    private var cDice1Num : Int = 0
    private var cDice2Num : Int = 0
    private var cDice3Num : Int = 0
    private var cDice4Num : Int = 0
    private var cDice5Num : Int = 0
    private var totalAttempts : Int = 0
    private var scoreClickTime : Int =0
    private var playerAttemptScore : Int = 0
    private var computerAttemptScore : Int = 0
    private var playerTotScore : Int = 0
    private var computerTotScore : Int = 0
    private var targetScore : Int = 101
    private var playerRemainingRolls : Int = 0
    private var computerRemainingRolls : Int = 0
    private var doingReroll : Int = 0
    private var pDice1Selected : Int = 0
    private var pDice2Selected : Int = 0
    private var pDice3Selected : Int = 0
    private var pDice4Selected : Int = 0
    private var pDice5Selected : Int = 0
    private var computerWins : Int = 0
    private var playerWins : Int = 0
    private var gameOver : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val throwDice = findViewById<Button>(R.id.throwDice)
        throwDice.setOnClickListener { throwDice() }

        val score = findViewById<Button>(R.id.score)
        score.setOnClickListener { showScore() }

        val target = findViewById<Button>(R.id.target)
        target.setOnClickListener { setTarget() }

        pDice1 = findViewById(R.id.playerDice1)
        pDice2 = findViewById(R.id.playerDice2)
        pDice3 = findViewById(R.id.playerDice3)
        pDice4 = findViewById(R.id.playerDice4)
        pDice5 = findViewById(R.id.playerDice5)
        cDice1 = findViewById(R.id.computerDice1)
        cDice2 = findViewById(R.id.computerDice2)
        cDice3 = findViewById(R.id.computerDice3)
        cDice4 = findViewById(R.id.computerDice4)
        cDice5 = findViewById(R.id.computerDice5)
        wins = findViewById(R.id.showWins)
        pScore = findViewById(R.id.pScore)
        cScore = findViewById(R.id.cScore)

        // get the received number of wins and loses values from main activity, update the variables and display them
        val intent = intent
        val pWins = intent.getIntExtra("pWins",0)
        val cWins = intent.getIntExtra("cWins",0)
        playerWins = pWins
        computerWins = cWins
        wins.setText("H: "+playerWins+" / "+"C: "+computerWins)

        gameOver = 0

        /* after a throw, if user wants to take a re-roll, user can click any of the dices that wants to keep for the re-roll
             if user click on the dice, the variable that is used to identify whether that dice is selected will be updated and
             it will update the variable that is used to identify whether user doing a re-roll or not accordingly. */
        // give onClick listener to every player dice
        pDice1.setOnClickListener {
            if (pDice1Selected == 0) {
                val toast = Toast.makeText(applicationContext, "Dice 1 Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice1Selected = 1
                doingReroll = 1
            } else {
                val toast = Toast.makeText(applicationContext, "Dice 1 Not Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice1Selected = 0
                doingReroll = 0
            }
        }
        pDice2.setOnClickListener {
            if (pDice2Selected == 0) {
                val toast = Toast.makeText(applicationContext, "Dice 2 Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice2Selected = 1
                doingReroll = 1
            } else {
                val toast = Toast.makeText(applicationContext, "Dice 2 Not Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice2Selected = 0
                doingReroll = 0
            }
        }
        pDice3.setOnClickListener {
            if (pDice3Selected == 0) {
                val toast = Toast.makeText(applicationContext, "Dice 3 Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice3Selected = 1
                doingReroll = 1
            } else {
                val toast = Toast.makeText(applicationContext, "Dice 3 Not Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice3Selected = 0
                doingReroll = 0
            }
        }
        pDice4.setOnClickListener {
            if (pDice4Selected == 0) {
                val toast = Toast.makeText(applicationContext, "Dice 4 Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice4Selected = 1
                doingReroll = 1
            } else {
                val toast = Toast.makeText(applicationContext, "Dice 4 Not Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice4Selected = 0
                doingReroll = 0
            }
        }
        pDice5.setOnClickListener {
            if (pDice5Selected == 0) {
                val toast = Toast.makeText(applicationContext, "Dice 5 Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice5Selected = 1
                doingReroll = 1
            } else {
                val toast = Toast.makeText(applicationContext, "Dice 5 Not Selected.", Toast.LENGTH_SHORT)
                toast.show()
                pDice5Selected = 0
                doingReroll = 0
            }
        }

    }


    private fun throwDice(){
        /*check whether the game is over or not, because after find out the result (win or lose), the app should not do anything if the user
        click the throw button again and again */
        if (gameOver==0) {
            // check whether user is doing a re-roll or not, if its not a re-roll, continue
            if (doingReroll == 0) {
                /* check number of total attempts and score button click times are equal, because after user click on the throw button,
                user should either score the current attempt or take the optional re-rolls */
                if (totalAttempts == scoreClickTime) {
                    // when starting a new throw give 2 optional re-rolls for both player and computer
                    // update the total attempts by 1
                    playerRemainingRolls = 2
                    computerRemainingRolls = 2
                    totalAttempts += 1

                    // get random numbers for each dice of the player
                    pDice1Num = (1..6).random()
                    pDice2Num = (1..6).random()
                    pDice3Num = (1..6).random()
                    pDice4Num = (1..6).random()
                    pDice5Num = (1..6).random()

                    showDice(pDice1Num, pDice1)
                    showDice(pDice2Num, pDice2)
                    showDice(pDice3Num, pDice3)
                    showDice(pDice4Num, pDice4)
                    showDice(pDice5Num, pDice5)

                    // get random numbers for each dice of the computer
                    cDice1Num = (1..6).random()
                    cDice2Num = (1..6).random()
                    cDice3Num = (1..6).random()
                    cDice4Num = (1..6).random()
                    cDice5Num = (1..6).random()

                    showDice(cDice1Num, cDice1)
                    showDice(cDice2Num, cDice2)
                    showDice(cDice3Num, cDice3)
                    showDice(cDice4Num, cDice4)
                    showDice(cDice5Num, cDice5)

                    // after user throw the dices for the first time display some tips for the user, this message will only display once
                    if (totalAttempts == 1) {
                        displayTipsMessage()
                    }

                    /* Update the total score for the both player and computer */
                    playerAttemptScore = pDice1Num + pDice2Num + pDice3Num + pDice4Num + pDice5Num
                    playerTotScore += playerAttemptScore

                    computerAttemptScore = cDice1Num + cDice2Num + cDice3Num + cDice4Num + cDice5Num
                    computerTotScore += computerAttemptScore

                } else {
                    // if user click throw button again without taking either the optional re-rolls or scoring the current attempt,
                    // this message will be displayed
                    val toast = Toast.makeText(applicationContext, "Take available re-rolls or click Score button to score the attempt.",
                        Toast.LENGTH_LONG)
                    toast.show()
                }
            } else {
                // if user doing a re-roll
                /* First check the remaining number of re-rolls for the player,
                    then check user has selected any of the dices, if selected, that variables values should be 1 (those dices will not change)
                    if the variables values is equal to 0 those dices will change based on the new random number */

                if (playerRemainingRolls > 0) {

                    if (pDice1Selected == 0) {
                        pDice1Num = (1..6).random()
                    }
                    if (pDice2Selected == 0) {
                        pDice2Num = (1..6).random()
                    }
                    if (pDice3Selected == 0) {
                        pDice3Num = (1..6).random()
                    }
                    if (pDice4Selected == 0) {
                        pDice4Num = (1..6).random()
                    }
                    if (pDice5Selected == 0) {
                        pDice5Num = (1..6).random()
                    }

                    showDice(pDice1Num, pDice1)
                    showDice(pDice2Num, pDice2)
                    showDice(pDice3Num, pDice3)
                    showDice(pDice4Num, pDice4)
                    showDice(pDice5Num, pDice5)

                    playerRemainingRolls -= 1    // reduce player er-rolls by 1

                    /* Reduce previous attempt score from total score add new score to the total score */
                    playerTotScore -= playerAttemptScore
                    playerAttemptScore = pDice1Num + pDice2Num + pDice3Num + pDice4Num + pDice5Num
                    playerTotScore += playerAttemptScore

                    /* set variable value to 0, because after the re-roll is completed, if user wants to take other re-rolls,
                       user has to select another dice */
                    doingReroll = 0

                    val toast = Toast.makeText(applicationContext, "Reroll Completed.", Toast.LENGTH_SHORT)
                    toast.show()

                    /* If user take all optional re-rolls, the score will update automatically without clicking the score button.
                        Also run the computer strategy function */
                    if (playerRemainingRolls == 0) {
                        computerStrategy()
                        showScore()
                    }

                    // after the re-roll, set the values of the variable to 0 that is used to identify whether that dice is selected or not
                    pDice1Selected = 0
                    pDice2Selected = 0
                    pDice3Selected = 0
                    pDice4Selected = 0
                    pDice5Selected = 0

                } else {
                    /* if user click a dice again after taking all optional re-rolls, this message will display and update the variable that is
                        used identify whether user doing a re-roll or not */
                    val toast = Toast.makeText(applicationContext, "You don't have any rerolls.", Toast.LENGTH_SHORT)
                    toast.show()
                    doingReroll = 0
                    // set the values of the variable to 0 that is used to identify whether that dice is selected or not
                    pDice1Selected = 0
                    pDice2Selected = 0
                    pDice3Selected = 0
                    pDice4Selected = 0
                    pDice5Selected = 0
                }
            }
        }
        // if user click throw button after the game is over, this message will display
        else{
            val toast = Toast.makeText(applicationContext, "Game Over. Go Back to start a new game.", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun setTarget(){

        val targetTxt = findViewById<TextView>(R.id.showTarget)

        if (gameOver==0) { // check the game is over or not, if its not over, continue

            if (totalAttempts == 0) {
                // check if total attempts equal to 0 (Before throwing the dices first time)
                // then only user can set the target, after user start the game by clicking throw button, user can't change the target

                // create a object of AlertDialog Builder class
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Set a Target")    // set the title
                builder.setMessage("Enter the target.(Default target is 101)")   // set the message
                builder.setCancelable(false) // if user click outside

                val input = EditText(this)
                input.inputType = InputType.TYPE_CLASS_NUMBER
                builder.setView(input)
                builder.setPositiveButton("OK") { dialog, which ->
                    val txt = input.text.toString()
                    if (txt.isEmpty()) {                  // if the user click OK without giving a target, set the target to default
                        val toast = Toast.makeText(applicationContext, "You haven't set a target.", Toast.LENGTH_LONG)
                        toast.show()
                    } else {
                        val target = Integer.parseInt(txt)        // if the user enter a target, update the target value and display it
                        targetScore = target
                        targetTxt.setText("Target : " + target)
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    targetScore=101                               // if the user click cancel button with or without giving a target,
                    dialog.cancel()  }                            // target will be set to default
                builder.show()

            } else {           // if user click target button after start the game, this message will display
                val toast = Toast.makeText(
                    applicationContext,
                    "Target should be set before starting the game.",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
        }
        else{
            val toast = Toast.makeText(        // after the game is over, if user click target button, this message will display
                applicationContext,
                "Game Over. Go Back to start a new game.",
                Toast.LENGTH_SHORT
            )
            toast.show()
            doingReroll = 0
        }

    }

    // if user click on Score button run this method
    private fun showScore(){

        if (gameOver==0) {

            if (totalAttempts!=0) {
                /* when user click on score button, if both player's and human's score is not equal to target score,
                    run the computer strategy method and display the total score for both player and computer */
                if (playerTotScore < targetScore && computerTotScore < targetScore) {

                    computerStrategy()

                    pScore.text = playerTotScore.toString()
                    cScore.text = computerTotScore.toString()

                    /* if total attempts greater than number of times user clicks on score button, update the variable*/
                    if (scoreClickTime<totalAttempts) {
                        scoreClickTime += 1
                    }

                }

                // if either player or computer reach to the target, run this code
                if (playerTotScore >= targetScore || computerTotScore >= targetScore) {

                    if (playerTotScore > computerTotScore) {  // if player's score greater than computer's score, run the showWins function
                        // create a pop up window
                        val window = PopupWindow(this)
                        val view = layoutInflater.inflate(R.layout.win,null)
                        window.contentView = view
                        val txt = view.findViewById<TextView>(R.id.txt)
                        txt.setOnClickListener{       // if user click on the pop up window, disappear the pop up window
                            window.dismiss()
                        }
                        window.showAtLocation(pDice2,Gravity.CENTER,0,0)   // centralized the pop up window

                        playerWins += 1
                        pScore.text = playerTotScore.toString()
                        cScore.text = computerTotScore.toString()
                        wins.setText("H: "+playerWins+" / "+"C: "+computerWins)    // display the number of wins by player and computer
                        gameOver = 1          // after find out a winner, game is over
                    }
                    /* if the scores are equal, again get random values for dices and update the score. After that again run this method
                        to check who is the winner */
                    else if (playerTotScore == computerTotScore) {

                        pDice1Num = (1..6).random()
                        pDice2Num = (1..6).random()
                        pDice3Num = (1..6).random()
                        pDice4Num = (1..6).random()
                        pDice5Num = (1..6).random()

                        showDice(pDice1Num, pDice1)
                        showDice(pDice2Num, pDice2)
                        showDice(pDice3Num, pDice3)
                        showDice(pDice4Num, pDice4)
                        showDice(pDice5Num, pDice5)

                        // get random numbers for each dice of the computer
                        cDice1Num = (1..6).random()
                        cDice2Num = (1..6).random()
                        cDice3Num = (1..6).random()
                        cDice4Num = (1..6).random()
                        cDice5Num = (1..6).random()

                        showDice(cDice1Num, cDice1)
                        showDice(cDice2Num, cDice2)
                        showDice(cDice3Num, cDice3)
                        showDice(cDice4Num, cDice4)
                        showDice(cDice5Num, cDice5)

                        playerAttemptScore = pDice1Num + pDice2Num + pDice3Num + pDice4Num + pDice5Num
                        playerTotScore += playerAttemptScore

                        computerAttemptScore = cDice1Num + cDice2Num + cDice3Num + cDice4Num + cDice5Num
                        computerTotScore += computerAttemptScore

                        pScore.text = playerTotScore.toString()
                        cScore.text = computerTotScore.toString()

                        showScore()

                    } else {
                        // create a pop up window
                        val window = PopupWindow(this)
                        val view = layoutInflater.inflate(R.layout.lose,null)
                        window.contentView = view
                        val txt = view.findViewById<TextView>(R.id.txt)
                        txt.setOnClickListener{   // if user click on the pop up window, disappear the pop up window
                            window.dismiss()
                        }
                        window.showAtLocation(pDice2,Gravity.CENTER,0,0)  // centralized the pop up window
                        computerWins += 1     // update computer wins
                        pScore.text = playerTotScore.toString()
                        cScore.text = computerTotScore.toString()
                        wins.setText("H: "+playerWins+" / "+"C: "+computerWins)    // display the number of wins by player and computer
                        gameOver = 1          // after find out a winner, game is over*/
                    }

                }
                // when user throw dice once and then click on the score button without getting optional re-rolls,
                // then set the remainingRolls to 0
                if (playerRemainingRolls != 2) {
                    playerRemainingRolls = 0

                }
            }

        }
        else{
            val toast = Toast.makeText(applicationContext, "Game Over. Go Back to start a new game.", Toast.LENGTH_SHORT)
            toast.show()
            doingReroll = 0
        }
    }

    private fun displayTipsMessage(){
        // this popup window will display after user throw dices for the firs time
        // create a object of AlertDialog Builder class
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // when the user clicks on the outside the Dialog Box then it will remain show
        builder.setTitle("Tips")   // set the title
        builder.setMessage("Each Throw (attempt) has 2 optional re-rolls. If you want to get a re-roll, click on the player dices " +
                "which you are planning to keep for the re-roll and click Throw button. Any dices that are not selected will be re-rolled." +
                "If you don't want any re-rolls click Score button to score your attempt." )  // set the message

        builder.setPositiveButton(
            "OK"                     // when the user click OK button the dialog box will close
        ) { dialog, which ->  dialog.cancel()}
        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }

    /* this is the method to display the dices on the screen
        here the method will take a number and image*/
    private fun showDice(num: Int, diceImage: ImageView){
        val dice = when (num){
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage.setImageResource(dice)
    }


    /* Computer Strategy Explanation --
        First it decides whether computer dices need to re-roll or not randomly, But if the current total score of the player is greater than.
        computer's score, then it will take the re-roll option.
        If it decides to re-roll, then decides randomly which dices to keep for the re-roll (1 - keeping, 0 - not keeping )
        If it's not going to keep a dice for the re-roll, generate a random number for that dice and check if that number is greater than
        the previous value of that dice. Then only it will change the value of that dice, otherwise the value won't change.
        The main advantage of this strategy is computer score won't reduce because of doing a re-roll */
    private fun computerStrategy() {

        // select randomly whether it would like reroll, if value is equal to 1 it will reroll
        var reroll = (0..1).random()

        if (playerTotScore>computerTotScore){  // if the player's total score is greater than computer's total score at the moment,
            reroll=1                             // computer will take the re-roll
        }

        if (reroll == 1) {

            while (computerRemainingRolls > 0) {

                // select which dices to keep for the reroll randomly, if value is equal to 1, the dice will keep for the reroll
                val keepCDice1 = (0..1).random()
                val keepCDice2 = (0..1).random()
                val keepCDice3 = (0..1).random()
                val keepCDice4 = (0..1).random()
                val keepCDice5 = (0..1).random()

                /* if the generated number equals to 0, generate another number for that dice and show it. Otherwise keep the dice as it is.
                 if the generated random number for a dice is less than the existing number, then the dice will not change. The dice will
                 change only when the generated random number is greater than the existing number. */
                if (keepCDice1 == 0) {
                    val diceNum = (1..6).random()
                    if (diceNum>cDice1Num) {
                        cDice1Num = diceNum
                    }
                }
                if (keepCDice2 == 0) {
                    val diceNum = (1..6).random()
                    if (diceNum>cDice2Num) {
                        cDice2Num = diceNum
                    }
                }
                if (keepCDice3 == 0) {
                    val diceNum = (1..6).random()
                    if (diceNum>cDice3Num) {
                        cDice3Num = diceNum
                    }
                }
                if (keepCDice4 == 0) {
                    val diceNum = (1..6).random()
                    if (diceNum>cDice4Num) {
                        cDice4Num = diceNum
                    }
                }
                if (keepCDice5 == 0) {
                    val diceNum = (1..6).random()
                    if (diceNum>cDice5Num) {
                        cDice5Num = diceNum
                    }
                }

                /* If computer takes the optional re-roll, then reduce the previous values of the dices and add new values for the total score.
                   This is because to take the current values of the dices*/
                computerTotScore -= computerAttemptScore
                computerAttemptScore = cDice1Num + cDice2Num + cDice3Num + cDice4Num + cDice5Num
                computerTotScore += computerAttemptScore

                computerRemainingRolls -= 1  // reduce computer's re-roll by 1

            }
            showDice(cDice1Num, cDice1)
            showDice(cDice2Num, cDice2)
            showDice(cDice3Num, cDice3)
            showDice(cDice4Num, cDice4)
            showDice(cDice5Num, cDice5)
        }
    }

    /* after the game is over, user can't play the game anymore. So user has to go back to start a new game
  when the user click on the back button it will start the main activity and pass the number of wins by player and computer to that activity */
    override fun onBackPressed() {
        super.onBackPressed()
        val act = Intent(this,MainActivity::class.java)
        act.putExtra("pWins",playerWins)
        act.putExtra("cWins",computerWins)
        startActivity(act)
    }

    // using onSaveInstanceState to save the state of the game
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("pScore",playerTotScore)
        outState.putInt("cScore",computerTotScore)
        outState.putInt("pDice1",pDice1Num)
        outState.putInt("pDice2",pDice2Num)
        outState.putInt("pDice3",pDice3Num)
        outState.putInt("pDice4",pDice4Num)
        outState.putInt("pDice5",pDice5Num)
        outState.putInt("cDice1",cDice1Num)
        outState.putInt("cDice2",cDice2Num)
        outState.putInt("cDice3",cDice3Num)
        outState.putInt("cDice4",cDice4Num)
        outState.putInt("cDice5",cDice5Num)
        outState.putInt("totAttempts",totalAttempts)
        outState.putInt("pRerolls",playerRemainingRolls)
        outState.putInt("doingReroll",doingReroll)
        outState.putInt("pAttemptScore",playerAttemptScore)
        outState.putInt("target",targetScore)
        outState.putInt("scoreBtnClick",scoreClickTime)
        outState.putInt("pWins",playerWins)
        outState.putInt("cWins",computerWins)
        outState.putInt("gameOver",gameOver)
        outState.putInt("pD1",pDice1Selected)
        outState.putInt("pD2",pDice2Selected)
        outState.putInt("pD3",pDice3Selected)
        outState.putInt("pD4",pDice4Selected)
        outState.putInt("pD5",pDice5Selected)
    }

    // using onRestoreInstanceState to restore the state of the game
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val targetTxt = findViewById<TextView>(R.id.showTarget)
        pDice1Num = savedInstanceState.getInt("pDice1")
        pDice2Num = savedInstanceState.getInt("pDice2")
        pDice3Num = savedInstanceState.getInt("pDice3")
        pDice4Num = savedInstanceState.getInt("pDice4")
        pDice5Num = savedInstanceState.getInt("pDice5")
        cDice1Num = savedInstanceState.getInt("cDice1")
        cDice2Num = savedInstanceState.getInt("cDice2")
        cDice3Num = savedInstanceState.getInt("cDice3")
        cDice4Num = savedInstanceState.getInt("cDice4")
        cDice5Num = savedInstanceState.getInt("cDice5")
        playerTotScore = savedInstanceState.getInt("pScore")
        computerTotScore = savedInstanceState.getInt("cScore")
        playerWins = savedInstanceState.getInt("pWins")
        computerWins = savedInstanceState.getInt("cWins")
        totalAttempts = savedInstanceState.getInt("totAttempts")
        playerRemainingRolls = savedInstanceState.getInt("pRerolls")
        doingReroll = savedInstanceState.getInt("doingReroll")
        playerAttemptScore = savedInstanceState.getInt("pAttemptScore")
        targetScore = savedInstanceState.getInt("target")
        scoreClickTime = savedInstanceState.getInt("scoreBtnClick")
        gameOver = savedInstanceState.getInt("gameOver")
        pDice1Selected = savedInstanceState.getInt("pD1")
        pDice2Selected = savedInstanceState.getInt("pD2")
        pDice3Selected = savedInstanceState.getInt("pD3")
        pDice4Selected = savedInstanceState.getInt("pD4")
        pDice5Selected = savedInstanceState.getInt("pD5")


        if (totalAttempts!=0) {
            showDice(pDice1Num, pDice1)
            showDice(pDice2Num, pDice2)
            showDice(pDice3Num, pDice3)
            showDice(pDice4Num, pDice4)
            showDice(pDice5Num, pDice5)
            showDice(cDice1Num, cDice1)
            showDice(cDice2Num, cDice2)
            showDice(cDice3Num, cDice3)
            showDice(cDice4Num, cDice4)
            showDice(cDice5Num, cDice5)
        }

        // restore textviews
        wins.setText("H: "+playerWins+" / "+"C: "+computerWins)    // display the number of wins by player and computer
        if (totalAttempts==scoreClickTime){
            pScore.text = playerTotScore.toString()
            cScore.text = computerTotScore.toString()
        }
        if (gameOver==1){
            pScore.text = playerTotScore.toString()
            cScore.text = computerTotScore.toString()
        }

        targetTxt.text = "Target : "+targetScore.toString()

    }
}


/* References
    1. Pass data between two activities - https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
    2. Create a alert dialog to get target as input - https://stackoverflow.com/questions/55684053/edittext-in-alert-dialog-android
            got the code to create a editText field
    3. Save and Restore the state of the application - https://www.youtube.com/watch?v=TcTgbVudLyQ&t=2s
    4. Pop up window - https://www.youtube.com/watch?v=Zgx3C9DQyjM , https://stackoverflow.com/questions/6063667/show-a-popupwindow-centralized
            got the code to centralized the pop up window */