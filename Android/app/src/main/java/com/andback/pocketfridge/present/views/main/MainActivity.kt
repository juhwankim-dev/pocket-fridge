package com.andback.pocketfridge.present.views.main


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.ActivityMainBinding
import com.andback.pocketfridge.present.config.BaseActivity
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

        binding.bnvMain.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.select_regi_ingre) {
                val modalBottomSheet = SelectRegiIngreBottomSheet()
                modalBottomSheet.show(supportFragmentManager, SelectRegiIngreBottomSheet.TAG)

                return@setOnItemSelectedListener false
            }
            onNavDestinationSelected(menuItem, navController)
            true
        }

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
}