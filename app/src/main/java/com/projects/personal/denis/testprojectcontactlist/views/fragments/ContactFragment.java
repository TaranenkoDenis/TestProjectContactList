package com.projects.personal.denis.testprojectcontactlist.views.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.projects.personal.denis.testprojectcontactlist.App;
import com.projects.personal.denis.testprojectcontactlist.views.activities.MainActivity;
import com.projects.personal.denis.testprojectcontactlist.R;
import com.projects.personal.denis.testprojectcontactlist.models.Contact;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.reactive.DataSubscription;


public class ContactFragment extends Fragment {
    private static final String TAG = ContactFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int PARAM_NEW_CONTACT = 1;
    public static final int PARAM_VIEW_OLD_CONTACT = 2;

    private DataSubscription subscription;

    private static boolean isModeViewing = true;

    private int mParam1;
    private long mParam2;

    private EditText etFirstName;
    private EditText etSecondName;
    private EditText etAddress;
    private EditText etPhoneNumber;
    private Box<Contact> contactsBox;
    private FloatingActionButton fab;


    public ContactFragment() {
    }

    public static ContactFragment newInstance(int param1, long param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putLong(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ContactFragment newInstance(int param1) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getLong(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_contact, container, false);

        BoxStore boxStore = ((App)getActivity().getApplication()).getBoxStore();
        contactsBox = boxStore.boxFor(Contact.class);

        etFirstName = v.findViewById(R.id.et_firstName);
        etSecondName = v.findViewById(R.id.et_secondName);
        etAddress = v.findViewById(R.id.et_address);
        etPhoneNumber = v.findViewById(R.id.et_phone);

        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(fabClickListener);

        if(mParam1 == PARAM_VIEW_OLD_CONTACT){
            Contact contact = contactsBox.get(mParam2);
            showContact(contact);
            setMode(false);
        }
        return v;
    }

    private void showContact(Contact contact) {
        etFirstName.setText(contact.getFirst_name());
        etSecondName.setText(contact.getSecond_name());
        etAddress.setText(contact.getAddress());
        etPhoneNumber.setText(contact.getPhone_number());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container_fragment, new ListFragment());
                ft.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param mode True turns on the visible mode. False turns on the edit mode.
     */
    private void setMode(boolean mode) {
        isModeViewing = mode;
        if(!isModeViewing){

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            etFirstName.setInputType(InputType.TYPE_NULL);
            etSecondName.setInputType(InputType.TYPE_NULL);
            etPhoneNumber.setInputType(InputType.TYPE_NULL);
            etAddress.setInputType(InputType.TYPE_NULL);
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_mode_edit_24dp));
        } else {

            etFirstName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            etSecondName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            etPhoneNumber.setInputType(InputType.TYPE_CLASS_PHONE);
            etAddress.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_24dp));
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity activity = (MainActivity)getActivity();
        if (activity != null) {
            activity.showUpButton();
        }
    }

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!isModeViewing) {
                if (etFirstName.getText().length() == 0 ||
                        etSecondName.getText().length() == 0 ||
                        etAddress.getText().length() == 0 ||
                        etPhoneNumber.getText().length() == 0)
                    Toast.makeText(getActivity(), "Fill in all the fields.", Toast.LENGTH_SHORT).show();
                else {
                    Contact c = new Contact(
                            etFirstName.getText().toString(),
                            etSecondName.getText().toString(),
                            etAddress.getText().toString(),
                            etPhoneNumber.getText().toString()
                    );
                    contactsBox.put(c);
                    setMode(true);
                }
            } else setMode(false);
        }
    };
}