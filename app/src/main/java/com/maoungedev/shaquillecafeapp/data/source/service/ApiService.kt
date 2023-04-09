package com.maoungedev.shaquillecafeapp.data.source.service

import com.maoungedev.shaquillecafeapp.data.source.response.GeneralFetchResponse
import com.maoungedev.shaquillecafeapp.data.source.response.GeneralUpdateResponse
import com.maoungedev.shaquillecafeapp.data.source.response.KitchenResponse
import com.maoungedev.shaquillecafeapp.data.source.response.MenuCaffeResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("UJK_SHAQUILLE/menu_table/read_by_category.php")
    suspend fun fetchMenuByCategory(
        @Field("kategori") category: String
    ): GeneralFetchResponse<List<MenuCaffeResponse>>

    @POST("UJK_SHAQUILLE/menu_table/read_all_menu.php")
    suspend fun fetchAllMenu(): GeneralFetchResponse<List<MenuCaffeResponse>>

    @POST("UJK_SHAQUILLE/pesanan_table/select_all_pesanan.php")
    suspend fun fetchOrder(): GeneralFetchResponse<List<KitchenResponse>>

    @POST("UJK_SHAQUILLE/pesanan_table/select_pesanan.php")
    suspend fun fetchKitchen(): GeneralFetchResponse<List<KitchenResponse>>

    @FormUrlEncoded
    @POST("UJK_SHAQUILLE/pesanan_table/add_pesanan.php")
    suspend fun addOrder(
        @Field("id_menu") menuId: String,
        @Field("nomor_meja") tableNumber: String
    ): GeneralUpdateResponse

    @FormUrlEncoded
    @POST("UJK_SHAQUILLE/pesanan_table/delete_pesanan.php")
    suspend fun deleteOrderState(
        @Field("id_pesanan") orderId: String
    ): GeneralUpdateResponse


    @POST("UJK_SHAQUILLE/pesanan_table/update_pesanan.php")
    suspend fun updateOrderState(): GeneralUpdateResponse

}