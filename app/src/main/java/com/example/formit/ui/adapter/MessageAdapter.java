package com.example.formit.ui.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.formit.R;
import com.example.formit.data.model.Message;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;
    private static final int TYPE_IMAGE_SENT = 2;
    private static final int TYPE_IMAGE_RECEIVED = 3;


    private LayoutInflater inflater;
    private List<Message> list;
    private String IdUser;
    String name ;
    //private List<JSONObject> messages = new ArrayList<>();

    public MessageAdapter (LayoutInflater inflater, List<Message> list,String idUser,String name) {
        this.inflater = inflater;
        this.list=list;
        this.name=name;
        IdUser=idUser;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;
        ImageView image;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.MessageSendUserPic);
            messageTxt = itemView.findViewById(R.id.sentTxt);
        }
    }



    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;
        ImageView image;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.MessageRecieveUserPic);
            messageTxt = itemView.findViewById(R.id.receivedTxt);
        }
    }



    @Override
    public int getItemViewType(int position) {

        //JSONObject message = messages.get(position);

        Message msg = list.get(position);
        Log.e("message "+position,msg.toString());
        if (msg.getAuthor().getId().equals(IdUser))
            return TYPE_MESSAGE_SENT;

        if (!msg.getAuthor().getId().equals(IdUser))
            return TYPE_MESSAGE_RECEIVED;
        /*try {
            if (message.getBoolean("isSent")) {

                if (message.has("message"))
                    return TYPE_MESSAGE_SENT;
                else
                    return TYPE_IMAGE_SENT;

            } else {

                if (message.has("message"))
                    return TYPE_MESSAGE_RECEIVED;
                else
                    return TYPE_IMAGE_RECEIVED;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_sent_message, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:

                view = inflater.inflate(R.layout.item_received_message, parent, false);
                return new ReceivedMessageHolder(view);


        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        Message msg = list.get(position);
        Log.e("msg auther",msg.getAuthor().getId());
        Log.e("user id",IdUser);
        if (msg.getAuthor().getId().equals(IdUser)) {

            if (!msg.getMessage().isEmpty()) {
                //String img = msg.getAuthor().getPicture();
                //String uri = "@drawable/"+img+"";
                //int imageResource = Resources.getSystem().getIdentifier(img, "drawable", Application.getPackageName);

                //Drawable res = Resources.getSystem().getDrawable(imageResource,null);
                SentMessageHolder messageHolder = (SentMessageHolder) holder;
                if (msg.getAuthor().getFirstname().equals("aaaa")){
                    messageHolder.image.setImageResource(R.drawable.test1);
                }else
                {
                    messageHolder.image.setImageResource(R.drawable.backpack);
                }

                messageHolder.messageTxt.setText(msg.getMessage());

            }

        } else {

            if (!msg.getMessage().isEmpty()) {

               /*String img = msg.getAuthor().getPicture();
                String uri = "@drawable/"+img+"";
                int imageResource = Resources.getSystem().getIdentifier(img, "drawable", new Application().getPackageName());

                Drawable res = Resources.getSystem().getDrawable(imageResource,null);*/
                ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;

                if (msg.getAuthor().getFirstname().equals("Seifeddine")){
                    messageHolder.image.setImageResource(R.drawable.test1);
                }else
                {
                    messageHolder.image.setImageResource(R.drawable.backpack);
                }
                messageHolder.messageTxt.setText(msg.getMessage());

            }
        }
            /*JSONObject message = messages.get(position);


                if (message.getBoolean("isSent")) {

                    if (message.has("message")) {

                        SentMessageHolder messageHolder = (SentMessageHolder) holder;
                        messageHolder.messageTxt.setText(message.getString("message"));

                    }

                } else {

                    if (message.has("message")) {

                        ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
                        messageHolder.nameTxt.setText(message.getString("name"));
                        messageHolder.messageTxt.setText(message.getString("message"));

                    }

                }*/


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem (Message msg)  {

        list.add(msg);
        notifyDataSetChanged();
    }

}
