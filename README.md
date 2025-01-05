---
https://www.figma.com/design/D7SDLU8qInyi9fs20K71Rx/Kotlin-Konnect?node-id=272-33&p=f&t=iYxnIXFcU8hbVxXt-0


## Full Tutorial: UI Design and Backend Integration with PocketBase  

### 📝 Prerequisites  

1. A working PocketBase server.  
2. Basic knowledge of Kotlin, Jetpack Compose, and Android Studio.  

---

### 🚀 Steps  

### 1. **Set Up Dependencies**  

Add these dependencies to your `build.gradle` file under `dependencies`:  

```kotlin
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.google.code.gson:gson:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

Sync your project after adding the dependencies.  

---

### 2. **Prepare PocketBase Backend**  

1. Start your PocketBase server.  
2. Create a collection named `notes` with fields:  
   - `title` (Text)  
   - `content` (Text)  
   - `date` (Date)  

3. API endpoint to fetch notes:  

```http
GET http://<your-pocketbase-url>/api/collections/notes/records
```

Replace `<your-pocketbase-url>` with your server URL.  

---

### 3. **Set Up Retrofit**  

1. Create a Retrofit instance:  

```kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("<your-pocketbase-url>")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(NotesApiService::class.java)
```

Replace `<your-pocketbase-url>` with your PocketBase server URL.  

2. Define an API interface:  

```kotlin
interface NotesApiService {
    @GET("api/collections/notes/records")
    suspend fun getNotes(): Response<NotesResponse>
}

data class NotesResponse(
    val items: List<Note>
)

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val date: String
)
```

---

### 4. **Build the UI Directly in the Activity**  

Inside your `setContent` block in the `MainActivity`, add the following code:  

```kotlin
setContent {
    val coroutineScope = rememberCoroutineScope()
    val notes = remember { mutableStateListOf<Note>() }
    val isLoading = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    isLoading.value = true
                    val response = api.getNotes()
                    if (response.isSuccessful) {
                        response.body()?.let { notesResponse ->
                            notes.clear()
                            notes.addAll(notesResponse.items)
                        }
                    }
                    isLoading.value = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text(text = "Get Notes")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notes) { note ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = note.title, style = MaterialTheme.typography.h6)
                        Text(text = note.content, style = MaterialTheme.typography.body2)
                        Text(text = note.date, style = MaterialTheme.typography.caption)
                    }
                }
            }
        }
    }
}
```

---

### 5. **Test Your Application**  

1. Run the app.  
2. Press the "Get Notes" button.  
3. Verify that:  
   - The list displays data fetched from the PocketBase backend.  
   - The button shows a loading indicator while fetching.  

---

## 🎉 Congratulations!  

You’ve successfully built a simple app with Jetpack Compose that connects to a PocketBase backend, all within your `MainActivity`.

### راهنمای کامل: طراحی رابط کاربری و اتصال به بک‌اند PocketBase  

---

### 📝 پیش‌نیازها  

1. یک سرور PocketBase در حال اجرا.  
2. آشنایی پایه با Kotlin، Jetpack Compose، و Android Studio.  

---

### 🚀 مراحل انجام کار  

### 1. **اضافه کردن وابستگی‌ها**  

کد زیر را به بخش `dependencies` فایل `build.gradle` پروژه خود اضافه کنید:  

```kotlin
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.google.code.gson:gson:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

پس از اضافه کردن وابستگی‌ها، پروژه را **Sync** کنید.  

---

### 2. **راه‌اندازی سرور PocketBase**  

1. سرور PocketBase خود را راه‌اندازی کنید.  
2. یک کالکشن با نام `notes` ایجاد کنید و فیلدهای زیر را اضافه کنید:  
   - `title` (متن)  
   - `content` (متن)  
   - `date` (تاریخ)  

3. آدرس API برای دریافت اطلاعات:  

```http
GET http://<your-pocketbase-url>/api/collections/notes/records
```

به جای `<your-pocketbase-url>` آدرس سرور خود را قرار دهید.  

---

### 3. **تنظیم Retrofit**  

1. ایجاد یک نمونه از Retrofit:  

```kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("<your-pocketbase-url>")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(NotesApiService::class.java)
```

آدرس سرور PocketBase را به جای `<your-pocketbase-url>` قرار دهید.  

2. تعریف یک اینترفیس API:  

```kotlin
interface NotesApiService {
    @GET("api/collections/notes/records")
    suspend fun getNotes(): Response<NotesResponse>
}

data class NotesResponse(
    val items: List<Note>
)

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val date: String
)
```

---

### 4. **ساخت رابط کاربری در داخل اکتیویتی**  

در بخش `setContent` اکتیویتی اصلی (`MainActivity`) کد زیر را قرار دهید:  

```kotlin
setContent {
    val coroutineScope = rememberCoroutineScope()
    val notes = remember { mutableStateListOf<Note>() }
    val isLoading = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    isLoading.value = true
                    val response = api.getNotes()
                    if (response.isSuccessful) {
                        response.body()?.let { notesResponse ->
                            notes.clear()
                            notes.addAll(notesResponse.items)
                        }
                    }
                    isLoading.value = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text(text = "دریافت یادداشت‌ها")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notes) { note ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = note.title, style = MaterialTheme.typography.h6)
                        Text(text = note.content, style = MaterialTheme.typography.body2)
                        Text(text = note.date, style = MaterialTheme.typography.caption)
                    }
                }
            }
        }
    }
}
```

---

### 5. **تست برنامه**  

1. برنامه را اجرا کنید.  
2. دکمه "دریافت یادداشت‌ها" را فشار دهید.  
3. بررسی کنید که:  
   - لیست یادداشت‌ها از سرور PocketBase نمایش داده شود.  
   - دکمه هنگام دریافت اطلاعات حالت بارگذاری را نمایش دهد.  

---

## 🎉 تبریک!  

شما با موفقیت یک اپلیکیشن ساده با استفاده از Jetpack Compose و اتصال به بک‌اند PocketBase طراحی و پیاده‌سازی کردید.
