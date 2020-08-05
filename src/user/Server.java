package user;

import java.net.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Server{
    Set<Integer> generatedQ = new LinkedHashSet<Integer>();

    public Socket socket[] = new Socket[5];
    private ServerSocket server = null;
    DataOutputStream dOUT = null;
    DataInputStream dIn = null;
    public int intLastSocket = 0;

    public Server(int port) throws IOException {
        server = new ServerSocket(8900);
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");

        while (true) {
            try {
                socket[intLastSocket] = server.accept();
                System.out.println("Client accepted");
                dIn = new DataInputStream(socket[intLastSocket].getInputStream());
                String pl = dIn.readUTF();
                System.out.println(pl);
                if(pl.equals("100"))
                {
                    Random rng = new Random();
                    while (generatedQ.size() < 2) {
                        Integer next = rng.nextInt((7 - 1) + 1) + 1;
                        generatedQ.add(next);
                    }
                    while (generatedQ.size() < 4) {
                        Integer next = rng.nextInt((7 - 1) + 1) + 1;
                        generatedQ.add(next);
                    }
                    while (generatedQ.size() < 5) {
                        Integer next = rng.nextInt((7 - 1) + 1) + 1;
                        generatedQ.add(next);
                    }
                    try {
                        int ii=intLastSocket-1;
                        System.out.println("Start "+ ii);
                        while(ii>=0) {
                            dOUT = new DataOutputStream(socket[ii].getOutputStream());
                            dOUT.writeUTF("0");
                            ii--;
                        }
                        int i=intLastSocket-1;
                        Iterator<Integer> itr = generatedQ.iterator();

                        while(i>=0) {
                            dOUT = new DataOutputStream(socket[i].getOutputStream());
                            while (itr.hasNext()) {
                                dOUT.writeInt(itr.next());
                                dOUT.flush();
                            }
                            i--;
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    dOUT = new DataOutputStream(socket[intLastSocket].getOutputStream());
                    int i = intLastSocket;
                    System.out.println(i);
                    while (i >= 0) // send the new player name to all player
                    {
                        dOUT = new DataOutputStream(socket[i].getOutputStream());
                        dOUT.writeUTF(pl);
                        System.out.println("Sent " + pl);
                        i--;
                    }
                    intLastSocket++;
                }
            } catch (Exception e) {
                socket[intLastSocket].close();
                e.printStackTrace();
            }
        }
    }
            public static void main(String args[]) throws IOException {
                Server server = new Server(8900);
            }
        }

