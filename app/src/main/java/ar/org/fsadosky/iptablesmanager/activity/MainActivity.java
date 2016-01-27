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
package ar.org.fsadosky.iptablesmanager.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.codingbad.library.activity.AbstractSideBarActivity;
import com.codingbad.library.utils.ComplexSharedPreference;

import java.util.ArrayList;
import java.util.List;

import ar.org.fsadosky.iptablesmanager.R;
import ar.org.fsadosky.iptablesmanager.fragment.AboutFragment;
import ar.org.fsadosky.iptablesmanager.fragment.ApplicationsListFragment;
import ar.org.fsadosky.iptablesmanager.fragment.HowToFragment;
import ar.org.fsadosky.iptablesmanager.fragment.RateMeFragment;
import ar.org.fsadosky.iptablesmanager.model.ApplicationItem;
import ar.org.fsadosky.iptablesmanager.model.ListOfApplicationItems;
import ar.org.fsadosky.iptablesmanager.model.SystemProperties;

public class MainActivity extends AbstractSideBarActivity implements ApplicationsListFragment.Callbacks {

    private static final String APPLICATIONS = "applications";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    protected void setInitialFragment() {
        setInitialFragment(getInitialFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.action_about:
                replaceFragment(AboutFragment.newInstance());
                break;
            case R.id.action_howto:
                replaceFragment(HowToFragment.newInstance());
                break;
            case R.id.action_main:
                replaceFragment(getInitialFragment());
                break;
            case R.id.action_rate_me:
                replaceFragment(RateMeFragment.newInstance());
                goToGooglePlay();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToGooglePlay() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private Fragment getInitialFragment() {
            return ApplicationsListFragment.newInstance();
    }

    @Override
    public void onChangesApplied(List<ApplicationItem> applications) {
        saveApplications(applications);

        Integer vpn_client = 0;
        List<Integer> uids = new ArrayList<>();

        for (ApplicationItem applicationItem : applications) {
            switch (applicationItem.getState()) {
                case VPN_CLIENT:
                    vpn_client= applicationItem.getUID();
                    break;
                case BLOCK:
                    Log.d("DEBUG", "Block");
                    break;
                case ALLOW:
                    uids.add(applicationItem.getUID());
                    break;
                default:
                    Log.d("DEBUG","There was a problem");
                    break;
            }
        }

        if(vpn_client != 0) {
            SystemProperties.set("persist.security.vpn.uid", "" + vpn_client);
        }
        else {
            SystemProperties.set("persist.security.vpn.uid", "");
        }

        if(uids.size() > 0){
            SystemProperties.set("persist.security.whitelist",TextUtils.join(", ", uids));
        }

    }


    private void saveApplications(List<ApplicationItem> applicationItems) {
        ListOfApplicationItems listOfApplicationItems = new ListOfApplicationItems();
        listOfApplicationItems.applicationItems = applicationItems;
        ComplexSharedPreference.write(this, listOfApplicationItems, APPLICATIONS);
    }

    @Override
    public List<ApplicationItem> getApplicationsSavedStatus() {
        ListOfApplicationItems items = ComplexSharedPreference.read(this, APPLICATIONS, ListOfApplicationItems.class);
        if (items == null) {
            return new ArrayList<ApplicationItem>();
        }
        return items.applicationItems;
    }
}
