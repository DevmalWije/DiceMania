package com.example.myapplication

import android.widget.ImageButton

public class  UserObject(imageButton: ImageButton) : DiceObject(imageButton) {


    var userScoreValue = 0
    var userWinsValue = 0

    var userDiceArray: ArrayList<DiceObject> = ArrayList()
    var userDiceRolls: ArrayList<Int> = ArrayList()

    override fun throwDice() {
        for (i in userDiceArray.indices) {
            userDiceArray[i].throwDice()
            userDiceRolls.add(userDiceArray[i].rollValue)
        }
    }
}