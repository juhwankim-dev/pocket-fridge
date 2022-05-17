package com.andback.pocketfridge.present.views.user.login

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.andback.pocketfridge.BuildConfig
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FcmTokenEntity
import com.andback.pocketfridge.databinding.FragmentLoginBinding
import com.andback.pocketfridge.databinding.FragmentSnsLoginBinding
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.domain.usecase.user.GetFcmTokenUseCase
import com.andback.pocketfridge.domain.usecase.user.SendFcmTokenUseCase
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.andback.pocketfridge.present.views.main.MainActivity
import com.andback.pocketfridge.present.workmanager.DailyNotiWorker
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val TAG = "LogiinFragment_debuk"
    private val viewModel: LoginViewModel by activityViewModels()

    @Inject
    lateinit var readDataStoreUseCase: ReadDataStoreUseCase
    @Inject
    lateinit var getFcmTokenUseCase: GetFcmTokenUseCase
    @Inject
    lateinit var sendFcmTokenUseCase: SendFcmTokenUseCase

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
            toastMsg.observe(viewLifecycleOwner) {
                showToastMessage(it)
            }

            isLogin.observe(viewLifecycleOwner) {
                registerNotiWorker()

                sendFcmToken().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            moveToMain()
                        },
                        {
                            // TODO: 업뎃 실패
                            Log.d(TAG, "initViewModel: ${it.javaClass.canonicalName}")
                        }
                    )
            }
        }
    }

    private fun moveToMain() {
        startActivity(Intent(activity, MainActivity::class.java))
        requireActivity().finish()
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

        binding.tvLoginFSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_emailAuthFragment)
        }

        binding.tvLoginFFindPw.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_findPWFragment)
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

    private fun registerNotiWorker() {
        runBlocking {
            DailyNotiWorker.runAt(requireContext(), readDataStoreUseCase)
        }
    }

    private fun sendFcmToken(): Single<BaseResponse<Any>> {
        return getFcmTokenUseCase().flatMap { token ->
            val androidId = Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
            sendFcmTokenUseCase(FcmTokenEntity(androidId, token)).subscribeOn(Schedulers.io())
        }
    }

    companion object Social {
        val GOOGLE = "google"
    }
}