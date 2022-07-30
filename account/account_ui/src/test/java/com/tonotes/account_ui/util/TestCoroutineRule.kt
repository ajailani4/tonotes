package com.tonotes.account_ui.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()
    private val testCoroutineScope = TestScope()

    override fun apply(base: Statement, description: Description) =
        object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)
                base.evaluate()
                Dispatchers.resetMain()
            }
        }

    fun runTest(block: suspend TestScope.() -> Unit) {
        testCoroutineScope.runTest {
            block()
        }
    }
}