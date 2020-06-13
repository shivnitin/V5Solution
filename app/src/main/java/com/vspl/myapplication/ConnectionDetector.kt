package com.vspl.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import android.util.Log

@Suppress("DEPRECATION")
class ConnectionDetector(private val _context: Context) {

    /**
     * Checking for all possible internet providers
     */
    internal var connected = false

    /*  ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;*/ val isConnectingToInternet: Boolean
        get() {
            val connectivityManager =
                _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI
                )!!.state == NetworkInfo.State.CONNECTED
            ) {
                Log.e("dmcknc", "mkemwk")
                return true
            } else {
                Log.e("dmckdsdefewnc", "mkemwk")
                return false
            }
        }

    val connectivityStatus: Int
        get() {

            val cm = _context.getSystemService(
                Context
                    .CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo

            if (null != activeNetwork && activeNetwork.isConnected) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI
                else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE
            }
            return TYPE_NOT_CONNECTED
        }


    fun showNoInternetAlret(_activity: Activity) {
        val internet_dialog = AlertDialog.Builder(_activity).create()
        internet_dialog.setTitle("No Internet")
        internet_dialog.setMessage("No internet connection make sure WIFI or cellular data is turned on, then try again")
        internet_dialog.setButton("Setting") { dialog, which ->
            internet_dialog.dismiss()
            _activity.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
        }
        internet_dialog.show()
    }

    fun showCustomDialog(_activity: Activity, title: String, msg: String) {
        val dialog = AlertDialog.Builder(_activity).create()
        dialog.setTitle(title)
        dialog.setMessage(msg)
        dialog.show()
        dialog.setButton(
            "OK"
        ) { dialog, which -> dialog.dismiss() }


    }


    fun showCustomDialogForParsingAndServerError(_activity: Activity) {

        val internet_dialog = AlertDialog.Builder(
            _activity
        ).create()
        /*internet_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
        // Setting Dialog Title
        internet_dialog.setTitle("Server Error!")

        // Setting Dialog Message
        internet_dialog.setMessage("Please try later")

        // Setting Icon to Dialog
        //internet_dialog.setIcon(R.drawable.tick);

        // Setting OK Button
        internet_dialog.setButton(
            "OK"
        ) { dialog, which -> internet_dialog.dismiss() }

        // Showing Alert Message
        internet_dialog.show()
    }

    fun showAPISuccessDialog(_activity: Activity, msg: String) {

        val internet_dialog = AlertDialog.Builder(
            _activity
        ).create()
        /*internet_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
        // Setting Dialog Title
        internet_dialog.setTitle("Congratulation")

        // Setting Dialog Message
        internet_dialog.setMessage(msg)

        // Setting Icon to Dialog
        //internet_dialog.setIcon(R.drawable.tick);

        // Setting OK Button
        internet_dialog.setButton(
            "OK"
        ) { dialog, which -> internet_dialog.dismiss() }

        // Showing Alert Message
        internet_dialog.show()
    }


/*
    fun showAPIFailureDialog(_activity: Activity, msg: String) {

        val internet_dialog = AlertDialog.Builder(
            _activity
        ).create()
        */
/*internet_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*//*

        // Setting Dialog Title
        internet_dialog.setTitle(_context.getString(R.string.Requests_Failed))

        // Setting Dialog Message
        internet_dialog.setMessage(msg)

        // Setting Icon to Dialog
        //internet_dialog.setIcon(R.drawable.tick);

        // Setting OK Button
        internet_dialog.setButton(
            "OK"
        ) { dialog, which -> internet_dialog.dismiss() }

        // Showing Alert Message
        internet_dialog.show()
    }
*/

    companion object {
        val TYPE_WIFI = 1
        val TYPE_MOBILE = 2
        val TYPE_NOT_CONNECTED = 0
    }

    /*	public void showAPIFailureDialog1(final Activity _activity, String msg){

		final AlertDialog internet_dialog = new AlertDialog.Builder(
				_activity).create();
	*//*internet_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*//*
		// Setting Dialog Title
		internet_dialog.setTitle("Request Failed");

		internet_dialog.setCancelable(false);
		// Setting Dialog Message
		internet_dialog.setMessage(msg);

		// Setting Icon to Dialog
		//internet_dialog.setIcon(R.drawable.tick);

		// Setting OK Button
		internet_dialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				Intent itn = new Intent(_activity, LoginActivity.class);

				_activity.startActivity(itn);

				internet_dialog.dismiss();
			}
		});

		// Showing Alert Message
		internet_dialog.show();
	}*/


}



