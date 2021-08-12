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
package com.carbon.treasure.domain.map;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

import com.carbon.treasure.domain.CartesianPosition;
import com.carbon.treasure.domain.Position;

public class GameMap {

	private static final class PositionCellFilter implements Predicate<Cell> {

		private Position p;

		public PositionCellFilter(Position p) {
			this.p = p;
		}

		@Override
		public boolean test(Cell t) {
			return p.equals(t.getPosition());
		}

	}

	private final Set<Cell> cases;

	public GameMap(Set<Cell> cases) {
		this.cases = Collections.unmodifiableSet(cases);
	}

	public Set<Cell> getCells() {
		return cases;
	}

	public Cell getCellAt(int x, int y) {
		return getCellAt(new CartesianPosition(x, y));
	}

	public Cell getCellAt(Position p) {
		return cases.stream().filter(new PositionCellFilter(p)).findFirst().orElseThrow();
	}

}
