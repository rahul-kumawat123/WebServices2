package com.example.webservices2

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_show_data.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ShowDataActivity : AppCompatActivity() {

    private lateinit var adapter: CustomAdapter
    private lateinit var progressDialog: ProgressDialog

    var dataList = ArrayList<DataModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)


        createProgressDialog()

        //recycler view
        val layoutManager = LinearLayoutManager(this ,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager

        //attaching adapter to recycler view
        adapter = CustomAdapter(this,dataList)
        recyclerView.adapter = adapter

        showData()

    }



    private fun createProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading..")
        progressDialog.setMessage("Please wait while we fetch data..")
        progressDialog.setCancelable(false)
    }

    private fun showData() {

        progressDialog.show()

        val call = ApiClient.getClient.getData()
        call.enqueue(object : retrofit2.Callback<ResponseDataModel>{

            override fun onResponse(call: Call<ResponseDataModel>, response: Response<ResponseDataModel>) {

               if(response.isSuccessful) {
                   dataList.addAll(response.body()?.posts ?: ArrayList())
                   recyclerView.adapter?.notifyDataSetChanged()
                   Log.e("Data", "Data is ${response.body()}")
               }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseDataModel>, t: Throwable) {

                progressDialog.dismiss()
                Log.e("Failure","Error is ${t.localizedMessage}")
                showToast("Some Error Occurred while fetching data")
            }
        })

    }
}