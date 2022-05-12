package com.andback.pocketfridge.present.views.user.login

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.BuildConfig
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentLoginBinding
import com.andback.pocketfridge.databinding.FragmentSnsLoginBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.andback.pocketfridge.present.views.user.UserActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val TAG = "LogiinFragment_debuk"
    private val viewModel: LoginViewModel by activityViewModels()

    var isValidName = false
    var isValidPw = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initEvent()
    }

    private fun initViewModel() {
        binding.vm = viewModel

        with(viewModel) {
            pageNumber.observe(viewLifecycleOwner) {
                (context as UserActivity).onChangeFragement(it)
            }

            toastMsg.observe(viewLifecycleOwner) {
                showToastMessage(it)
            }
        }
    }

    private fun initEvent() {
        binding.etLoginFEmail.addTextChangedListener { newText ->
            SignUpChecker.validateEmail(newText.toString()).apply {
                binding.tilLoginFEmail.error = resources.getString(stringId)
                isValidName = isValid
            }
        }

        binding.etLoginFPw.addTextChangedListener { newText ->
            SignUpChecker.validatePw(newText.toString()).apply {
                binding.tilLoginFPw.error = resources.getString(stringId)
                isValidPw = isValid
            }
        }

        binding.btnLoginFSocialLogin.setOnClickListener {
            showSNSLoginDialog()
        }
    }

    private fun showSNSLoginDialog() {
        val dialogBinding = FragmentSnsLoginBinding.inflate(LayoutInflater.from(requireContext()))

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) {
                    return@also
                }

                dialogBinding.tvSnsLoginFCancel.setOnClickListener {
                    alertDialog.dismiss()
                }
                dialogBinding.btnSnsLoginFGoogle.apply {
                    (getChildAt(0) as TextView).text = getString(R.string.google_login)
                }.setOnClickListener {
                    googleSignIn()
                    alertDialog.dismiss()
                }
            }
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(BuildConfig.GOOGLE_AUTH_SERVER_ID)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        googleSignInResult.launch(signInIntent)
    }

    private val googleSignInResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val accessToken = account.serverAuthCode!!
                viewModel.socialLogin(GOOGLE, accessToken)
            } catch (e: ApiException) {
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)
                showToastMessage(resources.getString(R.string.login_fail))
            }
        } else {
            showToastMessage(resources.getString(R.string.login_fail))
        }
    }

    companion object Social {
        val GOOGLE = "google"
    }
}