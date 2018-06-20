package com.brendansapps.soulmeds;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by bt on 3/24/18.
 *
 * DataManager handles all information about the S
 *
 * Each SymptomObject has a symptom name and threeymptoms and their associated Verses arrays used for the bible verse info (verse, reference, card)
 */

// Structure for each Symptom and the associated Verses
class SymptomObject{
    public String symptomName;
    public ArrayList<String> arrayVerses;
    public ArrayList<String> arrayReferences;
    public ArrayList<String> arrayVerseCards;

    public SymptomObject(){
        arrayVerses = new ArrayList<>();
        arrayReferences = new ArrayList<>();
        arrayVerseCards = new ArrayList<>();
    }
}

// Data Manager for the Symptoms and the Verses
public class DataManager {

    private static final String TAG = "DataManager";
    private ArrayList<SymptomObject> arraySymptomObjects;

    // ===========================================================
    // Constructor
    // ===========================================================
    public DataManager(){
        arraySymptomObjects = new ArrayList<>();
        populateSymptomObjectData();
//        printSymptomData();
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

    // Returns the number of Verses for that symptom
    public int getNumVerses(String symptom){
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            if (symptom.equals(arraySymptomObjects.get(i).symptomName)){
                return arraySymptomObjects.get(i).arrayVerses.size();
            }
        }
        return 0;
    }

    // Returns the verse for the Symptom at the Verse
    public String getVerse(String currentSymptom, int quoteNumber){
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            if (currentSymptom.equals(arraySymptomObjects.get(i).symptomName)){
                return arraySymptomObjects.get(i).arrayVerses.get(quoteNumber);
            }
        }
        return "Error: VerseNotFound";
    }

    // Returns the Reference for the verse for the Symptom at the Verse
    public String getVerseReference(String currentSymptom, int quoteNumber){
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            if (currentSymptom.equals(arraySymptomObjects.get(i).symptomName)){
                return arraySymptomObjects.get(i).arrayReferences.get(quoteNumber);
            }
        }
        return "Error: VerseNotFound";
    }

    // Returns the Verse Card for the Symptom at the Verse
    public String getVerseCard(String currentSymptom, int quoteNumber){
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            if (currentSymptom.equals(arraySymptomObjects.get(i).symptomName)){
                return arraySymptomObjects.get(i).arrayVerseCards.get(quoteNumber);
            }
        }
        return "Error: VerseNotFound";
    }

    public void printSymptomData(){
        Log.d(TAG, "Symptom Data:");
        for (int i = 0; i < arraySymptomObjects.size(); i++){
            String currentSymptom = arraySymptomObjects.get(i).symptomName;
            Log.d(TAG, currentSymptom);
            for (int j = 0; j < arraySymptomObjects.get(i).arrayVerses.size(); j++){
                Log.d(currentSymptom, arraySymptomObjects.get(i).arrayVerses.get(j));
                Log.d(currentSymptom, arraySymptomObjects.get(i).arrayReferences.get(j));
                Log.d(currentSymptom, arraySymptomObjects.get(i).arrayVerseCards.get(j));
            }
        }
    }

    // ===========================================================
    // Database
    // ===========================================================

    // Fills in all of the data for the Symptoms and their associated Verses
    // currentSystem doesn't cause a memory leak because of garbage collection
    private void populateSymptomObjectData(){
        SymptomObject currentSymptom = new SymptomObject();

        // Anger
        currentSymptom = new SymptomObject();
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

        currentSymptom.arrayVerses.add("But He gives a greater grace. Therefore  it says, “God is opposed to the proud, but gives grace to the humble.”");
        currentSymptom.arrayReferences.add("James 4:6");
        currentSymptom.arrayVerseCards.add("Pride1.png");

        currentSymptom.arrayVerses.add("Thus says the Lord, “Let not a wise man boast of his wisdom, and let not the mighty man boast of his might, let not a rich man boast of his riches;    but let him who boasts boast of this, that he understands and knows Me, that I am the Lord who exercises lovingkindness, justice and righteousness on earth; for I delight in these things,” declares the Lord.");
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

        // Purity
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Purity";

        currentSymptom.arrayVerses.add("For this is the will of God, your sanctification;  that is, that you abstain from sexual immorality;");
        currentSymptom.arrayReferences.add("1 Thessalonians 4:3");
        currentSymptom.arrayVerseCards.add("sexualPurity1.png");

        currentSymptom.arrayVerses.add("Flee immorality. Every  other sin that a man commits is outside the body, but the immoral man sins against his own body.");
        currentSymptom.arrayReferences.add("1 Corinthians 6:18");
        currentSymptom.arrayVerseCards.add("sexualPurity2.png");

        currentSymptom.arrayVerses.add("But I say to you that everyone who looks at a woman with lust for her has already committed adultery with her in his heart.");
        currentSymptom.arrayReferences.add("Matthew 5:28");
        currentSymptom.arrayVerseCards.add("sexualPurity3.png");

        currentSymptom.arrayVerses.add("No temptation has overtaken you but such as is common to man; and God is faithful, who will not allow you to be tempted beyond what you are able, but with the temptation will provide the way of escape also, so that you will be able to endure it.");
        currentSymptom.arrayReferences.add("1 Corinthians 10:13");
        currentSymptom.arrayVerseCards.add("sexualPurity4.png");

        currentSymptom.arrayVerses.add("Now the deeds of the flesh are evident, which are: immorality, impurity,  sensuality, idolatry, sorcery, enmities, strife, jealousy, outbursts of anger, disputes, dissensions, factions, envying, drunkenness, carousing, and things like these, of which I forewarn you, just as I have forewarned you, that those who practice such things  will not inherit the kingdom of God.");
        currentSymptom.arrayReferences.add("Galatians 5:19-21");
        currentSymptom.arrayVerseCards.add("sexualPurity5.png");

        arraySymptomObjects.add(currentSymptom);

        // Forgiveness
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Forgiveness";

        currentSymptom.arrayVerses.add("For if you forgive others for their transgressions, your heavenly Father will also forgive you.    But if you do not forgive others, then your Father will not forgive your transgressions.");
        currentSymptom.arrayReferences.add("Matthew 6:14-15");
        currentSymptom.arrayVerseCards.add("Forgiveness1.png");

        currentSymptom.arrayVerses.add("bearing with one another, and forgiving each other, whoever has a complaint against anyone; just as the Lord forgave you, so also should you.");
        currentSymptom.arrayReferences.add("Colossians 3:13");
        currentSymptom.arrayVerseCards.add("Forgiveness2.png");

        currentSymptom.arrayVerses.add("Whenever you stand praying, forgive, if you have anything against anyone, so that your Father who is in heaven will also forgive you your traansgressions.");
        currentSymptom.arrayReferences.add("Mark 11:25");
        currentSymptom.arrayVerseCards.add("Forgiveness3.png");

        currentSymptom.arrayVerses.add("Be kind to one another, tender-hearted, forgiving each other, just as God in Christ also has forgiven you.");
        currentSymptom.arrayReferences.add("Ephesians 4:32");
        currentSymptom.arrayVerseCards.add("Forgiveness4.png");

        currentSymptom.arrayVerses.add("And forgive us our debts, as we also have forgiven our debtors.");
        currentSymptom.arrayReferences.add("Matthew 6:12");
        currentSymptom.arrayVerseCards.add("Forgiveness5.png");

        arraySymptomObjects.add(currentSymptom);

        // Gossip
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Gossip";

        currentSymptom.arrayVerses.add("He who goes about as a slanderer reveals secrets, Therefore do not associate with a gossip.");
        currentSymptom.arrayReferences.add("Proverbs 20:19");
        currentSymptom.arrayVerseCards.add("Gossip1.png");

        currentSymptom.arrayVerses.add("He who guards his mouth and his tongue, Guards his soul from troubles.");
        currentSymptom.arrayReferences.add("Proverbs 21:23");
        currentSymptom.arrayVerseCards.add("Gossip2.png");

        currentSymptom.arrayVerses.add("You shall not go about as a slanderer among your people, and you are not to act against the life of your neighbor; I am the Lord.");
        currentSymptom.arrayReferences.add("Leviticus 19:16");
        currentSymptom.arrayVerseCards.add("Gossip3.png");

        currentSymptom.arrayVerses.add("to malign no one, to be peaceable, gentle, showing every consideration for all men.");
        currentSymptom.arrayReferences.add("Titus 3:2");
        currentSymptom.arrayVerseCards.add("Gossip4.png");

        currentSymptom.arrayVerses.add("Set a guard, O Lord, over my mouth; Keep watch over the door of my lips.");
        currentSymptom.arrayReferences.add("Psalm 141:3");
        currentSymptom.arrayVerseCards.add("Gossip5.png");

        arraySymptomObjects.add(currentSymptom);

        // Complaining
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Complaining";

        currentSymptom.arrayVerses.add("Do all things without grumbling or disputing;");
        currentSymptom.arrayReferences.add("Philippians 2:14");
        currentSymptom.arrayVerseCards.add("Complaining1.png");

        currentSymptom.arrayVerses.add("Rejoice always; pray without ceasing; in everything give thanks; for this is God’s will for you in Christ Jesus.");
        currentSymptom.arrayReferences.add("1 Thessalonians 5:16-18");
        currentSymptom.arrayVerseCards.add("Complaining2.png");

        currentSymptom.arrayVerses.add("Now the people became like those who complain of adversity in the hearing of the Lord; and when the Lord heard  it, His anger was kindled.");
        currentSymptom.arrayReferences.add("Numbers 11:1a");
        currentSymptom.arrayVerseCards.add("Complaining3.png");

        currentSymptom.arrayVerses.add("Not that I speak from want, for I have learned to be   content in whatever circumstances I am.");
        currentSymptom.arrayReferences.add("Philippians 4:11");
        currentSymptom.arrayVerseCards.add("Complaining4.png");

        currentSymptom.arrayVerses.add("Moses said, “ This will happen when the Lord gives you meat to eat in the evening, and bread to the full in the morning; for the Lord hears your grumblings which you grumble against Him. And what are we? Your grumblings are not against us but against the Lord.”");
        currentSymptom.arrayReferences.add("Exodus 16:8");
        currentSymptom.arrayVerseCards.add("Complaining5.png");

        arraySymptomObjects.add(currentSymptom);

        // Hate
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Hate";

        currentSymptom.arrayVerses.add("The one who says he is in the Light and yet hates his brother is in the darkness until now.");
        currentSymptom.arrayReferences.add("1 John 2:9");
        currentSymptom.arrayVerseCards.add("Hate1.png");

        currentSymptom.arrayVerses.add("Let all bitterness and wrath and anger and clamor and slander be put away from you, along with all malice.");
        currentSymptom.arrayReferences.add("Ephesians 4:31");
        currentSymptom.arrayVerseCards.add("Hate2.png");

        currentSymptom.arrayVerses.add("Everyone who hates his brother is a murderer; and you know that no murderer has eternal life abiding in him.");
        currentSymptom.arrayReferences.add("1 John 3:15");
        currentSymptom.arrayVerseCards.add("Hate3.png");

        currentSymptom.arrayVerses.add("Hatred stirs up strife, But love covers all transgressions.");
        currentSymptom.arrayReferences.add("Proverbs 10:12");
        currentSymptom.arrayVerseCards.add("Hate4.png");

        currentSymptom.arrayVerses.add("So this I say, and affirm together with the Lord, that you walk no longer just as the Gentiles also walk, in the futility of their mind, being darkened in their understanding, excluded from the life of God because of the ignorance that is in them, because of the hardness of their heart");
        currentSymptom.arrayReferences.add("Ephesians 4:17-18");
        currentSymptom.arrayVerseCards.add("Hate5.png");

        arraySymptomObjects.add(currentSymptom);

        // Lying
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Lying";

        currentSymptom.arrayVerses.add("You shall not steal, nor deal falsely, nor lie to one another.");
        currentSymptom.arrayReferences.add("Leviticus 19:11");
        currentSymptom.arrayVerseCards.add("Lying1.png");

        currentSymptom.arrayVerses.add("Lying lips are an abomination to the Lord, But those who deal faithfully are His delight.");
        currentSymptom.arrayReferences.add("Proverbs 12:22");
        currentSymptom.arrayVerseCards.add("Lying2.png");

        currentSymptom.arrayVerses.add("Therefore, laying aside falsehood, speak truth each one  of you with his neighbor, for we are members of one another.");
        currentSymptom.arrayReferences.add("Ephesians 4:25");
        currentSymptom.arrayVerseCards.add("Lying3.png");

        currentSymptom.arrayVerses.add("You shall not bear false witness against your neighbor.");
        currentSymptom.arrayReferences.add("Exodus 20:16");
        currentSymptom.arrayVerseCards.add("Lying4.png");

        currentSymptom.arrayVerses.add("A false witness will not go unpunished, And he who tells lies will perish.");
        currentSymptom.arrayReferences.add("Proverbs 19:9");
        currentSymptom.arrayVerseCards.add("Lying5.png");

        arraySymptomObjects.add(currentSymptom);

        // Cussing/Sarcasm
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Cussing/Sarcasm";

        currentSymptom.arrayVerses.add("Let no unwholesome word proceed from your mouth, but only such  a word as is good for edification according to the need  of the moment, so that it will give grace to those who hear.");
        currentSymptom.arrayReferences.add("Ephesians 4:29");
        currentSymptom.arrayVerseCards.add("Cussing1.png");

        currentSymptom.arrayVerses.add("You shall not take the name of the Lord your God in vain, for the Lord will not leave him unpunished who takes His name in vain.");
        currentSymptom.arrayReferences.add("Exodus 20:7");
        currentSymptom.arrayVerseCards.add("Cussing2.png");

        currentSymptom.arrayVerses.add("If anyone thinks himself to be religious, and yet does not bridle his tongue but deceives his  own heart, this man’s religion is worthless.");
        currentSymptom.arrayReferences.add("James 1:26");
        currentSymptom.arrayVerseCards.add("Cussing3.png");

        currentSymptom.arrayVerses.add("The mouth of the righteous flows with wisdom, But the perverted tongue will be cut out.");
        currentSymptom.arrayReferences.add("Proverbs 10:31");
        currentSymptom.arrayVerseCards.add("Cussing4.png");

        currentSymptom.arrayVerses.add("It is not what enters into the mouth  that defiles the man, but what proceeds out of the mouth, this defiles the man.");
        currentSymptom.arrayReferences.add("Matthew 15:11");
        currentSymptom.arrayVerseCards.add("Cussing5.png");

        arraySymptomObjects.add(currentSymptom);

        // Stealing
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Stealing";

        currentSymptom.arrayVerses.add("You shall not steal.");
        currentSymptom.arrayReferences.add("Exodus 20:15");
        currentSymptom.arrayVerseCards.add("Stealing1.png");

        currentSymptom.arrayVerses.add("He who steals must steal no longer; but rather he must labor, performing with his own hands what is good, so that he will have  something to share with one who has need.");
        currentSymptom.arrayReferences.add("Ephesians 4:28");
        currentSymptom.arrayVerseCards.add("Stealing2.png");

        currentSymptom.arrayVerses.add("Then he said to Him, “Which ones?” And Jesus said, “You shall not commit murder; You shall not commit adultery; You shall not steal; You shall not bear false witness”");
        currentSymptom.arrayReferences.add("Matthew 19:18");
        currentSymptom.arrayVerseCards.add("Stealing1.png");

        currentSymptom.arrayVerses.add("Or do you not know that the unrighteous will not inherit the kingdom of God? Do not be deceived; neither fornicators, nor idolaters, nor adulterers, nor effeminate, nor homosexuals,  nor thieves , nor  the covetous, nor drunkards, nor revilers, nor swindlers, will inherit the kingdom of God. Such were some of you; but you were washed, but you were sanctified, but you were justified in the name of the Lord Jesus Christ and in the Spirit of our God.");
        currentSymptom.arrayReferences.add("1 Corinthians 6:9-11");
        currentSymptom.arrayVerseCards.add("Stealing1.png");

        currentSymptom.arrayVerses.add("Bread obtained by falsehood is sweet to a man, But afterward his mouth will be filled with gravel.");
        currentSymptom.arrayReferences.add("Proverbs 20:17");
        currentSymptom.arrayVerseCards.add("Stealing1.png");

        arraySymptomObjects.add(currentSymptom);

        // Father's love your family
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Father's love your family";

        currentSymptom.arrayVerses.add("But if anyone does not provide for his own, and especially for those of his household, he has denied the faith and is worse than an unbeliever.");
        currentSymptom.arrayReferences.add("1 Timothy 5:8");
        currentSymptom.arrayVerseCards.add("fatherLoveYourFamily1.png");

        currentSymptom.arrayVerses.add("Fathers, do not provoke your children to anger, but bring them up in the discipline and instruction of the Lord.");
        currentSymptom.arrayReferences.add("Ephesians 6:4");
        currentSymptom.arrayVerseCards.add("fatherLoveYourFamily2.png");

        currentSymptom.arrayVerses.add("Husbands, love your wives, just as Christ also loved the church and gave himself up for her");
        currentSymptom.arrayReferences.add("Ephesians 5:25");
        currentSymptom.arrayVerseCards.add("fatherLoveYourFamily3.png");

        currentSymptom.arrayVerses.add("Husbands, love your wives and do not be embittered against them.");
        currentSymptom.arrayReferences.add("Colossians 3:19");
        currentSymptom.arrayVerseCards.add("fatherLoveYourFamily4.png");

        currentSymptom.arrayVerses.add("For this reason a man shall leave his father and mother and shall be joined to his wife, and the two shall become one flesh.");
        currentSymptom.arrayReferences.add("Ephesians 5:31");
        currentSymptom.arrayVerseCards.add("fatherLoveYourFamily5.png");

        arraySymptomObjects.add(currentSymptom);

        // Wives love your Family
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Wives love your Family";

        currentSymptom.arrayVerses.add("Wives, be subject to your own husbands, as to the Lord.");
        currentSymptom.arrayReferences.add("Ephesians 5:22");
        currentSymptom.arrayVerseCards.add("wivesLoveYourFamily1.png");

        currentSymptom.arrayVerses.add("An excellent wife is the crown of her husband, But she who shames  him is like rottenness in his bones.");
        currentSymptom.arrayReferences.add("Proverbs 12:4");
        currentSymptom.arrayVerseCards.add("wivesLoveYourFamily2.png");

        currentSymptom.arrayVerses.add("Wives, be subject to your husbands, as is fitting in the Lord.");
        currentSymptom.arrayReferences.add("Colossians 3:18");
        currentSymptom.arrayVerseCards.add("wivesLoveYourFamily3.png");

        currentSymptom.arrayVerses.add("House and wealth are an inheritance from fathers, But a prudent wife is from the Lord.");
        currentSymptom.arrayReferences.add("Proverbs 19:14");
        currentSymptom.arrayVerseCards.add("wivesLoveYourFamily4.png");

        currentSymptom.arrayVerses.add("Train up a child in the way he should go,Even when he is old he will not depart from it.");
        currentSymptom.arrayReferences.add("Proverbs 22:6");
        currentSymptom.arrayVerseCards.add("wivesLoveYourFamily5.png");

        arraySymptomObjects.add(currentSymptom);

        // Laziness
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Laziness";

        currentSymptom.arrayVerses.add("Whatever you do, do your work heartily, as for the Lord rather than for men");
        currentSymptom.arrayReferences.add("Colossians 3:23");
        currentSymptom.arrayVerseCards.add("Laziness1.png");

        currentSymptom.arrayVerses.add("The soul of the sluggard craves and  gets nothing, But the soul of the diligent is made fat.");
        currentSymptom.arrayReferences.add("Proverbs 13:4");
        currentSymptom.arrayVerseCards.add("Laziness2.png");

        currentSymptom.arrayVerses.add("With good will render service, as to the Lord, and not to men");
        currentSymptom.arrayReferences.add("Ephesians 6:7");
        currentSymptom.arrayVerseCards.add("Laziness3.png");

        currentSymptom.arrayVerses.add("In all labor there is profit, But mere talk  leads only to poverty.");
        currentSymptom.arrayReferences.add("Proverbs 14:23");
        currentSymptom.arrayVerseCards.add("Laziness4.png");

        currentSymptom.arrayVerses.add("But if anyone does not provide for his own, and especially for those of his household, he has denied the faith and is worse than an unbeliever.");
        currentSymptom.arrayReferences.add("1 Timothy 5:8");
        currentSymptom.arrayVerseCards.add("Laziness5.png");

        arraySymptomObjects.add(currentSymptom);

        // Greed
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Greed";

        currentSymptom.arrayVerses.add("He who loves money will not be satisfied with money, nor he who loves abundance  with its income. This too is vanity.");
        currentSymptom.arrayReferences.add("Ecclesiastes 5:10");
        currentSymptom.arrayVerseCards.add("Greed1.png");

        currentSymptom.arrayVerses.add("Make sure that your character is free from the love of money, being content with what you have; for He Himself has said, “I will never desert you, nor will I ever forsake you,”");
        currentSymptom.arrayReferences.add("Hebrews 13:5");
        currentSymptom.arrayVerseCards.add("Greed2.png");

        currentSymptom.arrayVerses.add("Then He said to them, “Beware, and be on your guard against every form of greed; for not even when one has an abundance does his life consist of his possessions.”");
        currentSymptom.arrayReferences.add("Luke 12:15");
        currentSymptom.arrayVerseCards.add("Greed3.png");

        currentSymptom.arrayVerses.add("No one can serve two masters; for either he will hate the one and love the other, or he will be devoted to one and despise the other. You cannot serve God and wealth.");
        currentSymptom.arrayReferences.add("Matthew 6:24");
        currentSymptom.arrayVerseCards.add("Greed4.png");

        currentSymptom.arrayVerses.add("He who is generous will be blessed, For he gives some of his food to the poor.");
        currentSymptom.arrayReferences.add("Proverbs 22:9");
        currentSymptom.arrayVerseCards.add("Greed5.png");

        arraySymptomObjects.add(currentSymptom);

        // Substance abuse
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Substance abuse";

        currentSymptom.arrayVerses.add("No temptation has overtaken you but such as is common to man; and God is faithful, who will not allow you to be tempted beyond what you are able, but with the temptation will provide the way of escape also, so that you will be able to endure it.");
        currentSymptom.arrayReferences.add("1 Corinthians 10:13");
        currentSymptom.arrayVerseCards.add("substanceAbuse1.png");

        currentSymptom.arrayVerses.add("Submit therefore to God. Resist the devil and he will flee from you.");
        currentSymptom.arrayReferences.add("James 4:7");
        currentSymptom.arrayVerseCards.add("substanceAbuse2.png");

        currentSymptom.arrayVerses.add("For you have been bought with a price: therefore glorify God in your body");
        currentSymptom.arrayReferences.add("1 Corinthians 6:20");
        currentSymptom.arrayVerseCards.add("substanceAbuse3.png");

        currentSymptom.arrayVerses.add("I can do all things through Him who strengthens me.");
        currentSymptom.arrayReferences.add("Philippians 4:13");
        currentSymptom.arrayVerseCards.add("substanceAbuse4.png");

        currentSymptom.arrayVerses.add("Keep watching and praying that you may not enter into temptation; the spirit is willing, but the flesh is weak.");
        currentSymptom.arrayReferences.add("Matthew 26:41");
        currentSymptom.arrayVerseCards.add("substanceAbuse5.png");

        arraySymptomObjects.add(currentSymptom);

        // Worrying
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Worrying";

        currentSymptom.arrayVerses.add("And do not lean on your own understanding. In all your ways acknowledge Him, And He will make your paths straight.");
        currentSymptom.arrayReferences.add("Proverbs 3:5-6");
        currentSymptom.arrayVerseCards.add("Worrying1.png");

        currentSymptom.arrayVerses.add("Be anxious for nothing, but in everything by prayer and supplication with thanksgiving let your requests be made known to God. And the peace of God, which surpasses all comprehension, will guard your hearts and your minds in Christ Jesus.");
        currentSymptom.arrayReferences.add("Philippians 4:6-7");
        currentSymptom.arrayVerseCards.add("Worrying2.png");

        currentSymptom.arrayVerses.add("Come to Me, all who are weary and heavy-laden, and I will give you rest. Take My yoke upon you and learn from Me, for I am gentle and humble in heart, and you will find rest for your souls. For My yoke is easy and My burden is light.");
        currentSymptom.arrayReferences.add("Matthew 11:28-30");
        currentSymptom.arrayVerseCards.add("Worrying3.png");

        currentSymptom.arrayVerses.add("Now may the Lord of peace Himself continually grant you peace in every circumstance. The Lord be with you all");
        currentSymptom.arrayReferences.add("2 Thessalonians 3:16");
        currentSymptom.arrayVerseCards.add("Worrying4.png");

        currentSymptom.arrayVerses.add("Cast your burden upon the Lord and He will sustain you; He will never allow the righteous to be shaken.");
        currentSymptom.arrayReferences.add("Psalm 55:22");
        currentSymptom.arrayVerseCards.add("Worrying5.png");

        arraySymptomObjects.add(currentSymptom);

        // Witnessing
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Witnessing";

        currentSymptom.arrayVerses.add("Let your light shine before men in such a way that they may see your good works, and glorify your Father who is in heaven.");
        currentSymptom.arrayReferences.add("Matthew 5:16");
        currentSymptom.arrayVerseCards.add("Witnessing1.png");

        currentSymptom.arrayVerses.add("Go therefore and make disciples of all the nations, baptizing them in the name of the Father and the Son and the Holy Spirit, teaching them to observe all that I commanded you; and lo, I am with you always, even to the end of the age");
        currentSymptom.arrayReferences.add("Matthew 28:19-20");
        currentSymptom.arrayVerseCards.add("Witnessing2.png");

        currentSymptom.arrayVerses.add("Let your speech always be with grace,  as though seasoned with salt, so that you will know how you should respond to each person.");
        currentSymptom.arrayReferences.add("Colossians 4:6");
        currentSymptom.arrayVerseCards.add("Witnessing3.png");

        currentSymptom.arrayVerses.add("And He said to them, “Go into all the world and preach the gospel to all creation.");
        currentSymptom.arrayReferences.add("Mark 16:15");
        currentSymptom.arrayVerseCards.add("Witnessing4.png");

        currentSymptom.arrayVerses.add("For I am not ashamed of the gospel, for it is the power of God for salvation to everyone who believes, to the Jew first and also to the Greek.");
        currentSymptom.arrayReferences.add("Romans 1:16");
        currentSymptom.arrayVerseCards.add("Witnessing5.png");

        arraySymptomObjects.add(currentSymptom);

        // Faith
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Faith";

        currentSymptom.arrayVerses.add("For I know the plans that I have for you,’ declares the Lord, ‘plans for welfare and not for calamity to give you a future and a hope.");
        currentSymptom.arrayReferences.add("Jeremiah 29:11");
        currentSymptom.arrayVerseCards.add("Faith1.png");

        currentSymptom.arrayVerses.add("And without faith it is impossible to please  Him, for he who comes to God must believe that He is and  that He is a rewarder of those who seek Him.");
        currentSymptom.arrayReferences.add("Hebrews 11:6");
        currentSymptom.arrayVerseCards.add("Faith2.png");

        currentSymptom.arrayVerses.add("But he must ask in faith without any doubting, for the one who doubts is like the surf of the sea, driven and tossed by the wind.");
        currentSymptom.arrayReferences.add("James 1:6");
        currentSymptom.arrayVerseCards.add("Faith3.png");

        currentSymptom.arrayVerses.add("For in it  the righteousness of God is revealed from faith to faith; as it is written, “But the righteous  man shall live by faith.”");
        currentSymptom.arrayReferences.add("Romans 1:17");
        currentSymptom.arrayVerseCards.add("Faith4.png");

        currentSymptom.arrayVerses.add("for we walk by faith, not by sight");
        currentSymptom.arrayReferences.add("2 Corinthians 5:7");
        currentSymptom.arrayVerseCards.add("Faith5.png");

        arraySymptomObjects.add(currentSymptom);

        // Loneliness
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Loneliness";

        currentSymptom.arrayVerses.add("Be strong and courageous, do not be afraid or tremble at them, for the Lord your God is the one who goes with you. He will not fail you or forsake you.");
        currentSymptom.arrayReferences.add("Deuteronomy 31:6");
        currentSymptom.arrayVerseCards.add("Loneliness1.png");

        currentSymptom.arrayVerses.add("Do not fear, for I am with you; Do not anxiously look about you, for I am your God. I will strengthen you, surely I will help you, Surely I will uphold you with my righteous right hand.");
        currentSymptom.arrayReferences.add("Isaiah 41:10");
        currentSymptom.arrayVerseCards.add("Loneliness2.png");

        currentSymptom.arrayVerses.add("casting all your anxiety on him, because he cares for you.");
        currentSymptom.arrayReferences.add("1 Peter 5:7");
        currentSymptom.arrayVerseCards.add("Loneliness3.png");

        currentSymptom.arrayVerses.add("The Lord is the one who goes ahead of you; He will be with you. He will not fail you or forsake you. Do not fear or be dismayed.");
        currentSymptom.arrayReferences.add("Deuteronomy 31:8");
        currentSymptom.arrayVerseCards.add("Loneliness4.png");

        currentSymptom.arrayVerses.add("Why are you in despair, O my soul and why are you disturbed within me? Hope in God, for I shall again praise him, hhe help of my countenance and my God.");
        currentSymptom.arrayReferences.add("Psalm 43:5");
        currentSymptom.arrayVerseCards.add("Loneliness5.png");

        arraySymptomObjects.add(currentSymptom);

        // Jealousy
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Jealousy";

        currentSymptom.arrayVerses.add("Love is patient, love is kind and is not jealous; love does not brag and is not arrogant");
        currentSymptom.arrayReferences.add("1 Corinthians 13:4");
        currentSymptom.arrayVerseCards.add("Jealousy1.png");

        currentSymptom.arrayVerses.add("But if you have bitter jealousy and selfish ambition in your heart, do not be arrogant and so lie against the truth. This wisdom is not that which comes down from above, but is earthly, natural, demonic. For where jealousy and selfish ambition exist, there is disorder and every evil thing.");
        currentSymptom.arrayReferences.add("James 3:14-16");
        currentSymptom.arrayVerseCards.add("Jealousy2.png");

        currentSymptom.arrayVerses.add("for you are still fleshly. For since there is jealousy and strife among you, are you not fleshly, and are you not walking like mere men?");
        currentSymptom.arrayReferences.add("1 Corinthians 3:3");
        currentSymptom.arrayVerseCards.add("Jealousy3.png");

        currentSymptom.arrayVerses.add("Therefore, putting aside all malice and all deceit and hypocrisy and envy and all slander");
        currentSymptom.arrayReferences.add("1 Peter 2:1");
        currentSymptom.arrayVerseCards.add("Jealousy4.png");

        currentSymptom.arrayVerses.add("For this, “You shall not commit adultery, You shall not murder, You shall not steal,  You shall not covet ,” and if there is any other commandment, it is summed up in this saying, “You shall love your neighbor as yourself.”");
        currentSymptom.arrayReferences.add("Romans 13:9");
        currentSymptom.arrayVerseCards.add("Jealousy5.png");

        arraySymptomObjects.add(currentSymptom);

        // Time Management
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Time Management";

        currentSymptom.arrayVerses.add("Therefore be careful how you walk, not as unwise men but as wise, making the most of your time, because the days are evil.");
        currentSymptom.arrayReferences.add("Ephesians 5:15-16");
        currentSymptom.arrayVerseCards.add("TimeManagement1.png");

        currentSymptom.arrayVerses.add("Yet you do not know what your life will be like tomorrow. You are  just a vapor that appears for a little while and then vanishes away.");
        currentSymptom.arrayReferences.add("James 4:14");
        currentSymptom.arrayVerseCards.add("TimeManagement2.png");

        currentSymptom.arrayVerses.add("So teach us to number our days, That we may present to You a heart of wisdom.");
        currentSymptom.arrayReferences.add("Psalm 90:12");
        currentSymptom.arrayVerseCards.add("TimeManagement3.png");

        currentSymptom.arrayVerses.add("There is an appointed time for everything. And there is a time for every event under heaven.");
        currentSymptom.arrayReferences.add("Ecclesiastes 3:1");
        currentSymptom.arrayVerseCards.add("TimeManagement4.png");

        currentSymptom.arrayVerses.add("Therefore be careful how you walk, not as unwise men but as wise, making the most of your time, because the days are evil.");
        currentSymptom.arrayReferences.add("Ephesians 5:15-16");
        currentSymptom.arrayVerseCards.add("TimeManagement5.png");

        arraySymptomObjects.add(currentSymptom);

        // Self-Worth
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Self-Worth";

        currentSymptom.arrayVerses.add("I will give thanks to You, for I am fearfully and wonderfully made; Wonderful are Your works, And my soul knows it very well.");
        currentSymptom.arrayReferences.add("Psalm 139:14");
        currentSymptom.arrayVerseCards.add("selfWorth1.png");

        currentSymptom.arrayVerses.add("just as he chose us in him before the foundation of the world, that we would be holy and blameless before him. In love");
        currentSymptom.arrayReferences.add("Ephesians 1:4");
        currentSymptom.arrayVerseCards.add("selfWorth2.png");

        currentSymptom.arrayVerses.add("Know that the Lord Himself is God; It is he who has made us, and not we ourselves;  We are his people and the sheep of his pasture.");
        currentSymptom.arrayReferences.add("Psalm 100:3");
        currentSymptom.arrayVerseCards.add("selfWorth3.png");

        currentSymptom.arrayVerses.add("For you have been bought with a price: therefore glorify God in your body.");
        currentSymptom.arrayReferences.add("1 Corinthians 6:20");
        currentSymptom.arrayVerseCards.add("selfWorth4.png");

        currentSymptom.arrayVerses.add("But we all, with unveiled face, beholding as in a mirror the glory of the Lord, are being transformed into the same image from glory to glory, just as from the Lord, the Spirit.");
        currentSymptom.arrayReferences.add("2 Corinthians 3:18");
        currentSymptom.arrayVerseCards.add("selfWorth5.png");

        arraySymptomObjects.add(currentSymptom);

        // Loving Others
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Loving Others";

        currentSymptom.arrayVerses.add("But love your enemies, and do good, and lend, expecting nothing in return; and your reward will be great,");
        currentSymptom.arrayReferences.add("Luke 6:35");
        currentSymptom.arrayVerseCards.add("lovingOthers1.png");

        currentSymptom.arrayVerses.add("Love does no wrong to a neighbor; therefore love is the fulfillment of the law.");
        currentSymptom.arrayReferences.add("Romans 13:10");
        currentSymptom.arrayVerseCards.add("lovingOthers2.png");

        currentSymptom.arrayVerses.add("Love is patient, love is kind and is not jealous; love does not brag and is not arrogant");
        currentSymptom.arrayReferences.add("1 Corinthians 13:4");
        currentSymptom.arrayVerseCards.add("lovingOthers3.png");

        currentSymptom.arrayVerses.add("The one who does not love does not know God, for God is love.");
        currentSymptom.arrayReferences.add("1 John 4:8");
        currentSymptom.arrayVerseCards.add("lovingOthers4.png");

        currentSymptom.arrayVerses.add("The second is this, ‘You shall love your neighbor as yourself.’ There is no other commandment greater than these.");
        currentSymptom.arrayReferences.add("Mark 12:31");
        currentSymptom.arrayVerseCards.add("lovingOthers5.png");

        arraySymptomObjects.add(currentSymptom);

        // Putting God First
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Putting God First";

        currentSymptom.arrayVerses.add("In all your ways acknowledge him, And He will make your paths straight.");
        currentSymptom.arrayReferences.add("Proverbs 3:6");
        currentSymptom.arrayVerseCards.add("puttingGodFirst1.png");

        currentSymptom.arrayVerses.add("But seek first his kingdom and his righteousness, and all these things will be added to you.");
        currentSymptom.arrayReferences.add("Matthew 6:33");
        currentSymptom.arrayVerseCards.add("puttingGodFirst2.png");

        currentSymptom.arrayVerses.add("You shall love the Lord your God with all your heart and with all your soul and with all your might.");
        currentSymptom.arrayReferences.add("Deuteronomy 6:5");
        currentSymptom.arrayVerseCards.add("puttingGodFirst3.png");

        currentSymptom.arrayVerses.add("Whether, then, you eat or drink or whatever you do, do all to the glory of God.");
        currentSymptom.arrayReferences.add("1 Corinthians 10:31");
        currentSymptom.arrayVerseCards.add("puttingGodFirst4.png");

        currentSymptom.arrayVerses.add("He must increase, but I must decrease.");
        currentSymptom.arrayReferences.add("John 3:30");
        currentSymptom.arrayVerseCards.add("puttingGodFirst5.png");

        arraySymptomObjects.add(currentSymptom);

        // Selfishness
        currentSymptom = new SymptomObject();
        currentSymptom.symptomName = "Selfishness";

        currentSymptom.arrayVerses.add("Do nothing from selfishness or empty conceit, but with humility of mind regard one another as more important than yourselves");
        currentSymptom.arrayReferences.add("Philippians 2:3");
        currentSymptom.arrayVerseCards.add("Selfishness1.png");

        currentSymptom.arrayVerses.add("Love is patient, love is kind  and is not jealous; love does not brag and is not arrogant, does not act unbecomingly; it does not seek its own, is not provoked, does not take into account a wrong suffered");
        currentSymptom.arrayReferences.add("1 Corinthians 13:4-5");
        currentSymptom.arrayVerseCards.add("Selfishness2.png");

        currentSymptom.arrayVerses.add("Let no one seek his own good, but that of his neighbor.");
        currentSymptom.arrayReferences.add("1 Corinthians 10:24");
        currentSymptom.arrayVerseCards.add("Selfishness3.png");

        currentSymptom.arrayVerses.add("Bear one another’s burdens, and thereby fulfill the law of Christ.");
        currentSymptom.arrayReferences.add("Galatians 6:2");
        currentSymptom.arrayVerseCards.add("Selfishness4.png");

        currentSymptom.arrayVerses.add("And do not neglect doing good and sharing, for with such sacrifices God is pleased.");
        currentSymptom.arrayReferences.add("Hebrews 13:16");
        currentSymptom.arrayVerseCards.add("Selfishness5.png");

        arraySymptomObjects.add(currentSymptom);

        // Clean Memory
        currentSymptom = null;
        System.gc();
    }

}
