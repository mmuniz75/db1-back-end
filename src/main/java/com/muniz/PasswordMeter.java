package com.muniz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class PasswordMeter {
	
	private static enum Score {
		NUMBEROFCHARACTERS,	
		UPPERCASELETTERS,	
		LOWERCASELETTERS,	
		NUMBERS,
		SYMBOLS,	
		MIDDLENUMBERSORSYMBOLS,	
		REQUIREMENTS,	
		LETTERSONLY,	
		NUMBERSONLY,	
		REPEATCHARACTERS,	
		CONSECUTIVEUPPERCASELETTERS,	
		CONSECUTIVELOWERCASE,	
		CONSECUTIVENUMBERS,	
		SEQUENTIALLETTERS,	
		SEQUENTIALNUMBERS,	
		SEQUENTIALSYMBOLS
	}
	
	
	private static final Map<Score, String> rates = new HashMap<>();
	static {
	    rates.put(Score.NUMBEROFCHARACTERS,"+(n*4)");	
		rates.put(Score.UPPERCASELETTERS,"+((len-n)*2)");	
		rates.put(Score.LOWERCASELETTERS,"+((len-n)*2)");	
		rates.put(Score.NUMBERS,"+(n*4)");
		rates.put(Score.SYMBOLS,"+(n*6)");	
		rates.put(Score.MIDDLENUMBERSORSYMBOLS,"+(n*2)");	
		rates.put(Score.REQUIREMENTS,"+(n*2)");	
		rates.put(Score.LETTERSONLY,"-n");	
		rates.put(Score.NUMBERSONLY,"-n");	
		rates.put(Score.REPEATCHARACTERS,"-");	
		rates.put(Score.CONSECUTIVEUPPERCASELETTERS,"-(n*2)");	
		rates.put(Score.CONSECUTIVELOWERCASE,"-(n*2)");	
		rates.put(Score.CONSECUTIVENUMBERS,"-(n*2)");	
		rates.put(Score.SEQUENTIALLETTERS,"-(n*3)");	
		rates.put(Score.SEQUENTIALNUMBERS,"-(n*3)");	
		rates.put(Score.SEQUENTIALSYMBOLS,"-(n*3)");
	}  
	
	public static int scorePassword(String password) {
		int score  = 0;
		
		List<String> passwordScores = buildCheckVector(password);
		
		if(passwordScores.size()>0) { 
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			
			for(String passordScore:passwordScores){
				Integer result = 0;
				try{
					result = (Integer)engine.eval(passordScore);
				}catch(Exception ex){ex.printStackTrace();}
				score+= result;
			}
		}	
					
		return score<100?score:100;
	}
	
	private static List<String> buildCheckVector(String password){
		String[] checkVector = new String[]{
												checkNumberOfCharacters(password),
												checkUppercaseLetters(password),
												checkLowercaseLetters(password),
												checkNumbers(password),
												checkSymbols(password),
												checkMiddleNumbersOrSymbols(password),
												checkRequirements(password),
												checkLettersOnly(password),
												checkNumbersOnly(password),
												checkRepeatCharacters(password),
												checkConsecutiveUppercaseLetters(password),
												checkConsecutiveLowercaseLetters(password),
												checkConsecutiveNumbers(password),
												checkSequentialLetters(password),
												checkSequentialNumbers(password),
												checkSequentialSymbols(password)
											 };
		
		List<String> scores = new ArrayList<>(Arrays.asList(checkVector));
		scores.removeIf(item -> item.equals(""));
		
		return scores;
	}
	
	
	private static String checkNumberOfCharacters(String password){
		String rate = "";
		boolean test =  true;
		
		int occur = 1;
		
		if(test) {		
			rate = rates.get(Score.NUMBEROFCHARACTERS);
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
		
		return rate;
	}
	private static String checkUppercaseLetters(String password){
		String rate = "";
		boolean test =  true;
		
		int occur = 1;
		
		if(test) {		
			rate = rates.get(Score.UPPERCASELETTERS);
			int calc = password.length()-occur;
			rate = rate.replaceAll("len-n", Integer.toString(calc));
			
		}	
		
		return rate;
	}
	private static String checkLowercaseLetters(String password){
		String rate = "";
		boolean test =  true;
		
		int occur = 1;
		
		if(test){		
			rate = rates.get(Score.UPPERCASELETTERS);
			int calc = password.length()-occur;
			rate = rate.replaceAll("len-n", Integer.toString(calc));
			
		}	
		return rate;
	}
	private static String checkNumbers(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){		
			rate = rates.get(Score.NUMBERS);
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
		
		
		return rate;
	}
	private static String checkSymbols(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){		
			rate = rates.get(Score.SYMBOLS);
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		
		return rate;
	}
	private static String checkMiddleNumbersOrSymbols(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){			
			rate = rates.get(Score.MIDDLENUMBERSORSYMBOLS);
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		
		return rate;
	}
	private static String checkRequirements(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){			
			rate = rates.get(Score.REQUIREMENTS); 
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
			
		
		return rate;
	}
	private static String checkLettersOnly(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){			
			rate = rates.get(Score.LETTERSONLY); 
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		
		return rate;
	}
	private static String checkNumbersOnly(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){	
			rate = rates.get(Score.NUMBERSONLY);
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		
		return rate;
	}
	
	// source not avaliable
	private static String checkRepeatCharacters(String password){
		String rate = "";
		boolean test =  false;
		int occur = 1;
		
		if(test){			
			rate = rates.get(Score.REPEATCHARACTERS); 
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		
		return rate;
	}
	private static String checkConsecutiveUppercaseLetters(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){		
			rate = rates.get(Score.CONSECUTIVEUPPERCASELETTERS); 	
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		
		return rate;
	}
	private static String checkConsecutiveLowercaseLetters(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){			
			rate = rates.get(Score.CONSECUTIVELOWERCASE); 
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
			
		
		return rate;
	}
	private static String checkConsecutiveNumbers(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){			
			rate = rates.get(Score.CONSECUTIVENUMBERS);
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		
		return rate;
	}
	private static String checkSequentialLetters(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){			
			rate = rates.get(Score.SEQUENTIALLETTERS); 	
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
			
		return rate;
	}
	private static String checkSequentialNumbers(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){			
			rate = rates.get(Score.SEQUENTIALNUMBERS); 	
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		return rate;
	}
	private static String checkSequentialSymbols(String password){
		String rate = "";
		boolean test =  true;
		int occur = 1;
		
		if(test){		
			rate = rates.get(Score.SEQUENTIALSYMBOLS);
			rate = rate.replaceAll("n", Integer.toString(occur));
		}	
	
		
		return rate;
	}
	
	
	public static int scorePasswordDummy(String password){
	
		if(password==null)
			return 0;
		
		 switch(password.length()) {
	     case 1 :
	         return 19;
	     case 2:
	         return 39;
	     case 3:
	         return 61;       
	     case 4:
	         return 81;    
	     case 5:
	         return 100;
	     default:
	         return 0;
	 }    

	}
}	
