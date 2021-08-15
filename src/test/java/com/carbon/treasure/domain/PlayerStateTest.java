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

import static com.carbon.treasure.domain.Orientation.NORTH;
import static com.carbon.treasure.domain.Orientation.SOUTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.carbon.treasure.domain.map.Position;
import com.google.common.testing.EqualsTester;

class PlayerStateTest {

	@Test
	void testEquals() {
		Player toto = new Player("toto");
		Player tata = new Player("tata");
		List<Instruction> empInstr = Collections.emptyList();
		List<Instruction> instr = Arrays.asList(Instruction.MOVE);
		Position p1 = mock(Position.class);
		Position p2 = mock(Position.class);
		new EqualsTester()//
				.addEqualityGroup(//
						new PlayerState(toto, p1, NORTH, empInstr), //
						new PlayerState(toto, p1, NORTH, instr), //
						new PlayerState(toto, p2, NORTH, empInstr), //
						new PlayerState(toto, p2, NORTH, instr), //
						new PlayerState(toto, p1, SOUTH, empInstr), //
						new PlayerState(toto, p1, SOUTH, instr), //
						new PlayerState(toto, p2, SOUTH, empInstr), //
						new PlayerState(toto, p2, SOUTH, instr))//
				.addEqualityGroup(//
						new PlayerState(tata, p1, NORTH, empInstr), //
						new PlayerState(tata, p1, NORTH, instr), //
						new PlayerState(tata, p2, NORTH, empInstr), //
						new PlayerState(tata, p2, NORTH, instr), //
						new PlayerState(tata, p1, SOUTH, empInstr), //
						new PlayerState(tata, p1, SOUTH, instr), //
						new PlayerState(tata, p2, SOUTH, empInstr), //
						new PlayerState(tata, p2, SOUTH, instr))//
				.testEquals();
	}

	@Test
	void testSetOrientation() {
		Player toto = new Player("toto");
		List<Instruction> instr = Arrays.asList(Instruction.MOVE);
		Position p1 = mock(Position.class);
		PlayerState state = new PlayerState(toto, p1, NORTH, instr);
		assertEquals(NORTH, state.getOrientation());
		state.setOrientation(SOUTH);
		assertEquals(SOUTH, state.getOrientation());
	}

	@Test
	void testSetPosition() {
		Player toto = new Player("toto");
		List<Instruction> instr = Arrays.asList(Instruction.MOVE);
		Position p1 = mock(Position.class);
		Position p2 = mock(Position.class);
		PlayerState state = new PlayerState(toto, p1, NORTH, instr);
		assertEquals(p1, state.getPosition());
		state.setPosition(p2);
		assertEquals(p2, state.getPosition());
	}

}
