package parkettklub.smartcheckroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import parkettklub.smartcheckroom.core.Core;
import parkettklub.smartcheckroom.core.driver.dbdriver.CheckroomItem;
import parkettklub.smartcheckroom.core.driver.dbdriver.CheckroomTransaction;
import parkettklub.smartcheckroom.core.driver.dbdriver.ManageDB;

public class ItemHandlingActivity extends AppCompatActivity {

    public static final String KEY_BARCODE_NUMBER = "KEY_BARCODE_NUMBER";

    // UI
    private TextView editBarcodeNumber;
    private NumberPicker npCheckroomNumber;

    private TextView editCoatNumber;
    private Button incCoat;
    private Button decCoat;

    private TextView tvBag;
    private TextView tvShoe;
    private TextView tvOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_handling);

        Intent intent = getIntent();

        Core.barcodeNumber = intent.getStringExtra(KEY_BARCODE_NUMBER);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Core.init();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // UI elem referenciak elkerese
                        editBarcodeNumber = (TextView) findViewById(R.id.barcodeNumber);
                        editBarcodeNumber.setText(Core.barcodeNumber);

                        npCheckroomNumber = (NumberPicker) findViewById(R.id.npCheckroomNum);

                        npCheckroomNumber.setMinValue(0);
                        npCheckroomNumber.setMaxValue(Core.numbers.size()-1);

                        //Specify the NumberPicker data source as array elements
                        npCheckroomNumber.setDisplayedValues(Core.values);

                        npCheckroomNumber.setWrapSelectorWheel(false);

                        editCoatNumber = (TextView) findViewById(R.id.coatNum);
                        editCoatNumber.setText(Core.coatNum.toString());

                        incCoat = (Button) findViewById(R.id.btnCoatPlus);
                        incCoat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Core.coatNum++;
                                editCoatNumber.setText(Core.coatNum.toString());
                            }
                        });

                        decCoat = (Button) findViewById(R.id.btnCoatMinus);
                        decCoat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(Core.coatNum != 0)
                                {
                                    Core.coatNum--;
                                    editCoatNumber.setText(Core.coatNum.toString());
                                }
                            }
                        });

                        tvBag = findViewById(R.id.tvBag);
                        tvBag.setText(getString(R.string.bagNum, Core.bagNum));
                        tvBag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Core.bagNum++;
                                tvBag.setText(getString(R.string.bagNum, Core.bagNum));
                            }
                        });
                        tvBag.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                Core.bagNum = 0;
                                tvBag.setText(getString(R.string.bagNum, Core.bagNum));

                                return true;
                            }
                        });

                        tvShoe = findViewById(R.id.tvShoe);
                        tvShoe.setText(getString(R.string.shoeNum, Core.shoeNum));
                        tvShoe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Core.shoeNum++;
                                tvShoe.setText(getString(R.string.shoeNum, Core.shoeNum));
                            }
                        });
                        tvShoe.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {

                                Core.shoeNum = 0;
                                tvShoe.setText(getString(R.string.shoeNum, Core.shoeNum));

                                return true;
                            }
                        });

                        tvOther = findViewById(R.id.tvOther);
                        tvOther.setText(getString(R.string.otherNum, Core.otherNum));
                        tvOther.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Core.otherNum++;
                                tvOther.setText(getString(R.string.otherNum, Core.otherNum));
                            }
                        });
                        tvOther.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                Core.otherNum = 0;
                                tvOther.setText(getString(R.string.otherNum, Core.otherNum));

                                return true;
                            }
                        });


                        // A gombok esemenykezeloinek beallitasa
                        Button btnOk = (Button) findViewById(R.id.btnCreateCheckroomItem);
                        if(Core.newItem) {
                            btnOk.setText("ADD");
                        }
                        else
                        {
                            btnOk.setText("DELETE");
                        }
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                if(Core.newItem) {
                                    Core.handleItem(Long.valueOf(npCheckroomNumber.getValue()), "ADDED");
                                    //Core.handleItem((Long) spnrCheckroomItemNumber.getSelectedItem(), "ADDED");
                                    Toast.makeText(ItemHandlingActivity.this, "Added", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Core.handleItem(Long.valueOf(npCheckroomNumber.getValue()), "DELETED");
                                    Toast.makeText(ItemHandlingActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                                }

                                finish();
                            }
                        });

                        Button btnUpdate = (Button) findViewById(R.id.btnUpdateCheckroomItem );
                        if(Core.newItem) {
                            btnUpdate.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            btnUpdate.setVisibility(View.VISIBLE);
                            btnUpdate.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    Core.newItem = true;
                                    Core.handleItem(Long.valueOf(npCheckroomNumber.getValue()), "UPDATED");

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
                });
            }
        }).start();
    }
}

