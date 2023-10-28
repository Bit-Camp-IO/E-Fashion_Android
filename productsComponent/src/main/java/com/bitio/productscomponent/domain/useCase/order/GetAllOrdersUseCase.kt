package com.bitio.productscomponent.domain.useCase.order


import com.bitio.productscomponent.domain.entities.order.Order
import com.bitio.productscomponent.domain.repository.ProductRepository

class GetAllOrdersUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Order>?> {
        return try {
            val response = repository.getAllOrders()
            if (response.data != null){
              Result.success(response.data)
            }else{
                Result.failure(response.error!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}