package com.luongthuan.demoretrofit.util

/**
 * Created by Luong Thuan on 15/08/2022.
 */
object Constants {
    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    /*chỉ ra lượng cache sẽ lưu là 10MB. Chú ý rằng CACHE_SIZE phải là kiểu long.*/
    const val CACHE_SIZE = (10 * 1024 * 1024).toLong()
    const val READ_TIMEOUT = 5000
    const val WRITE_TIMEOUT = 5000
    const val CONNECT_TIMEOUT = 5000
    const val CACHE_CONTROL = "Cache-Control"
    const val HEADER_PRAGMA = "Pragma"

    /*nếu có internet sẽ lấy cache lưu 1 phút trước, nếu quá 1 phút sẽ không lấy,
     'max-age' là thuộc tính phụ trách việc này.*/
    const val TIME_CACHE_ONLINE = "public, max-age = 60" // 1 minute


    /*nếu không có internet sẽ lấy cache lưu 1 ngày trước đây, nếu quá thì không lấy,
     "max-stale" là thuộc tính phụ trách việc này,
     'only-if-cached' là để không request mà chỉ lấy cache.*/
    const val TIME_CACHE_OFFLINE = "public, only-if-cached, max-stale = 86400" //1 day

}