import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Prog54 {
	
	public Scanner t;
	
	public Prog54() {
		this.t=new Scanner(System.in);
	}
	
	private SecretKey generarClave() {
		SecretKey clave = null;
		try {
			//Object keyGenerator;
			KeyGenerator keyGen = KeyGenerator.getInstance("DES");
			keyGen.init(56);
			clave=keyGen.generateKey();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return clave;
	}
	
	public void mostrarMenu() {
		SecretKey clave = generarClave();
		int opcion;
		do {
			System.out.println("1.Generar archivo \n"
					+ "2.Encriptar archivo\n"
					+ "3.Desencriptar archivo\n"
					+ "4.Salir");
			
			opcion = Integer.parseInt(t.nextLine());
			
			
			switch(opcion) {
				case 1: crearArchivo();
						break;
				case 2: 
						System.out.print("Nombre del fichero(no introduzcas la extension): ");
						String fichero = t.nextLine();
						encriptarFichero(fichero, clave);
						break;
				case 3: 
						System.out.print("Nombre del fichero(sin extension): ");
						String fichero2 = t.nextLine();
						desencriptarFichero(fichero2, clave);
						break;
				case 4: System.out.println("Has salido del programa");
						break;
				default: System.out.println("opcion invalida");
			}
			
		}while(opcion!=4);
	}
		
	
	public void crearArchivo() {
		
		System.out.print("Introduce un nombre para el archivo: ");
		String nombreArchivo = t.nextLine();
		File file = new File(nombreArchivo + ".txt");
		try {
			FileWriter fw = new FileWriter(file, true);
			System.out.println("Introduce un texto en el archivo");
			String textoArchivo = t.nextLine();
			fw.write(textoArchivo);
			fw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void encriptarFichero(String fichero, SecretKey clave) {
		//System.out.println("Que fichero deseas encriptar");
		//String archivo = sc.nextLine();
		
		try {
			FileInputStream fe = null;
			FileOutputStream fs = null;
			int bytesLeidos;
			byte[] buffer = new byte[1000];
			byte[] bufferCifrado;
			fichero=fichero+".txt";
			Cipher cifrador = Cipher.getInstance("DES");
			cifrador.init(Cipher.ENCRYPT_MODE, clave);
			fe = new FileInputStream(fichero);
			fs = new FileOutputStream(fichero+".cifrado");
			bytesLeidos = fe.read(buffer,0,1000);
			while(bytesLeidos!=-1) {
				bufferCifrado = cifrador.update(buffer,0,bytesLeidos);
				fs.write(bufferCifrado);
				bytesLeidos = fe.read(buffer,0,1000);
			}
			bufferCifrado = cifrador.doFinal();
			fs.write(bufferCifrado);
			fe.close();
			fs.close();
			System.out.println("Texto encriptado con exito");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void desencriptarFichero(String fichero, SecretKey clave) {
		try {
			FileInputStream fe = null;
			FileOutputStream fs = null;
			int bytesLeidos;
			byte[] buffer = new byte[1000];
			byte[] bufferDescifrado;
			fichero = fichero+".txt.cifrado";
			Cipher cifrador = Cipher.getInstance("DES");
			cifrador.init(Cipher.DECRYPT_MODE, clave);
			fe = new FileInputStream(fichero);
			fs = new FileOutputStream(fichero+".descifrado");
			bytesLeidos = fe.read(buffer,0,1000);
			while(bytesLeidos!=-1) {
				bufferDescifrado = cifrador.update(buffer,0,bytesLeidos);
				fs.write(bufferDescifrado);
				bytesLeidos = fe.read(buffer,0,1000);
			}
			bufferDescifrado = cifrador.doFinal();
			fs.write(bufferDescifrado);
			fe.close();
			fs.close();
			System.out.println("Texto desencriptado con exito");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Prog54 ejer = new Prog54();
		ejer.mostrarMenu();
	}

}
