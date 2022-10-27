package com.seguro.taxis.ui.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seguro.taxis.ui.base.BaseFragment
import com.seguro.taxis.R

class ServicesFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_services, container, false)
        return view
    }

    override fun init() {
    }

    override fun setUp(view: View?) {
    }

    override fun isWifi(wifi: Boolean) {

    }

    companion object {
        val IDENTIFIER = "ServicesFragment"

        fun newInstance(): ServicesFragment {
            return ServicesFragment()
        }
    }
}
