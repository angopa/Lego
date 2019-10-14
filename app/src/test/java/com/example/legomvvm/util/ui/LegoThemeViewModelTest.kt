package com.example.legomvvm.util.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.legomvvm.ui.legotheme.data.LegoThemeRepository
import com.example.legomvvm.ui.legotheme.ui.LegoThemeViewModel
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.notNull

@RunWith(JUnit4::class)
class LegoThemeViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(LegoThemeRepository::class.java)
    private val viewModel = LegoThemeViewModel(repository)

    @Test
    fun testNull() {
        assertThat(viewModel.legoThemes, notNullValue())
    }

}