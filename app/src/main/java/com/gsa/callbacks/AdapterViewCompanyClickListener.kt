package com.gsa.callbacks

interface AdapterViewCompanyClickListener<T> {

    fun onClickCompanyAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}