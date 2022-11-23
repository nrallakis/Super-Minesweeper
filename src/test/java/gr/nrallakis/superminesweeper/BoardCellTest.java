package gr.nrallakis.superminesweeper;

import gr.nrallakis.superminesweeper.cell.BoardCell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardCellTest {
    @Test
    void reveal_removes_marking_of_mine() {
        BoardCell cell = new BoardCell(0, 0);
        cell.setMarkedAsMine(true);

        cell.reveal();
        assertFalse(cell.isMarkedAsMine());
    }

    @Test
    void can_be_marked_as_mine_if_not_revealed() {
        BoardCell cell = new BoardCell(0, 0);
        cell.setMarkedAsMine(true);
        assertTrue(cell.isMarkedAsMine());
    }

    @Test
    void cannot_be_marked_as_mine_if_revealed() {
        BoardCell cell = new BoardCell(0, 0);
        cell.reveal();
        cell.setMarkedAsMine(true);
        assertFalse(cell.isMarkedAsMine());
    }

}
