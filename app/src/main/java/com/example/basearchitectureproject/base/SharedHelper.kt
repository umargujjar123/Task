package g5.consultency.cuitalibilam.helper

import android.content.Context
import android.content.SharedPreferences

class SharedHelper {

    companion object {
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        public fun putKey(context: Context, key: String, value: String): String {
            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            editor?.putString(key, value);
            editor?.commit()
            return key
        }

        public fun getKey(context: Context, key: String): String? {
            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            val value: String? = sharedPreferences!!.getString(key, "")
            return value
        }


//        fun putCustomModel(context: Context, key: String, value: List<AboutUsDataModel>): String {
//            val gson = Gson()
//            val json: String = gson.toJson(value)
//            editor = sharedPreferences!!.edit()
//            editor?.putString(AppConstants.ABOUT_US_DATA, json)
//            editor?.commit()
//            return key
//        }

//        fun getAboutUsCustomModel(context: Context, key: String): Resource<List<AboutUsDataModel>> {
//            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
//
//            val gson = Gson()
//            val jsonString = sharedPreferences?.getString(key, "")
//
//            if (jsonString != null) {
//                return Resource.success(
//                    data = gson.fromJson(jsonString, AboutUsDataModel::class.java) as  List<AboutUsDataModel>,
//                    message = null
//                )
//            } else {
//                return Resource.error(message = "Failed to fetch from cache")
//            }
//        }

        fun clearAllSharedPreferenceValues(context: Context) {
            sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            editor!!
                .clear().commit()
        }
    }
}