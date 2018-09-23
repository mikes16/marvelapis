package com.interview.kotlin.iterview.feature.characters.ui.exception

import com.interview.kotlin.iterview.core.exception.Failure

class CharactersException {
    class DataNotAvailable: Failure.FeatureFailure()
}