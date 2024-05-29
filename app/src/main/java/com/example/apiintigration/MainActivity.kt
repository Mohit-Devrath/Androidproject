package com.example.apiintigration

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apiintigration.R

class MainActivity : AppCompatActivity() {
    private var nameEdt: EditText? = null
    private var jobEdt: EditText? = null
    private var postDataBtn: Button? = null
    private var loadingPB: ProgressBar? = null
    private var responseTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEdt = findViewById(R.id.idEdtName)
        jobEdt = findViewById(R.id.idEdtJob)
        postDataBtn = findViewById(R.id.idBtnPostData)
        loadingPB = findViewById(R.id.idPBLoading)
        responseTV = findViewById(R.id.idTVResponse)

        postDataBtn?.setOnClickListener {
            if (nameEdt?.text.toString().isEmpty() || jobEdt?.text.toString().isEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter name and job.", Toast.LENGTH_SHORT).show()
            } else {
                postDataUsingVolley(nameEdt?.text.toString(), jobEdt?.text.toString())
            }
        }
    }

    private fun postDataUsingVolley(name: String?, job: String?) {
        val url = "https://reqres.in/api/users"
        loadingPB?.visibility = View.VISIBLE
        val queue: RequestQueue = Volley.newRequestQueue(this@MainActivity)
        val request: StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                loadingPB?.visibility = View.GONE
                responseTV?.text = "Response from the API is: $response"
                Toast.makeText(this@MainActivity, "Data posted successfully.", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                loadingPB?.visibility = View.GONE
                responseTV?.text = error.message
                Toast.makeText(this@MainActivity, "Failed to get response.", Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["name"] = name!!
                params["job"] = job!!
                return params
            }
        }
        queue.add(request)
    }
}
