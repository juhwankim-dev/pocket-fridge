package com.andback.pocketfridge.present.views.main


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ActivityMainBinding
import com.andback.pocketfridge.present.config.BaseActivity
import com.andback.pocketfridge.present.views.main.fridge.FridgeViewModel
import com.andback.pocketfridge.present.views.user.UserActivity
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import dagger.hilt.android.AndroidEntryPoint

/**
 * onStart
 *  로그인 확인 -> isLogin 변경
 *
 *  viewModel.isLogin? showUi() : moveToLogin()
 *
 *  showUi() -> intent의 extras 있으면 알림 센터로 이동.
 *
 *      안 읽은 알림이 있는지 확인
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val TAG = "MainActivity_debuk"
    private val fridgeViewModel: FridgeViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObserver()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.checkLogin()
    }

    private fun setObserver() {
        with(mainViewModel) {
            isLogin.observe(this@MainActivity) {
                if(it == true) showUi()
                else moveToLogin()
            }
        }
    }

    private fun showUi() {
        binding.flMain.visibility = View.GONE
        setBottomNav()
        registerReceiver()
        if (intent.extras != null) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
            val navController = navHostFragment.findNavController()
            navController.navigate(R.id.action_fridgeFragment_to_notificationFragment)
            intent.replaceExtras(null)
        }
    }

    private fun registerReceiver() {
        val br = BRReceiver()
        LocalBroadcastManager.getInstance(this).registerReceiver(br, IntentFilter())
    }

    private fun moveToLogin() {
        startActivity(Intent(this, UserActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun setBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bnvMain.setupWithNavController(navController)

        customBottomNav(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.fridgeFragment,
                R.id.recipeFragment -> {
                    binding.bnvMain.visibility = View.VISIBLE
                }
                else -> binding.bnvMain.visibility = View.GONE
            }
        }
    }

    private fun customBottomNav(navController: NavController) {
        val itemView = LayoutInflater.from(this).inflate(R.layout.button_nav_item, binding.bnvMain, false)

        val menuView = binding.bnvMain.getChildAt(0) as BottomNavigationMenuView
        val midMenu = menuView.getChildAt(menuView.childCount/2) as BottomNavigationItemView
        midMenu.addView(itemView)

        binding.bnvMain.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.selectRegiIngre) {
                // 자신 소유의 냉장고가 하나라도 없으면 냉장고 관리 화면으로 이동
                if(!hasOwnFridge()) {
                    moveToFridgeManageFragment(navController)
                    showToastMessage("식재료를 등록하려면 자신 소유의 냉장고를 추가해 주세요.")
                    return@setOnItemSelectedListener false
                }

                val modalBottomSheet = SelectRegiIngreBottomSheet()
                modalBottomSheet.show(supportFragmentManager, SelectRegiIngreBottomSheet.TAG)

                return@setOnItemSelectedListener false
            }
            onNavDestinationSelected(menuItem, navController)
            true
        }
    }

    private fun hasOwnFridge(): Boolean {
        return fridgeViewModel.fridges.value != null && fridgeViewModel.fridges.value!!.any { it.isOwner }
    }

    private fun moveToFridgeManageFragment(navController: NavController) {
        navController.navigate(R.id.fridgeManageFragment)
    }

    inner class BRReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == INTENT_ACTION) {
                fridgeViewModel.newNotificationArrival()
                Log.d(TAG, "onReceive: br recieved")
            }
        }
    }

    companion object {
        const val INTENT_ACTION = "new notification"
    }
}