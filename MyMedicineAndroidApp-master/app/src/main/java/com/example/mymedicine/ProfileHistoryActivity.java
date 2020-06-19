package com.example.mymedicine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymedicine.MongoServices.MongoRetrofitClient;
import com.example.mymedicine.MongoServices.MongoService;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ProfileHistoryActivity extends AppCompatActivity {

    private static String TAG = "MyActivity";

    private static String[] userProfileValues = new String[7];

    private static String[] userHistoryValues;

    static CompositeDisposable compositeDisposable = new CompositeDisposable();


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_history);

        loadProfile();
        loadHistory();
        //loadId();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    private void loadProfile() {
        //File directory = this.getFilesDir();
        //File file = new File(directory, "userProfile");

        String profileInput = "";
        try {
            // Read File and Content
            FileInputStream fileInputStream = openFileInput("userProfile");
            int size;


            // read inside if it is not null (-1 means empty)
            while ((size = fileInputStream.read()) != -1) {
                // add & append content
                profileInput += Character.toString((char) size);
            }
        } catch (Exception e) {
            //Exception
        }

        Log.d(TAG, "PROFILE INPUT: " + profileInput);
        //profileInput = "";


        if (profileInput == "") {
            userProfileValues[0] = "0";
            userProfileValues[1] = "0";
            userProfileValues[2] = "0";
            userProfileValues[3] = "0";
            userProfileValues[4] = "0";
            userProfileValues[5] = "0";
            userProfileValues[6] = "0";

            try {
                Retrofit retrofitClient = MongoRetrofitClient.getInstance();

                MongoService mongoService = retrofitClient.create(MongoService.class);

                compositeDisposable.add(mongoService.createId(0, 0, 0, 0)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String response) throws Exception {
                                Log.d(TAG, "Successfully sent to it");
                                userProfileValues[6] = response;

                                Log.d(TAG, "USER ID -- " + userProfileValues[6]);

                            }
                        }));
            } catch (Exception e){
                e.printStackTrace();
            }

            String filename = "userProfile";
            String fileContents = "1" + "," + "1" + "," + "1900" + "," + "0" + "," + "0" + "," + "0" + "," + userProfileValues[6];
            FileOutputStream outputStream;

            try {
                outputStream =  this.openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(fileContents.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            userProfileValues = profileInput.split(",");

            Log.d(TAG, "PROFILE DETAILS: " + profileInput);

        }
    }

    private void loadHistory(){
        String historyInput = "";
        try {
            // Read File and Content
            FileInputStream fileInputStream = openFileInput("userHistory");
            int size;


            // read inside if it is not null (-1 means empty)
            while ((size = fileInputStream.read()) != -1) {
                // add & append content
                historyInput += Character.toString((char) size);
            }
        } catch (Exception e) {
            //Exception
        }

        if (!historyInput.equals("")) {
            userHistoryValues = historyInput.split("_");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class HistoryFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public HistoryFragment() {

        }

        public static HistoryFragment newInstance(int sectionNumber) {
            HistoryFragment fragment = new HistoryFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.history_tab_fragment, container, false);
            if (userHistoryValues != null) {
                configureHistoryLayout(rootView);
            }

            Button submitProfileBtn = rootView.findViewById(R.id.submit_history_btn);
            submitProfileBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    LinearLayout layout = rootView.findViewById(R.id.history_layout);

                    int count = layout.getChildCount();

                    TextView txt = null;
                    Spinner spinner = null;
                    String treatNo = "";
                    String condName = "";
                    String treatName = "";
                    String spin;
                    boolean spinning = false;

                    String historyOutput = "";
                    String tempOutput = "";

                    String condNames = "";
                    String treatNos = "";
                    String treatReviews = "";


                    for (int i = 1; i < count; i++) {

                        try {
                            txt = (TextView) ((LinearLayout) layout).getChildAt(i);
                        } catch (ClassCastException cce){
                            spinning = true;
                        }

                        if (!spinning) {
                            txt = (TextView) ((LinearLayout) layout).getChildAt(i);
                            Log.d(TAG, "LOOP AT " + Integer.toString(i));
                            treatNo = txt.getTag(R.string.treatment_no_store).toString();
                            condName = txt.getTag(R.string.condition_name_store).toString();
                            treatName = txt.getTag(R.string.treatment_name_store).toString();
                            spin = txt.getTag(R.string.treatment_review_store).toString();

                            if (spin.equals("0")) {
                                spinning = true;
                            } else {
                                tempOutput += condName + ",.," + spin + ",.," + treatName + ",.," + treatNo + "_";
                            }

                        } else {

                            spinner = (Spinner) ((LinearLayout) layout).getChildAt(i);
                            spin = Integer.toString(spinner.getSelectedItemPosition());
                            tempOutput += condName + ",.," + spin + ",.," + treatName + ",.," + treatNo + "_";

                            Log.d(TAG, "TEMPOUTPUT " + tempOutput);

                            if (!spin.equals("0")) {
                                condNames += condName + ",";
                                treatNos += treatNo + ",";
                                treatReviews += spin + ",";
                            }
                            spinning = false;
                        }

                        historyOutput += tempOutput;
                    }

                    historyOutput = tempOutput;

                    //Store these values in a string eg. 1,3,4

                    String filename = "userHistory";
                    FileOutputStream outputStream;

                    try {
                        outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(historyOutput.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "CONDNAMES: " + condNames);

                    if (!condNames.equals("")) {

                        try {

                            Retrofit retrofitClient = MongoRetrofitClient.getInstance();

                            MongoService mongoService = retrofitClient.create(MongoService.class);

                            //Create the treatments if they dont already exist
                            compositeDisposable.add(mongoService.sendTreatment(condNames, treatNos)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<String>() {

                                        @Override
                                        public void accept(String response) {
                                            Log.d(TAG, "RESPONSE - " + response);
                                        }

                                    }));

                            compositeDisposable.add(mongoService.sendTreatmentReview(userProfileValues[6], condNames, treatNos, treatReviews)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<String>() {

                                        @Override
                                        public void accept(String response) throws Exception {
                                            Log.d(TAG, "RESPONSE2 - " + response);
                                        }
                                    }));

                        } catch (Exception e){
                            Log.d(TAG, "Exception XXXXXXX" + e);
                        }
                    }

                    Intent refresh = new Intent(getActivity(), ProfileHistoryActivity.class);
                    refresh.putExtra("EXTRA_CONNECTION_ERROR", "error");
                    startActivity(refresh);//Start the same Activity


                    getActivity().finish(); //finish Activity.
                }
            });

            return rootView;
        }

        private void configureHistoryLayout(View view) {

            String[] historySingleSplit;
            String conditionName;
            String treatmentReview;
            String treatmentName;
            String treatmentNo;
            String txtviewTextDisplay;
            //int i = 0;
            String historySingle;
            int arrayLength = userHistoryValues.length;

            for(int i = arrayLength - 1; i >= 0; i--){

                historySingle = userHistoryValues[i];

            //for (String historySingle : userHistoryValues) {
                if (!historySingle.equals("")) {
                    LinearLayout treatmentsDisplay = (LinearLayout) view.findViewById(R.id.history_layout);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    historySingleSplit = historySingle.split(",.,");

                    Log.d(TAG, historySingle);
                    conditionName = historySingleSplit[0];
                    treatmentReview = historySingleSplit[1];
                    treatmentName = historySingleSplit[2];
                    treatmentNo = historySingleSplit[3];

                    if (treatmentReview.equals("0")) {

                        final TextView txtview = new TextView(view.getContext());
                        txtview.setId(i);
                        txtview.setPadding(5, 30, 30, 2);

                        txtviewTextDisplay = conditionName + " - " + treatmentName;
                        txtview.setText(txtviewTextDisplay);
                        if (i % 2 == 0) {
                            txtview.setBackgroundColor(Color.parseColor("#b2fab4"));
                        } else {
                            txtview.setBackgroundColor(Color.parseColor("#519657"));
                        }

                        txtview.setTag(R.string.treatment_no_store, treatmentNo);
                        txtview.setTag(R.string.condition_name_store, conditionName);
                        txtview.setTag(R.string.treatment_name_store, treatmentName);
                        //Tag for  if there is a spinner following
                        txtview.setTag(R.string.treatment_review_store, "0");

                        //txtview.setClickable(true);
                        treatmentsDisplay.addView(txtview, params);

                        //i++;

                        Spinner spinner = new Spinner(getContext());
                        spinner.setId(i);
                        spinner.setLayoutParams(params);
                        String[] treatmentReviewLevels = {"N/A", "1", "2", "3", "4", "5"};
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, treatmentReviewLevels);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(arrayAdapter);
                        spinner.setSelection(0);
                        treatmentsDisplay.addView(spinner);

                    } else {
                        final TextView txtview = new TextView(view.getContext());
                        txtview.setId(i);
                        txtview.setPadding(5, 30, 30, 2);

                        txtviewTextDisplay = conditionName + " - " + treatmentName + ": " + treatmentReview + "/5";
                        txtview.setText(txtviewTextDisplay);
                        if (i % 2 == 0) {
                            txtview.setBackgroundColor(Color.parseColor("#b2fab4"));
                        } else {
                            txtview.setBackgroundColor(Color.parseColor("#519657"));
                        }

                        txtview.setTag(R.string.treatment_no_store, treatmentNo);
                        txtview.setTag(R.string.condition_name_store, conditionName);
                        txtview.setTag(R.string.treatment_name_store, treatmentName);
                        //Tag for  if there is a spinner following
                        txtview.setTag(R.string.treatment_review_store, treatmentReview);
                        treatmentsDisplay.addView(txtview, params);
                    }

                    //i++;
                }

            }
        }

    }


    public static class ProfileFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public ProfileFragment() {

        }

        public static ProfileFragment newInstance(int sectionNumber) {
            ProfileFragment fragment = new ProfileFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.profile_tab_fragment, container, false);

            configureSpinner(rootView);

            Button submitProfileBtn = rootView.findViewById(R.id.submit_profile_btn);
            submitProfileBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here

                    DatePicker dobPicker = rootView.findViewById(R.id.dob_picker);
                    String dobMonth = Integer.toString(dobPicker.getMonth() + 1);

                    //Get position values of all spinners
                    Spinner genderSpinner = rootView.findViewById(R.id.gender_spinner);
                    int genderOutput = genderSpinner.getSelectedItemPosition();

                    Spinner activitySpinner = rootView.findViewById(R.id.activity_level_spinner);
                    int activityOutput = activitySpinner.getSelectedItemPosition();

                    Spinner stressSpinner = rootView.findViewById(R.id.stress_level_spinner);
                    int stressOutput = stressSpinner.getSelectedItemPosition();

                    //Store these values in a string eg. 1,3,4

                    int age = -1;
                    int gender = -1;
                    int activity = -1;
                    int stress = -1;
                    String userId = userProfileValues[6];

                    if (dobPicker.getYear() != 1900) {
                        age = Calendar.getInstance().get(Calendar.YEAR) - dobPicker.getYear(); //year 2, gender 3, activity 4, stress 5
                    }
                    if (genderOutput != 0) {
                        gender = genderOutput;
                    }
                    if (activityOutput != 0) {
                        activity = activityOutput;
                    }
                    if (stressOutput != 0) {
                        stress = stressOutput;
                    }

                    try {
                        Retrofit retrofitClient = MongoRetrofitClient.getInstance();

                        MongoService mongoService = retrofitClient.create(MongoService.class);

                        compositeDisposable.add(mongoService.updateUser(userId, age, gender, activity, stress)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<String>() {

                                    @Override
                                    public void accept(String response) {
                                        Log.d(TAG, "RESPONSE - " + response);
                                    }
                                }));
                    } catch(Exception e){
                        Log.d(TAG, "ERROR " + e);
                    }


                    String filename = "userProfile";
                    String fileContents = dobPicker.getDayOfMonth() + "," + dobPicker.getMonth() + "," + dobPicker.getYear() + "," + Integer.toString(genderOutput) + "," + Integer.toString(activityOutput) + "," + Integer.toString(stressOutput) + "," + userProfileValues[6];
                    FileOutputStream outputStream;

                    try {
                        outputStream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(fileContents.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getActivity(),
                            "Profile changes submitted", Toast.LENGTH_SHORT)
                            .show();

                }
            });

            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            super.onSaveInstanceState(savedInstanceState);
        }

        private void configureSpinner(View view){
            DatePicker dobPicker = view.findViewById(R.id.dob_picker);
            Calendar calendar = Calendar.getInstance();//get the current day
            dobPicker.setMaxDate(calendar.getTimeInMillis());
            int year = Integer.parseInt(userProfileValues[2]);
            int month = Integer.parseInt(userProfileValues[1]);
            int day = Integer.parseInt(userProfileValues[0]);
            dobPicker.updateDate(year, month, day);

            Spinner genderSpinner = view.findViewById(R.id.gender_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.gender_options_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            genderSpinner.setAdapter(genderAdapter);
            //Set spinner selection
            genderSpinner.setSelection(Integer.parseInt(userProfileValues[3]));

            Spinner activitySpinner = view.findViewById(R.id.activity_level_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.activity_level_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            activitySpinner.setAdapter(activityAdapter);
            //Set spinner selection
            activitySpinner.setSelection(Integer.parseInt(userProfileValues[4]));

            Spinner stressSpinner = view.findViewById(R.id.stress_level_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> stressAdapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.stress_level_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            stressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            stressSpinner.setAdapter(stressAdapter);
            //Set spinner selection
            stressSpinner.setSelection(Integer.parseInt(userProfileValues[5]));
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return HistoryFragment.newInstance(position + 1);
            } else {
                return ProfileFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

}

/*
                List<Document> treatmentReviews = new ArrayList<Document>();
                Document newTreatmentReview;


                if (user does not exist in database){
                    newPerson = new Document("_id", UserId)
                            .append("age", age)
                            .append("gender", gender)
                            .append("activity", activity)
                            .append("stress", stress)
                }


                for (int i = 0; i < condNames.size(); i++) {
                    Log.d(TAG, "SEND TO DB: " + condNames.get(i) + treatNos.get(i) + treatReviews.get(i));

                    newTreatmentUsage = new Document("condition", condNames.get(i))
                            .append("treatment", treatNos.get(i))

                    newTreatmentReview = new Document("_id", treatmentId)
                            .append(userID, userId)
                            .append("treatmentReview", treatReviews.get(i));

                    //collection.insert(newTreatmentReview);

                    treatmentReviews.add(newTreatmentReview);
                }

                collection.insertMany(treatmentReviews);
                mongoClient.close();
                  */



