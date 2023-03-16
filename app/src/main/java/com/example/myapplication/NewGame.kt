package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class NewGame : AppCompatActivity() {

    // declaring  non-null variables without initializing

    //declaring dice arrays
     lateinit var userDiceArray: ArrayList<DiceObject>
     lateinit var pcDiceArray: ArrayList<DiceObject>


//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt("userScoreValue",userScoreValue )
//        outState.putInt("computerScoreValue", computerScoreValue)
//        outState.putInt("userWinsValue", userWinsValue)
//        outState.putInt("pcWinsValue", pcWinsValue)
//        outState.putInt("targetScore", targetScore)
//        outState.putIntegerArrayList("userDiceRolls", ArrayList(userDiceRolls))
//        outState.putIntegerArrayList("pcDiceRolls", ArrayList(pcDiceRolls))
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        //    defining target score for game
        var targetScore: Int = 101
        // initializing list of dice rolls
        val userDiceRolls = mutableListOf<Int>()
        val pcDiceRolls = mutableListOf<Int>()
        //initializing map of dice buttons
        val diceMap= mutableMapOf<Int,Boolean>()

        val scoreButton: Button = findViewById<Button>(R.id.score_button)
        val throwButton: Button = findViewById<Button>(R.id.throw_button)
        val userScore = findViewById<TextView>(R.id.human_score)
        val computerScore = findViewById<TextView>(R.id.pc_score)
        val currentUserScore=findViewById<TextView>(R.id.current_user_score)
        val currentComputerScore=findViewById<TextView>(R.id.current_pc_score)
        val userWins=findViewById<TextView>(R.id.human_wins)
        val pcWins=findViewById<TextView>(R.id.pc_wins)
        val reRollButton=findViewById<Button>(R.id.re_roll_button)

        // Set initial scores
        var userScoreValue = 0
        var computerScoreValue = 0
        var userWinsValue=0
        var pcWinsValue=0
        var selectedDice: MutableList<DiceObject> = mutableListOf()
        var reRollCount = 0
        var reRollUsed = false


        //initializing map with buttons to unselected state
        diceMap[R.id.user_die_1]=false
        diceMap[R.id.user_die_2]=false
        diceMap[R.id.user_die_3]=false
        diceMap[R.id.user_die_4]=false
        diceMap[R.id.user_die_5]=false

        diceMap[R.id.pc_die_1]=false
        diceMap[R.id.pc_die_2]=false
        diceMap[R.id.pc_die_3]=false
        diceMap[R.id.pc_die_4]=false
        diceMap[R.id.pc_die_5]=false


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

//        //adding click listeners to dice buttons
//        findViewById<ImageButton>(R.id.user_die_1).setOnClickListener{
//            diceMap[R.id.user_die_1]=!diceMap[R.id.user_die_1]!!
//            if (diceMap[R.id.user_die_1]!!)
//                it.setBackgroundColor(resources.getColor(R.color.purple_200))
//
//            else
//                it.setBackgroundColor(resources.getColor(R.color.Moody_light))
//        }


        //adding click listeners to user dice buttons
        for (i in 0 until userDiceArray.size){
            userDiceArray[i].imageButton.setOnClickListener{
                diceMap[userDiceArray[i].imageButton.id]=!diceMap[userDiceArray[i].imageButton.id]!!
                if (diceMap[userDiceArray[i].imageButton.id]!!)
                    userDiceArray[i].imageButton.setBackgroundColor(resources.getColor(R.color.purple_200))

                else
                    userDiceArray[i].imageButton.setBackgroundColor(resources.getColor(R.color.Moody_light))
            }
        }

        //adding click listeners to pc dice buttons
        for (i in 0 until pcDiceArray.size){
            pcDiceArray[i].imageButton.setOnClickListener{
                diceMap[pcDiceArray[i].imageButton.id]=!diceMap[pcDiceArray[i].imageButton.id]!!
                if (diceMap[pcDiceArray[i].imageButton.id]!!)
                    pcDiceArray[i].imageButton.setBackgroundColor(resources.getColor(R.color.purple_200))

                else
                    pcDiceArray[i].imageButton.setBackgroundColor(resources.getColor(R.color.Moody_light))
            }
        }



        // Initialize diceRolls with 6 zeros
        userDiceRolls.addAll(listOf(0, 0, 0, 0, 0))
        pcDiceRolls.addAll(listOf(0, 0, 0, 0, 0))


        userScore.text = userScoreValue.toString()
        computerScore.text = computerScoreValue.toString()


        throwButton.setOnClickListener {
            //resetting userDiceRolls
            for (i in 0 until userDiceRolls.size) {
                userDiceRolls[i] = 0
            }
            currentUserScore.text = userDiceRolls.sum().toString()
            //resetting pcDiceRolls
            for (i in 0 until pcDiceRolls.size) {
                pcDiceRolls[i] = 0
            }
            currentComputerScore.text = pcDiceRolls.sum().toString()

            // Update diceRolls with random numbers
            for (i in 0 until userDiceRolls.size) {
                userDiceRolls[i] = (1..6).random()
            }

            currentUserScore.text = userDiceRolls.sum().toString()

            for (i in 0 until pcDiceRolls.size) {
                pcDiceRolls[i] = (1..6).random()
            }

            currentComputerScore.text = pcDiceRolls.sum().toString()

            // Update the dice images
            for (i in 0 until userDiceArray.size) {
                userDiceArray[i].setImage(userDiceRolls[i])
            }

            for (i in 0 until pcDiceArray.size) {
                pcDiceArray[i].setImage(pcDiceRolls[i])
            }
            reRollButton.isEnabled = true

        }


        scoreButton.setOnClickListener {

            // adding dice amounts to scores
            userScoreValue += currentUserScore.text.toString().toInt()
            computerScoreValue += currentComputerScore.text.toString().toInt()

            if (userScoreValue >= targetScore || computerScoreValue >= targetScore) {
                if (userScoreValue > computerScoreValue) {
                    Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show()
                    userWinsValue++
                    userWins.text=userWinsValue.toString()
                    userScore.text="0"
                    computerScore.text="0"
                    currentComputerScore.text="0"
                    currentUserScore.text="0"
                    userScoreValue=0
                    computerScoreValue=0
                }
                else {
                    Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show()
                    pcWinsValue++
                    pcWins.text=pcWinsValue.toString()
                    userScore.text="0"
                    computerScore.text="0"
                    currentComputerScore.text="0"
                    currentUserScore.text="0"
                    userScoreValue=0
                    computerScoreValue=0
                }

            }
                // Update text of TextViews with new scores
                userScore.text = userScoreValue.toString()
                computerScore.text = computerScoreValue.toString()

        }

        for ((buttonId, isSelected) in diceMap) {
            if (isSelected) {
                // Find the DiceObject associated with the buttonId and add it to the selectedDice list
                val diceObject = userDiceArray.find { it.imageButton.id == buttonId }
                if (diceObject != null) {
                    selectedDice.add(diceObject)
                }
            }
        }


//        reRollButton.setOnClickListener {
//            if (!reRollUsed) {
//                reRollCount++
//                if (reRollCount <= 2) {
//                    // Update diceRolls with random numbers
//                    for (i in 0 until userDiceRolls.size) {
//                        userDiceRolls[i] = (1..6).random()
//                    }
//
//                    currentUserScore.text = userDiceRolls.sum().toString()
//
//                    // Update the dice images
//                    for (i in 0 until userDiceArray.size) {
//                        userDiceArray[i].setImage(userDiceRolls[i])
//                    }
//                } else {
//                    // User has used all their re-rolls, update score and pass turn to computer
//                    userScoreValue += currentUserScore.text.toString().toInt()
//                }
//            }
//        }

        reRollButton.setOnClickListener {
            if (reRollCount == 2) {
                reRollButton.isEnabled = false
            }
            if (!reRollUsed) {
                reRollCount++
                if (reRollCount <= 2) {
                    // Update diceRolls with random numbers
                    for (i in 0 until userDiceRolls.size) {
                        userDiceRolls[i] = (1..6).random()
                    }

                    currentUserScore.text = userDiceRolls.last().toString()

                    // Update the dice images
                    for (i in 0 until userDiceArray.size) {
                        userDiceArray[i].setImage(userDiceRolls[i])
                    }
                } else {
                    // User has used all their re-rolls, update score and pass turn to computer
                    userScoreValue += currentUserScore.text.toString().toInt()
                }
            }
        }



    }
}