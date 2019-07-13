package com.britt.listi.ui.login

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.britt.listi.ListiClient
import com.britt.listi.R
import com.britt.listi.ui.item_list.ListActivity
import com.britt.listi.usecase.LoginUser
import com.nexmo.client.NexmoClient
import com.nexmo.client.NexmoUser
import com.nexmo.client.request_listener.NexmoApiError
import com.nexmo.client.request_listener.NexmoLoginListener
import com.nexmo.client.request_listener.NexmoRequestListener
import kotlinx.android.synthetic.main.activity_login.*

val loginListener =
    NexmoLoginListener { status,
                         reason ->
        println("NexmoLoginListener.onLoginStateChange : $status : $reason")
    }

class LoginActivity : AppCompatActivity() {

    val loginUser = LoginUser()
    val uiModel = LoginUiModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        NexmoClient.init(NexmoClient.NexmoClientConfig(), this, loginListener)

        btnLogin.setOnClickListener {
            uiModel.message.value = "Login Clicked..."
            loginUser.execute(
                tilUsername.editText?.text.toString(),
                tilPassword.editText?.text.toString(),
                {
                    uiModel.message.postValue("Logged In!")
                    finish()
                    startActivity(Intent(this, ListActivity::class.java))
                },
                message = uiModel.message
            )
        }

        uiModel.message.observe(this, Observer<String> {
            tvMessage.text = it
        })


    }


    class LoginUiModel : ViewModel() {
        val message = MutableLiveData<String>()
        var username: String? = null
        var password: String? = null
    }
}


