package com.lindahasanah.submissionawal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lindahasanah.submissionawal.R
import com.lindahasanah.submissionawal.data.adapter.FollowAdapter
import com.lindahasanah.submissionawal.data.viewmodel.FollowersViewModel
import com.lindahasanah.submissionawal.data.viewmodel.FollowingViewModel
import com.lindahasanah.submissionawal.databinding.FragmentFollowersBinding
import com.lindahasanah.submissionawal.databinding.FragmentFollowingBinding

class FollowersFragment : Fragment() {

    private var _binding  : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: FollowAdapter
    private lateinit var username : String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(ARG_USERNAME).toString()

        _binding = FragmentFollowersBinding.bind(view)


        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowing1.setHasFixedSize(true)
            rvFollowing1.layoutManager = LinearLayoutManager(activity)
            rvFollowing1.adapter = adapter
        }

        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) { followers->
            if (followers != null) {
                adapter.setList(followers)
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