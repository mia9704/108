package com.example.user.a108;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by user on 7/4/16.
 */
public class Deck implements Serializable{
    final Card[] allCards = {new Card(0), new Card(1), new Card(2), new Card(3), new Card(4), new Card(5), new Card(6),
            new Card(7), new Card(8), new Card(9), new Card(10), new Card(11), new Card(12), new Card(13), new Card(14),
            new Card(15), new Card(16), new Card(17), new Card(18), new Card(19), new Card(20), new Card(21), new Card(22),
            new Card(23), new Card(24), new Card(25), new Card(26), new Card(27), new Card(28), new Card(29), new Card(30),
            new Card(31)};
    ArrayList<Card> deck = new ArrayList<Card>();
    Hand[] hands = {new Hand(), new Hand(), new Hand(), new Hand()};
    ArrayList<Card> middle = new ArrayList<Card>();

    void shuffleList()
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = allCards.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Card tmp = allCards[index];
            allCards[index] = allCards[i];
            allCards[i] = tmp;
        }
    }
    void assignCards()
    {
        for (int i = 0; i < allCards.length; i++)
        {
            if (i < 5)
            {
                hands[0].add(allCards[i]);
            }
            else if (i < 10)
            {
                hands[1].add(allCards[i]);
            }
            else if (i < 15)
            {
                hands[2].add(allCards[i]);
            }
            else if (i < 20)
            {
                hands[3].add(allCards[i]);
            }
            else if (i < 21)
            {
                middle.add(allCards[i]);
            }
            else
            {
                deck.add(allCards[i]);
            }
        }
    }
    Card drawCard(int handNum)
    {
        if (deck.size() == 0)
        {
            return null;
        }
        Card card = deck.remove(0);
        hands[handNum].add(card);
        return card;
    }
    boolean gameOver()
    {
        for (int i = 0; i < hands.length; i++)
        {
            if (hands[i].list.size() == 0)
            {
                return true;
            }
        }
        return false;
    }
}
