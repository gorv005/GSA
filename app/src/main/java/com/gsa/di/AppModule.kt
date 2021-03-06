package com.gsa.di


import android.app.Application
import com.gsa.interfaces.ApplicationSchedulerProvider
import com.gsa.interfaces.SchedulerProvider
import com.gsa.managers.PreferenceManager
import com.gsa.model.home.CompaniesListResponse
import com.gsa.ui.CategoryList.CategoryListRepository
import com.gsa.ui.CategoryList.CategoryListRepositoryImpl
import com.gsa.ui.CategoryList.CategoryListViewModel
import com.gsa.ui.cart.CartRepository
import com.gsa.ui.cart.CartRepositoryImpl
import com.gsa.ui.cart.CartViewModel
import com.gsa.ui.change_password.ChangePasswordRepository
import com.gsa.ui.change_password.ChangePasswordViewModel
import com.gsa.ui.change_password.Change_passwordRepositoryImpl
import com.gsa.ui.companyCategoryList.CategoryCompanyListRepository
import com.gsa.ui.companyCategoryList.CategoryCompanyListRepositoryImpl
import com.gsa.ui.companyCategoryList.CompanyCategoryListViewModel
import com.gsa.ui.companyList.CompanyListActivity
import com.gsa.ui.companyList.CompanyListRepository
import com.gsa.ui.companyList.CompanyListRepositoryImpl
import com.gsa.ui.companyList.CompanyListViewModel
import com.gsa.ui.featureList.FeatureListRepository
import com.gsa.ui.featureList.FeatureListRepositoryImpl
import com.gsa.ui.featureList.FeatureListViewModel
import com.gsa.ui.landing.accounts.AccountRepository
import com.gsa.ui.landing.accounts.AccountRepositoryImpl
import com.gsa.ui.landing.accounts.AccountViewModel
import com.gsa.ui.landing.favorites.FavoritesRepository
import com.gsa.ui.landing.favorites.FavoritesRepositoryImpl
import com.gsa.ui.landing.favorites.FavoritesViewModel
import com.gsa.ui.landing.home.HomeRepository
import com.gsa.ui.landing.home.HomeRepositoryImpl
import com.gsa.ui.landing.home.HomeViewModel
import com.gsa.ui.landing.ledger.LedgerRepository
import com.gsa.ui.landing.ledger.LedgerRepositoryImpl
import com.gsa.ui.landing.ledger.LedgerViewModel
import com.gsa.ui.login.LoginRepository
import com.gsa.ui.login.LoginRepositoryImpl
import com.gsa.ui.login.LoginViewModel
import com.gsa.ui.notification.NotificationRepository
import com.gsa.ui.notification.NotificationRepositoryImpl
import com.gsa.ui.notification.NotificationViewModel
import com.gsa.ui.order.OrderListRepositoryImpl
import com.gsa.ui.order.OrderRepository
import com.gsa.ui.order.OrderViewModel
import com.gsa.ui.points.PointsRepository
import com.gsa.ui.points.PointsRepositoryImpl
import com.gsa.ui.points.PointsViewModel
import com.gsa.ui.productList.ProductListRepository
import com.gsa.ui.productList.ProductListRepositoryImpl
import com.gsa.ui.productList.ProductListViewModel
import com.gsa.ui.register.RegisterRepository
import com.gsa.ui.register.RegisterRepositoryImpl
import com.gsa.ui.register.RegistrationViewModel
import com.gsa.ui.retailer_List.RetailerListRepository
import com.gsa.ui.retailer_List.RetailterListRepositoryImpl
import com.gsa.ui.retailer_List.RetailterListViewModel
import com.gsa.ui.search.SearchRepository
import com.gsa.ui.search.SearchRepositoryImpl
import com.gsa.ui.search.SearchViewModel
import com.gsa.ui.splash.SplashRepository
import com.gsa.ui.splash.SplashRepositoryImpl
import com.gsa.ui.splash.SplashViewModel

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object AppModule {


    /*  bean { PreferenceManager(androidApplication()) }

    bean { ActivityLifecycleManager() }

//     for splash activity
    viewModel { AuthViewModel(get()) }
    bean { AuthnticationRepository() }*/
    val appModule: Module = module {
        single { getSharedPrefrence(androidApplication()) }

       single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
        viewModel { LoginViewModel(get(),get(),get()) }

        single { ApplicationSchedulerProvider() as SchedulerProvider }

        single<RegisterRepository> { RegisterRepositoryImpl(get(), get()) }
        viewModel { RegistrationViewModel(get(),get()) }

        single<HomeRepository> { HomeRepositoryImpl(get(), get()) }
        viewModel { HomeViewModel(get(),get(),get()) }

        single<CompanyListRepository> { CompanyListRepositoryImpl(get(), get()) }
        viewModel { CompanyListViewModel(get(),get(),get()) }

        single<CategoryListRepository> { CategoryListRepositoryImpl(get(), get()) }
        viewModel { CategoryListViewModel(get(),get(),get()) }

        single<CategoryCompanyListRepository> { CategoryCompanyListRepositoryImpl(get(), get()) }
        viewModel { CompanyCategoryListViewModel(get(),get(),get()) }

        single<ProductListRepository> { ProductListRepositoryImpl(get(), get()) }
        viewModel { ProductListViewModel(get(),get(),get()) }

        single<FeatureListRepository> { FeatureListRepositoryImpl(get(), get()) }
        viewModel { FeatureListViewModel(get(),get(),get()) }

        single<OrderRepository> { OrderListRepositoryImpl(get(), get()) }
        viewModel { OrderViewModel(get(),get(),get()) }

        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        viewModel { CartViewModel(get(),get(),get()) }

        single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
        viewModel { AccountViewModel(get(),get(),get()) }

        single<LedgerRepository> { LedgerRepositoryImpl(get(), get()) }
        viewModel { LedgerViewModel(get(),get(),get()) }


        single<PointsRepository> { PointsRepositoryImpl(get(), get()) }
        viewModel { PointsViewModel(get(),get(),get()) }

              single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
        viewModel { SearchViewModel(get(),get(),get()) }

        single<ChangePasswordRepository> { Change_passwordRepositoryImpl(get(), get()) }
        viewModel { ChangePasswordViewModel(get(),get(),get()) }

        single<NotificationRepository> { NotificationRepositoryImpl(get(), get()) }
        viewModel { NotificationViewModel(get(),get(),get()) }

        single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
        viewModel { FavoritesViewModel(get(),get(),get()) }

        single<SplashRepository> { SplashRepositoryImpl(get(), get()) }
        viewModel { SplashViewModel(get(),get(),get()) }


        single<RetailerListRepository> { RetailterListRepositoryImpl(get(), get()) }
        viewModel { RetailterListViewModel(get(),get(),get()) }
        /*  single<SignUpRepository> { SignUpRepositoryImpl(get(), get()) }
         viewModel { SignUpViewModel(get(),get()) }

              single<ResetPasswordRepository> { ResetPasswordRepositoryImpl(get(), get()) }
              viewModel { ResetPasswordViewModel(get(),get()) }

              single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
              viewModel { ProfileViewModel(get(),get()) }

              single<HomeRepository> { HomeRepositoryImpl(get(), get()) }
              viewModel { HomeViewModel(get(),get()) }

              single<ShopRepository> { ShopRepositoryImpl(get(), get()) }
              viewModel { ShopViewModel(get(),get()) }

              single<SubcategoriesRepository> { SubcategoriesRepositoryImpl(get(), get()) }
              viewModel { SubCategoriesViewModel(get(),get()) }



              single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
              viewModel { ProductsViewModel(get(),get()) }


              single<OrderListRepository> { OrderListRepositoryImpl(get(), get()) }
              viewModel { OrdersViewModel(get(),get()) }

              single<CartRepository> { CartRepositoryImpl(get(), get()) }
              viewModel { CartViewModel(get(),get(),get()) }

              single<CouponRepository> { CouponRepositoryImpl(get(), get()) }
              viewModel { CouponViewModel(get(),get()) }

              single<OrderDetailRepository> { OrderDetailRepositoryImpl(get(), get()) }
              viewModel { OrderDetailsViewModel(get(),get()) }

              single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
              viewModel { SearchViewModel(get(),get()) }

              viewModel { ProductsDetailViewModel(get(),get()) }*/
        /* single(named("")) { PreferenceManager(get(named(OKHTTP))) }


      viewModel { MainViewModel(get(),get(),get(),get(),get()) }
        single<MainRepository> { MainRepositoryImpl(get(named(NetworkModule.RETROFIT_AIRLINES_API))) }

    }*/


    }


    fun getSharedPrefrence(app: Application): PreferenceManager {
        return PreferenceManager(app)
    }
}