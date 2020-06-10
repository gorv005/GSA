package com.gsa.model

data class SearchEvent(val isLoading: Boolean = false, val isSuccess: Boolean = false, val error: Throwable? = null)