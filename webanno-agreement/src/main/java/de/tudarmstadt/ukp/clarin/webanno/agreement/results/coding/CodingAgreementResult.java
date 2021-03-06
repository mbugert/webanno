/*
 * Copyright 2019
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
package de.tudarmstadt.ukp.clarin.webanno.agreement.results.coding;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import org.dkpro.statistics.agreement.coding.ICodingAnnotationStudy;

import de.tudarmstadt.ukp.clarin.webanno.agreement.AgreementResult;
import de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.CasDiff.ConfigurationSet;
import de.tudarmstadt.ukp.clarin.webanno.curation.casdiff.CasDiff.DiffResult;

public class CodingAgreementResult
    extends AgreementResult<ICodingAnnotationStudy>
{
    private static final long serialVersionUID = -1262324752699430461L;

    protected final DiffResult diff;
    protected final List<ConfigurationSet> setsWithDifferences;
    protected final List<ConfigurationSet> completeSets;
    protected final List<ConfigurationSet> irrelevantSets;
    protected final List<ConfigurationSet> incompleteSetsByPosition;
    protected final List<ConfigurationSet> incompleteSetsByLabel;
    protected final List<ConfigurationSet> pluralitySets;
    
    public CodingAgreementResult(String aType, String aFeature, DiffResult aDiff,
            ICodingAnnotationStudy aStudy, List<String> aCasGroupIds,
            List<ConfigurationSet> aComplete, List<ConfigurationSet> aIrrelevantSets,
            List<ConfigurationSet> aSetsWithDifferences,
            List<ConfigurationSet> aIncompleteByPosition, List<ConfigurationSet> aIncompleteByLabel,
            List<ConfigurationSet> aPluralitySets, boolean aExcludeIncomplete)
    {
        super(aType, aFeature, aStudy, aCasGroupIds, aExcludeIncomplete);
        
        diff = aDiff;
        setsWithDifferences = aSetsWithDifferences;
        completeSets = unmodifiableList(new ArrayList<>(aComplete));
        irrelevantSets = aIrrelevantSets;
        incompleteSetsByPosition = unmodifiableList(new ArrayList<>(aIncompleteByPosition));
        incompleteSetsByLabel = unmodifiableList(new ArrayList<>(aIncompleteByLabel));
        pluralitySets = unmodifiableList(new ArrayList<>(aPluralitySets));
    }
    
    public boolean noPositions()
    {
        return study.getItemCount() == 0;
    }
    
    /**
     * Positions that were not seen in all CAS groups.
     */
    public List<ConfigurationSet> getIncompleteSetsByPosition()
    {
        return incompleteSetsByPosition;
    }

    /**
     * Positions that were seen in all CAS groups, but labels are unset (null).
     */
    public List<ConfigurationSet> getIncompleteSetsByLabel()
    {
        return incompleteSetsByLabel;
    }

    public List<ConfigurationSet> getPluralitySets()
    {
        return pluralitySets;
    }
    
    /**
     * @return sets differing with respect to the type and feature used to calculate agreement.
     */
    public List<ConfigurationSet> getSetsWithDifferences()
    {
        return setsWithDifferences;
    }
    
    public List<ConfigurationSet> getCompleteSets()
    {
        return completeSets;
    }
    
    public List<ConfigurationSet> getIrrelevantSets()
    {
        return irrelevantSets;
    }
    
    public int getDiffSetCount()
    {
        return setsWithDifferences.size();
    }
    
    public int getUnusableSetCount()
    {
        return incompleteSetsByPosition.size() + incompleteSetsByLabel.size()
                + pluralitySets.size();
    }
    
    public Object getCompleteSetCount()
    {
        return completeSets.size();
    }

    public int getTotalSetCount()
    {
        return diff.getPositions().size();
    }
    
    public int getRelevantSetCount()
    {
        return diff.getPositions().size() - irrelevantSets.size();
    }
    
    public DiffResult getDiff()
    {
        return diff;
    }
    
    @Override
    public String toString()
    {
        return "CodingAgreementResult [type=" + type + ", feature=" + feature + ", diffs="
                + getDiffSetCount() + ", unusableSets=" + getUnusableSetCount()
                + ", agreement=" + agreement + "]";
    }
}
