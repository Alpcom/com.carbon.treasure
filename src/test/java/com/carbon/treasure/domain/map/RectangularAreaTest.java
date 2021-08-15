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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RectangularAreaTest {

	@ParameterizedTest //
	@CsvSource({ //
			"-2,-2,false", //
			"-2,-1,false", //
			"-2,0,false", //
			"-2,1,false", //
			"-2,2,false", //
			"-2,3,false", //
			"-2,4,false", //
			"-2,5,false", //
			"-2,6,false", //
			"-2,7,false", //
			"-1,-2,false", //
			"-1,-1,true", //
			"-1,0,true", //
			"-1,1,true", //
			"-1,2,true", //
			"-1,3,true", //
			"-1,4,false", //
			"-1,5,false", //
			"-1,6,false", //
			"-1,7,false", //
			"0,-2,false", //
			"0,-1,true", //
			"0,0,true", //
			"0,1,true", //
			"0,2,true", //
			"0,3,true", //
			"0,4,false", //
			"0,5,false", //
			"0,6,false", //
			"0,7,false", //
			"1,-2,false", //
			"1,-1,true", //
			"1,0,true", //
			"1,1,true", //
			"1,2,true", //
			"1,3,true", //
			"1,4,false", //
			"1,5,false", //
			"1,6,false", //
			"1,7,false", //
			"2,-2,false", //
			"2,-1,true", //
			"2,0,true", //
			"2,1,true", //
			"2,2,true", //
			"2,3,true", //
			"2,4,false", //
			"2,5,false", //
			"2,6,false", //
			"2,7,false", //
			"3,-2,false", //
			"3,-1,true", //
			"3,0,true", //
			"3,1,true", //
			"3,2,true", //
			"3,3,true", //
			"3,4,false", //
			"3,5,false", //
			"3,6,false", //
			"3,7,false", //
			"4,-2,false", //
			"4,-1,false", //
			"4,0,false", //
			"4,1,false", //
			"4,2,false", //
			"4,3,false", //
			"4,4,false", //
			"4,5,false", //
			"4,6,false", //
			"4,7,false" //

	})

	public void testcontains_1(int x, int y, boolean expected) {
		var p = new CartesianPosition(x, y);
		var r = new RectangularArea(-1, -1, 5, 5);
		assertEquals(expected, r.contains(p));
	}

}
