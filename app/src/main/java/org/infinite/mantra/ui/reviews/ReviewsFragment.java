package org.infinite.mantra.ui.reviews;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.infinite.mantra.R;
import org.infinite.mantra.database.dao.PetographDAO;
import org.infinite.mantra.database.model.PetographModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    View view;
    static List<PetographModel> dbList;
    PetographDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviews, container, false);
        recyclerView = view.findViewById(R.id.review_recycler);

        populateRecyclerView();
        enableSwipeToDeleteAndUndo();
        return view;
    }

    public void populateRecyclerView() {
        try {
            dao = new PetographDAO(getActivity());
            dbList = new ArrayList<>();
            dbList = dao.getDataFromDB();
            adapter = new RecyclerViewAdapter(dbList);
            recyclerView.setAdapter(adapter);

        } catch (NumberFormatException e) {
            Snackbar.make(view, "No Previous Records", 0).setAction("Cancel", null).show();
        }
    }

    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final PetographModel item = adapter.getData().get(position);

                adapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(view, "1 Deleted", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", view -> {

                    adapter.restoreItem(item, position);
                    recyclerView.scrollToPosition(position);
                });
                snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        if (event != DISMISS_EVENT_ACTION) {
                            // Dismiss wasn't because of tapping "UNDO"
                            // so here delete the item from databse
                            dao.deleteARow(item.getTimeMeasured());
                        }
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        populateRecyclerView();
    }
}