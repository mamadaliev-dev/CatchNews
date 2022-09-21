package uz.madgeeks.catchnews.data.base

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPrefrenceHelper(context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences("APP_PREFS_NAME", MODE_PRIVATE)

    private lateinit var editor: SharedPreferences.Editor

    fun setPosition(position: Int) {
        editor = preferences.edit()
        editor.putInt("POS", position)
        editor.apply()
    }

    fun getPosition() = preferences.getInt("POS", 0)
}
