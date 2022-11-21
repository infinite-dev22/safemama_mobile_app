package org.infinite.mantra;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.infinite.mantra.ui.charts.ChartsFragment;
import org.infinite.mantra.ui.checkup.CheckupFragment;
import org.infinite.mantra.ui.reviews.ReviewsFragment;

import java.util.ArrayList;

public class FragmentViewPagerAdapter  extends FragmentStateAdapter {

    public FragmentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment currentFragment = null;
        switch (position) {
            case 0:
                currentFragment = new CheckupFragment();
                break;
            case 1:
                currentFragment = new ChartsFragment();
                break;
            case 2:
                currentFragment = new ReviewsFragment();
                break;
        }
        assert currentFragment != null;
        return currentFragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setData(ArrayList<Fragment> fragments) {
    }
}
