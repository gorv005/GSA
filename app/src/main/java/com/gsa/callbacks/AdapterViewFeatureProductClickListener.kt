package com.gsa.callbacks

interface AdapterViewFeatureProductClickListener<T> {

    fun onClickFeatureProductAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}