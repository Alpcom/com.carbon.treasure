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
 * Interface that provide cell features
 * 
 * @author aleprevost
 *
 */
public interface Cell {

	/**
	 * if {@link #canRemoveATreasure()} will remove a treasure (only
	 * {@link TreasureCell} can remove )
	 * 
	 * @return true if and only if a treasure has been removed
	 */
	boolean removeATreasure();

	/**
	 * only {@link TreasureCell} can remove a treasure
	 * 
	 * @return true if and only if a treasure can be removed
	 */
	boolean canRemoveATreasure();

	/**
	 * only {@link TreasureCell} can provide a non null return value
	 * 
	 * @return the number of treasure available to collect
	 */
	int getTreasureCount();

	/**
	 * @return the position of the cell
	 */
	Position getPosition();

	/**
	 * @return true if and only if is an instance of {@link MountainCell}
	 */
	boolean isMountain();

	/**
	 * @return true if and only if is an instance of {@link TreasureCell}
	 */
	boolean isTreasure();

}