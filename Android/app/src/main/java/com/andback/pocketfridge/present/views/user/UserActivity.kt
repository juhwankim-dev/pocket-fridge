package com.andback.pocketfridge.present.views.user

import android.os.Bundle
import androidx.activity.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ActivityUserBinding
import com.andback.pocketfridge.present.config.BaseActivity
import com.andback.pocketfridge.present.views.user.UserViewModel.Companion.STEP_ONE_PAGE
import com.andback.pocketfridge.present.views.user.UserViewModel.Companion.STEP_TWO_PAGE
import com.andback.pocketfridge.present.views.user.signup.StepOneFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : BaseActivity<ActivityUserBinding>(R.layout.activity_user) {
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //초기 실행화면 설정
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fl_main)

        if(currentFragment == null){
            val fragment = StepOneFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fl_main, fragment)
                .commit()
        }
    }

    fun onChangeFragement(p: Int) {
        when (p) {
            STEP_ONE_PAGE -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main, StepOneFragment())
                .addToBackStack(null)
                .commit()
            STEP_TWO_PAGE -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main, StepOneFragment())
                .addToBackStack(null)
                .commit()
        }

        var test = 1
        when(test) {
            0 -> {}
        }
    }


}