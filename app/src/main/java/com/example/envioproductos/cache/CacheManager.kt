package com.example.envioproductos.cache

object CacheManager {
    private val cache = mutableMapOf<String, Any>()

    fun put(key: String, value: Any) {
        cache[key] = value
    }

    fun get(key: String): Any? {
        return cache[key]
    }

    fun clear() {
        cache.clear()
    }
}