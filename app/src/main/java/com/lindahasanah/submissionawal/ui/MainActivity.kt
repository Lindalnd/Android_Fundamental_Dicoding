package com.lindahasanah.submissionawal.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lindahasanah.submissionawal.R
import com.lindahasanah.submissionawal.data.response.ItemsItem
import com.lindahasanah.submissionawal.data.viewmodel.MainViewModel
import com.lindahasanah.submissionawal.data.viewmodel.SettingViewModel
import com.lindahasanah.submissionawal.data.viewmodel.ViewModelFactory
import com.lindahasanah.submissionawal.databinding.ActivityMainBinding
import com.lindahasanah.submissionawal.utils.SettingPreference
import com.lindahasanah.submissionawal.utils.dataStore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        mainViewModel.userGithub.observe(this) { items ->
            setListData(items)
        }


        val pref = SettingPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(
            this, ViewModelFactory(pref)
        )[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        val itemUserGithub = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemUserGithub)


        supportActionBar?.hide()
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchView.text.toString().trim()
                    mainViewModel.searchGithubUser(query)
                    searchView.hide()
                    Toast.makeText(
                        this@MainActivity, "Searching for: $query", Toast.LENGTH_SHORT
                    ).show()
                    true
                } else {
                    false
                }
            }
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                // Handle menuItem click.
                when (menuItem.itemId) {
                    R.id.favorite_menu -> {
                        val a = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(a)
                    }

                    R.id.setting -> {
                        val b = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(b)
                    }
                }
                true
            }
        }

    }


    private fun setListData(items: List<ItemsItem?>?) {
        val adapter = GithubAdapter()
        adapter.submitList(items)
        binding.rvGithub.adapter = adapter
//        binding.edReview.setText("")

        adapter.setOnItemClickCallback(object : GithubAdapter.OnItemClickCallback {
            override fun onItemClicked(user: ItemsItem) {
                showSelectedHuman(user)
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showSelectedHuman(user: ItemsItem) {
        val detail = Intent(this@MainActivity, DetailUserActivity::class.java)
        detail.putExtra("userImage", user.avatarUrl)
        detail.putExtra("namaUser", user.login)
        detail.putExtra("id", user.id)
        detail.putExtra("followers", user.followersUrl)
        detail.putExtra("following", user.followingUrl)
        startActivity(detail)
    }

}



