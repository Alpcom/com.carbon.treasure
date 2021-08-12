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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.carbon.treasure.service.PlayerHandler;
import com.carbon.treasure.service.PrecomputedPlayerHandler;

public class PlayerStates {

	Map<Player, PlayerHandler> players = new HashMap<>();

	public boolean add(Player adventurer, PlayerState adventurerState, List<Instruction> instructions) {
		return null == players.putIfAbsent(adventurer, new PrecomputedPlayerHandler(adventurer,adventurerState,instructions));
	}

	public PlayerHandler getPlayerHandler(String adventurerName) {
		return players.get(new Player(adventurerName));
	}

	public int count() {
		return players.size();
	}

}
