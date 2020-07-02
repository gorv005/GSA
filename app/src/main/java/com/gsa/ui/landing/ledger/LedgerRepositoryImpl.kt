package com.gsa.ui.landing.ledger


import com.gsa.managers.PreferenceManager
import com.gsa.model.ledger.LedgerResponse
import com.gsa.model.login.LoginRequest
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.login.UserList

import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class LedgerRepositoryImpl(private val restApi: AppRestApiFast, private val pre: PreferenceManager) : LedgerRepository {
    override fun getLedger(
        service: String,
        user_id: String,
        role_id: String
    ): Single<LedgerResponse> {
        return restApi.getLedgerReport(service,user_id,role_id)
    }
}