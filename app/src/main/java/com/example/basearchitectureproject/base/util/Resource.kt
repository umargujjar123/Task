package g5.consultency.cuitalibilam.base.util


data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        FAILURE
    }

    companion object {

        fun <T> success(data: T, message: String?): Resource<T> {
            return Resource(Status.SUCCESS, data, message)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            if (message == "Network call has failed for a following reason: No Internet Connection")
                return Resource(Status.ERROR, data, "No Internet Connection!!!")
            else
                return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null, message:String? = null): Resource<T> {
            return Resource(Status.LOADING, data, message)
        }

        fun <T> failed(message: String, data: T? = null): Resource<T> {
            return Resource(Status.FAILURE, data, message)
        }

    }
}
