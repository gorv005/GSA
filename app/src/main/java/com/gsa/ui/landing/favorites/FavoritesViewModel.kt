package com.gsa.ui.landing.favorites

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gsa.base.AbstractViewModel
import com.gsa.base.SingleLiveEvent
import com.gsa.common.CommonBoolean
import com.gsa.interfaces.SchedulerProvider
import com.gsa.managers.PreferenceManager
import com.gsa.model.SearchEvent
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.favorites.FavoritesListResponse
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.utils.Config
import com.gsa.utils.Logger

import retrofit2.HttpException

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val pre: PreferenceManager,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val favoritesListModel = MutableLiveData<FavoritesListResponse>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val addProductFromFavoritesModel = MutableLiveData<AddToCartResponse>()
    val removeProductFromFavoritesModel = MutableLiveData<AddToCartResponse>()

    fun getFavorites(service :String, user_id: String, role_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            favoritesRepository.getFavorites(service,user_id,role_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    favoritesListModel.value = it
                    searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)

                }, {

                    try {
                        Logger.Debug(msg = it.toString())
                        val error = it as HttpException
                        val errorBody = error?.response()?.errorBody()?.run {

                            val r = string()
                            Logger.Debug(msg = r)
                            val error = r.replaceRange(0, 0, "")
                                .replaceRange(r.length, r.length, "")
                            //  val json = Gson().toJson(error)

                            favoritesListModel.value =
                                Gson().fromJson(error, FavoritesListResponse::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }

    fun addProductToFavorites(service :String, user_id: String, product_id: String) {
        launch {
            favoritesRepository.addToFavorites(service,user_id,product_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addProductFromFavoritesModel.value = it


                }, {

                    try {
                        Logger.Debug(msg = it.toString())
                        val error = it as HttpException
                        val errorBody = error?.response()?.errorBody()?.run {

                            val r = string()
                            Logger.Debug(msg = r)
                            val error = r.replaceRange(0, 0, "")
                                .replaceRange(r.length, r.length, "")
                            //  val json = Gson().toJson(error)

                            addProductFromFavoritesModel.value =
                                Gson().fromJson(error, AddToCartResponse::class.java)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }



    fun removeProductToFavorites(service :String, user_id: String, product_id: String) {
        launch {
            favoritesRepository.deleteFavorites(service,user_id,product_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    removeProductFromFavoritesModel.value = it


                }, {

                    try {
                        Logger.Debug(msg = it.toString())
                        val error = it as HttpException
                        val errorBody = error?.response()?.errorBody()?.run {

                            val r = string()
                            Logger.Debug(msg = r)
                            val error = r.replaceRange(0, 0, "")
                                .replaceRange(r.length, r.length, "")
                            //  val json = Gson().toJson(error)

                            removeProductFromFavoritesModel.value =
                                Gson().fromJson(error, AddToCartResponse::class.java)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }

   fun getUserID(): String?{
      return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_ID)
   }
    fun getRoleID(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_ROLE_ID)
    }
    fun getUserName(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_NAME)
    }
    fun getCartValue(): Int?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE)?.toInt()
    }
    fun saveCartValue(cartValue:Int?){
        return pre.savePreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE,cartValue,0)
    }
}