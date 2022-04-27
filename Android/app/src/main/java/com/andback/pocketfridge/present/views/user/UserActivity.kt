package com.andback.pocketfridge.present.views.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ActivityUserBinding
import com.andback.pocketfridge.present.config.BaseActivity
import com.andback.pocketfridge.present.utils.PageSet
import com.andback.pocketfridge.present.views.main.MainActivity
import com.andback.pocketfridge.present.views.user.login.LoginFragment
import com.andback.pocketfridge.present.views.user.signup.EmailAuthFragment
import com.andback.pocketfridge.present.views.user.signup.SignUpFragment
import com.andback.pocketfridge.present.views.user.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : BaseActivity<ActivityUserBinding>(R.layout.activity_user) {
    private val upViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //초기 실행화면 설정
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fl_main)

        if(currentFragment == null){
            val fragment = LoginFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fl_main, fragment)
                .commit()
        }
    }

    fun onChangeFragement(p: PageSet) {
        when (p) {
            PageSet.EMAIL_AUTH -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main, EmailAuthFragment())
                .addToBackStack(null)
                .commit()
            PageSet.SIGN_UP -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main, SignUpFragment())
                .addToBackStack(null)
                .commit()
            PageSet.LOGIN -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main, LoginFragment())
                .addToBackStack(null)
                .commit()
            PageSet.MAIN -> startActivity(Intent(this, MainActivity::class.java))
        }
    }
}