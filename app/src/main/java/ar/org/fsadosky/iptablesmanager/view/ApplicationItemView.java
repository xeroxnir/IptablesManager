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
package ar.org.fsadosky.iptablesmanager.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codingbad.library.utils.ViewUtil;
import com.codingbad.library.view.ThreeStatesButton;
import ar.org.fsadosky.iptablesmanager.R;

import ar.org.fsadosky.iptablesmanager.model.ApplicationItem;
import roboguice.inject.InjectView;

public class ApplicationItemView extends LinearLayout {

    @InjectView(R.id.item_view_icon)
    private ImageView icon;

    @InjectView(R.id.item_button)
    private ThreeStatesButton stateButton;

    @InjectView(R.id.item_text)
    private TextView textButton;

    public ApplicationItemView(Context context) {
        super(context);
        init();
    }

    public ApplicationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ApplicationItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_view, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewUtil.reallyInjectViews(this);
    }

    public void fill(String text, Uri iconUri, ApplicationItem.StateEnum state) {
        textButton.setText(text);

        stateButton.setState(state.toInt());
        int size = getResources().getDimensionPixelSize(R.dimen.icon_size);

        Glide.with(getContext())
                .load(iconUri)
                .centerCrop()
                .crossFade()
                .override(size, size)
                .placeholder(R.drawable.ic_thumbnail)
                .into(this.icon);
    }
}
