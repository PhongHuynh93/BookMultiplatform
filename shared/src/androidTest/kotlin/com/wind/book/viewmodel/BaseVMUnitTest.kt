package com.wind.book.viewmodel

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import com.wind.book.log

open class BaseVMUnitTest : BaseUnitTest() {

    init {
        log = Kermit(CommonLogger()).withTag("Test")
    }
}
