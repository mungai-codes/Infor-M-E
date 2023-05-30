package com.mungai.infor_m_e.notifications

import android.app.Notification
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.mungai.domain.NotificationService
import com.mungai.domain.repository.InformeRepository
import com.mungai.infor_m_e.MainActivity
import com.mungai.infor_m_e.R
import javax.inject.Inject

class NotificationServiceImpl @Inject constructor(
    private val builder: NotificationCompat.Builder,
    private val context: Context,
    private val repository: InformeRepository
) : NotificationService {

    override suspend fun createNotification(): Notification {
        val results = repository.getNotificationArticle()
        val newsArticle = results.shuffled().first()
        val articleUrl = newsArticle.url
        val intent = Intent(
            Intent.ACTION_VIEW,
            "myapp://mungai-codes.com/details?url=${articleUrl}".toUri(),
            context,
            MainActivity::class.java
        )
        Log.d("My Work Manager", "Eureka!!")
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }
        return builder
            .setContentTitle(newsArticle.title)
            .setContentText(newsArticle.description)
            .setSmallIcon(R.drawable.read_more)
            .setContentIntent(pendingIntent)
            .build()
    }
}