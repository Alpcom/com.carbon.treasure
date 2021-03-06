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
 * 
 * {@link Cell} implementation that represent a cell that will provide point to
 * adventurer that will move on
 * 
 * @author aleprevost
 *
 * 
 */
public class TreasureCell extends PlainCell {

	private int treasureCount;

	/**
	 * create a new cell for position p that contains treasureCount treasures
	 * 
	 * @param p
	 * @param treasureCount
	 */
	public TreasureCell(Position p, int treasureCount) {
		super(p);
		this.treasureCount = treasureCount;
	}

	@Override
	public boolean removeATreasure() {
		if (canRemoveATreasure()) {
			this.treasureCount--;
			return true;
		}
		return false;
	}

	@Override
	public boolean canRemoveATreasure() {
		return this.treasureCount > 0;
	}

	@Override
	public int getTreasureCount() {
		return this.treasureCount;
	}

	@Override
	public String toString() {
		return "TreasureCell [treasureCount=" + this.treasureCount + ", getPosition()=" + getPosition() + "]";
	}

	@Override
	public boolean isTreasure() {
		return true;
	}

}
