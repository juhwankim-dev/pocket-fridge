package com.andback.pocketfridge.present.views.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ActivityMainBinding
import com.andback.pocketfridge.present.config.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val TAG = "MainActivity_debuk"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBottomNav()
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
                val modalBottomSheet = SelectRegiIngreBottomSheet()
                modalBottomSheet.show(supportFragmentManager, SelectRegiIngreBottomSheet.TAG)

                return@setOnItemSelectedListener false
            }
            onNavDestinationSelected(menuItem, navController)
            true
        }
    }
}