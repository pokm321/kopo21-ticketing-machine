package lotte;

import java.io.IOException;

public class Lotte_World_Ticketing {
	public static void main(String[] args) {
		TicketSystem ticketsystem = new TicketSystem();
		try {
			ticketsystem.startSystem();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
