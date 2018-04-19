package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parcelpaladin.d30f2n.parcelpaladinfirebase.R;
import com.parcelpaladin.d30f2n.parcelpaladinfirebase.UserInformation;

import org.w3c.dom.Text;

import java.util.List;
/**
 * Created by ndrag on 3/21/2018.
 */

//public class userlist extends ArrayAdapter<UserInformation> {
//    private Activity context;
//    private List<UserInformation> userList;
//
//    public userlist(Activity context, List<UserInformation> userList){
//        super(context, R.layout.list_layout, userList);
//        this.context = context;
//        this.userList = userList;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = context.getLayoutInflater();
//        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
//
//        TextView textviewname = (TextView) listViewItem.findViewById(R.id.textViewUserEmail);
////        TextView textviewname = (TextView) listViewItem.findViewById(R.id.textviewname);
//
//        UserInformation user = userList.get(position);
//
//        textviewname.setText(user.getName());
//
//        return listViewItem;
//    }
//}
