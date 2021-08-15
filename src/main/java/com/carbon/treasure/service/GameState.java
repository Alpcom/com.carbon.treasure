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

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carbon.treasure.domain.GameData;
import com.carbon.treasure.domain.PlayerState;
import com.carbon.treasure.domain.map.GameMap;
import com.carbon.treasure.domain.map.Position;

class GameState {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

	/**
	 * cyclique iterator to handle game turn it will skip player with no more
	 * instruction to execute
	 *
	 * @author aleprevost
	 *
	 */
	private static final class PlayerIterator implements Iterator<PlayerHandler> {

		private final Iterable<PlayerHandler> initial;

		private Iterator<PlayerHandler> currentIterator;

		private boolean hasNextCalled = false;

		private PlayerHandler nextElement;

		public PlayerIterator(Iterable<PlayerHandler> iterator) {
			this.initial = iterator;
			this.currentIterator = this.initial.iterator();
		}

		@Override
		public boolean hasNext() {
			this.hasNextCalled = true;
			var iteratorNotReset = true;
			var elementsNotFound = true;
			while (elementsNotFound && (this.currentIterator.hasNext() || iteratorNotReset)) {
				if (this.currentIterator.hasNext()) {
					this.nextElement = this.currentIterator.next();
					elementsNotFound = !this.nextElement.haveRemainingInstruction();
				} else {
					this.currentIterator = this.initial.iterator();
					iteratorNotReset = false;
				}
			}
			if (elementsNotFound) {
				return false;
			}
			LOGGER.debug("{} will not play : no more instruction to execute.",
					this.nextElement.getAdventurer().getName());
			return true;
		}

		@Override
		public PlayerHandler next() {
			if (!this.hasNextCalled) {
				hasNext();
			}
			this.hasNextCalled = false;
			return this.nextElement;
		}

	}

	private final LinkedHashSet<PlayerHandler> players = new LinkedHashSet<>();
	private Iterator<PlayerHandler> iterator;
	private final GameMap map;

	GameState(GameData data) {
		data.getAdventurers().forEach(this::add);
		this.map = data.getMap();
	}

	private boolean add(PlayerState adventurerState) {
		var player = new PrecomputedPlayerHandler(adventurerState);
		var playerAlreadyPresent = this.players.stream()//
				.map(PlayerHandler::getAdventurer)//
				.anyMatch(adventurerState.getPlayer()::equals);
		if (!playerAlreadyPresent) {
			this.players.add(player);
		}
		return !playerAlreadyPresent;
	}

	/**
	 * default package for test handle
	 *
	 * @return
	 */
	PlayerHandler nextPlayer() {
		if (this.iterator == null) {
			this.iterator = new PlayerIterator(new LinkedList<>(this.players));
		}
		return this.iterator.next();
	}

	boolean hasNextPlayer() {
		if (this.iterator == null) {
			this.iterator = new PlayerIterator(new LinkedList<>(this.players));
		}
		return this.iterator.hasNext();
	}

	/**
	 * convert to GameData that will be ready to be dump
	 *
	 * @return current state data
	 */
	public GameData toData() {
		return new GameData(this.map,
				this.players.stream().map(PlayerHandler::getCurrentState).collect(Collectors.toList()));
	}

	GameState play() {
		while (hasNextPlayer()) {
			var currentPlayer = nextPlayer();
			LOGGER.debug("{} starts its turn.", currentPlayer.getAdventurer().getName());
			var instructionToApply = currentPlayer.getNextInstruction();
			var currentState = currentPlayer.getCurrentState();
			switch (instructionToApply) {
			case LEFT:
				currentState.setOrientation(currentState.getOrientation().previous());
				LOGGER.debug("{} turn on left, its orientation is {}.", currentState.getPlayer().getName(),
						currentState.getOrientation());
				currentPlayer.consomeNextInstruction();
				break;
			case RIGHT:
				currentState.setOrientation(currentState.getOrientation().next());
				LOGGER.debug("{} turn on right, its orientation is {}.", currentState.getPlayer().getName(),
						currentState.getOrientation());
				currentPlayer.consomeNextInstruction();
				break;
			case MOVE:
				var targetPosition = currentState.getPosition().nextTo(currentState.getOrientation());
				var targetedCellOpt = this.map.getCellAt(targetPosition);
				var targetedCell = targetedCellOpt.get();
				if (!targetedCellOpt.isEmpty() && !targetedCell.isMountain()) {
					if (isCellAvailable(targetPosition)) {
						currentState.setPosition(targetPosition);
						LOGGER.debug("{} move to {},{}.", currentState.getPlayer().getName(), targetPosition.getX(),
								targetPosition.getY());
						if (targetedCell.isTreasure() && targetedCell.canRemoveATreasure()) {
							targetedCell.removeATreasure();
							currentPlayer.treasureFound();
							LOGGER.debug("{} found a treasure at {},{}.", currentState.getPlayer().getName(),
									targetPosition.getX(), targetPosition.getY());
						}
						currentPlayer.consomeNextInstruction();
					} else if (couldBecomeFree(targetPosition)) {
						LOGGER.debug("{} can't move to {},{} so wait.", currentState.getPlayer().getName(),
								targetPosition.getX(), targetPosition.getY());
					} else {
						currentPlayer.consomeAllInstruction();
					}
				} else {
					currentPlayer.consomeAllInstruction();
				}
			}
		}
		return this;

	}

	private boolean couldBecomeFree(Position targetPosition) {
		return !this.players.stream() //
				.map(PlayerHandler::getCurrentState) //
				.filter(s -> s.getPosition().equals(targetPosition)) //
				.findAny() //
				.map(PlayerState::getRemainingInstructions) //
				.map(List::isEmpty).orElseThrow();
	}

	private boolean isCellAvailable(Position targetPosition) {
		return this.players.stream()//
				.map(PlayerHandler::getCurrentState)//
				.map(PlayerState::getPosition) //
				.noneMatch(targetPosition::equals);
	}

}
