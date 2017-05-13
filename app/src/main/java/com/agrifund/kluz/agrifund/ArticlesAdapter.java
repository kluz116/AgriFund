package com.agrifund.kluz.agrifund;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Allan Musembya on 14/11/16.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Article> articleList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author,date_created;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            date_created = (TextView) view.findViewById(R.id.date);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public ArticlesAdapter(Context mContext, List<Article> articleList) {
        this.mContext = mContext;
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Article article = articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(""+ article.getAuthor()+" ");
       // holder.date_created.setText(article.getDateCreated());

        try {
            long now = System.currentTimeMillis();
            //String datetime1 = "12/06/2016 03:58 PM";
            //String datetime1 = article.getDateCreated();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            Date convertedDate = dateFormat.parse(article.getDateCreated());

            CharSequence relavetime1 = DateUtils.getRelativeTimeSpanString(
                    convertedDate.getTime(),
                    now,
                    DateUtils.HOUR_IN_MILLIS);
            holder.date_created.setText(relavetime1);


            //txt_time.append(relavetime1+"\n\n");
            //System.out.println(relavetime1);
        }catch(ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }



        // loading article cover using Glide library
        Glide.with(mContext).load(article.getThumbnail()).centerCrop().crossFade().placeholder(R.drawable.background).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);


            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext,Details.class);
                in.putExtra("article", article);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);

            }
        });
        holder.title.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext,Details.class);
                in.putExtra("article", article);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(in);

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
