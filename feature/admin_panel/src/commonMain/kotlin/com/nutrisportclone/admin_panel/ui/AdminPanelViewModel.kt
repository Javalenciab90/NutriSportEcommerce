package com.nutrisportclone.admin_panel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrisportclone.data.domain.admin.AdminRepository
import com.nutrisportclone.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.emptyList

class AdminPanelViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {

    val products = adminRepository.readLastTenProducts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RequestState.Loading
        )
}