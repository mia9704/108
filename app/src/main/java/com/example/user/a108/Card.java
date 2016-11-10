package com.example.user.a108;

import java.util.Random;

/**
 * Created by user on 7/1/16.
 */
public class Card
{
    public int rank;
    public int suit;
    public int card;
    public boolean firstCard;
    final public int numRank = 8;
    final public int numSuit = 4;
    final public int[][] matrix = new int[][]{
            {0, 1, 2, 3, 4, 5, 6, 7},
            {8, 9, 10, 11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20, 21, 22, 23},
            {24, 25, 26, 27, 28, 29, 30, 31}
    };
    Card (int card)
    {
        this.card = card;
        rank = card % 8;
        assignSuit();
    }
    public void assignSuit()
    {
        if (card < 8)
        {
            suit = 0;
        }
        else if (card < 16)
        {
           suit = 1;
        }
        else if (card < 24)
        {
            suit = 2;
        }
        else
        {
            suit = 3;
        }
    }
    public String getRank()
    {
        switch (rank) {
            case 0:
                return "A";
            case 1:
                return "K";
            case 2:
                return "Q";
            case 3:
                return "J";
            case 4:
                return "10";
            case 5:
                return "9";
            case 6:
                return "8";
            case 7:
                return "7";
            default:
                return null;
        }
    }
    public String getSuit()
    {
        if (suit == 0)
        {
            return "HEART";
        }
        else if (suit == 1)
        {
            return "DIAMOND";
        }
        else if (suit == 2)
        {
            return "CLUBS";
        }
        else
        {
            return "SPADES";
        }
    }
    public void assignRandom()
    {
        Random random = new Random();
        int max = numRank * numSuit - 1;
        int min = 0;
        card = random.nextInt(((max - min) + 1) + min);
    }
    public boolean sameRank(Card card)
    {
        return card.rank == this.rank;
    }
    public boolean sameSuit(Card card)
    {
        return card.suit == this.suit;
    }

    public int cardUI()
    {
        switch (card)
        {
            case 0: return R.drawable.hearts_ace;
            case 1: return R.drawable.hearts_king;
            case 2: return R.drawable.hearts_queen;
            case 3: return R.drawable.hearts_jack;
            case 4: return R.drawable.hearts10;
            case 5: return R.drawable.hearts9;
            case 6: return R.drawable.hearts8;
            case 7: return R.drawable.hearts7;
            case 8: return R.drawable.diamonds_ace;
            case 9: return R.drawable.diamonds_king;
            case 10: return R.drawable.diamonds_queen;
            case 11: return R.drawable.diamonds_jack;
            case 12: return R.drawable.diamonds10;
            case 13: return R.drawable.diamonds9;
            case 14: return R.drawable.diamonds8;
            case 15: return R.drawable.diamonds7;
            case 16: return R.drawable.clubs_ace;
            case 17: return R.drawable.clubs_king;
            case 18: return R.drawable.clubs_queen;
            case 19: return R.drawable.clubs_jack;
            case 20: return R.drawable.clubs10;
            case 21: return R.drawable.clubs9;
            case 22: return R.drawable.clubs8;
            case 23: return R.drawable.clubs7;
            case 24: return R.drawable.spades_ace;
            case 25: return R.drawable.spades_king;
            case 26: return R.drawable.spades_queen;
            case 27: return R.drawable.spades_jack;
            case 28: return R.drawable.spades10;
            case 29: return R.drawable.spades9;
            case 30: return R.drawable.spades8;
            case 31: return R.drawable.spades7;
            default: return -1;
        }
    }
    public void firstCard(boolean first)
    {
        firstCard = first;
    }
    public int score()
    {
        switch (rank) {
            case 0:
                return 11;
            case 1:
                return 4;
            case 2:
                return 30;
            case 3:
                return 2;
            case 4:
                return 10;
            case 5:
                return 9;
            case 6:
                return 8;
            case 7:
                return 7;
            default:
                return -1000;
        }
    }
}
