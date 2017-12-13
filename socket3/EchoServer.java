package socket3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	static PrintWriter[] pwb = new PrintWriter[10];
	static int count = 0;
	public static void main(String args[]){
		ServerSocket server = null;
		try {
			server = new ServerSocket(9852);
			System.out.println("클라이언트의 접속을 대기중");
			while(true){
				Socket socket = server.accept();
				new EchoThread(socket).start();
			}
			}catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			} finally{
					try {
						if(server !=null)	server.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
	}
}

class EchoThread  extends Thread  {
	Socket socket;
	PrintWriter pw;
	
	public EchoThread() {	}
	public EchoThread(Socket socket) {		this.socket = socket;	}


	@Override
	public void run() {
		try {
			InetAddress address = socket.getInetAddress();
			System.out.println(address.getHostAddress()+" 로부터 접속했습니다.");
			
			InputStream in;
			in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			pw = new PrintWriter(new OutputStreamWriter(out));
			EchoServer.pwb[EchoServer.count] = this.pw;
			EchoServer.count++;
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String message = null;
			while((message = br.readLine())!= null){
				System.out.println(message);
				
				pw.println(message);
				pw.flush();
			}
			br.close();
			pw.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}