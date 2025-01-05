package com.sy.montaxeri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sy.montaxeri.api.Api
import com.sy.montaxeri.api.Note
import com.sy.montaxeri.ui.theme.MontaXeriTheme
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
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
                    modifier = Modifier
                        .fillMaxWidth().fillMaxHeight().background(Color(247,230,202)),
                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
                ) {
                    Row (
                        modifier = Modifier.padding(0.dp).fillMaxWidth().background(Color(117,78,26)).height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Notepad",
                            modifier = Modifier.weight(1f).padding(start = 8.dp),
                            fontSize = 20.sp,
                            color = Color(248, 225, 183)
                        )
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
                        },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(203, 163, 92),
                                contentColor = Color(248, 225, 183)
                            )) {
                            Text(text = "Get Notes")
                        }
                        Button(onClick = {
                                notes.clear()
                            },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(203, 163, 92),
                                contentColor = Color(248, 225, 183)
                            )) {
                            Text(text = "Clear")
                        }
                    }
                    LazyColumn {
                        items(items = notes) { note ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 0.dp),

                            ) {
                                Column (
                                    modifier = Modifier.fillMaxWidth().background(Color(240, 187, 120)).padding(top = 10.dp, bottom = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    Text(
                                        text = note.title,
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
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