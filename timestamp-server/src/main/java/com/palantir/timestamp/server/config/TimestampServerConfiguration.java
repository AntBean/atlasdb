/**
 * Copyright 2015 Palantir Technologies
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
package com.palantir.timestamp.server.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;

public class TimestampServerConfiguration extends Configuration {
	@NotNull
	@Valid
	public ClientConfiguration lockClient = new ClientConfiguration();

	@NotNull
	@Valid
	public ClientConfiguration timestampClient = new ClientConfiguration();

	@NotNull
	@Valid
	public LeaderConfiguration leader = new LeaderConfiguration();

	@NotNull
	@Valid
	public ServerType serverType = ServerType.LEVELDB;

	public String levelDbDir = "leveldb";

	@Valid
	public CassandraKeyValueConfiguration cassandra = new CassandraKeyValueConfiguration();

	public enum ServerType {
	    LEVELDB,
	    CASSANDRA;
	}
}
