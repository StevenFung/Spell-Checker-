package cs310;
import java.util.*;
import java.io.*;
public class SpellChecker {
	
	public static void main(String[] args) throws FileNotFoundException{
		//make that to hashset so i can use the function "contains"
		Set<String> dictionary = new HashSet<String>();  
		Set<String> myDictionary = new HashSet<String>();
		Map<String,List<Integer>> misspell = new TreeMap<String,List<Integer>>( ); 
		String word, word1, word2, fix;
		int i;
		//make a file reader so scanner can read the whole txt file
		FileReader file = new FileReader(args[0]); 
		FileReader file1 = new FileReader(args[1]);
		FileReader file2 = new FileReader(args[2]);
		Scanner scan1 = new Scanner(file1);
		while(scan1.hasNextLine())  
		{
			word1 = scan1.nextLine();
			dictionary.add(word1); 
			//use scanner so i can add all words from the provided dictionary to dictionary hashset
		}
		
		Scanner scan2 = new Scanner(file2);
		while(scan2.hasNextLine())
		{
			word2 = scan2.nextLine();
			myDictionary.add(word2);
			//same as above, but this time is my dictionary
		}

		int lineNumber = 0;
		Scanner scan = new Scanner(file);			
		//read the file that i want to check the misspell
	while(scan.hasNextLine()) 
		{
		lineNumber += 1;
		word = scan.nextLine();
		//find out the punctuation and make the punctuation to space
		word = word.replaceAll("\\W"," ");
		//split the words to string array, the array should contain no space
		String[] words = word.split("\\s+");
			for(i = 0; i < words.length; i++)
			{
				if(dictionary.contains(words[i].toLowerCase()))
				{
					//if the word we check are contains in dictionary, that's mean is a correct word
					continue;
				}
				else if(myDictionary.contains(words[i].toLowerCase()))
				{
					continue;
				}
				else{
					//if not match to dictionary and my dictionary, that's mean this is a misspell word
					//use Treemap fuction to put the misspell inside and provide the line number for the word
					List<Integer> lines = misspell.get(words[i]);
					if(lines == null){
						lines = new ArrayList<Integer>();
						misspell.put(words[i],lines);
					}
					lines.add(lineNumber);
				}
					
			}
		}

    for( Map.Entry<String,List<Integer>> thisNode : misspell.entrySet() )
    {
        Iterator<Integer> lineItr = thisNode.getValue().iterator();

            // Print misspell and first line where it occurs
        System.out.print( thisNode.getKey() + ": " );
        System.out.print( lineItr.next() );
        
            // Print all other lines on which it occurs
        while( lineItr.hasNext() )
        {
            System.out.print( ", " + lineItr.next() );
        }
        System.out.println();
        fix = thisNode.getKey();
        //go to add one character method
        addOneCh(fix, dictionary, myDictionary);
        //go to remove one character method
        rmOneCh(fix, dictionary, myDictionary);
        exAdCh(fix,dictionary,myDictionary);

    }    
}

    static char[]spell = new char[26];
	public static void addOneCh(String tryAdd,Set<String> dictionary,Set<String> myDictionary)
	{
		int n,m;
		for(m = 0; m < 26 ; m ++)
		{
			//create a char array which include each alphabet
			spell [m] = (char)('a' + m);
			for(n =0 ; n < tryAdd.length()+1; n ++)
			{
				StringBuilder addch = new StringBuilder(tryAdd);
				//try to insert one character from spell[m] on n index 
				addch.insert(n,spell[m]);
				if(myDictionary.contains(addch.toString()) || dictionary.contains(addch.toString()))
						{
					//if it match to dictionary after adding one character, print out the correct word
					System.out.println("Add one charater = " + addch.toString());
						}
			}
		}
	}
	
	
	public static void rmOneCh(String tryRm,Set<String> dictionary,Set<String> myDictionary)
	{
		int n;
			for(n =0 ; n < tryRm.length(); n ++)
			{
				StringBuilder rmch = new StringBuilder(tryRm);
				//remove one character on each position
				rmch.deleteCharAt(n);
				if(myDictionary.contains(rmch.toString()) || dictionary.contains(rmch.toString()))
						{
					System.out.println("Remove one charater = " + rmch.toString());
					
						}
			}
		}
	public static void exAdCh(String tryExAd,Set<String> dictionary,Set<String> myDictionary)
	{
		int n;
		int m =1;
		//create one char array include of character in string tryExAd
		char[] exAd = tryExAd.toCharArray();
			for(n =0 ; n < tryExAd.length()-1; n ++)
			{
				StringBuilder exAdc = new StringBuilder(tryExAd);
				//use two setCharAt function to try to swap their postion 
				exAdc.setCharAt(n,exAd[m]);
				exAdc.setCharAt(n+1,exAd[m-1]);
				if(myDictionary.contains(exAdc.toString()) || dictionary.contains(exAdc.toString()))
						{
					System.out.println("Exchange adjacent charater = " + exAdc.toString());
					
						}
				m++;
			}
		}
	}
		
