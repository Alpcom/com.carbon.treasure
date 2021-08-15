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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.carbon.treasure.domain.GameData;
import com.carbon.treasure.domain.Orientation;
import com.carbon.treasure.domain.Player;
import com.carbon.treasure.domain.PlayerState;
import com.carbon.treasure.domain.map.CartesianPosition;
import com.carbon.treasure.domain.map.Cell;
import com.carbon.treasure.domain.map.CellFactory;
import com.carbon.treasure.domain.map.CellFactoryImpl;
import com.carbon.treasure.domain.map.GameMap;
import com.carbon.treasure.service.GameDataSerializationService;

public class GameDataSerializerTest {

	private static GameDataSerializationService parser;

	@BeforeAll
	public static void beforeAll() {
		GameDataSerializerTest.parser = new GameDataIOServiceImpl();
	}

	@Test
	public void testWorkingSerialization_1() throws IOException {
		CellFactory cellFactory = new CellFactoryImpl();
		List<Cell> cells = Arrays.asList(//
				cellFactory.createMountain(new CartesianPosition(1, 6)), //
				cellFactory.createTreasure(new CartesianPosition(4, 2), 1), //
				cellFactory.createTreasure(new CartesianPosition(3, 1), 0), //
				cellFactory.createMountain(new CartesianPosition(2, 6)) //
		);

		var indiana = new PlayerState(new Player("Indiana"), new CartesianPosition(1, 2), Orientation.NORTH,
				Collections.emptyList());
		indiana.addScorePoint(3);
		List<PlayerState> players = Arrays.asList(indiana);
		var data = new GameData(new GameMap(new HashSet<>(cells)), players);
		try (var outputStream = new ByteArrayOutputStream()) {
			parser.serialized(data, outputStream);
			assertEquals("C - 5 - 7" + System.lineSeparator() //
					+ "M - 2 - 6" + System.lineSeparator() + "M - 1 - 6" + System.lineSeparator()
					+ "# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}"
					+ System.lineSeparator() + //
					"T - 4 - 2 - 1" + System.lineSeparator() //
					+ "# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe vertical} - {Orientation} - {Nb. trésors ramassés}"
					+ System.lineSeparator() //
					+ "A - Indiana - 1 - 2 - N - 3", outputStream.toString().stripTrailing());
		}
	}

	@Test
	public void testFailSerialization_1() throws IOException {
		var data = mock(GameData.class);
		doReturn(Collections.emptyList()).when(data).getAdventurers();
		var map = mock(GameMap.class);
		doReturn(map).when(data).getMap();
		doReturn(Collections.emptySet()).when(map).getCells();
		var outputStream = mock(OutputStream.class);
		doThrow(IOException.class).when(outputStream).write(any(byte[].class), anyInt(), anyInt());

		assertThrows(SerializationException.class, () -> parser.serialized(data, outputStream));
	}

	@Test
	public void testFailSerialization_2() throws IOException {
		CellFactory cellFactory = new CellFactoryImpl();
		List<Cell> cells = IntStream.range(0, 10000)
				.mapToObj(i -> cellFactory.createMountain(new CartesianPosition(i, i)))//
				.collect(Collectors.toList());
		var data = new GameData(new GameMap(new HashSet<>(cells)), Collections.emptyList());
		var outputStream = mock(OutputStream.class);
		doThrow(IOException.class).when(outputStream).write(any(byte[].class), anyInt(), anyInt());

		assertThrows(SerializationException.class, () -> parser.serialized(data, outputStream));
	}

}
