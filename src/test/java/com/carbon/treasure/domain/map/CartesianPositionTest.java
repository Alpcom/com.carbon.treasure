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

import static com.carbon.treasure.domain.Orientation.EAST;
import static com.carbon.treasure.domain.Orientation.NORTH;
import static com.carbon.treasure.domain.Orientation.SOUTH;
import static com.carbon.treasure.domain.Orientation.WEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class CartesianPositionTest {

	@Test
	void testEquals() {
		new EqualsTester()//
				.addEqualityGroup(new CartesianPosition(0, 0))//
				.addEqualityGroup(new CartesianPosition(0, 1))// o
				.addEqualityGroup(new CartesianPosition(1, 0))//
				.addEqualityGroup(new CartesianPosition(1, 1))//
				.testEquals();
	}

	@Test
	void moveWithNullOrientation() {
		var cartesianPosition = new CartesianPosition(0, 0);
		assertThrows(IllegalArgumentException.class, () -> cartesianPosition.to(1, null));
	}

	@Test
	void moveWithNorthOrientation() {
		var cartesianPosition = new CartesianPosition(0, 0);
		assertEquals(new CartesianPosition(0, -2), cartesianPosition.to(2, NORTH));
	}

	@Test
	void moveWithSouthOrientation() {
		var cartesianPosition = new CartesianPosition(0, 0);
		assertEquals(new CartesianPosition(0, 2), cartesianPosition.to(2, SOUTH));
	}

	@Test
	void moveWithEastOrientation() {
		var cartesianPosition = new CartesianPosition(0, 0);
		assertEquals(new CartesianPosition(2, 0), cartesianPosition.to(2, EAST));
	}


	@Test
	void moveWithWestOrientation() {
		var cartesianPosition = new CartesianPosition(0, 0);
		assertEquals(new CartesianPosition(-2, 0), cartesianPosition.to(2, WEST));
	}

	@Test
	void moveNext() {
		var cartesianPosition = new CartesianPosition(0, 0);
		assertEquals(new CartesianPosition(1, 0), cartesianPosition.nextTo(EAST));
	}	
	
}
