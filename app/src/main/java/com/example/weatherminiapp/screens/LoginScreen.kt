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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherminiapp.database.BaseDatabase
import com.example.weatherminiapp.entities.Users
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
        val roomdb = BaseDatabase.getDatabase(this)
        val userRepo = UserRepository(roomdb.userDao())
        loginViewModel = ViewModelProvider(this, LoginVMFactory(userRepository = userRepo))[UserLoginViewModel::class.java]

        loginViewModel.logginResult.observe(this) { loggined ->  if (loggined) {
            Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show()
            // 登录成功，跳转到主页面
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }

        else {
            Toast.makeText(this, "登录失败,请重试", Toast.LENGTH_SHORT).show()
        }}
        setContent {
            WeatherMiniAppTheme {
                LoginScreen(loginViewModel = loginViewModel)
            }
        }


    }
}

//@PreviewScreenSizes
@Composable
fun LoginScreen(loginViewModel : UserLoginViewModel)
{
    var usernames by rememberSaveable {mutableStateOf("") }
    var passwords by rememberSaveable { mutableStateOf("")}
    val currentUser by loginViewModel.currentUser.collectAsStateWithLifecycle()
    val isloggined = currentUser?.isLoggedIn == true
        Scaffold(modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(1f)) { innerPadding ->
        Column(Modifier
            .fillMaxSize(1f).padding(innerPadding)) {
            Spacer(Modifier.height(65.dp).fillMaxWidth(1f))
            Column(
                modifier = Modifier
                    .fillMaxSize(1f), Arrangement.Top,Alignment.CenterHorizontally
            ) {
                if (isloggined){
                    Text("欢迎回来,${currentUser?.username}!", fontSize = 17.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(220.dp), maxLines = 1)
                }
                else {
                    Text("请登录用户", fontSize = 17.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(120.dp), maxLines = 1)

                }
                Spacer(Modifier.height(44.dp).fillMaxWidth(1f))
                Text("用户名:",fontSize = 15.sp)
                TextField(value = usernames,
                    onValueChange = {
                        usernames = it
                    }, modifier = Modifier.height(55.dp),enabled = !isloggined
                )
                Spacer(Modifier.height(15.dp).fillMaxWidth(1f))
                Text("密码:",fontSize = 15.sp)
                TextField(value = passwords,
                    onValueChange = {
                        passwords = it
                    }, modifier = Modifier.height(55.dp), enabled = !isloggined
                    )
                Spacer(Modifier.height(25.dp).fillMaxWidth(1f))
                if(!isloggined)
                {
                    Button(onClick = {
                        loginViewModel.userLogin(username = usernames, password = passwords)
                    }, Modifier.height(50.dp).width(130.dp)) {
                        Text("注册/登录")
                    }
                }
                else
                {
                    Button(onClick = {
                        loginViewModel.logout(currentUser!!.username)
                    },Modifier.height(50.dp).width(130.dp)){
                        Text("登出")
                    }
                }

            }
        }
        }
}

@Composable
fun LoginCard()
{
    Card(Modifier.fillMaxSize(1f)){

    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun LoginPreview()
{
    WeatherMiniAppTheme() {
        val mockRepo = object : UserRepository(null!!) {
         suspend fun validateAndLogin(username: String, password: String): Boolean = true
          override  suspend fun logout(currentUsername: String) {}
            suspend fun getCurrentLoggedInUser(): Users? = null
        }
        val mockViewModel = UserLoginViewModel(mockRepo)
        LoginScreen(loginViewModel = mockViewModel)
    }
}
