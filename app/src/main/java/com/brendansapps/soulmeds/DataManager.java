package com.brendansapps.soulmeds;

import java.util.ArrayList;

/**
 * Created by bt on 3/24/18.
 */

// Structure for each Symptom and the associated Verses
class SymptomObject{
    public String symptomName;
    public ArrayList<String> arrayVerses;
    public ArrayList<String> arrayReferences;
    public ArrayList<String> arrayVerseCards;
}

// Data Manager for the Symptoms and the Verses
public class DataManager {

    ArrayList<SymptomObject> arraySymptomObjects;

    // ===========================================================
    // Constructor
    // ===========================================================
    void DataManager(){
        arraySymptomObjects = new ArrayList<SymptomObject>();
        populateSymptomObjectData();
    }

    // ===========================================================
    // Public Accessors
    // ===========================================================

    // Returns the number of Symptom Objects
    public int size(){
        return arraySymptomObjects.size();
    }

    // Returns the name of the symptom at the index
    public String getSymptomAtIndex(int index){
        if (index < arraySymptomObjects.size()){
            return arraySymptomObjects.get(index).symptomName;
        }
        return "Error: SymptomNotFound";
    }

    // Returns the list of symptoms
    public ArrayList<String> getSymptomsList(){
        ArrayList<String> symptomsList = new ArrayList<>();
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            symptomsList.add(arraySymptomObjects.get(i).symptomName);
        }
        return symptomsList;
    }

    // Returns the verse for the Symptom at the Verse
    public String getVerse(String currentSymptom, int quoteNumber){
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            if (currentSymptom == arraySymptomObjects.get(i).symptomName){
                return arraySymptomObjects.get(i).arrayVerses.get(quoteNumber);
            }
        }
        return "Error: VerseNotFound";
    }

    // Returns the Reference for the verse for the Symptom at the Verse
    public String getVerseReference(String currentSymptom, int quoteNumber){
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            if (currentSymptom == arraySymptomObjects.get(i).symptomName){
                return arraySymptomObjects.get(i).arrayReferences.get(quoteNumber);
            }
        }
        return "Error: VerseNotFound";
    }

    // Returns the Verse Card for the Symptom at the Verse
    public String getVerseCard(String currentSymptom, int quoteNumber){
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            if (currentSymptom == arraySymptomObjects.get(i).symptomName){
                return arraySymptomObjects.get(i).arrayVerseCards.get(quoteNumber);
            }
        }
        return "Error: VerseNotFound";
    }

    // ===========================================================
    // Database
    // ===========================================================

    // Fills in all of the data for the Symptoms and their associated Verses
    // currentSystem doesn't cause a memory leak because of garbage collection
    private void populateSymptomObjectData(){

        // Anger
        SymptomObject currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Anger";

        currentSymptom.arrayVerses.add("for the anger of man does not achieve the righteousness of God.");
        currentSymptom.arrayReferences.add("James 1:20");
        currentSymptom.arrayVerseCards.add("Anger1.png");

        currentSymptom.arrayVerses.add("A fool always loses his temper, But a wise man holds it back.");
        currentSymptom.arrayReferences.add("Proverbs 29:11");
        currentSymptom.arrayVerseCards.add("Anger2.png");

        currentSymptom.arrayVerses.add("Cease from anger and forsake wrath; Do not fret; it leads only to evildoing.");
        currentSymptom.arrayReferences.add("Psalms 37:8");
        currentSymptom.arrayVerseCards.add("Anger3.png");

        currentSymptom.arrayVerses.add("Do not be eager in your heart to be angry For anger resides in the bosom of fools.");
        currentSymptom.arrayReferences.add("Ecclesiastes 7:9");
        currentSymptom.arrayVerseCards.add("Anger4.png");

        currentSymptom.arrayVerses.add("He who is slow to anger has great understanding But he who is quick-tempered exalts folly.");
        currentSymptom.arrayReferences.add("Proverbs 14:29");
        currentSymptom.arrayVerseCards.add("Anger5.png");

        arraySymptomObjects.add(currentSymptom);

        // Depression
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Depression";

        currentSymptom.arrayVerses.add("The Lord is the one who goes ahead of you; He will be with you. He will not fail you or forsake you. Do not fear or be dismayed.");
        currentSymptom.arrayReferences.add("Deuteronomy 31:8");
        currentSymptom.arrayVerseCards.add("Depression1.png");

        currentSymptom.arrayVerses.add("Therefore humble yourselves under the mighty hand of God, that He may exalt you at the proper time, casting all your anxiety on Him, because He cares for you.");
        currentSymptom.arrayReferences.add("1st Peter 5:6-7");
        currentSymptom.arrayVerseCards.add("Depression2.png");

        currentSymptom.arrayVerses.add("Blessed be the God and Father of our Lord Jesus Christ, the Father of mercies and God of all comfort, who comforts us in all our affliction so that we will be able to comfort those who are in any affliction with the comfort with which we ourselves are comforted by God.");
        currentSymptom.arrayReferences.add("2 Corinthians 1:3-4");
        currentSymptom.arrayVerseCards.add("Depression3.png");

        currentSymptom.arrayVerses.add("The righteous cry, and the Lord hears and delivers them out of all their troubles.");
        currentSymptom.arrayReferences.add("Psalms 34:17");
        currentSymptom.arrayVerseCards.add("Depression4.png");

        currentSymptom.arrayVerses.add("These things I have spoken to you, so that in Me you may have peace. In the world you have tribulation, but take courage; I have overcome the world.");
        currentSymptom.arrayReferences.add("John 16:33");
        currentSymptom.arrayVerseCards.add("Depression5.png");

        arraySymptomObjects.add(currentSymptom);

        // Pride
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Pride";

        currentSymptom.arrayVerses.add("But He gives a greater grace. Therefore  it says, 'God is opposed to the proud, but gives grace to the humble.'");
        currentSymptom.arrayReferences.add("Deuteronomy 31:8");
        currentSymptom.arrayVerseCards.add("James 4:6");

        currentSymptom.arrayVerses.add("Thus says the Lord, 'Let not a wise man boast of his wisdom, and let not the mighty man boast of his might, let not a rich man boast of his riches; but let him who boasts boast of this, that he understands and knows Me, that I am the Lord who exercises lovingkindness, justice and righteousness on earth; for I delight in these things,' declares the Lord.");
        currentSymptom.arrayReferences.add("Jeremiah 9:23-24");
        currentSymptom.arrayVerseCards.add("Pride2.png");

        currentSymptom.arrayVerses.add("Do nothing from selfishness or empty conceit, but with humility of mind regard one another as more important than yourselves; do not  merely look out for your own personal interests, but also for the interests of others.");
        currentSymptom.arrayReferences.add("Philippians 2:3-4");
        currentSymptom.arrayVerseCards.add("Pride3.png");

        currentSymptom.arrayVerses.add("Let another praise you, and not your own mouth; A stranger, and not your own lips.");
        currentSymptom.arrayReferences.add("Proverbs 27:2");
        currentSymptom.arrayVerseCards.add("Pride4.png");

        currentSymptom.arrayVerses.add("Be of the same mind toward one another; do not be haughty in mind, but associate with the lowly. Do not be wise in your own estimation.");
        currentSymptom.arrayReferences.add("Romans 12:16");
        currentSymptom.arrayVerseCards.add("Pride5.png");

        arraySymptomObjects.add(currentSymptom);

        // Clean Memory
        currentSymptom = null;
        System.gc();
    }

}
