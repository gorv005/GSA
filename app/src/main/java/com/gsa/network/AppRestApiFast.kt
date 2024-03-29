package com.gsa.network


import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.cart.CartListResponse
import com.gsa.model.city_list.CityListResponse
import com.gsa.model.companyCategoryList.CompanyCategoryList
import com.gsa.model.favorites.FavoritesListResponse
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.ledger.LedgerResponse
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.notification.NotificationListResponse
import com.gsa.model.order.OrderListResponse
import com.gsa.model.points.PointsResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.model.reatilter_list.RetailterListResponse
import com.gsa.model.register.RegisterResponsePayload
import com.gsa.model.splash.VersionResponse
import com.gsa.model.stateList.StateListResponse
import com.gsa.model.user.UserResponsePayload
import com.gsa.utils.Config
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface AppRestApiFast {

        @FormUrlEncoded
        @POST(Config.Endpoints.LOGIN_API)
        fun login(
            @Field("user_name") username: String, @Field("password") password: String,
            @Field("device_type") device_type: String, @Field("gcm_id") gcm_id: String
        )
                : Single<LoginResponsePayload>


    @FormUrlEncoded
    @POST(Config.Endpoints.SIGN_UP_API)
    fun register(
        @Field("service") service: String, @Field("phone") phone: String,
        @Field("role_id") role_id: String,
        @Field("name") name: String,
        @Field("shop_name") shop_name: String,
        @Field("email") email: String, @Field("pincode") pincode: String,
        @Field("address") address: String, @Field("state_id") state_id: String,
        @Field("city_id") city_id: String, @Field("gst_no") gst_no: String,
        @Field("pancard_no") pancard_no: String, @Field("adharcard_no") adharcard_no: String,
        @Field("password") password: String
    ): Single<RegisterResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.STATE_LIST)
    fun stateList(
        @Field("service") service: String

    ): Single<StateListResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.CITY_LIST)
    fun cityList(
        @Field("service") service: String, @Field("state_id") state_id: String

        ): Single<CityListResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.COMPANIES_LIST)
    fun companyList(
        @Field("service") service: String, @Field("user_id") user_id: String
            , @Field("role_id") role_id: String
    ): Single<CompaniesListResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.CATEGORIES_LIST)
    fun categoriesList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String
    ): Single<CategoriesListResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.COMPANY_CATEGORIES_LIST)
    fun companyCategoriesList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("company_id") company_id: String
    ): Single<CompanyCategoryList>

    @FormUrlEncoded
    @POST(Config.Endpoints.FEATURE_PRODUCT_LIST)
    fun featureProductList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String
    ): Single<FeatureProductResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_LIST)
    fun productList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("company_id") company_id: String,@Field("category_id") category_id: String

    ): Single<ProductListResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_LIST)
    fun searchProduct(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("company_id") company_id: String,@Field("category_id") category_id: String
,@Field("search_key") search_key: String
    ): Single<ProductListResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.ORDER_LIST_API)
    fun orderList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String

    ): Single<OrderListResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.ORDER_LIST_API_RETAILER)
    fun orderList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String, @Field("retailer_id") retailer_id: String

    ): Single<OrderListResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_LIST)
    fun productList1(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("company_id") company_id: String

    ): Single<ProductListResponse>
    @FormUrlEncoded
    @POST(Config.Endpoints.ADD_PRODUCT_API)
    fun addToCart(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("item_id") item_id: String,
        @Field("item_qty") item_qty: String,@Field("item_amount") item_amount: String

    ): Single<AddToCartResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.ADD_PRODUCT_API_RETAILER)
    fun addToCart(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("item_id") item_id: String,
        @Field("item_qty") item_qty: String,@Field("item_amount") item_amount: String,
        @Field("retailer_id") retailer_id: String

    ): Single<AddToCartResponse>
    @FormUrlEncoded
    @POST(Config.Endpoints.CART_LIST)
    fun cartList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String

    ): Single<CartListResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.CART_LIST_RETAILER)
    fun cartList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("retailer_id") retailer_id: String

    ): Single<CartListResponse>
    @FormUrlEncoded
    @POST(Config.Endpoints.PLACE_ORDER_API)
    fun orderPlace(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String    ): Single<AddToCartResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PLACE_ORDER_API_RETAILER)
    fun orderPlace(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("retailer_id") retailer_id: String    ): Single<AddToCartResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.POINTS_API)
    fun getPoints(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String ): Single<PointsResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.REDEEM_POINT_LIST_API)
    fun redeemPointList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String,@Field("redeem_point") redeem_point: String ): Single<AddToCartResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.GET_USER_API)
    fun getUser(
        @Field("service") service: String, @Field("user_id") user_id: String


    ): Single<UserResponsePayload>


    @FormUrlEncoded
    @POST(Config.Endpoints.VERSION_CHECK_API)
    fun getVersion(
        @Field("service") service: String
    ): Single<VersionResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.CHANGE_PASSWORD_API)
    fun changePassword(
        @Field("user_id") user_id: String, @Field("old_password") old_password: String
,@Field("new_password") new_password: String

    ): Single<AddToCartResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.UPDATE_USER_API)
    fun updateUser(
        @Field("service") service: String, @Field("user_id") user_id: String,
        @Field("role_id")
        role_id: String,
        @Field("name")
        name: String,
        @Field("shop_name")
        shopName: String,
        @Field("email")
        email: String,
        @Field("pincode")
        pincode: String,
        @Field("address")
        address: String,
        @Field("state_id")
        stateId: String,
        @Field("city_id")
        cityId: String,
        @Field("gst_no")
        gstNo: String,
        @Field("pancard_no")
        pancard_no: String,
        @Field("adharcard_no")
        aadharCard_no: String
    ): Single<AddToCartResponse>



    @FormUrlEncoded
    @POST(Config.Endpoints.LEDGER_API)
    fun getLedgerReport(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String

    ): Single<LedgerResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.NOTIFICATION_API)
    fun getNotificationList(
        @Field("service") service: String, @Field("user_id") user_id: String
        , @Field("role_id") role_id: String

    ): Single<NotificationListResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.GET_FAVORITES_LIST_API)
    fun getFavoritesList(
        @Field("service") service: String, @Field("user_id") user_id: String


    ): Single<FavoritesListResponse>
    @FormUrlEncoded
    @POST(Config.Endpoints.ADD_FAVORITES_API)
    fun addFavorites(
        @Field("service") service: String, @Field("user_id") user_id: String,@Field("product_id") product_id: String


    ): Single<AddToCartResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.DELETE_FAVORITES_API)
    fun removeFavorites(
        @Field("service") service: String, @Field("user_id") user_id: String,
        @Field("product_id") product_id: String

    ): Single<AddToCartResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.RETAILTER_LIST_API)
    fun getRetailterList(
        @Field("service") service: String

    ): Single<RetailterListResponse>

/*
    @POST(Config.Endpoints.LOGIN_API)
    fun login(@Body data: LoginRequestPayload)
            : Single<LoginResponse>

    // reset password

    @POST(Config.Endpoints.RESET_PASSWORD_API)
    fun resetPassword(@Body data: ResetPasswordRequestPayload)
            : Single<ResetPasswordResponsePayload>
    */
/*Authentication*//*


    */
/* Register *//*

    @POST(Config.Endpoints.SIGN_UP_API)
    fun signUp(@Body data: SignUpRequestPayload)
            : Single<SignUpResponse>

    */
/*   @POST(Config.Endpoints.LOGIN_API)
       fun signUp(
           @Body data: LoginRequestPayload
       ): Observable<Response<AuthenticationResponse>>

       @POST(Config.Endpoints.LOGIN_API)
       fun getProducts(
           @Header("Authorization")  token :String,
           @Query("uuid")  uuid: String?,
           @Query("with_subcategory")  with_subcategory:String
       ): Observable<Response<ProductsWithCategoryResponse>>
   *//*


    //fetch user
    @GET(Config.Endpoints.FETCH_USER_API)
    fun fetchUser(@Header("Authorization") token: String)
            : Single<UserDetailResponse>

    //SearchProduct
    @GET(Config.Endpoints.SEARCH_PRODUCT_API)
    fun searchProduct(@Header("Authorization") token: String,
                      @Query("search")search: String)
            : Single<ProductResponsePayload>

    //delete user
    @GET(Config.Endpoints.DELETE_USER_API)
    fun deleteUser(@Header("Authorization") token: String)
            : Single<UserDetailResponse>

    //change password
    @PATCH(Config.Endpoints.CHANGE_PASSWORD_API)
    fun updatePassword(@Header("Authorization") token: String, @Body data: UpdatePasswordRequestPayload)
            : Single<UserDetailResponse>


    //categories
    @GET(Config.Endpoints.CATEGORIES_API)
    fun getCategories(@Header("Authorization") token: String)
            : Single<CategoriesResponsePayload>

    //sub categories
*/
/*    @GET(Config.Endpoints.SUB_CATEGORIES_LIST_API)
    fun getSubCategories(
        @Query("category_id") cat_id: Int?,
        @Query("trade_offer_id") offer_id: Int?
    ): Single<SubcategoriesResponsePayload>*//*


    @GET(Config.Endpoints.SUB_CATEGORIES_LIST_API)
    fun getSubCategories(
        @Query("category_id") cat_id: Int?
    ): Single<SubcategoriesResponsePayload>


    //brandList

    @GET(Config.Endpoints.BRAND_LIST_API)
    fun getBrandList(
        @Header("Authorization" ) token: String
    ): Single<BrandListResponse>

    //trade_offers
    @GET(Config.Endpoints.TRADE_OFFER_LIST_API)
    fun getTradeOffers(@Header("Authorization" ) token: String)
            : Single<TradeOfferResponsePayload>

    //Products List
    @GET(Config.Endpoints.PRODUCT_LIST_API)
    fun getProducts(@Header("Authorization" ) token: String,@Query("subcategory_id") subcat_id: Int)
            : Single<ProductResponsePayload>

    @GET(Config.Endpoints.PRODUCT_LIST_API)
    fun getProductsFilter(@Header("Authorization" ) token: String,
                          @Query("subcategory_id") subcat_id: Int,
                          @Query("category_id")category_id: List<Int?>,
                          @Query("brand_id")brand_id: List<Int?>,
                          @Query("min_price")min_price: Int,
                          @Query("max_price")max_price: Int,
                          @Query("sort_by")sort_by: String,
                          @Query("column")column: String,
                          @Query("per")per: Int,
                          @Query("page")page: Int)
            : Single<ProductResponsePayload>

    //Banner PlaceOrderData
    @GET(Config.Endpoints.BANNER_DATA_API)
    fun getBanners()
            : Single<BannerResponsePayload>

    @GET(Config.Endpoints.DIVISION_DATA_API)
    fun getDivisions(@Header("Authorization" ) token: String)
            : Single<DivisionListingResponse>


    @PATCH(Config.Endpoints.UPDATE_USER_PROFILE)
    fun updateDivisions(@Header("Authorization" ) token: String,@Body data: DivisionUpdateRequestPayload)
            : Single<UserDetailResponse>


    @PATCH(Config.Endpoints.ADD_PRODUCT_API)
    fun addProduct(@Header("Authorization" ) token: String,@Body data: AddProductRequestPayload)
            : Single<AddProductResponsePayload>

    @PATCH(Config.Endpoints.ADD_PRODUCT_API)
    fun updateProduct(@Header("Authorization" ) token: String,@Body data: UpdateProductRequestPayload)
            : Single<AddProductResponsePayload>


    @GET(Config.Endpoints.CART_LIST)
    fun getCartList(@Header("Authorization" ) token: String)
            : Single<CartListResponsePayload>



    @GET(Config.Endpoints.COUPON_LIST)
    fun getCouponList(@Header("Authorization" ) token: String)
            : Single<CouponsListResponsePayload>

    @GET(Config.Endpoints.APPLY_COUPON)
    fun applyCoupon(@Header("Authorization" ) token: String,
                    @Query("promo_no") promo_no: String?,
                    @Query("order_id") order_id: Int)
            : Single<ApplyCouponResponsePayload>

    @PATCH(Config.Endpoints.PLACE_ORDER_API)
    fun placeOrder(@Header("Authorization" ) token: String,
                   @Body data: OrderRequestPayload
    )
            : Single<OrderPlaceResponsePayload>


    @PATCH(Config.Endpoints.UPDATE_ADDRESS_API)
    fun updateAddress(@Header("Authorization" ) token: String,
                   @Body data: AddAddressRequestPayload
    )
            : Single<AddressResponsePayload>

    @GET(Config.Endpoints.SHIPPING_CHARGE_API)
    fun getShippingData(@Header("Authorization" ) token: String)
            : Single<ShippingResponsePayload>

    @PATCH(Config.Endpoints.CANCEL_ORDER_API)
    fun cancelOrder(@Header("Authorization" ) token: String,
                    @Query("order_id") order_id: Int)
            : Single<OrderCancelResponsePayLoad>

    @GET(Config.Endpoints.ORDER_DETAILS_API)
    fun orderDetails(@Header("Authorization" ) token: String,
                    @Path("id") order_id: Int)
            : Single<OrderDetailsResponse>

    @GET(Config.Endpoints.ORDER_LIST_API)
    fun orderList(@Header("Authorization" ) token: String)
            : Single<OrderListResponsePayload>

    @Multipart
    @PATCH(Config.Endpoints.UPDATE_USER_PROFILE)
    fun updateUserProfile(
        @Header("Authorization") token: String
        , @Part("user[first_name]") first_name: RequestBody
        , @Part("user[last_name]") last_name: RequestBody
        , @Part file: MultipartBody.Part?
    ): Single<UserDetailResponse>
*/

}

