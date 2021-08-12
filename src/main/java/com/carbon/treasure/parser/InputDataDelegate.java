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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carbon.treasure.Messages;
import com.carbon.treasure.domain.CartesianPosition;
import com.carbon.treasure.domain.InputData;
import com.carbon.treasure.domain.Instruction;
import com.carbon.treasure.domain.Orientation;
import com.carbon.treasure.domain.Player;
import com.carbon.treasure.domain.PlayerState;
import com.carbon.treasure.domain.PlayerStates;
import com.carbon.treasure.domain.RectangularArea;
import com.carbon.treasure.domain.map.CellFactory;
import com.carbon.treasure.domain.map.GameMap;

class InputDataDelegate {
	// Instruction key
	private static final char RIGHT_KEY = 'D'; // $NON-NLS-1$
	private static final char LEFT_KEY = 'G'; // $NON-NLS-1$
	private static final char ADVANCE_KEY = 'A'; // $NON-NLS-1$
	// Orientation constant
	private static final String WEST_KEY = "W"; //$NON-NLS-1$
	private static final String SOUTH_KEY = "S"; //$NON-NLS-1$
	private static final String EAST_KEY = "E"; //$NON-NLS-1$
	private static final String NORTH_KEY = "N"; //$NON-NLS-1$
	// Significant line prefix
	private static final String ADVENTURER_KEY = "A"; //$NON-NLS-1$
	private static final String TREASURE_KEY = "T"; //$NON-NLS-1$
	private static final String MOUNTAIN_KEY = "M"; //$NON-NLS-1$
	private static final String MAP_KEY = "C"; //$NON-NLS-1$
	// parsing constant
	private static final String COMMENT_PREFIX = "#"; //$NON-NLS-1$
	private static final String SPLIT_CHAR = "-"; //$NON-NLS-1$

	private static final Logger LOGGER = LoggerFactory.getLogger(InputDataDelegate.class);

	private final GameMapBuilder mapBuilder = new GameMapBuilder();
	private final PlayerStates adventurers = new PlayerStates();

	public InputData build(CellFactory cellFactory) {
		GameMap map = mapBuilder.build(cellFactory);
		return new InputData(map, adventurers);

	}

	void readLine(String lineToRead) {
		String strippedLine = lineToRead.strip();
		LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger10"), lineToRead); //$NON-NLS-1$
		if (strippedLine.startsWith(COMMENT_PREFIX) || strippedLine.isEmpty()) {
			LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger11")); //$NON-NLS-1$
			return; // comment line or empty line : nothing to build
		} else if (strippedLine.startsWith(MAP_KEY)) {
			LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger12")); //$NON-NLS-1$
			parseMap(strippedLine);
		} else if (strippedLine.startsWith(MOUNTAIN_KEY)) {
			LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger13")); //$NON-NLS-1$
			parseMountain(strippedLine);
		} else if (strippedLine.startsWith(TREASURE_KEY)) {
			LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger14")); //$NON-NLS-1$
			parseTreasure(strippedLine);
		} else if (strippedLine.startsWith(ADVENTURER_KEY)) {
			LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger15")); //$NON-NLS-1$
			parseAdventurer(strippedLine);
		} else {
			throw new ParsingException(String.format(Messages.getMessage("InputDataDelegate.Logger29"), strippedLine)); //$NON-NLS-1$
		}

	}

	private void parseAdventurer(String strippedLine) {
		String[] split = strippedLine.split(SPLIT_CHAR);
		if (split.length != 6) {
			throw createParseAdventurerException(strippedLine, null);
		}
		String adventurerName = split[1].trim();
		int x = 0;
		try {
			x = Integer.parseInt(split[2].trim());
		} catch (NumberFormatException e) {
			throw createParseAdventurerException(strippedLine, e);
		}
		int y = 0;
		try {
			y = Integer.parseInt(split[3].trim());
		} catch (NumberFormatException e) {
			throw createParseAdventurerException(strippedLine, e);
		}
		Orientation orientation = null;
		switch (split[4].trim()) {
		case NORTH_KEY:
			orientation = Orientation.NORTH;
			break;
		case EAST_KEY:
			orientation = Orientation.EAST;
			break;
		case SOUTH_KEY:
			orientation = Orientation.SOUTH;
			break;
		case WEST_KEY:
			orientation = Orientation.WEST;
			break;
		default:
			throw createParseAdventurerException(strippedLine, null);
		}
		List<Instruction> instructions = Collections.emptyList();
		String trimmedInstructions = split[5].trim();
		try {
			instructions = trimmedInstructions.chars().mapToObj(i -> (char) i).map(this::toInstruction)
					.collect(Collectors.toList());

		} catch (IllegalArgumentException e) {
			throw new ParsingException(String.format(Messages.getMessage("InputDataDelegate.Logger0"), //$NON-NLS-1$
					strippedLine, System.lineSeparator(), e.getMessage()), e);
		}
		PlayerState adventurerState = new PlayerState(new CartesianPosition(x, y), orientation);
		Player adventurer = new Player(adventurerName);
		if (!adventurers.add(adventurer, adventurerState, instructions)) {
			throw new ParsingException(Messages.getMessage("InputDataDelegate.Logger1")); //$NON-NLS-1$
		}
		LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger18"), // //$NON-NLS-1$
				adventurerName, //
				x, y, //
				orientation, //
				trimmedInstructions);
	}

	private Instruction toInstruction(char c) {
		switch (c) {
		case ADVANCE_KEY:
			return Instruction.MOVE;
		case LEFT_KEY:
			return Instruction.LEFT;
		case RIGHT_KEY:
			return Instruction.RIGHT;
		default:
			throw new IllegalArgumentException(String.valueOf(c));
		}
	}

	private ParsingException createParseAdventurerException(String strippedLine, Exception object) {
		return new ParsingException(String.format(Messages.getMessage("InputDataDelegate.Logger19"), //$NON-NLS-1$
				strippedLine), object);
	}

	private void parseTreasure(String strippedLine) {
		String[] split = strippedLine.split(SPLIT_CHAR);
		if (split.length != 4) {
			throw createParseTreasureException(strippedLine, null);
		}
		int x = 0;
		try {
			x = Integer.parseInt(split[1].trim());
		} catch (NumberFormatException e) {
			throw createParseTreasureException(strippedLine, e);
		}
		int y = 0;
		try {
			y = Integer.parseInt(split[2].trim());
		} catch (NumberFormatException e) {
			throw createParseTreasureException(strippedLine, e);
		}
		int treasureCount = 0;
		try {
			treasureCount = Integer.parseInt(split[3].trim());
		} catch (NumberFormatException e) {
			throw createParseTreasureException(strippedLine, e);
		}
		if (mapBuilder.addTreasure(new CartesianPosition(x, y), treasureCount)) {
			throw new ParsingException(Messages.getMessage("InputDataDelegate.Logger20")); //$NON-NLS-1$
		}
		LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger21"), // //$NON-NLS-1$
				x, y, //
				treasureCount);
	}

	private ParsingException createParseTreasureException(String strippedLine, Exception object) {
		return new ParsingException(String.format(Messages.getMessage("InputDataDelegate.Logger22"), strippedLine), //$NON-NLS-1$
				object);
	}

	private void parseMountain(String strippedLine) {
		String[] split = strippedLine.split(SPLIT_CHAR);
		if (split.length != 3) {
			throw createParseMountainException(strippedLine, null);
		}
		int x = 0;
		try {
			x = Integer.parseInt(split[1].trim());
		} catch (NumberFormatException e) {
			throw createParseMountainException(strippedLine, e);
		}
		int y = 0;
		try {
			y = Integer.parseInt(split[2].trim());
		} catch (NumberFormatException e) {
			throw createParseMountainException(strippedLine, e);
		}
		if (!mapBuilder.addMountains(new CartesianPosition(x, y))) {
			throw new ParsingException(Messages.getMessage("InputDataDelegate.Logger23")); //$NON-NLS-1$
		}
		LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger24"), x, y); //$NON-NLS-1$
	}

	private ParsingException createParseMountainException(String strippedLine, Exception object) {
		return new ParsingException(String.format(Messages.getMessage("InputDataDelegate.Logger25"), strippedLine), //$NON-NLS-1$
				object);
	}

	private void parseMap(String strippedLine) {
		if (mapBuilder.containsMap()) {
			throw new ParsingException(Messages.getMessage("InputDataDelegate.Logger26")); //$NON-NLS-1$
		}
		String[] split = strippedLine.split(SPLIT_CHAR);
		if (split.length != 3) {
			throw createParseMapException(strippedLine, null);
		}
		int width = 0;
		try {
			width = Integer.parseInt(split[1].trim());
		} catch (NumberFormatException e) {
			throw createParseMapException(strippedLine, e);
		}
		int height = 0;
		try {
			height = Integer.parseInt(split[2].trim());
		} catch (NumberFormatException e) {
			throw createParseMapException(strippedLine, e);
		}
		mapBuilder.setArea(new RectangularArea(0, 0, width, height));
		LOGGER.debug(Messages.getMessage("InputDataDelegate.Logger27"), width, height); //$NON-NLS-1$
	}

	private ParsingException createParseMapException(String strippedLine, Exception object) {
		return new ParsingException(String.format(Messages.getMessage("InputDataDelegate.Logger28"), strippedLine), //$NON-NLS-1$
				object);
	}
}
