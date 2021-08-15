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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hamcrest.text.IsEqualCompressingWhiteSpace;
import org.junit.jupiter.api.Test;

import com.carbon.treasure.integrationTest.IntegrationTestResourceHelper;

import picocli.CommandLine;

class TreasureCliTest {

	@Test
	void testDefaultParam() throws IOException {
		Path outputPath = Paths.get(".", "output.treasure");
		if (outputPath.toFile().exists()) {
			Files.delete(outputPath);
		}
		var exitCode = new CommandLine(new TreasureCli())
				.execute("-i=./src/test/resources/com/carbon/treasure/integrationTest/it1");
		assertEquals(0, exitCode);
		var golden = new String(IntegrationTestResourceHelper.getInputStream("it1_golden_output").readAllBytes());
		try (var fileInputStream = new FileInputStream(outputPath.toFile())) {
			var output = new String(fileInputStream.readAllBytes());
			assertTrue(IsEqualCompressingWhiteSpace.equalToCompressingWhiteSpace(golden).matches(output));
		}
	}

	@Test
	void testFullParam() throws IOException {
		Path outputPath = Paths.get("./target/output/treasurecli1");
		if (outputPath.toFile().exists()) {
			Files.delete(outputPath);
		}
		runAndCheck(outputPath);
		runAndCheck(outputPath);
	}

	private void runAndCheck(Path outputPath) throws IOException, FileNotFoundException {
		var exitCode = new CommandLine(new TreasureCli()).execute(
				"-i=./src/test/resources/com/carbon/treasure/integrationTest/it1", //
				"-o=" + outputPath.toString());
		assertEquals(0, exitCode);
		var golden = new String(IntegrationTestResourceHelper.getInputStream("it1_golden_output").readAllBytes());
		try (var fileInputStream = new FileInputStream(outputPath.toFile())) {
			var output = new String(fileInputStream.readAllBytes());
			assertTrue(IsEqualCompressingWhiteSpace.equalToCompressingWhiteSpace(golden).matches(output));
		}
	}

	@Test
	void testError() throws IOException {
		Path outputPath = mock(Path.class);
		File file = mock(File.class);
		doReturn(file).when(outputPath).toFile();
		doReturn(true).when(file).exists();

		TreasureCli command = new TreasureCli();

		command.outputFilePath = outputPath;
		CommandLine commandLine = new CommandLine(command);
		commandLine.setExecutionExceptionHandler(command::handleExecutionException);

		var exitCode = commandLine.execute("-i=./src/test/resources/com/carbon/treasure/integrationTest/it1" //
		);
		assertEquals(4, exitCode);

	}

}
