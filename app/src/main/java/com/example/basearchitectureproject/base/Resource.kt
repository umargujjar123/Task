package com.example.basearchitectureproject.base

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    object Empty : Resource<Nothing>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()


    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Empty -> "Empty"
            Loading -> "Loading"
        }
    }

}

//suspend fun <T> callApi(apiCall: suspend () -> T): Resource<T> {
//    return try {
//        val response = apiCall.invoke()
//        if (response.isEmptyResponse()) {
//            Resource.Empty
//        } else {
//            Resource.Success(response)
//        }
//    } catch (throwable: Throwable) {
//        throwable.printStackTrace()
//        when (throwable) {
//            is EOFException -> Resource.Error(NoResponseBodyException())
//            is IOException -> Resource.Error(NoInternetException())
//            is HttpException -> Resource.Error(convertToLocalizedException(throwable))
//            else -> Resource.Error(throwable)
//        }
//    }
//}
//
//class NoResponseBodyException() : IOException() {
//    override val message: String
//        get() = context.getString(R.string.no_response_body)
//}

fun convertToLocalizedException(exception: HttpException): Exception {
    val errorBody = exception.response()?.errorBody()
    return if (errorBody != null) {
        return try {
          val rawBody =  errorBody.string()

            val message = JSONObject(rawBody).getString("message")
            val statusCode = JSONObject(rawBody).getString("statusCode")

            val response: Response = okhttp3.Response.Builder() //
                .body(null)
                .code(statusCode.toInt())
                .message(message)
                .protocol(Protocol.HTTP_1_1)
                .request(Request.Builder().url("http://localhost/").build())
                .build()

            CustomHttpException(
                retrofit2.Response.error<String>(
                    message.toResponseBody("plain/text".toMediaTypeOrNull()),
                    response
                )
            )

        } catch (e: JSONException) {
            return e
        }
    } else {
        exception
    }
}

fun <T> Resource<T>.successOr(fallback: T): T {
    return (this as? Resource.Success<T>)?.data ?: fallback
}

fun <T> Resource<T>.succeeded(): Boolean {
    return this is Resource.Success<T>
}

val <T> Resource<T>.data: T?
    get() = (this as? Resource.Success<T>)?.data

fun <T> T.isEmptyResponse(): Boolean {
    return this != null && this is List<*> && this.isEmpty()
}

class CustomHttpException(response: retrofit2.Response<*>) : HttpException(response) {

    override val message: String?
        get() = message()

    override fun getLocalizedMessage(): String? {
        return message()
    }
}