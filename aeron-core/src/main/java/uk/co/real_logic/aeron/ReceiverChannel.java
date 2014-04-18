/*
 * Copyright 2014 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.real_logic.aeron;

import uk.co.real_logic.aeron.admin.ChannelNotifiable;
import uk.co.real_logic.aeron.admin.TermBufferNotifier;
import uk.co.real_logic.aeron.util.concurrent.logbuffer.LogReader;
import uk.co.real_logic.aeron.util.protocol.DataHeaderFlyweight;

import static uk.co.real_logic.aeron.Receiver.MessageFlags.NONE;

public class ReceiverChannel extends ChannelNotifiable
{
    private LogReader[] logReaders;
    private final Receiver.DataHandler dataHandler;
    private final DataHeaderFlyweight dataHeader;

    public ReceiverChannel(final Destination destination, final long channelId, final Receiver.DataHandler dataHandler)
    {
        super(new TermBufferNotifier(), destination.destination(), channelId);
        this.dataHandler = dataHandler;
        dataHeader = new DataHeaderFlyweight();
    }

    public boolean matches(final String destination, final long channelId)
    {
        return this.destination.equals(destination) && this.channelId == channelId;
    }

    public int process() throws Exception
    {
        if (logReaders != null)
        {
            final LogReader logReader = logReaders[currentBuffer];
            return logReader.read((buffer, offset, length) ->
            {
                dataHeader.wrap(buffer, offset);
                dataHandler.onData(buffer, dataHeader.dataOffset(), dataHeader.sessionId(), NONE);
            });
        }
        return 0;
    }

    protected void rollTerm()
    {

    }

    public void onBuffersMapped(final LogReader[] logReaders)
    {
        this.logReaders = logReaders;
    }

}
