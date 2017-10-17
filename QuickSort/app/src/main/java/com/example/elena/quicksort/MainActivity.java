package com.example.elena.quicksort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Integer> mSortedValues, mInputValues;
    private static SortedListAdapter mAdapter;
    private static EditText mInputEditText;
    private static TextView mAddedValues;
    private static final String BUNDLE_INPUT_VALUES = "input-values";
    private static final String BUNDLE_SORTED_VALUES= "sorted-values";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        mInputEditText = (EditText) findViewById(R.id.edit_number);
        mAddedValues = (TextView) findViewById(R.id.text_added_values);
        mInputValues = new ArrayList<>();
        mSortedValues = new ArrayList<>();

        if (savedInstanceState!=null){
            if(savedInstanceState.containsKey(BUNDLE_INPUT_VALUES)){
                mInputValues = savedInstanceState.getIntegerArrayList(BUNDLE_INPUT_VALUES);
            }
            if (savedInstanceState.containsKey(BUNDLE_SORTED_VALUES)){
                mSortedValues = savedInstanceState.getIntegerArrayList(BUNDLE_SORTED_VALUES);
            }
            setTexts();
        }
    }

    private void setTexts(){
        mAdapter.setData(mSortedValues);
        if (mInputValues.size() != 0) mAddedValues.setText(mInputValues.get(0)+"");
        for (int i=1; i<mInputValues.size(); i++){
            mAddedValues.append(", "+mInputValues.get(i));
        }
    }

    public void setupRecyclerView(){
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_sorted);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SortedListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void reset(View view) {
        resetData();
    }

    public static void resetData(){
        mInputEditText.clearComposingText();
        mAdapter.setData(null);
        mInputValues.clear();
        mSortedValues.clear();
        mAddedValues.setText("");
    }

    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        int pivot = mSortedValues.get(lowerIndex+(higherIndex-lowerIndex)/2);
        while (i <= j) {
            while (mSortedValues.get(i) < pivot) {
                i++;
            }
            while (mSortedValues.get(j) > pivot) {
                j--;
            }
            if (i <= j) {
                int valueI = mSortedValues.get(i);
                int valueJ  = mSortedValues.get(j);
                mSortedValues.set(i, valueJ);
                mSortedValues.set(j, valueI);
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

    public void sortValues(View view) {
        mSortedValues= mInputValues;
        if (mSortedValues!= null && mSortedValues.size()>0){
            quickSort(0, mSortedValues.size()-1);
            NotificationUtils.displayNotification(this);
        }

        mAdapter.setData(mSortedValues);

    }

    public void addValueToUnsortedList(View view) {

        if (mInputEditText.getText() != null && mInputEditText.getText().toString().length()>0){
            mInputValues.add(Integer.valueOf(mInputEditText.getText().toString()));
            if (mInputValues.size() == 1){
                mAddedValues.setText(mInputEditText.getText().toString());
            }else{
                mAddedValues.append(", "+mInputEditText.getText().toString());
            }
            mInputEditText.setText("");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(BUNDLE_INPUT_VALUES,(ArrayList<Integer>)mInputValues);
        outState.putIntegerArrayList(BUNDLE_SORTED_VALUES,(ArrayList<Integer>)mSortedValues);
    }
}
