package com.gsa.callbacks

interface AdapterViewPacksClickListener<T> {

    fun onClickPacksAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}