package com.example.coroutine_and_flow.util

data class Resource <T>
    ( val status: Status, val message: String?, val data: T? ) {

        companion object {
            fun <T> success(data: T?) : Resource<T> {
                return Resource(Status.SUCCESS,null,data)
            }
            fun <T> error(message: String, data: T?) : Resource<T> {
                return Resource(Status.ERROR,message,data)
            }
        }
}