package gr.charos.christmas.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand implements Comparable<Hand> {
	

	

	private final int bid;
	private final List<Card> cards;
	private final Type type;
	
	
	
	public Hand(List<Card> cards, int bid) {
		this.cards = cards;
		this.type = Type.ofHand(this);
		this.bid = bid;
	}
	
	public boolean isRankedHigherThan(Hand h) {
		if (this.type.strength != h.type.strength) {
			return this.type.strength > h.type.strength;
		}
		if (this.cards.get(0).strength != h.cards.get(0).strength) {
			return this.cards.get(0).strength > h.cards.get(0).strength;
		}
		if (this.cards.get(1).strength != h.cards.get(1).strength) {
			return this.cards.get(1).strength > h.cards.get(1).strength;
		}
		if (this.cards.get(2).strength != h.cards.get(2).strength) {
			return this.cards.get(2).strength > h.cards.get(2).strength;
		}
		if (this.cards.get(3).strength != h.cards.get(3).strength) {
			return this.cards.get(3).strength > h.cards.get(3).strength;
		}
		if (this.cards.get(4).strength != h.cards.get(4).strength) {
			return this.cards.get(4).strength > h.cards.get(4).strength;
		}
		
		return true;
	}
	
	@Override
	public int compareTo(Hand o) {
		if (this.isRankedHigherThan(o)) {
			return 1;
		}
		if (o.isRankedHigherThan(this)) {
			return -1;
		}
		
		return 0;
	}
	
	public List<Card> cards() {
		return cards;
	}
	
	public int bid() {
		return bid;
	}
	
	@Override
	public String toString() {
		return "Hand [bid=" + bid + ", cards=" + cards + ", type=" + type + "]";
	}

	public static Hand of(String s) {
		String[] split = s.split(" ");
		List<Card> c = new ArrayList<Hand.Card>();
		for (int i =0;i<split[0].length();i++) {
			c.add(Card.of(split[0].substring(i,i+1)));
		}
		int bid = Integer.parseInt(split[1]);
		
		return new Hand(c,bid);
	}


	enum Card {
		A("A", 13),
		K("K",12), 
		Q("Q",11), 
		J("J",10), 
		T("T",9), 
		NINE("9",8), 
		EIGHT("8",7), 
		SEVEN("7",6), 
		SIX("6",5), 
		FIVE("5",4), 
		FOUR("4", 3), 
		THREE("3",2),
		TWO("2",1);
		
		
		private String sign;
		private int strength;
		Card(String sign, int strength){
			this.sign = sign;
			this.strength = strength;
					
		}
		public String sign() {
			return sign;
		}
		
		public static Card of(String s) {
		
			return Arrays.asList(Card.values()).stream()
					.filter(p->{ System.out.println(p.sign + " = " + s+ " = "+ p.sign.equalsIgnoreCase(s)); return p.sign.equalsIgnoreCase(s);}).findFirst().get();
		}
		
	}
	enum Type {
		
		FIVE_OF_A_KIND(7),
		FOUR_OF_A_KIND(6),
		FULL_HOUSE(5),
		THREE_OF_A_KIND(4),
		TWO_PAIR(3),
		ONE_PAIR(2),
		HIGH_CARD(1);
		
		int strength;
		Type(int strength)  {
			this.strength = strength;
		}
		
		public static Type ofHand(Hand h ) {
			List<Card> hand = h.cards();
			Map<String, List<Card>> grouped = hand.stream().collect(Collectors.groupingBy(Card::sign));
			
			var entryAsList = new ArrayList<>(grouped.entrySet());
			
			if (entryAsList.size() == 1) {
				//all fives
				return FIVE_OF_A_KIND;
			}
			
			if (entryAsList.size() == 2) {
				if (entryAsList.get(0).getValue().size() == 4 ||entryAsList.get(1).getValue().size() == 4) {
					return FOUR_OF_A_KIND;
				}
				if ((entryAsList.get(0).getValue().size() == 3 && entryAsList.get(1).getValue().size() == 2)
						||
					(entryAsList.get(0).getValue().size() == 2 && entryAsList.get(1).getValue().size() == 3)
						 ) {
					return FULL_HOUSE;
				}
			}
			if (entryAsList.size() == 3) {
				if (entryAsList.get(0).getValue().size() == 3 || entryAsList.get(1).getValue().size() == 3 || entryAsList.get(2).getValue().size() == 3) {
					return THREE_OF_A_KIND;
				}
				return TWO_PAIR;
			}
			if (entryAsList.size() == 4) {
				return ONE_PAIR;
			}
			return HIGH_CARD;
		}
	}

	
}
