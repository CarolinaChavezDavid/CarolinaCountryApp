

package com.example.countryappbycaro.data.model

import java.io.InputStream

data class ImageDownloadedEntity(
    val contentLength: Long,
    val byteStream: InputStream
)
