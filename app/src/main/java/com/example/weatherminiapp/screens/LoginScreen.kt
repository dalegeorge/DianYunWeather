package com.example.weatherminiapp.screens

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.weatherminiapp.database.BaseDatabase
import com.example.weatherminiapp.repository.UserRepository
import com.example.weatherminiapp.ui.theme.WeatherMiniAppTheme
import com.example.weatherminiapp.viewModelFactory.LoginVMFactory
import com.example.weatherminiapp.viewModels.UserLoginViewModel
import kotlinx.coroutines.flow.observeOn


class LoginActivity: ComponentActivity()
{
    private lateinit var loginViewModel: UserLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherMiniAppTheme {
                LoginScreen()
            }
        }
        val roomdb = BaseDatabase.getInstance(this)
        val userRepo = UserRepository(roomdb.userDao())
        loginViewModel = ViewModelProvider(this, LoginVMFactory(userRepository = userRepo))[UserLoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { users ->  if (users != null && users.isLoggedIn) {
            Toast.makeText(this, "登录成功: ${users.username}", Toast.LENGTH_SHORT).show()
            // 登录成功，跳转到主页面
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish() // 完成登录页
        }}

    }
}

@PreviewScreenSizes
@Composable
fun LoginScreen()
{

        Scaffold(modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f)) { innerPadding ->
            Row(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(1f)

            ) {
                Spacer(Modifier.height(65.dp).fillMaxWidth(1f))


            }

            Spacer(Modifier.height(65.dp).fillMaxWidth(1f))
        }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun LoginPreview()
{
    WeatherMiniAppTheme() {
        LoginScreen()
    }
}
