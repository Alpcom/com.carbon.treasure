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

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

class MowerStateTest {

    @Test
    void testEquals() {
	Position p1 = new CartesianPosition(0, 0);
	Position p2 = new CartesianPosition(1, 1);
	new EqualsTester()//
		.addEqualityGroup(new PlayerState(p1, Orientation.NORTH))//
		.addEqualityGroup(new PlayerState(p1, Orientation.EAST))//
		.addEqualityGroup(new PlayerState(p2, Orientation.NORTH))//
		.addEqualityGroup(new PlayerState(p2, Orientation.SOUTH))//
		.testEquals();
    }

}
