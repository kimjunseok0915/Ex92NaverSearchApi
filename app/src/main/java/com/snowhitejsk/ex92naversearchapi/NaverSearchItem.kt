package com.snowhitejsk.ex92naversearchapi

data class NaverSearchResponse constructor(var total:Int,var items:MutableList<NaverSearchItem>)

data class NaverSearchItem constructor(
    var title:String,
    var link:String,
    var image:String,
    var lprice:String,
    var mallName:String)

