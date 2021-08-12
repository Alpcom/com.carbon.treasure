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
package com.carbon.treasure.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.carbon.treasure.Messages;
import com.carbon.treasure.domain.CartesianPosition;
import com.carbon.treasure.domain.Instruction;
import com.carbon.treasure.domain.Orientation;
import com.carbon.treasure.domain.PlayerState;
import com.carbon.treasure.domain.map.Cell;
import com.carbon.treasure.domain.map.GameMap;
import com.carbon.treasure.domain.map.TreasureCell;
import com.carbon.treasure.service.PlayerHandler;
import com.carbon.treasure.unittest.UnitTestResourceHelper;

import junit.framework.AssertionFailedError;

public class InputDataParserTest {

	private static InputDataDeserialisationService parser;

	@BeforeAll
	public static void beforeAll() {
		InputDataParserTest.parser = new InputDataBasicFileDeserialisationService();
	}

	@Test
	public void testWorkingParsing_1() {
		var fis = UnitTestResourceHelper.getInputStream("parsingOk_1");
		var parse = InputDataParserTest.parser.parse(fis);
		GameMap map = parse.getMap();
		assertEquals(12, map.getCells().size());

		testAllCellAreInMap(map, 3, 4);

		Cell firstMountain = map.getCellAt(1, 1);
		assertTrue(firstMountain.isMountain());

		Cell secondMountain = map.getCellAt(2, 2);
		assertTrue(secondMountain.isMountain());

		Cell firstTreasure = map.getCellAt(0, 3);
		assertTrue(firstTreasure.isTreasure());
		TreasureCell castedFirstTreasure = (TreasureCell) firstTreasure;
		assertEquals(2, castedFirstTreasure.getTreasureCount());

		Cell secondTreasure = map.getCellAt(1, 3);
		assertTrue(secondTreasure.isTreasure());
		TreasureCell castedSecondTreasure = (TreasureCell) secondTreasure;
		assertEquals(1, castedSecondTreasure.getTreasureCount());

		PlayerHandler indiana = parse.getAdventurers().getPlayerHandler("Indiana");
		PlayerState state = indiana.getCurrentState();
		assertEquals(new CartesianPosition(1, 1), state.getPosition());
		assertEquals(Orientation.SOUTH, state.getOrientation());
		List<Instruction> instructions = new ArrayList<>(indiana.getInstructions());
		instructions.removeAll(Arrays.asList(//
				Instruction.MOVE, //
				Instruction.MOVE, //
				Instruction.RIGHT, //
				Instruction.MOVE, //
				Instruction.RIGHT, //
				Instruction.MOVE));
		assertTrue(instructions.isEmpty());
		assertEquals(1, parse.getAdventurers().count());

	}

	@Test
	public void testWorkingParsing_2() {
		var fis = UnitTestResourceHelper.getInputStream("parsingOk_2");
		var parse = InputDataParserTest.parser.parse(fis);
		GameMap map = parse.getMap();
		assertEquals(12, map.getCells().size());

		testAllCellAreInMap(map, 3, 4);

		Cell firstMountain = map.getCellAt(1, 0);
		assertTrue(firstMountain.isMountain());

		Cell secondMountain = map.getCellAt(2, 1);
		assertTrue(secondMountain.isMountain());

		Cell firstTreasure = map.getCellAt(0, 3);
		assertTrue(firstTreasure.isTreasure());
		TreasureCell castedFirstTreasure = (TreasureCell) firstTreasure;
		assertEquals(2, castedFirstTreasure.getTreasureCount());

		Cell secondTreasure = map.getCellAt(1, 3);
		assertTrue(secondTreasure.isTreasure());
		TreasureCell castedSecondTreasure = (TreasureCell) secondTreasure;
		assertEquals(3, castedSecondTreasure.getTreasureCount());

		PlayerHandler lara = parse.getAdventurers().getPlayerHandler("Lara");
		PlayerState state = lara.getCurrentState();
		assertEquals(new CartesianPosition(1, 1), state.getPosition());
		assertEquals(Orientation.NORTH, state.getOrientation());
		List<Instruction> larasInstructions = new ArrayList<>(lara.getInstructions());
		larasInstructions.removeAll(Arrays.asList(//
				Instruction.MOVE, //
				Instruction.MOVE, //
				Instruction.RIGHT, //
				Instruction.MOVE, //
				Instruction.RIGHT, //
				Instruction.MOVE, //
				Instruction.LEFT, //
				Instruction.LEFT, //
				Instruction.MOVE));
		assertTrue(larasInstructions.isEmpty());

		PlayerHandler toto = parse.getAdventurers().getPlayerHandler("Toto");
		PlayerState totosState = toto.getCurrentState();
		assertEquals(new CartesianPosition(1, 1), totosState.getPosition());
		assertEquals(Orientation.EAST, totosState.getOrientation());
		List<Instruction> totosInstructions = new ArrayList<>(toto.getInstructions());
		totosInstructions.removeAll(Arrays.asList(Instruction.MOVE));
		assertTrue(totosInstructions.isEmpty());

		PlayerHandler amalya = parse.getAdventurers().getPlayerHandler("Amalya");
		PlayerState amalyasState = amalya.getCurrentState();
		assertEquals(new CartesianPosition(1, 1), amalyasState.getPosition());
		assertEquals(Orientation.SOUTH, amalyasState.getOrientation());
		List<Instruction> amalyasInstructions = new ArrayList<>(amalya.getInstructions());
		amalyasInstructions.removeAll(Arrays.asList(Instruction.RIGHT));
		assertTrue(amalyasInstructions.isEmpty());

		PlayerHandler nathanael = parse.getAdventurers().getPlayerHandler("Nathanaël");
		PlayerState nathanaelsState = nathanael.getCurrentState();
		assertEquals(new CartesianPosition(1, 1), nathanaelsState.getPosition());
		assertEquals(Orientation.WEST, nathanaelsState.getOrientation());
		List<Instruction> nathanaelsInstructions = new ArrayList<>(nathanael.getInstructions());
		nathanaelsInstructions.removeAll(Arrays.asList(Instruction.MOVE));
		assertTrue(nathanaelsInstructions.isEmpty());

		assertEquals(4, parse.getAdventurers().count());

	}

	private void testAllCellAreInMap(GameMap map, int width, int height) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				try {
					map.getCellAt(x, y);
				} catch (NoSuchElementException e) {
					fail(String.format("No Cell defined at %s,%s", x, y));
				}
			}
		}
	}

	@ParameterizedTest
	@MethodSource("getFailingParsingData")
	public void testFailParsing(String fileNameToParse, String expectedFailmsg) {
		var fis = UnitTestResourceHelper.getInputStream(fileNameToParse);
		Objects.requireNonNull(fis);
		try {
			InputDataParserTest.parser.parse(fis);
			fail("No exception has been thrown");
		} catch (RuntimeException e) {
			if (!expectedFailmsg.equals(e.getMessage())) {
				throw new AssertionFailedError(
						"unexpected msg ==> expected: <" + expectedFailmsg + "> but was: <" + e.getMessage() + ">");
			}
		} catch (Exception e) {
			fail("unexpected exception", e);
		}
	}

	private static Stream<Arguments> getFailingParsingData() {
		return Stream.of(
	// @formatter:off
//adventurer
Arguments.of("parsingFailAdventurerTwiceSet", getMsg(Messages::getMessage, "InputDataDelegate.Logger1")),
Arguments.of("parsingFailMalformedAdventurer_1", getMsg(Messages::getMessage, "InputDataDelegate.Logger19", "A - Indiana - 1 - 1 - S - AADADA - 1")),			
Arguments.of("parsingFailMalformedAdventurer_2", getMsg(Messages::getMessage, "InputDataDelegate.Logger19", "A - Indiana - A - 1 - S - AADADA")),			
Arguments.of("parsingFailMalformedAdventurer_3", getMsg(Messages::getMessage, "InputDataDelegate.Logger19", "A - Indiana - 1 - B - S - AADADA")),			
Arguments.of("parsingFailMalformedAdventurer_4", getMsg(Messages::getMessage, "InputDataDelegate.Logger19", "A - Indiana - 1 - 1 - T - AADADA")),			
Arguments.of("parsingFailMalformedAdventurer_5", getMsg(Messages::getMessage, "InputDataDelegate.Logger0", "A - Indiana - 1 - 1 - S - Y",System.lineSeparator(),"Y")),			
//treasure
Arguments.of("parsingFailTreasureTwiceSet", getMsg(Messages::getMessage, "InputDataDelegate.Logger20")),
Arguments.of("parsingFailMalformedTreasure_1", getMsg(Messages::getMessage, "InputDataDelegate.Logger22", "T - 3 - 4")),
Arguments.of("parsingFailMalformedTreasure_2", getMsg(Messages::getMessage, "InputDataDelegate.Logger22", "T - A - 1 - 1")),
Arguments.of("parsingFailMalformedTreasure_3", getMsg(Messages::getMessage, "InputDataDelegate.Logger22", "T - 3 - B - 1")),
Arguments.of("parsingFailMalformedTreasure_4", getMsg(Messages::getMessage, "InputDataDelegate.Logger22", "T - 3 - 1 - C")),
				
//moutain
Arguments.of("parsingFailMountainTwiceSet", getMsg(Messages::getMessage, "InputDataDelegate.Logger23",1,1)),
Arguments.of("parsingFailMalformedMountain_1", getMsg(Messages::getMessage, "InputDataDelegate.Logger25", "M - 3 - 4 - 1")),
Arguments.of("parsingFailMalformedMountain_2", getMsg(Messages::getMessage, "InputDataDelegate.Logger25", "M - A - 1")),
Arguments.of("parsingFailMalformedMountain_3", getMsg(Messages::getMessage, "InputDataDelegate.Logger25", "M - 3 - B")),
// map
Arguments.of("parsingFailMapTwiceSet", getMsg(Messages::getMessage, "InputDataDelegate.Logger26")),
Arguments.of("parsingFailMalformedMap_1", getMsg(Messages::getMessage, "InputDataDelegate.Logger28", "C - 3 - 4 - 1")),
Arguments.of("parsingFailMalformedMap_2", getMsg(Messages::getMessage, "InputDataDelegate.Logger28", "C - A - 1")),
Arguments.of("parsingFailMalformedMap_3", getMsg(Messages::getMessage, "InputDataDelegate.Logger28", "C - 3 - B")),

Arguments.of("parsingFailUnknowLineStart", getMsg(Messages::getMessage, "InputDataDelegate.Logger29","G - 2"))

	// @formatter:on
		);
	}

	private static Object getMsg(UnaryOperator<String> msgRetriever, String key, Object... args) {
		if (args != null && args.length != 0) {
			return String.format(msgRetriever.apply(key), args);
		} else {
			return msgRetriever.apply(key);
		}
	}

}
