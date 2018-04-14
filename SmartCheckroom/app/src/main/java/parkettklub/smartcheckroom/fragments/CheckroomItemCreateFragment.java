package parkettklub.smartcheckroom.fragments;

/**
 * Created by Badbeloved on 2018. 04. 14..
 */

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import parkettklub.smartcheckroom.R;
import parkettklub.smartcheckroom.data.CheckroomItem;
import parkettklub.smartcheckroom.data.ManageDB;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckroomItemCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckroomItemCreateFragment extends DialogFragment {

    // Log tag
    public static final String TAG = "CheckroomItemCreateFragment";

    // UI
    private TextView editBarcodeNumber;
    private Spinner spnrCheckroomItemNumber;

    private TextView editCoatNumber;
    private Button incCoat;
    private Button decCoat;

    private static Integer coatNum;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public CheckroomItemCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CheckroomItemCreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckroomItemCreateFragment newInstance(String param1) {
        CheckroomItemCreateFragment fragment = new CheckroomItemCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checkroom_item_create, container, false);

        // Dialog cimenek beallitasa
        getDialog().setTitle("Dialog Fragment");

        // UI elem referenciak elkerese
        editBarcodeNumber = (TextView) root.findViewById(R.id.barcodeNumber);
        editBarcodeNumber.setText(mParam1);

        spnrCheckroomItemNumber = (Spinner) root.findViewById(R.id.checkroomNumber);

        Long[] numbers = ManageDB.getInstance().getFreeIds(0);
        /*
        numbers[0] = ManageDB.getInstance().getCheckroomNumber();
        numbers[1] = ManageDB.getInstance().getCheckroomNumber()+2;
        numbers[2] = ManageDB.getInstance().getCheckroomNumber()+4;
        */

        spnrCheckroomItemNumber.setAdapter(new ArrayAdapter<Long>(getActivity(),
                android.R.layout.simple_spinner_item, numbers));

        coatNum = 0;

        editCoatNumber = (TextView) root.findViewById(R.id.coatNum);
        editCoatNumber.setText("0");

        incCoat = (Button) root.findViewById(R.id.btnCoatPlus);
        incCoat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                coatNum++;
                editCoatNumber.setText(coatNum.toString());
            }
        });

        decCoat = (Button) root.findViewById(R.id.btnCoatMinus);
        decCoat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(coatNum != 0)
                {
                    coatNum--;
                    editCoatNumber.setText(coatNum.toString());
                }
            }
        });

        // A gombok esemenykezeloinek beallitasa
        Button btnOk = (Button) root.findViewById(R.id.btnCreateCheckroomItem);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* add Checkroom Item */
                //Toast.makeText(getContext(), String.valueOf(spnrCheckroomItemNumber.getSelectedItem()), Toast.LENGTH_LONG).show();

                CheckroomItem item = CheckroomItem.findById(CheckroomItem.class, (Long) spnrCheckroomItemNumber.getSelectedItem());

                item.setDueReserved(true);
                item.setDueBarcodeNumber(mParam1);
                item.setDueCoatNumber(coatNum);
                item.setDueDate(new Date(System.currentTimeMillis()));

                //CheckroomItem item = new CheckroomItem(true, spnrCheckroomItemNumber.getSelectedItem(), mParam1,
                //        new Date(System.currentTimeMillis()));
                item.save();

                Toast.makeText(getContext(), String.valueOf(item.getId()), Toast.LENGTH_LONG).show();


                getActivity().finish();
            }
        });

        Button btnCancel = (Button) root.findViewById(R.id.btnCancelCreateCheckroomItem);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return root;
    }

}
