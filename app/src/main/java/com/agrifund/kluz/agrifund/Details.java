package com.agrifund.kluz.agrifund;

import android.net.ParseException;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.textView);
        TextView date_created = (TextView) findViewById(R.id.date);
        TextView author = (TextView) findViewById(R.id.author);
        TextView articl = (TextView) findViewById(R.id.textView2);
        ImageView thumbnail = (ImageView) findViewById(R.id.imageView);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getSerializableExtra("article")!=null){
            Article article = (Article) getIntent().getSerializableExtra("article");
            title.setText(article.getTitle());
            author.setText(""+ article.getAuthor());
            articl.setText(article.getArticlel());
            Glide.with(this).load(article.getThumbnail()).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().crossFade().placeholder(R.drawable.background).into(thumbnail);
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
                date_created.setText(relavetime1);


                //txt_time.append(relavetime1+"\n\n");
                //System.out.println(relavetime1);
            }catch(ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
