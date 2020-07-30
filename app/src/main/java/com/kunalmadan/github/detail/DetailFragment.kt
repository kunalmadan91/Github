package com.kunalmadan.github.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.kunalmadan.github.R
import com.kunalmadan.github.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = DetailFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val userName = DetailFragmentArgs.fromBundle(arguments!!).userName
        val viewModelFactory = DetailViewModelFactory(userName, application)
        val detailFragmentViewModel = ViewModelProviders.of(
            this, viewModelFactory).get(DetailViewModel::class.java)

        binding.viewModel = detailFragmentViewModel

        binding.setLifecycleOwner(this)

        val adapter = RepositoryAdapter()
        binding.repositoryList.adapter = adapter

        binding.repositoryList.itemAnimator = DefaultItemAnimator()
        detailFragmentViewModel.repositoryInfo.observe(viewLifecycleOwner, Observer {
            it.let {
                if(it.isEmpty()){
                    binding.emptyRepositories.visibility = View.VISIBLE
                } else {
                    adapter.data = it
                }
            }
        })

        detailFragmentViewModel.status.observe(viewLifecycleOwner, Observer {
                status->
            when (status){
                RepositoryApiStatus.ERROR ->{
                    binding.loader.visibility = View.GONE
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "Error Loading Repositories",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                RepositoryApiStatus.LOADING ->
                    binding.loader.visibility = View.VISIBLE
                RepositoryApiStatus.DONE -> {
                    binding.totalCount.visibility = View.VISIBLE
                    binding.loader.visibility = View.GONE
                }
            }
        })

        return binding.root
    }

}