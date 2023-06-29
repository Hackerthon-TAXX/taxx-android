package com.android.taxx.presentation

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import com.android.taxx.R
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivitySplashBinding
import com.android.taxx.presentation.login.LoginActivity
import com.android.taxx.util.LoadingDialog

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private val dialog = LoadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // 스플래시 끝난뒤 LoadingDialog 띄우기
            dialog.show(supportFragmentManager, "dialog")

            if (!isNetworkConnected(this)) {
                // 네트워크 검사 끝났으면 LoadingDialog 내리기
                dialog.dismiss()

                val builder = AlertDialog.Builder(this)
                builder.setTitle("")
                    .setMessage("네트워크에 연결되지 않았습니다.")
                    .setPositiveButton(
                        "다시 시도하기",
                        DialogInterface.OnClickListener { dialog, id ->
                            // 앱 처음부터 다시시작
                            val intent = Intent(applicationContext, SplashActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    )
                builder.show()
            } else {
                // 네트워크 검사 끝났으면 LoadingDialog 내리기
                dialog.dismiss()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1500)
    }

    // 네트워크 연결되었는지 검사코드
    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
