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
		//System.out.println("Initial Lookup table");
		//printMatrix(S);
		//System.out.println("Initial key table");
		//printMatrix(K);
		
		//Initial permutation
		int J = 0; //index to switch Ith index element with
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
		System.out.println("i after initialization: " + (I*I-1) + "\n");
		
		

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
				System.out.printf("%5s", S[i][j]);

			}
			System.out.println();
		}
		System.out.println();
	}

	public void generateByteStream(int i, int j, byte[] S) {

	}

	public void swap(int i, int j, byte[] S) {

	}

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
					in.close();
				} else {
					System.out.println("Please select a valid option! (1 or 2)");
					System.out.println(
							"Please select: \n" + "1: Use default key from textbook \n" + "2: Enter custom key \n");

				}

			}
		}

		RC4 rc = new RC4(p, keys);
		rc.initialize(keys);

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
