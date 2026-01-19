package com.example.weatherminiapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

object WeatherRespones {
    private var cityCode = "440300"
    private val gaodeKey = "6665cad90fa16cf8185db8890a632858"
    private val BASE_URL =
        "https://restapi.amap.com/v3/weather/weatherInfo"

    private val oclient = OkHttpClient()
    private val gson = Gson()


    suspend fun getEveryWeekForcast(cityCode: String): Unit? =
        withContext(Dispatchers.IO) {
            try{
                val requestUrl = "$BASE_URL?city=$cityCode&key=$gaodeKey&extensions=all"
                // 发送请求到高德天气
                val request = Request.Builder().url(requestUrl).build()
                // 接收天气预报
                val response = oclient.newCall(request).execute()


                // 如果成功发送请求
                if (response.isSuccessful)
                {
                    val jsonStr = response.body.toString()
                    val weatherResp = gson.fromJson(jsonStr, WeatherData::class.java)
                    weatherResp.forecast?.firstOrNull()
                }

            }catch(e : Exception)
            {
                println(e.message)
                e.printStackTrace()
                null
            }
        }
}
