import com.fazecast.jSerialComm.SerialPort;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serial {

    static SerialPort serialPort;
    static InputStream streamIn;

    static final String USER_DEFAULT_PORT_FILE = "user_default_port.txt"; // for storing last successfully opened COM port
    static final String DEFAULT_COM_PORT = "COM8";
    static final int BAUD_RATE = 9600;
    static final int DATA_BITS = 8;
    static final int STOP_BITS = 1;
    static final int PARITY = 0;

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
        return initializeSerial(getLastCOMPort());
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
        serialPort.setComPortParameters(BAUD_RATE, DATA_BITS, STOP_BITS, PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0 , 0);

        /* Initialize COM port */
        if (serialPort.openPort()) {
            System.out.println("Port " + comPort + " opened successfully!");
            saveCOMPort(comPort);
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

    static void saveCOMPort(String comPort) {
        try {
            PrintWriter fileOut = new PrintWriter(USER_DEFAULT_PORT_FILE);
            fileOut.print(comPort);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String getLastCOMPort() {
        String comPort = DEFAULT_COM_PORT;

        try {
            File file = new File(USER_DEFAULT_PORT_FILE);
            Scanner scanner = new Scanner(file);
            comPort = scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return comPort;
    }
}
