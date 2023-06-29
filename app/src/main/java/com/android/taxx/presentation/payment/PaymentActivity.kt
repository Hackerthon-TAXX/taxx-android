package com.android.taxx.presentation.payment

import android.os.Bundle
import com.android.taxx.R
import com.android.taxx.databinding.ActivityPaymentBinding
import com.android.taxx.model.payment.Card
import com.android.taxx.util.binding.BindingActivity

class PaymentActivity : BindingActivity<ActivityPaymentBinding>(R.layout.activity_payment) {
    private val cardList = arrayListOf<Card>(Card("신한카드", "ic_arm"), Card("신한카드", "ic_back"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO initAdapter()
    }

    private fun initAdapter() {
//        binding.vpPayment.adapter = PaymentAdapter(cardList)
//        binding.vpPayment.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}
