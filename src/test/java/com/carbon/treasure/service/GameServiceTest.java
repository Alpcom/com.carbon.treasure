/**
 * MIT License
 *
 * Copyright (c) 2021 Le Prevost-Corvellec Arnault
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.carbon.treasure.service;

import static com.carbon.treasure.domain.Instruction.LEFT;
import static com.carbon.treasure.domain.Instruction.MOVE;
import static com.carbon.treasure.domain.Instruction.RIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.carbon.treasure.domain.GameData;
import com.carbon.treasure.domain.Orientation;
import com.carbon.treasure.domain.Player;
import com.carbon.treasure.domain.PlayerState;
import com.carbon.treasure.domain.map.Position;
import com.carbon.treasure.domain.map.Cell;
import com.carbon.treasure.domain.map.CellFactory;
import com.carbon.treasure.domain.map.CellFactoryImpl;
import com.carbon.treasure.domain.map.GameMap;

class GameServiceTest {

	@Test
	void test_1() {

		var service = new GameService();
		CellFactory cellFactory = new CellFactoryImpl();

		List<Cell> cells = Arrays.asList(//
				cellFactory.createPlain(new Position(0, 0)), //
				cellFactory.createMountain(new Position(1, 0)), //
				cellFactory.createPlain(new Position(2, 0)), //

				cellFactory.createPlain(new Position(0, 1)), //
				cellFactory.createPlain(new Position(1, 1)), //
				cellFactory.createMountain(new Position(2, 1)), //

				cellFactory.createPlain(new Position(0, 2)), //
				cellFactory.createPlain(new Position(1, 2)), //
				cellFactory.createPlain(new Position(2, 2)), //

				cellFactory.createTreasure(new Position(0, 3), 2), //
				cellFactory.createTreasure(new Position(1, 3), 3), //
				cellFactory.createPlain(new Position(2, 3))//

		);

		var indiana = new PlayerState(new Player("Indiana"), new Position(1, 1), Orientation.SOUTH,
				new ArrayList<>(Arrays.asList(MOVE, MOVE, RIGHT, MOVE, RIGHT, MOVE, LEFT, LEFT, MOVE)));//
		List<PlayerState> players = Arrays.asList(indiana);
		var data = new GameData(new GameMap(new HashSet<>(cells)), players);

		var toCheck = service.play(data);
		// check map
		var treasure1 = toCheck.getMap().getCellAt(0, 3);
		assertTrue(treasure1.isTreasure());
		assertFalse(treasure1.canRemoveATreasure());
		var treasure2 = toCheck.getMap().getCellAt(1, 3);
		assertTrue(treasure2.isTreasure());
		assertEquals(2, treasure2.getTreasureCount());
		// check player
		var indiana_out = toCheck.getAdventurers().get(0);
		assertEquals(Orientation.SOUTH, indiana_out.getOrientation());
		assertEquals(new Position(0, 3), indiana_out.getPosition());
		assertEquals(3, indiana_out.getScorePoint());

	}

	@Test
	void test_2() {

		var service = new GameService();
		CellFactory cellFactory = new CellFactoryImpl();

		List<Cell> cells = Arrays.asList(//
				cellFactory.createPlain(new Position(0, 0)), //
				cellFactory.createPlain(new Position(0, 1)), //
				cellFactory.createPlain(new Position(0, 2)), //
				cellFactory.createPlain(new Position(0, 3)), //
				cellFactory.createPlain(new Position(0, 4)), //
				cellFactory.createPlain(new Position(0, 5)) //

		);

		var indiana = new PlayerState(new Player("Indiana"), new Position(0, 0), Orientation.SOUTH,
				new ArrayList<>(Arrays.asList(MOVE, MOVE, MOVE, MOVE)));//
		var lara = new PlayerState(new Player("Lara"), new Position(0, 1), Orientation.SOUTH,
				new ArrayList<>(Arrays.asList(MOVE, MOVE, LEFT, LEFT, LEFT, LEFT, MOVE)));//
		List<PlayerState> players = Arrays.asList(indiana, lara);
		var data = new GameData(new GameMap(new HashSet<>(cells)), players);

		var toCheck = service.play(data);

		// check player
		var lara_out = toCheck.getAdventurers().get(1);
		assertEquals(Orientation.SOUTH, lara_out.getOrientation());
		assertEquals(new Position(0, 4), lara_out.getPosition());

		var indiana_out = toCheck.getAdventurers().get(0);
		assertEquals(Orientation.SOUTH, indiana_out.getOrientation());
		assertEquals(new Position(0, 3), indiana_out.getPosition());

	}

	@Test
	void test_3() {

		var service = new GameService();
		CellFactory cellFactory = new CellFactoryImpl();

		List<Cell> cells = Arrays.asList(//
				cellFactory.createPlain(new Position(0, 0)), //
				cellFactory.createMountain(new Position(0, 1)) //
		);

		var indiana = new PlayerState(new Player("Indiana"), new Position(0, 0), Orientation.SOUTH,
				new ArrayList<>(Arrays.asList(MOVE, MOVE)));//
		List<PlayerState> players = Arrays.asList(indiana);
		var data = new GameData(new GameMap(new HashSet<>(cells)), players);

		var toCheck = service.play(data);

		var indiana_out = toCheck.getAdventurers().get(0);
		assertEquals(Orientation.SOUTH, indiana_out.getOrientation());
		assertEquals(new Position(0, 0), indiana_out.getPosition());

	}
	
	@Test
	void test_4() {

		var service = new GameService();
		CellFactory cellFactory = new CellFactoryImpl();

		List<Cell> cells = Arrays.asList(//
				cellFactory.createPlain(new Position(0, 0)) //
		);

		var indiana = new PlayerState(new Player("Indiana"), new Position(0, 0), Orientation.SOUTH,
				new ArrayList<>(Arrays.asList(MOVE, MOVE)));//
		List<PlayerState> players = Arrays.asList(indiana);
		var data = new GameData(new GameMap(new HashSet<>(cells)), players);

		var toCheck = service.play(data);

		var indiana_out = toCheck.getAdventurers().get(0);
		assertEquals(Orientation.SOUTH, indiana_out.getOrientation());
		assertEquals(new Position(0, 0), indiana_out.getPosition());

	}

}
