package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.data.repository.category.CategoryRemoteDataSource
import com.andback.pocketfridge.domain.repository.CategoryRepository
import io.reactivex.Single
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource
): CategoryRepository {
    override fun getMainCategory(): Single<BaseResponse<List<MainCategoryEntity>>> = categoryRemoteDataSource.getMainCategory()
    override fun getSubCategory(): Single<BaseResponse<List<SubCategoryEntity>>> = categoryRemoteDataSource.getSubCategory()
}