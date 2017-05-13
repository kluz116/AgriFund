package com.agrifund.kluz.agrifund;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kluz on 11/26/16.
 */
public class Fixtures  extends Fragment {

    private FixtureAdapter adapter;
    private List<Fixture> articleList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fixtures, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swifeRefresh);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                getArticles();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getArticles();
            }
        });

        articleList = new ArrayList<>();
        adapter = new FixtureAdapter(getActivity(), articleList);
        recyclerView.setAdapter(adapter);

        return view;
    }
    private void getArticles(){
        mSwipeRefreshLayout.setRefreshing(true);
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "http://takethecorner.com/get_fixtures.php";
        //String url_ = "https://api.soccerama.pro/v1.2/livescore?api_token=RXYPUS4GRhFqurhyJQiGhRf0JhmIMZrJMMgkTLbu7g4ITlJ3dggo0XPFpKHQ";
        JsonArrayRequest arrayreq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        String data = "";
                        int count = 0;
                        while (count<response.length()){
                            try {
                                JSONObject obj = response.getJSONObject(count);
                                int rank = obj.getInt("id");
                                Fixture a = new Fixture(obj.getInt("id"),obj.getString("league"), obj.getString("matchday"),obj.getString("hometeam"),obj.getString("awayteam"),obj.getString("time"));

                                articleList.add(a);
                                adapter.notifyDataSetChanged();


                            }

                            catch (JSONException e) {

                                e.printStackTrace();
                            }
                            count++;
                        }
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        queue.add(arrayreq);


    }


}
