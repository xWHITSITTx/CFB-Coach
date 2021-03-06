/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Positions;

import java.util.ArrayList;

import Simulation.Team;

public class PlayerK extends Player {

    //KickPow affects how far he can kick
    public int ratKickPow;
    //KickAcc affects how accurate his kicks are
    public int ratKickAcc;
    //KickFum affects how often he can fumble the snap
    public int ratKickFum;
    public int ratPressure;

    //Stats
    public int statsXPAtt;
    public int statsXPMade;
    public int statsFGAtt;
    public int statsFGMade;

    public int careerXPAtt;
    public int careerXPMade;
    public int careerFGAtt;
    public int careerFGMade;

    //Size Config
    private int hAvg = 69;
    private int hMax = 4;
    private int hMin = -5;
    private int wAvg = 165;
    private int wMax = 35;
    private int wMin = 15;

    public PlayerK(Team t, String nm, int yr, int reg, int trait, int iq, int scout, boolean transfer, boolean wasRS, int pot, int dur, boolean rs, int pow, int acc, int fum, int prs, int h, int w) {
        position = "K";
        team = t;
        name = nm;
        year = yr;

        ratOvr = (pow + acc + prs) / 3;
        ratPot = pot;
        ratFootIQ = iq;
        ratDur = dur;
        ratKickPow = pow;
        ratKickAcc = acc;
        ratKickFum = fum;
        ratPressure = prs;
        isRedshirt = rs;
        if (isRedshirt) year = 0;
        wasRedshirt = wasRS;

        region = reg;
        personality = trait;
        recruitRating = scout;
        height = h;
        weight = w;

        resetSeasonStats();
        resetCareerStats();
    }

    public PlayerK(Team t, String nm, int yr, int reg, int trait, int iq, int scout, boolean transfer, boolean wasRS, boolean wo, int pot, int dur, boolean rs, int cGamesPlayed, int cWins, int cHeismans, int cAA, int cAC, int cTF, int cAF,
                    int pow, int acc, int fum, int prs, int h, int w, int cXPA, int cXPM, int cFGA, int cFGM) {
        position = "K";
        team = t;
        name = nm;
        year = yr;

        ratOvr = (pow + acc + prs) / 3;
        ratPot = pot;
        ratFootIQ = iq;
        ratDur = dur;
        ratKickPow = pow;
        ratKickAcc = acc;
        ratKickFum = fum;
        ratPressure = prs;
        isRedshirt = rs;
        if (isRedshirt) year = 0;
        wasRedshirt = wasRS;

        isTransfer = transfer;
        isWalkOn = wo;
        region = reg;
        personality = trait;
        recruitRating = scout;
        height = h;
        weight = w;

        resetSeasonStats();

        careerXPAtt = cXPA;
        careerXPMade = cXPM;
        careerFGAtt = cFGA;
        careerFGMade = cFGM;
        careerGames = cGamesPlayed;
        careerHeismans = cHeismans;
        careerAllAmerican = cAA;
        careerAllConference = cAC;
        careerTopFreshman = cTF;
        careerAllFreshman = cAF;
        careerWins = cWins;
    }

    public PlayerK(String nm, int yr, int stars, Team t) {
        position = "K";
        height = hAvg + 	(int)(Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + 	(int)(Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        ratPot = (int) (attrBase + 50 * Math.random());
        ratFootIQ = (int) (attrBase + 50 * Math.random());
        ratDur = (int) (attrBase + 50 * Math.random());
        ratKickPow = (int) (ratBase + year*yearFactor + stars*starFactor - ratTolerance*Math.random());
        ratKickAcc = (int) (ratBase + year*yearFactor + stars*starFactor - ratTolerance*Math.random());
        ratKickFum = (int) (ratBase + year*yearFactor + stars*starFactor - ratTolerance*Math.random());
        ratPressure = (int) (ratBase + year*yearFactor + stars*starFactor - ratTolerance*Math.random());
        ratOvr = (ratKickPow + ratKickAcc + ratPressure) / 3;
        region = (int)(Math.random()*5);
        personality = (int) (attrBase + 50 * Math.random());

        recruitRating = getScoutingGrade();

        recruitTolerance = (int)((60 - team.teamPrestige)/kImportance);
        cost = (int)((Math.pow((float) ratOvr - costBaseRating, 2)/5) + (int)Math.random()*recruitTolerance);

        cost = (int)(cost/kImportance);

        double locFactor = Math.abs(team.location - region) - 2.5;
        cost = cost + (int)(Math.random()*(locFactor * locationDiscount));
        if (cost < 0) cost = (int)Math.random()*7+1;

        resetSeasonStats();
        resetCareerStats();
    }

    public PlayerK(String nm, int yr, int stars, Team t, boolean custom) {
        position = "K";
        height = hAvg + 	(int)(Math.random() * ((hMax - hMin) + 1)) + hMin;
        weight = wAvg + 	(int)(Math.random() * ((wMax - wMin) + 1)) + wMin;
        name = nm;
        year = yr;
        team = t;

        ratPot = (int) (attrBase + 50 * Math.random());
        ratFootIQ = (int) (attrBase + 50 * Math.random());
        ratDur = (int) (attrBase + 50 * Math.random());
        ratKickPow = (int) (ratBase + stars * customFactor - ratTolerance * Math.random());
        ratKickAcc = (int) (ratBase + stars * customFactor - ratTolerance * Math.random());
        ratKickFum = (int) (ratBase + stars * customFactor - ratTolerance * Math.random());
        ratPressure = (int) (ratBase + stars * customFactor - ratTolerance * Math.random());
        ratOvr = (ratKickPow + ratKickAcc + 75) / 3;
        region = (int)(Math.random()*5);
        personality = (int) (attrBase + 50 * Math.random());

        if(custom) isWalkOn = true;
        recruitRating = getScoutingGrade();

        resetSeasonStats();
        resetCareerStats();
    }
    
    @Override
    public void advanceSeason() {
        int oldOvr = ratOvr;
        progression = (ratPot * 3 + team.HC.get(0).ratTalent * 2 + team.HC.get(0).ratOff) / 6;
        int games = gamesStarted + (gamesPlayed-gamesStarted)/3;

        if (!isMedicalRS) {
            year++;
            if (wonAllConference) ratPot += (int)Math.random()*allConfPotBonus;
            if (wonAllAmerican) ratPot += (int)Math.random()*allAmericanBonus;
            if (wonAllFreshman) ratPot += (int)Math.random()*allFreshmanBonus;
            if (wonTopFreshman) ratPot += (int)Math.random()*topBonus;
            if (wonHeisman) ratPot += (int)Math.random()*topBonus;

            if (year > 2 && games < minGamesPot) ratPot -= (int) (Math.random() * 15);

            ratFootIQ += (int) (Math.random() * (progression + games - 35)) / 10;
            ratKickPow += (int) (Math.random() * (progression + games - 35)) / 10;
            ratKickAcc += (int) (Math.random() * (progression + games - 35)) / 10;
            ratKickFum += (int) (Math.random() * (progression + games - 35)) / 10;
            ratPressure += (int) (Math.random() * (progression + games - 35)) / 10;

            if (Math.random() * 100 < progression) {
                //breakthrough
                ratKickPow += (int) (Math.random() * (progression + games - 40)) / 10;
                ratKickAcc += (int) (Math.random() * (progression + games - 40)) / 10;
                ratKickFum += (int) (Math.random() * (progression + games - 40)) / 10;
                ratPressure += (int) (Math.random() * (progression + games - 40)) / 10;
            }
        }
        
        ratOvr = (ratKickPow + ratKickAcc + ratPressure) / 3;
        ratImprovement = ratOvr - oldOvr;
        //reset stats (keep career stats?)
        careerXPAtt += statsXPAtt;
        careerXPMade += statsXPMade;
        careerFGAtt += statsFGAtt;
        careerFGMade += statsFGMade;
        careerGames += gamesPlayed;
        careerWins += statsWins;

        if (wonHeisman) careerHeismans++;
        if (wonAllAmerican) careerAllAmerican++;
        if (wonAllConference) careerAllConference++;
        if (wonAllFreshman) careerAllFreshman++;
        if (wonTopFreshman) careerTopFreshman++;

        resetSeasonStats();

        if (isTransfer) {
            isTransfer = false;
            year -= 1;
        }

        if (isRedshirt) wasRedshirt = true;

    }

    private void resetSeasonStats() {
        gamesStarted = 0;
        gamesPlayed = 0;
        isInjured = false;
        troubledTimes = 0;

        statsXPAtt = 0;
        statsXPMade = 0;
        statsFGAtt = 0;
        statsFGMade = 0;
        wonHeisman = false;
        wonAllAmerican = false;
        wonAllConference = false;
        wonAllFreshman = false;
        wonTopFreshman = false;
        statsWins = 0;
    }

    private void resetCareerStats() {
        careerXPAtt = 0;
        careerXPMade = 0;
        careerFGAtt = 0;
        careerFGMade = 0;
        careerGames = 0;
        careerHeismans = 0;
        careerAllAmerican = 0;
        careerAllConference = 0;
        careerAllFreshman = 0;
        careerTopFreshman = 0;
        careerWins = 0;
    }

    @Override
    public int getHeismanScore() {
        return (statsFGMade * 20 + statsXPMade*5) * (int)getFGpct()/100 + ratOvr * 10;
    }

    @Override
    public int getCareerScore() {
        return (statsFGMade * 20 + statsXPMade*5) * (int)getFGpct()/100 + ratOvr * 10 + (careerFGMade * 25 + careerXPMade *5) * (int)(getCareerFGpct()/100) + ratOvr*10*year;
    }

    public double getFGpct() {
        if (statsFGAtt < 1) {
            return 0;
        } else {

            int rating = 100 * statsFGMade / (statsFGAtt);
            return rating;
        }
    }

    public double getCareerFGpct() {
        if (statsFGAtt + careerFGAtt < 1) {
            return 0;
        } else {

            int rating = 100 * (statsFGMade + careerFGMade) / (statsFGAtt + careerFGAtt);
            return rating;
        }
    }

    @Override
    public ArrayList<String> getDetailStatsList(int games) {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Height " + getHeight() + ">Weight: " + getWeight());
        if (statsXPAtt > 0) {
            pStats.add("XP Made/Att: " + statsXPMade + "/" + statsXPAtt + ">XP Percent: " + (100 * statsXPMade / (statsXPAtt)) + "%");
        } else {
            pStats.add("XP Made/Att: 0/0>XP Percent: 0%");
        }

        if (statsFGAtt > 0) {
            pStats.add("FG Made/Att: " + statsFGMade + "/" + statsFGAtt + ">FG Percent: " + (100 * statsFGMade / statsFGAtt + "%"));
        } else {
            pStats.add("FG Made/Att: 0/0>FG Percent: 0%");
        }
        pStats.add("Games: " + gamesPlayed + " (" + statsWins + "-" + (gamesStarted - statsWins) + ")" + "> ");
        pStats.add("Kick Strength: " + getLetterGrade(ratKickPow) + ">Kick Accuracy: " + getLetterGrade(ratKickAcc));
        pStats.add("Clumsiness: " + getLetterGrade(ratKickFum) + ">Pressure: " + getLetterGrade(ratPressure));
        pStats.add("Durability: " + getLetterGrade(ratDur) + ">Football IQ: " + getLetterGrade(ratFootIQ));
        pStats.add("Home Region: " + getRegion(region) + ">Personality: " + getLetterGrade(personality));
        pStats.add("Scout Grade: " + getScoutingGradeString() + " > " + getStatus());
        pStats.add(" > ");
        return pStats;
    }

    @Override
    public ArrayList<String> getDetailAllStatsList(int games) {
        ArrayList<String> pStats = new ArrayList<>();
        pStats.add("Height " + getHeight() + ">Weight: " + getWeight());
        if (statsXPAtt > 0) {
            pStats.add("XP Made/Att: " + statsXPMade + "/" + statsXPAtt + ">XP Percent: " + (100 * statsXPMade / (statsXPAtt)) + "%");
        } else {
            pStats.add("XP Made/Att: 0/0>XP Percent: 0%");
        }

        if (statsFGAtt > 0) {
            pStats.add("FG Made/Att: " + statsFGMade + "/" + statsFGAtt + ">FG Percent: " + (100 * statsFGMade / statsFGAtt + "%"));
        } else {
            pStats.add("FG Made/Att: 0/0>FG Percent: 0%");
        }
        pStats.add("Games: " + gamesPlayed + " (" + statsWins + "-" + (gamesStarted - statsWins) + ")" + "> ");
        pStats.add("Kick Strength: " + getLetterGrade(ratKickPow) + ">Kick Accuracy: " + getLetterGrade(ratKickAcc));
        pStats.add("Clumsiness: " + getLetterGrade(ratKickFum) + ">Pressure: " + getLetterGrade(ratPressure));
        pStats.add("Durability: " + getLetterGrade(ratDur) + ">Football IQ: " + getLetterGrade(ratFootIQ));
        pStats.add("Home Region: " + getRegion(region) + ">Personality: " + getLetterGrade(personality));
        pStats.add("Scout Grade: " + getScoutingGradeString() + " > " + getStatus());
        pStats.add(" > ");
        pStats.add("[B]CAREER STATS:");
        pStats.addAll(getCareerStatsList());
        return pStats;
    }

    @Override
    public ArrayList<String> getCareerStatsList() {
        ArrayList<String> pStats = new ArrayList<>();
        if ((statsXPAtt + careerXPAtt) > 0) {
            pStats.add("XP Made/Att: " + (statsXPMade + careerXPMade) + "/" + (statsXPAtt + careerXPAtt) +
                    ">XP Percentage: " + (100 * (statsXPMade + careerXPMade) / (statsXPAtt + careerXPAtt)) + "%");
        } else {
            pStats.add("XP Made/Att: 0/0>XP Percentage: 0%");
        }

        if ((statsFGAtt + careerFGAtt) > 0) {
            pStats.add("FG Made/Att: " + (statsFGMade + careerFGMade) + "/" + (statsFGAtt + careerFGAtt) +
                    ">FG Percentage: " + (100 * (statsFGMade + careerFGMade) / (statsFGAtt + careerFGAtt) + "%"));
        } else {
            pStats.add("FG Made/Att: 0/0>FG Percentage: 0%");
        }
        pStats.addAll(super.getCareerStatsList());
        return pStats;
    }

    @Override
    public String getInfoForLineup() {
        if (injury != null)
            return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(ratPot, ratOvr, year, team.HC.get(0).ratTalent) + " " + injury.toString();
        return getInitialName() + " [" + getYrStr() + "] " + ratOvr + "/" + getPotRating(ratPot, ratOvr, year, team.HC.get(0).ratTalent) + " (" +
                getLetterGrade(ratKickPow) + ", " + getLetterGrade(ratKickAcc) + ", " + getLetterGrade(ratKickFum) + ", " + getLetterGrade(ratPressure) + ")";
    }
}
