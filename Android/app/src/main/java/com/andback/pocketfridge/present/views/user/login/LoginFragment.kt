package com.andback.pocketfridge.present.views.user.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentLoginBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.andback.pocketfridge.present.views.user.UserActivity
import com.google.android.gms.common.SignInButton

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
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
        AlertDialog.Builder(requireContext())
            .setView(R.layout.fragment_sns_login)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) {
                    return@also
                }

                val btnGoogleLogin = alertDialog.findViewById<SignInButton>(R.id.btn_sns_loginF_google)
                val tvCancel = alertDialog.findViewById<TextView>(R.id.tv_sns_loginF_cancel)

                tvCancel.setOnClickListener {
                    alertDialog.dismiss()
                }
                btnGoogleLogin.apply {
                    (getChildAt(0) as TextView).text = getString(R.string.google_login)
                }.setOnClickListener {
                    // TODO : sns 로직 구현
                }
            }
    }
}