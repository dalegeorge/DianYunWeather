package com.example.weatherminiapp.cityOptions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.core.R
import com.example.weatherminiapp.ui.theme.WeatherMiniAppTheme
import kotlin.math.exp

class CItyOptions: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherMiniAppTheme() {
                CityMenu({},false)
            }
        }
    }
}

@Composable
fun CityMenu(
    currentCity: (String) -> Unit,
    isLoggined : Boolean
)
{
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.
        padding(10.dp)
    ){
        Button(onClick = { expanded = !expanded }) {
            Text("切换城市")
        }
        if (isLoggined) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("深圳") },
                    onClick = {
                        currentCity("440300")
                    }
                )
                DropdownMenuItem(
                    text = { Text("上海") },
                    onClick = {
                        currentCity("310000")
                    }
                )
                DropdownMenuItem(
                    text = { Text("北京") },
                    onClick = {
                        currentCity("110000")
                    }
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun optionPreview()
{
    CityMenu({},false)
}