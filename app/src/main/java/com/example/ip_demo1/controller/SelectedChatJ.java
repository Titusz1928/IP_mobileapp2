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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ip_demo1.R;
import com.example.ip_demo1.model.Chat;
import com.example.ip_demo1.model.Message;
import com.example.ip_demo1.model.MessageBox;
import com.example.ip_demo1.model.MessageCardView;
import com.example.ip_demo1.model.UserSingleton;
import com.example.ip_demo1.model.User;
import com.example.ip_demo1.constants.Constants;
import com.example.ip_demo1.model.WebSocketEcho;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class SelectedChatJ extends AppCompatActivity {

    String url;
    private static final String TAG = "MyTag";
    private static final long DELAY_MS = 10000;
    private long mLastClickTime = 0;
    private Handler handler = new Handler();
    private NestedScrollView nestedScrollView;
    private LinearLayout parentLayout;
    private String chatId;
    boolean firstLoad=true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_chat2);

        Log.d(TAG,"entered sekectedCjhatJ");


        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        if (user == null) {
            Intent intent = new Intent(SelectedChatJ.this, LoginActivity.class);
            startActivity(intent);
        }

        FloatingActionButton back = findViewById(R.id.SCHbcvBackButton);
        nestedScrollView = findViewById(R.id.clNestedScrollView);
        parentLayout = findViewById(R.id.nsvLinearLayout);
        TextView nameText = findViewById(R.id.tcvNameText);
        ImageView sendButton = findViewById(R.id.mscvImageView);
        EditText messageInputField = findViewById(R.id.mscvMessageEditText);

        //we retrieve the prenume and id_conv from the ChatFragment Activity
        String prenume = getIntent().getStringExtra("prenume");
        chatId = getIntent().getStringExtra("chat_id");

        if (prenume != null) {
            nameText.setText(prenume);
        } else {
            //displaying default name
            nameText.setText("Error loading name");
        }

        if (!chatId.equals("-1")) {
            Log.d(TAG,"calling loadMessages");
            loadMessages(nestedScrollView, parentLayout, chatId);
            //handler.postDelayed(loadMessagesTask, DELAY_MS);
        }

        OkHttpClient client = new OkHttpClient();
        WebSocketEcho webSocketEcho = new WebSocketEcho(this);

        okhttp3.Request request = new okhttp3.Request.Builder()
                //.url("ws://192.168.1.247:4000/")
                .url("ws://echo.websocket.org/")
                .build();

        WebSocket webSocket = client.newWebSocket(request, webSocketEcho);
        webSocketEcho.setWebSocket(webSocket);



        //executed after pressing send button
        sendButton.setOnClickListener(v -> {
            //start();
            String message = messageInputField.getText().toString();
            messageInputField.setText(" ");
            webSocketEcho.sendMessage(message);
            nestedScrollView.fullScroll(View.FOCUS_DOWN);

        });

        //go back to chat list
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handler.removeCallbacks(loadMessagesTask);
                Intent intent = new Intent(SelectedChatJ.this, MenuActivityJ.class);
                startActivity(intent);
            }
        });
    }


    public void output(final String txt){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"OUTPUT FUNCTION ENTERED  : "+txt);
                UserSingleton userSingleton = UserSingleton.getInstance();
                User user = userSingleton.getUser();
                Integer id = user.getId();
                Message message = new Message(id,txt);
                String dateToConvert = LocalDateTime.now().toString();
                message.setSendingDate(dateToConvert);
                Log.d(TAG,"selectedchatactivity: "+message.getSendingDate().toString());
                CardView cardView = createCardViewRight(message.getContent(),String.valueOf(message.getSendingDate()));
                parentLayout.addView(cardView);
            }
        });
    }



    private Map<String, Integer> generateRequestBodyForChat(final Integer userId, final Integer chatId)
    {
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put(Constants.RequestParameters.ID, userId);
        requestBody.put(Constants.RequestParameters.CHAT_ID, chatId);
        return requestBody;
    }


    private void loadMessages(NestedScrollView nestedScrollView, LinearLayout parentLayout, String idConv) {
        //loading previous messages
        url = getString(R.string.CLOUD_SERVER) + getString(R.string.GET_CHAT);

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();

        Map<String, Integer> conversationData = generateRequestBodyForChat(user.getId(), Integer.parseInt(chatId));
        Log.d(TAG, conversationData.toString());

        JSONObject jsonConversationData = null;
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            // Convert Map to JSON string
            String jsonString = objectMapper.writeValueAsString(conversationData);
            // Create JSONObject from JSON string
            jsonConversationData = new JSONObject(jsonString);
            // Print JSON string
            Log.d(TAG,jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }



        // Create the request (this assumes you are using the Volley library)
        JsonObjectRequest chatRequest = new JsonObjectRequest(Request.Method.POST, url, jsonConversationData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response
                        Log.d(TAG, "Response: " + response.toString());

                        ObjectMapper objectMapper = new ObjectMapper();
                        Chat chat = null;
                        try {
                            chat = objectMapper.readValue(response.toString(), Chat.class);
                            Log.d(TAG, chat.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, String.valueOf(response));
                        parentLayout.removeAllViews();
                        generateChat(chat, parentLayout);

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

    private void generateChat(Chat chat, LinearLayout parentLayout) {
        List<Message> messages = chat.getMessages();
        for(Message message : messages){
            UserSingleton userSingleton = UserSingleton.getInstance();
            User user = userSingleton.getUser();
            Integer idUser= user.getId();
            if(idUser==message.getSendingUserId()){
                CardView messageCardView = createCardViewRight(message.getContent(),String.valueOf(message.getSendingDate()));
                parentLayout.addView(messageCardView);
            }else{
                CardView messageCardView = createCardViewLeft(message.getContent(),String.valueOf(message.getSendingDate()));
                parentLayout.addView(messageCardView);
            }
        }
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

}
