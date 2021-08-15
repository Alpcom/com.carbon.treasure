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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carbon.treasure.Messages;
import com.carbon.treasure.domain.GameData;
import com.carbon.treasure.domain.PlayerState;
import com.carbon.treasure.domain.map.Cell;
import com.carbon.treasure.domain.map.CellFactoryImpl;
import com.carbon.treasure.domain.map.GameMap;
import com.carbon.treasure.domain.map.Position;
import com.carbon.treasure.service.GameDataDeserializationService;
import com.carbon.treasure.service.GameDataSerializationService;

/**
 * Implementation of {@link GameDataDeserializationService} for basic file see
 * the readme that describe the file
 */
public class GameDataIOServiceImpl implements GameDataDeserializationService, GameDataSerializationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameDataIOServiceImpl.class);

	@Override
	public GameData parse(InputStream inputStream) {
		LOGGER.info(Messages.getMessage("GameDataSerializationServiceImpl.Logger0")); //$NON-NLS-1$
		GameDataSerializationDelegate builder = new GameDataSerializationDelegate();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		reader.lines().forEach(builder::readLine);
		GameData result = builder.build(new CellFactoryImpl());
		LOGGER.info(Messages.getMessage("GameDataSerializationServiceImpl.Logger1")); //$NON-NLS-1$
		return result;
	}

	@Override
	public void serialized(GameData data, OutputStream outputStream) {
		LOGGER.info(Messages.getMessage("GameDataSerializationServiceImpl.Logger2")); //$NON-NLS-1$
		List<String> lines = toLines(data);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
		try {
			writer.append(String.join(System.lineSeparator(), lines));
			writer.flush();
		} catch (IOException e) {
			throw new SerializationException(e);
		}
		LOGGER.info(Messages.getMessage("GameDataSerializationServiceImpl.Logger3")); //$NON-NLS-1$

	}

	private List<String> toLines(GameData data) {
		LinkedList<String> lines = new LinkedList<>();
		lines.addAll(mapToLines(data.getMap()));
		lines.add(Messages.getMessage("GameDataSerializationServiceImpl.OutputFormat0")); //$NON-NLS-1$
		data.getAdventurers().stream().map(this::scoreToLine).forEach(lines::add);
		return lines;
	}

	private List<String> mapToLines(GameMap map) {
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		LinkedList<Position> mountains = new LinkedList<>();
		Map<Position, Integer> tresors = new LinkedHashMap<>();
		for (Cell c : map.getCells()) {
			int x = c.getPosition().getX();
			if (x > maxX) {
				maxX = x;
			}
			int y = c.getPosition().getY();
			if (y > maxY) {
				maxY = y;
			}
			if (c.isMountain()) {
				mountains.add(c.getPosition());
			}
			if (c.isTreasure() && c.canRemoveATreasure()) {
				tresors.put(c.getPosition(), c.getTreasureCount());
			}
		}
		LinkedList<String> toDump = new LinkedList<>();
		toDump.add(String.format(Messages.getMessage("GameDataSerializationServiceImpl.OutputFormat1"), maxX + 1, //$NON-NLS-1$
				maxY + 1));
		mountains.stream().map(this::formatMoutain).forEach(toDump::add);
		toDump.add(Messages.getMessage("GameDataSerializationServiceImpl.OutputFormat2")); //$NON-NLS-1$
		tresors.entrySet().stream().map(this::formatTreasure).forEach(toDump::add);
		return toDump;
	}

	private String formatTreasure(Entry<Position, Integer> p) {
		return String.format(Messages.getMessage("GameDataSerializationServiceImpl.OutputFormat3"), p.getKey().getX(), // $NON-NLS-1$ //$NON-NLS-1$
				p.getKey().getY(), p.getValue());

	}

	private String formatMoutain(Position p) {
		return String.format(Messages.getMessage("GameDataSerializationServiceImpl.OutputFormat4"), p.getX(), p.getY()); //$NON-NLS-1$
	}

	private String scoreToLine(PlayerState state) {
		Position position = state.getPosition();
		return String.format(Messages.getMessage("GameDataSerializationServiceImpl.OutputFormat5"), //$NON-NLS-1$
				state.getPlayer().getName(), position.getX(), position.getY(),
				state.getOrientation().toString().charAt(0), state.getScorePoint());
	}

}