package com.rappi.testapp.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.rappi.testapp.BR
import com.rappi.testapp.core.viewmodel.BaseViewModel

abstract class BaseFragment<DATA_BINDING : ViewDataBinding> : Fragment() {

    protected lateinit var viewDataBinding: DATA_BINDING
    protected var isRestoredFromBackStack = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isRestoredFromBackStack = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        viewDataBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, getInternalViewModel())
            executePendingBindings()
        }

        return viewDataBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRestoredFromBackStack = true
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getInternalViewModel(): BaseViewModel
}