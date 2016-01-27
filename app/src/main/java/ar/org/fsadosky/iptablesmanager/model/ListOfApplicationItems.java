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
package ar.org.fsadosky.iptablesmanager.model;

import java.util.List;

/**
 * Created by ayi on 8/15/15.
 */
public class ListOfApplicationItems {
    public List<ApplicationItem> applicationItems;

    public List<ApplicationItem> getApplicationItems() {
        return applicationItems;
    }

    public void setApplicationItems(List<ApplicationItem> applicationItems) {
        this.applicationItems = applicationItems;
    }
}
