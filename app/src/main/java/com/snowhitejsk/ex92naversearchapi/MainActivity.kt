package com.snowhitejsk.ex92naversearchapi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.snowhitejsk.ex92naversearchapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn.setOnClickListener{searchItems()}
    }

    private fun searchItems(){
        //Naver 검색 API 를 사용하여 검색어의 쇼핑결과를 받아오기

        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)

        //Retrofit을 이용하여 HTTP 통tls 시작
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()

        val retrofitService  =retrofit.create(NaverApiRetrofitService::class.java)

        retrofitService.searchData(binding.et.text.toString()).enqueue(object :Callback<NaverSearchResponse>{
            override fun onResponse(
                call: Call<NaverSearchResponse>,
                response: Response<NaverSearchResponse>
            ) {
                val apiResponse:NaverSearchResponse?= response.body()
                apiResponse?.items?.let {
                    binding.recyclerView.adapter= NaverSearchAdapter(this@MainActivity,it)
                }



            }

            override fun onFailure(call: Call<NaverSearchResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "error : ${t.message}" , Toast.LENGTH_SHORT).show()
            }
        })

//        val call:Call<String> = retrofitService.searchDataString(binding.et.text.toString(),"4ehS1uxP5o4Bi5MCkwiO","_WcYMcxq6I")
//        call.enqueue(object : Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                val s:String? = response.body() //nullable
//                Log.i("TAG",s.toString())
//                AlertDialog.Builder(this@MainActivity).setMessage(s).show()
//
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
    }
}