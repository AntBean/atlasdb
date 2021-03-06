/*
 * Copyright 2016 Palantir Technologies, Inc. All rights reserved.
 *
 * Licensed under the BSD-3 License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.palantir.atlasdb.cli.output;

import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;

public class OutputPrinter {
    // TODO(ssouza): remove this class in a separate PR. It's not compliant with internal logging guidelines.
    private Logger logger;

    public OutputPrinter(Logger logger) {
        this.logger = logger;
    }

    public void info(String message, Object... args) {
        String infoMessage = MessageFormatter.arrayFormat(message, args).getMessage();
        logger.info("{}", infoMessage);
        System.out.println(infoMessage);
    }

    public void warn(String message, Object... args) {
        String warnMessage = MessageFormatter.arrayFormat(message, args).getMessage();
        logger.warn("{}", warnMessage);
        System.err.println(warnMessage);
    }

    public void error(final String message, Object... args) {
        String errorMessage = MessageFormatter.arrayFormat(message, args).getMessage();
        logger.error("{}", errorMessage);
        System.err.println(errorMessage);
    }
}
