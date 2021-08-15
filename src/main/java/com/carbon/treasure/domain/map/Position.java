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

import com.carbon.treasure.domain.Orientation;

/**
 * A {@link Position} implementation that is based on cartesian construction
 */
public class Position {

	private final int x;
	private final int y;

	/**
	 * @param x the x-axis position
	 * @param y the y-axis position
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return position on x-axis
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return position on y-axis
	 */
	public int getY() {
		return this.y;
	}

	/**
	 *
	 * @param d           the distance
	 * @param orientation
	 * @return the position after move
	 */
	public Position to(int d, Orientation orientation) {
		if (orientation == Orientation.EAST) {
			return new Position(this.x + d, this.y);
		}
		if (orientation == Orientation.NORTH) {
			return new Position(this.x, this.y - d);
		}
		if (orientation == Orientation.SOUTH) {
			return new Position(this.x, this.y + d);
		} else if (orientation == Orientation.WEST) {
			return new Position(this.x - d, this.y);
		} else {
			// TODO unrecognise option orientation internationnalized msg
			throw new IllegalArgumentException();
		}
	}

	/**
	 *
	 * @param orientation
	 * @return the position after move
	 */
	public Position nextTo(Orientation orientation) {
		return to(1, orientation);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.x, this.y);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		var other = (Position) obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CartesianPosition [x=" + this.x + ", y=" + this.y + "]";
	}

}
