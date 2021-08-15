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
 * implementation of a rectangular area
 */
public class RectangularArea {

	private final int x;
	private final int y;
	private final int width;
	private final int height;

	/**
	 * @param x      the x-coordinate of the lower left corner
	 * @param y      the y-coordinate of the lower left corner
	 * @param width  the width of the area
	 * @param height the height of the area
	 */
	public RectangularArea(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return x coordinate
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return y coordinate
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return rectangle width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * @return rectangle width
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * 
	 * @param position
	 * @return true if and only of the position is in area
	 */
	public boolean contains(Position position) {
		var px = position.getX();
		var py = position.getY();
		return this.x <= px && px < this.x + this.width //
				&& this.y <= py && py < this.y + this.height;
	}

}
