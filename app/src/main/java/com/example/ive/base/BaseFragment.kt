package com.example.ive.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.ive.ui.discover.IProgressVisibility

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var binding: T
    private val navControllerOnboard: NavController by lazy { findNavController() }

    protected lateinit var progressVisibility: IProgressVisibility
    override fun onAttach(context: Context) {
        super.onAttach(context)
        progressVisibility = context as IProgressVisibility
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = getDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    protected open fun initListeners() {}

    protected open fun initObservers() {}

    protected open fun initViews() {}

    protected abstract fun getDataBinding(): T

    protected fun hideStatusBar() {
    }

    protected fun navigate(id: Int) {
        navControllerOnboard.navigate(id)
    }

    protected fun navigate(directions: NavDirections) {
        navControllerOnboard.navigate(directions)
    }

    protected fun onBack(view: View) {
        Navigation.findNavController(view).popBackStack()
    }

}