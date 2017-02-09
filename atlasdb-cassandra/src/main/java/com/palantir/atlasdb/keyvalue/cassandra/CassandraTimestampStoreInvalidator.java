/**
 * Copyright 2017 Palantir Technologies
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
package com.palantir.atlasdb.keyvalue.cassandra;

import com.palantir.timestamp.TimestampStoreInvalidator;

public class CassandraTimestampStoreInvalidator implements TimestampStoreInvalidator {
    private final CassandraTimestampStore timestampStore;

    public CassandraTimestampStoreInvalidator(CassandraTimestampStore timestampStore) {
        this.timestampStore = timestampStore;
    }

    @Override
    public long backupAndInvalidate() {
        return timestampStore.backupExistingTimestamp(CassandraTimestampUtils.INITIAL_VALUE);
    }

    @Override
    public void revalidateFromBackup() {
        timestampStore.restoreFromBackup();
    }
}
