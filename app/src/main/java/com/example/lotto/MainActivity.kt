package com.example.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.lotto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val numberTextList : List<TextView> by lazy{
        listOf<TextView>(
            binding.num1Tv,
            binding.num2tv,
            binding.num3tv,
            binding.num4tv,
            binding.num5tv,
            binding.num6tv
        )
    }

    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 45

        initAddButton()
        initClearButton()
        initRunButton()
    }

    private fun initClearButton(){
        binding.clearButton.setOnClickListener {
            didRun = false
            pickNumberSet.clear()
            numberTextList.forEach {
                it.isVisible = false
            }
        }
    }

    private fun initAddButton(){
        binding.addButton.setOnClickListener {
            var pickNum = binding.numberPicker.value
            val textView = numberTextList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = pickNum.toString()

            setNumberBackground(pickNum, textView)
            pickNumberSet.add(pickNum)
        }
    }

    private fun initRunButton(){
        binding.runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed { index, number ->
                val textView = numberTextList[index]
                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground(number, textView)
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for( i in 1..45 ){
                if(pickNumberSet.contains(i))
                    continue
                this.add(i)
            }
        }
        numberList.shuffle()
        val numList = pickNumberSet.toList() +
                numberList.subList(0, 6 - pickNumberSet.size)
        return numList.sorted()
    }

    private fun setNumberBackground(number:Int, tv :TextView){
        when(number){
            in 1..10 -> tv.background =
                ContextCompat.getDrawable(this,
                    R.drawable.circle_yellow)
            in 11..20 -> tv.background =
                ContextCompat.getDrawable(this,
                    R.drawable.circle_blue)
            in 21..30 -> tv.background =
                ContextCompat.getDrawable(this,
                    R.drawable.circle_red)
            in 31..40 -> tv.background =
                ContextCompat.getDrawable(this,
                    R.drawable.circle_gray)
            else -> tv.background =
                ContextCompat.getDrawable(this,
                    R.drawable.circle_green)
        }
    }

}