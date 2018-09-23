/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.interview.kotlin.iterview.core.navigation

import android.content.Context
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import com.interview.kotlin.iterview.feature.characters.ui.activities.CharacterDetailActivity
import com.interview.kotlin.iterview.feature.characters.ui.activities.CharactersActivity
import com.interview.kotlin.iterview.feature.characters.ui.model.CharactersView
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showCharacters(context)
    }

    private fun showCharacters(context: Context) =
            context.startActivity(CharactersActivity.callingIntent(context))

    fun showCharacterDetails(activity: FragmentActivity,
                             character: CharactersView.DataBean.ResultsBean?) =
        activity.startActivity(CharacterDetailActivity.callingIntent(activity, character))
}


