package com.example.firebasedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class HomeroomClassAdapter extends RecyclerView.Adapter<HomeroomClassAdapter.ViewHolder> {

    private List<HomeroomClass> mHomeroomClasses;

    public HomeroomClassAdapter(List<HomeroomClass> homeroomClasses)
    {
        mHomeroomClasses = homeroomClasses;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView className;
        public TextView classTeacherName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            className = (TextView) itemView.findViewById(R.id.classname);
            classTeacherName = (TextView) itemView.findViewById(R.id.classteachername);
        }
    }


    public HomeroomClassAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_homeroom_class, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(HomeroomClassAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        HomeroomClass homeroomClass = mHomeroomClasses.get(position);

        // Set item views based on your views and data model
        viewHolder.className.setText(homeroomClass.getClassName());
        viewHolder.classTeacherName.setText(homeroomClass.getClassTeacherName());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mHomeroomClasses.size();
    }
}