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
package com.carbon.treasure.domain;

import static com.carbon.treasure.domain.Orientation.EAST;
import static com.carbon.treasure.domain.Orientation.NORTH;
import static com.carbon.treasure.domain.Orientation.SOUTH;
import static com.carbon.treasure.domain.Orientation.WEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrientationTest {

	@Test
	void testNext() {
		assertEquals(EAST, NORTH.next());
		assertEquals(SOUTH, EAST.next());
		assertEquals(WEST, SOUTH.next());
		assertEquals(NORTH, WEST.next());
	}

	@Test
	void testPrevious() {
		assertEquals(WEST, NORTH.previous());
		assertEquals(NORTH, EAST.previous());
		assertEquals(EAST, SOUTH.previous());
		assertEquals(SOUTH, WEST.previous());
	}

}
