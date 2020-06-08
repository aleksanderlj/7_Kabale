package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;

import java.util.ArrayList;

public class TableauMovement {

    private ArrayList<ArrayList<Card>> tableauDecks;
    private Card topDeckCard;

    public TableauMovement(ArrayList<ArrayList<Card>> tableauDecks, Card topDeckCard) {
        this.tableauDecks = tableauDecks;
        this.topDeckCard = topDeckCard;
    }


    public boolean topdeckToTableau() {
        // Check om topdeck kortet kan lægges ned på et tableaudeck.

        for (int i = 0; i < tableauDecks.size(); i++) {
            if (tableauDecks.get(i).get(tableauDecks.get(i).size() - 1).getValue() == topDeckCard.getValue()+1
                    && tableauDecks.get(i).get(tableauDecks.get(i).size() - 1).getSuit()
                    .equals(topDeckCard.getSuit())) {
                        System.out.println("Move " + topDeckCard + " from topdeck to tableaudeck " + (i + 1));
                        return true;
            }
        }
        return false;
    }


    public boolean tableauToTableau() {
        /* Check om forreste kort i tableaudeck, hvor tableaudecket kun har et shown card,
        kan rykkes over på et andet tableaudeck - vend derefter det bagvedliggende kort. */

        for (int i = 0; i < tableauDecks.size(); i++) {
            Card cardToSearch = tableauDecks.get(i).get(tableauDecks.get(i).size() - 1);

                for (int j = i; j < tableauDecks.size(); j++) {
                    if (tableauDecks.get(j).get(tableauDecks.get(j).size() - 1).getValue()
                            == cardToSearch.getValue()+1
                            && tableauDecks.get(j).get(tableauDecks.get(j).size() - 1)
                            .getSuit().equals(cardToSearch.getSuit())){
                        System.out.println("Move " + cardToSearch.toString() + " from tableaudeck "
                                + (i+1) + " to tableaudeck " + (j+1));
                        return true;
                    }
                }
        }
        return false;
    }

}