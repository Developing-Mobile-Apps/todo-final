package com.example.todofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateAppFragment} factory method to
 * create an instance of this fragment.
 */
public class RateAppFragment extends Fragment {
    public RateAppFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_rate_app, container, false);
        final RatingBar appRatingBar = rootView.findViewById(R.id.app_rating_bar);

        // set ratingBar OnRatingBarChangeListener
        appRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) ->
                Toast.makeText(getContext(),
                        "My App Rating: " + rating,
                        Toast.LENGTH_SHORT).show());

        return rootView;
    }
}