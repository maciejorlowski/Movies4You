package com.maciej.movies4you.functional.utils

import com.maciej.movies4you.functional.data.Constants.StatusCodes.INTERNAL_ERROR
import com.maciej.movies4you.functional.data.Constants.StatusCodes.PERMISSION_CODE
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import com.maciej.movies4you.models.errorResponses.APIError
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Retrofit

import java.io.IOException
import java.util.Objects

object ErrorParser {



    fun parseError(error: Throwable, retrofit: Retrofit): APIError? {

        if (error is HttpException) {
            val response = error.response()

            val converter = retrofit
                .responseBodyConverter<APIError>(APIError::class.java, arrayOfNulls(0))

            val apiError: APIError

            try {
                apiError =
                    converter.convert(Objects.requireNonNull<ResponseBody>(response.errorBody()))
            } catch (e: IOException) {
                return APIError()
            }

            when (apiError.code) {
                PERMISSION_CODE -> RxBus.publish(RxEvent.EventRequestNoPermission())
                INTERNAL_ERROR -> return APIError(null,apiError.code)
                else -> return apiError
            }
        }
        return null
    }
}