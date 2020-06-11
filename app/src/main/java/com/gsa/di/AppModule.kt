package com.gsa.di


import android.app.Application
import com.gsa.interfaces.ApplicationSchedulerProvider
import com.gsa.interfaces.SchedulerProvider
import com.gsa.managers.PreferenceManager
import com.gsa.model.home.CompaniesListResponse
import com.gsa.ui.CategoryList.CategoryListRepository
import com.gsa.ui.CategoryList.CategoryListRepositoryImpl
import com.gsa.ui.CategoryList.CategoryListViewModel
import com.gsa.ui.companyList.CompanyListActivity
import com.gsa.ui.companyList.CompanyListRepository
import com.gsa.ui.companyList.CompanyListRepositoryImpl
import com.gsa.ui.companyList.CompanyListViewModel
import com.gsa.ui.landing.home.HomeRepository
import com.gsa.ui.landing.home.HomeRepositoryImpl
import com.gsa.ui.landing.home.HomeViewModel
import com.gsa.ui.login.LoginRepository
import com.gsa.ui.login.LoginRepositoryImpl
import com.gsa.ui.login.LoginViewModel
import com.gsa.ui.register.RegisterRepository
import com.gsa.ui.register.RegisterRepositoryImpl
import com.gsa.ui.register.RegistrationViewModel
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
        viewModel { LoginViewModel(get(),get()) }

        single { ApplicationSchedulerProvider() as SchedulerProvider }

        single<RegisterRepository> { RegisterRepositoryImpl(get(), get()) }
        viewModel { RegistrationViewModel(get(),get()) }

        single<HomeRepository> { HomeRepositoryImpl(get(), get()) }
        viewModel { HomeViewModel(get(),get(),get()) }

        single<CompanyListRepository> { CompanyListRepositoryImpl(get(), get()) }
        viewModel { CompanyListViewModel(get(),get(),get()) }

        single<CategoryListRepository> { CategoryListRepositoryImpl(get(), get()) }
        viewModel { CategoryListViewModel(get(),get(),get()) }

        viewModel { SplashViewModel(get()) }

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