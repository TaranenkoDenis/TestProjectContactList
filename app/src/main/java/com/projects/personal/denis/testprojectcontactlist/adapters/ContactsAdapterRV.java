package com.projects.personal.denis.testprojectcontactlist.adapters;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.personal.denis.testprojectcontactlist.R;
import com.projects.personal.denis.testprojectcontactlist.tools.RecyclerViewClickListener;
import com.projects.personal.denis.testprojectcontactlist.models.Contact;
import com.projects.personal.denis.testprojectcontactlist.tools.MyDiffCallbackContact;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapterRV extends RecyclerView.Adapter<ContactsAdapterRV.ContactViewHolder>{

    private static final String TAG = ContactsAdapterRV.class.getSimpleName();
    private RecyclerViewClickListener clickListenerItem;
    private List<Contact> contacts;

    static class ContactViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        TextView tv_name;
        RecyclerViewClickListener clickListener;

        ContactViewHolder(View itemView, RecyclerViewClickListener clickListener) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }

    public ContactsAdapterRV(List<Contact> contacts, RecyclerViewClickListener clickListener){
        this.clickListenerItem = clickListener;

        if(contacts == null)
            this.contacts = new ArrayList<>();
        else
            this.contacts = contacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);

        return new ContactViewHolder(v, clickListenerItem);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        String tmp = contact.getFirst_name() + " " + contact.getSecond_name();
        holder.tv_name.setText(tmp);
    }

    @Override
    public long getItemId(int position) {
        return contacts.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }


    public void setContacts(List<Contact> newContacts) {
        DiffUtil.DiffResult diffResult
                        = DiffUtil.calculateDiff(new MyDiffCallbackContact(contacts, newContacts));

        diffResult.dispatchUpdatesTo(this);

        contacts = newContacts;
    }

    public Contact getItemByPosition(int position){
        return contacts.get(position);
    }
}
