import java.util.ArrayList;
import java.util.Scanner;

public class RC4 {

	private String pText;
	private byte[] keys;
	private int[][] S;
	private int[][] K;

	public RC4(String plaintext, byte[] keys) {
		this.pText = plaintext;
		this.keys = keys;
		S = new int[16][16];
		K = new int[16][16];
	}

	public void initialize(byte[] key) {

		int val = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				S[i][j] = val;
				K[i][j] = key[val % key.length];
				val++;
			}
		}

		// Debugging
		System.out.println("Initial Lookup table");
		printMatrix(S);
		System.out.println("Initial key table");
		printMatrix(K);

		// Initial permutation
		int J = 0; // index to switch Ith index element with
		int I = 0;
		for (I = 0; I < 16; I++) {
			for (int j = 0; j < 16; j++) {
				J = (J + S[I][j] + K[I][j]) % 256;
				swap(S, I, j, J);
			}
		}

		System.out.println("After initialization: \n");
		printMatrix(S);
		System.out.println("j after initialization: " + J);
		System.out.println("i after initialization: " + (I * I - 1) + "\n");

	}
	
	
	/**
	 * generate byte streams
	 * @param S lookup table
	 * @param N number of bytes to generate
	 */
	public void generateByteStream(int[][] S, double N) {
		System.out.println("Generating "+ N +" bytestreams");
		int tempSize = (int) Math.sqrt(N);
		ArrayList<Integer> K = new ArrayList<Integer>();
		int i = 0;
		int j = 0;
		while(N > 0)
		{
			i = (i+1) % 256;
			j = (j + S[i/16][i%16]) % 256;
			swap(S, i/16, i%16, j);
			int kthIdx = (S[i/16][i%16] + S[j/16][j%16]) % 256;
			int k = S[kthIdx/16][kthIdx%16];
			K.add(k);
			N--;
		}
		System.out.println("i after generating " + tempSize + " bytestreams: " + i);
		System.out.println("i after generating " + tempSize + " bytestreams: " + j);
		printMatrix(S);

	}

	public void swap(int[][] S, int i, int j, int J) {
		int tempI = J / 16;
		int tempJ = J % 16;
		int temp = S[i][j];
		S[i][j] = S[tempI][tempJ];
		S[tempI][tempJ] = temp;

	}

	public void printMatrix(int[][] S) {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				System.out.printf("%5s", Integer.toHexString(S[i][j]));
			}
			System.out.println();
		}
		System.out.println();
	}

	

	public String getpText() {
		return pText;
	}

	public void setpText(String pText) {
		this.pText = pText;
	}

	public byte[] getKeys() {
		return keys;
	}

	public void setKeys(byte[] keys) {
		this.keys = keys;
	}

	public int[][] getS() {
		return S;
	}

	public void setS(int[][] s) {
		S = s;
	}

	public int[][] getK() {
		return K;
	}

	public void setK(int[][] k) {
		K = k;
	}

	
	
	//Tester
	public static void main(String[] args) {
		boolean done = false;
		String p = null;
		int N = 0;
		byte[] keys = new byte[] { 0x1A, 0x2B, 0x3C, 0x4D, 0x5E, 0x6F, 0x77 };

		Scanner in = new Scanner(System.in);
		System.out.println("Enter plaintext to encrypt (without spaces):");
		while (!done) {
			p = in.next();
			if (p.equals(null)) {
				System.out.println("Enter plaintext to encrypt (without spaces):");
			} else {
				System.out.println(
						"Please select: \n" + "1: Use default key from textbook \n" + "2: Enter custom key \n");
				int option = in.nextInt();
				if (option == 1) {
					done = true;
				} else if (option == 2) {
					System.out.print("Please enter key size:");
					N = in.nextInt();
					int i = 0;

					while (i < N) {
						System.out.print(i + "-->");
						byte val = in.nextByte();
						keys[i] = val;
						i++;
					}
					done = true;
					//in.close();
				} else {
					System.out.println("Please select a valid option! (1 or 2)");
					System.out.println(
							"Please select: \n" + "1: Use default key from textbook \n" + "2: Enter custom key \n");

				}

			}
		}

		RC4 rc = new RC4(p, keys);
		rc.initialize(keys);
		System.out.println("Enter number of bytestreams to generate (int):"); 
		double numBytes = in.nextDouble();
		
		//generating bytestreams
		rc.generateByteStream(rc.getS(), numBytes);

		// For debugging
		System.out.println("PlainText: \n" + p + "\n");

		System.out.print("Key: \n {");
		for (int j = 0; j < keys.length; j++) {
			if (j == keys.length - 1) {
				System.out.print(keys[j]);
			} else {
				System.out.print(keys[j] + ", ");
			}
		}
		System.out.print("}");

	}

}
