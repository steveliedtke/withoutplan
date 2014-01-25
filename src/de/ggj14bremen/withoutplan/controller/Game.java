package de.ggj14bremen.withoutplan.controller;

import de.ggj14bremen.withoutplan.event.CellClicked;
import de.ggj14bremen.withoutplan.model.Board;

public interface Game {

	Board getBoard();
	
	TimeAndScore getTimeAndScore();
	
	void dispatchEvent(CellClicked event);
}
