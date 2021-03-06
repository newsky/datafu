/*
 * Copyright 2010 LinkedIn, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
 
package datafu.pig.util;

import java.io.IOException;

import org.apache.pig.FilterFunc;
import org.apache.pig.data.Tuple;

/**
 * Asserts some boolean.  There is a unary and a binary version.
 * <p>
 * The unary version just takes a boolean, and throws out a generic exception message when the
 * assertion is violated.
 * <p>
 * The binary version takes a String as a second argument and throws that out when the assertion
 * is violated.
 * <p>
 * Unfortunately, the pig interpreter doesn't recognize boolean expressions nested as function
 * arguments, so this has reverted to C-style booleans.  That is, the first argument should be
 * an integer.  0 for false, anything else for true.
 * <p>
 * Example:
 * <pre>
 * {@code
 * FILTER members BY ASSERT( (member_id >= 0 ? 1 : 0), 'Doh! Some member ID is negative.' );
 * }
 * </pre></p>
 */
public class ASSERT extends FilterFunc
{
  @Override
  public Boolean exec(Tuple tuple)
      throws IOException
  {
    if ((Integer) tuple.get(0) == 0) {
      if (tuple.size() > 1) {
        throw new IOException("Assertion violated: " + tuple.get(1).toString());
      }
      else {
        throw new IOException("Assertion violated.  What assertion, I do not know, but it was officially violated.");
      }
    }
    else {
      return true;
    }
  }
}
