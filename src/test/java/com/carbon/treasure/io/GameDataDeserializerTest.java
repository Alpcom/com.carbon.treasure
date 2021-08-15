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
package com.carbon.treasure.io;

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
import com.carbon.treasure.domain.Instruction;
import com.carbon.treasure.domain.Orientation;
import com.carbon.treasure.domain.PlayerState;
import com.carbon.treasure.domain.map.CartesianPosition;
import com.carbon.treasure.domain.map.Cell;
import com.carbon.treasure.domain.map.GameMap;
import com.carbon.treasure.domain.map.TreasureCell;
import com.carbon.treasure.service.GameDataDeserializationService;
import com.carbon.treasure.unittest.UnitTestResourceHelper;

import junit.framework.AssertionFailedError;

public class GameDataDeserializerTest {

	private static GameDataDeserializationService parser;

	@BeforeAll
	public static void beforeAll() {
		GameDataDeserializerTest.parser = new GameDataIOServiceImpl();
	}

	@Test
	public void testWorkingParsing_1() {
		var fis = UnitTestResourceHelper.getInputStream("parsingOk_1");
		var parse = GameDataDeserializerTest.parser.parse(fis);
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

		PlayerState indiana = parse.getAdventurers().stream().filter(ps -> ps.getPlayer().getName().equals("Indiana"))
				.findFirst().orElseThrow();
		assertEquals(new CartesianPosition(1, 1), indiana.getPosition());
		assertEquals(Orientation.SOUTH, indiana.getOrientation());
		List<Instruction> instructions = new ArrayList<>(indiana.getRemainingInstructions());
		instructions.removeAll(Arrays.asList(//
				Instruction.MOVE, //
				Instruction.MOVE, //
				Instruction.RIGHT, //
				Instruction.MOVE, //
				Instruction.RIGHT, //
				Instruction.MOVE));
		assertTrue(instructions.isEmpty());
		assertEquals(1, parse.getAdventurers().size());

	}

	@Test
	public void testWorkingParsing_2() {
		var fis = UnitTestResourceHelper.getInputStream("parsingOk_2");
		var parse = GameDataDeserializerTest.parser.parse(fis);
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

		PlayerState lara = parse.getAdventurers().stream() //
				.filter(ps -> ps.getPlayer().getName().equals("Lara")) //
				.findFirst().orElseThrow();
		assertEquals(new CartesianPosition(1, 1), lara.getPosition());
		assertEquals(Orientation.NORTH, lara.getOrientation());
		List<Instruction> larasInstructions = new ArrayList<>(lara.getRemainingInstructions());
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

		PlayerState toto = parse.getAdventurers().stream()//
				.filter(ps -> ps.getPlayer().getName().equals("Toto")) //
				.findFirst().orElseThrow();
		assertEquals(new CartesianPosition(1, 1), toto.getPosition());
		assertEquals(Orientation.EAST, toto.getOrientation());
		List<Instruction> totosInstructions = new ArrayList<>(toto.getRemainingInstructions());
		totosInstructions.removeAll(Arrays.asList(Instruction.MOVE));
		assertTrue(totosInstructions.isEmpty());

		PlayerState amalya = parse.getAdventurers().stream()//
				.filter(ps -> ps.getPlayer().getName().equals("Amalya")) //
				.findFirst().orElseThrow();
		assertEquals(new CartesianPosition(1, 1), amalya.getPosition());
		assertEquals(Orientation.SOUTH, amalya.getOrientation());
		List<Instruction> amalyasInstructions = new ArrayList<>(amalya.getRemainingInstructions());
		amalyasInstructions.removeAll(Arrays.asList(Instruction.RIGHT));
		assertTrue(amalyasInstructions.isEmpty());

		PlayerState nathanael = parse.getAdventurers().stream()//
				.filter(ps -> ps.getPlayer().getName().equals("Nathanaël")) //
				.findFirst().orElseThrow();
		assertEquals(new CartesianPosition(1, 1), nathanael.getPosition());
		assertEquals(Orientation.WEST, nathanael.getOrientation());
		List<Instruction> nathanaelsInstructions = new ArrayList<>(nathanael.getRemainingInstructions());
		nathanaelsInstructions.removeAll(Arrays.asList(Instruction.MOVE));
		assertTrue(nathanaelsInstructions.isEmpty());

		assertEquals(4, parse.getAdventurers().size());

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
			GameDataDeserializerTest.parser.parse(fis);
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
Arguments.of("parsingFailAdventurerTwiceSet", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger1")),
Arguments.of("parsingFailMalformedAdventurer_1", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger19", "A - Indiana - 1 - 1 - S - AADADA - 1")),			
Arguments.of("parsingFailMalformedAdventurer_2", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger19", "A - Indiana - A - 1 - S - AADADA")),			
Arguments.of("parsingFailMalformedAdventurer_3", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger19", "A - Indiana - 1 - B - S - AADADA")),			
Arguments.of("parsingFailMalformedAdventurer_4", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger19", "A - Indiana - 1 - 1 - T - AADADA")),			
Arguments.of("parsingFailMalformedAdventurer_5", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger0", "A - Indiana - 1 - 1 - S - Y",System.lineSeparator(),"Y")),			
//treasure
Arguments.of("parsingFailTreasureTwiceSet", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger20")),
Arguments.of("parsingFailMalformedTreasure_1", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger22", "T - 3 - 4")),
Arguments.of("parsingFailMalformedTreasure_2", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger22", "T - A - 1 - 1")),
Arguments.of("parsingFailMalformedTreasure_3", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger22", "T - 3 - B - 1")),
Arguments.of("parsingFailMalformedTreasure_4", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger22", "T - 3 - 1 - C")),
				
//moutain
Arguments.of("parsingFailMountainTwiceSet", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger23",1,1)),
Arguments.of("parsingFailMalformedMountain_1", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger25", "M - 3 - 4 - 1")),
Arguments.of("parsingFailMalformedMountain_2", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger25", "M - A - 1")),
Arguments.of("parsingFailMalformedMountain_3", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger25", "M - 3 - B")),
// map
Arguments.of("parsingFailMapTwiceSet", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger26")),
Arguments.of("parsingFailMalformedMap_1", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger28", "C - 3 - 4 - 1")),
Arguments.of("parsingFailMalformedMap_2", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger28", "C - A - 1")),
Arguments.of("parsingFailMalformedMap_3", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger28", "C - 3 - B")),

Arguments.of("parsingFailUnknowLineStart", getMsg(Messages::getMessage, "GameDataSerializationDelegate.Logger29","G - 2"))

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
