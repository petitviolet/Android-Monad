package net.petitviolet.monad_example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SampleFragment extends Fragment {
    private Button mAddFormButton;
    private TextView mSumTextView;
    private TextView mIncomeTextView;
    private TextView mOutcomeTextView;
    private List<View> mFormLayoutList;

    public SampleFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SampleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SampleFragment newInstance(String param1, String param2) {
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
        mFormLayoutList = new ArrayList<>();
        final ViewGroup formContainer = (ViewGroup) view.findViewById(R.id.form_container);

        mAddFormButton.setOnClickListener(v -> {
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
