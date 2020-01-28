package com.adaptris.core.common;

import com.adaptris.core.MultiPayloadAdaptrisMessage;
import com.adaptris.interlok.InterlokException;
import com.adaptris.interlok.types.InterlokMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.InputStream;

/**
 * This {@code MultiPayloadDataInputParameter} is used when you want to
 * source data from the {@link com.adaptris.core.MultiPayloadAdaptrisMessage}
 * payload.
 *
 * @author andersonam
 * @config multi-payload-stream-input-parameter
 */
@XStreamAlias("multi-payload-stream-input-parameter")
public class MultiPayloadStreamInputParameter extends PayloadStreamInputParameter implements MultiPayloadDataInputParameter<InputStream>
{
	private String payloadId;

	@Override
	public String getPayloadId()
	{
		return payloadId;
	}

	@Override
	public void setPayloadId(String payloadId)
	{
		this.payloadId = payloadId;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public InputStream extract(InterlokMessage m) throws InterlokException
	{
		if (m instanceof MultiPayloadAdaptrisMessage)
		{
			return extract(payloadId, (MultiPayloadAdaptrisMessage)m);
		}
		throw new InterlokException("Cannot extract payload from message type " + m.getClass().getName() + " as it does not support multiple payloads.");
	}

	/**
	 * Extract the message payload, for the given ID, as an input stream.
	 *
	 * @param id The payload ID to extract.
	 * @param m The message to extract the payload from.
	 *
	 * @return The message payload.
	 */
	@Override
	public InputStream extract(String id, MultiPayloadAdaptrisMessage m)
	{
		return m.getInputStream(id != null ? id : m.getCurrentPayloadId());
	}
}
