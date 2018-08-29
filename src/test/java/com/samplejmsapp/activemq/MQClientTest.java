
package com.samplejmsapp.activemq;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Hashtable;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQAsyncStatus;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class MQClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQClientTest.class);

    @Test
    public void testSendMessageQueue() {
        // Create a connection to the queue manager
        Hashtable<String, Object> props = new Hashtable<String, Object>();
        props.put(MQConstants.CHANNEL_PROPERTY, "DEV.APP.SVRCONN");
        props.put(MQConstants.PORT_PROPERTY, 1414);
        props.put(MQConstants.HOST_NAME_PROPERTY, "localhost");

        String qManager = "QM1";
        String queueName = "DEV.QUEUE.3";
        MQQueueManager qMgr = null;
        try {
            qMgr = new MQQueueManager(qManager, props);

            // MQOO_OUTPUT = Open the queue to put messages. The queue is opened for use with subsequent MQPUT calls.
            // MQOO_INPUT_AS_Q_DEF = Open the queue to get messages using the queue-defined default.
            // The queue is opened for use with subsequent MQGET calls. The type of access is either
            // shared or exclusive, depending on the value of the DefInputOpenOption queue attribute.
            int openOptions = MQConstants.MQOO_OUTPUT | MQConstants.MQOO_INPUT_AS_Q_DEF;

            // creating destination
            MQQueue queue = qMgr.accessQueue(queueName, openOptions);

            // specify the message options...
            MQPutMessageOptions pmo = new MQPutMessageOptions(); // default
            // MQPMO_ASYNC_RESPONSE = The MQPMO_ASYNC_RESPONSE option requests that an MQPUT or MQPUT1 operation
            // is completed without the application waiting for the queue manager to complete the call.
            // Using this option can improve messaging performance, particularly for applications using client bindings.
            pmo.options = MQConstants.MQPMO_ASYNC_RESPONSE;

            // create message
            MQMessage message = new MQMessage();
            // MQFMT_STRING = The application message data can be either an SBCS string (single-byte character set),
            // or a DBCS string (double-byte character set). Messages of this format can be converted
            // if the MQGMO_CONVERT option is specified on the MQGET call.
            message.format = MQConstants.MQFMT_STRING;
            message.writeString("<message>Hallo Vinh</message>");
            queue.put(message, pmo);
            queue.close();

            MQAsyncStatus asyncStatus = qMgr.getAsyncStatus();
            assertEquals(1, asyncStatus.putSuccessCount);
        } catch (MQException e) {
            LOGGER.error("Die Verbindung zum Message Broker mit den " + "Eigenschaften {} und dem QueueManager {} konnte nicht hergestellt werden.", props, qManager, e);
        } catch (IOException e) {
            LOGGER.error("Fehler beim Schreiben der Message.", e);
        } finally {
            try {
                qMgr.disconnect();
            } catch (MQException e) {
                LOGGER.error("Die Verbindung konnte nicht geschlossen werden.", e);
            }
        }
    }
}