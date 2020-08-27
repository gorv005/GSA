package com.gsa.ui.search

import android.content.ClipData
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.gsa.base.AbstractViewModel
import com.gsa.base.SingleLiveEvent
import com.gsa.common.CommonBoolean
import com.gsa.interfaces.SchedulerProvider
import com.gsa.managers.PreferenceManager
import com.gsa.model.SearchEvent
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.utils.Config
import com.gsa.utils.Logger

import retrofit2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.ObservableSource
import androidx.lifecycle.Transformations.distinctUntilChanged
import androidx.lifecycle.Transformations.switchMap
import com.jakewharton.rxbinding2.widget.RxSearchView
import com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
import io.reactivex.Observable
import io.reactivex.Single
import org.reactivestreams.Subscriber
import java.util.concurrent.TimeUnit


class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val pre: PreferenceManager,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val searchEvent = SingleLiveEvent<SearchEvent>()
    val productModel = MutableLiveData<ProductListResponse>()



    fun getProducts(service :String, user_id: String, role_id: String
                     ,company_id: String,category_id: String,search_key: String) {
   //     searchEvent.value = SearchEvent(isLoading = true)

        launch {
            searchRepository.searchProduct(service,user_id,role_id,company_id,category_id,search_key)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productModel.value = it
                   /* searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)*/
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

                            productModel.value =
                                Gson().fromJson(error, ProductListResponse::class.java)

                          /*  searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)*/

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }


/*
    fun getSearchData(){


        RxSearchView.queryTextChangeEvents(mSearchView)
            .debounce(400, TimeUnit.MILLISECONDS)
            .flatMap(Func1<SearchViewQueryTextEvent, Observable<String>>() {
                fun call(txtChangeEvt: SearchViewQueryTextEvent): Observable<String> {
                    return Observable.just(txtChangeEvt.queryText().toString())
                        .subscribeOn(AndroidSchedulers.mainThread())
                }
            })
            .flatMap(Func1<GifsData, Observable<String>>() {
                fun call(txtChangeEvt: String): Observable<GifsData> {
                    return RestWebClient.get().getSearchedGifs(txtChangeEvt, "dcJmzC")
                        .subscribeOn(Schedulers.newThread())
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Observer<GifsData> {
                fun onCompleted() {
                    Log.d("#######", "onCompleted searchGifs")
                }

                fun onError(e: Throwable) {
                    Log.d("#######", e.toString())
                }

                fun onNext(gifsData: GifsData) {
                    Log.d("#######", gifsData)
                }
            })



        RxSearchView.queryTextChanges(searchView)
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .switchMap <Single<ProductListResponse>>{ query ->  searchRepository.searchProduct(service,user_id,role_id,company_id,category_id,search_key) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Single<ProductListResponse>()  {
                  fun onCompleted() {

                }

                 fun onError(e: Throwable) {
                  //  Log.e(LOG_TAG, "Error", e)
                }

                  fun onNext(items: ProductListResponse) {
                    // adapter.addItems(...)
                }
            })
    }
*/

   fun getUserID(): String?{
      return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_ID)
   }
    fun getRoleID(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_ROLE_ID)
    }
    fun getUserName(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_NAME)
    }

    fun getRetailerID(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_RETAILTER_ID)
    }
    fun getIsSalesMan(): Boolean{
        return pre.getIsSalesMan()
    }

}