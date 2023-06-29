package com.android.taxx.presentation.mypage

import android.os.Bundle
import android.util.Log
import com.android.taxx.R
import com.android.taxx.databinding.ActivityMyPageBinding
import com.android.taxx.util.binding.BindingActivity
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient

class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    val TAG = "debugging"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateKakaoData()
    }

    private fun setCircleImage(imgUrl: String?) {
        this.let {
            Glide.with(this@MyPageActivity)
                .load(imgUrl)
                .apply {
                    if (imgUrl == null) {
                        placeholder(R.drawable.ic_my_page)
                    }
                }
                .circleCrop()
                .into(binding.ivMyPageProfileImage)
        }
    }

    // 로그인 유저정보 불러오기
    private fun updateKakaoData() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.d(TAG, "사용자 정보 요청 성공 : $user")

                val email = user.kakaoAccount?.email
                val image = user.kakaoAccount?.profile?.profileImageUrl
                val name = user.kakaoAccount?.profile?.nickname
                initUser(name, image, email)
            }
        }
    }

    private fun initUser(name: String?, image: String?, email: String?) {
        binding.tvMyPageName.text = name
        binding.tvMyPageEmail.text = email
        setCircleImage(image)
    }
}
