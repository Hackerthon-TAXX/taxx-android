package com.android.taxx.presentation.servicestart

import android.os.Bundle
import android.view.View
import com.android.taxx.R
import com.android.taxx.config.BaseActivity
import com.android.taxx.databinding.ActivityServiceStartBinding

class ServicestartActivity :
    BaseActivity<ActivityServiceStartBinding>(ActivityServiceStartBinding::inflate) {


    inner class RoomToFrag() {

        fun updateProgress(temp: Int) {
            runOnUiThread {
                binding.progressBar.progress = temp
            }
        }

        fun updateAnnounce1(text: String) {
            runOnUiThread {
                binding.tvAnnounce.text = text
            }
        }

        fun updateAnnounce2(text: String) {
            runOnUiThread {
                binding.tvAnnounce2.text = text
            }
        }

        fun updateAnnounce3(text: String) {
            runOnUiThread {
                binding.tvAnnounce3.text = text
            }
        }

        fun updateDistance(text: String) {
            runOnUiThread {
                binding.tvDistance.text = text
            }
        }

        fun show1(temp: Boolean) {
            runOnUiThread {

                if (temp) {
                    binding.tvDistance.visibility = View.VISIBLE
                    binding.tvAnnounce3.visibility = View.VISIBLE
                } else {
                    binding.tvDistance.visibility = View.INVISIBLE
                    binding.tvAnnounce3.visibility = View.INVISIBLE
                }

            }
        }

        fun show2() {
            runOnUiThread {

                binding.tvDistance.visibility = View.INVISIBLE
                binding.tvAnnounce3.visibility = View.INVISIBLE
                binding.tvAnnounce2.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, MapFragment())
            .commitAllowingStateLoss()

    }


}