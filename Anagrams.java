/**
 *
 *  @author Mróz Marcin
 *
 */

package ex1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Anagrams {
	private String path;
	List<List<String>> res;
	
	public Anagrams(String allWords) {
		path = allWords;
	}
	

	public List<List<String>> getSortedByAnQty() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(path));
		List<String> words = new ArrayList<>();
		
		while(sc.hasNext()){
			words.add(sc.next());
		}
		sc.close();
		
		List<String> words1 = new ArrayList<>(words);
		
		List<String> length = words.stream()
								.map(s -> s.toCharArray())
								.map(s->{
									int min_index;
								      char temp;
								       
								      for(int i=0; i<s.length-1; i++)
								      {min_index = i;
								          for(int j=min_index+1; j<s.length; j++)
								          if (s[j]<s[min_index])
								            min_index = j;
								             
								        temp = s[min_index];
								        s[min_index] = s[i];
								        s[i] = temp;
								    }       
									return s;
								})
								.map(s->{
									String m = "";
									for(int i = 0; i<s.length; i++){
										m +=s[i];
									}
									return m;
								})
								
								.collect(Collectors.toList());
		
		List<Integer> l = new ArrayList<>();
		List<String> m = new ArrayList<>();
		
		int i = 0;
		m.add(length.get(0));
		
		for(String s : length){
			if(m.contains(s)) l.add(m.indexOf(s));
			else{
				i++;
				l.add(i);
				m.add(s);
			}
		}
		
		List<List<String>> res = new ArrayList<>();
		
		for (int j=0; j<Collections.max(l);){
			List<String> res1 = new ArrayList<>();
			for (int k = 0; k<l.size(); k++){
				if(l.get(k)==j) res1.add(words1.get(k));
			}
			res.add(res1);
			
			j++;
			
			if (j==4){
				List<String> res2 = new ArrayList<>();
				for (int k = 0; k<l.size(); k++){
					if(l.get(k)==j) res2.add(words1.get(k));
				}
				res.add(res2);
			}
		}
		
		Collections.sort(res, new Comparator<List<String>>(){
		    public int compare(List<String> a1, List<String> a2) {
		        return a2.size() - a1.size();
		    }
		});
		
		this.res=res;
		
		return res;
	}


	public String getAnagramsFor(String next) {
		String concat = next+": ";
		List<String> m = new ArrayList<>();
		
		for(List<String> l : this.res){
			if (l.contains(next)){
				for(String s : l){
					if(!s.equals(next)) m.add(s);
				}
			}
		}
		
		return concat+m.toString();
	}
}  
