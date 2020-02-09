package szu.panda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author: wunt
 * @date: 2019-04-04
 * @description:
 */
public class BIOClient {

	private static Charset charset = Charset.forName("UTF-8");

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 8888);
		OutputStream outputStream = socket.getOutputStream();
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入：");
		byte[] msg = scanner.nextLine().getBytes(charset);
		outputStream.write(msg);
		scanner.close();
		socket.close();

	}

}
