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
package com.carbon.treasure.service;

import com.carbon.treasure.domain.Instruction;
import com.carbon.treasure.domain.Player;
import com.carbon.treasure.domain.PlayerState;

/**
 * 
 * Interface for player handling those method wil be used by {@link GameState}
 * to handle player during game flow
 * 
 * @author aleprevost
 *
 */
public interface PlayerHandler {
	/**
	 * @return the player current state
	 */
	PlayerState getCurrentState();

	/**
	 * remove list of instruction head from player state
	 */
	void consomeNextInstruction();

	/**
	 * @return true if and only if some instruction can be executed
	 */
	boolean haveRemainingInstruction();

	/**
	 * @return list of instruction 's head from player state
	 * 
	 */
	Instruction getNextInstruction();

	/**
	 * 
	 * @return player from player state
	 * @see PlayerState#getPlayer()
	 * @see #getCurrentState()
	 */
	Player getAdventurer();

	/**
	 * fire a treasure has been found : a point will be add on {@link PlayerState}
	 */
	void treasureFound();

	/**
	 * end player game by removing all instructions
	 */
	void consomeAllInstruction();

}
