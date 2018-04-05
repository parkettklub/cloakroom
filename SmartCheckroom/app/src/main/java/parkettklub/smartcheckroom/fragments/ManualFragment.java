package parkettklub.smartcheckroom.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import parkettklub.smartcheckroom.R;
import parkettklub.smartcheckroom.data.CheckroomItem;
import parkettklub.smartcheckroom.data.ManageDB;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManualFragment extends Fragment implements View.OnClickListener  {

    View v;

    private String barCode ="";

    EditText callEditText;

    ImageButton callBackSpaceButton;

    Button callButton;

    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button zero;


    public ManualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_manual, container, false);

        callEditText = (EditText) view.findViewById(R.id.callEditText);
        //myText.setText("hello");

        callBackSpaceButton = (ImageButton) view.findViewById(R.id.callBackSpaceButton);
        callBackSpaceButton.setOnClickListener(this);

        Button one = (Button) view.findViewById(R.id.one);
        one.setOnClickListener(this);
        Button two = (Button) view.findViewById(R.id.two);
        two.setOnClickListener(this);
        Button three = (Button) view.findViewById(R.id.three);
        three.setOnClickListener(this);
        Button four = (Button) view.findViewById(R.id.four);
        four.setOnClickListener(this);
        Button five = (Button) view.findViewById(R.id.five);
        five.setOnClickListener(this);
        Button six = (Button) view.findViewById(R.id.six);
        six.setOnClickListener(this);
        Button seven = (Button) view.findViewById(R.id.seven);
        seven.setOnClickListener(this);
        Button eight = (Button) view.findViewById(R.id.eight);
        eight.setOnClickListener(this);
        Button nine = (Button) view.findViewById(R.id.nine);
        nine.setOnClickListener(this);
        Button zero = (Button) view.findViewById(R.id.zero);
        zero.setOnClickListener(this);
        Button hash = (Button) view.findViewById(R.id.hash);
        hash.setOnClickListener(this);
        Button call_button = (Button) view.findViewById(R.id.call_button);
        call_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.callBackSpaceButton:{
                if(barCode.length() > 0) {
                    barCode = barCode.substring(0, barCode.length() - 1);
                }
                break;
            }

            case R.id.one:{
                barCode = barCode + "1";
                break;
            }

            case R.id.two:{
                barCode = barCode + "2";
                break;
            }

            case R.id.three:{
                barCode = barCode + "3";
                break;
            }

            case R.id.four:{
                barCode = barCode + "4";
                break;
            }

            case R.id.five:{
                barCode = barCode + "5";
                break;
            }

            case R.id.six:{
                barCode = barCode + "6";
                break;
            }

            case R.id.seven:{
                barCode = barCode + "7";
                break;
            }

            case R.id.eight:{
                barCode = barCode + "8";
                break;
            }

            case R.id.nine:{
                barCode = barCode + "9";
                break;
            }

            /*
            case R.id.star:{
                phoneNumber.concat("*");
                break;
            }
               */
            case R.id.hash:{
                ManageDB.getInstance().deleteAllCheckRoomItems();
                break;
            }


            case R.id.zero:{
                barCode = barCode + "0";
                break;
            }

            case R.id.call_button: {
                if(ManageDB.getInstance().findItem(barCode))
                {
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();
                }
                else
                {
                    /*
                    ManageDB.getInstance().createNewDayItem(barCode);
                    Toast.makeText(getContext(), "Added", Toast.LENGTH_LONG).show();
                    */
                    CheckroomItemCreateFragment createFragment = CheckroomItemCreateFragment.newInstance(barCode,"meeh");
                    android.support.v4.app.FragmentManager fm = getFragmentManager();
                    createFragment.show(fm, CheckroomItemCreateFragment.TAG);
                }
                barCode = "";
                break;
            }

            default:
                break;
        }
        callEditText.setText(barCode);

        // "Reading" the text (printing it to stdout):
        System.out.println( callEditText.getText() );
        //System.out.println(phoneNumber);
    }

}
