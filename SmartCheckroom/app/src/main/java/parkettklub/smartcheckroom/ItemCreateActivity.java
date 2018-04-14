package parkettklub.smartcheckroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;

import java.util.Date;

import parkettklub.smartcheckroom.data.CheckroomItem;
import parkettklub.smartcheckroom.data.ManageDB;

public class ItemCreateActivity extends AppCompatActivity {

    public static final String KEY_BARCODE_NUMBER = "KEY_BARCODE_NUMBER";

    // UI
    private TextView editBarcodeNumber;
    private Spinner spnrCheckroomItemNumber;

    private TextView editCoatNumber;
    private Button incCoat;
    private Button decCoat;

    private String barcodeNumber;

    private Integer coatNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        Intent intent = getIntent();
        barcodeNumber = intent.getStringExtra(KEY_BARCODE_NUMBER);

        // UI elem referenciak elkerese
        editBarcodeNumber = (TextView) findViewById(R.id.barcodeNumber);
        editBarcodeNumber.setText(barcodeNumber);

        spnrCheckroomItemNumber = (Spinner) findViewById(R.id.checkroomNumber);

        Long[] numbers = ManageDB.getInstance().getFreeIds(0);

        spnrCheckroomItemNumber.setAdapter(new ArrayAdapter<Long>(this,
                android.R.layout.simple_spinner_item, numbers));

        coatNum = 0;

        editCoatNumber = (TextView) findViewById(R.id.coatNum);
        editCoatNumber.setText("0");

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

        // A gombok esemenykezeloinek beallitasa
        Button btnOk = (Button) findViewById(R.id.btnCreateCheckroomItem);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* add Checkroom Item */
                //Toast.makeText(getContext(), String.valueOf(spnrCheckroomItemNumber.getSelectedItem()), Toast.LENGTH_LONG).show();

                CheckroomItem item = CheckroomItem.findById(CheckroomItem.class, (Long) spnrCheckroomItemNumber.getSelectedItem());

                item.setDueReserved(true);
                item.setDueBarcodeNumber(barcodeNumber);
                item.setDueCoatNumber(coatNum);
                item.setDueDate(new Date(System.currentTimeMillis()));

                //CheckroomItem item = new CheckroomItem(true, spnrCheckroomItemNumber.getSelectedItem(), mParam1,
                //        new Date(System.currentTimeMillis()));
                item.save();

                Toast.makeText(ItemCreateActivity.this, String.valueOf(item.getId()), Toast.LENGTH_LONG).show();

                finish();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancelCreateCheckroomItem);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }


}
