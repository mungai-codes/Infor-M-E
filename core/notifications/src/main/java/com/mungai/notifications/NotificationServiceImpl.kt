package com.mungai.notifications

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.mungai.domain.NotificationService
import com.mungai.domain.repository.InformeRepository
import javax.inject.Inject

class NotificationServiceImpl @Inject constructor(
    private val builder: NotificationCompat.Builder,
    private val context: Context,
    private val repository: InformeRepository
) : NotificationService {
    override suspend fun createNotification(): Notification {
        val results = repository.getNotificationArticle()
        val article = results.first()
        return builder
            .setSmallIcon(com.mungai.common.R.drawable.read_more)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle(article.title)
                    .bigText(article.description)
                    .setSummaryText(article.content)
            )
            .build()
    }
}