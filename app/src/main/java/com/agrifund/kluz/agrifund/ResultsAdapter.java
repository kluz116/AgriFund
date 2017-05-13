package com.agrifund.kluz.agrifund;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kluz on 11/28/16.
 */
public class ResultsAdapter  extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Result> articleList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, awayteam,hometeam, matchday,status,goal_one,goal_two;
        public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            matchday = (TextView) view.findViewById(R.id.matchday);
            hometeam = (TextView) view.findViewById(R.id.hometeam);
            awayteam = (TextView) view.findViewById(R.id.awayteam);
            goal_one = (TextView) view.findViewById(R.id.goal_one);
            goal_two = (TextView) view.findViewById(R.id.goal_two);
            status = (TextView) view.findViewById(R.id.status);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public ResultsAdapter(Context mContext, List<Result> articleList) {
        this.mContext = mContext;
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.results_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Result results = articleList.get(position);
        holder.matchday.setText(results.GetDAte());
        holder.title.setText(results.getLeague());
        holder.hometeam.setText(results.getHometeam());
        holder.goal_one.setText(results.getHomegoal());
        holder.awayteam.setText(results.getAwayteam());
        holder.goal_two.setText(results.getAwaygoal());
        holder.status.setText("("+results.getStatus()+")");

        // loading article cover using Glide library
        //Glide.with(mContext).load(article.getThumbnail()).centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);


            }
        });

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_articles, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Share", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}


