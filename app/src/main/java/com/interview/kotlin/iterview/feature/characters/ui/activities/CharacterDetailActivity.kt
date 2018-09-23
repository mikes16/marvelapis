package com.interview.kotlin.iterview.feature.characters.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.interview.kotlin.iterview.core.platform.BaseActivity
import com.interview.kotlin.iterview.feature.characters.ui.fragments.CharactersDetailFragment
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView

class CharacterDetailActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM = "com.interview.INTENT_PARAM"

        fun callingIntent(context: Context, charactersView: CharactersView.DataBean.ResultsBean?): Intent {
            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA_PARAM, charactersView)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun fragment() = CharactersDetailFragment
            .forCharacter(intent.getParcelableExtra(INTENT_EXTRA_PARAM))


}