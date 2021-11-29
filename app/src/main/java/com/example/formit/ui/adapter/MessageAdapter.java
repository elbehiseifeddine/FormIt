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

import com.example.formit.BuildConfig;
import com.example.formit.R;
import com.example.formit.data.model.Message;
import com.example.formit.data.model.User;
import com.example.formit.data.repository.ApiInterface;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter {

    /*private static MessageAdapter instance;
    public static String PACKAGE_NAME;

    public static MessageAdapter getInstance() {
        return instance;
    }*/
    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;
    private static final int TYPE_IMAGE_SENT = 2;
    private static final int TYPE_IMAGE_RECEIVED = 3;


    public Context conn;
    private LayoutInflater inflater;
    private List<Message> list;
    private String IdUser;
    private User user;
    String name ;
    private ApiInterface api = ApiInterface.Companion.create();

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

        TextView messageTxt,nameTxt;
        ImageView image;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            image = itemView.findViewById(R.id.MessageRecieveUserPic);
            messageTxt = itemView.findViewById(R.id.receivedTxt);
        }
    }



    @Override
    public int getItemViewType(int position) {

        //JSONObject message = messages.get(position);

        Message msg = list.get(position);
        Log.e("message "+position,msg.toString());
        if (msg.getAuthor().equals(IdUser))
            return TYPE_MESSAGE_SENT;

        if (!msg.getAuthor().equals(IdUser))
            return TYPE_MESSAGE_RECEIVED;
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //String PkgName = BuildConfig.APPLICATION_ID;

        conn = parent.getContext();
        View view;
        //instance=this;
        //PACKAGE_NAME = getContext().getPackageName();
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
        //String packagename= MessageAdapter.getContext().getPackageName();


        Message msg = list.get(position);
        Log.e("msg auther",msg.getAuthor());
        Log.e("user id",IdUser);
        if (msg.getAuthor().equals(IdUser)) {

            if (!msg.getMessage().isEmpty()) {


                //String img = msg.getAuthor().getPicture();
                //String uri = "@drawable/"+img+"";
                //int imageResource = Resources.getSystem().getIdentifier(img, "drawable", Application.getPackageName);

                //Drawable res = Resources.getSystem().getDrawable(imageResource,null);
                SentMessageHolder messageHolder = (SentMessageHolder) holder;
                //if (msg.getAuthor().getFirstname().equals("aaaa")){
                    messageHolder.image.setImageResource(R.drawable.test1);
                //}else
               // {
                    messageHolder.image.setImageResource(R.drawable.backpack);
                //}

                messageHolder.messageTxt.setText(msg.getMessage());

            }

        } else {

            if (!msg.getMessage().isEmpty()) {
                ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
                Log.e("message auther ",msg.getAuthor());

                Call<User> call = api.getUserById(msg.getAuthor());


                call.enqueue(new Callback<User>() {
                                 @Override
                                 public void onResponse(Call<User> call, Response<User> response) {
                                        user= response.body();
                                        Log.e("-------------------------user from get user by id ", user.toString());
                                     messageHolder.nameTxt.setText(user.getFirstname());
                                 }

                                 @Override
                                 public void onFailure(Call<User> call, Throwable t) {
                                     Log.e("failure get user message adapter","true");
                                 }
                             });

               /* String img = user.getPicture();
                String uri = "@drawable/"+img+"";
                int imageResource = Resources.getSystem().getIdentifier(uri, null, conn.getPackageName());

                Drawable res = Resources.getSystem().getDrawable(imageResource,null);
*/


                //if (msg.getAuthor().getFirstname().equals("Seifeddine")){
                //messageHolder.image.setImageDrawable(res);
                // }else
                // {


                messageHolder.image.setImageResource(R.drawable.backpack);
                // }
                messageHolder.messageTxt.setText(msg.getMessage());

            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem (Message msg)  {

        list.add(msg);
        notifyDataSetChanged();
    }

    /*public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }*/
}
