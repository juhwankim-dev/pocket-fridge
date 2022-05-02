package com.andback.pocketfridge.domain.usecase.category

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.domain.repository.CategoryRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    fun getMainCategory(): Single<BaseResponse<List<MainCategoryEntity>>> = categoryRepository.getMainCategory()
    fun getSubCategory(): Single<BaseResponse<List<SubCategoryEntity>>> = categoryRepository.getSubCategory()
    fun getAllCategories(): Observable<BaseResponse<List<Any>>> {
        return Observable.merge(getMainCategory().toObservable(), getSubCategory().toObservable())
    }
}