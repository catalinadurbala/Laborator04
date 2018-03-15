package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private Button additionalFieldsButton;
    private boolean additionalFields = false;
    private Button saveButton;
    private Button cancelButton;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText jobEditText;
    private EditText companyEditText;
    private EditText websiteEditText;
    private EditText IMEditText;

    final public static int CONTACTS_MANAGER_REQUEST_CODE = 2017;

    private AdditionalFieldsButtonListener additionalFieldsButtonListener = new AdditionalFieldsButtonListener();
    private class AdditionalFieldsButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            additionalFields = !additionalFields;
            if (additionalFields) {
                jobEditText.setVisibility(View.VISIBLE);
                companyEditText.setVisibility(View.VISIBLE);
                websiteEditText.setVisibility(View.VISIBLE);
                IMEditText.setVisibility(View.VISIBLE);
                additionalFieldsButton.setText("HIDE ADDITIONAL FIELDS");
            } else {
                jobEditText.setVisibility(View.GONE);
                companyEditText.setVisibility(View.GONE);
                websiteEditText.setVisibility(View.GONE);
                IMEditText.setVisibility(View.GONE);
                additionalFieldsButton.setText("SHOW ADDITIONAL FIELDS");
            }
        }
    }

    private SaveButtonListener saveButtonListener = new SaveButtonListener();
    private class SaveButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String jobTitle = jobEditText.getText().toString();
            String company = companyEditText.getText().toString();
            String website = websiteEditText.getText().toString();
            String im = IMEditText.getText().toString();

            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            if (!name.equals("")) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            }
            if (!phone.equals("")) {
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
            }
            if (!email.equals("")) {
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
            }
            if (!address.equals("")) {
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
            }
            if (!jobTitle.equals("")) {
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
            }
            if (!company.equals("")) {
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
            }
            ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
            if (!website.equals("")) {
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                contactData.add(websiteRow);
            }
            if (!im.equals("")) {
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                contactData.add(imRow);
            }
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
            startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE);
        }
    }

    private CancelButtonListener cancelButtonListener = new CancelButtonListener();
    private class CancelButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            setResult(Activity.RESULT_CANCELED, new Intent());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        additionalFieldsButton = findViewById(R.id.additional_fields_button);
        additionalFieldsButton.setOnClickListener(additionalFieldsButtonListener);

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(saveButtonListener);

        cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(cancelButtonListener);

        nameEditText = findViewById(R.id.name_edit_text);
        phoneEditText = findViewById(R.id.number_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        jobEditText = findViewById(R.id.job_edit_text);
        companyEditText = findViewById(R.id.company_edit_text);
        websiteEditText = findViewById(R.id.website_edit_text);
        IMEditText = findViewById(R.id.IM_edit_text);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, "Phone number is empty!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
