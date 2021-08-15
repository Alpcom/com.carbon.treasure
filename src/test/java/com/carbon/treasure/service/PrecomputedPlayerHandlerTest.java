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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import com.carbon.treasure.domain.Instruction;
import com.carbon.treasure.domain.Player;
import com.carbon.treasure.domain.PlayerState;

class PrecomputedPlayerHandlerTest {

	@Test
	void testInitialState() {
		var player = mock(Player.class);
		var state = mock(PlayerState.class);
		doReturn(player).when(state).getPlayer();
		var instr1 = Instruction.LEFT;
		var instr2 = Instruction.RIGHT;
		List<Instruction> asList = Arrays.asList(instr1, instr2);
		doReturn(asList).when(state).getRemainingInstructions();
		var objTotest = new PrecomputedPlayerHandler(state);

		// check inital state
		assertSame(state, objTotest.getCurrentState());
		assertSame(player, objTotest.getAdventurer());
		assertTrue(objTotest.haveRemainingInstruction());
		assertSame(instr1, objTotest.getNextInstruction());
	}

	@Test
	void testGetterDoesNotChangeStatus() {
		var player = mock(Player.class);
		var state = mock(PlayerState.class);
		doReturn(player).when(state).getPlayer();
		var instr1 = Instruction.LEFT;
		var instr2 = Instruction.RIGHT;
		List<Instruction> asList = Arrays.asList(instr1, instr2);
		doReturn(asList).when(state).getRemainingInstructions();
		var objTotest = new PrecomputedPlayerHandler(state);

		// check that getNext does not change state
		assertSame(instr1, objTotest.getNextInstruction());
		assertTrue(objTotest.haveRemainingInstruction());
		assertSame(instr1, objTotest.getNextInstruction());
		assertTrue(objTotest.haveRemainingInstruction());

	}

	@Test
	void testInstructionConsumption() {
		var player = mock(Player.class);
		var state = mock(PlayerState.class);
		doReturn(player).when(state).getPlayer();
		var instr1 = Instruction.LEFT;
		var instr2 = Instruction.RIGHT;
		List<Instruction> asList = new LinkedList<>(Arrays.asList(instr1, instr2));
		doReturn(asList).when(state).getRemainingInstructions();
		var objTotest = new PrecomputedPlayerHandler(state);

		// check instruction consumption
		assertDoesNotThrow(objTotest::consomeNextInstruction);
		assertTrue(objTotest.haveRemainingInstruction());
		assertSame(instr2, objTotest.getNextInstruction());
		assertDoesNotThrow(objTotest::consomeNextInstruction);
		assertFalse(objTotest.haveRemainingInstruction());
		assertThrows(NoSuchElementException.class, objTotest::getNextInstruction);
		assertThrows(NoSuchElementException.class, objTotest::consomeNextInstruction);
	}

	@Test
	void testFailAllInstructionConsumption() {
		var player = mock(Player.class);
		var state = mock(PlayerState.class);
		doReturn(player).when(state).getPlayer();
		List<Instruction> asList = new LinkedList<>(Arrays.asList());
		doReturn(asList).when(state).getRemainingInstructions();
		var objTotest = new PrecomputedPlayerHandler(state);
		assertThrows(NoSuchElementException.class, objTotest::consomeAllInstruction);
	}

}
