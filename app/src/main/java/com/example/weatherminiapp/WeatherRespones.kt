package com.example.weatherminiapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

object WeatherRespones {
    private val cityCode = "440300"
    private val gaodeKey = "6665cad90fa16cf8185db8890a632858"
    private val BASE_URL =
        "https://restapi.amap.com/v3/weather/weatherInfo"

    private val oclient = OkHttpClient()
    private val gson = Gson()


    suspend fun getEveryWeekForecast(cityCode: String): List<Temperature>? =
        withContext(Dispatchers.IO) {
            try{
                val requestUrl = "$BASE_URL?city=$cityCode&key=$gaodeKey&extensions=all"
                // 发送请求到高德天气
                val request = Request.Builder().url(requestUrl).build()
                // 接收天气预报
                val response = oclient.newCall(request).execute()


                // 如果成功发送请求
                if (!response.isSuccessful)
                {
                    println("okhttp请求失败 ${response.code}")
                    return@withContext null
                }

                val jsonStr = response.body?.string() ?: return@withContext null
                println(jsonStr)
                val weatherResp = gson.fromJson(jsonStr, WeatherData::class.java)
                println(weatherResp)

                if (weatherResp.status != "1") {
                    println("高德 API 错误状态: ${weatherResp.status} - ${weatherResp.forecasts}")
                    return@withContext null
                }

                val forecasts = weatherResp.forecasts

                val forecast = forecasts?.firstOrNull()

                val casts = forecast?.casts
                // 获取5天的天气（包含今天）
                val fiveDaysCast = casts?.take(5)
                Log.d("WeatherService", "成功获取 ${fiveDaysCast?.size} 天预报数据")
                fiveDaysCast
//                weatherResp.forecasts?.firstOrNull()

            }catch(e : Exception)
            {
                println(e.message)
                null
            }
        }
}
