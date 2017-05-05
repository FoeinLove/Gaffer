/*
 * Copyright 2016-2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.cache.impl;


import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.gchq.gaffer.cache.ICache;
import uk.gov.gchq.gaffer.cache.ICacheService;

import java.util.Properties;

import static uk.gov.gchq.gaffer.cache.util.CacheProperties.CACHE_CONFIG_FILE;


public class HazelcastCacheService implements ICacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastCacheService.class);
    private static HazelcastInstance hazelcast;

<<<<<<< HEAD
    private static void configureHazelcast(final Properties properties) {
        String configFile = properties.getProperty(CACHE_CONFIG_FILE);
=======
    @Override
    public void initialise() {
        configureHazelcast();
        LOGGER.info(hazelcast.getCluster().getClusterState().name()); // bootstraps hazelcast
    }

    @Override
    public <K, V> ICache<K, V> getCache(final String cacheName) {
        IMap<K, V> cache = hazelcast.getMap(cacheName);
        return new HazelcastCache<>(cache);
    }

    @Override
    public void shutdown() {
        hazelcast.shutdown();
    }


    private void configureHazelcast() {
        final String configFile = System.getProperty(CACHE_CONFIG_FILE);
>>>>>>> d6477eb5a695e0440a783a4d2d5f958122bd4591
        if (configFile == null) {
            LOGGER.warn("Config file not set using system property: " + CACHE_CONFIG_FILE
                    + ". Using default settings");

            hazelcast = Hazelcast.newHazelcastInstance();
        } else {
            try {
                final Config config = new FileSystemXmlConfig(configFile);
                hazelcast = Hazelcast.newHazelcastInstance(config);
            } catch (Exception e) {
                throw new IllegalArgumentException("Could not create cache using config path: " + configFile, e);
            }
        }
    }
<<<<<<< HEAD

    @Override
    public void initialise(final Properties properties) {
        configureHazelcast(properties);
        LOGGER.info(hazelcast.getCluster().getClusterState().name()); // bootstraps hazelcast
    }

    @Override
    public void shutdown() {
        hazelcast.shutdown();
    }

    @Override
    public <K, V> ICache<K, V> getCache(final String cacheName) {
        IMap<K, V> cache = hazelcast.getMap(cacheName);
        return new HazelcastCache<>(cache);
    }
=======
>>>>>>> d6477eb5a695e0440a783a4d2d5f958122bd4591
}
