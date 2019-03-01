/*
 * Copyright 2019 Crown Copyright
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

package uk.gov.gchq.gaffer.store.operation.handler.join.match;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.gaffer.operation.impl.join.match.Match;
import uk.gov.gchq.koryphe.impl.function.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * A {@code KeyFunctionMatch} is a {@link Match} which takes two key functions which are used to extract keys from
 * two inputs. A match occurs when the keys are equal. The first key function is applied to the Left input
 * in a Left sided join and vice versa.
 */

@JsonPropertyOrder(value = {"class", "firstKeyFunction", "secondKeyFunction"}, alphabetic = true)
public class KeyFunctionMatch implements Match {

    private static final String NULL_FUNCTION_ERROR_MESSAGE = "Key functions for left and right input cannot be null";
    private static final String NULL_LIST_ERROR_MESSAGE = "List of objects cannot be null";

    private Function firstKeyFunction;
    private Function secondKeyFunction;

    public KeyFunctionMatch() {
        this(new Identity(), new Identity());
    }

    public KeyFunctionMatch(final Function firstKeyFunction, final Function secondKeyFunction) {
        this.firstKeyFunction = firstKeyFunction;
        this.secondKeyFunction = secondKeyFunction;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
    public Function getFirstKeyFunction() {
        return firstKeyFunction;
    }

    public void setFirstKeyFunction(final Function firstKeyFunction) {
        this.firstKeyFunction = firstKeyFunction;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
    public Function getSecondKeyFunction() {
        return secondKeyFunction;
    }

    public void setSecondKeyFunction(final Function secondKeyFunction) {
        this.secondKeyFunction = secondKeyFunction;
    }

    @Override
    public List matching(final Object testObject, final List testList) {
        ArrayList<Object> matching = new ArrayList<>();

        if (this.firstKeyFunction == null || this.secondKeyFunction == null) {
            throw new IllegalArgumentException(NULL_FUNCTION_ERROR_MESSAGE);
        }
        if (testList == null) {
            throw new IllegalArgumentException(NULL_LIST_ERROR_MESSAGE);
        }

        Object firstInputCriteria = firstKeyFunction.apply(testObject);
        Object secondInputCriteria;
        for (final Object rightHandObject : testList) {
            secondInputCriteria = secondKeyFunction.apply(rightHandObject);
            if (firstInputCriteria == null && secondInputCriteria == null) {
                matching.add(null);
            } else if (secondInputCriteria != null && secondInputCriteria.equals(firstInputCriteria)) {
                matching.add(rightHandObject);
            }
        }

        return matching;
    }

    public static final class Builder {
        private Function firstKeyFunction = new Identity();
        private Function secondKeyFunction = new Identity();

        public KeyFunctionMatch build() {
            return new KeyFunctionMatch(firstKeyFunction, secondKeyFunction);
        }

        public Builder firstKeyFunction(final Function firstKeyFunction) {
            this.firstKeyFunction = firstKeyFunction;
            return this;
        }

        public Builder secondKeyFunction(final Function secondKeyFunction) {
            this.secondKeyFunction = secondKeyFunction;
            return this;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.firstKeyFunction)
                .append(this.secondKeyFunction)
                .hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        KeyFunctionMatch match = (KeyFunctionMatch) obj;

        return new EqualsBuilder()
                .append(this.firstKeyFunction, match.firstKeyFunction)
                .append(this.secondKeyFunction, match.secondKeyFunction)
                .isEquals();

    }
}