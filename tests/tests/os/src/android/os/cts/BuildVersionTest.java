/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.os.cts;

import dalvik.annotation.TestTargetClass;

import android.os.Build;

import junit.framework.TestCase;

@TestTargetClass(Build.VERSION.class)
public class BuildVersionTest extends TestCase {

    // TODO: need to change these constants once release number and SDK API value has been
    // defined for donut 
    private static final String EXPECTED_RELEASE = "Donut";
    private static final String EXPECTED_SDK = "3";

    public void testReleaseVersion() {
        // Applications may rely on the exact release version
        assertEquals(EXPECTED_RELEASE, Build.VERSION.RELEASE);
        assertEquals(EXPECTED_SDK, Build.VERSION.SDK);
    }
}
