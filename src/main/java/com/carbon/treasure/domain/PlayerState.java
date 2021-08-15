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

import java.util.List;
import java.util.Objects;

import com.carbon.treasure.domain.map.Position;

/**
 * this class represent the state of an adventurer : its position and its
 * orientation
 */
public class PlayerState {
	private Position position;
	private Orientation orientation;
	private List<Instruction> remainingInstruction;
	private final Player player;
	private int score;

	/**
	 * @param position    see {@link Position}
	 * @param orientation see {@link Orientation}
	 */
	public PlayerState(Player player, Position position, Orientation orientation,
			List<Instruction> remainingInstruction) {
		this.player = player;
		updateState(position, orientation, remainingInstruction);
	}

	private void updateState(Position position, Orientation orientation, List<Instruction> remainingInstruction) {
		Objects.requireNonNull(position);
		Objects.requireNonNull(orientation);
		Objects.requireNonNull(remainingInstruction);
		this.position = position;
		this.orientation = orientation;
		this.remainingInstruction = remainingInstruction;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * @return the orientation
	 */
	public Orientation getOrientation() {
		return this.orientation;
	}

	public List<Instruction> getRemainingInstructions() {
		return this.remainingInstruction;
	}

	public Player getPlayer() {
		return this.player;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.player);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		var other = (PlayerState) obj;
		return Objects.equals(this.player, other.player);
	}

	@Override
	public String toString() {
		return "PlayerState [position=" + this.position + ", orientation=" + this.orientation
				+ ", remainingInstruction=" + this.remainingInstruction + ", player=" + this.player + "]";
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public void setPosition(Position targetPosition) {
		this.position = targetPosition;
	}

	public void addScorePoint(int i) {
		this.score += i;
	}

	public int getScorePoint() {
		return this.score;
	}

}
