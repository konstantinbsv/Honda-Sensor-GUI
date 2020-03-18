import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serial {

    static SerialPort serialPort;
    static InputStream streamIn;
    static final String defaultComPort = "COM8";
    static final int baudRate = 9600;
    static final int dataBits = 8;
    static final int stopBits = 1;
    static final int parity = 0;

    static Logger logger = Logger.getLogger("Serial");

    /**
     * Retrieves next line from serial (stops when it reaches '\r' or '\n')
     *
     * @return String
     */
    static String getNextLine() {
        if (streamIn == null) {
            logger.log(Level.WARNING, "streamIn null - wait for initialization");
            return "";
        }

        char currentChar;
        StringBuilder bufferString = new StringBuilder();

        try {
            do {
                currentChar = (char) streamIn.read();
                bufferString.append(currentChar);
            } while (currentChar != '\r');
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bufferString.toString();
    }


    /**
     * Send string over serial
     * @param inputString String to be sent
     */
    static void sendStringUART(String inputString) {
        logger.log(Level.INFO, inputString);        // send inputString to debugger
        byte[] buffer = inputString.getBytes();

        // send bytes in new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                serialPort.writeBytes(buffer, buffer.length);
            }
        }).start();

    }

    /**
     * Initializes serial communication using default COM port
     *
     * @return true if initialization successful
     */
    static boolean initializeSerial() {
        return initializeSerial(defaultComPort);
    }

    /**
     * Initializes serial communication on given COM port
     *
     * @param comPort COM port to open
     * @return true if initialization successful
     */
    static boolean initializeSerial(String comPort) {

        // close port if it's already open
        if (serialPort != null) {
            serialPort.closePort();
            serialPort = null;
            streamIn = null;
        }

        serialPort = SerialPort.getCommPort(comPort);
        serialPort.setComPortParameters(baudRate, dataBits, stopBits, parity);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0 , 0);

        /* Initialize COM port */
        if (serialPort.openPort()) {
            System.out.println("Port " + comPort + " opened successfully!");
            streamIn = serialPort.getInputStream();
            return true;
        } else {
            System.out.println("Port " + comPort + " unreachable");
            serialPort.closePort();
            // TODO: request new port from user
            return false;
        }
    }

    static Map<String, String> getAvailableSerialPorts() {
        SerialPort[] availableComPorts = SerialPort.getCommPorts();
        Map<String, String> portMap = new HashMap<>();

        for (SerialPort port: availableComPorts) {
            portMap.put(port.getSystemPortName(), port.getDescriptivePortName());
            logger.log(Level.INFO,  port.getSystemPortName() + ": " + port.getDescriptivePortName());
        }

        return portMap;
    }

    static boolean isPortOpened() {
        if (serialPort == null) {
            return false;
        }

        return serialPort.isOpen();
    }
}
