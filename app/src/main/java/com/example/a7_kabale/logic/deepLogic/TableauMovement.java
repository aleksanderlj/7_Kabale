package com.example.a7_kabale.logic.deepLogic;

import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.LogicState;


public class TableauMovement {

    private LogicState logicState;
    private Card temporaryCard;

    public TableauMovement() {
    }


    public String topdeckToTableau() {
        // Check om topdeck kortet kan lægges ned på en tableau row.
        if (logicState.getTopDeckCard() == null)
            return null;
        for (int i = 0; i < logicState.getTableauRows().size(); i++) {
            if (logicState.getTableauRows().get(i).size() != 0) {
                temporaryCard = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);
                if (temporaryCard.getValue() == logicState.getTopDeckCard().getValue() + 1
                        && temporaryCard.isRed() != logicState.getTopDeckCard().isRed()) {
                    logicState.setTotalCardsInTopDeck(logicState.getTotalCardsInTopDeck() - 1);
                    return "Move " + logicState.getTopDeckCard().toString() + " to "
                            + logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);
                }
            }
        }
        return null;
    }

    public String tableauToTableauHiddenCard() {
        /* Check om forreste kort i tableau row, hvor tableau row kun har et shown card,
        kan rykkes over på en anden tableau row - vend derefter det bagvedliggende kort. */
        int mostHidden = 0, fromRow = 0, toRow = 0;
        Card bestCard = null;

        for (int i = 0; i < logicState.getTableauRows().size(); i++) {
            if (logicState.getHiddenCards()[i] > 0 && logicState.getTableauRows().get(i).size() == 1) {

                Card cardToSearch = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);

                for (int j = 0; j < logicState.getTableauRows().size(); j++) {
                    if (logicState.getTableauRows().get(j).size() != 0) {
                        temporaryCard = logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1);
                        if (temporaryCard.getValue() == cardToSearch.getValue() + 1
                                && temporaryCard.isRed() != cardToSearch.isRed() && mostHidden <= logicState.getHiddenCards()[i]) {
                            bestCard = cardToSearch;
                            mostHidden = logicState.getHiddenCards()[i];
                            fromRow = i;
                            toRow = j;
                        }
                    }
                }
            }
        }
        if (mostHidden != 0) {
            int[] newHiddenCards = logicState.getHiddenCards();
            newHiddenCards[fromRow] = newHiddenCards[fromRow] - 1;
            logicState.setHiddenCards(newHiddenCards);
            return "Move " + bestCard.toString() + " to " +
                    logicState.getTableauRows().get(toRow).get(logicState.getTableauRows().get(toRow).size() - 1);
        }
        return null;
    }


    public String tableauToTableau() {
        /* Check om forreste kort i tableau deck, kan rykkes over på et andet tableau deck,
        sålænge der ikke skabes et infinite loop. */

        for (int i = 0; i < logicState.getTableauRows().size(); i++) {
            if (logicState.getTableauRows().get(i).size() >= 1) {
                Card cardToSearch = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);

                for (int j = 0; j < logicState.getTableauRows().size(); j++) {
                    if (logicState.getTableauRows().get(j).size() != 0) {
                        temporaryCard = logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1);
                        if (temporaryCard.getValue() == cardToSearch.getValue() + 1
                                && temporaryCard.isRed() != cardToSearch.isRed()) {
                            if (logicState.getTableauRows().get(i).size() == 1 && logicState.getHiddenCards()[i] != 0) {
                                return "Move " + cardToSearch.toString() + " to " +
                                        logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1);
                            } else if (logicState.getTableauRows().get(i).size() == 1 && logicState.getHiddenCards()[i] == 0) {
                                if (logicState.getTopDeckCard().getValue() == 13) {
                                    return "Move " + cardToSearch.toString() + " to " +
                                            logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1);
                                }
                                for (int k = 0; k < logicState.getTableauRows().size(); k++) {
                                    if (logicState.getTableauRows().get(k).size() != 0 &&
                                            logicState.getTableauRows().get(k).get(0).getValue() == 13 && logicState.getHiddenCards()[k] != 0) {
                                        return "Move " + cardToSearch.toString() + " to " +
                                                logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1);
                                    }
                                }
                            } else if (checkBehindTabToFou(logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 2))) {
                                return "Move " + cardToSearch.toString() + " to " +
                                        logicState.getTableauRows().get(j).get(logicState.getTableauRows().get(j).size() - 1);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public String tabRowToTabRow() {
        /*
        Check om række af kort i tableau deck, kan rykkes over på et andet tableau deck,
        sålænge der ikke skabes et infinite loop. Undgå loopet ved at tjekke,
        at bagvedliggende kort kan bruges til foundations row.
         */

        for (int tableauRow = 0; tableauRow < logicState.getTableauRows().size(); tableauRow++) {
            for (int cardPlacement = 0; cardPlacement <= logicState.getTableauRows().get(tableauRow).size() - 2; cardPlacement++) {
                if (logicState.getTableauRows().get(tableauRow).get(cardPlacement) != null
                        && checkBehindTabToTab(logicState.getTableauRows().get(tableauRow).get(cardPlacement), tableauRow, cardPlacement) != null) {
                    return checkBehindTabToTab(logicState.getTableauRows().get(tableauRow).get(cardPlacement), tableauRow, cardPlacement);
                }
            }
        }
        return null;
    }


    // Checker bagvedliggende kort kan lægges op på foundation decks:
    private boolean checkBehindTabToFou(Card card) {
        switch (card.getSuit()) {
            case "Diamonds":
                return logicState.getFoundationsDeckDiamonds() != null && logicState.getFoundationsDeckDiamonds().getValue() == card.getValue() - 1;
            case "Hearts":
                return logicState.getFoundationsDeckHearts() != null && logicState.getFoundationsDeckHearts().getValue() == card.getValue() - 1;
            case "Clubs":
                return logicState.getFoundationsDeckClubs() != null && logicState.getFoundationsDeckClubs().getValue() == card.getValue() - 1;
            case "Spades":
                return logicState.getFoundationsDeckSpades() != null && logicState.getFoundationsDeckSpades().getValue() == card.getValue() - 1;
        }
        return false;
    }

    private String checkBehindTabToTab(Card card, int tableauRow, int cardPlacement) {
          /* Check om bagerste kort i række af kort i tableau row, kan rykkes over på et andet tableau deck,
        sålænge der ikke skabes et infinite loop. */

        for (int i = 0; i < logicState.getTableauRows().size(); i++) {
            if (i != tableauRow) {
                if (logicState.getTableauRows().get(i).size() != 0) {
                    temporaryCard = logicState.getTableauRows().get(i).get(logicState.getTableauRows().get(i).size() - 1);
                    if (temporaryCard.getValue() == card.getValue() + 1
                            && temporaryCard.isRed() != card.isRed()) {
                        // Først tjek for hidden card bagved række, der flyttes:
                        if (logicState.getHiddenCards()[tableauRow] != 0 && cardPlacement == 0) {
                            int[] newHiddenCards = logicState.getHiddenCards();
                            newHiddenCards[i] = newHiddenCards[i] - 1;
                            logicState.setHiddenCards(newHiddenCards);
                            return "Move " + card + " to " + temporaryCard;
                        }
                        // Derefter tjek for at kortet bagved rækken der flyttes (hvis der er et) kan rykkes op på foundations bagefter:
                        else if (cardPlacement != 0 && logicState.getTableauRows().get(tableauRow).get(cardPlacement - 1) != null
                                && checkBehindTabToFou(logicState.getTableauRows().get(tableauRow).get(cardPlacement - 1))) {
                            return "Move " + card + " to " + temporaryCard;
                        }
                        // Til sidst tjek om en hel række kort kan rykkes uden nogen kort bagved, men kun hvis der kan flyttes en konge på pladsen
                        else if (logicState.getHiddenCards()[tableauRow] == 0 && cardPlacement == 0) {
                            for (int j = 0; j < logicState.getTableauRows().size(); j++) {
                                if (logicState.getTableauRows().get(j).get(0).getValue() == 13 && logicState.getHiddenCards()[j] != 0) {
                                    return "Move " + card + " to " + temporaryCard;
                                }
                            }
                            if (logicState.getTopDeckCard().getValue() == 13) {
                                return "Move " + card + " to " + temporaryCard;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public void setLogicState(LogicState logicState) {
        this.logicState = logicState;
    }
}