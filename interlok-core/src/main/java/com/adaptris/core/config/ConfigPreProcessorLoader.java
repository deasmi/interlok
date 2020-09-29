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

package com.adaptris.core.config;

import java.io.InputStream;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import com.adaptris.core.CoreException;
import com.adaptris.core.management.BootstrapProperties;
import com.adaptris.util.KeyValuePairSet;

public interface ConfigPreProcessorLoader {

  ConfigPreProcessors load(BootstrapProperties bootstrapProperties) throws CoreException;

  ConfigPreProcessors load(String preProcessors, KeyValuePairSet config) throws CoreException;

  /**
   * Helper method to use the default pre-processor chain to read configuration.
   *
   * @since 3.11.0
   */
  static String loadInterlokConfig(BootstrapProperties config) throws Exception {
    String result = "";
    try (InputStream in = config.getConfigurationStream()) {
      result = IOUtils.toString(in, Charset.defaultCharset());
    }
    return new DefaultPreProcessorLoader().load(config).process(result);
  }

}
