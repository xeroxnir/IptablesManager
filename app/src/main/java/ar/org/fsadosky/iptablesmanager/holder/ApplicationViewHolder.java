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
package ar.org.fsadosky.iptablesmanager.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codingbad.library.view.ThreeStatesButton;
import ar.org.fsadosky.iptablesmanager.R;

import ar.org.fsadosky.iptablesmanager.model.ApplicationItem;
import ar.org.fsadosky.iptablesmanager.view.ApplicationItemView;


/**
 * Created by ayi on 8/6/15.
 */
public class ApplicationViewHolder extends RecyclerView.ViewHolder implements ThreeStatesButton.StateListener {

    private final ApplicationItemView applicationItemView;
    private final ThreeStatesButton threeStatesButton;
    private ApplicationItem applicationItem;

    public ApplicationViewHolder(ApplicationItemView itemView) {
        super(itemView);
        this.applicationItemView = itemView;
        this.threeStatesButton = (ThreeStatesButton) itemView.findViewById(R.id.item_button);
    }

    public void bind(ApplicationItem applicationItem) {
        this.applicationItem = applicationItem;

        applicationItemView.fill(applicationItem.getApplicationName(), applicationItem.getIconUri(), applicationItem.getState());
        threeStatesButton.setStateListener(this);
    }

    public View getApplicationItemView() {
        return applicationItemView;
    }

    @Override
    public void onState1() {
        this.applicationItem.setState(ApplicationItem.StateEnum.ALLOW);
    }

    @Override
    public void onState2() {
        this.applicationItem.setState(ApplicationItem.StateEnum.VPN_CLIENT);
    }

    @Override
    public void onState3() {
        this.applicationItem.setState(ApplicationItem.StateEnum.BLOCK);
    }
}
