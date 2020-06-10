package com.gsa.callbacks;


import com.gsa.network.RestResponse;

public interface APIResponseCallback<T> {

    void onResponse(RestResponse<T> response);
}
