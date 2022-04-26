package com.andback.pocketfridge.present.views.user

import android.os.Bundle
import androidx.activity.viewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ActivityUserBinding
import com.andback.pocketfridge.present.config.BaseActivity
import com.andback.pocketfridge.present.utils.PageSet
import com.andback.pocketfridge.present.views.user.signup.StepOneFragment
import com.andback.pocketfridge.present.views.user.signup.StepTwoFragment
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

    fun onChangeFragement(p: PageSet) {
        when (p) {
            PageSet.STEP_ONE -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main, StepOneFragment())
                .addToBackStack(null)
                .commit()
            PageSet.STEP_TWO -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main, StepTwoFragment())
                .addToBackStack(null)
                .commit()
            PageSet.LOGIN -> {
                // Login Activity로 이동
            }
        }
    }
}