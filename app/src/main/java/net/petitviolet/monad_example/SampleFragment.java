package net.petitviolet.monad_example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import net.petitviolet.monad.list.ListM;
import net.petitviolet.monad.list.Tuple;

import java.util.ArrayList;
import java.util.List;

public class SampleFragment extends Fragment {
    private Button mAddFormButton;
    private TextView mSumTextView;
    private TextView mIncomeTextView;
    private TextView mOutcomeTextView;
    private ListM<View> mFormLayoutList;

    public SampleFragment() {
    }

    public static SampleFragment newInstance() {
        SampleFragment fragment = new SampleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plus_one, container, false);
        mAddFormButton = (Button) view.findViewById(R.id.button_add_form);
        mSumTextView = (TextView) view.findViewById(R.id.textview_sum);
        mIncomeTextView = (TextView) view.findViewById(R.id.textview_income);
        mOutcomeTextView = (TextView) view.findViewById(R.id.textview_outcome);
        final ViewGroup formContainer = (ViewGroup) view.findViewById(R.id.form_container);
        mFormLayoutList = ListM.of(formContainer);

        mAddFormButton.setOnClickListener(v -> {
            Tuple<ListM<View>, ListM<View>> partitionLayouts = mFormLayoutList.partition(layout -> ((CheckBox) layout.findViewById(R.id.select_plus_or_minus)).isChecked());

            int income = partitionLayouts.fst
                    .map(layout -> ((EditText) layout.findViewById(R.id.input_number)).getText().toString())
                    .filter(s -> !TextUtils.isEmpty(s))
                    .map(Integer::parseInt)
                    .foldLeft(0, (acc, i) -> acc + i);

            int outcome = partitionLayouts.snd
                    .map(layout -> ((EditText) layout.findViewById(R.id.input_number)).getText().toString())
                    .filter(s -> !TextUtils.isEmpty(s))
                    .map(Integer::parseInt)
                    .foldRight((acc, i) -> acc + i, 0);

            mIncomeTextView.setText("￥" + income);
            mOutcomeTextView.setText("￥ -" + outcome);
            mSumTextView.setText("￥" + (income - outcome));

            View formLayout = inflater.inflate(R.layout.input_column, container, false);
            formContainer.addView(formLayout);
            mFormLayoutList.add(formLayout);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
