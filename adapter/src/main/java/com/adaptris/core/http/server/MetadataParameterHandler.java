package com.adaptris.core.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * {@link ParameterHandler} implementation that stores headers as standard metadata.
 * 
 * @config http-parameters-as-metadata
 * @deprecated since 3.0.6 use {@link com.adaptris.core.http.jetty.MetadataParameterHandler} instead.
 * 
 */
@XStreamAlias("http-parameters-as-metadata")
@Deprecated
public class MetadataParameterHandler extends com.adaptris.core.http.jetty.MetadataParameterHandler {

  private static transient boolean warningLogged;
  private transient Logger log = LoggerFactory.getLogger(this.getClass());

  public MetadataParameterHandler() {
    super();
    if (!warningLogged) {
      log.warn("[{}] is deprecated, use [{}] instead", this.getClass().getSimpleName(),
          com.adaptris.core.http.jetty.MetadataParameterHandler.class.getName());
      warningLogged = true;
    }
  }
}
