package com.expedia.itineraryplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> array;
    private ArrayList<Data> rightSwipe = new ArrayList<>();
    private SwipeFlingAdapterView flingContainer;
    private Integer mountain=0;
    private Integer beach=0;
    private Integer forest=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        array = new ArrayList<>();
        array.add(new Data("https://www.desktopbackground.org/download/480x800/2015/12/10/1055072_tiger-couple-in-the-forest-wallpapers_1920x1200_h.jpg", "forest"));
        array.add(new Data("https://www.wildernessshots.com/wp-content/uploads/2017/08/Elk-Mountains-Sunset-Colorado-Photos-1000x1000.jpg", "mountain"));
        array.add(new Data("https://www.christchurchnz.com/media/4282/waikuku-beach-for-web.jpg", "beach"));
        array.add(new Data("https://images.unsplash.com/photo-1516866165397-e78bc3336c0d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1000&q=80", "mountain"));
        array.add(new Data("https://media.fromthegrapevine.com/assets/images/2016/1/beach-couple.jpg.480x0_q71_crop-scale.jpg", "beach"));
        array.add(new Data("https://media-cdn.tripadvisor.com/media/photo-s/12/cf/e8/04/view-from-the-business.jpg", "forest"));
        array.add(new Data("https://images.pexels.com/photos/618833/pexels-photo-618833.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "mountain"));
        array.add(new Data("https://images.pexels.com/photos/994605/pexels-photo-994605.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "beach"));
        array.add(new Data("https://images.unsplash.com/photo-1492486169476-8c5dafd9539f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80", "forest"));

        myAppAdapter = new MyAppAdapter(array, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject


                ifArrayEnd();
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                System.out.println(".............."+ array.get(0).getId());

                rightSwipe.add(array.get(0));
                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
                 ifArrayEnd();


            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });
    }

    private void ifArrayEnd() {
        if(array.isEmpty()){
            for(int i=0;i<rightSwipe.size();i++){
                String pid = rightSwipe.get(i).getId();
                if(pid.equals("mountain")){
                    mountain++;
                }
                else if(pid.equals("beach")){

                    beach++;
                }
                else
                    forest++;
            }

            String finalAns;

            if(mountain >= forest && mountain >= beach){
                finalAns = "mountain";
            }

            else if(forest>= mountain && forest >= beach){
                finalAns = "forest";
            }
            else
                finalAns = "beach";
        }


    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;

    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
//            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            Glide.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }
}
