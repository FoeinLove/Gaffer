/*
 * Copyright 2016-2019 Crown Copyright
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

package uk.gov.gchq.gaffer.graph.operation.handler;

import uk.gov.gchq.gaffer.graph.operation.GetSchema;
import uk.gov.gchq.gaffer.graph.util.GraphConfig;
import uk.gov.gchq.gaffer.operation.OperationException;
import uk.gov.gchq.gaffer.store.Context;
import uk.gov.gchq.gaffer.store.Store;
import uk.gov.gchq.gaffer.store.operation.handler.OutputOperationHandler;
import uk.gov.gchq.gaffer.graph.schema.Schema;

/**
 * A {@code GetSchemaHandler} handles the {@link GetSchema} operation.
 */
public class GetSchemaHandler implements OutputOperationHandler<GetSchema, Schema> {
    @Override
    public Schema doOperation(final GetSchema operation, final Context context, final Store store) throws OperationException {
        final Schema schema;
        if (null == operation) {
            throw new OperationException("Operation cannot be null");
        }

        if (operation.isCompact()) {
            schema = Schema.fromJson(((GraphConfig)store.getConfig()).getSchema().toCompactJson());
        } else {
            schema = ((GraphConfig)store.getConfig()).getOriginalSchema();
        }

        return schema;
    }
}