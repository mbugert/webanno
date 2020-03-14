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
package de.tudarmstadt.ukp.clarin.webanno.agreement.measures.fleisskappa;

import org.springframework.stereotype.Component;

import de.tudarmstadt.ukp.clarin.webanno.agreement.PairwiseAnnotationResult;
import de.tudarmstadt.ukp.clarin.webanno.agreement.measures.AgreementMeasure;
import de.tudarmstadt.ukp.clarin.webanno.agreement.measures.DefaultAgreementTraits;
import de.tudarmstadt.ukp.clarin.webanno.agreement.results.coding.AbstractCodingAgreementMeasureSupport;
import de.tudarmstadt.ukp.clarin.webanno.agreement.results.coding.CodingAgreementResult;
import de.tudarmstadt.ukp.clarin.webanno.api.AnnotationSchemaService;
import de.tudarmstadt.ukp.clarin.webanno.model.AnnotationFeature;

@Component
public class FleissKappaAgreementMeasureSupport
    extends AbstractCodingAgreementMeasureSupport<DefaultAgreementTraits>
{
    private final AnnotationSchemaService annotationService;
    
    public FleissKappaAgreementMeasureSupport(AnnotationSchemaService aAnnotationService)
    {
        super();
        annotationService = aAnnotationService;
    }
    
    @Override
    public String getName()
    {
        return "Fleiss' Kappa (coding)";
    }

    @Override
    public AgreementMeasure<PairwiseAnnotationResult<CodingAgreementResult>> createMeasure(
            AnnotationFeature aFeature, DefaultAgreementTraits aTraits)
    {
        return new FleissKappaAgreementMeasure(aFeature, aTraits, annotationService);
    }
}