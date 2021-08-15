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
package com.carbon.treasure.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.carbon.treasure.domain.map.CartesianPosition;
import com.carbon.treasure.domain.map.Cell;
import com.carbon.treasure.domain.map.CellFactory;
import com.carbon.treasure.domain.map.GameMap;
import com.carbon.treasure.domain.map.Position;
import com.carbon.treasure.domain.map.RectangularArea;

public class GameMapBuilder {

	private final Map<Position, Integer> treasures = new HashMap<>();
	private final Set<Position> mountains = new HashSet<>();
	private RectangularArea area;

	public GameMapBuilder() {
	}

	public boolean addTreasure(Position cartesianPosition, int treasureCount) {
		return null != this.treasures.put(cartesianPosition, treasureCount);
	}

	public boolean addMountains(Position cartesianPosition) {
		return this.mountains.add(cartesianPosition);
	}

	public boolean containsMap() {
		return null != this.area;
	}

	public void setArea(RectangularArea rectangularArea) {
		if (this.area != null) {
			throw new IllegalStateException();
		}
		this.area = rectangularArea;
	}

	public GameMap build(CellFactory cellFactory) {
		var createdCell = new HashSet<Cell>(this.area.getX() * this.area.getY());
		for (var x = this.area.getX(); x < this.area.getWidth(); x++) {
			for (var y = this.area.getY(); y < this.area.getHeight(); y++) {
				Position p = new CartesianPosition(x, y);
				if (this.mountains.contains(p)) {
					if (this.treasures.containsKey(p)) {
						throw new IllegalArgumentException("A cell can't be a mountains and a treasure");
					}
					createdCell.add(cellFactory.createMountain(p));
				} else if (this.treasures.containsKey(p)) {
					createdCell.add(cellFactory.createTreasure(p, this.treasures.get(p)));
				} else {
					createdCell.add(cellFactory.createPlain(p));
				}
			}
		}
		return new GameMap(createdCell);
	}

}
