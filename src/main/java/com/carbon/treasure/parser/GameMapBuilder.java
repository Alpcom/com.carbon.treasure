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
package com.carbon.treasure.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.carbon.treasure.domain.CartesianPosition;
import com.carbon.treasure.domain.Position;
import com.carbon.treasure.domain.RectangularArea;
import com.carbon.treasure.domain.map.Cell;
import com.carbon.treasure.domain.map.CellFactory;
import com.carbon.treasure.domain.map.GameMap;

public class GameMapBuilder {

	private final Map<Position, Integer> treasures = new HashMap<>();
	private final Set<Position> mountains = new HashSet<>();
	private RectangularArea area;

	public GameMapBuilder() {
	}

	public boolean addTreasure(Position cartesianPosition, int treasureCount) {
		return null != treasures.put(cartesianPosition, treasureCount);
	}

	public boolean addMountains(Position cartesianPosition) {
		return mountains.add(cartesianPosition);
	}

	public boolean containsMap() {
		return null != area;
	}

	public void setArea(RectangularArea rectangularArea) {
		if (area != null) {
			throw new IllegalStateException();
		}
		this.area = rectangularArea;
	}

	public GameMap build(CellFactory cellFactory) {
		HashSet<Cell> createdCell = new HashSet<>(area.getX() * area.getY());
		for (int x = area.getX(); x < area.getWidth(); x++) {
			for (int y = area.getY(); y < area.getHeight(); y++) {
				Position p = new CartesianPosition(x, y);
				if (mountains.contains(p)) {
					if (treasures.containsKey(p)) {
						throw new IllegalArgumentException("A cell can't be a mountains and a treasure");
					}
					createdCell.add(cellFactory.createMountain(p));
				} else if (treasures.containsKey(p)) {
					createdCell.add(cellFactory.createTreasure(p, treasures.get(p)));
				}else {
					createdCell.add(cellFactory.createPlain(p));
				}
			}
		}
		return new GameMap(createdCell);
	}

}
