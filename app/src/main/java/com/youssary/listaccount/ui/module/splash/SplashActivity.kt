package com.youssary.listaccount.ui.module.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youssary.listaccount.R
import com.youssary.listaccount.core.SpringAnimationHelper
import com.youssary.listaccount.ui.module.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animation()


    }

    private fun animation() {
        val animatorListener: Animator.AnimatorListener =
            object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    startActivityMain()
                    overridePendingTransition(0, 0)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }
        val springAnimationHelper =
            SpringAnimationHelper(relative_layout, this.windowManager, animatorListener)
        springAnimationHelper.animate()
    }


    private fun startActivityMain() {
        val intent = Intent(this, MainActivity::class.java)
        if (getIntent() != null && getIntent().extras != null) {
            intent.putExtras(getIntent())
        }
        startActivity(intent)
        finish()
    }
}
