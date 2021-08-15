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

/**
 * Factory for {@link Cell}
 * 
 * @author aleprevost
 *
 */
public interface CellFactory {
	/**
	 * create cell that represent a mountain
	 * 
	 * @param p position
	 * @return created cell
	 */
	Cell createMountain(Position p);

	/**
	 * create cell that represent a treasure
	 * 
	 * @param p             position
	 * @param treasureCount number of treasure available on the cell
	 * @return created cell
	 */
	Cell createTreasure(Position p, Integer treasureCount);

	/**
	 * create cell that represent a plain
	 * 
	 * @param p position
	 * @return created cell
	 */
	Cell createPlain(Position p);

}