import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	public static void main(String[] args) throws IOException {
		new Servidor(12345).executa();
	}

	private int porta;
	private List<PrintStream> clientes;

	public Servidor(int porta) {
		this.porta = porta;
		this.clientes = new ArrayList<PrintStream>();
	}

	public void executa() throws IOException {
		ServerSocket servidor = new ServerSocket(this.porta);
		System.out.println("porta 12345 aberta");

		while (true) {
			Socket cliente = servidor.accept();
			System.out.println("nova conex‹o com o cliente "
					+ cliente.getInetAddress().getHostAddress());

			PrintStream ps = new PrintStream(cliente.getOutputStream());
			this.clientes.add(ps);

			TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
			new Thread(tc).start();
		}

	}

	public void distribuiMensagem(String msg) {
		for (PrintStream cliente : this.clientes) {
			cliente.println(msg);
		}
	}

}
