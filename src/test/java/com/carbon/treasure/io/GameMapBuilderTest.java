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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.carbon.treasure.domain.map.CartesianPosition;
import com.carbon.treasure.domain.map.CellFactory;
import com.carbon.treasure.domain.map.RectangularArea;

class GameMapBuilderTest {

	@Test
	void testConflictBetweenTreasureAndMountain() {
		GameMapBuilder builder = new GameMapBuilder();
		builder.setArea(new RectangularArea(0, 0, 2, 2));
		builder.addMountains(new CartesianPosition(1, 1));
		builder.addTreasure(new CartesianPosition(1, 1), 1);
		assertThrows(IllegalArgumentException.class, () -> builder.build(mock(CellFactory.class)));
	}

	@Test
	void testTwiceSetArea() {
		GameMapBuilder builder = new GameMapBuilder();
		builder.setArea(mock(RectangularArea.class));
		assertThrows(IllegalStateException.class, () -> builder.setArea(mock(RectangularArea.class)));
	}

}
