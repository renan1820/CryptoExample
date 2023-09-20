package com.example.cryptoexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptoexample.ui.theme.CryptoExampleTheme
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cryptoManager = CryptoManager()

        setContent {
            CryptoExampleTheme {
                var messageToEncrypt by remember {
                    mutableStateOf("")
                }
                var messageToDecrypt by remember {
                    mutableStateOf("")
                }

                var result by remember {
                    mutableStateOf(messageToDecrypt)
                }

                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }


                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ){
                    TextField(
                        value = messageToEncrypt,
                        onValueChange = { messageToEncrypt = it },
                        modifier =  Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Encrypt string")}
                        )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Button(onClick = {
                            messageToEncrypt.let {
                                val bytes = it.encodeToByteArray()
                                val file = File(filesDir, "secret.txt")
                                if(!file.exists()){
                                    file.createNewFile()
                                }
                                val fos = FileOutputStream(file)

                                messageToDecrypt = cryptoManager.encrypt(
                                    bytes = bytes,
                                    outputStream = fos
                                ).decodeToString()
                            }.also {


                            }
                        }) {
                            Text(text = "Encrypt")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            val file = File(filesDir,"secret.txt")
                            messageToEncrypt = cryptoManager.decrypt(inputStream = FileInputStream(file)).decodeToString()
                            result = messageToEncrypt

                        }) {
                            Text(text = "Decrypt")
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = messageToDecrypt,
                        onValueChange = {},
                        enabled = false,
                        label = {
                            Text( text = "Encrypted")
                        },
                        singleLine = true,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = result,
                        onValueChange = {},
                        enabled = false,
                        label = {
                            Text(text = "Decrypted"
                            )
                        },
                        singleLine = true,
                    )

                }
            }
        }
    }
}
