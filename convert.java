import org.arabidopsis.ahocorasick.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;

public class convert
{

	public static char revcomp(char nt)
	{
		char rc;
		switch (Character.toUpperCase(nt))
		{
			case 'T': 
				rc = 'A';
				break;
			case 'C': 
				rc = 'G';
				break;
			case 'A': 
				rc = 'T';
				break;
			case 'G': 
				rc = 'C';
				break;
			case 'M': 
				rc = 'K';
				break;
			case 'R': 
				rc = 'Y';
				break;
			case 'W': 
				rc = 'W';
				break;
			case 'S': 
				rc = 'S';
				break;
			case 'Y': 
				rc = 'R';
				break;
			case 'K': 
				rc = 'M';
				break;
			case 'V': 
				rc = 'B';
				break;
			case 'H': 
				rc = 'D';
				break;
			case 'D': 
				rc = 'H';
				break;
			case 'B': 
				rc = 'V';
				break;
			case 'E': 
			case 'F': 
			case 'I': 
			case 'J': 
			case 'L': 
			case 'N': 
			case 'O': 
			case 'P': 
			case 'Q': 
			case 'U': 
			case 'X': 
			default: 
				rc = 'N';
		}
		if (Character.isLowerCase(nt)) {
			return Character.toLowerCase(rc);
		}
		return rc;
	}

	public static String revcomp(String seq)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = seq.length() - 1; i >= 0; i--)
		{
			char nt = seq.charAt(i);
			char rc = revcomp(nt);

			sb.append(rc);
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		//System.out.println("hello");
		AhoCorasick tree = new AhoCorasick();
		String terms = args[0];
		System.out.println(revcomp(terms));

	}
}

