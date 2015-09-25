package com.adaptris.core.http.server;

import com.adaptris.core.AdaptrisMessage;

/**
 * Interface for handling HTTP Parameters.
 * 
 * 
 */
public interface ParameterHandler<T> {

  /**
   * Handle the parameters from the HTTP request.
   * 
   * @param message the target {@link AdaptrisMessage}
   * @param request the request
   * @param itemPrefix any prefix that needs to be applied.
   * @deprecated since 3.0.6, concrete implementations of this interface can decide what to do with prefixes.
   */
  @Deprecated
  public void handleParameters(AdaptrisMessage message, T request, String itemPrefix);
  
  /**
   * Handle the parameters from the HTTP request.
   * 
   * @param message the target {@link AdaptrisMessage}.
   * @param request the request.
   */
  public void handleParameters(AdaptrisMessage message, T request);


}
