# Network Layer

### Lazy initialization of OkHttp

Creating the OkHttp client can be expensive, upwards of 100ms and the cause of dropped frames when initialized on the main thread.  

```kotlin
  @Provides
    fun provideRetrofit(
        json: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>
    ) =
        Retrofit.Builder()
            .baseUrl("https://example.com/")
             // lazy injection to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()Normally OkHttp is created along with the retrofit client when injection happens, and this will be on the main thread.  
```

Instead we lazily initialize the OkHttp client once the first API call happens. This would be done off the main thread, as all calls are made on a background thread.

For more info see:[Dagger Party Tricks: Deferred OkHttp Initialization](https://www.zacsweers.dev/dagger-party-tricks-deferred-okhttp-init/) 