package com.luongthuan.demoretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.luongthuan.demoretrofit.respository.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        /*Send a simple GET request*/
//        viewModel.getPost()
//        viewModel.myResponse.observe(this, Observer { response ->
//            Log.i("thuanoc", response.body.toString())
//        })


//        viewModel.run {
//            getPostNumber(edtNumber.text.toString().toInt())
//            myResponseNumber.observe(this@MainActivity, Observer { response ->
//                tvData.text = response.body.toString()
//            })
//        }


//        viewModel.run {
//            getCustomPost(edtNumber.text.toString().toInt(), "id", "desc")
//            myResponseUserId.observe(this@MainActivity, Observer { response ->
//                if (response.isSuccessful) {
//                    tvData.text = response.body().toString()
//                    response.body()?.forEach {
//                        Log.i(
//                            "thuanoc",
//                            "${it.userId.toString()} \n ${it.id.toString()} \n ${it.title.toString()}"
//                        )
//                    }
//                } else {
//                    tvData.text = response.code().toString()
//                }
//            })
//        }

        val options: HashMap<String, String> = HashMap()
        options["_sort"] = "id"
        options["_order"] = "asc"
        btnDone.setOnClickListener {
            viewModel.run {
                getCustomPostMap(edtNumber.text.toString().toInt(), options)
                myResponseMap.observe(this@MainActivity, Observer { response ->
                    if (response.isSuccessful) {
                        tvData.text = response.body().toString()
                        response.body()?.forEach {
                            Log.i(
                                "thuanoc",
                                "${it.userId.toString()} \n ${it.id.toString()} \n ${it.title.toString()}"
                            )
                        }
                    } else {
                        tvData.text = response.code().toString()
                    }
                })
            }
        }

    }


}