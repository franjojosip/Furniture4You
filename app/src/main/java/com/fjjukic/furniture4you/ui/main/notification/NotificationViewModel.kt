package com.fjjukic.furniture4you.ui.main.notification

import androidx.lifecycle.ViewModel
import com.fjjukic.furniture4you.ui.components.Message
import com.fjjukic.furniture4you.ui.mock.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import ht.ferit.fjjukic.foodlovers.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {

    private val _notifications = MutableStateFlow(MockRepository.getNotifications())
    val notifications: StateFlow<List<NotificationModel>> = _notifications

    private val _showMessage = MutableStateFlow<Message?>(null)
    val showMessage: StateFlow<Message?> = _showMessage

    fun removeNotification(index: Int) {
        _notifications.update {
            it.toMutableList().also { list ->
                list.removeAt(index)
            }
        }
        _showMessage.value = Message(
            toastResId = R.string.title_notification_deleted
        )
    }

    fun archiveNotification(index: Int) {
        _notifications.update {
            it.toMutableList().also { list ->
                list.removeAt(index)
            }
        }
        _showMessage.value = Message(
            toastResId = R.string.title_notification_archived
        )
    }

    fun onMessageShown() {
        _showMessage.value = null
    }
}