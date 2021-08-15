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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.testing.EqualsTester;

class CellTest {

	@Test
	void testEquality() {
		Position p = Mockito.mock(Position.class);
		Position p1 = Mockito.mock(Position.class);

		new EqualsTester()//
				.addEqualityGroup(new PlainCell(p), new PlainCell(p))//
				.addEqualityGroup(new MountainCell(p), new MountainCell(p))//
				.addEqualityGroup(new TreasureCell(p, 0), new TreasureCell(p, 0), new TreasureCell(p, 1))//
				.addEqualityGroup(new PlainCell(p1))//
				.addEqualityGroup(new MountainCell(p1))//
				.addEqualityGroup(new TreasureCell(p1, 0))//
				.testEquals();
	}

	@Test
	void testPlainCell() {
		Position p = Mockito.mock(Position.class);
		doReturn("p").when(p).toString();
		PlainCell cell = new PlainCell(p);
		assertFalse(cell.removeATreasure());
		assertFalse(cell.canRemoveATreasure());
		assertFalse(cell.isMountain());
		assertFalse(cell.isTreasure());
		assertEquals(0, cell.getTreasureCount());
		assertSame(p, cell.getPosition());
		assertEquals(cell.toString(), "PlainCell [getPosition()=p]");
	}

	@Test
	void testMountainCell() {
		Position p = Mockito.mock(Position.class);
		doReturn("p").when(p).toString();
		MountainCell cell = new MountainCell(p);
		assertFalse(cell.removeATreasure());
		assertFalse(cell.canRemoveATreasure());
		assertTrue(cell.isMountain());
		assertFalse(cell.isTreasure());
		assertEquals(0, cell.getTreasureCount());
		assertSame(p, cell.getPosition());
		assertEquals(cell.toString(), "MountainCell [getPosition()=p]");
	}

	@Test
	void testTreasureCell() {
		Position p = Mockito.mock(Position.class);
		doReturn("p").when(p).toString();
		TreasureCell cell = new TreasureCell(p, 3);
		assertTrue(cell.canRemoveATreasure());
		assertEquals(3, cell.getTreasureCount());
		assertTrue(cell.removeATreasure());

		assertTrue(cell.canRemoveATreasure());
		assertEquals(2, cell.getTreasureCount());
		assertTrue(cell.removeATreasure());

		assertTrue(cell.canRemoveATreasure());
		assertEquals(1, cell.getTreasureCount());
		assertTrue(cell.removeATreasure());

		assertFalse(cell.canRemoveATreasure());
		assertEquals(0, cell.getTreasureCount());
		assertFalse(cell.removeATreasure());

		assertFalse(cell.canRemoveATreasure());
		assertEquals(0, cell.getTreasureCount());
		assertFalse(cell.removeATreasure());

		assertFalse(cell.isMountain());
		assertTrue(cell.isTreasure());
		assertSame(p, cell.getPosition());
		assertEquals(cell.toString(), "TreasureCell [treasureCount=0, getPosition()=p]");
	}

}
