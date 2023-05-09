package com.mungai.data.repository

import com.mungai.common.Resource
import com.mungai.data.mappers.toArticle
import com.mungai.data.remote.ApiService
import com.mungai.domain.model.Article
import com.mungai.domain.repository.InformeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InformeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : InformeRepository {

    override fun searchForNews(query: String): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.newsSearch(query = query)
                emit(Resource.Success(data = response.articles.map { it.toArticle() }))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.message ?: com.mungai.data.R.string.error_message.toString()
                    )
                )
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = e.message ?: com.mungai.data.R.string.error_message.toString()
                    )
                )
            }
        }.catch { e ->
            emit(
                Resource.Error(
                    message = e.message ?: com.mungai.data.R.string.error_message.toString()
                )
            )
        }
    }
}