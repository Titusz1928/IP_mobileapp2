package com.example.ip_demo1.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ip_demo1.R;
import com.example.ip_demo1.model.Chat;
import com.example.ip_demo1.model.EmailNamePair;
import com.example.ip_demo1.model.Message;
import com.example.ip_demo1.model.User;
import com.example.ip_demo1.model.UserSingleton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChatFragmentJ extends Fragment {

    String url;
    private static final String TAG = "MyTag";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_j2, container, false);


        //declaring buttons
        ImageView searchButton = view.findViewById(R.id.strImageView);
        EditText searchInputField = view.findViewById(R.id.strEditText);

        LinearLayout parentLayout = view.findViewById(R.id.cltrLinearLayout);

        //LOADING IN EXISTING CHATS
        url=getString(R.string.CLOUD_SERVER)+getString(R.string.GET_CHAT_HISTORY);

        JSONArray chatHistoryRequestArray = new JSONArray();
        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        Integer ID = user.getId();
        chatHistoryRequestArray.put(ID);
        Log.d(TAG, chatHistoryRequestArray.toString());


        JsonArrayRequest chatHistoryRequest = new JsonArrayRequest(Request.Method.POST, url, chatHistoryRequestArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        // Handle response
                        onResponseJSONArray(responseArray,parentLayout);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                Log.e(TAG, "Volley error: " + error.getMessage());
                Toast.makeText(requireContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(requireContext()).add(chatHistoryRequest);


        //executed when search button is pressed
        searchButton.setOnClickListener(v -> {

            url=getString(R.string.CLOUD_SERVER)+getString(R.string.GET_CHAT_SEARCH);

            JSONArray chatSearchRequestArray = new JSONArray();
            String searchText= searchInputField.getText().toString();
            chatSearchRequestArray.put(ID);
            Log.d(TAG, chatSearchRequestArray.toString());


            JsonArrayRequest chatSearchRequest = new JsonArrayRequest(Request.Method.POST, url, chatSearchRequestArray,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray responseArray) {
                            // Handle response
                            Log.d(TAG,String.valueOf(responseArray));
                            parentLayout.removeAllViews();
                            onResponseJSONArray(responseArray,parentLayout);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error response
                    Log.e(TAG, "Volley error: " + error.getMessage());
                    Toast.makeText(requireContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
                }
            });

            Volley.newRequestQueue(requireContext()).add(chatSearchRequest);

        });



        return view;
    }

    private void onResponseJSONArray(JSONArray responseArray, LinearLayout parent) {
        try {
            // Iterate over each JSON object in the array
            for (int i = 0; i < responseArray.length(); i++) {
                // Get the JSON object at index i
                JSONObject chatJson = responseArray.getJSONObject(i);

                // Parse the JSON object into a Chat object
                ObjectMapper objectMapper = new ObjectMapper();
                Chat chat = objectMapper.readValue(chatJson.toString(), Chat.class);
                Log.d(TAG,chat.toString());

                Message firstMessage = getFirstMessage(chat);

                Log.d(TAG,"PREPARING TO CREATE CARDVIEW");
                //Log.d(TAG,firstMessage.getSendingDate().toString());
                CardView cardView = createCardViewExisting(chat.getOtherUser().getEmailAddress(),chat.getOtherUser().getFirstName(),String.valueOf(firstMessage.getSendingDate()),firstMessage.getContent(),String.valueOf(chat.getId()));
                parent.addView(cardView);

                StringBuilder logMessage = new StringBuilder();
                logMessage.append("Chat ").append(i).append(": \n")
                        .append("Chat ID: ").append(chat.getId()).append("\n")
                        .append("Messages: ").append(chat.getMessages()).append("\n")
                        .append("Other User: ").append(chat.getOtherUser());

                // Log the formatted message
                Log.d(TAG, logMessage.toString());
            }
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
            // Handle JSON processing exception
        }
    }

    @NonNull
    private static Message getFirstMessage(Chat chat) {
        Message firstMessage = null;
        List<Message> messages = chat.getMessages();

        // Check if the messages list is not empty and contains at least one message
        if (messages != null && !messages.isEmpty()) {
            // Get the first message from the list
            firstMessage = messages.get(0);
        }

        // If firstMessage is still null, set a default message
        if (firstMessage == null) {
            // Create a default message with empty content and sendingDate
            firstMessage = new Message();
            firstMessage.setContent("");
        }
        return firstMessage;
    }



    private CardView createCardViewExisting(String email, String prenume, String date, String lastMsg, String id_conv) {

        CardView cardView = new CardView(requireContext());
        email = "email (HARDCODED)";
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                950, // Width
                300  // Height
        );
        cardView.setRadius(30);
        layoutParams.setMargins(16, 16, 16, 16); // Left, top, right, bottom margins
        cardView.setLayoutParams(layoutParams);

        //cardView.setCardCornerRadius(30); // Card corner radius
        cardView.setCardBackgroundColor(Color.WHITE); // Card background color
        cardView.setContentPadding(10, 10, 10, 10); // Card content padding

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(),SelectedChatJ.class);
                //we pass the email, prenume and id_conv to the SelectedChatJ activity
                //intent.putExtra("email", email);
                intent.putExtra("prenume", prenume);
                intent.putExtra("id_conv",id_conv);
                startActivity(intent);
            }
        });


        // Create a ConstraintLayout for the card view
        ConstraintLayout constraintLayout = new ConstraintLayout(requireContext());

        // Set constraint layout properties
        ConstraintLayout.LayoutParams constraintLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        constraintLayout.setLayoutParams(constraintLayoutParams);

        cardView.addView(constraintLayout);

        // Create and add TextView for prenume to the ConstraintLayout
        TextView textPrenume = new TextView(requireContext());
        textPrenume.setId(View.generateViewId());
        textPrenume.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        textPrenume.setText(prenume);
        textPrenume.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        textPrenume.setTextColor(Color.BLACK);
        constraintLayout.addView(textPrenume);

        // Set constraints for prenume TextView
        ConstraintSet constraintSetPrenume = new ConstraintSet();
        constraintSetPrenume.clone(constraintLayout);
        constraintSetPrenume.connect(textPrenume.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSetPrenume.connect(textPrenume.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSetPrenume.applyTo(constraintLayout);


        // Create and add TextView for last msg to the ConstraintLayout
        TextView textLastMsg = new TextView(requireContext());
        textLastMsg.setId(View.generateViewId());
        textLastMsg.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        textLastMsg.setText(lastMsg);
        textLastMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textLastMsg.setTextColor(Color.BLACK);
        constraintLayout.addView(textLastMsg);

        // Set constraints for last msg TextView
        ConstraintSet constraintSetLastmsg = new ConstraintSet();
        constraintSetLastmsg.clone(constraintLayout);
        constraintSetLastmsg.connect(textLastMsg.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSetLastmsg.connect(textLastMsg.getId(), ConstraintSet.TOP, textPrenume.getId(), ConstraintSet.BOTTOM);
        constraintSetLastmsg.applyTo(constraintLayout);


        // Create and add TextView for date to the ConstraintLayout
        TextView textDate = new TextView(requireContext());
        textDate.setId(View.generateViewId());
        textDate.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        if(!date.equals("null"))
            textDate.setText(date);
        else
            textDate.setText(" ");
        textDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textDate.setTextColor(Color.GRAY);
        constraintLayout.addView(textDate);

        // Set constraints for date TextView
        ConstraintSet constraintSetDate = new ConstraintSet();
        constraintSetDate.clone(constraintLayout);
        constraintSetDate.connect(textDate.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END); // Align to the right side of the ConstraintLayout
        constraintSetDate.connect(textDate.getId(), ConstraintSet.TOP, textLastMsg.getId(), ConstraintSet.BOTTOM); // Position below textEmail
        constraintSetDate.applyTo(constraintLayout);

        // Create and add TextView for email to the ConstraintLayout
        TextView textEmail = new TextView(requireContext());
        textEmail.setId(View.generateViewId());
        textEmail.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        Log.d(TAG,email);
        textEmail.setText(email);
        textEmail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textEmail.setTextColor(Color.GRAY);
        constraintLayout.addView(textEmail);

        // Set constraints for email TextView
        ConstraintSet constraintSetEmail = new ConstraintSet();
        constraintSetEmail.clone(constraintLayout);
        constraintSetEmail.connect(textEmail.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSetEmail.connect(textEmail.getId(), ConstraintSet.TOP, textDate.getId(), ConstraintSet.BOTTOM);
        constraintSetEmail.applyTo(constraintLayout);



        return cardView;
    }



}
