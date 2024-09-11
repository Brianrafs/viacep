package com.example.viacep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viacep.model.Endereco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.viacep.services.EnderecoServiceIF

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnderecoApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnderecoApp() {
    var cep by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf<Endereco?>(null) }
    var erro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F2F1)) // fundo verde claro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Busca de Endereço", fontSize = 24.sp, color = Color(0xFF00796B))

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = cep,
            onValueChange = { cep = it },
            label = { Text("Digite o CEP", color = Color(0xFF00796B)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(
                onDone = {
                    buscarEndereco(cep, onSuccess = { endereco = it }, onError = { erro = it })
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF00796B),
                unfocusedBorderColor = Color(0xFF004D40),
                containerColor = Color.White,
                focusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                buscarEndereco(cep, onSuccess = { endereco = it }, onError = { erro = it })
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
        ) {
            Text("Buscar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (erro.isNotEmpty()) {
            Text(text = erro, color = Color.Red, fontSize = 16.sp)
        }


        endereco?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Logradouro: ${it.logradouro}", fontSize = 16.sp)
                    Text(text = "Bairro: ${it.bairro}", fontSize = 16.sp)
                    Text(text = "Cidade: ${it.localidade}", fontSize = 16.sp)
                    Text(text = "UF: ${it.uf}", fontSize = 16.sp)
                    Text(text = "CEP: ${it.cep}", fontSize = 16.sp)
                }
            }
        }
    }
}

fun buscarEndereco(
    cep: String,
    onSuccess: (Endereco) -> Unit,
    onError: (String) -> Unit
) {
    val call = RetrofitClient.enderecoServiceIF.getEndereco(cep)
    call.enqueue(object : Callback<Endereco> {
        override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
            if (response.isSuccessful) {
                response.body()?.let { onSuccess(it) } ?: onError("Endereço não encontrado.")
            } else {
                onError("Falha ao buscar o endereço.")
            }
        }

        override fun onFailure(call: Call<Endereco>, t: Throwable) {
            onError("Erro de comunicação: ${t.message}")
        }
    })
}
