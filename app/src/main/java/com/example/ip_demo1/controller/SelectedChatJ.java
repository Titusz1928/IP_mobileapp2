package com.example.ip_demo1.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ip_demo1.R;
import com.example.ip_demo1.model.MessageBox;
import com.example.ip_demo1.model.MessageCardView;
import com.example.ip_demo1.model.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectedChatJ extends AppCompatActivity {

    String url;
    private static final String TAG = "MyTag";

    private static final long DELAY_MS = 10000;

    private long mLastClickTime = 0;

    private Handler handler = new Handler();

    private NestedScrollView nestedScrollView;
    private LinearLayout parentLayout;
    private String idConv;

    boolean firstLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_chat2);

        FloatingActionButton back = findViewById(R.id.SCHbcvBackButton);
        nestedScrollView = findViewById(R.id.clNestedScrollView);

        parentLayout = findViewById(R.id.nsvLinearLayout);




        TextView nameText = findViewById(R.id.tcvNameText);
        //nameText.setText(getIntent().getStringExtra("prenume")+getIntent().getStringExtra("id_conv"));

        //we retrieve the prenume and id_conv from the ChatFragment Activity
        String prenume = getIntent().getStringExtra("prenume");
        idConv = getIntent().getStringExtra("id_conv");

        if (prenume != null) {
            nameText.setText(prenume);
        } else {
            //displaying default name
            nameText.setText("Error loading name");
        }

        //declaring buttons and inputs
        ImageView sendButton = findViewById(R.id.mscvImageView);
        EditText messageInputField = findViewById(R.id.mscvMessageEditText);

        if (!idConv.equals("-1")) {
            loadMessages(nestedScrollView, parentLayout, idConv);
            handler.postDelayed(loadMessagesTask, DELAY_MS);
        }






        //executed after pressing send button
        sendButton.setOnClickListener(v -> {
            //used for preventing double clicks
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            url=getString(R.string.URLsend);

            //creating json object
            JSONObject messageData = new JSONObject();
            try {
                UserData userDataManager = UserData.getInstance();

                String receiverEmail = getIntent().getStringExtra("email");

                messageData.put("message",messageInputField.getText().toString());
                messageData.put("user_id",userDataManager.getId());
                messageData.put("remail",receiverEmail);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, messageData.toString());


            //creating request
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, messageData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle successful response
                            Log.d(TAG, "Response: " + response.toString());

                            String message = response.optString("message", "Unknown message");

                            //convert non existing conversation to existing so that it can load the messages
                            if (idConv.equals("-1")) {
                                String idConvNew = response.optString("id_conv");
                                Toast.makeText(SelectedChatJ.this, "New Conversation started!", Toast.LENGTH_SHORT).show();
                                idConv=idConvNew;
                                loadMessages(nestedScrollView, parentLayout, idConv);
                                handler.postDelayed(loadMessagesTask, DELAY_MS);
                            }else{
                                loadMessages(nestedScrollView, parentLayout, idConv);
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error response
                    Log.e(TAG, "Volley error: " + error.getMessage());
                    Toast.makeText(SelectedChatJ.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                }
            });

            Volley.newRequestQueue(SelectedChatJ.this).add(request);
            messageInputField.setText("");

        });


        //go back to chat list
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(loadMessagesTask);
                Intent intent = new Intent(SelectedChatJ.this, MenuActivityJ.class);
                startActivity(intent);
            }
        });
    }

    //used for loading in messages
    Runnable loadMessagesTask = new Runnable() {
        @Override
        public void run() {
            loadMessages(nestedScrollView, parentLayout, idConv); // Call the function to load messages
            handler.postDelayed(this, DELAY_MS); // Schedule the task to run again after the delay
        }
    };


    private void loadMessages(NestedScrollView nestedScrollView, LinearLayout parentLayout, String idConv) {
        //loading previous messages
        url=getString(R.string.URLmessages);

        //creating json object
        JSONObject conversationData = new JSONObject();
        try {
            UserData userDataManager = UserData.getInstance();
            conversationData.put("id_conv", idConv);
            //to get database id
            conversationData.put("user_id",userDataManager.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, conversationData.toString());

        //creating request
        JsonObjectRequest chatRequest = new JsonObjectRequest(Request.Method.POST, url, conversationData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response
                        Log.d(TAG, "Response: " + response.toString());

                        String message = response.optString("message", "Unknown message");

                        try {
                            UserData userDataManager = UserData.getInstance();
                            String user_id=String.valueOf(userDataManager.getId());
                            //System.out.println(user_id);
                            //creating array from the json arrays
                            JSONArray continutArray = response.getJSONArray("continut_values");
                            Log.d(TAG,continutArray.toString());
                            JSONArray dataArray = response.getJSONArray("data_values");
                            Log.d(TAG,dataArray.toString());
                            JSONArray idSenderArray = response.getJSONArray("id_sender_values");
                            Log.d(TAG,idSenderArray.toString());
                            //uniting arrays into a list
                            List<MessageBox> messageBoxList = parseJsonArrays(continutArray, dataArray,idSenderArray);

                            parentLayout.removeAllViews();

                            //for (int i = messageBoxList.size() - 1; i >= 0; i--) {
                            for(MessageBox messagebox : messageBoxList){
                                //MessageBox messagebox = messageBoxList.get(i);

                                // Declare cardView variable outside the if-else block
                                CardView cardView;

                                // Create a CardView for each pair and add it to the parent LinearLayout
                                //Log.d(TAG,"[i] Comparing:"+messagebox.getSender_id()+user_id);
                                if (messagebox.getSender_id() == user_id) {
                                    //currenlty logged in user message
                                    cardView = createCardViewRight(messagebox.getContinut(), messagebox.getData());
                                } else {
                                    //other user message
                                    cardView = createCardViewLeft(messagebox.getContinut(), messagebox.getData());
                                }

                                if(firstLoad) {
                                    //puts scorll view to the bottom (always the newest message is at the bottom)
                                    nestedScrollView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            nestedScrollView.fullScroll(View.FOCUS_DOWN);
                                        }
                                    });
                                    firstLoad=false;
                                }

                                parentLayout.addView(cardView);
                                Log.d(TAG, "Object created: " + messagebox.toString());
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                        // Display the message in a Toast
                        //Toast.makeText(SelectedChatJ.this, message, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                Log.e(TAG, "Volley error: " + error.getMessage());
                Toast.makeText(SelectedChatJ.this, "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(SelectedChatJ.this).add(chatRequest);
    }

    private CardView createCardViewLeft(String continut, String data) {
        // Create a new CardView
        MessageCardView cardView = new MessageCardView(SelectedChatJ.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, // Width
                ViewGroup.LayoutParams.WRAP_CONTENT  // Height
        );
        layoutParams.setMargins(16, 16, 16, 16);
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(40);
        cardView.setCardBackgroundColor(ContextCompat.getColor(SelectedChatJ.this, R.color.lightgray));
        cardView.setContentPadding(10, 10, 10, 10);

        // Create a LinearLayout for the card view contents
        LinearLayout linearLayout = new LinearLayout(SelectedChatJ.this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Create the TextView for 'continut'
        TextView textViewContinut = new TextView(SelectedChatJ.this);
        textViewContinut.setText(continut);
        textViewContinut.setTextColor(Color.BLACK);
        textViewContinut.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textViewContinut.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Create the TextView for 'data'
        TextView textViewData = new TextView(SelectedChatJ.this);
        textViewData.setText(data);
        textViewData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        textViewData.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add TextViews to LinearLayout
        linearLayout.addView(textViewContinut);
        linearLayout.addView(textViewData);

        // Add LinearLayout to CardView
        cardView.addView(linearLayout);

        // Return the constructed CardView
        return cardView;
    }



    private CardView createCardViewRight(String continut, String data) {
        // Create a new CardView
        MessageCardView cardView = new MessageCardView(SelectedChatJ.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, // Width
                    ViewGroup.LayoutParams.WRAP_CONTENT  // Height
        );
        layoutParams.setMargins(16, 16, 16, 16);
        layoutParams.gravity = Gravity.END;
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(40);
        cardView.setCardBackgroundColor(ContextCompat.getColor(SelectedChatJ.this, R.color.background));
        cardView.setContentPadding(10, 10, 10, 10);


        // Create a LinearLayout for the card view contents
        LinearLayout linearLayout = new LinearLayout(SelectedChatJ.this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Create the TextView for 'continut'
        TextView textViewContinut = new TextView(SelectedChatJ.this);
        textViewContinut.setText(continut);
        textViewContinut.setTextColor(Color.BLACK);
        textViewContinut.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textViewContinut.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Create the TextView for 'data'
        TextView textViewData = new TextView(SelectedChatJ.this);
        textViewData.setText(data);
        textViewData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        textViewData.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add TextViews to LinearLayout
        linearLayout.addView(textViewContinut);
        linearLayout.addView(textViewData);

        // Add LinearLayout to CardView
        cardView.addView(linearLayout);

        // Return the constructed CardView
        return cardView;
    }



    private List<MessageBox> parseJsonArrays(JSONArray continutArray, JSONArray dataArray, JSONArray idSenderArray) throws JSONException {
        List<MessageBox> messageBoxList = new ArrayList<>();

        int length = Math.min(continutArray.length(), dataArray.length());

        for (int i = 0; i < length; i++) {
            String continut = continutArray.getString(i);
            String data = dataArray.getString(i);
            String idSender = idSenderArray.getString(i);

            MessageBox messageBox = new MessageBox(continut,data,idSender);
            messageBoxList.add(messageBox);
        }

        return messageBoxList;

    }
}
