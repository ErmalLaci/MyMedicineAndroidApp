package com.example.mymedicine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mymedicine.NHSClasses.Conditions;
import com.example.mymedicine.NHSClasses.SignificantLink;
import com.example.mymedicine.NHSServices.NHSApiService;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;


public class SymptomSearchActivity extends AppCompatActivity {
    public String TAG = "MyActivity";
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_search);

        ProgressBar progressBar1 = (ProgressBar)findViewById(R.id.progress_bar1);
        progressBar1.setVisibility(View.GONE);
        configureBackBtn();
        configureRequestButtons();
    }

    @Override
    protected void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    private void configureBackBtn() {
        Button backBtn = findViewById(R.id.back_btn2);
        backBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });

    }

    private void getApiRequest(String categoryChosen){

        Log.d(TAG, "api request method");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nhs.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        String conditionSearchInput = "";
        String condString = "condition";

        // create an instance of the ApiService
        NHSApiService nhsApiService = retrofit.create(NHSApiService.class);

        // make a request by calling the corresponding method

        Single<Conditions> conditionsSingle = nhsApiService.getPersonData(conditionSearchInput, categoryChosen, condString);
        conditionsSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Conditions>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        ProgressBar progressBar1 = (ProgressBar)findViewById(R.id.progress_bar1);
                        progressBar1.setVisibility(View.VISIBLE);

                        //TextView txtView = (TextView) findViewById(R.id.condition_display);
                        //txtView.setText("Loading...");

                    }

                    @Override
                    public void onSuccess(Conditions conditions) {
                        // data is ready and we can update the UI
                        //Log.d(TAG, conditions.getSignificantLink().toString());
                        ProgressBar progressBar1 = (ProgressBar)findViewById(R.id.progress_bar1);
                        progressBar1.setVisibility(View.GONE);
                        ArrayList<SignificantLink> returnedLinks = new ArrayList<>();
                        returnedLinks.addAll(conditions.getSignificantLink());


                        LinearLayout conditionsDisplay = (LinearLayout) findViewById(R.id.condition_display);
                        conditionsDisplay.removeAllViews();

                        String textDisplay;
                        int i = 0;
                        for (SignificantLink link : returnedLinks) {


                            //Reason for this if statement is that when the link has keywords the API never has a treatment page for the condition
                            if (link.getMainEntityOfPage().getKeywords().size() == 0) {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);

                                TextView txtview = new TextView(getApplicationContext());
                                txtview.setId(i);
                                txtview.setPadding(5, 2, 5, 2);

                                if (i % 2 == 0) {
                                    txtview.setBackgroundColor(Color.parseColor("#b2fab4"));
                                } else {
                                    txtview.setBackgroundColor(Color.parseColor("#519657"));
                                }

                                textDisplay = link.getName() + "\n" + link.getDescription();
                                if (link.getName().toLowerCase().equals("acne")) {
                                    txtview.setTextSize(40);
                                }
                                txtview.setText(textDisplay);
                                txtview.setClickable(true);
                                conditionsDisplay.addView(txtview, params);
                                final String linkURL = link.getUrl();
                                final String linkName = link.getName();
                                txtview.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View view) {

                                        Intent intent = new Intent(SymptomSearchActivity.this, ShowConditionActivity.class);
                                        intent.putExtra("EXTRA_CONDITION_URL", linkURL);
                                        intent.putExtra("EXTRA_CONDITION_NAME", linkName);
                                        startActivity(intent);

                                    /*
                                    Toast.makeText(view.getContext(),
                                            "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                                            .show();
                                    */
                                    }
                                });
                                i++;
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // oops, we best show some error message
                    }

                });
    }

    private void configureRequestButtons(){

        Button reqBtnA = findViewById(R.id.condition_a_btn);
        reqBtnA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("A");
            }
        });
        Button reqBtnB = findViewById(R.id.condition_b_btn);
        reqBtnB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("B");
            }
        });
        Button reqBtnC = findViewById(R.id.condition_c_btn);
        reqBtnC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("C");
            }
        });
        Button reqBtnD = findViewById(R.id.condition_d_btn);
        reqBtnD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("D");
            }
        });
        Button reqBtnE = findViewById(R.id.condition_e_btn);
        reqBtnE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("E");
            }
        });
        Button reqBtnF = findViewById(R.id.condition_f_btn);
        reqBtnF.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("F");
            }
        });
        Button reqBtnG = findViewById(R.id.condition_g_btn);
        reqBtnG.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("G");
            }
        });
        Button reqBtnH = findViewById(R.id.condition_h_btn);
        reqBtnH.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("H");
            }
        });
        Button reqBtnI = findViewById(R.id.condition_i_btn);
        reqBtnI.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("I");
            }
        });
        Button reqBtnJ = findViewById(R.id.condition_j_btn);
        reqBtnJ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("J");
            }
        });
        Button reqBtnK = findViewById(R.id.condition_k_btn);
        reqBtnK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("K");
            }
        });
        Button reqBtnL = findViewById(R.id.condition_l_btn);
        reqBtnL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("L");
            }
        });
        Button reqBtnM = findViewById(R.id.condition_m_btn);
        reqBtnM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("M");
            }
        });
        Button reqBtnN = findViewById(R.id.condition_n_btn);
        reqBtnN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("N");
            }
        });
        Button reqBtnO = findViewById(R.id.condition_o_btn);
        reqBtnO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("O");
            }
        });
        Button reqBtnP = findViewById(R.id.condition_p_btn);
        reqBtnP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("P");
            }
        });
        Button reqBtnQ = findViewById(R.id.condition_q_btn);
        reqBtnQ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("Q");
            }
        });
        Button reqBtnR = findViewById(R.id.condition_r_btn);
        reqBtnR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("R");
            }
        });
        Button reqBtnS = findViewById(R.id.condition_s_btn);
        reqBtnS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("S");
            }
        });
        Button reqBtnT = findViewById(R.id.condition_t_btn);
        reqBtnT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("T");
            }
        });
        Button reqBtnU = findViewById(R.id.condition_u_btn);
        reqBtnU.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("U");
            }
        });
        Button reqBtnV = findViewById(R.id.condition_v_btn);
        reqBtnV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("V");
            }
        });
        Button reqBtnW = findViewById(R.id.condition_w_btn);
        reqBtnW.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("W");
            }
        });
        Button reqBtnX = findViewById(R.id.condition_x_btn);
        reqBtnX.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("X");
            }
        });
        Button reqBtnY = findViewById(R.id.condition_y_btn);
        reqBtnY.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("Y");
            }
        });
        Button reqBtnZ = findViewById(R.id.condition_z_btn);
        reqBtnZ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d(TAG, "request button presses --------------------");
                getApiRequest("Z");
            }
        });
    }


}




