package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

public class DonateActivity extends AppCompatActivity {

    EditText charityTitleET,amountET;
    ImageView donateButton;
    int charity_id,user_id;
    private PaymentsClient paymentsclient;
    AppDatabase db;

    // Arbitrarily-picked constant integer you define to track a request for payment data activity.
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        charityTitleET=findViewById(R.id.charityTitleET);
        amountET=findViewById(R.id.donationAmountET);

        donateButton = findViewById(R.id.googlePayButton);

        Wallet.WalletOptions walletOptions =
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build();
        paymentsclient=Wallet.getPaymentsClient(this,walletOptions);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();
//get charity to which we are donating to
        charity_id= intent.getIntExtra("charity_id",0);

        CharityDao charityDao = db.getCharityDao();
        Charity charity = charityDao.findById(charity_id);
        //set page to show charity name
        charityTitleET.setText(charityTitleET.getText().toString()+" "+charity.getTitle());
        //get logged in user's user_id
        user_id= intent.getIntExtra("user_id",0);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( amountET.getText().toString().length()!=0 && Integer.parseInt(amountET.getText().toString())!=0 )
                {
                    requestPayment(view,Integer.parseInt(amountET.getText().toString()));

                }

            }
        });

    }

    private void makeNewDonation(int charity_id,int user_id,String price)
    {
        DonationDao dao = db.getDonationDao();
        Donation d1 = new Donation(user_id,charity_id,price);
        //merge payingactivity with this
        //dont allow to access activity if user has no card. let users add cards
        dao.insertAll(d1);

        DonationWithUserAndCharityDao dao2 = db.getDonationWithUserAndCharityDao();
        List<DonationWithUserAndCharity> donationsList = dao2.getDonationsWithUsersAndCharities();

        for ( DonationWithUserAndCharity x : donationsList) {

            Log.e("makenewdonation", "all donations: " + x.toString());
            Log.e("makenewdonation", "newly inserted DONATION class obj : " + x.donation.getDate()+" "+PaymentsUtil.centsToString(Integer.parseInt(x.donation.getAmount())));

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                    default:
                }


                donateButton.setClickable(true);
                break;
        }

    }
    private void handlePaymentSuccess(PaymentData paymentData) {
        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        Log.d("payment data", paymentInfo);
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");

            final String token = tokenizationData.getString("token");

            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");


            Toast.makeText(this, "Payment Success: ", Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);
            //adding the donation to the database
            makeNewDonation(charity_id,user_id,amountET.getText().toString());
            finish();

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }


    }

    private void handleError(int statusCode) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    public void requestPayment(View view,long price) {
        donateButton.setClickable(false);

        Optional paymentDataRequestJson =
                PaymentsUtil.getPaymentDataRequest(price);
        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());


        AutoResolveHelper.resolveTask(paymentsclient.loadPaymentData(request),
                this,
                LOAD_PAYMENT_DATA_REQUEST_CODE);
    }



    private void showGooglePayButton(boolean shouldShow)
    {
        if(shouldShow)
        {
            donateButton.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(this, "unavailable", Toast.LENGTH_LONG).show();
        }

    }

    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }
        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.

        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        Task<Boolean> task = paymentsclient.isReadyToPay(request);


        task.addOnCompleteListener(this,
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            showGooglePayButton(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }







}