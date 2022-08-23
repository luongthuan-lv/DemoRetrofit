package com.luongthuan.demoretrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.luongthuan.demoretrofit.respository.Repository

/**
 * Created by Luong Thuan on 15/08/2022.
 */
class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}