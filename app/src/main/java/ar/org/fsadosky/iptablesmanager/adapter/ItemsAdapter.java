/*
* Copyright (C) 2015 Ayelen Chavez y Joaquín Rinaudo
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Contributors:
*
* Ayelen Chavez ashy.on.line@gmail.com
* Joaquin Rinaudo jmrinaudo@gmail.com
*
*/
package ar.org.fsadosky.iptablesmanager.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.codingbad.library.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import ar.org.fsadosky.iptablesmanager.holder.ApplicationViewHolder;
import ar.org.fsadosky.iptablesmanager.model.ApplicationItem;
import ar.org.fsadosky.iptablesmanager.view.ApplicationItemView;


public class ItemsAdapter extends RecyclerView.Adapter<ApplicationViewHolder> {

    private List<ApplicationItem> applicationList;
    private int lastPosition = -1;

    public ItemsAdapter() {
        this.applicationList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ApplicationItemView view = new ApplicationItemView(parent.getContext());

        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ApplicationViewHolder holder, int position) {
        final ApplicationItem applicationItem = applicationList.get(position);

        if (position > lastPosition) {
            for (Animator anim : getAnimators(holder.getApplicationItemView())) {
                anim.setDuration(500).start();
                anim.setInterpolator(new LinearInterpolator());
            }

            lastPosition = position;
        }

        holder.bind(applicationItem);
    }

    public void addItem(int position, ApplicationItem applicationItem) {
        this.applicationList.add(position, applicationItem);
        notifyItemInserted(getItemCount());
    }

    public void moveItem(int fromPosition, int toPosition) {
        final ApplicationItem moved = this.applicationList.remove(fromPosition);
        this.applicationList.add(toPosition, moved);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addItemList(List<ApplicationItem> applicationItems) {
        this.applicationList.addAll(applicationItems);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.applicationList.clear();
        notifyDataSetChanged();
    }

    public ApplicationItem removeItem(int position) {
        final ApplicationItem removed = this.applicationList.remove(position);
        notifyItemRemoved(position);
        return removed;
    }

    public ApplicationItem getItemAtPosition(int position) {
        return this.applicationList.get(position);
    }

    public List<ApplicationItem> getSearchApplicationItems() {
        return applicationList;
    }

    @Override
    public int getItemCount() {
        return this.applicationList.size();
    }

    protected Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0),
                getAlphaAnimator(view)
        };
    }

    protected Animator getAlphaAnimator(final View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        return anim;
    }

    public void setItems(List<ApplicationItem> items) {
        this.applicationList = items;//ArrayUtils.copyFrom();
        notifyDataSetChanged();
    }

    public void animateTo(List<ApplicationItem> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<ApplicationItem> newItems) {
        for (int i = applicationList.size() - 1; i >= 0; i--) {
            final ApplicationItem applicationItem = applicationList.get(i);
            if (!newItems.contains(applicationItem)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<ApplicationItem> newItems) {
        for (int i = 0, count = newItems.size(); i < count; i++) {
            final ApplicationItem applicationItem = newItems.get(i);
            if (!applicationList.contains(applicationItem)) {
                addItem(i, applicationItem);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<ApplicationItem> newItems) {
        for (int toPosition = newItems.size() - 1; toPosition >= 0; toPosition--) {
            final ApplicationItem applicationItem = newItems.get(toPosition);
            final int fromPosition = applicationList.indexOf(applicationItem);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
}
