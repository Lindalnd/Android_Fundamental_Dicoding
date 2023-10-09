package com.lindahasanah.submissionawal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lindahasanah.submissionawal.data.response.ItemsItem
import com.lindahasanah.submissionawal.data.viewmodel.FavoriteViewModel
import com.lindahasanah.submissionawal.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: GithubAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        adapter = GithubAdapter()
        adapter.notifyDataSetChanged()

        favoriteViewModel = ViewModelProvider(
            this
        )[FavoriteViewModel::class.java]

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        adapter.setOnItemClickCallback(object : GithubAdapter.OnItemClickCallback {
            override fun onItemClicked(user: ItemsItem) {
                showSelectedHuman(user)
            }
        })

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        favoriteViewModel.getFavorite()?.observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, id = it.id, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
        }
    }

    private fun showSelectedHuman(user: ItemsItem) {
        val fav = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
        fav.putExtra("userImage", user.avatarUrl)
        fav.putExtra("namaUser", user.login)
        fav.putExtra("id", user.id)
        startActivity(fav)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }

}