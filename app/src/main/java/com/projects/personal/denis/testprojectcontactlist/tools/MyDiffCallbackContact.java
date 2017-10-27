package com.projects.personal.denis.testprojectcontactlist.tools;

import android.support.v7.util.DiffUtil;

import com.projects.personal.denis.testprojectcontactlist.models.Contact;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by denis on 26.10.17.
 */

public class MyDiffCallbackContact extends DiffUtil.Callback {

    List<Contact> oldList;
    List<Contact> newList;

    public MyDiffCallbackContact(List<Contact> oldList, List<Contact> newList) {
        this.oldList = new ArrayList<>();
        this.newList = new ArrayList<>();
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(oldItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getFirst_name().equals(newList.get(newItemPosition).getFirst_name())
                && oldList.get(oldItemPosition).getSecond_name().equals(newList.get(newItemPosition).getSecond_name());
    }
}
