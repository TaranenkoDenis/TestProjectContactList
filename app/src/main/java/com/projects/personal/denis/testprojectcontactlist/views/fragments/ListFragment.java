package com.projects.personal.denis.testprojectcontactlist.views.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.personal.denis.testprojectcontactlist.App;
import com.projects.personal.denis.testprojectcontactlist.tools.RecyclerViewClickListener;
import com.projects.personal.denis.testprojectcontactlist.views.activities.MainActivity;
import com.projects.personal.denis.testprojectcontactlist.R;
import com.projects.personal.denis.testprojectcontactlist.adapters.ContactsAdapterRV;
import com.projects.personal.denis.testprojectcontactlist.models.Contact;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment {


    private static final String TAG = ListFragment.class.getSimpleName();
    private DataSubscription subscription;
    private ContactsAdapterRV contactsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        final BoxStore boxStore = ((App)getActivity().getApplication()).getBoxStore();
        Box<Contact> contactsBox = boxStore.boxFor(Contact.class);

        subscription = contactsBox.query().build()
                .subscribe().on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<Contact>>() {
                    @Override
                    public void onData(@NonNull List<Contact> contacts) {
                        contactsAdapter.setContacts(contacts);
                    }
                });

        RecyclerView recyclerView = v.findViewById(R.id.recyclerContacts);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        contactsAdapter = new ContactsAdapterRV(
                contactsBox.query().build().find(),
                new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Contact c = contactsAdapter.getItemByPosition(position);

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();

                        ContactFragment fragment = ContactFragment.newInstance(
                                ContactFragment.PARAM_VIEW_OLD_CONTACT,
                                c.getId());

                        ft.replace(R.id.container_fragment, fragment);

                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });
        recyclerView.setAdapter(contactsAdapter);

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ContactFragment fragment = ContactFragment.newInstance(ContactFragment.PARAM_NEW_CONTACT);
                ft.replace(R.id.container_fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        subscription.cancel();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity activity = (MainActivity)getActivity();
        if (activity != null) {
            activity.hideUpButton();
        }
    }

    public interface GetterOfBox{
        Box<Contact> getBox();
    }
}
