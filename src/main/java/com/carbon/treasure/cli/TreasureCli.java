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
package com.carbon.treasure.cli;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carbon.treasure.domain.GameData;
import com.carbon.treasure.io.GameDataIOServiceImpl;
import com.carbon.treasure.service.GameService;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParseResult;

@Command(name = "treasure", version = "1.0", mixinStandardHelpOptions = true)
public class TreasureCli implements Callable<Integer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TreasureCli.class);

	@Option(names = { "-i", "--input-file-path" }, description = "path to file to read", required = true)
	Path inputFilePath;
	@Option(names = { "-o", "--output-file-path" }, description = "path to file to write")
	Path outputFilePath;

	@Override
	public Integer call() throws IOException {
		if (this.outputFilePath == null) {
			this.outputFilePath = Paths.get(".", "output.treasure");
		}
		var io = new GameDataIOServiceImpl();
		GameData data;
		try (InputStream inputStream = new FileInputStream(this.inputFilePath.toFile())) {
			data = io.parse(inputStream);
		}
		var service = new GameService();
		var play = service.play(data);
		var outputFile = this.outputFilePath.toFile();
		if (!outputFile.exists()) {
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();
		}
		try (OutputStream outputStream = new FileOutputStream(outputFile)) {
			io.serialized(play, outputStream);
		}
		return 0;
	}

	public static void main(String[] args) {
		var command = new TreasureCli();
		var commandLine = new CommandLine(command);
		commandLine.setExecutionExceptionHandler(command::handleExecutionException);
		var exitCode = commandLine.execute(args);
		System.exit(exitCode);
	}

	public int handleExecutionException(Exception ex, CommandLine commandLine, ParseResult parseResult)
			throws Exception {
		LOGGER.error("An Unknown error occurs");
		LOGGER.debug("Cause :", ex);
		return 1;
	}
}
