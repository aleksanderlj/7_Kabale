package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;

import java.util.ArrayList;

public class TableauMovement {

    private ArrayList<ArrayList<Card>> tableauRows;
    private Card topDeckCard;

    public TableauMovement(ArrayList<ArrayList<Card>> tableauRows, Card topDeckCard) {
        this.tableauRows = tableauRows;
        this.topDeckCard = topDeckCard;
    }


    public boolean topdeckToTableau() {
        // Check om topdeck kortet kan lægges ned på en tableau row.

        for (int i = 0; i < tableauRows.size(); i++) {
            if (tableauRows.get(i).get(tableauRows.get(i).size() - 1).getValue() == topDeckCard.getValue()+1
                    && tableauRows.get(i).get(tableauRows.get(i).size() - 1).getSuit()
                    .equals(topDeckCard.getSuit())) {
                        System.out.println("Move " + topDeckCard + " from top deck to tableau row " + (i + 1));
                        return true;
            }
        }
        return false;
    }


    public boolean tableauToTableau() {
        /* Check om forreste kort i tableau row, hvor tableau row kun har et shown card,
        kan rykkes over på en anden tableau row - vend derefter det bagvedliggende kort. */

        for (int i = 0; i < tableauRows.size(); i++) {
            Card cardToSearch = tableauRows.get(i).get(tableauRows.get(i).size() - 1);

                for (int j = 0; j < tableauRows.size(); j++) {
                    if (tableauRows.get(j).get(tableauRows.get(j).size() - 1).getValue()
                            == cardToSearch.getValue()+1
                            && tableauRows.get(j).get(tableauRows.get(j).size() - 1)
                            .getSuit().equals(cardToSearch.getSuit())){
                        System.out.println("Move " + cardToSearch.toString() + " from tableau row "
                                + (i+1) + " to tableau row " + (j+1));
                        return true;
                    }
                }
        }
        return false;
    }

}