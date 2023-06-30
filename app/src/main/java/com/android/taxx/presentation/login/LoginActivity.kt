package com.android.taxx.presentation.login

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivityLoginBinding
import com.android.taxx.model.checkusermodel.CheckuserResponse
import com.android.taxx.model.makeusermodel.MakeuserPostData
import com.android.taxx.model.makeusermodel.MakeuserResponse
import com.android.taxx.model.postformmodel.postFormData
import com.android.taxx.presentation.login.network.CheckuserAPI
import com.android.taxx.presentation.login.network.MakeuserAPI
import com.android.taxx.presentation.main.MainActivity
import com.android.taxx.util.RetrofitInterface
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private val TAG = "debugging"
    private val LC_OK = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        KakaoSdk.init(this, "96905163f2197d62cdad7ea15601df79")
        binding.btnKakao.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun kakaoLogin() {
        // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "앱 로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            this,
                            callback = kakaoEmailCb
                        ) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.d(TAG, "앱 로그인 성공 ${token.accessToken}")
                    kakaoData()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                this,
                callback = kakaoEmailCb
            ) // 카카오 이메일 로그인
        }
    }

    // 카카오 연결 끊기
    private fun kakaoUnlink(){

        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                Log.d(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // 카카오톡 이메일 로그인 콜백
    private val kakaoEmailCb: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "이메일 로그인 실패 $error")
        } else if (token != null) {
            Log.d(TAG, "이메일 로그인 성공 ${token.accessToken}")
            kakaoData()
        }
    }

    // 로그인 유저정보 불러오기
    private fun kakaoData() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.d(TAG, "사용자 정보 요청 성공 : $user")

                val birthday = user.kakaoAccount?.birthday
                val email = user.kakaoAccount?.email
                val age = user.kakaoAccount?.ageRange.toString()
                val image = user.kakaoAccount?.profile?.profileImageUrl

                val uuid = user.id
                val name = user.kakaoAccount?.profile?.nickname

                if (uuid != null && name != null) {
                    postFormData.uuid = uuid
                    val datas = MakeuserPostData(uuid, name)
                    checkUser(datas)
                }
            }
        }
    }

    // 존재하는 유저면 페이지이동
    // 존재하지 않는 유저면 유저생성 함수 호출
    private fun checkUser(datas: MakeuserPostData?) {
        Log.d(TAG, "${datas!!.id}")
        RetrofitInterface().getInstance().create(CheckuserAPI::class.java)
            .checkUser(datas!!.id).enqueue(object : Callback<CheckuserResponse> {
                override fun onResponse(
                    call: Call<CheckuserResponse>,
                    response: Response<CheckuserResponse>
                ) {
                    Log.d(TAG, response.raw().toString())
                    if (response.body() != null) {
                        if (response.body()!!.success) {
                            Log.d(TAG, response.body().toString())
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            if (datas != null) {
                                postUserData(datas)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CheckuserResponse>, t: Throwable) {
                }
            })
    }

    // 존재하지 않는 유저면 유저생성 API.
    // 성공시 페이지 이동
    private fun postUserData(datas: MakeuserPostData) {
        Log.d(TAG, "send data $datas")
        RetrofitInterface().getInstance().create(MakeuserAPI::class.java)
            .postMakeuser(datas).enqueue(object : Callback<MakeuserResponse> {
                override fun onResponse(
                    call: Call<MakeuserResponse>,
                    response: Response<MakeuserResponse>
                ) {
                    Log.d(TAG, response.raw().toString())

                    if (response.body() != null) {
                        Log.d(TAG, response.body().toString())
                    }

                    if (response.code() == 201) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<MakeuserResponse>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                }
            })
    }

    // 위치 권한 확인
    private fun permissionCheck() {
        val preference = getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // 권한 거절 (다시 한 번 물어봄)
                val builder = AlertDialog.Builder(this)
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        LC_OK
                    )
                }
                builder.setNegativeButton("취소") { dialog, which ->
                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        LC_OK
                    )
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->
                    }
                    builder.show()
                }
            }
        }
    }

    // 권한 요청 후 행동
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LC_OK) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()
                permissionCheck()
            }
        }
    }
}
