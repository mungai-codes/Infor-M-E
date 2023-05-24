package com.mungai.domain

import android.app.Notification

interface NotificationService {
    suspend fun createNotification() : Notification
}