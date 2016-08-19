/*
 * Copyright 2015 Adaptris Ltd.
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

package com.adaptris.core.fs.enhanced;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Sort the list of files alphabetically in reverse order
 * 
 * @config fs-sort-alphabetic-descending
 * @author lchan
 * 
 */
@XStreamAlias("fs-sort-alphabetic-descending")
public class AlphabeticDescending implements FileSorter, Comparator<File> {


  @Override
  public List<File> sort(List<File> list) {
    Collections.sort(list, this);
    return list;
  }

  @Override
  public int compare(File o1, File o2) {
    return o2.compareTo(o1);
  }

}
