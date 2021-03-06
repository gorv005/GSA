package com.gsa.managers

//import com.gsa.models.signUp.UserRequestEmail
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.gsa.model.login.UserList
import com.gsa.model.reatilter_list.RetailerlListItem
import com.gsa.utils.Config
import com.gsa.utils.Config.SharedPreferences.PROPERTY_FCM_REGISTRATION_TOKEN

class PreferenceManager(context: Context) /*: BasePrefManager(context)*/ {

    @SuppressLint("ApplySharedPref")
    fun logOut() {
        val fcmToken = getStringPreference(PROPERTY_FCM_REGISTRATION_TOKEN)

        val editor = sharedPreferences.edit()
        editor.clear()
        commitChangesInEditor(editor)

        savePreference(PROPERTY_FCM_REGISTRATION_TOKEN, fcmToken)
    }


  fun loginUser(token: String?, user: UserList?,isRemember :Boolean) {
        savePreference(Config.SharedPreferences.PROPERTY_JWT_TOKEN, token)
        user?.let { saveUserData(it) }
        savePreference(Config.SharedPreferences.PROPERTY_LOGIN_PREF, isRemember)
    }

    fun saveUserData(user: UserList?) {
        savePreference(Config.SharedPreferences.PROPERTY_USER_ID, user?.userId)
        savePreference(Config.SharedPreferences.PROPERTY_ROLE_ID, user?.roleId)

        savePreference(Config.SharedPreferences.PROPERTY_USER_NAME, user?.name)
        savePreference(Config.SharedPreferences.PROPERTY_USER_EMAIL, user?.email)
     //   saveUserImage(user?.profilePicture?.url)

    }
    fun saveRetailerData(user: RetailerlListItem?) {
        savePreference(Config.SharedPreferences.PROPERTY_RETAILTER_ID, user?.userId)
        savePreference(Config.SharedPreferences.PROPERTY_RETAILTER_NAME, user?.name)
        savePreference(Config.SharedPreferences.PROPERTY_RETAILTER__ROLE_ID, user?.roleId)

        //   saveUserImage(user?.profilePicture?.url)

    }

    fun saveUserID(userID: String?) {
        savePreference(Config.SharedPreferences.PROPERTY_USER_ID, userID)

        //   saveUserImage(user?.profilePicture?.url)

    }

    fun saveUserImage(userImage: String?) {
        savePreference(Config.SharedPreferences.PROPERTY_USER_IMAGE, userImage)
    }

    fun isUserFBLoggedIn() = getBooleanPreference(Config.SharedPreferences.PROPERTY_USER_IS_FB_LOGIN)
    fun isUserLoggedIn() = getBooleanPreference(Config.SharedPreferences.PROPERTY_LOGIN_PREF)
    fun getRoleId() = getStringPreference(Config.SharedPreferences.PROPERTY_ROLE_ID)
    fun getLoggedInUserEmail() = getStringPreference(Config.SharedPreferences.PROPERTY_USER_EMAIL)
    fun getLoggedInUserImage() = getStringPreference(Config.SharedPreferences.PROPERTY_USER_IMAGE)
    fun getLoggedInUserImageThumb() = getStringPreference(Config.SharedPreferences.PROPERTY_USER_IMAGE_THUMB)
    fun getIsSalesMan() = getBooleanPreference(Config.SharedPreferences.IS_SALESMAN_LOGIN)


    val sharedPreferences = context.getSharedPreferences(
        Config.SharedPreferences.PROPERTY_PREF,
        Context.MODE_PRIVATE
    )

    fun savePreference(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        commitChangesInEditor(editor)
    }

    fun savePreference(key: String, value: Int?, defaultValue: Int = 0) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value ?: defaultValue)
        commitChangesInEditor(editor)
    }

    fun savePreference(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        commitChangesInEditor(editor)
    }

    fun getStringPreference(prefName: String, defaultValue: String? = null): String? =
        sharedPreferences.getString(prefName, defaultValue)

    fun getIntPreference(prefName: String, defaultValue: Int = 0) = sharedPreferences.getInt(prefName, defaultValue)

    fun getBooleanPreference(prefName: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(prefName, defaultValue)

    private fun clearPreference(key: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, null)
        commitChangesInEditor(editor)
    }

    private fun commitChangesInEditor(editor: SharedPreferences.Editor) {
        editor.commit()
    }

    fun getDeviceTokenForFCM(): String? {

        return getStringPreference(Config.SharedPreferences.PROPERTY_FCM_REGISTRATION_TOKEN,null)
    }
     fun getBearerToken(): String {
        val token = sharedPreferences.getString(Config.SharedPreferences.PROPERTY_JWT_TOKEN,"")
        return "Bearer $token"
    }
}