package com.example.cryptoexample

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import com.example.cryptoexample.ui.theme.CryptoExampleTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class DataStoreActivity : ComponentActivity() {

    private val Context.dataStore by dataStore(
        fileName = "user-settings.json",
        serializer = UserSettingsSerializer(CryptoManager())
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoExampleTheme {

                var username by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var settings by remember {
                    mutableStateOf(UserSettings())
                }

                val scope = rememberCoroutineScope()
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ){
                    TextField(
                        value = username,
                        onValueChange = {username = it},
                        placeholder = {
                            Text(text = "Username")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = password,
                        onValueChange = {password = it},
                        placeholder = {
                            Text(text = "Password")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Button(onClick = {
                            scope.launch {

                                dataStore.updateData {
                                    UserSettings(
                                    userName = username,
                                    password = password
                                    )
                                }
                            }
                        }) {
                            Text(text = "Save")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            scope.launch {
                                settings = dataStore.data.first()
                            }
                        }) {
                            Text(text = "Load")
                        }
                    }
                    Text(text = settings.toString())
                }
            }
        }
    }
}
