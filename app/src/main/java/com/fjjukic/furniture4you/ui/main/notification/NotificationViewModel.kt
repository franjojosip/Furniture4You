package com.fjjukic.furniture4you.ui.main.notification

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.R
import com.fjjukic.furniture4you.ui.common.mock.MockRepository
import com.fjjukic.furniture4you.ui.common.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        NotificationScreenState(MockRepository.getNotifications())
    )
    val state = _state.asStateFlow()

    fun removeNotification(index: Int) {
        _state.update {
            it.copy(
                notifications = it.notifications.toMutableList().also { list ->
                    list.removeAt(index)
                },
                message = Message(toastResId = R.string.title_notification_deleted)
            )
        }
    }

    fun archiveNotification(index: Int) {
        _state.update {
            it.copy(
                notifications = it.notifications.toMutableList().also { list ->
                    list.removeAt(index)
                },
                message = Message(toastResId = R.string.title_notification_archived)
            )
        }
    }

    fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }
}