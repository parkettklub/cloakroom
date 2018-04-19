package parkettklub.smartcheckroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Date;

import parkettklub.smartcheckroom.data.CheckroomItem;
import parkettklub.smartcheckroom.data.CheckroomTransaction;
import parkettklub.smartcheckroom.data.ManageDB;

public class ItemHandlingActivity extends AppCompatActivity {

    public static final String KEY_BARCODE_NUMBER = "KEY_BARCODE_NUMBER";

    // UI
    private TextView editBarcodeNumber;
    private Spinner spnrCheckroomItemNumber;

    private TextView editCoatNumber;
    private Button incCoat;
    private Button decCoat;

    private TextView tvBag;
    private TextView tvShoe;
    private TextView tvOther;

    private String barcodeNumber;

    private Integer coatNum;
    private Integer bagNum;
    private Integer shoeNum;
    private Integer otherNum;

    private Boolean newItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_handling);

        final String myString;

        Intent intent = getIntent();
        barcodeNumber = intent.getStringExtra(KEY_BARCODE_NUMBER);

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();


        Long checkroomNumber = ManageDB.getInstance().findItem(barcodeNumber);
        if(checkroomNumber == null) {
            newItem = true;
        }
        else
        {
            newItem = false;
        }

        // UI elem referenciak elkerese
        editBarcodeNumber = (TextView) findViewById(R.id.barcodeNumber);
        editBarcodeNumber.setText(barcodeNumber);

        spnrCheckroomItemNumber = (Spinner) findViewById(R.id.checkroomNumber);

        if(newItem)
        {
            //Long[] numbers = ManageDB.getInstance().getFreeIds(0);
            ArrayList<Long> numbers = ManageDB.getInstance().getFreeIds(0);
            spnrCheckroomItemNumber.setAdapter(new ArrayAdapter<Long>(this,
                    android.R.layout.simple_spinner_item, numbers));

            coatNum = 0;
            bagNum = 0;
            shoeNum = 0;
            otherNum = 0;
        }
        else
        {
            CheckroomItem oldItem = CheckroomItem.findById(CheckroomItem.class, checkroomNumber);

            Long[] numbers = new Long[1];
            numbers [0] = checkroomNumber;

            spnrCheckroomItemNumber.setAdapter(new ArrayAdapter<Long>(this,
                    android.R.layout.simple_spinner_item, numbers));

            coatNum = oldItem.getDueCoatNumber();
            bagNum = oldItem.getDueBagNumber();
            shoeNum = oldItem.getDueShoeNumber();
            otherNum = oldItem.getDueOtherNumber();
        }

        editCoatNumber = (TextView) findViewById(R.id.coatNum);
        editCoatNumber.setText(coatNum.toString());

        incCoat = (Button) findViewById(R.id.btnCoatPlus);
        incCoat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                coatNum++;
                editCoatNumber.setText(coatNum.toString());
            }
        });

        decCoat = (Button) findViewById(R.id.btnCoatMinus);
        decCoat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(coatNum != 0)
                {
                    coatNum--;
                    editCoatNumber.setText(coatNum.toString());
                }
            }
        });

        tvBag = findViewById(R.id.tvBag);
        tvBag.setText(getString(R.string.bagNum, bagNum));
        tvBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bagNum++;
                tvBag.setText(getString(R.string.bagNum, bagNum));
            }
        });
        tvBag.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                bagNum = 0;
                tvBag.setText(getString(R.string.bagNum, bagNum));

                return true;
            }
        });

        tvShoe = findViewById(R.id.tvShoe);
        tvShoe.setText(getString(R.string.shoeNum, shoeNum));
        tvShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoeNum++;
                tvShoe.setText(getString(R.string.shoeNum, shoeNum));
            }
        });
        tvShoe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                shoeNum = 0;
                tvShoe.setText(getString(R.string.shoeNum, shoeNum));

                return true;
            }
        });

        tvOther = findViewById(R.id.tvOther);
        tvOther.setText(getString(R.string.otherNum, otherNum));
        tvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherNum++;
                tvOther.setText(getString(R.string.otherNum, otherNum));
            }
        });
        tvOther.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                otherNum = 0;
                tvOther.setText(getString(R.string.otherNum, otherNum));

                return true;
            }
        });



        // A gombok esemenykezeloinek beallitasa
        Button btnOk = (Button) findViewById(R.id.btnCreateCheckroomItem);
        if(newItem) {
            btnOk.setText("ADD");
        }
        else
        {
            btnOk.setText("DELETE");
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* add Checkroom Item */
                //Toast.makeText(getContext(), String.valueOf(spnrCheckroomItemNumber.getSelectedItem()), Toast.LENGTH_LONG).show();

                CheckroomItem item = CheckroomItem.findById(CheckroomItem.class, (Long) spnrCheckroomItemNumber.getSelectedItem());

                item.setDueReserved(newItem);

                if(newItem)
                {
                    item.setDueBarcodeNumber(barcodeNumber);
                }
                else
                {
                    item.setDueBarcodeNumber("");
                }

                item.setDueCoatNumber(coatNum);
                item.setDueBagNumber(bagNum);
                item.setDueShoeNumber(shoeNum);
                item.setDueOtherNumber(otherNum);
                item.setDueDate(new Date(System.currentTimeMillis()));

                //CheckroomItem item = new CheckroomItem(true, spnrCheckroomItemNumber.getSelectedItem(), mParam1,
                //        new Date(System.currentTimeMillis()));
                item.save();

                if(newItem) {

                    Integer allThings = item.getDueCoatNumber() + item.getDueBagNumber() + item.getDueShoeNumber() + item.getDueOtherNumber();

                    CheckroomTransaction newTransaction = new CheckroomTransaction(
                            "ADD", item.getDueBarcodeNumber(), item.getId(), allThings,
                            new Date(System.currentTimeMillis()));

                    newTransaction.save();

                    Toast.makeText(ItemHandlingActivity.this, "Added", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Integer allThings = item.getDueCoatNumber() + item.getDueBagNumber() + item.getDueShoeNumber() + item.getDueOtherNumber();

                    CheckroomTransaction newTransaction = new CheckroomTransaction(
                            "DELETE", item.getDueBarcodeNumber(), item.getId(), allThings,
                            new Date(System.currentTimeMillis()));

                    newTransaction.save();

                    Toast.makeText(ItemHandlingActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });

        Button btnUpdate = (Button) findViewById(R.id.btnUpdateCheckroomItem );
        if(newItem) {
            btnUpdate.setVisibility(View.INVISIBLE);
        }
        else
        {
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    CheckroomItem item = CheckroomItem.findById(CheckroomItem.class, (Long) spnrCheckroomItemNumber.getSelectedItem());

                    item.setDueReserved(true);

                    item.setDueCoatNumber(coatNum);
                    item.setDueCoatNumber(coatNum);
                    item.setDueBagNumber(bagNum);
                    item.setDueShoeNumber(shoeNum);
                    item.setDueOtherNumber(otherNum);

                    item.setDueDate(new Date(System.currentTimeMillis()));

                    //CheckroomItem item = new CheckroomItem(true, spnrCheckroomItemNumber.getSelectedItem(), mParam1,
                    //        new Date(System.currentTimeMillis()));
                    item.save();

                    Integer allThings = item.getDueCoatNumber() + item.getDueBagNumber() + item.getDueShoeNumber() + item.getDueOtherNumber();

                    CheckroomTransaction newTransaction = new CheckroomTransaction(
                            "UPDATE", item.getDueBarcodeNumber(), item.getId(), allThings,
                            new Date(System.currentTimeMillis()));

                    newTransaction.save();

                    Toast.makeText(ItemHandlingActivity.this, "Updated", Toast.LENGTH_LONG).show();

                    finish();
                }
            });
        }


        Button btnCancel = (Button) findViewById(R.id.btnCancelCreateCheckroomItem);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }


}

