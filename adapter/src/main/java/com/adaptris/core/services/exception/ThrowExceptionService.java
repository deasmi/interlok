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

package com.adaptris.core.services.exception;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.CoreException;
import com.adaptris.core.ServiceException;
import com.adaptris.core.ServiceImp;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Throws an exception based on a configurable set of criteria.
 * <p>
 * Use this service as part of a workflow to throw an exception at a time and place of your choosing (rather than when the exception
 * actually occurs perhaps). The {@link ServiceException} is generated by concrete implementations of {@link ExceptionGenerator}; if
 * the generated exception is null, then no exception is thrown.
 * </p>
 * 
 * @config throw-exception-service
 * 
 * @license BASIC
 * @see ExceptionGenerator
 * 
 */
@XStreamAlias("throw-exception-service")
public class ThrowExceptionService extends ServiceImp {

  @NotNull
  @Valid
  private ExceptionGenerator exceptionGenerator;

  public ThrowExceptionService() {
    super();
  }

  public ThrowExceptionService(ExceptionGenerator eg) {
    this();
    setExceptionGenerator(eg);
  }

  public ThrowExceptionService(String uniqueId, ExceptionGenerator eg) {
    this(eg);
    setUniqueId(uniqueId);
  }


  public void doService(AdaptrisMessage msg) throws ServiceException {
    ServiceException exc = getExceptionGenerator().create(msg);
    if (exc != null) {
      throw exc;
    }
  }

  public void init() throws CoreException {
    if (exceptionGenerator == null) {
      throw new CoreException("No Exception Generator configured");
    }
  }

  public void close() {
  }

  public ExceptionGenerator getExceptionGenerator() {
    return exceptionGenerator;
  }

  /**
   * How to generate the exception for throwing.
   *
   * @param eg the generator.
   */
  public void setExceptionGenerator(ExceptionGenerator eg) {
    exceptionGenerator = eg;
  }

  @Override
  public void prepare() throws CoreException {
  }
}
