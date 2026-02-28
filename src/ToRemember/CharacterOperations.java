package ToRemember;

public class CharacterOperations {
    public static void main(String[] args) {
        char ch = 'z';
		
		// Character to index
		int idx = ch - 'a';
		System.out.println(idx);
		
		// Index to character
		char ch1 = (char) (idx + 'a');
		System.out.println(ch1);
		
		// Next nth character (n==2)
		char nextNCh = (char) ('a' + (ch - 'a' + 2) % 26);
		System.out.println(nextNCh);
		
		// Previous nth character (n==2)
		char prevNCh = (char) ('a' + (ch - 'a' - 2 + 26) % 26);
		System.out.println(prevNCh);
		
		// difference between character 1 and character 2
		int diff = (nextNCh - ch + 26) % 26; // 2
		System.out.println(diff);
		
		// mirror of character
		char mirrCh = (char) (25 - (ch - 'a') + 'a');
		System.out.println(mirrCh);
		
		// toUpperCase
		char upperCaseCh = (char) (ch - 32);
		System.out.println(upperCaseCh);
		
		// toLowerCase
		char lowerCaseCh = (char) (upperCaseCh + 32);
		System.out.println(lowerCaseCh);



		// Digit-Character to Integer
		char digitCh = '7';
		int digit = digitCh - '0';
		System.out.println(digit);

		// Integer to Digit-Character
		char digitCh1 = (char) (digit + '0');
		System.out.println(digitCh1);
    }
}
