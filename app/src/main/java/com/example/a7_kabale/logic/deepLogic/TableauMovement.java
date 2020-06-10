package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;


public class TableauMovement {

    private LogicState logicState;

    public TableauMovement(LogicState logicState) {
        this.logicState = logicState;
    }


    public boolean topdeckToTableau() {
        // Check om topdeck kortet kan lægges ned på en tableau row.

        for (int i = 0; i < logicState.getTableauRows().size(); i++) {
            if (logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getValue() == logicState.getTopDeckCard().getValue()+1
                    && logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1).getSuit()
                    .equals(logicState.getTopDeckCard().getSuit())) {
                        System.out.println("Move " + logicState.getTopDeckCard() + " from top deck to tableau row " + (i + 1));
                        return true;
            }
        }
        return false;
    }

    public boolean tableauToTableauHiddenCard() {
        /* Check om forreste kort i tableau row, hvor tableau row kun har et shown card,
        kan rykkes over på en anden tableau row - vend derefter det bagvedliggende kort. */

        for (int i = 0; i < logicState.getTableauRows().size(); i++) {
            if (logicState.getArrayofHiddenCards()[i] > 0 && logicState.getTableauRows().get(i).size() == 1){

                Card cardToSearch = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);

                for (int j = 0; j < logicState.getTableauRows().size(); j++) {
                    if (logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1).getValue() == cardToSearch.getValue() + 1 &&
                            logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1).getSuit().equals(cardToSearch.getSuit())) {
                        System.out.println("Move " + cardToSearch.toString() + " from tableau row "
                                + (i + 1) + " to tableau row " + (j + 1) + " and flip hidden card");
                        return true;
                    }
                }
            }
        }
        return false;
    }



    public boolean tableauToTableau() {
        /* Check om forreste kort i tableau deck, kan rykkes over på et andet tableau deck,
        sålænge der ikke skabes et infinite loop. */

        for (int i = 0; i < logicState.getTableauRows().size(); i++) {
            Card cardToSearch = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);

                for (int j = 0; j < logicState.getTableauRows().size(); j++) {
                    if (logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1).getValue()
                            == cardToSearch.getValue()+1
                            && logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1)
                            .getSuit().equals(cardToSearch.getSuit())) {
                        if (checkBehindTabToFou(cardToSearch)) {
                            System.out.println("Move " + cardToSearch.toString() + " from tableau row "
                                    + (i + 1) + " to tableau row " + (j + 1));
                            return true;
                        }
                    }
                }
        }
        return false;
    }


    // Checker bagvedliggende kort kan lægges op på foundation decks:
    private boolean checkBehindTabToFou(Card card){
        switch (card.getSuit()) {
            case "Diamonds":
                return logicState.getFoundationsDeckDiamonds() != null && logicState.getFoundationsDeckDiamonds().getValue() == card.getValue() - 1;
            case "Hearts":
                return logicState.getFoundationsDeckHearts() != null && logicState.getFoundationsDeckDiamonds().getValue() == card.getValue() - 1;
            case "Clubs":
                return logicState.getFoundationsDeckClubs() != null && logicState.getFoundationsDeckDiamonds().getValue() == card.getValue() - 1;
            case "Spades":
                return logicState.getFoundationsDeckSpades() != null && logicState.getFoundationsDeckDiamonds().getValue() == card.getValue() - 1;
        }
        return false;
    }

}