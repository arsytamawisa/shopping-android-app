package com.example.myapplication.utils

class ResourceState(val status: ResourceStatus, val data: Any?, val message: String?) {
    companion object {
        fun success(data: Any?) =
                ResourceState(status = ResourceStatus.SUCCESS, data = data, message = null)

        fun loading() = ResourceState(status = ResourceStatus.LOADING, data = null, message = null)

        fun fail(message: String?) =
                ResourceState(status = ResourceStatus.FAIL, data = null, message = message)
    }
}