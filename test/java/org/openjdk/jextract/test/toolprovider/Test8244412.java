/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.openjdk.jextract.test.toolprovider;

import java.nio.file.Path;

import org.openjdk.jextract.test.TestUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.assertNotNull;

/*
 * @test
 * @library /test/lib
 * @build JextractToolRunner
 * @bug 8244412
 * @summary jextract should generate static utils class for primitive typedefs
 * @run testng/othervm --enable-native-access=ALL-UNNAMED Test8244412
 */
public class Test8244412 extends JextractToolRunner {
    @Test
    public void testPrimitiveTypedefs() {
        Path typedefsOutput = getOutputFilePath("typedefsgen");
        Path typedefsH = getInputFilePath("typedefs.h");
        run("-d", typedefsOutput.toString(), typedefsH.toString()).checkSuccess();
        try(TestUtils.Loader loader = TestUtils.classLoader(typedefsOutput)) {
            Class<?> headerCls = loader.loadClass("typedefs_h");
            assertNotNull(findField(headerCls, "byte_t"));
            assertNotNull(findField(headerCls, "mysize_t"));
        } finally {
            TestUtils.deleteDir(typedefsOutput);
        }
    }
}