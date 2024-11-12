package com.truth_or_dare

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

class GamePage : AppCompatActivity() {
    lateinit var player1: TextView
    lateinit var player2: TextView
    lateinit var context:Context
    lateinit var players : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_page)
        val thunderImageView = findViewById<ImageView>(R.id.thunder)
        animateThunder(thunderImageView)
        greencardanimation(findViewById(R.id.green_card))
        bluecardanimation(findViewById(R.id.blue_card))
        circlecardanimation(findViewById(R.id.cardView3))

        context = this



        player1 = findViewById(R.id.player1)
        player2 = findViewById(R.id.player2)

        // Set fullscreen mode
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val intent2: Intent = intent
        players = intent2.getStringArrayListExtra("players")!!
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            val loopJob = launch {
                startLoop(player1, player2, players)

            }

        }

    }
    override fun onPause() {
        super.onPause()
        // Cancel all running coroutines
        finish()
    }


    public fun startLoop(text: TextView, text2: TextView, list: List<String>) {
        var player1 = ""
        var player2 = ""

        CoroutineScope(Dispatchers.Main).launch {
            val loopJob = launch {
                while (true) {
                    val random = Random.nextInt(list.size)
                    var random2 = Random.nextInt(list.size)

                    while (random == random2) {
                        random2 = Random.nextInt(list.size)
                    }

                    text.text = list[random]
                    player1 = list[random]
                    text2.text = list[random2]
                    player2 = list[random2]
                    delay(100)
                }
            }
            delay(3000)
            loopJob.cancel()
            delay(1000)
            showCustomPopup(context, player1, player2)
        }
    }

    private fun showCustomPopup(context: Context, play1: String, play2: String) {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.custom_popup, null)

        // Find the views in the custom layout
        val dialogTitle = dialogView.findViewById<LinearLayout>(R.id.dialog)
        val player1TextView = dialogView.findViewById<TextView>(R.id.pl1)
        val player2TextView = dialogView.findViewById<TextView>(R.id.pl2)
        player1TextView.text = play1
        player2TextView.text = play2


        // Create the AlertDialog
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)

        val dialog = builder.create()
        dialog.show()

        dialogTitle.setOnClickListener {
            dialog.dismiss()
            startLoop(this.player1, this.player2,players)
        }
    }
    private fun animateThunder(thunderImageView: ImageView) {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val animator = ObjectAnimator.ofFloat(thunderImageView, "translationY", -screenHeight, 0f)
        animator.duration = 1000 // 1 second
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }
    private fun greencardanimation(greenCard: CardView) {
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val animator = ObjectAnimator.ofFloat(greenCard, "translationX", -screenWidth, 0f)
        animator.duration = 1000 // 1 second
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    private fun bluecardanimation(blueCard: CardView) {
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val animator = ObjectAnimator.ofFloat(blueCard, "translationX", screenWidth, 0f)
        animator.duration = 1000 // 1 second
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    private fun circlecardanimation(circleCard: CardView) {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val animator = ObjectAnimator.ofFloat(circleCard, "rotationY", 0f, 360f)
        animator.duration = 1000 // 1 second
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }





}
