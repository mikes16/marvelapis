package com.interview.kotlin.iterview.core.platform

import android.arch.lifecycle.ViewModelProvider
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.interview.kotlin.iterview.R
import com.interview.kotlin.iterview.core.extensions.viewContainer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.toolbar.progress
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : DaggerFragment(), ConnectivityReceiver.ConnectivityReceiverListener  {

    abstract fun layoutId(): Int
    @Inject lateinit var appContext:Context
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var conectivityReceiver: BroadcastReceiver

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        conectivityReceiver = ConnectivityReceiver()
        activity?.registerReceiver(conectivityReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(conectivityReceiver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
            with(activity) { if (this is BaseActivity) this.progress.visibility = viewStatus }

    internal fun notify(@StringRes message: Int) =
            Snackbar.make(viewContainer, message, Snackbar.LENGTH_LONG).show()

    internal fun notify(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(ContextCompat.getColor(appContext, R.color.colorPrimary))
        snackBar.show()
    }
}
