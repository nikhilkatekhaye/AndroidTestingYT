package com.nikhil.androidtestingyt.data.remote.responses

data class ImageResponse(
    var hits : List<ImageResult>,
    var total : Int,
    var totalHits: Int
)