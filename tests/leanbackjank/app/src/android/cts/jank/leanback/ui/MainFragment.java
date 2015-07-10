/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package android.cts.jank.leanback.ui;

import android.content.res.Resources.Theme;
import android.cts.jank.leanback.R;
import android.cts.jank.leanback.data.VideoProvider;
import android.cts.jank.leanback.model.Movie;
import android.cts.jank.leanback.presenter.CardPresenter;
import android.cts.jank.leanback.presenter.GridItemPresenter;
import android.cts.jank.leanback.presenter.IconHeaderItemPresenter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class to show BrowseFragment with header and rows of videos
 */
public class MainFragment extends BrowseFragment {
    private ArrayObjectAdapter mRowsAdapter;
    private DisplayMetrics mMetrics;
    private BackgroundManager mBackgroundManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buildRowAdapterItems(VideoProvider.buildMedia());
        prepareBackgroundManager();
        setupUIElements();
        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        mBackgroundManager.release();
        super.onStop();
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mBackgroundManager.setDrawable(getActivity().getResources().getDrawable(
                R.drawable.default_background, getContext().getTheme()));
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        setBadgeDrawable(getActivity().getResources().getDrawable(
                R.drawable.videos_by_google_banner, getContext().getTheme()));
        setTitle(getString(R.string.browse_title));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        Theme theme = getContext().getTheme();
        setBrandColor(getResources().getColor(R.color.fastlane_background, theme));

        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque, theme));

        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new IconHeaderItemPresenter();
            }
        });
    }

    private void setupEventListeners() {
        // Add lister to show the search button.
        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public void buildRowAdapterItems(HashMap<String, List<Movie>> data) {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();

        int i = 0;

        for (Map.Entry<String, List<Movie>> entry : data.entrySet()) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            List<Movie> list = entry.getValue();

            for (int j = 0; j < list.size(); j++) {
                listRowAdapter.add(list.get(j));
            }
            HeaderItem header = new HeaderItem(i, entry.getKey());
            i++;
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }

        HeaderItem gridHeader = new HeaderItem(i, getString(R.string.settings));

        GridItemPresenter gridPresenter = new GridItemPresenter(this);
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(gridPresenter);
        for (int j = 0; j < 10; j++) {
            gridRowAdapter.add(getString(R.string.grid_item_template, j));
        }
        mRowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));

        setAdapter(mRowsAdapter);
    }
}
