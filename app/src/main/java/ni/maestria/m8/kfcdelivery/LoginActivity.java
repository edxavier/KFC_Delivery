package ni.maestria.m8.kfcdelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import ni.maestria.m8.kfcdelivery.db.OperationsTempDetalle;
import ni.maestria.m8.kfcdelivery.db.OperationsUser;
import ni.maestria.m8.kfcdelivery.models.Cliente;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    int MAIN_ACTIVITY = 10;
    SignInButton gpSignIn;
    ProgressDialog pgdialog;
    boolean exit= false;
    int exit_code;
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    /* A flag indicating that a PendingIntent is in progress and prevents
  * us from starting further intents.
  */
    private boolean mIntentInProgress;
    /* Track whether the sign-in button has been clicked so that we know to resolve
 * all issues preventing sign-in without waiting.
 */
    private boolean mSignInClicked;
    /* Store the connection result from onConnectionFailed callbacks so that we can
     * resolve them when the user clicks sign-in.
     */
    private ConnectionResult mConnectionResult;
    OperationsUser operationsUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        gpSignIn = (SignInButton) findViewById(R.id.login_btn);
        gpSignIn.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        pgdialog = new ProgressDialog(this);
        pgdialog.setMessage("Conectando...");
        pgdialog.setTitle("Espere por favor");
        pgdialog.setCancelable(false);
        if(operationsUser==null)
            operationsUser= new OperationsUser(this);
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(getActivity(),"Login",Toast.LENGTH_LONG).show();

        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(operationsUser.getUser()==null) {
            pgdialog.show();
            mGoogleApiClient.connect();
        }else{
            Intent main = new Intent().setClass(LoginActivity.this, MainActivity.class);
            startActivityForResult(main, MAIN_ACTIVITY);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        OperationsTempDetalle dbOperations = new OperationsTempDetalle(this);
        dbOperations.delete();
    }

    @Override
    public void onConnected(Bundle bundle) {
        pgdialog.dismiss();
        mSignInClicked = false;
        if(!exit) {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                Cliente cliente = new Cliente();
                cliente.setNombre(currentPerson.getDisplayName());
                cliente.setImgUrl(currentPerson.getImage().getUrl());
                cliente.setSocialId(currentPerson.getId());
                cliente.setTelefono("---- ----");
                operationsUser.insert(cliente);
                Toast.makeText(this, "Bienvenido, " + cliente.getNombre(), Toast.LENGTH_LONG).show();
                Intent main = new Intent().setClass(LoginActivity.this, MainActivity.class);
                //startActivity(main);
                startActivityForResult(main, MAIN_ACTIVITY);
            }
        }
        else{
            if(exit_code== MainActivity.RESULT_EXIT_SESSION)
            {
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                mGoogleApiClient.disconnect();
                operationsUser.delete();
               // exit=false;
                finish();
            }else{
                revoke();
                operationsUser.delete();
               // exit=false;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        pgdialog.dismiss();
        if (!mIntentInProgress) {
            // Store the ConnectionResult so that we can use it later when the user clicks
            // 'sign-in'.
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if ( resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                pgdialog.show();
                mGoogleApiClient.connect();
            }
        }
        if(requestCode == MAIN_ACTIVITY){
            if(resultCode == RESULT_OK) {
                int result = data.getIntExtra("result",0);
                if(result == MainActivity.RESULT_EXIT)
                    finish();
                else if(result == MainActivity.RESULT_EXIT_SESSION)
                {
                    if (mGoogleApiClient.isConnected()) {
                        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                        mGoogleApiClient.disconnect();
                        finish();
                    }else{
                        exit= true;
                        exit_code =  MainActivity.RESULT_EXIT_SESSION;
                        mGoogleApiClient.connect();
                    }

                }
                else if (result == MainActivity.RESULT_REVOKE_ACCESS){
                    if (mGoogleApiClient.isConnected()) {
                       revoke();
                    }else {
                        exit= true;
                        exit_code =  MainActivity.RESULT_REVOKE_ACCESS;
                        mGoogleApiClient.connect();
                    }
                   // finish();

                }


            }

        }

    }

    void revoke()
    {
        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getApplicationContext(),
                                "Tu cuenta ha sido desvinculada de nuestra aplicacion!", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                Toast.makeText(this,"Espera por favor...",Toast.LENGTH_LONG).show();
                startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                pgdialog.show();
                mGoogleApiClient.connect();
            }
        }
    }


}
