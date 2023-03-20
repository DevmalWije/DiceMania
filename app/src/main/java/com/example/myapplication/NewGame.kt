//Describing the computer player strategy
//the computer player takes into account their current roll value and their current total score as its major decision making criteria
//For each roll, the computer player checks their rolls to determine whether to re-roll or not
//In a best case scenario, where the computer player has gotten a roll of 25 or better, it is statistically unlikely they will get a better outcome by re-rolling
//hence they will not re-roll
//in a optimal case scenario where the computer player has gotten above 18 but below 25 points, it will re-roll all their dice EXCEPT for the ones with a value of 4 our above
//this is because again, it is statistically unlike they will get a better role for those dice
//in a worst case scenario, where they have gotten a total barley above 10, they will re-roll very aggressively and re-roll all their dice.
package com.example.myapplication

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class NewGame : AppCompatActivity() {

    // declaring  non-null variables without initializing
    //declaring dice arrays
    lateinit var userDiceArray: ArrayList<DiceObject>
    lateinit var pcDiceArray: ArrayList<DiceObject>
    //initializing map of dice buttons
    lateinit var scoreButton: Button //button to display score
    lateinit var throwButton: Button //button to throw dice
    lateinit var reRollButton: Button//button to re-roll dice

    //declaring textviews
    lateinit var totalUserScore: TextView //textview to display user score
    lateinit var totalComputerScore: TextView //textview to display computer score
    lateinit var currentUserScore: TextView //textview to display current user score
    lateinit var currentComputerScore: TextView //textview to display current computer score
    lateinit var userWins: TextView //textview to display user wins
    lateinit var pcWins: TextView //textview to display computer wins
    lateinit var dialog :Dialog //dialog box to display the winner or
    lateinit var dialogBoxText:TextView
    lateinit var dialogBoxImage:ImageView

    private lateinit var sharedPreferences: SharedPreferences //shared preferences to save the state of the game

    // Set initial scores
    private var userScoreValue = 0
    private var computerScoreValue = 0
    private  var userWinsValue = 0
    private  var pcWinsValue = 0
    private var reRollCount = 0
    private var targetScore=0
    private var userAttempts=0
    private var pcAttempts=0
    private var gameTied=false
    private var outcome=false

    //using onSaveInstanceState to save the state of the game
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("userScoreValue", userScoreValue)
        outState.putInt("computerScoreValue", computerScoreValue)
        outState.putInt("userWinsValue", userWinsValue)
        outState.putInt("pcWinsValue", pcWinsValue)
        outState.putInt("reRollCount", reRollCount)
        outState.putInt("targetScore", targetScore)
        outState.putBoolean("gameTied", gameTied)
        outState.putInt("userAttempts", userAttempts)
        outState.putInt("pcAttempts", pcAttempts)

        for (i in userDiceArray.indices) {
            outState.putInt("userRollValue$i", userDiceArray[i].rollValue)
            outState.putBoolean("userIsSelected$i", userDiceArray[i].isSelected)
        }

        for (x in pcDiceArray.indices) {
            outState.putInt("pcRollValue$x", pcDiceArray[x].rollValue)
            outState.putBoolean("pcIsSelected$x", pcDiceArray[x].isSelected)
        }

        outState.putBoolean("throwButtonEnabled", throwButton.isEnabled)
        outState.putBoolean("reRollButtonEnabled", reRollButton.isEnabled)
        outState.putBoolean("scoreButtonEnabled", scoreButton.isEnabled)

        outState.putCharSequence("totalUserScore", totalUserScore.text)
        outState.putCharSequence("totalComputerScore", totalComputerScore.text)
        outState.putCharSequence("currentUserScore", currentUserScore.text)
        outState.putCharSequence("currentComputerScore", currentComputerScore.text)
        outState.putCharSequence("userWins", userWins.text)
        outState.putCharSequence("pcWins", pcWins.text)

    }


    //using onRestoreInstanceState to restore the state of the game
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        userScoreValue = savedInstanceState.getInt("userScoreValue")
        computerScoreValue = savedInstanceState.getInt("computerScoreValue")
        userWinsValue = savedInstanceState.getInt("userWinsValue")
        pcWinsValue = savedInstanceState.getInt("pcWinsValue")
        reRollCount = savedInstanceState.getInt("reRollCount")
        targetScore = savedInstanceState.getInt("targetScore")
        gameTied = savedInstanceState.getBoolean("gameTied")
        userAttempts = savedInstanceState.getInt("userAttempts")
        pcAttempts = savedInstanceState.getInt("pcAttempts")

        //initializing dice objects
        //User dice
        userDiceArray=arrayListOf(DiceObject(findViewById(R.id.user_die_1)),
            DiceObject(findViewById(R.id.user_die_2)),
            DiceObject(findViewById(R.id.user_die_3)),
            DiceObject(findViewById(R.id.user_die_4)),
            DiceObject(findViewById(R.id.user_die_5)))

        //PC dice
        pcDiceArray=arrayListOf(DiceObject(findViewById(R.id.pc_die_1)),
            DiceObject(findViewById(R.id.pc_die_2)),
            DiceObject(findViewById(R.id.pc_die_3)),
            DiceObject(findViewById(R.id.pc_die_4)),
            DiceObject(findViewById(R.id.pc_die_5)))

        //restore dice values
        for (i in userDiceArray.indices) {
            userDiceArray[i].rollValue = savedInstanceState.getInt("userRollValue$i")
            userDiceArray[i].setImage(userDiceArray[i].rollValue)
            userDiceArray[i].isSelected = savedInstanceState.getBoolean("userIsSelected$i")

            if (userDiceArray[i].isSelected) {
                userDiceArray[i].setSelectedBackground()
            } else {
                userDiceArray[i].setUnselectedBackground()
            }
        }

        //restore pc dice values
        for (x in pcDiceArray.indices) {
            pcDiceArray[x].rollValue = savedInstanceState.getInt("pcRollValue$x")
            pcDiceArray[x].setImage(pcDiceArray[x].rollValue)
            pcDiceArray[x].isSelected = savedInstanceState.getBoolean("pcIsSelected$x")

            if (pcDiceArray[x].isSelected) {
                pcDiceArray[x].setSelectedBackground()
            } else {
                pcDiceArray[x].setUnselectedBackground()
            }
        }

        //restore button states
        throwButton.isEnabled = savedInstanceState.getBoolean("throwButtonEnabled")
        reRollButton.isEnabled = savedInstanceState.getBoolean("reRollButtonEnabled")
        scoreButton.isEnabled = savedInstanceState.getBoolean("scoreButtonEnabled")

        //restore textviews
        totalUserScore.text = savedInstanceState.getCharSequence("totalUserScore")
        totalComputerScore.text = savedInstanceState.getCharSequence("totalComputerScore")
        currentUserScore.text = savedInstanceState.getCharSequence("currentUserScore")
        currentComputerScore.text = savedInstanceState.getCharSequence("currentComputerScore")
        userWins.text = savedInstanceState.getCharSequence("userWins")
        pcWins.text = savedInstanceState.getCharSequence("pcWins")
    }

    //function defining computers users strategy
    private fun pcRollStrat(
        pcDiceArray: ArrayList<DiceObject>
    ) {

        for (i in 0 until 2) {
            //Optimal scenario where pc has already gotten a majority of the points
            if (pcDiceArray.sumBy { it.rollValue } >= 25) {
                currentComputerScore.text = pcDiceArray.sumBy { it.rollValue }.toString()

            }
            //sub-optimal scenario where pc has gotten a good roll, but could get more
            else if (pcDiceArray.sumBy { it.rollValue } in 18..24) {
                for (dice in pcDiceArray) {
                    //statistically, it is most likely that to get a score of 18 or above at least a majority of the
                    //dice will have a value of 4 or above
                    if (dice.rollValue >= 4) {
                        dice.isSelected = true
                        dice.setSelectedBackground()
                    }
                }
                for (dice in pcDiceArray) {
                    if (!dice.isSelected) {
                        dice.throwDice()
                        dice.setImage(dice.rollValue)
                    }
                }
                currentComputerScore.text = pcDiceArray.sumBy { it.rollValue }.toString()
            } else if (pcDiceArray.sumBy { it.rollValue } in 11..17) {
                for (dice in pcDiceArray) {
                    //statistically, it is most likely that to get a score of 18 or above at least a majority of the
                    //dice will have a value of 4 or above
                    if (dice.rollValue >= 3) {
                        dice.isSelected = true
                        dice.setSelectedBackground()
                    }
                }
                for (dice in pcDiceArray) {
                    if (!dice.isSelected) {
                        dice.throwDice()
                        dice.setImage(dice.rollValue)
                    }
                }
                currentComputerScore.text = pcDiceArray.sumBy { it.rollValue }.toString()
            } else {
                for (dice in pcDiceArray) {
                    dice.throwDice()
                    dice.setImage(dice.rollValue)
                }
                currentComputerScore.text = pcDiceArray.sumBy { it.rollValue }.toString()
            }
        }
        Toast.makeText(this, "Computer rolled ${pcDiceArray.sumBy { it.rollValue }}", Toast.LENGTH_SHORT).show()
    }

    //function to determine the winner of the game
    private fun outcomeDecider():Boolean{
        if (userScoreValue >= targetScore || computerScoreValue >= targetScore)  {
            if (userScoreValue > computerScoreValue) {
                Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show()
                userWinsValue++
                //number of user wins
                userWins.text=userWinsValue.toString()
                //score for the score board
                totalUserScore.text="0"
                totalComputerScore.text="0"
                currentComputerScore.text="0"
                //score from current round role
                currentUserScore.text="0"
                userScoreValue=0
                computerScoreValue=0

                //dialog box
                dialogBoxText.text="You win!"
                dialogBoxText.setTextColor(Color.parseColor("#FFD700"))
                dialogBoxImage.setImageResource(R.drawable.won)
                dialog.show()

            }
            else  if (userScoreValue < computerScoreValue){
                Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show()
                pcWinsValue++
                pcWins.text=pcWinsValue.toString()
                totalUserScore.text="0"
                totalComputerScore.text="0"
                currentComputerScore.text="0"
                currentUserScore.text="0"
                userScoreValue=0
                computerScoreValue=0

                //dialog box
                dialogBoxText.text="You Lost!"
                dialogBoxText.setTextColor(Color.parseColor("#FF0000"))
                dialogBoxImage.setImageResource(R.drawable.lost)
                dialog.show()

            }else{
                Toast.makeText(this, "It's a tie!, roll till you beat the opponent!", Toast.LENGTH_SHORT).show()
                gameTied=true
                userScoreValue=0
                computerScoreValue=0
            }
        }
        return outcome
    }

    //method override for when the back button is pressed
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userWins", userWinsValue)
        intent.putExtra("pcWins", pcWinsValue)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        // Hide the status bar
        val decorView: View = window.decorView
        val uiOptions: Int = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.setSystemUiVisibility(uiOptions)

        //initializing win and score textviews
        userWins = findViewById<TextView>(R.id.human_wins)
        pcWins = findViewById<TextView>(R.id.pc_wins)

        //    defining target score for game
        val targetScoreInput = intent.getIntExtra("targetScore", 0)
        val userWinsReload = intent.getIntExtra("userWins", 0)
        val pcWinsReload = intent.getIntExtra("pcWins", 0)

        userWinsValue = userWinsReload
        pcWinsValue = pcWinsReload

        targetScore = targetScoreInput
        userWins.text = userWinsValue.toString()
        pcWins.text = pcWinsValue.toString()

        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.setCancelable(false)
        dialogBoxText = dialog.findViewById<TextView>(R.id.dialogBoxTextView)
        dialogBoxImage = dialog.findViewById<ImageView>(R.id.dialogBoxImage)
        dialog.findViewById<Button>(R.id.dialogBoxButton).setOnClickListener {
           throwButton.isEnabled=false
            dialog.dismiss()
        }
        // Set the layout parameters of the dialog box
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = lp

//        //initializing map of dice buttons
         scoreButton= findViewById<Button>(R.id.score_button)
         throwButton = findViewById<Button>(R.id.throw_button)
         reRollButton = findViewById<Button>(R.id.re_roll_button)

        //textview for user total score
         totalUserScore = findViewById<TextView>(R.id.human_score)
        //textview for user total score
         totalComputerScore = findViewById<TextView>(R.id.pc_score)

         currentUserScore=findViewById<TextView>(R.id.current_user_score)
         currentComputerScore=findViewById<TextView>(R.id.current_pc_score)

         userWins=findViewById<TextView>(R.id.human_wins)
         pcWins=findViewById<TextView>(R.id.pc_wins)

        //initializing dice objects
        //User dice
        userDiceArray=arrayListOf(DiceObject(findViewById(R.id.user_die_1)),
            DiceObject(findViewById(R.id.user_die_2)),
            DiceObject(findViewById(R.id.user_die_3)),
            DiceObject(findViewById(R.id.user_die_4)),
            DiceObject(findViewById(R.id.user_die_5)))

        //PC dice
        pcDiceArray=arrayListOf(DiceObject(findViewById(R.id.pc_die_1)),
            DiceObject(findViewById(R.id.pc_die_2)),
            DiceObject(findViewById(R.id.pc_die_3)),
            DiceObject(findViewById(R.id.pc_die_4)),
            DiceObject(findViewById(R.id.pc_die_5)))

        //disabling the image buttons
        for(dice in userDiceArray){
            dice.imageButton.isEnabled=false
        }


        fun countSelectedDice(): Int {
            var count = 0
            for (dice in userDiceArray) {
                if (dice.isSelected) {
                    count++
                }
            }
            return count
        }

        for(dice in userDiceArray){
            dice.imageButton.setOnClickListener{
                val selectedCount = countSelectedDice()
                if (selectedCount == 4 && !dice.isSelected) {
                    // Prevent selecting more dice if all dice are already selected
                    return@setOnClickListener
                }
                dice.isSelected=!dice.isSelected
                if (dice.isSelected)
                    dice.setSelectedBackground()
                else
                    dice.setUnselectedBackground()
            }
        }



        //disabling re-roll and score buttons at screen start
        reRollButton.isEnabled = false
        scoreButton.isEnabled = false

        totalUserScore.text = userScoreValue.toString()
        totalComputerScore.text = computerScoreValue.toString()

        //adding click listener to throw button
        throwButton.setOnClickListener {
            pcAttempts++
            userAttempts++

            if (!gameTied){
                //throwing user dice
                for (dice in userDiceArray){
                    dice.throwDice()
                }
                for (dice in userDiceArray){
                    dice.setImage(dice.rollValue)
                }
                //adding roll-values to current user score
               currentUserScore.text= userDiceArray.sumBy { it.rollValue }.toString()

                //throwing pc dice
                for (dice in pcDiceArray){
                    dice.throwDice()
                }
                for (dice in pcDiceArray){
                    dice.setImage(dice.rollValue)
                }
                //adding roll-values to current pc score
                currentComputerScore.text= pcDiceArray.sumBy { it.rollValue }.toString()

                //enable re-roll, score buttons nad image buttons
                for(dice in userDiceArray){
                    dice.imageButton.isEnabled=true
                }
                for(dice in pcDiceArray){
                    dice.imageButton.isEnabled=true
                }

                throwButton.isEnabled = false
                reRollButton.isEnabled = true
                scoreButton.isEnabled = true
                reRollCount++}

            else{
                //disabling re-roll button
                reRollButton.isEnabled = false
                //throwing user dice
                for (dice in userDiceArray){
                    dice.throwDice()
                }
                for (dice in userDiceArray){
                    dice.setImage(dice.rollValue)
                }
                //adding roll-values to current user score
                currentUserScore.text= userDiceArray.sumBy { it.rollValue }.toString()

                //throwing pc dice
                for (dice in pcDiceArray){
                    dice.throwDice()
                }
                for (dice in pcDiceArray){
                    dice.setImage(dice.rollValue)
                }
                //adding roll-values to current pc score
                currentComputerScore.text= pcDiceArray.sumBy { it.rollValue }.toString()
            }
        }

        //adding click listener to score button
        scoreButton.setOnClickListener {
            //pc user algorithm implementation
            if (!gameTied) {
                pcRollStrat(pcDiceArray)
            }
            // adding dice amounts to scores
            computerScoreValue += currentComputerScore.text.toString().toInt()
            totalComputerScore.text = computerScoreValue.toString()
            userScoreValue += currentUserScore.text.toString().toInt()

            //checking if game is tied or won
            outcomeDecider()

            // Update text of TextViews with new scores
            totalUserScore.text = userScoreValue.toString()
            scoreButton.isEnabled = false
            throwButton.isEnabled = true
            reRollButton.isEnabled = false
            reRollCount = 0

            for (dice in userDiceArray) {
                dice.isSelected = false
                dice.setUnselectedBackground()
            }
            for (dice in pcDiceArray) {
                dice.isSelected = false
                dice.setUnselectedBackground()
            }
            for(dice in userDiceArray){
                dice.imageButton.isEnabled=false
            }
        }

        //adding click listener to re-roll button and respective logic
        reRollButton.setOnClickListener {
            reRollCount++
            if (reRollCount<=2) {
                // Update diceRolls with random numbers if they are not selected
                for (dice in userDiceArray) {
                    if (!dice.isSelected) {
                        dice.throwDice()
                        dice.setImage(dice.rollValue)
                    }
                }
                currentUserScore.text = userDiceArray.sumBy { it.rollValue }.toString()
            }
             else {
                 pcRollStrat(pcDiceArray)
                computerScoreValue += currentComputerScore.text.toString().toInt()

                //show the value of the last re-roll with a toast
                Toast.makeText(this, "Your round is exhausted, final re-roll score is ${currentUserScore.text}", Toast.LENGTH_SHORT).show()

                outcomeDecider()

                // Update text of TextViews with new scores
                reRollButton.isEnabled = false
                userScoreValue += currentUserScore.text.toString().toInt()
                totalUserScore.text = userScoreValue.toString()
                totalComputerScore.text = computerScoreValue.toString()
                throwButton.isEnabled = true
                scoreButton.isEnabled = false
                reRollCount = 0

                //resetting the dice
                for (dice in userDiceArray) {
                    dice.isSelected = false
                    dice.setUnselectedBackground()
                }
                for (dice in pcDiceArray) {
                    dice.isSelected = false
                    dice.setUnselectedBackground()
                }
                for(dice in userDiceArray){
                    dice.imageButton.isEnabled=false
                }

            }
        }
    }
}