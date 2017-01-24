package offsec.com.nethuntershortcuttemplate;

/*
*
* This has the default command "wifite"
*
* Permission code from: https://www.simplifiedcoding.net/android-marshmallow-permissions-example/
*/

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Permision code that will be checked in the method onRequestPermissionsResult
    private int NH_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing button
        Button buttonRequestPermission = (Button) findViewById(R.id.launchButton);

        //Adding a click listener
        buttonRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First checking if the app is already having the permission
                if(isTerminalAllowed()){
                    // This is where we actually launch the app.  We pass the terminal command in intentClickListener
                    Log.d("MainActivity", "Launch terminal here");

                    intentClickListener_NH("wifite");
                }

                // No Permission!
                requestStoragePermission();
            }
        });
    }

    //We are calling this method to check the permission status
    private boolean isTerminalAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, "com.offsec.nhterm.permission.RUN_SCRIPT_NH");

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "com.offsec.nhterm.permission.RUN_SCRIPT_NH")){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{"com.offsec.nhterm.permission.RUN_SCRIPT_NH"},NH_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == NH_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted for Nethunter Terminal",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission to Nethunter Terminal",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void intentClickListener_NH(final String command) {
        try {
            Intent intent =
                    new Intent("com.offsec.nhterm.RUN_SCRIPT_NH");
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            intent.putExtra("com.offsec.nhterm.iInitialCommand", command);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this,"Error launching intent",Toast.LENGTH_LONG).show();
        }
    }
}
