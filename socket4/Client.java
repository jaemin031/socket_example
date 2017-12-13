package socket4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String args[]){
		 try {
			String name = "[" + args[0] + "]"; 
			Socket socket = new Socket("127.0.0.1",9001);
			System.out.println("立加凳..");
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			Thread job = new ClientThread(socket,br);
			job.start();
			String message = null;
			while((message = keyboard.readLine())!= null){
				if(message.equals("quit")) {
					pw.println(message);
					pw.flush();
					break;
				}
					pw.println(name + message);
					pw.flush();
			}
			br.close();
			pw.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class ClientThread extends Thread{
	Socket socket;
	BufferedReader br;
	
	public ClientThread() {	}

	public ClientThread(Socket socket, BufferedReader br) {
		this.socket = socket;
		this.br = br;
	}

	@Override
	public void run() {
		try {
			String message;
			if(br == null){System.out.println("立加 辆丰");}
			while((message = br.readLine())!= null){
				if(message.equals("quit")){break;}
				System.out.println(message);
			}
		} catch (IOException e) {
		} finally{
			try {
				if(socket != null) socket.close();
				if(br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

