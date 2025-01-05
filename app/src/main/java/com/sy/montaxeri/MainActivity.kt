package com.sy.montaxeri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sy.montaxeri.api.Api
import com.sy.montaxeri.api.Note
import com.sy.montaxeri.ui.theme.MontaXeriTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://185.243.48.179:8888/").build()

        val api = retrofit.create(Api::class.java)
        setContent {
            val coroutineScope = rememberCoroutineScope()
            val notes = remember {
                mutableStateListOf<Note>()
            }
            MontaXeriTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(onClick = {
                        coroutineScope.launch {
                            val data = api.getNotes()
                            if (data.isSuccessful) {
                                notes.clear()
                                data.body()?.let {
                                    notes.addAll(it.items)
                                }
                            }
                            println(data)
                        }
                    }) {
                        Text(text = "Get Notes")
                    }
                    LazyColumn {
                        items(items = notes) { note ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                colors = CardDefaults.cardColors(Color.LightGray),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp,
                                                top = 16.dp,
                                                end = 16.dp,
                                                bottom =  8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = note.title,
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = note.content,
                                        textAlign = TextAlign.Center 
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}