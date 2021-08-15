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

import static com.carbon.treasure.domain.Instruction.LEFT;
import static com.carbon.treasure.domain.Instruction.MOVE;
import static com.carbon.treasure.domain.Instruction.RIGHT;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.carbon.treasure.domain.GameData;
import com.carbon.treasure.domain.Instruction;
import com.carbon.treasure.domain.Player;
import com.carbon.treasure.domain.PlayerState;

class GameStateTest {

	@Test
	void testCustomIterator() {
		GameData data = mock(GameData.class);
		List<PlayerState> states = Arrays.asList(mockPlayerState(Arrays.asList(LEFT, LEFT)), //
				mockPlayerState(Arrays.asList(MOVE)), //
				mockPlayerState(Arrays.asList(RIGHT, RIGHT, RIGHT)));
		doReturn(states).when(data).getAdventurers();
		GameState state = new GameState(data);

		LinkedList<Instruction> playedInstruction = new LinkedList<>();
		while (state.hasNextPlayer()) {
			PlayerHandler nextPlayer = state.nextPlayer();
			playedInstruction.add(nextPlayer.getNextInstruction());
			nextPlayer.consomeNextInstruction();
		}

		assertIterableEquals(Arrays.asList(LEFT, MOVE, RIGHT, LEFT, RIGHT, RIGHT), playedInstruction);
	}

	private PlayerState mockPlayerState(List<Instruction> asList) {
		PlayerState p = mock(PlayerState.class);
		doReturn(new LinkedList<>(asList)).when(p).getRemainingInstructions();
		doReturn(mock(Player.class)).when(p).getPlayer();
		return p;
	}

}
