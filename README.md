# atom-paynetz-android-kotlin-demo

This is the integration guide to Atom Paynetz Payment Gateway to your application.

##### Step 1. Go to [documentaions](https://github.com/Atom-Technologies/atom-paynetz-android-kotlin-demo/tree/master/documentations "documentaions")
##### 
##### Step 2: You will find [Atom Paynetz Library](https://github.com/Atom-Technologies/atom-paynetz-android-kotlin-demo/blob/master/documentations/mpsdklibrary-release.aar "Atom Paynetz Library") which you have to include in your project.

##### Step 3: [Documentation](https://github.com/Atom-Technologies/atom-paynetz-android-kotlin-demo/blob/master/documentations/Kotlin%20-%20Atom%20Payment%20Gateway%20Integration%20Document.docx "Documentation") is also avalaible, whic will guide you how to interagte the library. 

After this you can head forward to start writing your code.

##### Step 4:  Add following block of code in your app for encoding the client code 


    import com.sun.org.apache.xerces.internal.impl.dv.util.Base64
    
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
    

#####  Step 5: Use following code to invoke payment gateway 

##### For NB/All :


    
    val newPayIntent= Intent(this, PayActivity:: class.java);
    newPayIntent.putExtra("isLive", false);//true for Live newPayIntent.putExtra("merchantId", "197");//1191
    newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be �0�
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
    newPayIntent.putExtra("mprod",mprod );  
    // Pass data in XML format, only for Multi product
    startActivityForResult(newPayIntent,1);
    
#####            For CC/DC :
```java
val newPayIntent= Intent(this, PayActivity:: class.java);
newPayIntent.putExtra("merchantId", "197");//1191
newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be  0
newPayIntent.putExtra("loginid","197" );
newPayIntent.putExtra("password", "Test@123");//NCA@1234
newPayIntent.putExtra("prodid", "NSE");//NCA
newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be  INR
newPayIntent.putExtra("clientcode",encodeBase64( "007") );
newPayIntent.putExtra("custacc","100000036600" );
newPayIntent.putExtra("channelid", "INT");
newPayIntent.putExtra("amt","100.00" ); //Should be 2 decimal number i.e 1.00
newPayIntent.putExtra("txnid", "013"); //013
newPayIntent.putExtra("date", "01/10/2019 18:31:00");//Should be in same format
newPayIntent.putExtra("signature_request","KEY123657234" ); 
newPayIntent.putExtra("signature_response","KEYRESP123657234" );
// CC or DC ONLY (value should be same as commented)
newPayIntent.putExtra("cardtype", "CC");
// VISA or MASTER or MAESTRO ONLY (value should be same as commented)
newPayIntent.putExtra("cardAssociate", "VISA");
newPayIntent.putExtra("surcharge", "YES");
 newPayIntent.putExtra("ru","https://paynetzuat.atomtech.in/mobilesdk/param"); 
//Optinal Parameters
newPayIntent.putExtra("customerName", "LMN PQR");//Only for Name
newPayIntent.putExtra("customerEmailID", "Test@gmail.com");//Only for Email ID
newPayIntent.putExtra("customerMobileNo","8087911057" );//Only for Mobile No. 
newPayIntent.putExtra("billingAddress", "Pune");//Only for Address
newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 2");// Can pass any data
// Pass data in XML format, only for Multi product
newPayIntent.putExtra("mprod",mprod ); 
startActivityForResult(newPayIntent,1);
```

##### Step 6: To receive response in app use following app as :

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

If you have any queries you can contact helpdesk team of Atom Technologies
