package com.example.mymedicine;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.mymedicine.NHSClasses.Condition;
import com.example.mymedicine.NHSClasses.MainEntityOfPage;
import com.example.mymedicine.NHSClasses.RelatedLink;
import com.example.mymedicine.NHSClasses.Treatment;
import com.example.mymedicine.NHSServices.ConditionAPIService;
import com.example.mymedicine.NHSServices.TreatmentAPIService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowConditionActivity extends AppCompatActivity {
    private String TAG = "MyActivity";
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_condition);
        configureBackBtn();

        String conditionUrlInput = getIntent().getStringExtra("EXTRA_CONDITION_URL");
        String conditionInput= conditionUrlInput.replace("/conditions/", "");

        String conditionName = getIntent().getStringExtra("EXTRA_CONDITION_NAME");

        TextView nameTxt = findViewById(R.id.condition_name_textview);
        nameTxt.setText(conditionName);

        getApiRequest(conditionInput);

    }

    @Override
    protected void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }


    private void getApiRequest(String conditionInput){

        Log.d(TAG, "api request method");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nhs.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        // create an instance of the ApiService
        ConditionAPIService conditionAPIService = retrofit.create(ConditionAPIService.class);

        // make a request by calling the corresponding method

        Single<Condition> conditionSingle = conditionAPIService.getPersonData(conditionInput);
        conditionSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Condition>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Condition condition) {
                        // data is ready and we can update the UI
                        //Log.d(TAG, conditions.getSignificantLink().toString());
                        //ProgressBar progressBar2 = (ProgressBar)findViewById(R.id.progress_bar2);
                        //progressBar2.setVisibility(View.GONE);

                        try {
                            ArrayList<RelatedLink> returnedLinks = new ArrayList<>();
                            returnedLinks.addAll(condition.getRelatedLink());
                            Boolean treatmentFound = false;
                            for (RelatedLink link : returnedLinks) {

                                Log.d(TAG, link.getUrl());
                                if (link.getName().toLowerCase().contains("treatment")) {
                                    String treatmentInput = link.getUrl().replace("/conditions/", "");
                                    Log.d(TAG, treatmentInput);
                                    getTreatmentAPIRequest(treatmentInput);
                                    treatmentFound = true;
                                }
                            }
                            if (!treatmentFound) {
                                ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progress_bar2);
                                progressBar2.setVisibility(View.GONE);
                                TextView treatTxt = findViewById(R.id.textView6);
                                treatTxt.setText("We do not currently have information on the treatments for this illness.");
                            }
                        }
                        catch(Error e){
                            ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progress_bar2);
                            progressBar2.setVisibility(View.GONE);
                            TextView treatTxt = findViewById(R.id.textView6);
                            treatTxt.setText("We do not currently have information on the treatments for this illness.");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // oops, we best show some error message
                    }
                });
    }

    private void getTreatmentAPIRequest(String treatmentInput){

        Log.d(TAG, "api request method");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nhs.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        // create an instance of the ApiService
        TreatmentAPIService treatmentAPIService = retrofit.create(TreatmentAPIService.class);

        // make a request by calling the corresponding method

        Single<Treatment> treatmentSingle = treatmentAPIService.getPersonData(treatmentInput);
        treatmentSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Treatment>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Treatment treatment) {

                        // data is ready and we can update the UI
                        //Log.d(TAG, conditions.getSignificantLink().toString());

                        ProgressBar progressBar2 = (ProgressBar)findViewById(R.id.progress_bar2);
                        progressBar2.setVisibility(View.GONE);

                        TextView descTxt = findViewById(R.id.condition_description_textView);
                        descTxt.setText(treatment.getDescription());


                        ArrayList<MainEntityOfPage> pages = new ArrayList<>();
                        pages.addAll(treatment.getMainEntityOfPage());

                        int i = 0;

                        Log.d(TAG, "Creating treatment boxes");

                        Object treatmentName;
                        Object treatmentDescription;

                        for (MainEntityOfPage page : pages) {

                            LinearLayout treatmentsDisplay = (LinearLayout) findViewById(R.id.treatment_display_layout);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            Log.d(TAG, page.getText());


                            if (!page.getText().equals("")) {
                                final TextView txtview = new TextView(getApplicationContext());

                                txtview.setId(i);
                                txtview.setPadding(5, 30, 30, 2);

                                txtview.setText(page.getText());

                                if (i % 2 == 0) {
                                    txtview.setBackgroundColor(Color.parseColor("#b2fab4"));
                                } else {
                                    txtview.setBackgroundColor(Color.parseColor("#519657"));
                                }

                                treatmentDescription = page.getMainEntityOfPage().get(0).getText();

                                Log.d(TAG, page.getText());
                                Log.d(TAG, page.getMainEntityOfPage().get(0).getText());

                                txtview.setTag(R.string.treatment_description_dialog_store, treatmentDescription);
                                txtview.setTag(R.string.treatment_no_dialog_store, i);

                                txtview.setClickable(true);
                                treatmentsDisplay.addView(txtview, params);

                                txtview.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View view) {

                                        Log.d(TAG, "Treat dialog btn clicked");
                                        displayDialog(txtview.getId(), txtview.getText(), txtview.getTag(R.string.treatment_description_dialog_store), txtview.getTag(R.string.treatment_no_dialog_store));

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

    private void displayDialog(int i, Object namePass, Object descriptionPass, Object numberPass){

        final String name = namePass.toString();
        String descriptionInp = descriptionPass.toString();
        String description = descriptionInp.replaceAll("href=", "");

        final String treatmentNoPass = numberPass.toString();

        int tNInc = Integer.parseInt(treatmentNoPass) + 1;

        final String treatmentNumber = Integer.toString(tNInc);

        // create a WebView with the current stats
        WebView webView = new WebView(getApplicationContext());
        webView.loadData(description, "text/html", "utf-8");

        final String id = Integer.toString(i);

        Log.d(TAG, "loading dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowConditionActivity.this);
        builder.setView(webView)
                .setTitle(name);

        builder.setPositiveButton(R.string.select_treatment, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                //Store these values in a string eg. 1,3,4
                String conditionName = getIntent().getStringExtra("EXTRA_CONDITION_NAME");

                String filename = getApplicationContext().getFilesDir().getPath() + "/userHistory";
                //File file = new File("userHistory.txt");
                String fileContents = conditionName + ",.," + "0" + ",.," + name + ",.," + treatmentNumber + "_";
                FileOutputStream outputStream = null;

                try {
                    outputStream = new FileOutputStream(filename, true);
                    outputStream.write(fileContents.getBytes());

                    Toast.makeText(getApplicationContext(),
                            "Treatment selected", Toast.LENGTH_SHORT)
                            .show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException ioe) {
                    Log.e(TAG, "Error writing to file");
                } finally {
                    try {
                        if (outputStream != null){
                            outputStream.close();
                        }
                    } catch (IOException ioe) {
                        Log.e(TAG, "Error closing stream");
                    }
                }

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();

    }


    private void configureBackBtn() {
        Button backBtn = findViewById(R.id.back_btn3);
        backBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });

    }


}
