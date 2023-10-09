package com.lindahasanah.submissionawal.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lindahasanah.submissionawal.R
import com.lindahasanah.submissionawal.data.adapter.SectionsPagerAdapter
import com.lindahasanah.submissionawal.data.viewmodel.DetailViewModel
import com.lindahasanah.submissionawal.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var detailViewModel: DetailViewModel
    private var _ischeck = false
    private lateinit var userName: String // Tambahkan ini
    private var id: Int = 0 // Tambahkan ini
    private lateinit var image: String

    companion object {
        const val USERNAME = "namaUser"
        const val ID = "id"
        const val AVATAR_URL = "userImage"
        private val TAB_TITLES = intArrayOf(
            R.string.tabs_1, R.string.tabs_2
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        userName = intent.getStringExtra(USERNAME).toString()
        id = intent.getIntExtra(ID, 0)
        image = intent.getStringExtra(AVATAR_URL).toString()
        val bundle = Bundle()
        bundle.putString(USERNAME, userName)

        detailViewModel = ViewModelProvider(
            this
        )[DetailViewModel::class.java]

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.setUserDetail(userName)
        detailViewModel.userDetail.observe(this) { user ->
            user?.let {
                with(binding) {
                    tvNama.text = it.name
                    tvUsername.text = it.login ?: ""
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"

                    Glide.with(this@DetailUserActivity).load(it.avatarUrl).into(profileImage)
                }
            }
        }

        initializeFavoriteButton()

        binding.fabFavBtn.setOnClickListener {
            _ischeck = !_ischeck
            updateFavoriteButtonState()
        }

        //Tab Layout
        val sectionPagerAdapter = SectionsPagerAdapter(this, bundle)
        sectionPagerAdapter.username = userName.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun initializeFavoriteButton() {
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    _ischeck = count > 0
                    updateFavoriteButtonState()
                }
            }
        }
    }

    private fun updateFavoriteButtonState() {
        if (_ischeck) {
            detailViewModel.addToFav(userName, id, image)
            binding.fabFavBtn.setImageResource(R.drawable.favorite_fill)
        } else {
            detailViewModel.deleteFav(id)
            binding.fabFavBtn.setImageResource(R.drawable.favorite)
        }
    }

}