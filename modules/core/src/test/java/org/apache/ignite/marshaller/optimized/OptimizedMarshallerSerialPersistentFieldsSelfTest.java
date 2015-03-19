/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ignite.marshaller.optimized;

import org.apache.ignite.testframework.junits.common.*;

import java.io.*;

/**
 * Test that Optimized Marshaller works with classes with serialPersistentFields.
 */
public class OptimizedMarshallerSerialPersistentFieldsSelfTest extends GridCommonAbstractTest {
    /**
     * @throws Exception If failed.
     */
    public void testOptimizedMarshaller() throws Exception {
        OptimizedMarshaller m = new OptimizedMarshaller();

        m.unmarshal(m.marshal(new TestClass()), TestClass.class.getClassLoader());

        TestClass2 val = m.unmarshal(m.marshal(new TestClass2()), TestClass2.class.getClassLoader());

        assertNull(val.field3);
    }

    /**
     * Test class with serialPersistentFields fields.
     */
    private static class TestClass implements Serializable {
        private static final long serialVersionUID = 0L;

        /** For serialization compatibility. */
        private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("field1", Integer.TYPE),
            new ObjectStreamField("field2", Integer.TYPE)
        };

        /**
         * @param s Object output stream.
         */
        private void writeObject(ObjectOutputStream s) throws IOException {
            s.putFields().put("field1", 1);
            s.putFields().put("field2", 2);
            s.writeFields();

            s.writeObject(null);
        }

        /**
         * @param s Object input stream.
         */
        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject();

            s.readObject();
        }
    }

    /**
     * Test class with serialPersistentFields fields.
     */
    private static class TestClass2 implements Serializable {
        private static final long serialVersionUID = 0L;

        private Integer field3 = 1;

        /** For serialization compatibility. */
        private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("field1", Integer.TYPE),
            new ObjectStreamField("field2", Integer.TYPE)
        };

        /**
         * @param s Object output stream.
         */
        private void writeObject(ObjectOutputStream s) throws IOException {
            s.putFields().put("field1", 1);
            s.putFields().put("field2", 2);
            s.writeFields();

            s.writeObject(null);
        }

        /**
         * @param s Object input stream.
         */
        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject();

            s.readObject();
        }
    }
}
