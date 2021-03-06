/*
 * Copyright 2020
 * Ubiquitous Knowledge Processing (UKP) Lab and FG Language Technology
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.clarin.webanno.api.annotation.actionbar.docnav;

import org.apache.wicket.markup.html.panel.Panel;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import de.tudarmstadt.ukp.clarin.webanno.api.annotation.actionbar.ActionBarExtension;
import de.tudarmstadt.ukp.clarin.webanno.api.annotation.actionbar.open.OpenDocumentDialog;
import de.tudarmstadt.ukp.clarin.webanno.api.annotation.page.AnnotationPageBase;

@Order(0)
@Component
public class DefaultDocumentNavigatorActionBarExtension implements ActionBarExtension
{
    @Override
    public Panel createActionBarItem(String aId, AnnotationPageBase aPage)
    {
        return new DocumentNavigator(aId, aPage);
    }
    
    @Override
    public void onInitialize(AnnotationPageBase aPage)
    {
        ActionBarExtension.super.onInitialize(aPage);
        
        aPage.getFooterItems().getObject().stream()
                .anyMatch(component -> component instanceof OpenDocumentDialog);
        
        // Open the dialog if no document has been selected.
        aPage.add(new AutoOpenDialogBehavior());
        
        // We put the dialog into the page footer since this is presently the only place where we
        // can dynamically add stuff to the page. We cannot add simply to the action bar (i.e.
        // DocumentNavigator) because the action bar only shows *after* a document has been
        // selected. In order to allow the dialog to be rendered *before* a document has been
        // selected (i.e. when the action bar is still not on screen), we need to attach it to the
        // page. The same for the AutoOpenDialogBehavior we add below.
        OpenDocumentDialog openDocumentsModal = createOpenDocumentsDialog("item", aPage);
        aPage.addToFooter(openDocumentsModal);
    }
    
    protected OpenDocumentDialog createOpenDocumentsDialog(String aId, AnnotationPageBase aPage)
    {
        return new OpenDocumentDialog(aId, aPage.getModel(), aPage.getAllowedProjects(), null);
    }
}
