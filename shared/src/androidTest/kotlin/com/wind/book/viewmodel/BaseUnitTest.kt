package com.wind.book.viewmodel

import org.junit.Rule

open class BaseUnitTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    protected val testDispatcher = coroutineRule.testDispatcher
}
