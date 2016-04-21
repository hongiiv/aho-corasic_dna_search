import org.arabidopsis.ahocorasick.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;

public class test
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

		//String[] terms = { "ATAACTGTCAATCCAGACTCTGAAGAACTT",};
		//String[] terms = new String[];
		//List<String> terms = new ArrayList<String>();
		//ArrayList<String> terms = new ArraryList<String>();
		long start = System.currentTimeMillis();
		int priSize = 0;

		try{
			String line;
			BufferedReader reader = new BufferedReader(new FileReader("/Users/hongiiv/Downloads/primers/IFU188_BRCAMASTR_Primerfile_v130744.txt"));
			//System.out.println("++++++++++++++++++");
			while ((line = reader.readLine()) != null)
			{
				//terms.add(line.split("\t")[0]);
				String line_a = line.split("\t")[1];
				String line_b = revcomp(line.split("\t")[1]);
				tree.add(line_a.getBytes(), line_a);
				tree.add(line_b.getBytes(), line_b);
				System.out.println(line_a+" "+line_b);
				//System.out.println(line.split("\t")[1]+" "+revcomp(line.split("\t")[1]));
				priSize++;
			}
			//System.out.println("++++++++++++++++++");

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		tree.prepare();
		//System.out.println(tree.toString());

		int nReads = 1;
		int tReads = 1;
		double nFound = 0.0;
		int maxReads = 4;
		String line;
		try{
			Set termsThatHit = new HashSet();
			BufferedReader reader = new BufferedReader(new FileReader("/Users/hongiiv/Illumina/160223_M03987_0015_000000000-AKDRA/Data/Intensities/BaseCalls/"+args[0]));
			//while ((nReads < maxReads) && (line  != null))
                                char A = "A".charAt(0);
                                char T = "T".charAt(0);
                                char G = "G".charAt(0);
                                char C = "C".charAt(0);
                                char N = "N".charAt(0);
                                char[] skipPosIndicators = {'?','1','2','3','4','5','6','7','8','9','0','B','D','E','F','H','I' };
                                Arrays.sort(skipPosIndicators);
			while ((line = reader.readLine()) != null)
			{

                                char a = line.charAt(0);
				if (a == A || a==T||a==G||a==C||a==N ){
				boolean isSkip = false;
				for (int j =0; j<10;j++){
					//System.out.println(j);
					//System.out.println(line.charAt(j));
					isSkip = Arrays.binarySearch(skipPosIndicators, line.charAt(j)) > 0;
					if (isSkip){
						break;
					}
					//System.out.println(line.charAt(j)+" "+isSkip);
				}
				if (!isSkip){
				/////System.out.println("============================== "+nReads+" ==============================");
				//System.out.println(line);
				Iterator iter = tree.search(line.getBytes());
				if(!iter.hasNext()){
					System.out.println(tReads+" "+line);
					nFound++;
				}
				while(iter.hasNext()){ 
				//for (Iterator iter = tree.search(line.getBytes()); iter.hasNext();) {
					SearchResult result = (SearchResult) iter.next();
					//System.out.println(result.getOutputs());
					//System.out.println(line);
					//System.out.println("line at: "+(nReads/4+1)+" Found at index: " + result.getLastIndex());
					//System.out.println("line at: "+nReads+" Found at index: " + result.getLastIndex());
					termsThatHit.addAll(result.getOutputs());
				}
				nReads++;
				}
				}
				tReads++;
			}
			System.out.println("++++++++++++++++++++++++++++++");
			System.out.println(termsThatHit);
			System.out.println("++++++++++++++++++++++++++++++");
			System.out.println("Total used primer count: "+termsThatHit.size());
			System.out.println("Total primer count: "+priSize);
			System.out.println("Total F/R primer count: "+priSize*2);
			double totReads = tReads/4;
			System.out.println("Total Reads: "+totReads);
			System.out.println("Primer not found: "+ nFound);
			double per = nFound / totReads *100;
			System.out.println(per+"% has not primer sequence in reads");

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

