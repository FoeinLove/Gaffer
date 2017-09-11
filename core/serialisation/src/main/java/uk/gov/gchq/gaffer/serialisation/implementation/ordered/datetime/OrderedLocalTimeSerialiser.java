/*
 * Copyright 2017 Crown Copyright
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

package uk.gov.gchq.gaffer.serialisation.implementation.ordered.datetime;

import uk.gov.gchq.gaffer.serialisation.DelegateSerialiser;
import uk.gov.gchq.gaffer.serialisation.implementation.raw.CompactRawIntegerSerialiser;
import java.time.LocalTime;

public class OrderedLocalTimeSerialiser extends DelegateSerialiser<LocalTime, Integer> {

    private static final long serialVersionUID = 6636121009320739764L;
    private static final CompactRawIntegerSerialiser INT_SERIALISER = new CompactRawIntegerSerialiser();

    public OrderedLocalTimeSerialiser() {
        super(INT_SERIALISER);
    }

    @Override
    public LocalTime fromDelegateType(final Integer object) {
        return LocalTime.ofSecondOfDay(object);
    }

    @Override
    public Integer toDelegateType(final LocalTime object) {
        return object.toSecondOfDay();
    }

    @Override
    public boolean canHandle(final Class clazz) {
        return LocalTime.class.equals(clazz);
    }

    @Override
    public boolean isConsistent() {
        return true;
    }
}
