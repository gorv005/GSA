package com.gsa.ui.landing.ledger


import com.gsa.model.ledger.LedgerResponse
import com.gsa.model.login.LoginRequest
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.login.UserList
import io.reactivex.Single

interface LedgerRepository {
    fun getLedger(service :String, user_id: String, role_id: String) : Single<LedgerResponse>

}