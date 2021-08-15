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

import java.util.Objects;

/**
 * Abstract definition of {@link Cell} for {@link GameMap} this implementation
 * expose all abstract method a provide default implementation to avoid code
 * duplication. by the way it contains also the {@link Position} and the
 * {@link #hashCode()} and {@link #equals(Object) } implementation for all sub
 * classes
 * 
 * @author aleprevost
 *
 */
public abstract class AbstractCell implements Cell {
	private final Position position;

	/**
	 * create an abstact cell
	 * 
	 * @param p
	 */
	public AbstractCell(Position p) {
		this.position = p;
	}

	/**
	 * if {@link #canRemoveATreasure()} will remove a treasure (only
	 * {@link TreasureCell} can remove )
	 * 
	 * @return true if and only if a treasure has been removed
	 */
	@Override
	public boolean removeATreasure() {
		return false;
	}

	/**
	 * only {@link TreasureCell} can remove a treasure
	 * 
	 * @return true if and only if a treasure can be removed
	 */
	@Override
	public boolean canRemoveATreasure() {
		return false;
	}

	/**
	 * only {@link TreasureCell} can provide a non null return value
	 * 
	 * @return the number of treasure available to collect
	 */
	@Override
	public int getTreasureCount() {
		return 0;
	}

	/**
	 * @return the position of the cell
	 */
	@Override
	public Position getPosition() {
		return this.position;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(this.position);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		var other = (AbstractCell) obj;
		return Objects.equals(this.position, other.position);
	}

	/**
	 * @return true if and only if is an instance of {@link MountainCell}
	 */
	@Override
	public boolean isMountain() {
		return false;
	}

	/**
	 * @return true if and only if is an instance of {@link TreasureCell}
	 */
	@Override
	public boolean isTreasure() {
		return false;
	}

}
