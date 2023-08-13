package com.example.greentrip.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.greentrip.constants.Constants
import com.example.greentrip.constants.Constants.dataStore
import com.example.greentrip.data.repository.AuthRepo
import com.example.greentrip.data.repository.DataStoreRepository
import com.example.greentrip.utils.TokenInterceptor
import com.example.greentrip.utils.WebServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepository(dataStore)
    }


    @Provides
    @Singleton
    fun provideTokenInterceptor(dataStoreRepository: DataStoreRepository): TokenInterceptor {
        return TokenInterceptor(dataStoreRepository)
    }

    @Provides
    @Singleton
    fun provideHttp(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()

    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(retrofit: Retrofit): WebServices {
        return retrofit.create(WebServices::class.java)
    }

    @Provides
    @Singleton
    fun getRepo(webServices: WebServices): AuthRepo {
        return AuthRepo(webServices)
    }

    @Provides
    @Singleton
    fun getJson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


}