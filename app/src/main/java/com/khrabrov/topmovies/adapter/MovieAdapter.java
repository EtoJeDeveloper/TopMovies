package com.khrabrov.topmovies.adapter;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.khrabrov.topmovies.fragment.CalendarDialogFragment;
import com.khrabrov.topmovies.model.MoviePageResult;
import com.khrabrov.topmovies.notification.RemainderBroadcast;
import com.khrabrov.topmovies.R;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static String movieName;
    private static String CHANNEL_ID = "Movie channel";

    public static void setMovieName(String movieName) {
        MovieAdapter.movieName = movieName;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView movieName;
        private ImageView poster;
        private TextView averageVote;
        private TextView dateRelease;
        private TextView overView;
        private TextView schedule;
        private CardView cardView;

        @SuppressLint("ClickableViewAccessibility")
        private ViewHolder(View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_name);
            poster = itemView.findViewById(R.id.poster);
            averageVote = itemView.findViewById(R.id.average_vote);
            dateRelease = itemView.findViewById(R.id.release_date);
            overView = itemView.findViewById(R.id.overview);
            schedule = itemView.findViewById(R.id.schedule);
            cardView = itemView.findViewById(R.id.cardView);

            //Прокрутка внутри recycler
            overView.setOnTouchListener((v, event) -> {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "TestChannel";
                String description = "Channel for test";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = itemView.getContext().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

//            Отображение календаря
            Calendar mCalendar = Calendar.getInstance();
            CalendarDialogFragment calendarDialogFragment =
                    new CalendarDialogFragment(itemView.getContext(), (view, year, monthOfYear, dayOfMonth) -> {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(mCalendar.getTime());

//                        Сообщение о назначенной дате
                        Snackbar.make(cardView, "Viewing scheduled for " + date, Snackbar.LENGTH_LONG).show();

                        Intent intent = new Intent(itemView.getContext(), RemainderBroadcast.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(itemView.getContext(), 0, intent, 0);
                        AlarmManager alarmManager = (AlarmManager)itemView.getContext().getSystemService(
                                ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 1000, pendingIntent);

                    }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
            calendarDialogFragment.getDatePicker().setMinDate(mCalendar.getTimeInMillis());

            schedule.setOnClickListener(v ->
                    calendarDialogFragment.show());

        }
    }

    private Context context;
    private List<MoviePageResult> listOfMovies;

    public MovieAdapter(List<MoviePageResult> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_card, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.movieName.setText(listOfMovies.get(position).getTitle());
        holder.averageVote.setText(listOfMovies.get(position).getVoteAverage().toString());
        holder.dateRelease.setText(listOfMovies.get(position).getReleaseDate());
        holder.overView.setText(listOfMovies.get(position).getOverview());
        MovieAdapter.setMovieName(listOfMovies.get(position).getTitle());

        try {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" +
                    listOfMovies.get(position).getPosterPath()).into(holder.poster);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }
}
