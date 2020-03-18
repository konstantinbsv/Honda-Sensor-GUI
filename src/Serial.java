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
    static final String comPort = "COM8";
    static final int baudRate = 115200;
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

        char currentChar;
        StringBuilder bufferString = new StringBuilder();

        try {
            do {
                currentChar = (char) streamIn.read();
                bufferString.append(currentChar);
            } while (currentChar != '\r');
        } catch (IOException io) {
            System.out.println("IO exception: " + io.toString());
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
     * Initializes serial communication
     *
     * @return true if initialization successful
     */
    static boolean initializeSerial() {

        serialPort = SerialPort.getCommPort(comPort);
        serialPort.setComPortParameters(baudRate, dataBits, stopBits, parity);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0 , 0);

        /* Initialize COM port */
        if (serialPort.openPort()) {
            System.out.println("Port " + comPort + " opened successfully!");
            // stmIn = new BufferedReader(new InputStreamReader(stmPort.getInputStream()));
            streamIn = serialPort.getInputStream();
            return true;
        } else {
            System.out.println("Port " + comPort + " unreachable");
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
}
