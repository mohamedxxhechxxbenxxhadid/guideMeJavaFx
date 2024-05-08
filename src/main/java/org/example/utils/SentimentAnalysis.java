package org.example.utils;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.example.controllers.AjouterPostController;

import java.util.List;

public class SentimentAnalysis {

    public static int sentimentType(String text , AjouterPostController aPC){
        aPC.setLoading(true);
        int sentimentNum = 0 ;
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreSentence> sentences = coreDocument.sentences();
        for(CoreSentence sentence : sentences){
            String sentiment = sentence.sentiment();
            if(sentiment.equals("Negative")){
                sentimentNum = sentimentNum -1;
            }else if(sentiment.equals("Positive")){
                sentimentNum = sentimentNum +1;
            }
            System.out.println(sentiment +"\t"+sentence);
        }
        return sentimentNum ;
    }
}
