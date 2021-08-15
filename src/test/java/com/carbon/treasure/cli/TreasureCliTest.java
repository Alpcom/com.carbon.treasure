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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.hamcrest.text.IsEqualCompressingWhiteSpace;
import org.junit.jupiter.api.Test;

import com.carbon.treasure.integrationTest.IntegrationTestResourceHelper;

import picocli.CommandLine;

class TreasureCliTest {

	@Test
	void test1() throws IOException {
		var exitCode = new CommandLine(new TreasureCli()).execute(
				"-i=./src/test/resources/com/carbon/treasure/integrationTest/it1", //
				"-o=./target/output/treasurecli1");
		assertEquals(0, exitCode);
		var golden = new String(IntegrationTestResourceHelper.getInputStream("it1_golden_output").readAllBytes());
		try (var fileInputStream = new FileInputStream(Paths.get("./target/output/treasurecli1").toFile())) {
			var output = new String(fileInputStream.readAllBytes());
			assertTrue(IsEqualCompressingWhiteSpace.equalToCompressingWhiteSpace(golden).matches(output));
		}

	}

}
