package com.gsa.ui.change_password


import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.login.LoginRequest
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.login.UserList
import io.reactivex.Single

interface ChangePasswordRepository {
    fun change_password( user_id: String, old_password: String, new_password: String) : Single<AddToCartResponse>

}