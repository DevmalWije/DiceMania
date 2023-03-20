package com.example.myapplication

import android.widget.ImageButton

open class DiceObject(val imageButton: ImageButton) {
    var rollValue:Int=1
    var isSelected:Boolean=false

    open fun throwDice(){
        rollValue=(1..6).random()
    }
    fun setImage(i:Int){
        val drawable=when(i){
            1 -> R.drawable.die_1
            2 -> R.drawable.die_2
            3 -> R.drawable.die_3
            4 -> R.drawable.die_4
            5 -> R.drawable.die_5
            6 -> R.drawable.die_6
            else -> R.drawable.die_1
        }
        imageButton.setImageResource(drawable)
    }
    fun setSelectedBackground(){
        imageButton.setBackgroundColor(imageButton.resources.getColor(R.color.purple_200))
    }
    fun setUnselectedBackground(){
        imageButton.setBackgroundColor(imageButton.resources.getColor(R.color.Moody_light))
    }
}

