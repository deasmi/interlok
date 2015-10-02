package com.adaptris.core.services.xml;

import com.adaptris.interlok.config.DataDestination;

class Execution {
  
  private DataDestination sourceXpathExpression;
  
  private DataDestination targetDataDestination;
  
  public Execution() {
    
  }

  public DataDestination getSourceXpathExpression() {
    return sourceXpathExpression;
  }

  public void setSourceXpathExpression(DataDestination sourceXpathExpression) {
    this.sourceXpathExpression = sourceXpathExpression;
  }

  public DataDestination getTargetDataDestination() {
    return targetDataDestination;
  }

  public void setTargetDataDestination(DataDestination targetDataDestination) {
    this.targetDataDestination = targetDataDestination;
  }

}
