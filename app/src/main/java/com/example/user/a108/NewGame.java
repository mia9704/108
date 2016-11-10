package com.example.user.a108;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 7/1/16.
 */
public class NewGame extends AppCompatActivity {
    //boolean myTurn = true;
    Set<Card> set = new HashSet<>();
    ArrayList<Card> stack = new ArrayList<>(set);
    //Set<Card> stack = new LinkedHashSet<>();
    ArrayList<ImageButton> myCards = new ArrayList<>();

    final Deck setOfCards = new Deck();
    int manualSuit;

    //restore button to original position after go and reset
    float yCoordinate;
    boolean initialized = false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newgame);

        setOfCards.shuffleList();
        setOfCards.assignCards();

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.newgamelayout);
        final ImageView middle1 = (ImageView) findViewById(R.id.middle1);
        final ImageView middle2 = (ImageView) findViewById(R.id.middle2);
        final ImageView middle3 = (ImageView) findViewById(R.id.middle3);
        final ImageView middle4 = (ImageView) findViewById(R.id.middle4);
        final Button go = (Button) findViewById(R.id.go);
        final Button reset = (Button) findViewById(R.id.reset);
        final ImageButton deck = (ImageButton) findViewById(R.id.deck);
        final Hand mainHand = setOfCards.hands[0];

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        //System.out.println("HEIGHT "+height);


        for (int i = 0; i < 5; i++)
        {
            final ImageButton button = new ImageButton(NewGame.this);
            button.setBackgroundResource(mainHand.list.get(i).cardUI());
            button.setId(View.generateViewId());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(i != 0) {
                lp.addRule(RelativeLayout.END_OF, myCards.get(myCards.size() - 1).getId());
                lp.setMargins(-65, 0, 0, 0);
            }
            else
            {
                lp.addRule(RelativeLayout.START_OF, deck.getId());
                lp.setMargins(0,0,0,0);
            }
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            myCards.add(button);
            relativeLayout.addView(button, lp);
        }

        setOfCards.middle.get(0).firstCard(true);

        middle1.setBackgroundResource(setOfCards.middle.get(0).cardUI());
        middle2.setVisibility(View.INVISIBLE);
        middle3.setVisibility(View.INVISIBLE);
        middle4.setVisibility(View.INVISIBLE);

        int cardHeight = getDrawable(R.drawable.yellow).getBounds().height();
        yCoordinate = height - cardHeight;
        System.out.println("cardHeight "+cardHeight+ " yCoordinate "+yCoordinate);

        /*Intent intent = new Intent(NewGame.this, NGService.class) ;;
        intent.putExtra("deck", setOfCards);*/





        //onclicklistener for all the cards in my hand
        for (int i = 0; i < myCards.size(); i++)
        {
            final ImageButton button = myCards.get(i);
            ArrayList<Card> myHand = setOfCards.hands[0].list;
            final Card card = myHand.get(i);


            //the first middle card
            final Card middleCard = setOfCards.middle.get(0);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //boolean to keep track of duplicates
                    boolean exists = false;

                    //check for duplicates
                    for (int i = 0; i < stack.size(); i++)
                    {
                        //System.out.println("EQUALS "+ stack.get(i).equals(newCard));
                        if (stack.get(i).equals(card))
                        {
                            exists = true;
                        }
                    }
                    if (isAddable(card, middleCard))
                    {
                        if (!exists) {
                            stack.add(card);
                            /*if (!initialized) {
                                yCoordinate = button.getY();
                                System.out.println("HOW many times initialized");
                                initialized = true;
                            }*/
                            button.setY(button.getY() - 20);
                            //System.out.println("Y COORDINATE IS "+ button.getY());
                        }
                    }
                    else
                    {
                        Toast.makeText(NewGame.this, "Cannot pick this card. Try another", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (setOfCards.middle.get(0).firstCard)
                {
                    setOfCards.middle.get(0).firstCard = false;
                }
                //NewGame.this.myTurn = false;
                if (stack.size() != 0) {


                    //setting the UI for the middle cards
                    if (stack.size() > 0) {
                        middle1.setVisibility(View.VISIBLE);
                        middle1.setBackgroundResource(stack.get(0).cardUI());
                    }
                    if (stack.size() > 1) {
                        middle2.setVisibility(View.VISIBLE);
                        middle2.setBackgroundResource(stack.get(1).cardUI());
                    }
                    if (stack.size() > 2) {
                        middle3.setVisibility(View.VISIBLE);
                        middle3.setBackgroundResource(stack.get(2).cardUI());
                    }
                    if (stack.size() > 3) {
                        middle4.setVisibility(View.VISIBLE);
                        middle4.setBackgroundResource(stack.get(3).cardUI());
                    }

                    //erasing cards from main hand
                    for (int i = 0; i < stack.size(); i++) {
                        mainHand.list.remove(stack.get(i));
                    }

                    int cardsSize = myCards.size();

                    //setting the UI for the main hand
                    for (int i = cardsSize - 1; i >= 0; i--) {
                        if (i < mainHand.list.size()) {
                            myCards.get(i).setBackgroundResource(mainHand.list.get(i).cardUI());
                        } else {
                            myCards.get(i).setVisibility(View.INVISIBLE);
                            myCards.remove(i);
                        }
                    }
                    if (stack.get(0).getRank() == "Q") {
                        class ChooseSuitDialog extends DialogFragment {

                            @Override
                            public Dialog onCreateDialog(Bundle savedInstanceState) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                String[] chararray = {"Hearts", "Diamonds", "Clubs", "Spades"};

                                builder.setTitle("Choose a Suit")
                                        .setItems(chararray, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // The 'which' argument contains the index position
                                                // of the selected item
                                                manualSuit = which;
                                            }
                                        });
                                return builder.create();
                            }
                        }
                        DialogFragment newFragment = new ChooseSuitDialog();
                        newFragment.show(getFragmentManager(), "Choose Suit");
                    }

                    stack.clear();
                    for (int i = 0; i < myCards.size(); i++)
                    {
                        if (i < 14) {
                            myCards.get(i).setY(yCoordinate);
                        }
                        else
                        {
                            myCards.get(i).setY(yCoordinate+70);
                        }
                    }

                    theirTurn();
                }
            }
        });
        deck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Card newCard = setOfCards.drawCard(0);
                if (newCard != null) {
                    final ImageButton button = new ImageButton(NewGame.this);
                    button.setBackgroundResource(newCard.cardUI());
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    button.setId(View.generateViewId());
                    lp.addRule(RelativeLayout.END_OF, myCards.get(myCards.size()-1).getId());
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    if (myCards.size() < 12)
                    {
                        lp.setMargins(-65, 0, 0, 0);
                    }
                    else if (myCards.size() <= 13)
                    {
                        //shifting cards to the left
                        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        myCards.get(0).setLayoutParams(lp2);

                        lp.setMargins(-65, 0, 0, 0);
                    }
                    else if (myCards.size() == 14)
                    {
                        button.setY(yCoordinate+70);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    }
                    else
                    {
                        button.setY(yCoordinate+70);
                        lp.setMargins(-65, 0, 0, 0);
                    }

                    myCards.add(button);
                    relativeLayout.addView(button, lp);

                    //the first middle card
                    final Card middleCard = setOfCards.middle.get(setOfCards.middle.size()-1);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean exists = false;
                            for (int i = 0; i < stack.size(); i++)
                            {
                                //System.out.println("EQUALS "+ stack.get(i).equals(newCard));
                                if (stack.get(i).equals(newCard))
                                {
                                    exists = true;
                                }
                            }
                            if (isAddable(newCard, middleCard))
                            {
                                if (!exists) {
                                    stack.add(newCard);
                                    /*if (!initialized) {
                                        yCoordinate = button.getY();
                                        initialized = true;
                                        System.out.println("HOW many times initialized");
                                    }*/
                                    button.setY(button.getY() - 20);
                                }
                            }
                            else
                            {
                                Toast.makeText(NewGame.this, "Cannot pick this card. Try another", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                if (setOfCards.gameOver())
                {
                    //TODO implement game over
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stack.size() > 0) {
                    stack.clear();
                    for (int i = 0; i < myCards.size(); i++) {
                        if (i < 14) {
                            myCards.get(i).setY(yCoordinate);
                            System.out.println("i is "+i+ " Y COORDINATE "+ yCoordinate);
                        }
                        else
                        {
                            myCards.get(i).setY(yCoordinate+70);
                            System.out.println("i is "+i+ " Y COORDINATE "+ yCoordinate);
                        }
                    }
                }
            }
        });


    }
    public void theirTurn()
    {
        final Button go = (Button) findViewById(R.id.go);
        final Button reset = (Button) findViewById(R.id.reset);
        go.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);
        //TODO other players go
    }

    //method to know if card is addable to stack
    public boolean isAddable(Card card, Card middle)
    {
        //ensure that the stack does not include cards of different rank
        if (stack.size() == 0)
        {
            //when a suit is manually picked by a previous draw of a queen
            if (!setOfCards.middle.get(0).firstCard && (setOfCards.middle.get(0).getRank() == "Q"))
            {
                if (card.suit == manualSuit)
                {
                    return true;
                }
                else if (card.getRank() == "Q")
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            if (middle.sameRank(card) || middle.sameSuit(card))
            {

                return true;
            }
            else if (card.getRank() == "Q")
            {
                return true;
            }
            else
                return false;
        }
        else if (stack.get(0).sameRank(card))
        {
                return true;
        }
        else
            return false;
    }
}
