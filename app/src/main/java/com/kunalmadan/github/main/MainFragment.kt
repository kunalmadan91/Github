package com.kunalmadan.github.main

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kunalmadan.github.R
import com.kunalmadan.github.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private val TAG = MainFragment::class.java.simpleName

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = MainFragmentBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel


        viewModel.status.observe(viewLifecycleOwner, Observer {
            status->
            hideSoftKeyBoard(activity!!,activity!!.findViewById(android.R.id.content) )
            when (status){
                GithubApiStatus.ERROR -> Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "error",
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                GithubApiStatus.LOADING -> Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "loader",
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                GithubApiStatus.DONE -> Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "completed",
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                GithubApiStatus.EMPTY -> Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "empty",
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
            }
        })

        binding.buttonRepositories.setOnClickListener {
            Navigation.createNavigateOnClickListener(R.id.action_showDetail)
           //view.findNavController().navigate(R.id.action_showDetail)
        }
        return binding.root
    }

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            Log.d(TAG,e.message)
        }

    }


}