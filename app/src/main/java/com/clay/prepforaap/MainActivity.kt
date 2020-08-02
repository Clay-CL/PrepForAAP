package com.clay.prepforaap

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var prog = 0

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val myCanvasView = MyCanvasView(this)
//
//        myCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
//        myCanvasView.contentDescription = getString(R.string.canvasContentDescription)
//        setContentView(myCanvasView)

        progressText.text = "$prog%"
        myProgressBar.progress = prog

        startCount.setOnClickListener {
            resetValues()
            CoroutineScope(Dispatchers.Main).launch {
                startCount.isEnabled = false
                repeat(10) {
                    delayByOneSec()
                    progressText.text = "$prog%"
                    myProgressBar.progress = prog
                }
                animateProgressBar()
                startCount.isEnabled = true
            }
        }

        animateButton.setOnClickListener {
            animateProgressBar()
        }


    }

    private fun animateProgressBar() {
        val rotate = PropertyValuesHolder.ofFloat(View.ROTATION_Y, 0f, -180f)
        val animatorRotate = ObjectAnimator.ofPropertyValuesHolder(cardContainer, rotate)
        animatorRotate.interpolator = DecelerateInterpolator()
        animatorRotate.duration = 1000L
        animatorRotate.repeatCount = 1
        animatorRotate.repeatMode = ObjectAnimator.REVERSE


        animatorRotate.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                //
                shrinkProgContainer()
                growTick()
            }

            override fun onAnimationEnd(animation: Animator?) {
                //cardContainer.cardElevation = 4F
            }

            override fun onAnimationRepeat(animation: Animator?) {
                shrinkTick()
                growProgContainer()
            }
        })

        animatorRotate.start()
    }

    private fun shrinkTick() {
        tickContainer.scaleContainer(true, AccelerateDecelerateInterpolator(), 500L, null)
    }

    private fun growTick() {
        tickContainer.scaleContainer(false, AccelerateDecelerateInterpolator(), 500L, null)
    }

    private fun shrinkProgContainer() {

        innerContainerProgress.scaleContainer(true, DecelerateInterpolator(), 500L, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                resetValues()
            }
        })
    }

    private fun growProgContainer() {
        innerContainerProgress.scaleContainer(false, AccelerateDecelerateInterpolator(), 1000L, null)
    }


    private fun View.scaleContainer(shrink: Boolean, interpolator: Interpolator, duration: Long, listenerAdapter: AnimatorListenerAdapter?) {
        var fromScale = 0f
        var toScale = 1f
        if (shrink) {
            fromScale = 1f
            toScale = 0f
        }
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, fromScale, toScale)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, fromScale, toScale)

        val animatorScale = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        animatorScale.interpolator = interpolator
        animatorScale.duration = duration

        if (listenerAdapter != null) {
            animatorScale.addListener(listenerAdapter)
        }

        animatorScale.start()
    }


    private fun resetValues() {
        prog = 0
        progressText.text = "$prog%"
        myProgressBar.progress = prog

    }

    private suspend fun delayByOneSec() {
        if (prog <= 90) {
            delay(1000L)
            prog += 10
        }
    }

}