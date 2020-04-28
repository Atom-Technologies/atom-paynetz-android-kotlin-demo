package com.example.atompaynetzkotlintest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.atom.mpsdklibrary.PayActivity
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
           this.payNow()
        }
    }
    fun payNow() {
        Toast.makeText(this, "Pay now clicked", Toast.LENGTH_LONG).show();
        val newPayIntent= Intent(this, PayActivity:: class.java);
        newPayIntent.putExtra("isLive", false); //true for Live
        newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be �0�
        newPayIntent.putExtra("merchantId", "197");
        newPayIntent.putExtra("loginid","197" );
        newPayIntent.putExtra("password", "Test@123");//NCA@1234
        newPayIntent.putExtra("prodid", "NSE");//NCA
        newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be �INR�
        newPayIntent.putExtra("clientcode",encodeBase64( "007") );
        newPayIntent.putExtra("custacc","100000036600" );
        newPayIntent.putExtra("channelid", "INT");
        newPayIntent.putExtra("amt","100.00" ); //Should be 2 decimal number i.e 1.00
        newPayIntent.putExtra("txnid", "013"); //013
        newPayIntent.putExtra("date", "01/10/2019 18:31:00");//Should be in same format
        newPayIntent.putExtra("signature_request","KEY123657234" );
        newPayIntent.putExtra("signature_response","KEYRESP123657234" );
        newPayIntent.putExtra("discriminator","All");//NB,All
        newPayIntent.putExtra("ru","https://paynetzuat.atomtech.in/mobilesdk/param");
//Optinal Parameters
        newPayIntent.putExtra("customerName", "LMN PQR");//Only for Name
        newPayIntent.putExtra("customerEmailID", "Test@gmail.com");//Only for Email ID
        newPayIntent.putExtra("customerMobileNo","8087911057" );//Only for Mobile No.
        newPayIntent.putExtra("billingAddress", "Pune");//Only for Address
        newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 2");// Can pass any data
// Pass data in XML format, only for Multi product
        startActivityForResult(newPayIntent,1);

    }

    fun encodeBase64(encode: String): String? {
        println("[encodeBase64] Base64 encode : $encode")
        var decode: String? = null
        try {
            decode = Base64.encode(encode.toByteArray())
        } catch (e: Exception) {
            println("Unable to decode : $e")
        }
        return decode
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // check if the request code is same as what is passed here it is 2
        println("RESULTCODE--->$resultCode")
        println("REQUESTCODE--->$requestCode")
        println("RESULT_OK--->" + Activity.RESULT_OK)


        if (requestCode == 1) {
            println("---------INSIDE-------")
            if (data != null) {
                val message = data.getStringExtra("status")
                val resKey = data.getStringArrayExtra("responseKeyArray")
                val resValue = data.getStringArrayExtra("responseValueArray")
                if (resKey != null && resValue != null) {
                    for (i in resKey.indices)
                        println("  " + i + " resKey : " + resKey[i] + " resValue : " + resValue[i])
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                println("RECEIVED BACK--->$message")
            }
        }

    }






}
