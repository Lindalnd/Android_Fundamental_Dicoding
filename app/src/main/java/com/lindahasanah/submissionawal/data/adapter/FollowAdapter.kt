package com.lindahasanah.submissionawal.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lindahasanah.submissionawal.data.response.FollowResponseItem
import com.lindahasanah.submissionawal.data.response.ItemsItem
import com.lindahasanah.submissionawal.databinding.ItemUserBinding
import com.lindahasanah.submissionawal.ui.GithubAdapter

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: GithubAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: GithubAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private val list = ArrayList<FollowResponseItem>()
    fun setList(users: ArrayList<FollowResponseItem>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowResponseItem) {
            binding.apply {
                Glide.with(itemView).load(user.avatarUrl)
                    .centerCrop().into(imgMhs)
                tvName.text = user.login
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(user: ItemsItem)
    }
}