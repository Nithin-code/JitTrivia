package com.example.jittrivia

import android.app.Application
import com.example.jittrivia.network.APIClient
import com.example.jittrivia.network.APIInterface

class MyApplication : Application() {



    companion object{
        val mAPIInterface : APIInterface = APIClient.getClient().create(APIInterface::class.java)
    }


}