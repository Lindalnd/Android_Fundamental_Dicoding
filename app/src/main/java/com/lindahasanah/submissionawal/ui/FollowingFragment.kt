package com.lindahasanah.submissionawal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lindahasanah.submissionawal.R
import com.lindahasanah.submissionawal.data.adapter.FollowAdapter
import com.lindahasanah.submissionawal.data.viewmodel.FollowingViewModel
import com.lindahasanah.submissionawal.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: FollowAdapter
    private lateinit var username: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(ARG_USERNAME).toString()

        _binding = FragmentFollowingBinding.bind(view)


        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowing1.setHasFixedSize(true)
            rvFollowing1.layoutManager = LinearLayoutManager(activity)
            rvFollowing1.adapter = adapter
        }

        viewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) { following ->
            if (following != null) {
                adapter.setList(following)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar1.visibility = View.VISIBLE
        } else {
            binding.progressBar1.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_USERNAME = "namaUser"
    }
}