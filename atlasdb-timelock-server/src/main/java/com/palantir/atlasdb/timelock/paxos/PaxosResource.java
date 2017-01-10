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
package com.palantir.atlasdb.timelock.paxos;

import java.io.File;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.palantir.paxos.PaxosAcceptor;
import com.palantir.paxos.PaxosAcceptorImpl;
import com.palantir.paxos.PaxosLearner;
import com.palantir.paxos.PaxosLearnerImpl;

@Path("/{client: [a-zA-Z0-9_-]+}")
public final class PaxosResource {
    private static final File DEFAULT_LOG_DIRECTORY = new File("var/data/");
    private static final String LEARNER_PATH = "/learner";
    private static final String ACCEPTOR_PATH = "/acceptor";

    private final File logDirectory;
    private final Map<String, PaxosLearner> paxosLearners;
    private final Map<String, PaxosAcceptor> paxosAcceptors;

    private PaxosResource(File logDirectory,
            Map<String, PaxosLearner> paxosLearners,
            Map<String, PaxosAcceptor> paxosAcceptors) {
        this.logDirectory = logDirectory;
        this.paxosLearners = paxosLearners;
        this.paxosAcceptors = paxosAcceptors;
    }

    public static PaxosResource create() {
        return create(DEFAULT_LOG_DIRECTORY);
    }

    public static PaxosResource create(File logDirectory) {
        return new PaxosResource(logDirectory, Maps.newHashMap(), Maps.newHashMap());
    }

    public void addClient(String client) {
        Preconditions.checkState(!paxosLearners.containsKey(client),
                "Paxos resource already has client '%s' registered", client);
        String rootSubdirectory = logDirectory + client + "/";
        paxosLearners.put(client, PaxosLearnerImpl.newLearner(rootSubdirectory + LEARNER_PATH));
        paxosAcceptors.put(client, PaxosAcceptorImpl.newAcceptor(rootSubdirectory + ACCEPTOR_PATH));
    }

    @Path("/learner")
    public PaxosLearner getPaxosLearner(@PathParam("client") String client) {
        return paxosLearners.get(client);
    }

    @Path("/acceptor")
    public PaxosAcceptor getPaxosAcceptor(@PathParam("client") String client) {
        return paxosAcceptors.get(client);
    }
}