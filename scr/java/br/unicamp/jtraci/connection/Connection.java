package br.unicamp.jtraci.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

/*
 * Modifications by Enrico Gueli:
 * - added generic type to Vector
 * - changed visibility of attributes to protected
 */

@SuppressWarnings("javadoc")
public class Connection {

    private int port;
    private String host;
    private ServerSocket serverSocket;
    private Socket socketConnection;
    private InputStream inStream;
    private OutputStream outStream;

    public Connection(String host, int port) {
        this.setHost(host);
        this.setPort(port);
    }


    public Connection(int port) {
        this.setHost("localhost");
        this.setPort(port);
    }

    public void accept() {
        try {
            setServerSocket(new ServerSocket(getPort()));
            setSocketConnection(getServerSocket().accept());

            setOutStream(getSocketConnection().getOutputStream());
            setInStream(getSocketConnection().getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void connect() {
        try {
            setSocketConnection(new Socket(getHost(), getPort()));

            setOutStream(getSocketConnection().getOutputStream());
            setInStream(getSocketConnection().getInputStream());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException exIO) {
            exIO.printStackTrace();
        }
    }

    public void send(Vector<Integer> data) {
        int numBytes = 0;
        byte[] buffer;


        if (getSocketConnection() == null || getOutStream() == null)
            return;

        try {
            numBytes = data.size();
            buffer = new byte[numBytes];
            for (int i = 0; i < numBytes; i++)
                buffer[i] = ((Integer) (data.get(i))).byteValue();

            //System.err.println ("Send " + numBytes + " bytes via tcpip::socket: " + data.toString());

            getOutStream().write(buffer);
        } catch (IOException exIO) {
            exIO.printStackTrace();
        }
    }

    public void sendExact(Storage storageToSend) {
        int length;
        byte[] buffer;

        try {
            buffer = new byte[4 + storageToSend.size()];

            Storage storageLength = new Storage();

            length = storageToSend.size() + 4;
            storageLength.writeInt(length);


            for (int i = 0; i < 4; i++)
                buffer[i] = storageLength.getStorageList().get(i).byteValue();

            for (int i = 0; i < storageToSend.size(); i++)
                buffer[i + 4] = storageToSend.getStorageList().get(i).byteValue();

/*
        System.err.print("Send " + length + " bytes via tcpip::socket: ");
		for (int i = 0; i < length; ++i)
			System.err.print( " " + buffer[i] + " ");
		System.err.println("]");
*/
            getOutStream().write(buffer);
        } catch (IOException exIO) {
            exIO.printStackTrace();
        }
    }


    public Vector<Integer> receive(int bufSize) {
        Vector<Integer> returnData = new Vector<Integer>(0);
        byte[] buffer;
        int bytesRead;
        int returnByte;

        try {
            if (getSocketConnection() == null)
                connect();
            if (getInStream() == null)
                return returnData;

            buffer = new byte[bufSize];
            bytesRead = getInStream().read(buffer, 0, bufSize);

            if (bytesRead == -1) {
                throw new IOException("Socket.receive(): Socket closed unexpectedly");
            } else {
                returnData.ensureCapacity(bytesRead);
                for (int i = 0; i < bytesRead; i++) {
                    returnByte = buffer[i] & 0xFF;
                    returnData.add(i, new Integer(returnByte));
                }
            }

        } catch (IOException exIO) {
            exIO.printStackTrace();
        }

        //System.err.println("Rcvd " + bytesRead + " bytes via tcpip::socket: " + buffer);

        return returnData;
    }

    public byte[] receiveBytes(int bufSize){
        byte[] buffer = new byte[0];
        int bytesRead = 0;
        int readThisTime = 0;


        try {
            if (getSocketConnection() == null)
                connect();
            if (getInStream() == null)
                return null;

            buffer = new byte[bufSize];

            while (bytesRead < bufSize) {
                //DataInputStream i = new DataInputStream(inStream);
                readThisTime = getInStream().read(buffer, bytesRead, bufSize - bytesRead);

                if (readThisTime == -1) {
                    throw new IOException("Socket.receive(): Socket closed unexpectedly");
                }

                bytesRead += readThisTime;
            }
        }
        catch (IOException exIO){
            exIO.printStackTrace();
        }
/*		
        System.err.print("Rcvd " + bytesRead + " bytes via tcpip::socket: ");
		for (int i = 0; i < bytesRead; ++i)
			System.err.print( " " + buffer[i] + " ");
		System.err.println("]");
*/
        return buffer;
    }

    public Vector<Integer> receive() throws UnknownHostException, IOException {
        return receive(2048);
    }

    public Storage receiveExact() throws UnknownHostException, IOException {
        int length;

        Storage storageLength = new Storage(receiveBytes(4));
        length = storageLength.readInt() - 4;

        return new Storage(receiveBytes(length));
    }

    public void close() throws IOException {
        if (getSocketConnection() != null)
            getSocketConnection().close();
        if (getServerSocket() != null)
            getServerSocket().close();
        if (getInStream() != null)
            getInStream().close();
        if (getOutStream() != null)
            getOutStream().close();
    }

    public int port() {
        return getPort();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getSocketConnection() {
        return socketConnection;
    }

    public void setSocketConnection(Socket socketConnection) {
        this.socketConnection = socketConnection;
    }

    public InputStream getInStream() {
        return inStream;
    }

    public void setInStream(InputStream inStream) {
        this.inStream = inStream;
    }

    public OutputStream getOutStream() {
        return outStream;
    }

    public void setOutStream(OutputStream outStream) {
        this.outStream = outStream;
    }
}
