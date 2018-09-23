package com.interview.kotlin.iterview.feature.characters.ui.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.widget.StaggeredGridLayoutManager
import com.interview.kotlin.iterview.core.platform.BaseActivity
import com.interview.kotlin.iterview.feature.characters.ui.fragments.CharactersFragment
import kotlinx.android.synthetic.main.fragment_characters.*

class CharactersActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, CharactersActivity::class.java)
    }

    override fun fragment() = CharactersFragment()

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        when(newConfig?.orientation){

            Configuration.ORIENTATION_PORTRAIT -> {
                list.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            }
            Configuration.ORIENTATION_LANDSCAPE ->{
                list.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            }
        }
    }

    override fun onBackPressed() {
        System.exit(0)
    }

}
