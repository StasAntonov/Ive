package com.example.ive.repository

import com.example.ive.api.PhotoApi
import com.example.ive.dao.PhotoDao
import com.example.ive.network.ApiResponse
import com.example.ive.network.BaseRepository
import com.example.ive.network.model.PhotoData
import com.example.ive.network.model.PhotoDataList
import com.example.ive.network.model.PhotoEntity
import com.example.ive.network.model.PhotoGallery
import com.example.ive.utils.NetworkChecker
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val api:PhotoApi,
    private val photoDao: PhotoDao,
    private val networkChecker: NetworkChecker
):BaseRepository(){

    suspend fun getPhotosLatest(orderBy:String = ORDER_BY_LATEST, page: Int = 1, pageSize:Int = 10
    ): ApiResponse<List<PhotoData>> {
        return request { api.getPhoto(orderBy, page = page, pageSize = pageSize) }
    }

    suspend fun getPhotosPopular(orderBy:String = ORDER_BY_POPULAR, page: Int = 1, pageSize:Int = 10
    ): ApiResponse<List<PhotoData>> {
        return request { api.getPhoto(orderBy, page = page, pageSize = pageSize) }
    }

    suspend fun getGalleries(username: String):ApiResponse<List<PhotoGallery>>{
        return request { api.getGallery(username) }
    }

    suspend fun getSearchPhoto(query:String,
                               countItem:Int,page:Int
    ): ApiResponse<PhotoDataList> {
        return request { api.getSearchPhoto(query,countItem,page) }
    }

    private suspend fun getPhotosDao(orderBy: String): List<PhotoEntity> {
        return photoDao.getAllPhotos(orderBy)
    }

    suspend fun insertPhotoDao(photoData: List<PhotoEntity>) {
        photoDao.insertPhoto(photoData)
    }

    private suspend fun clearPhotos(){
        photoDao.clearPhotos()
    }

    companion object {
        const val ORDER_BY_LATEST = "latest"
        const val ORDER_BY_POPULAR = "popular"
    }
}